package com.carriez.flutter_hbb

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.hardware.display.DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR
import android.hardware.display.VirtualDisplay
import android.media.Image
import android.media.ImageReader
import android.media.projection.MediaProjection
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.os.Looper
import android.os.Process
import android.os.SystemClock
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.NotificationCompat
import kotlin.math.max

/**
 * Privacy mode follows the APK-derived route directly:
 * - draw a full-screen accessibility blackout overlay
 * - keep a small local preview driven by MediaProjection
 * - remove all brightness and vendor-specific fallback logic
 */
class PrivacyModeService : Service() {

    companion object {
        private const val TAG = "PrivacyModeService"
        private const val CHANNEL_ID = "privacy_mode_channel"
        private const val NOTIFICATION_ID = 2001
        private const val PREVIEW_DIVISOR = 4
        private const val PREVIEW_UPDATE_INTERVAL_MS = 120L

        @Volatile
        private var isActive = false

        @Synchronized
        fun startPrivacyMode(context: Context) {
            if (isActive) return

            val intent = Intent(context, PrivacyModeService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }

        @Synchronized
        fun stopPrivacyMode(context: Context) {
            if (!isActive) return
            context.stopService(Intent(context, PrivacyModeService::class.java))
        }
    }

    private val mainHandler = Handler(Looper.getMainLooper())

    private var captureThread: HandlerThread? = null
    private var captureHandler: Handler? = null
    private var windowManager: WindowManager? = null
    private var accessibilityService: InputService? = null

    private var imageReader: ImageReader? = null
    private var mediaProjection: MediaProjection? = null
    private var mediaProjectionCallback: MediaProjection.Callback? = null
    private var virtualDisplay: VirtualDisplay? = null
    private var projectionRunning = false

    private var screenWidth = 0
    private var screenHeight = 0
    private var screenDensity = 0
    private var previewWidth = 0
    private var previewHeight = 0
    private var lastPreviewUpdateAt = 0L

    override fun onCreate() {
        super.onCreate()
        isActive = true
        createForegroundNotification()

        val inputService = InputService.ctx
        if (inputService == null) {
            failToStart("Please enable Accessibility service first")
            return
        }

        val projection = MainService.currentMediaProjection()
        if (projection == null) {
            failToStart("Please start screen capture service first")
            return
        }

        try {
            accessibilityService = inputService
            windowManager =
                inputService.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            loadScreenMetrics(inputService)
            startCaptureThread()

            mediaProjection = projection
            registerMediaProjectionCallback()
            startMediaProjection()
            inputService.showPrivacyOverlay(screenWidth, screenHeight, previewWidth, previewHeight)

            Log.i(
                TAG,
                "Privacy mode activated: ${screenWidth}x${screenHeight}@${screenDensity}, preview=${previewWidth}x${previewHeight}, device=${Build.MANUFACTURER}/${Build.BRAND}/${Build.MODEL}"
            )
        } catch (e: Exception) {
            Log.e(TAG, "Failed to activate privacy mode", e)
            failToStart("Privacy mode failed to start")
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        stopCapture()
        stopCaptureThread()
        mainHandler.removeCallbacksAndMessages(null)
        isActive = false
        super.onDestroy()
    }

    private fun failToStart(message: String) {
        Log.e(TAG, message)
        mainHandler.post {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
        stopCapture()
        stopCaptureThread()
        isActive = false
        stopSelf()
    }

    private fun loadScreenMetrics(context: Context) {
        val manager = windowManager ?: throw IllegalStateException("WindowManager unavailable")
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val bounds = manager.maximumWindowMetrics.bounds
            screenWidth = bounds.width()
            screenHeight = bounds.height()
            screenDensity = context.resources.configuration.densityDpi
        } else {
            val dm = DisplayMetrics()
            manager.defaultDisplay.getRealMetrics(dm)
            screenWidth = dm.widthPixels
            screenHeight = dm.heightPixels
            screenDensity = dm.densityDpi
        }

        if (screenWidth <= 0 || screenHeight <= 0) {
            screenWidth = max(1, SCREEN_INFO.width)
            screenHeight = max(1, SCREEN_INFO.height)
            screenDensity = max(1, SCREEN_INFO.dpi)
        }

        previewWidth = max(1, screenWidth / PREVIEW_DIVISOR)
        previewHeight = max(1, screenHeight / PREVIEW_DIVISOR)
    }

    private fun startCaptureThread() {
        if (captureThread != null) return
        val thread = HandlerThread("PrivacyPreview", Process.THREAD_PRIORITY_BACKGROUND)
        thread.start()
        captureThread = thread
        captureHandler = Handler(thread.looper)
    }

    private fun stopCaptureThread() {
        captureHandler = null
        captureThread?.quitSafely()
        captureThread = null
    }

    private fun registerMediaProjectionCallback() {
        if (mediaProjection == null || mediaProjectionCallback != null) return

        mediaProjectionCallback = object : MediaProjection.Callback() {
            override fun onStop() {
                Log.w(TAG, "MediaProjection stopped by system, closing privacy mode")
                mainHandler.post {
                    stopSelf()
                }
            }
        }
        mediaProjection?.registerCallback(mediaProjectionCallback!!, mainHandler)
    }

    private fun unregisterMediaProjectionCallback() {
        val projection = mediaProjection ?: return
        val callback = mediaProjectionCallback ?: return
        try {
            projection.unregisterCallback(callback)
        } catch (e: Exception) {
            Log.w(TAG, "Failed to unregister MediaProjection callback", e)
        } finally {
            mediaProjectionCallback = null
        }
    }

    private fun startMediaProjection() {
        val projection = mediaProjection ?: throw IllegalStateException("MediaProjection unavailable")
        val handler = captureHandler ?: throw IllegalStateException("Capture handler unavailable")
        if (projectionRunning) return

        imageReader = ImageReader.newInstance(
            screenWidth,
            screenHeight,
            PixelFormat.RGBA_8888,
            2
        ).apply {
            setOnImageAvailableListener({ reader ->
                handlePreviewFrame(reader)
            }, handler)
        }

        virtualDisplay = projection.createVirtualDisplay(
            "RustDeskPrivacyPreview",
            screenWidth,
            screenHeight,
            screenDensity,
            VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
            imageReader!!.surface,
            null,
            handler
        )

        projectionRunning = true
        lastPreviewUpdateAt = 0L
    }

    private fun handlePreviewFrame(reader: ImageReader) {
        if (!projectionRunning) return

        val image = reader.acquireLatestImage() ?: return
        try {
            val now = SystemClock.elapsedRealtime()
            if (now - lastPreviewUpdateAt < PREVIEW_UPDATE_INTERVAL_MS) {
                return
            }
            lastPreviewUpdateAt = now
            updatePreviewFromImage(image)
        } catch (e: Exception) {
            Log.w(TAG, "Failed to update local preview", e)
        } finally {
            image.close()
        }
    }

    private fun updatePreviewFromImage(image: Image) {
        val plane = image.planes.firstOrNull() ?: return
        val pixelStride = plane.pixelStride
        if (pixelStride <= 0) return

        val buffer = plane.buffer
        val rowStride = plane.rowStride
        val rowPadding = max(0, rowStride - pixelStride * image.width)
        val bitmapWidth = image.width + rowPadding / pixelStride

        buffer.rewind()

        var baseBitmap: Bitmap? = null
        var croppedBitmap: Bitmap? = null
        var scaledBitmap: Bitmap? = null

        try {
            baseBitmap = Bitmap.createBitmap(
                bitmapWidth,
                image.height,
                Bitmap.Config.ARGB_8888
            )
            baseBitmap.copyPixelsFromBuffer(buffer)

            val cropped = if (bitmapWidth != image.width) {
                Bitmap.createBitmap(baseBitmap, 0, 0, image.width, image.height).also {
                    croppedBitmap = it
                }
            } else {
                baseBitmap
            }

            val previewBitmap = if (
                cropped.width != previewWidth ||
                cropped.height != previewHeight
            ) {
                Bitmap.createScaledBitmap(cropped, previewWidth, previewHeight, true).also {
                    scaledBitmap = it
                }
            } else {
                cropped
            }

            if (baseBitmap !== cropped && baseBitmap != null && !baseBitmap.isRecycled) {
                baseBitmap.recycle()
                baseBitmap = null
            }
            if (croppedBitmap != null && croppedBitmap !== previewBitmap && !croppedBitmap.isRecycled) {
                croppedBitmap.recycle()
                croppedBitmap = null
            }

            if (previewBitmap === baseBitmap) {
                baseBitmap = null
            }
            if (previewBitmap === croppedBitmap) {
                croppedBitmap = null
            }
            if (previewBitmap === scaledBitmap) {
                scaledBitmap = null
            }

            mainHandler.post {
                val inputService = accessibilityService
                if (!projectionRunning || inputService == null) {
                    if (!previewBitmap.isRecycled) {
                        previewBitmap.recycle()
                    }
                    return@post
                }

                inputService.updatePrivacyPreview(previewBitmap)
            }
        } finally {
            if (baseBitmap != null && !baseBitmap.isRecycled) {
                baseBitmap.recycle()
            }
            if (croppedBitmap != null && !croppedBitmap.isRecycled) {
                croppedBitmap.recycle()
            }
            if (scaledBitmap != null && !scaledBitmap.isRecycled) {
                scaledBitmap.recycle()
            }
        }
    }

    private fun stopMediaProjection() {
        projectionRunning = false
        unregisterMediaProjectionCallback()

        try {
            imageReader?.setOnImageAvailableListener(null, null)
        } catch (_: Exception) {
        }

        try {
            virtualDisplay?.release()
        } catch (e: Exception) {
            Log.w(TAG, "Failed to release privacy VirtualDisplay", e)
        } finally {
            virtualDisplay = null
        }

        try {
            imageReader?.close()
        } catch (e: Exception) {
            Log.w(TAG, "Failed to close privacy ImageReader", e)
        } finally {
            imageReader = null
        }

        mediaProjection = null
    }

    private fun stopCapture() {
        stopMediaProjection()
        accessibilityService?.hidePrivacyOverlay()
        accessibilityService = null
        stopForeground(true)
    }

    private fun createForegroundNotification() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Privacy Mode Service",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Keeps the Android blackout overlay active"
                setShowBadge(false)
            }
            notificationManager.createNotificationChannel(channel)
        }

        val launchIntent = packageManager.getLaunchIntentForPackage(packageName)?.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        }
        val pendingIntent = launchIntent?.let {
            val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }
            PendingIntent.getActivity(this, 0, it, flags)
        }

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Privacy mode active")
            .setContentText("This Android screen is black locally. Open RustDesk to disable it.")
            .setStyle(
                NotificationCompat.BigTextStyle().bigText(
                    "This Android screen is black locally. The remote session continues while the local device only shows a protected preview."
                )
            )
            .setSmallIcon(R.mipmap.ic_stat_logo)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .setAutoCancel(false)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(NOTIFICATION_ID, notification)
    }
}
