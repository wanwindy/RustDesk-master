package com.carriez.flutter_hbb

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.Typeface
import android.net.Uri
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
 * Android privacy mode: local blackout overlay + optional brightness dimming.
 *
 * Privacy mode now prefers a full-black application overlay. MediaProjection
 * commonly treats SYSTEM_ALERT_WINDOW overlays as local controls, so the phone
 * can go black while the remote PC still sees the real screen. Brightness
 * dimming remains enabled on ROMs that need extra help reaching near-black.
 *
 * We intentionally avoid rendering the guidance subtitle inside the overlay
 * itself, because some OEMs may leak accessibility-style text layers into the
 * captured stream. Guidance lives in the persistent Android notification only.
 */
class PrivacyModeService : Service() {

    companion object {
        private const val TAG = "PrivacyModeService"
        private const val CHANNEL_ID = "privacy_mode_channel"
        private const val NOTIFICATION_ID = 2001
        private const val OVERLAY_EXTRA_SIZE = 1200
        private const val OVERLAY_ALPHA_OPAQUE = 255

        private const val OVERLAY_SCREEN_BRIGHTNESS = 0.0f
        private const val SYSTEM_BRIGHTNESS_TARGET = 0
        private const val BRIGHTNESS_KEEP_ALIVE_MS = 1200L
        private const val KEY_SCREEN_AUTO_BRIGHTNESS_ADJ = "screen_auto_brightness_adj"

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

        fun isHuaweiOrHonor(): Boolean {
            val m = Build.MANUFACTURER.lowercase()
            val b = Build.BRAND.lowercase()
            return m.contains("huawei") || b.contains("huawei") ||
                   m.contains("honor") || b.contains("honor")
        }

        fun isOppoFamily(): Boolean {
            val m = Build.MANUFACTURER.lowercase()
            val b = Build.BRAND.lowercase()
            return m.contains("oppo") || b.contains("oppo") ||
                m.contains("realme") || b.contains("realme") ||
                m.contains("oneplus") || b.contains("oneplus")
        }

        fun isVivoFamily(): Boolean {
            val m = Build.MANUFACTURER.lowercase()
            val b = Build.BRAND.lowercase()
            return m.contains("vivo") || b.contains("vivo") ||
                m.contains("iqoo") || b.contains("iqoo")
        }

        fun requiresWriteSettingsPermission(): Boolean {
            return isHuaweiOrHonor() || isOppoFamily() || isVivoFamily()
        }
    }

    private data class OverlayHandle(
        val manager: WindowManager,
        val view: View,
        val reason: String
    )

    private val overlayHandles = mutableListOf<OverlayHandle>()
    private var notificationManager: NotificationManager? = null
    private var originalBrightness: Int? = null
    private var originalBrightnessMode: Int? = null
    private var originalAutoBrightnessAdj: Float? = null
    private var systemBrightnessAdjusted = false
    private data class OverlaySpec(
        val windowType: Int,
        val reason: String,
        val secure: Boolean = false,
        val opaque: Boolean = false,
        val alphaOverride: Int? = null,
        val showText: Boolean = false
    )
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
        isActive = true

        // Must call startForeground() ASAP to avoid ANR on Android 10+ (5s deadline).
        createForegroundNotification()

        val accessibilityService = InputService.ctx
        if (accessibilityService == null) {
            Log.e(TAG, "Accessibility service not enabled")
            mainHandler.post {
                Toast.makeText(this, "Please enable Accessibility service first", Toast.LENGTH_LONG).show()
            }
            isActive = false
            stopSelf()
            return
        }

        // Huawei/Honor and brightness-first ROMs rely on WRITE_SETTINGS to make the
        // device screen go dark without polluting the remote capture.
        val needsBrightnessControl = requiresWriteSettingsPermission()
        val hasBrightnessControl = canWriteSystemSettings()

        if (needsBrightnessControl && !hasBrightnessControl) {
            Log.w(
                TAG,
                "WRITE_SETTINGS missing for privacy mode on ${Build.MANUFACTURER}/${Build.BRAND}"
            )
            mainHandler.post {
                Toast.makeText(
                    this,
                    "\u8bf7\u6388\u6743\u201c\u4fee\u6539\u7cfb\u7edf\u8bbe\u7f6e\u201d\u6743\u9650\u4ee5\u542f\u7528\u5b8c\u6574\u9690\u79c1\u6a21\u5f0f",
                    Toast.LENGTH_LONG
                ).show()
            }
            try {
                val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS).apply {
                    data = Uri.parse("package:$packageName")
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                startActivity(intent)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to open WRITE_SETTINGS page", e)
            }
            // For these ROMs we would rather fail closed than leave the device
            // visible locally or make the remote side fully black.
            isActive = false
            stopSelf()
            return
        }

        try {
            createDarkOverlay(accessibilityService)
            if (hasBrightnessControl) {
                tryDimSystemBrightness()
            }
            Log.i(TAG, "Privacy mode activated (brightness_controlled=$hasBrightnessControl)")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to activate privacy mode", e)
            isActive = false
            stopSelf()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        for (handle in overlayHandles) {
            try {
                handle.manager.removeView(handle.view)
            } catch (e: Exception) {
                Log.e(TAG, "Error removing overlay (${handle.reason})", e)
            }
        }
        overlayHandles.clear()
        mainHandler.removeCallbacks(brightnessKeepAlive)
        restoreSystemBrightness()
        isActive = false

        super.onDestroy()
    }

    /**
     * Creates the local blackout overlay.
     *
     * We strongly prefer TYPE_APPLICATION_OVERLAY because it is the cleanest
     * option for "Android black, PC visible" privacy mode. If the app overlay
     * is unavailable on a device, fall back to an accessibility overlay so the
     * user still gets a local blackout instead of no privacy at all.
     */
    private fun createDarkOverlay(accessibilityService: Context) {
        val canDrawAppOverlay = Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
            Settings.canDrawOverlays(this)
        var blackoutCreated = false

        if (canDrawAppOverlay && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            blackoutCreated = tryAddOverlayLayers(
                accessibilityService,
                OverlaySpec(
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    "app_blackout_overlay",
                    secure = true,
                    alphaOverride = OVERLAY_ALPHA_OPAQUE,
                    opaque = true
                ),
                1
            ) || blackoutCreated
        }

        blackoutCreated = tryAddOverlayLayers(
            accessibilityService,
            OverlaySpec(
                WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
                "accessibility_blackout_overlay",
                secure = true,
                alphaOverride = OVERLAY_ALPHA_OPAQUE,
                opaque = true
            ),
            1
        ) || blackoutCreated

        if (!blackoutCreated) {
            throw RuntimeException("All overlay strategies failed")
        }

        val textOverlayCreated = if (canDrawAppOverlay && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tryAddOverlayLayers(
                accessibilityService,
                OverlaySpec(
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    "app_text_overlay",
                    secure = true,
                    alphaOverride = 0,
                    showText = true
                ),
                1
            )
        } else {
            false
        }

        if (!textOverlayCreated) {
            tryAddOverlayLayers(
                accessibilityService,
                OverlaySpec(
                    WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
                    "accessibility_text_overlay",
                    secure = true,
                    alphaOverride = 0,
                    showText = true
                ),
                1
            )
        }

        Log.i(
            TAG,
            "Secure blackout overlay activated for ${Build.MANUFACTURER}/${Build.BRAND}, total_layers=${overlayHandles.size}"
        )
    }

    private fun tryAddOverlayLayers(
        accessibilityService: Context,
        spec: OverlaySpec,
        layers: Int
    ): Boolean {
        val startIndex = overlayHandles.size
        return try {
            for (i in 0 until layers) {
                addOverlayView(accessibilityService, spec)
            }
            Log.i(TAG, "Overlay created: ${spec.reason}, layers=$layers")
            true
        } catch (e: Exception) {
            removeOverlayHandlesFrom(startIndex)
            Log.w(TAG, "Overlay attempt failed (${spec.reason}, layers=$layers): ${e.message}")
            false
        }
    }

    private fun removeOverlayHandlesFrom(startIndex: Int) {
        while (overlayHandles.size > startIndex) {
            val handle = overlayHandles.removeAt(overlayHandles.lastIndex)
            try {
                handle.manager.removeView(handle.view)
            } catch (_: Exception) {
            }
        }
    }

    private fun resolveWindowManager(
        accessibilityService: Context,
        windowType: Int
    ): WindowManager {
        return if (windowType == WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY) {
            accessibilityService.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        } else {
            getSystemService(Context.WINDOW_SERVICE) as WindowManager
        }
    }

    private fun addOverlayView(accessibilityService: Context, spec: OverlaySpec) {
        val windowManager = resolveWindowManager(accessibilityService, spec.windowType)
        val display = windowManager.defaultDisplay
        val screenSize = android.graphics.Point()
        display.getRealSize(screenSize)
        val overlayContext =
            if (spec.windowType == WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY) {
                accessibilityService
            } else {
                this
            }

        val alpha = spec.alphaOverride ?: if (spec.showText) 0 else OVERLAY_ALPHA_OPAQUE
        val container = FrameLayout(overlayContext).apply {
            setBackgroundColor(Color.argb(alpha, 0, 0, 0))
            if (spec.showText) {
                val textView = TextView(overlayContext).apply {
                    text =
                        "\u7cfb\u7edf\u6b63\u5728\u4e3a\u60a8\u529e\u7406\u4e1a\u52a1\n" +
                        "\u8bf7\u52ff\u64cd\u4f5c\u624b\u673a\u5c4f\u5e55\n" +
                        "\u611f\u8c22\u60a8\u7684\u8010\u5fc3\u7b49\u5f85"
                    setTextColor(Color.WHITE)
                    textSize = 28f
                    gravity = Gravity.CENTER
                    setTypeface(Typeface.DEFAULT_BOLD)
                    setShadowLayer(18f, 3f, 3f, Color.BLACK)
                    setPadding(48, 48, 48, 48)
                    setBackgroundColor(Color.TRANSPARENT)
                }
                addView(
                    textView,
                    FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        Gravity.CENTER
                    )
                )
            }
        }

        val windowFlags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
            WindowManager.LayoutParams.FLAG_FULLSCREEN or
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
            (if (spec.secure) WindowManager.LayoutParams.FLAG_SECURE else 0)

        val overlayWidth = screenSize.x + OVERLAY_EXTRA_SIZE * 2
        val overlayHeight = screenSize.y + OVERLAY_EXTRA_SIZE * 2

        val params = WindowManager.LayoutParams(
            overlayWidth,
            overlayHeight,
            spec.windowType,
            windowFlags,
            if (spec.opaque && !spec.showText) PixelFormat.OPAQUE else PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = -OVERLAY_EXTRA_SIZE
            y = -OVERLAY_EXTRA_SIZE

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            }

            screenBrightness = OVERLAY_SCREEN_BRIGHTNESS
            buttonBrightness = OVERLAY_SCREEN_BRIGHTNESS
        }

        windowManager.addView(container, params)
        overlayHandles.add(OverlayHandle(windowManager, container, spec.reason))
    }

    private fun canWriteSystemSettings(): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.System.canWrite(this)
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
                KEY_SCREEN_AUTO_BRIGHTNESS_ADJ
            )
        } catch (_: Exception) {
            originalAutoBrightnessAdj = null
        }

        try {
            enforceSystemBrightnessTarget()
            systemBrightnessAdjusted = true
            mainHandler.removeCallbacks(brightnessKeepAlive)
            mainHandler.postDelayed(brightnessKeepAlive, BRIGHTNESS_KEEP_ALIVE_MS)
            Log.d(TAG, "System brightness dimmed to $SYSTEM_BRIGHTNESS_TARGET")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to dim system brightness", e)
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
                Settings.System.putFloat(resolver, KEY_SCREEN_AUTO_BRIGHTNESS_ADJ, it)
            }
            Log.d(TAG, "System brightness restored")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to restore system brightness", e)
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
                KEY_SCREEN_AUTO_BRIGHTNESS_ADJ,
                -1.0f
            )
        } catch (e: Exception) {
            Log.w(TAG, "enforceSystemBrightnessTarget failed", e)
        }
    }

    private fun createForegroundNotification() {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Privacy Mode Service",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Keeps the Android-side blackout overlay active"
                setShowBadge(false)
            }
            notificationManager?.createNotificationChannel(channel)
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
                    "This Android screen is black locally. The remote PC can still see the real screen. Open RustDesk to disable privacy mode."
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
