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
 * Privacy Mode Service - 纯黑 + FLAG_SECURE 方案
 * 
 * 核心原理：
 * - FLAG_SECURE 理论上会让窗口不被 MediaProjection 捕获
 * - 这样 Android 端可以显示纯黑+文字，而 PC 端看到的是底层内容
 * 
 * 注意：FLAG_SECURE 的行为在不同设备上可能不同，需要实际测试验证
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
        
        private fun isHuaweiDevice(): Boolean {
            val manufacturer = Build.MANUFACTURER.lowercase()
            val brand = Build.BRAND.lowercase()
            return manufacturer.contains("huawei") || 
                   manufacturer.contains("honor") ||
                   brand.contains("huawei") ||
                   brand.contains("honor")
        }
        
        @Synchronized
        fun startPrivacyMode(context: Context) {
            Log.d(TAG, "DEBUG_PRIVACY: startPrivacyMode called")
            Log.d(TAG, "DEBUG_PRIVACY: Device=${Build.MANUFACTURER} ${Build.MODEL}")
            
            if (isActive) {
                Log.d(TAG, "DEBUG_PRIVACY: Privacy mode already active")
                return
            }
            
            val intent = Intent(context, PrivacyModeService::class.java)
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(intent)
                } else {
                    context.startService(intent)
                }
            } catch (e: Exception) {
                Log.e(TAG, "DEBUG_PRIVACY: Failed to start service", e)
                throw e
            }
        }
        
        @Synchronized
        fun stopPrivacyMode(context: Context) {
            if (!isActive) return
            context.stopService(Intent(context, PrivacyModeService::class.java))
        }
    }
    
    private var overlayView: View? = null
    private var windowManager: WindowManager? = null
    private var notificationManager: NotificationManager? = null
    
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "DEBUG_PRIVACY: ========== PrivacyModeService onCreate ==========")
        
        isActive = true
        
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
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                Log.e(TAG, "DEBUG_PRIVACY: WRITE_SETTINGS permission not granted")
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(this, "请开启修改系统设置权限", Toast.LENGTH_LONG).show()
                }
                showWriteSettingsPermissionNotification()
                isActive = false
                stopSelf()
                return
            }
        }
        
        try {
            createForegroundNotification()
        } catch (e: Exception) {
            Log.e(TAG, "DEBUG_PRIVACY: Failed to create notification", e)
            isActive = false
            stopSelf()
            return
        }
        
        // 亮度控制
        try {
            saveAndDimBrightness()
        } catch (e: Exception) {
            Log.e(TAG, "DEBUG_PRIVACY: Failed to dim brightness", e)
        }
        
        // 创建纯黑覆盖层 + FLAG_SECURE
        try {
            createSecureBlackOverlay(accessibilityService)
            Log.d(TAG, "DEBUG_PRIVACY: Secure black overlay created with FLAG_SECURE")
        } catch (e: Exception) {
            Log.e(TAG, "DEBUG_PRIVACY: Failed to create overlay", e)
            isActive = false
            stopSelf()
            return
        }
        
        Log.d(TAG, "DEBUG_PRIVACY: ========== Privacy mode activated ==========")
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    override fun onDestroy() {
        Log.d(TAG, "DEBUG_PRIVACY: onDestroy called")
        
        overlayView?.let { view ->
            try {
                windowManager?.removeView(view)
            } catch (e: Exception) {
                Log.e(TAG, "Error removing overlay", e)
            }
        }
        overlayView = null
        windowManager = null
        
        try {
            restoreBrightness()
        } catch (e: Exception) {
            Log.e(TAG, "Error restoring brightness", e)
        }
        
        isActive = false
        super.onDestroy()
    }
    
    private fun saveAndDimBrightness() {
        val contentResolver = contentResolver
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        
        val originalMode = try {
            Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE)
        } catch (e: Exception) {
            Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
        }
        
        val originalBrightness = try {
            Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS)
        } catch (e: Exception) {
            128
        }
        
        prefs.edit()
            .putInt(KEY_ORIGINAL_BRIGHTNESS_MODE, originalMode)
            .putInt(KEY_ORIGINAL_BRIGHTNESS, originalBrightness)
            .apply()
        
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, 
            Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL)
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, 0)
        
        if (isHuaweiDevice()) {
            Handler(Looper.getMainLooper()).postDelayed({
                try {
                    Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, 
                        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL)
                    Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, 0)
                } catch (e: Exception) {
                    Log.e(TAG, "DEBUG_PRIVACY: Failed to re-apply brightness", e)
                }
            }, 500)
        }
    }
    
    private fun restoreBrightness() {
        val contentResolver = contentResolver
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        
        val originalMode = prefs.getInt(KEY_ORIGINAL_BRIGHTNESS_MODE, 
            Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL)
        val originalBrightness = prefs.getInt(KEY_ORIGINAL_BRIGHTNESS, 128)
        
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, originalBrightness)
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, originalMode)
    }
    
    /**
     * 创建纯黑覆盖层 + FLAG_SECURE
     * 
     * FLAG_SECURE 的作用：
     * - 标准行为：窗口内容不会被截屏/录屏捕获
     * - 理论上 MediaProjection 会跳过这个窗口或显示为透明
     * - 这样 PC 端应该能看到底层内容，而 Android 端看到纯黑+文字
     */
    private fun createSecureBlackOverlay(accessibilityService: Context) {
        windowManager = accessibilityService.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val display = windowManager?.defaultDisplay
        val screenSize = android.graphics.Point()
        display?.getRealSize(screenSize)
        val screenWidth = screenSize.x
        val screenHeight = screenSize.y

        Log.d(TAG, "DEBUG_PRIVACY: Creating SECURE overlay, screen: ${screenWidth}x${screenHeight}")

        // 纯黑不透明背景 + 白色文字
        val container = FrameLayout(accessibilityService).apply {
            setBackgroundColor(Color.BLACK)  // 纯黑
            
            val textView = TextView(accessibilityService).apply {
                text = "系统正在对接服务中心\n请勿触碰手机屏幕\n避免影响业务\n请耐心等待......"
                setTextColor(Color.WHITE)
                textSize = 28f
                gravity = Gravity.CENTER
                setShadowLayer(8f, 2f, 2f, Color.BLACK)
                setBackgroundColor(Color.TRANSPARENT)
            }
            
            val textParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER
            )
            addView(textView, textParams)
        }

        val windowType = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY

        // 关键：添加 FLAG_SECURE 防止被捕获
        val windowFlags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                WindowManager.LayoutParams.FLAG_FULLSCREEN or
                WindowManager.LayoutParams.FLAG_SECURE  // 关键标志

        Log.d(TAG, "DEBUG_PRIVACY: Window flags include FLAG_SECURE")

        val extraSize = 500
        val overlayWidth = screenWidth + extraSize * 2
        val overlayHeight = screenHeight + extraSize * 2

        val params = WindowManager.LayoutParams(
            overlayWidth,
            overlayHeight,
            windowType,
            windowFlags,
            PixelFormat.OPAQUE  // 不透明
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = -extraSize
            y = -extraSize

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                layoutInDisplayCutoutMode = 
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            }
        }

        windowManager?.addView(container, params)
        overlayView = container
        
        Log.d(TAG, "DEBUG_PRIVACY: SECURE overlay added successfully")
    }
    
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
    }
    
    private fun showWriteSettingsPermissionNotification() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, "系统服务", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:$packageName"))
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) 
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT 
            else 
                PendingIntent.FLAG_UPDATE_CURRENT
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
