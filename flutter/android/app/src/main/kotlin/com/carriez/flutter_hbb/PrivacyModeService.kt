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
 * Privacy Mode Service - 半透明遮罩 + 亮度控制方案
 * 
 * 针对不同设备优化：
 * - 华为/荣耀设备：使用更高的alpha值(240)确保屏幕足够暗
 * - 其他设备：使用标准alpha值(235)
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
         * 检测是否是华为/荣耀设备
         */
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
            Log.d(TAG, "DEBUG_PRIVACY: isActive=$isActive")
            Log.d(TAG, "DEBUG_PRIVACY: Device=${Build.MANUFACTURER} ${Build.MODEL}")
            Log.d(TAG, "DEBUG_PRIVACY: isHuawei=${isHuaweiDevice()}")
            
            if (isActive) {
                Log.d(TAG, "DEBUG_PRIVACY: Privacy mode already active, ignoring")
                return
            }
            
            val intent = Intent(context, PrivacyModeService::class.java)
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Log.d(TAG, "DEBUG_PRIVACY: Starting foreground service...")
                    context.startForegroundService(intent)
                } else {
                    Log.d(TAG, "DEBUG_PRIVACY: Starting service...")
                    context.startService(intent)
                }
                Log.d(TAG, "DEBUG_PRIVACY: Service start command sent")
            } catch (e: Exception) {
                Log.e(TAG, "DEBUG_PRIVACY: Failed to start service", e)
                throw e
            }
        }
        
        @Synchronized
        fun stopPrivacyMode(context: Context) {
            Log.d(TAG, "DEBUG_PRIVACY: stopPrivacyMode called, isActive=$isActive")
            if (!isActive) {
                return
            }
            context.stopService(Intent(context, PrivacyModeService::class.java))
        }
    }
    
    private var overlayView: View? = null
    private var windowManager: WindowManager? = null
    private var notificationManager: NotificationManager? = null
    
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "DEBUG_PRIVACY: ========== PrivacyModeService onCreate ==========")
        Log.d(TAG, "DEBUG_PRIVACY: Device: ${Build.MANUFACTURER} ${Build.MODEL}")
        Log.d(TAG, "DEBUG_PRIVACY: Android: ${Build.VERSION.SDK_INT}")
        
        isActive = true
        
        // 检查无障碍服务
        val accessibilityService = InputService.ctx
        Log.d(TAG, "DEBUG_PRIVACY: AccessibilityService ctx = $accessibilityService")
        
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
            val canWrite = Settings.System.canWrite(this)
            Log.d(TAG, "DEBUG_PRIVACY: canWrite settings = $canWrite")
            
            if (!canWrite) {
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
            Log.d(TAG, "DEBUG_PRIVACY: Creating foreground notification...")
            createForegroundNotification()
            Log.d(TAG, "DEBUG_PRIVACY: Foreground notification created")
        } catch (e: Exception) {
            Log.e(TAG, "DEBUG_PRIVACY: Failed to create notification", e)
            isActive = false
            stopSelf()
            return
        }
        
        // 调暗亮度到最低
        try {
            Log.d(TAG, "DEBUG_PRIVACY: Dimming brightness...")
            saveAndDimBrightness()
            Log.d(TAG, "DEBUG_PRIVACY: Brightness dimmed to 0")
        } catch (e: Exception) {
            Log.e(TAG, "DEBUG_PRIVACY: Failed to dim brightness", e)
            // 继续执行，即使亮度控制失败
        }
        
        // 创建覆盖层
        try {
            Log.d(TAG, "DEBUG_PRIVACY: Creating overlay...")
            createDarkOverlay(accessibilityService)
            Log.d(TAG, "DEBUG_PRIVACY: Overlay created successfully")
        } catch (e: Exception) {
            Log.e(TAG, "DEBUG_PRIVACY: Failed to create overlay", e)
            e.printStackTrace()
            isActive = false
            stopSelf()
            return
        }
        
        Log.d(TAG, "DEBUG_PRIVACY: ========== Privacy mode activated ==========")
        
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(this, "隐私模式已开启", Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "DEBUG_PRIVACY: onStartCommand called")
        return START_NOT_STICKY
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    override fun onDestroy() {
        Log.d(TAG, "DEBUG_PRIVACY: onDestroy called")
        
        overlayView?.let { view ->
            try {
                windowManager?.removeView(view)
                Log.d(TAG, "DEBUG_PRIVACY: Overlay removed")
            } catch (e: Exception) {
                Log.e(TAG, "DEBUG_PRIVACY: Error removing overlay", e)
            }
        }
        overlayView = null
        windowManager = null
        
        try {
            restoreBrightness()
            Log.d(TAG, "DEBUG_PRIVACY: Brightness restored")
        } catch (e: Exception) {
            Log.e(TAG, "DEBUG_PRIVACY: Error restoring brightness", e)
        }
        
        isActive = false
        
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(this, "隐私模式已关闭", Toast.LENGTH_SHORT).show()
        }
        
        super.onDestroy()
    }
    
    private fun saveAndDimBrightness() {
        val contentResolver = contentResolver
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        
        val originalMode = try {
            Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE)
        } catch (e: Exception) {
            Log.w(TAG, "DEBUG_PRIVACY: Could not get brightness mode", e)
            Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
        }
        
        val originalBrightness = try {
            Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS)
        } catch (e: Exception) {
            Log.w(TAG, "DEBUG_PRIVACY: Could not get brightness", e)
            128
        }
        
        Log.d(TAG, "DEBUG_PRIVACY: Original brightness=$originalBrightness, mode=$originalMode")
        
        prefs.edit()
            .putInt(KEY_ORIGINAL_BRIGHTNESS_MODE, originalMode)
            .putInt(KEY_ORIGINAL_BRIGHTNESS, originalBrightness)
            .apply()
        
        // 多次设置亮度确保生效（某些设备需要）
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, 
            Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL)
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, 0)
        
        // 华为/荣耀设备延迟再次设置，确保亮度控制生效
        if (isHuaweiDevice()) {
            Handler(Looper.getMainLooper()).postDelayed({
                try {
                    Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, 
                        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL)
                    Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, 0)
                    Log.d(TAG, "DEBUG_PRIVACY: Re-applied brightness=0 for Huawei/Honor")
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
        
        Log.d(TAG, "DEBUG_PRIVACY: Restoring brightness=$originalBrightness, mode=$originalMode")
        
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, originalBrightness)
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, originalMode)
    }
    
    /**
     * 创建深色覆盖层
     * 针对华为/荣耀设备使用更高的alpha值
     */
    private fun createDarkOverlay(accessibilityService: Context) {
        windowManager = accessibilityService.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val display = windowManager?.defaultDisplay
        val screenSize = android.graphics.Point()
        display?.getRealSize(screenSize)
        val screenWidth = screenSize.x
        val screenHeight = screenSize.y
        
        Log.d(TAG, "DEBUG_PRIVACY: Screen size: ${screenWidth}x${screenHeight}")

        // 根据设备类型选择alpha值
        // 需要平衡：Android足够暗 vs PC仍可见
        // 荣耀设备亮度控制无效，只能依赖alpha
        val alphaValue = if (isHuaweiDevice()) {
            Log.d(TAG, "DEBUG_PRIVACY: Using alpha (240) for Huawei/Honor device")
            240  // 比其他设备高一点，补偿亮度控制无效
        } else {
            Log.d(TAG, "DEBUG_PRIVACY: Using standard alpha (235)")
            235
        }

        val container = FrameLayout(accessibilityService).apply {
            setBackgroundColor(Color.argb(alphaValue, 0, 0, 0))
            
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
        Log.d(TAG, "DEBUG_PRIVACY: Using window type: TYPE_ACCESSIBILITY_OVERLAY")

        val windowFlags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                WindowManager.LayoutParams.FLAG_FULLSCREEN

        val extraSize = 500
        val overlayWidth = screenWidth + extraSize * 2
        val overlayHeight = screenHeight + extraSize * 2
        
        Log.d(TAG, "DEBUG_PRIVACY: Overlay size: ${overlayWidth}x${overlayHeight}")

        val params = WindowManager.LayoutParams(
            overlayWidth,
            overlayHeight,
            windowType,
            windowFlags,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = -extraSize
            y = -extraSize

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                layoutInDisplayCutoutMode = 
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            }
        }

        try {
            windowManager?.addView(container, params)
            overlayView = container
            Log.d(TAG, "DEBUG_PRIVACY: Overlay view added to WindowManager")
        } catch (e: Exception) {
            Log.e(TAG, "DEBUG_PRIVACY: Failed to add overlay view", e)
            throw e
        }
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
