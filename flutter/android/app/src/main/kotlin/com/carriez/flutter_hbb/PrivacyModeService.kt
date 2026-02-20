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
 * Android privacy mode with adaptive overlay + brightness dimming.
 */
class PrivacyModeService : Service() {

    companion object {
        private const val TAG = "PrivacyModeService"
        private const val CHANNEL_ID = "privacy_mode_channel"
        private const val NOTIFICATION_ID = 2001
        // Adaptive profile: keep remote screen visible while obscuring local content.
        private const val OVERLAY_ALPHA_WITH_BRIGHTNESS = 112
        private const val OVERLAY_ALPHA_NO_BRIGHTNESS = 132
        private const val OVERLAY_ALPHA_XIAOMI_APP_OVERLAY = 255
        private const val OVERLAY_EXTRA_SIZE = 1200
        private const val OVERLAY_SCREEN_BRIGHTNESS = 0.0f
        private const val SYSTEM_BRIGHTNESS_TARGET = 0
        private const val BRIGHTNESS_KEEP_ALIVE_MS = 1200L

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
    private var originalAutoBrightnessAdj: Float? = null
    private var systemBrightnessAdjusted = false
    private data class OverlaySpec(val windowType: Int, val alpha: Int, val reason: String)
    private val mainHandler = Handler(Looper.getMainLooper())
    private val brightnessKeepAlive = object : Runnable {
        override fun run() {
            if (!isActive || !systemBrightnessAdjusted) return
            enforceSystemBrightnessTarget()
            mainHandler.postDelayed(this, BRIGHTNESS_KEEP_ALIVE_MS)
        }
    }

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
            val overlaySpec = resolveOverlaySpec(canWriteSystem)
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
            createDarkOverlay(accessibilityService, overlaySpec)
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
        mainHandler.removeCallbacks(brightnessKeepAlive)
        restoreSystemBrightness()
        isActive = false

        Handler(Looper.getMainLooper()).post {
            Toast.makeText(this, "Privacy mode disabled", Toast.LENGTH_SHORT).show()
        }

        super.onDestroy()
    }

    private fun createDarkOverlay(accessibilityService: Context, overlaySpec: OverlaySpec) {
        windowManager = accessibilityService.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val display = windowManager?.defaultDisplay
        val screenSize = android.graphics.Point()
        display?.getRealSize(screenSize)
        val screenWidth = screenSize.x
        val screenHeight = screenSize.y

        Log.d(
            TAG,
            "DEBUG_PRIVACY: Screen size=${screenWidth}x${screenHeight}, alpha=${overlaySpec.alpha}, type=${overlaySpec.windowType}, reason=${overlaySpec.reason}, windowBrightness=$OVERLAY_SCREEN_BRIGHTNESS, systemBrightnessAdjusted=$systemBrightnessAdjusted"
        )

        val container = FrameLayout(accessibilityService).apply {
            setBackgroundColor(Color.argb(overlaySpec.alpha, 0, 0, 0))

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

        val secureOverlay = overlaySpec.windowType == WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        val windowFlags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
            WindowManager.LayoutParams.FLAG_FULLSCREEN or
            (if (secureOverlay) WindowManager.LayoutParams.FLAG_SECURE else 0)

        val overlayWidth = screenWidth + OVERLAY_EXTRA_SIZE * 2
        val overlayHeight = screenHeight + OVERLAY_EXTRA_SIZE * 2

        val params = WindowManager.LayoutParams(
            overlayWidth,
            overlayHeight,
            overlaySpec.windowType,
            windowFlags,
            if (secureOverlay) PixelFormat.OPAQUE else PixelFormat.TRANSLUCENT
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

        try {
            windowManager?.addView(container, params)
            overlayView = container
            Log.d(TAG, "DEBUG_PRIVACY: Overlay created")
        } catch (e: Exception) {
            if (overlaySpec.windowType == WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY) {
                Log.w(TAG, "DEBUG_PRIVACY: App overlay failed, fallback to accessibility overlay", e)
                val fallback = OverlaySpec(
                    WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
                    resolveAccessibilityOverlayAlpha(canWriteSystemSettings()),
                    "fallback_accessibility_overlay"
                )
                createDarkOverlay(accessibilityService, fallback)
            } else {
                throw e
            }
        }
    }

    private fun canWriteSystemSettings(): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.System.canWrite(this)
    }

    private fun resolveAccessibilityOverlayAlpha(canWriteSystem: Boolean): Int {
        val manufacturer = Build.MANUFACTURER.lowercase()
        val brand = Build.BRAND.lowercase()
        val base = if (canWriteSystem) OVERLAY_ALPHA_WITH_BRIGHTNESS else OVERLAY_ALPHA_NO_BRIGHTNESS
        val extra = when {
            manufacturer.contains("huawei") || manufacturer.contains("honor") ||
                brand.contains("huawei") || brand.contains("honor") -> 14
            manufacturer.contains("xiaomi") || manufacturer.contains("redmi") ||
                manufacturer.contains("poco") || brand.contains("xiaomi") ||
                brand.contains("redmi") || brand.contains("poco") -> 10
            manufacturer.contains("oppo") || manufacturer.contains("vivo") ||
                manufacturer.contains("oneplus") || brand.contains("oppo") ||
                brand.contains("vivo") || brand.contains("oneplus") -> 12
            manufacturer.contains("samsung") || brand.contains("samsung") -> 8
            else -> 6
        }
        return (base + extra).coerceAtMost(150)
    }

    private fun resolveOverlaySpec(canWriteSystem: Boolean): OverlaySpec {
        val manufacturer = Build.MANUFACTURER.lowercase()
        val brand = Build.BRAND.lowercase()
        val isXiaomiLike = manufacturer.contains("xiaomi") || manufacturer.contains("redmi") ||
            manufacturer.contains("poco") || brand.contains("xiaomi") ||
            brand.contains("redmi") || brand.contains("poco")
        val canDrawAppOverlay = Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(this)
        return if (isXiaomiLike && canDrawAppOverlay && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            OverlaySpec(
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                OVERLAY_ALPHA_XIAOMI_APP_OVERLAY,
                "xiaomi_app_overlay"
            )
        } else {
            OverlaySpec(
                WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
                resolveAccessibilityOverlayAlpha(canWriteSystem),
                "default_accessibility_overlay"
            )
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
            originalAutoBrightnessAdj = Settings.System.getFloat(
                resolver,
                Settings.System.SCREEN_AUTO_BRIGHTNESS_ADJ
            )
        } catch (_: Exception) {
            originalAutoBrightnessAdj = null
        }

        try {
            enforceSystemBrightnessTarget()
            systemBrightnessAdjusted = true
            mainHandler.removeCallbacks(brightnessKeepAlive)
            mainHandler.postDelayed(brightnessKeepAlive, BRIGHTNESS_KEEP_ALIVE_MS)
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
        mainHandler.removeCallbacks(brightnessKeepAlive)
        val resolver = contentResolver
        try {
            originalBrightness?.let {
                Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, it)
            }
            originalBrightnessMode?.let {
                Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS_MODE, it)
            }
            originalAutoBrightnessAdj?.let {
                Settings.System.putFloat(resolver, Settings.System.SCREEN_AUTO_BRIGHTNESS_ADJ, it)
            }
            Log.d(TAG, "DEBUG_PRIVACY: System brightness restored")
        } catch (e: Exception) {
            Log.e(TAG, "DEBUG_PRIVACY: Failed to restore system brightness", e)
        } finally {
            systemBrightnessAdjusted = false
        }
    }

    private fun enforceSystemBrightnessTarget() {
        if (!canWriteSystemSettings()) return
        val resolver = contentResolver
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
            // Some ROMs still consult this adjustment value.
            Settings.System.putFloat(
                resolver,
                Settings.System.SCREEN_AUTO_BRIGHTNESS_ADJ,
                -1.0f
            )
        } catch (e: Exception) {
            Log.w(TAG, "DEBUG_PRIVACY: enforceSystemBrightnessTarget failed", e)
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
