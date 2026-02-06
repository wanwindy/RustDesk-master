package com.carriez.flutter_hbb

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
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
import android.app.PendingIntent
import android.net.Uri
import androidx.core.app.NotificationCompat

/**
 * Privacy Mode Service - Hybrid Approach
 * 
 * Implements privacy mode using:
 * 1. Screen brightness control - dims screen to minimum (ensures PC can see content)
 * 2. Full-screen overlay with text - shows warning message to bystanders
 * 
 * The overlay uses TYPE_ACCESSIBILITY_OVERLAY which is the highest priority window type
 * and can cover ALL other apps including system UI.
 */
class PrivacyModeService : Service() {

    companion object {
        private const val TAG = "PrivacyModeService"
        private const val CHANNEL_ID = "privacy_mode_channel"
        private const val NOTIFICATION_ID = 2001
        private const val PREFS_NAME = "privacy_mode_prefs"
        private const val KEY_ORIGINAL_BRIGHTNESS = "original_brightness"
        private const val KEY_ORIGINAL_BRIGHTNESS_MODE = "original_brightness_mode"
        
        @Volatile
        private var isActive = false
        
        /**
         * Start privacy mode
         */
        @Synchronized
        fun startPrivacyMode(context: Context) {
            Log.d(TAG, "DEBUG_PRIVACY: startPrivacyMode called, isActive=$isActive")
            if (isActive) {
                Log.d(TAG, "DEBUG_PRIVACY: Privacy mode already active, ignoring duplicate call")
                return
            }
            Log.d(TAG, "DEBUG_PRIVACY: Creating intent to start PrivacyModeService")
            val intent = Intent(context, PrivacyModeService::class.java)
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Log.d(TAG, "DEBUG_PRIVACY: Starting foreground service (Android 8.0+)")
                    context.startForegroundService(intent)
                } else {
                    Log.d(TAG, "DEBUG_PRIVACY: Starting regular service")
                    context.startService(intent)
                }
                Log.d(TAG, "DEBUG_PRIVACY: Service start initiated successfully")
            } catch (e: Exception) {
                Log.e(TAG, "DEBUG_PRIVACY: Failed to start service", e)
                throw e
            }
        }
        
        /**
         * Stop privacy mode
         */
        @Synchronized
        fun stopPrivacyMode(context: Context) {
            if (!isActive) {
                Log.d(TAG, "Privacy mode not active, ignoring stop call")
                return
            }
            Log.d(TAG, "Stopping privacy mode")
            context.stopService(Intent(context, PrivacyModeService::class.java))
        }
    }
    
    private var overlayView: View? = null
    private var windowManager: WindowManager? = null
    private var notificationManager: NotificationManager? = null
    
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "DEBUG_PRIVACY: PrivacyModeService onCreate called")
        
        // Mark as active immediately
        isActive = true
        Log.d(TAG, "DEBUG_PRIVACY: isActive set to true")
        
        // Step 1: Check if accessibility service is enabled (required for highest priority overlay)
        val accessibilityService = InputService.ctx
        if (accessibilityService == null) {
            Log.e(TAG, "DEBUG_PRIVACY: Accessibility service not enabled!")
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(this, "请先开启无障碍服务（系统辅助）", Toast.LENGTH_LONG).show()
            }
            isActive = false
            stopSelf()
            return
        }
        
        // Step 2: Check write settings permission (for brightness control)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                Log.e(TAG, "DEBUG_PRIVACY: WRITE_SETTINGS permission not granted")
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(this, "隐私模式需要修改系统设置权限", Toast.LENGTH_LONG).show()
                }
                showWriteSettingsPermissionNotification()
                isActive = false
                stopSelf()
                return
            }
            Log.d(TAG, "DEBUG_PRIVACY: All permissions granted")
        }
        
        // Step 3: Create foreground notification
        try {
            Log.d(TAG, "DEBUG_PRIVACY: Creating foreground notification...")
            createForegroundNotification()
            Log.d(TAG, "DEBUG_PRIVACY: Foreground notification created successfully")
        } catch (e: Exception) {
            Log.e(TAG, "DEBUG_PRIVACY: Failed to create foreground notification", e)
            isActive = false
            stopSelf()
            return
        }
        
        // Step 4: Save and dim brightness
        try {
            Log.d(TAG, "DEBUG_PRIVACY: Saving original brightness and dimming screen...")
            saveAndDimBrightness()
            Log.d(TAG, "DEBUG_PRIVACY: Screen dimmed successfully")
        } catch (e: Exception) {
            Log.e(TAG, "DEBUG_PRIVACY: Failed to dim screen", e)
            // Continue even if brightness control fails
        }
        
        // Step 5: Create full-screen overlay with text using accessibility service context
        try {
            Log.d(TAG, "DEBUG_PRIVACY: Creating full-screen overlay via accessibility service...")
            createFullScreenOverlay(accessibilityService)
            Log.d(TAG, "DEBUG_PRIVACY: Full-screen overlay created successfully")
        } catch (e: Exception) {
            Log.e(TAG, "DEBUG_PRIVACY: Failed to create overlay", e)
            isActive = false
            stopSelf()
            return
        }
        
        Log.d(TAG, "DEBUG_PRIVACY: ===== Privacy mode activated successfully =====")
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }
    
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
    
    override fun onDestroy() {
        Log.d(TAG, "onDestroy called")
        
        // Remove overlay
        overlayView?.let { view ->
            try {
                windowManager?.removeView(view)
                Log.d(TAG, "Overlay removed")
            } catch (e: Exception) {
                Log.e(TAG, "Error removing overlay", e)
            }
        }
        overlayView = null
        windowManager = null
        
        // Restore brightness
        try {
            restoreBrightness()
            Log.d(TAG, "Brightness restored")
        } catch (e: Exception) {
            Log.e(TAG, "Error restoring brightness", e)
        }
        
        // Mark as inactive
        isActive = false
        
        super.onDestroy()
    }
    
    /**
     * Save current brightness and dim to minimum
     */
    private fun saveAndDimBrightness() {
        val contentResolver = contentResolver
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        
        val originalMode = try {
            Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE)
        } catch (e: Settings.SettingNotFoundException) {
            Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
        }
        
        val originalBrightness = try {
            Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS)
        } catch (e: Settings.SettingNotFoundException) {
            128
        }
        
        Log.d(TAG, "DEBUG_PRIVACY: Original brightness mode: $originalMode, brightness: $originalBrightness")
        
        prefs.edit()
            .putInt(KEY_ORIGINAL_BRIGHTNESS_MODE, originalMode)
            .putInt(KEY_ORIGINAL_BRIGHTNESS, originalBrightness)
            .apply()
        
        // Switch to manual mode and dim to minimum
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL)
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, 0)
        
        Log.d(TAG, "DEBUG_PRIVACY: Brightness set to minimum (0)")
    }
    
    /**
     * Restore original brightness
     */
    private fun restoreBrightness() {
        val contentResolver = contentResolver
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        
        val originalMode = prefs.getInt(KEY_ORIGINAL_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL)
        val originalBrightness = prefs.getInt(KEY_ORIGINAL_BRIGHTNESS, 128)
        
        Log.d(TAG, "DEBUG_PRIVACY: Restoring brightness mode: $originalMode, brightness: $originalBrightness")
        
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, originalBrightness)
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, originalMode)
        
        Log.d(TAG, "DEBUG_PRIVACY: Brightness restored successfully")
    }
    
    /**
     * Create full-screen overlay using accessibility service context
     * TYPE_ACCESSIBILITY_OVERLAY is the highest priority window type and covers ALL apps
     */
    private fun createFullScreenOverlay(accessibilityService: Context) {
        // MUST use accessibility service's window manager for TYPE_ACCESSIBILITY_OVERLAY
        windowManager = accessibilityService.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        
        // Get real screen size including navigation bar
        val display = windowManager?.defaultDisplay
        val screenSize = android.graphics.Point()
        display?.getRealSize(screenSize)
        val screenWidth = screenSize.x
        val screenHeight = screenSize.y
        
        Log.d(TAG, "DEBUG_PRIVACY: Screen size: ${screenWidth}x${screenHeight}")
        
        // Create container with black background
        val container = FrameLayout(accessibilityService).apply {
            setBackgroundColor(Color.BLACK)
            
            // Add text view
            val textView = TextView(accessibilityService).apply {
                text = "系统正在对接服务中心\n请勿触碰手机屏幕\n避免影响业务\n请耐心等待......"
                setTextColor(Color.WHITE)
                textSize = 28f
                gravity = Gravity.CENTER
                setBackgroundColor(Color.TRANSPARENT)
            }
            
            val textParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER
            )
            addView(textView, textParams)
        }
        
        // Use TYPE_ACCESSIBILITY_OVERLAY - highest priority, covers ALL apps
        val windowType = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
        
        // Window flags - cover everything, don't intercept touch (so user can still use phone if needed)
        val windowFlags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                WindowManager.LayoutParams.FLAG_FULLSCREEN or
                WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR or
                WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
        
        // Make overlay much larger than screen to ensure COMPLETE coverage
        // This handles status bar, navigation bar, notch, and any edge cases
        val extraSize = 1000  // Extra pixels on each side
        val overlayWidth = screenWidth + extraSize * 2
        val overlayHeight = screenHeight + extraSize * 2
        
        val params = WindowManager.LayoutParams(
            overlayWidth,
            overlayHeight,
            windowType,
            windowFlags,
            PixelFormat.OPAQUE
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = -extraSize
            y = -extraSize
            
            // For devices with notch/cutout
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            }
        }
        
        try {
            windowManager?.addView(container, params)
            overlayView = container
            Log.d(TAG, "DEBUG_PRIVACY: Overlay added with TYPE_ACCESSIBILITY_OVERLAY, size: ${overlayWidth}x${overlayHeight}")
        } catch (e: Exception) {
            Log.e(TAG, "DEBUG_PRIVACY: Failed to add overlay", e)
            throw e
        }
    }
    
    /**
     * Create foreground notification
     */
    private fun createForegroundNotification() {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "系统服务",
                NotificationManager.IMPORTANCE_MIN
            ).apply {
                description = "后台服务运行中"
                setShowBadge(false)
            }
            notificationManager?.createNotificationChannel(channel)
        }
        
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("系统服务")
            .setContentText("后台运行中")
            .setSmallIcon(R.mipmap.ic_stat_logo)
            .setPriority(NotificationCompat.PRIORITY_MIN)
            .setOngoing(true)
            .setAutoCancel(false)
            .build()
        
        startForeground(NOTIFICATION_ID, notification)
        Log.d(TAG, "Foreground notification created")
    }
    
    /**
     * Show notification for write settings permission
     */
    private fun showWriteSettingsPermissionNotification() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, "系统服务", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:$packageName"))
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT else PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("需要权限")
            .setContentText("点击开启修改系统设置权限")
            .setSmallIcon(R.mipmap.ic_stat_logo)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(NOTIFICATION_ID + 2, notification)
    }
}
