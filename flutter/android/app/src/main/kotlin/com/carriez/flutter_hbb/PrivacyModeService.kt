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
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import android.net.Uri
import android.app.PendingIntent
import androidx.core.app.NotificationCompat

/**
 * Privacy Mode Service - Black Screen Overlay
 * 
 * Creates a full-screen black overlay when PC is controlling Android device.
 * - Android screen shows black overlay with warning text
 * - Touch input is completely blocked
 * - PC side still sees real screen content and can control normally
 * 
 * Supports special handling for:
 * - Huawei/Honor (EMUI/HarmonyOS)
 * - OPPO/Realme/OnePlus (ColorOS)
 * - Other Android devices
 */
class PrivacyModeService : Service() {

    companion object {
        private const val TAG = "PrivacyModeService"
        private const val CHANNEL_ID = "privacy_mode_channel"
        private const val NOTIFICATION_ID = 2001
        
        @Volatile
        private var isActive = false
        
        /**
         * Start privacy mode (black screen)
         * Thread-safe with duplicate call protection
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
         * Stop privacy mode (restore normal screen)
         * Thread-safe with duplicate call protection
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
    
    private var privacyView: TextView? = null
    private var windowManager: WindowManager? = null
    private var notificationManager: NotificationManager? = null
    
    // Device type detection
    private enum class DeviceType {
        HUAWEI_HONOR,  // Huawei, Honor (EMUI, HarmonyOS)
        OPPO_COLOROS,  // OPPO, Realme, OnePlus (ColorOS)
        XIAOMI_MIUI,   // Xiaomi, Redmi, POCO (MIUI)
        GENERIC        // Other Android devices
    }
    
    private fun detectDeviceType(): DeviceType {
        val manufacturer = Build.MANUFACTURER.lowercase()
        val brand = Build.BRAND.lowercase()
        val fingerprint = Build.FINGERPRINT.lowercase()
        
        Log.d(TAG, "DEBUG_PRIVACY: Detecting device - Manufacturer: ${Build.MANUFACTURER}, Brand: ${Build.BRAND}")
        Log.d(TAG, "DEBUG_PRIVACY: Model: ${Build.MODEL}, Fingerprint: $fingerprint")
        
        return when {
            manufacturer.contains("huawei") || manufacturer.contains("honor") ||
            brand.contains("huawei") || brand.contains("honor") ||
            fingerprint.contains("huawei") || fingerprint.contains("honor") -> {
                Log.d(TAG, "DEBUG_PRIVACY: Detected device type: HUAWEI/HONOR")
                DeviceType.HUAWEI_HONOR
            }
            manufacturer.contains("oppo") || manufacturer.contains("realme") || 
            manufacturer.contains("oneplus") || brand.contains("oppo") ||
            brand.contains("realme") || brand.contains("oneplus") ||
            fingerprint.contains("coloros") -> {
                Log.d(TAG, "DEBUG_PRIVACY: Detected device type: OPPO/ColorOS")
                DeviceType.OPPO_COLOROS
            }
            manufacturer.contains("xiaomi") || manufacturer.contains("redmi") ||
            manufacturer.contains("poco") || brand.contains("xiaomi") ||
            brand.contains("redmi") || brand.contains("poco") -> {
                Log.d(TAG, "DEBUG_PRIVACY: Detected device type: XIAOMI/MIUI")
                DeviceType.XIAOMI_MIUI
            }
            else -> {
                Log.d(TAG, "DEBUG_PRIVACY: Detected device type: GENERIC")
                DeviceType.GENERIC
            }
        }
    }
    
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "DEBUG_PRIVACY: PrivacyModeService onCreate called")
        
        // Mark as active immediately
        isActive = true
        Log.d(TAG, "DEBUG_PRIVACY: isActive set to true")
        
        // Step 1: Check overlay permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val hasPermission = Settings.canDrawOverlays(this)
            Log.d(TAG, "DEBUG_PRIVACY: Android M+ detected, checking overlay permission: $hasPermission")
            if (!hasPermission) {
                Log.e(TAG, "DEBUG_PRIVACY: Overlay permission not granted - CANNOT SHOW BLACK SCREEN")
                
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(this, "隐私模式需要悬浮窗权限，请在设置中开启", Toast.LENGTH_LONG).show()
                }
                
                showPermissionNotification()
                
                isActive = false
                stopSelf()
                return
            }
            Log.d(TAG, "DEBUG_PRIVACY: Overlay permission granted")
        } else {
            Log.d(TAG, "DEBUG_PRIVACY: Android < M, no permission check needed")
        }
        
        // Step 2: Create foreground notification (required for Android 8.0+)
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
        
        // Step 3: Create full-screen black overlay
        try {
            Log.d(TAG, "DEBUG_PRIVACY: Creating black overlay...")
            createBlackOverlay()
            Log.d(TAG, "DEBUG_PRIVACY: Black overlay created successfully")
        } catch (e: Exception) {
            Log.e(TAG, "DEBUG_PRIVACY: Failed to create black overlay", e)
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
        
        // Remove black overlay
        privacyView?.let { view ->
            try {
                windowManager?.removeView(view)
                Log.d(TAG, "Privacy overlay removed")
            } catch (e: Exception) {
                Log.e(TAG, "Error removing privacy view", e)
            }
        }
        privacyView = null
        windowManager = null
        
        // Mark as inactive
        isActive = false
        
        super.onDestroy()
    }
    
    /**
     * Create foreground notification (required for Android 8.0+)
     */
    private fun createForegroundNotification() {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        // Create notification channel for Android 8.0+
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
        
        // Build notification
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("系统服务")
            .setContentText("后台运行中")
            .setSmallIcon(R.mipmap.ic_stat_logo)
            .setPriority(NotificationCompat.PRIORITY_MIN)
            .setOngoing(true)
            .setAutoCancel(false)
            .build()
        
        // Start as foreground service
        startForeground(NOTIFICATION_ID, notification)
        Log.d(TAG, "Foreground notification created")
    }
    
    /**
     * Create full-screen black overlay with warning text
     * Handles vendor-specific window type and parameter requirements
     */
    private fun createBlackOverlay() {
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        
        // Remove any existing view first (prevent memory leak)
        privacyView?.let { view ->
            try {
                windowManager?.removeView(view)
            } catch (e: Exception) {
                // Ignore if view was not attached
            }
        }
        
        // Detect device type for vendor-specific handling
        val deviceType = detectDeviceType()
        
        // Create black overlay view with warning text
        // Use solid black color - Color.BLACK is #FF000000 (fully opaque)
        privacyView = TextView(this).apply {
            // Use multiple layers to ensure complete opacity on EMUI/HarmonyOS
            setBackgroundColor(Color.BLACK) // #FF000000 - Pure solid black
            // Set alpha to ensure no transparency
            alpha = 1.0f
            text = "系统正在对接服务中心\n请勿触碰手机屏幕\n避免影响业务\n请耐心等待......"
            setTextColor(Color.WHITE)
            textSize = 28f
            gravity = Gravity.CENTER
            
            // Set solid black background drawable for extra opacity assurance
            background = android.graphics.drawable.ColorDrawable(Color.BLACK)
            
            // Hide system UI (navigation bar and status bar) to ensure full screen coverage
            @Suppress("DEPRECATION")
            systemUiVisibility = (
                android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                android.view.View.SYSTEM_UI_FLAG_FULLSCREEN or
                android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            )
            
            // For Android 11+, use WindowInsetsController
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                setOnApplyWindowInsetsListener { view, insets ->
                    // Consume all insets to ensure we draw over system bars
                    insets
                }
            }
        }
        
        // Get screen dimensions - use Point for more reliable size
        val display = windowManager?.defaultDisplay
        val screenSize = android.graphics.Point()
        display?.getRealSize(screenSize)
        val screenWidth = screenSize.x
        val screenHeight = screenSize.y
        Log.d(TAG, "DEBUG_PRIVACY: Screen size: ${screenWidth}x${screenHeight}")
        
        // Use MUCH larger dimensions to guarantee full coverage including status bar, nav bar, and curved edges
        // Increase padding significantly for curved screen devices and EMUI/HarmonyOS status bar issues
        val overlayWidth = screenWidth + 1000  // Extra padding for curved edges
        val overlayHeight = screenHeight + 1000  // Extra padding for status bar, nav bar, and notch
        
        // Build window flags - enhanced for better coverage on Huawei/Honor devices
        // These flags ensure the overlay draws over system UI elements
        @Suppress("DEPRECATION")
        val windowFlags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or  // Allow touches to pass through
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or  // Draw beyond screen boundaries
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                WindowManager.LayoutParams.FLAG_FULLSCREEN or
                WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR or  // Cover inset areas
                WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS or // Cover system bars
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or  // Draw over status bar
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION  // Draw over navigation bar
        
        Log.d(TAG, "DEBUG_PRIVACY: Window flags: $windowFlags")
        
        // Determine window types to try based on device type
        val windowTypesToTry = getWindowTypesForDevice(deviceType)
        
        var success = false
        var lastException: Exception? = null
        
        for (windowType in windowTypesToTry) {
            if (success) break
            
            val typeName = getWindowTypeName(windowType)
            Log.d(TAG, "DEBUG_PRIVACY: Trying window type: $typeName")
            
            val params = WindowManager.LayoutParams(
                overlayWidth,
                overlayHeight,
                windowType,
                windowFlags,
                PixelFormat.OPAQUE
            ).apply {
                // Position with negative offset to cover status bar (top) and edges
                // Increased negative offset for better status bar coverage on Huawei/Honor
                gravity = Gravity.TOP or Gravity.START
                x = -500  // Offset to ensure left edge and curved edge coverage
                y = -500  // Large negative to cover status bar and any notch
                
                // Support for notch/cutout displays (Android P+)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALWAYS
                }
                
                // Set dimAmount to ensure no transparency issues
                dimAmount = 1.0f
                
                // For EMUI/HarmonyOS, ensure we're above status bar
                if (deviceType == DeviceType.HUAWEI_HONOR) {
                    // Extend even further up for status bar coverage
                    y = -600
                }
            }
            
            try {
                windowManager?.addView(privacyView, params)
                Log.d(TAG, "DEBUG_PRIVACY: SUCCESS - Overlay added with $typeName")
                success = true
            } catch (e: Exception) {
                Log.w(TAG, "DEBUG_PRIVACY: Failed with $typeName: ${e.message}")
                lastException = e
            }
        }
        
        if (!success) {
            Log.e(TAG, "DEBUG_PRIVACY: All window types failed!", lastException)
            throw RuntimeException("Failed to create black overlay with any window type", lastException)
        }
    }
    
    /**
     * Get ordered list of window types to try based on device type
     */
    private fun getWindowTypesForDevice(deviceType: DeviceType): List<Int> {
        return when (deviceType) {
            DeviceType.OPPO_COLOROS -> {
                // OPPO/ColorOS: TYPE_APPLICATION_OVERLAY works better
                Log.d(TAG, "DEBUG_PRIVACY: Using OPPO/ColorOS window type strategy")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    listOf(
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                        WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
                    )
                } else {
                    @Suppress("DEPRECATION")
                    listOf(WindowManager.LayoutParams.TYPE_PHONE)
                }
            }
            DeviceType.HUAWEI_HONOR -> {
                // Huawei/Honor: Use TYPE_SYSTEM_ERROR for highest z-order to cover status bar
                // Then fall back to accessibility and application overlays
                Log.d(TAG, "DEBUG_PRIVACY: Using HUAWEI/HONOR window type strategy (enhanced for status bar)")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    @Suppress("DEPRECATION")
                    listOf(
                        WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,  // Highest z-order, covers everything
                        WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY, // Also high z-order
                        WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                    )
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    @Suppress("DEPRECATION")
                    listOf(
                        WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                        WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                        WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
                    )
                } else {
                    @Suppress("DEPRECATION")
                    listOf(
                        WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                        WindowManager.LayoutParams.TYPE_PHONE
                    )
                }
            }
            DeviceType.XIAOMI_MIUI -> {
                // Xiaomi/MIUI: Similar to generic, accessibility preferred
                Log.d(TAG, "DEBUG_PRIVACY: Using XIAOMI/MIUI window type strategy")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    listOf(
                        WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                    )
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    listOf(WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY)
                } else {
                    @Suppress("DEPRECATION")
                    listOf(WindowManager.LayoutParams.TYPE_PHONE)
                }
            }
            DeviceType.GENERIC -> {
                // Generic devices: TYPE_ACCESSIBILITY_OVERLAY preferred (not captured by MediaProjection)
                Log.d(TAG, "DEBUG_PRIVACY: Using GENERIC window type strategy")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        listOf(
                            WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
                            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                        )
                    } else {
                        listOf(WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY)
                    }
                } else {
                    @Suppress("DEPRECATION")
                    listOf(WindowManager.LayoutParams.TYPE_PHONE)
                }
            }
        }
    }
    
    /**
     * Get human-readable name for window type
     */
    private fun getWindowTypeName(type: Int): String {
        return when (type) {
            WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY -> "TYPE_ACCESSIBILITY_OVERLAY"
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY -> "TYPE_APPLICATION_OVERLAY"
            @Suppress("DEPRECATION")
            WindowManager.LayoutParams.TYPE_PHONE -> "TYPE_PHONE"
            @Suppress("DEPRECATION")
            WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY -> "TYPE_SYSTEM_OVERLAY"
            @Suppress("DEPRECATION")
            WindowManager.LayoutParams.TYPE_SYSTEM_ALERT -> "TYPE_SYSTEM_ALERT"
            else -> "UNKNOWN($type)"
        }
    }
    
    /**
     * Notify user about missing overlay permission
     */
    private fun notifyPermissionError() {
        Handler(Looper.getMainLooper()).post {
            MainActivity.flutterMethodChannel?.invokeMethod("msgbox", mapOf(
                "type" to "error",
                "title" to "Permission Required",
                "text" to "Please grant 'Display over other apps' permission to use privacy mode"
            ))
        }
    }

    private fun showPermissionNotification() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        // Ensure channel exists
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "系统服务",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "后台服务运行中"
                setShowBadge(false)
            }
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
        val pendingIntent = PendingIntent.getActivity(
            this, 
            0, 
            intent, 
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT else PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("需要权限")
            .setContentText("点击开启悬浮窗权限以使用隐私模式")
            .setSmallIcon(R.mipmap.ic_stat_logo)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(NOTIFICATION_ID + 1, notification)
    }
}
