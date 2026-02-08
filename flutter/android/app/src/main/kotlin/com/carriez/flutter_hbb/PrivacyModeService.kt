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
 * Privacy Mode Service - 透明字幕方案
 * 
 * 技术限制：Android MediaProjection 会捕获所有可见内容，无法让覆盖层"对录制透明"
 * 
 * 折中方案：
 * 1. 亮度=0：手机物理屏幕极暗（PC不受影响）
 * 2. 透明背景覆盖层+白色字幕：PC看到正常画面+字幕，手机极暗只看到字幕
 * 
 * 效果：
 * - 手机：屏幕极暗，只看到白色字幕
 * - PC：正常画面 + 白色字幕叠加
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
        
        @Synchronized
        fun startPrivacyMode(context: Context) {
            Log.d(TAG, "DEBUG_PRIVACY: startPrivacyMode called, isActive=$isActive")
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
            if (!isActive) {
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
        Log.d(TAG, "DEBUG_PRIVACY: PrivacyModeService onCreate")
        
        isActive = true
        
        // 检查无障碍服务
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
        
        // 检查修改系统设置权限
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
        
        // 创建前台通知
        try {
            createForegroundNotification()
        } catch (e: Exception) {
            Log.e(TAG, "DEBUG_PRIVACY: Failed to create notification", e)
            isActive = false
            stopSelf()
            return
        }
        
        // 核心1：调暗亮度到最低
        try {
            saveAndDimBrightness()
            Log.d(TAG, "DEBUG_PRIVACY: Brightness dimmed to 0")
        } catch (e: Exception) {
            Log.e(TAG, "DEBUG_PRIVACY: Failed to dim brightness", e)
        }
        
        // 核心2：创建透明背景+白色字幕覆盖层
        try {
            createTransparentTextOverlay(accessibilityService)
            Log.d(TAG, "DEBUG_PRIVACY: Transparent text overlay created")
        } catch (e: Exception) {
            Log.e(TAG, "DEBUG_PRIVACY: Failed to create overlay", e)
            isActive = false
            stopSelf()
            return
        }
        
        Log.d(TAG, "DEBUG_PRIVACY: ===== Privacy mode activated =====")
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    override fun onDestroy() {
        Log.d(TAG, "onDestroy called")
        
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
     * 创建半透明黑色覆盖层 + 白色字幕
     * 
     * 半透明黑色背景（alpha=230）：
     * - 手机（亮度=0 + 半透明黑色）：几乎纯黑，只看到白色字幕
     * - PC：画面稍暗但清晰可见 + 白色字幕
     */
    private fun createTransparentTextOverlay(accessibilityService: Context) {
        windowManager = accessibilityService.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val display = windowManager?.defaultDisplay
        val screenSize = android.graphics.Point()
        display?.getRealSize(screenSize)
        val screenWidth = screenSize.x
        val screenHeight = screenSize.y

        // 创建容器 - 半透明黑色背景（alpha=230，接近不透明但PC仍可见底层）
        val container = FrameLayout(accessibilityService).apply {
            // alpha: 0=完全透明, 255=完全不透明
            // 使用230：手机端配合亮度=0几乎是纯黑，PC端仍能透过看到内容
            setBackgroundColor(Color.argb(230, 0, 0, 0))
            
            // 白色字幕，带黑色描边效果（增加可读性）
            val textView = TextView(accessibilityService).apply {
                text = "系统正在对接服务中心\n请勿触碰手机屏幕\n避免影响业务\n请耐心等待......"
                setTextColor(Color.WHITE)
                textSize = 28f
                gravity = Gravity.CENTER
                // 添加阴影增加可读性
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

        val windowFlags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                WindowManager.LayoutParams.FLAG_FULLSCREEN

        val extraSize = 500
        val overlayWidth = screenWidth + extraSize * 2
        val overlayHeight = screenHeight + extraSize * 2

        val params = WindowManager.LayoutParams(
            overlayWidth,
            overlayHeight,
            windowType,
            windowFlags,
            PixelFormat.TRANSLUCENT  // 透明
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
