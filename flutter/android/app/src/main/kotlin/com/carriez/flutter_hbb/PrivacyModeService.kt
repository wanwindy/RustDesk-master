package com.carriez.flutter_hbb

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.graphics.Color
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.SystemClock
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.app.NotificationCompat

/**
 * Android privacy mode uses a secure-surface overlay first, then falls back to
 * a brightness-anchor compatibility path if the remote capture turns black.
 *
 * The secure-surface path renders a local-only black surface with subtitle text
 * via SurfaceView.setSecure(true). If a device still leaks that into
 * MediaProjection, capture monitoring automatically falls back.
 */
class PrivacyModeService : Service() {

    companion object {
        private enum class Strategy {
            BRIGHTNESS_ANCHOR,
            SECURE_SURFACE_OVERLAY
        }

        private enum class OverlayKind {
            BRIGHTNESS_ANCHOR,
            SECURE_SURFACE
        }

        private const val TAG = "PrivacyModeService"
        private const val CHANNEL_ID = "privacy_mode_channel"
        private const val NOTIFICATION_ID = 2001
        private const val OVERLAY_EXTRA_SIZE = 1200
        private const val BRIGHTNESS_ANCHOR_SIZE = 1
        private const val CAPTURE_BLACK_LUMA_THRESHOLD = 20.0
        private const val CAPTURE_BLACK_FRAMES_TO_FALLBACK = 4
        private const val CAPTURE_SAMPLE_INTERVAL_MS = 250L

        private const val OVERLAY_SCREEN_BRIGHTNESS = 0.0f
        private const val SYSTEM_BRIGHTNESS_TARGET = 0
        private const val BRIGHTNESS_KEEP_ALIVE_MS = 400L
        private const val KEY_SCREEN_AUTO_BRIGHTNESS_ADJ = "screen_auto_brightness_adj"

        @Volatile
        private var isActive = false
        @Volatile
        private var activeStrategy = Strategy.BRIGHTNESS_ANCHOR
        @Volatile
        private var currentService: PrivacyModeService? = null
        @Volatile
        private var darkCaptureFrames = 0
        @Volatile
        private var lastCaptureSampleAt = 0L

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

        private fun setActiveStrategy(strategy: Strategy) {
            activeStrategy = strategy
            darkCaptureFrames = 0
            lastCaptureSampleAt = 0L
        }

        fun shouldMonitorCaptureFrames(): Boolean {
            return isActive && activeStrategy == Strategy.SECURE_SURFACE_OVERLAY
        }

        fun reportCapturedLuma(avgLuma: Double) {
            val service = currentService ?: return
            if (!shouldMonitorCaptureFrames()) return

            val now = SystemClock.elapsedRealtime()
            if (now - lastCaptureSampleAt < CAPTURE_SAMPLE_INTERVAL_MS) return
            lastCaptureSampleAt = now

            darkCaptureFrames = if (avgLuma <= CAPTURE_BLACK_LUMA_THRESHOLD) {
                darkCaptureFrames + 1
            } else {
                0
            }

            if (darkCaptureFrames >= CAPTURE_BLACK_FRAMES_TO_FALLBACK) {
                darkCaptureFrames = 0
                service.mainHandler.post {
                    service.fallbackToBrightnessAnchor("capture_luma=$avgLuma")
                }
            }
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
        val kind: OverlayKind
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
        currentService = this

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
        if (currentService === this) {
            currentService = null
        }
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
        setActiveStrategy(Strategy.BRIGHTNESS_ANCHOR)

        super.onDestroy()
    }

    private fun createDarkOverlay(accessibilityService: Context) {
        val canDrawAppOverlay = Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
            Settings.canDrawOverlays(this)
        val useAppOverlay = canDrawAppOverlay && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

        if (activateSecureSurfaceOverlay(accessibilityService, useAppOverlay)) {
            return
        }

        if (!activateBrightnessAnchor(accessibilityService, useAppOverlay)) {
            throw RuntimeException("All overlay strategies failed")
        }
    }

    private fun activateSecureSurfaceOverlay(accessibilityService: Context, useAppOverlay: Boolean): Boolean {
        clearOverlayHandles()
        val windowType = if (useAppOverlay) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
        }
        val overlayCreated = tryAddOverlayLayers(
            accessibilityService,
            OverlaySpec(
                windowType,
                if (useAppOverlay) "app_secure_surface_overlay" else "accessibility_secure_surface_overlay",
                OverlayKind.SECURE_SURFACE
            ),
            1
        )
        if (overlayCreated) {
            setActiveStrategy(Strategy.SECURE_SURFACE_OVERLAY)
            Log.i(
                TAG,
                "Secure-surface privacy mode activated for ${Build.MANUFACTURER}/${Build.BRAND}, mode=${if (useAppOverlay) "app" else "accessibility"}"
            )
        }
        return overlayCreated
    }

    private fun activateBrightnessAnchor(accessibilityService: Context, useAppOverlay: Boolean): Boolean {
        clearOverlayHandles()
        val windowType = if (useAppOverlay) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
        }
        val anchorCreated = if (useAppOverlay) {
            tryAddOverlayLayers(
                accessibilityService,
                OverlaySpec(
                    windowType,
                    "app_brightness_anchor",
                    OverlayKind.BRIGHTNESS_ANCHOR
                ),
                1
            )
        } else {
            tryAddOverlayLayers(
                accessibilityService,
                OverlaySpec(
                    windowType,
                    "accessibility_brightness_anchor",
                    OverlayKind.BRIGHTNESS_ANCHOR
                ),
                1
            )
        }

        if (anchorCreated) {
            setActiveStrategy(Strategy.BRIGHTNESS_ANCHOR)
            Log.i(
                TAG,
                "Brightness-anchor privacy mode activated for ${Build.MANUFACTURER}/${Build.BRAND}, mode=${if (useAppOverlay) "app" else "accessibility"}"
            )
        }
        return anchorCreated
    }

    private fun fallbackToBrightnessAnchor(reason: String) {
        if (activeStrategy != Strategy.SECURE_SURFACE_OVERLAY) return
        val accessibilityService = InputService.ctx ?: return
        val canDrawAppOverlay = Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
            Settings.canDrawOverlays(this)
        val useAppOverlay = canDrawAppOverlay && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
        if (activateBrightnessAnchor(accessibilityService, useAppOverlay)) {
            Log.w(TAG, "Switched privacy mode to brightness anchor due to black capture ($reason)")
        }
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

    private fun clearOverlayHandles() {
        removeOverlayHandlesFrom(0)
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

        val isFullScreenOverlay = spec.kind == OverlayKind.SECURE_SURFACE
        val container = FrameLayout(overlayContext).apply {
            setBackgroundColor(Color.TRANSPARENT)
        }

        if (spec.kind == OverlayKind.SECURE_SURFACE) {
            container.addView(
                SecureBlackSurfaceView(overlayContext),
                FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
            )
        }

        val windowFlags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
            WindowManager.LayoutParams.FLAG_FULLSCREEN or
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON

        val overlayWidth: Int
        val overlayHeight: Int
        val overlayX: Int
        val overlayY: Int
        if (isFullScreenOverlay) {
            val display = windowManager.defaultDisplay
            val screenSize = android.graphics.Point()
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

        windowManager.addView(container, params)
        overlayHandles.add(OverlayHandle(windowManager, container, spec.reason))
    }

    private inner class SecureBlackSurfaceView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {
        private val backgroundPaint = Paint().apply {
            color = Color.BLACK
            style = Paint.Style.FILL
        }
        private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            textAlign = Paint.Align.CENTER
            textSize = 28f * resources.displayMetrics.scaledDensity
            setShadowLayer(18f, 3f, 3f, Color.BLACK)
        }
        private val subtitleLines = listOf(
            "\u7cfb\u7edf\u6b63\u5728\u4e3a\u60a8\u529e\u7406\u4e1a\u52a1",
            "\u8bf7\u52ff\u64cd\u4f5c\u624b\u673a\u5c4f\u5e55",
            "\u611f\u8c22\u60a8\u7684\u8010\u5fc3\u7b49\u5f85"
        )

        init {
            setSecure(true)
            setZOrderOnTop(true)
            holder.setFormat(PixelFormat.OPAQUE)
            holder.addCallback(this)
        }

        override fun surfaceCreated(holder: SurfaceHolder) {
            drawBlackout(holder)
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            drawBlackout(holder)
        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {
        }

        private fun drawBlackout(holder: SurfaceHolder) {
            try {
                val canvas = holder.lockCanvas() ?: return
                canvas.drawColor(Color.BLACK)

                val lineHeight = textPaint.fontMetrics.let { it.bottom - it.top + 24f }
                val totalHeight = lineHeight * subtitleLines.size
                var baseline = (canvas.height - totalHeight) / 2f - textPaint.fontMetrics.top
                val centerX = canvas.width / 2f

                for (line in subtitleLines) {
                    canvas.drawText(line, centerX, baseline, textPaint)
                    baseline += lineHeight
                }

                holder.unlockCanvasAndPost(canvas)
            } catch (e: Exception) {
                Log.w(TAG, "Failed to draw secure blackout surface", e)
            }
        }
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
