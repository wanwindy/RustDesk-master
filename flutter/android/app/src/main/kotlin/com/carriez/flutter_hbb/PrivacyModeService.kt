package com.carriez.flutter_hbb

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.Point
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
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
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat

/**
 * Privacy mode is intentionally split into vendor routes instead of trying to
 * force one rendering technique across every ROM.
 *
 * Dedicated blackout overlay:
 * - Enabled only on model allowlists that are known to keep the remote PC
 *   visible while still blacking out Android locally.
 *
 * General compatibility route:
 * - Prefer a 1x1 brightness anchor plus aggressive system brightness dimming.
 * - Keep local subtitle prompts outside of capture overlays whenever possible.
 */
class PrivacyModeService : Service() {

    companion object {
        private enum class Strategy {
            HUAWEI_HONOR_OVERLAY,
            BRIGHTNESS_COMPAT
        }

        private enum class OverlayKind {
            BLACKOUT,
            BRIGHTNESS_ANCHOR
        }

        private const val TAG = "PrivacyModeService"
        private const val CHANNEL_ID = "privacy_mode_channel"
        private const val NOTIFICATION_ID = 2001
        private const val OVERLAY_EXTRA_SIZE = 1200
        private const val BRIGHTNESS_ANCHOR_SIZE = 1
        private const val OVERLAY_SCREEN_BRIGHTNESS = 0.0f
        private const val SYSTEM_BRIGHTNESS_TARGET = 0
        private const val BRIGHTNESS_KEEP_ALIVE_MS = 400L
        private const val SUBTITLE_TOAST_INTERVAL_MS = 3500L
        private const val KEY_SCREEN_AUTO_BRIGHTNESS_ADJ = "screen_auto_brightness_adj"

        private val SUBTITLE_LINES = listOf(
            "\u7cfb\u7edf\u6b63\u5728\u4e3a\u60a8\u529e\u7406\u4e1a\u52a1",
            "\u8bf7\u52ff\u64cd\u4f5c\u624b\u673a\u5c4f\u5e55",
            "\u611f\u8c22\u60a8\u7684\u8010\u5fc3\u7b49\u5f85"
        )

        @Volatile
        private var isActive = false
        @Volatile
        private var activeStrategy = Strategy.BRIGHTNESS_COMPAT

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

        private fun containsAny(source: String, vararg values: String): Boolean {
            return values.any { source.contains(it) }
        }

        fun isHuaweiOrHonor(): Boolean {
            val manufacturer = Build.MANUFACTURER.lowercase()
            val brand = Build.BRAND.lowercase()
            return containsAny(manufacturer, "huawei", "honor") ||
                containsAny(brand, "huawei", "honor")
        }

        fun isHonorX50Family(): Boolean {
            val manufacturer = Build.MANUFACTURER.lowercase()
            val brand = Build.BRAND.lowercase()
            val model = Build.MODEL.lowercase()
            return (manufacturer.contains("honor") || brand.contains("honor")) &&
                containsAny(model, "x50", "ali-an00", "ali-nx1")
        }

        fun isOppoFamily(): Boolean {
            val manufacturer = Build.MANUFACTURER.lowercase()
            val brand = Build.BRAND.lowercase()
            return containsAny(manufacturer, "oppo", "realme", "oneplus") ||
                containsAny(brand, "oppo", "realme", "oneplus")
        }

        fun isVivoFamily(): Boolean {
            val manufacturer = Build.MANUFACTURER.lowercase()
            val brand = Build.BRAND.lowercase()
            return containsAny(manufacturer, "vivo", "iqoo") ||
                containsAny(brand, "vivo", "iqoo")
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

    private data class OverlaySpec(
        val windowType: Int,
        val reason: String,
        val kind: OverlayKind,
        val secure: Boolean,
        val showSubtitle: Boolean = false
    )

    private val overlayHandles = mutableListOf<OverlayHandle>()
    private var notificationManager: NotificationManager? = null
    private var originalBrightness: Int? = null
    private var originalBrightnessMode: Int? = null
    private var originalAutoBrightnessAdj: Float? = null
    private var systemBrightnessAdjusted = false
    private var currentSubtitleToast: Toast? = null
    private val mainHandler = Handler(Looper.getMainLooper())
    private val brightnessKeepAlive = object : Runnable {
        override fun run() {
            if (!isActive || !systemBrightnessAdjusted) return
            enforceSystemBrightnessTarget()
            mainHandler.postDelayed(this, BRIGHTNESS_KEEP_ALIVE_MS)
        }
    }
    private val subtitleToastKeepAlive = object : Runnable {
        override fun run() {
            if (!isActive) return
            showSubtitleToast()
            mainHandler.postDelayed(this, SUBTITLE_TOAST_INTERVAL_MS)
        }
    }

    override fun onCreate() {
        super.onCreate()
        isActive = true

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

        activeStrategy = selectStrategy()
        val hasBrightnessControl = canWriteSystemSettings()

        if (!hasBrightnessControl && requiresWriteSettingsPermission()) {
            promptForWriteSettingsPermission()
        }

        try {
            val activated = when (activeStrategy) {
                Strategy.HUAWEI_HONOR_OVERLAY -> activateHuaweiHonorRoute(accessibilityService)
                Strategy.BRIGHTNESS_COMPAT -> activateBrightnessCompatRoute(accessibilityService)
            }

            if (!activated) {
                throw RuntimeException("No privacy-mode route could be activated")
            }

            if (hasBrightnessControl) {
                tryDimSystemBrightness()
            }

            startSubtitleToastKeepAlive()

            Log.i(
                TAG,
                "Privacy mode activated: strategy=${activeStrategy.name}, device=${Build.MANUFACTURER}/${Build.BRAND}/${Build.MODEL}, sdk=${Build.VERSION.SDK_INT}, brightness_controlled=$hasBrightnessControl"
            )
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
        mainHandler.removeCallbacks(subtitleToastKeepAlive)
        currentSubtitleToast?.cancel()
        currentSubtitleToast = null
        restoreSystemBrightness()
        isActive = false
        activeStrategy = Strategy.BRIGHTNESS_COMPAT
        super.onDestroy()
    }

    private fun selectStrategy(): Strategy {
        return if (isHonorX50Family()) {
            Strategy.HUAWEI_HONOR_OVERLAY
        } else {
            Strategy.BRIGHTNESS_COMPAT
        }
    }

    private fun activateHuaweiHonorRoute(accessibilityService: Context): Boolean {
        clearOverlayHandles()

        if (!canUseApplicationOverlay()) {
            Log.w(TAG, "Huawei/Honor overlay permission unavailable, falling back to brightness route")
            return activateBrightnessCompatRoute(accessibilityService)
        }

        val appOverlayType = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        val overlayCreated = tryAddOverlay(accessibilityService, OverlaySpec(
                appOverlayType,
                "huawei_honor_blackout_app",
                OverlayKind.BLACKOUT,
                secure = false,
                showSubtitle = false
            ))
        if (!overlayCreated) {
            clearOverlayHandles()
            Log.w(TAG, "Huawei/Honor app overlay failed, falling back to brightness route")
            return activateBrightnessCompatRoute(accessibilityService)
        }

        activeStrategy = Strategy.HUAWEI_HONOR_OVERLAY
        return true
    }

    private fun activateBrightnessCompatRoute(accessibilityService: Context): Boolean {
        clearOverlayHandles()

        val preferredWindowType = preferredOverlayWindowType()
        if (!tryAddOverlay(accessibilityService, OverlaySpec(
                preferredWindowType,
                "brightness_anchor",
                OverlayKind.BRIGHTNESS_ANCHOR,
                secure = false
            ))
        ) {
            val fallbackWindowType = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
            if (preferredWindowType == fallbackWindowType || !tryAddOverlay(
                    accessibilityService,
                    OverlaySpec(
                        fallbackWindowType,
                        "brightness_anchor_accessibility",
                        OverlayKind.BRIGHTNESS_ANCHOR,
                        secure = false
                    )
                )
            ) {
                clearOverlayHandles()
                return false
            }
        }

        activeStrategy = Strategy.BRIGHTNESS_COMPAT
        return true
    }

    private fun tryAddOverlay(accessibilityService: Context, spec: OverlaySpec): Boolean {
        return try {
            addOverlayView(accessibilityService, spec)
            Log.i(TAG, "Overlay created: ${spec.reason}")
            true
        } catch (e: Exception) {
            Log.w(TAG, "Overlay attempt failed (${spec.reason}): ${e.message}")
            false
        }
    }

    private fun clearOverlayHandles() {
        while (overlayHandles.isNotEmpty()) {
            val handle = overlayHandles.removeAt(overlayHandles.lastIndex)
            try {
                handle.manager.removeView(handle.view)
            } catch (_: Exception) {
            }
        }
    }

    private fun canUseApplicationOverlay(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
            (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(this))
    }

    private fun preferredOverlayWindowType(): Int {
        return if (canUseApplicationOverlay()) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
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
        val overlayContext =
            if (spec.windowType == WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY) {
                accessibilityService
            } else {
                this
            }

        val view = createOverlayView(overlayContext, spec)
        val isFullScreenOverlay = spec.kind == OverlayKind.BLACKOUT
        val windowFlags = buildWindowFlags(spec)

        val overlayWidth: Int
        val overlayHeight: Int
        val overlayX: Int
        val overlayY: Int
        if (isFullScreenOverlay) {
            val display = windowManager.defaultDisplay
            val screenSize = Point()
            display.getRealSize(screenSize)
            overlayWidth = screenSize.x + OVERLAY_EXTRA_SIZE * 2
            overlayHeight = screenSize.y + OVERLAY_EXTRA_SIZE * 2
            overlayX = -OVERLAY_EXTRA_SIZE
            overlayY = -OVERLAY_EXTRA_SIZE
        } else {
            overlayWidth = BRIGHTNESS_ANCHOR_SIZE
            overlayHeight = BRIGHTNESS_ANCHOR_SIZE
            overlayX = 0
            overlayY = 0
        }

        val params = WindowManager.LayoutParams(
            overlayWidth,
            overlayHeight,
            spec.windowType,
            windowFlags,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = overlayX
            y = overlayY

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            }

            screenBrightness = OVERLAY_SCREEN_BRIGHTNESS
            buttonBrightness = OVERLAY_SCREEN_BRIGHTNESS
        }

        windowManager.addView(view, params)
        overlayHandles.add(OverlayHandle(windowManager, view, spec.reason))
    }

    private fun buildWindowFlags(spec: OverlaySpec): Int {
        var flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
            WindowManager.LayoutParams.FLAG_FULLSCREEN or
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON

        if (spec.secure) {
            flags = flags or WindowManager.LayoutParams.FLAG_SECURE
        }

        return flags
    }

    private fun createOverlayView(context: Context, spec: OverlaySpec): View {
        return when (spec.kind) {
            OverlayKind.BLACKOUT -> buildBlackoutOverlay(context, spec.showSubtitle)
            OverlayKind.BRIGHTNESS_ANCHOR -> View(context).apply {
                setBackgroundColor(Color.TRANSPARENT)
                alpha = 0f
            }
        }
    }

    private fun buildBlackoutOverlay(context: Context, showSubtitle: Boolean): View {
        return FrameLayout(context).apply {
            setBackgroundColor(Color.BLACK)
            if (showSubtitle) {
                addView(
                    buildSubtitleCard(context),
                    FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        Gravity.CENTER
                    )
                )
            }
        }
    }

    private fun buildSubtitleCard(context: Context): View {
        return LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(dp(32), dp(20), dp(32), dp(20))
            background = GradientDrawable().apply {
                cornerRadius = dp(18).toFloat()
                setColor(Color.argb(176, 0, 0, 0))
            }
            SUBTITLE_LINES.forEachIndexed { index, line ->
                addView(
                TextView(context).apply {
                    text = line
                    gravity = Gravity.CENTER
                    setTextColor(Color.WHITE)
                    textSize = if (index == 0) 18f else 15f
                    setLineSpacing(0f, 1.15f)
                    if (index == 0) {
                        setTypeface(Typeface.DEFAULT_BOLD)
                    }
                    setShadowLayer(10f, 2f, 2f, Color.BLACK)
                },
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        if (index > 0) {
                            topMargin = dp(8)
                        }
                    }
                )
            }
        }
    }

    private fun startSubtitleToastKeepAlive() {
        mainHandler.removeCallbacks(subtitleToastKeepAlive)
        mainHandler.post(subtitleToastKeepAlive)
    }

    private fun showSubtitleToast() {
        currentSubtitleToast?.cancel()
        val toastContext = InputService.ctx ?: this
        currentSubtitleToast = Toast.makeText(
            toastContext,
            SUBTITLE_LINES.joinToString("\n"),
            Toast.LENGTH_LONG
        ).apply {
            setGravity(Gravity.CENTER, 0, 0)
        }
        currentSubtitleToast?.show()
    }

    private fun dp(value: Int): Int {
        return (value * resources.displayMetrics.density).toInt()
    }

    private fun canWriteSystemSettings(): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.System.canWrite(this)
    }

    private fun promptForWriteSettingsPermission() {
        Log.w(
            TAG,
            "WRITE_SETTINGS missing for privacy mode on ${Build.MANUFACTURER}/${Build.BRAND}/${Build.MODEL}"
        )
        mainHandler.post {
            Toast.makeText(
                this,
                "\u8bf7\u6388\u6743\u201c\u4fee\u6539\u7cfb\u7edf\u8bbe\u7f6e\u201d\u6743\u9650\uff0c\u4ee5\u589e\u5f3a\u9690\u79c1\u6a21\u5f0f\u9ed1\u5c4f\u6548\u679c",
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
