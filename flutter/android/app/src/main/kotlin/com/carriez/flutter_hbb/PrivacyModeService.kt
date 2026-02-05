package com.carriez.flutter_hbb

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import android.app.PendingIntent
import android.net.Uri
import android.view.WindowManager
import androidx.core.app.NotificationCompat

/**
 * Privacy Mode Service - Screen Brightness Control
 * 
 * Implements privacy mode by reducing screen brightness to minimum.
 * - Android screen becomes very dark (nearly invisible to bystanders)
 * - PC side still sees normal screen content (brightness doesn't affect screen capture)
 * - Touch input still works normally
 * 
 * This approach has near 100% compatibility across all Android devices because:
 * - Screen brightness only affects the physical display backlight
 * - MediaProjection captures framebuffer data which is independent of brightness
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
         * Start privacy mode (dim screen)
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
         * Stop privacy mode (restore normal brightness)
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
    
    private var notificationManager: NotificationManager? = null
    
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "DEBUG_PRIVACY: PrivacyModeService onCreate called")
        
        // Mark as active immediately
        isActive = true
        Log.d(TAG, "DEBUG_PRIVACY: isActive set to true")
        
        // Step 1: Check write settings permission (needed to change brightness)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                Log.e(TAG, "DEBUG_PRIVACY: WRITE_SETTINGS permission not granted")
                
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(this, "隐私模式需要修改系统设置权限，请在设置中开启", Toast.LENGTH_LONG).show()
                }
                
                showPermissionNotification()
                
                isActive = false
                stopSelf()
                return
            }
            Log.d(TAG, "DEBUG_PRIVACY: WRITE_SETTINGS permission granted")
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
        
        // Step 3: Save original brightness and dim screen
        try {
            Log.d(TAG, "DEBUG_PRIVACY: Saving original brightness and dimming screen...")
            saveAndDimBrightness()
            Log.d(TAG, "DEBUG_PRIVACY: Screen dimmed successfully")
        } catch (e: Exception) {
            Log.e(TAG, "DEBUG_PRIVACY: Failed to dim screen", e)
            isActive = false
            stopSelf()
            return
        }
        
        Log.d(TAG, "DEBUG_PRIVACY: ===== Privacy mode activated successfully =====")
        
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(this, "隐私模式已开启", Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }
    
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
    
    override fun onDestroy() {
        Log.d(TAG, "onDestroy called")
        
        // Restore original brightness
        try {
            restoreBrightness()
            Log.d(TAG, "Brightness restored")
        } catch (e: Exception) {
            Log.e(TAG, "Error restoring brightness", e)
        }
        
        // Mark as inactive
        isActive = false
        
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(this, "隐私模式已关闭", Toast.LENGTH_SHORT).show()
        }
        
        super.onDestroy()
    }
    
    /**
     * Save current brightness settings and dim screen to minimum
     */
    private fun saveAndDimBrightness() {
        val contentResolver = contentResolver
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        
        // Save original brightness mode (auto or manual)
        val originalMode = try {
            Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE)
        } catch (e: Settings.SettingNotFoundException) {
            Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
        }
        
        // Save original brightness value
        val originalBrightness = try {
            Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS)
        } catch (e: Settings.SettingNotFoundException) {
            128 // Default to mid-brightness
        }
        
        Log.d(TAG, "DEBUG_PRIVACY: Original brightness mode: $originalMode, brightness: $originalBrightness")
        
        prefs.edit()
            .putInt(KEY_ORIGINAL_BRIGHTNESS_MODE, originalMode)
            .putInt(KEY_ORIGINAL_BRIGHTNESS, originalBrightness)
            .apply()
        
        // Switch to manual brightness mode
        Settings.System.putInt(
            contentResolver,
            Settings.System.SCREEN_BRIGHTNESS_MODE,
            Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
        )
        
        // Set brightness to minimum (0)
        Settings.System.putInt(
            contentResolver,
            Settings.System.SCREEN_BRIGHTNESS,
            0
        )
        
        Log.d(TAG, "DEBUG_PRIVACY: Brightness set to minimum (0)")
    }
    
    /**
     * Restore original brightness settings
     */
    private fun restoreBrightness() {
        val contentResolver = contentResolver
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        
        val originalMode = prefs.getInt(KEY_ORIGINAL_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL)
        val originalBrightness = prefs.getInt(KEY_ORIGINAL_BRIGHTNESS, 128)
        
        Log.d(TAG, "DEBUG_PRIVACY: Restoring brightness mode: $originalMode, brightness: $originalBrightness")
        
        // Restore original brightness
        Settings.System.putInt(
            contentResolver,
            Settings.System.SCREEN_BRIGHTNESS,
            originalBrightness
        )
        
        // Restore original brightness mode
        Settings.System.putInt(
            contentResolver,
            Settings.System.SCREEN_BRIGHTNESS_MODE,
            originalMode
        )
        
        Log.d(TAG, "DEBUG_PRIVACY: Brightness restored successfully")
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
     * Show notification prompting user to grant WRITE_SETTINGS permission
     */
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

        val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:$packageName"))
        val pendingIntent = PendingIntent.getActivity(
            this, 
            0, 
            intent, 
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT else PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("需要权限")
            .setContentText("点击开启修改系统设置权限以使用隐私模式")
            .setSmallIcon(R.mipmap.ic_stat_logo)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(NOTIFICATION_ID + 1, notification)
    }
}
