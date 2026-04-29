package com.carriez.flutter_hbb

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.NotificationCompat
import kotlin.math.max

/**
 * Privacy mode follows the APK-derived route directly:
 * - draw a full-screen accessibility blackout overlay
 * - keep the blackout overlay owned by AccessibilityService
 * - remove all brightness and vendor-specific fallback logic
 */
class PrivacyModeService : Service() {

    companion object {
        private const val TAG = "PrivacyModeService"
        private const val CHANNEL_ID = "privacy_mode_channel"
        private const val NOTIFICATION_ID = 2001

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

    private var windowManager: WindowManager? = null
    private var accessibilityService: InputService? = null

    private var screenWidth = 0
    private var screenHeight = 0
    private var screenDensity = 0

    override fun onCreate() {
        super.onCreate()
        isActive = true
        createForegroundNotification()

        val inputService = InputService.ctx
        if (inputService == null) {
            failToStart("Please enable Accessibility service first")
            return
        }

        if (MainService.currentMediaProjection() == null) {
            failToStart("Please start screen capture service first")
            return
        }

        try {
            accessibilityService = inputService
            windowManager =
                inputService.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            loadScreenMetrics(inputService)
            inputService.showPrivacyOverlay(screenWidth, screenHeight, 0, 0)

            Log.i(
                TAG,
                "Privacy mode activated: ${screenWidth}x${screenHeight}@${screenDensity}, preview=disabled, device=${Build.MANUFACTURER}/${Build.BRAND}/${Build.MODEL}"
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
    }

    private fun stopCapture() {
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
                    "This Android screen is black locally. The remote session continues while the local device display stays hidden."
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
