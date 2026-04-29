# `docs/app.apk` 遮罩服务逆向分析

本文基于仓库内 `docs/app.apk` 的静态分析结果整理，不是原始源码导出。
Java/Kotlin 部分来自 `apkanalyzer dex code` 的 smali 反汇编，native 部分来自 `libprivacy_overlay.so` 导出符号与字符串推断。

## 结论先看

这个 APK 的“稳定黑屏遮罩”实现，并不是 RustDesk 现在这条“亮度锚点 + 系统亮度压到 0”的思路。

它的主链路是：

1. `MainActivity` 先拿 `MediaProjection` 权限。
2. 再要求用户启用 `AccessibilityService`。
3. `MediaProjection` 被存进全局 `MediaProjectionHolder`。
4. `PrivacyAccessibilityService` 启动后：
   - 创建 native 黑色全屏遮罩。
   - 用 `MediaProjection + ImageReader + VirtualDisplay` 抓当前屏幕帧。
   - 把处理后的帧显示到遮罩上的一个小型 `ImageView` 预览窗。
5. 录屏前台服务 `MediaProjectionForegroundService` 只负责保活权限和前台通知。

换句话说，这个 APK 的目标更像是：

- 本地屏幕几乎全黑。
- 本地操作者还能在角落看到一个小预览。
- 录屏权限不断。

它没有证据表明自己处理了“远端还能继续看到真实画面，而不是黑遮罩”这个 RustDesk 专属诉求。

## Manifest 与组件结构

从 `AndroidManifest.xml` 可确认的关键组件：

- 包名：`com.pj.myapplication`
- 权限：
  - `android.permission.SYSTEM_ALERT_WINDOW`
  - `android.permission.FOREGROUND_SERVICE`
  - `android.permission.FOREGROUND_SERVICE_MEDIA_PROJECTION`
  - `android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS`
- 服务：
  - `com.pj.myapplication.PrivacyAccessibilityService`
  - `com.pj.myapplication.MediaProjectionForegroundService`

组件职责：

- `MainActivity`
  - 申请录屏权限
  - 引导启用无障碍服务
  - 启停隐私服务
- `PrivacyAccessibilityService`
  - 真正的遮罩服务主体
  - 创建 overlay
  - 启动 `MediaProjection` 帧采集
- `MediaProjectionForegroundService`
  - 前台通知保活
- `MediaProjectionHolder`
  - 全局保存 `MediaProjection`
- `PrivacyOverlayNative`
  - JNI 入口，负责 native 遮罩与帧处理

## Java/Kotlin 侧伪源码

下面是根据 smali 还原出来的主逻辑，便于后续迁移，不追求逐行等价。

### `MainActivity`

```kotlin
class MainActivity : AppCompatActivity() {
    var hasMediaProjectionPermission = false
    var isAccessibilityEnabled = false
    var enableMask = true

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        privacyModeButton.setOnClickListener { handleButtonClick() }
        enableMaskCheckBox.setOnCheckedChangeListener { _, checked ->
            enableMask = checked
        }
        checkMediaProjectionPermissionFirst()
    }

    private fun handleButtonClick() {
        val service = PrivacyAccessibilityService.getInstance()
        if (service?.isReady() == true) {
            stopAllServices()
            return
        }

        if (!hasMediaProjectionPermission) {
            requestMediaProjectionDirectly()
            return
        }

        if (!isAccessibilityEnabled) {
            openAccessibilitySettings()
            return
        }

        startPrivacyService()
    }

    private fun onMediaProjectionGranted(data: Intent) {
        val manager = getSystemService(MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        MediaProjectionHolder.setMediaProjection(
            manager.getMediaProjection(Activity.RESULT_OK, data)
        )
        startForegroundServiceAfterPermission()
        hasMediaProjectionPermission = true
    }

    private fun startPrivacyService() {
        PrivacyAccessibilityService.getInstance()?.startCapture(enableMask)
    }

    private fun stopAllServices() {
        PrivacyAccessibilityService.getInstance()?.stopCapture()
        MediaProjectionHolder.clear()
        stopService(Intent(this, MediaProjectionForegroundService::class.java).apply {
            action = "STOP"
        })
    }
}
```

### `PrivacyAccessibilityService`

```kotlin
class PrivacyAccessibilityService : AccessibilityService() {
    var combinedOverlayView: FrameLayout? = null
    var previewImageView: ImageView? = null
    var imageReader: ImageReader? = null
    var mediaProjection: MediaProjection? = null
    var virtualDisplay: VirtualDisplay? = null
    var projectionRunning = false

    override fun onServiceConnected() {
        instance = this
        handler = Handler(Looper.getMainLooper())
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
    }

    fun startCapture(enableMask: Boolean = true) {
        val dm = resources.displayMetrics
        screenWidth = dm.widthPixels
        screenHeight = dm.heightPixels
        screenDensity = dm.densityDpi

        if (enableMask) {
            PrivacyOverlayNative.initPrivacyOverlay(screenWidth, screenHeight, 5)
        }

        mediaProjection = MediaProjectionHolder.getMediaProjection()
        registerMediaProjectionCallback()
        startMediaProjection()
        createCombinedOverlay(enableMask)
    }

    fun stopCapture() {
        stopMediaProjection()
        removeCombinedOverlay()
    }
}
```

### `createCombinedOverlay(enableMask)`

这里是最关键的还原点。

APK 在 Android 8+ 上用的是：

- `TYPE_ACCESSIBILITY_OVERLAY` (`0x7f0`)

Android 8 以下退回：

- `TYPE_SYSTEM_ALERT` (`0x7d3`)

然后调用 native 方法：

```kotlin
combinedOverlayView = PrivacyOverlayNative.createCombinedOverlay(
    service = this,
    windowManager = windowManager,
    enableMask = enableMask,
    screenWidth = screenWidth,
    screenHeight = screenHeight,
    flags = 0x804c0698.toInt(),
    width = screenWidth,
    height = screenHeight,
    pixelFormat = -3, // PixelFormat.TRANSLUCENT
    windowType = TYPE_ACCESSIBILITY_OVERLAY,
    gravity = 0x30 // TOP
)
```

native 返回 `FrameLayout` 之后，Java 再往上加一个右上角预览：

```kotlin
previewImageView = ImageView(this).apply {
    setBackgroundColor(Color.WHITE)
    scaleType = ImageView.ScaleType.FIT_CENTER
    layoutParams = FrameLayout.LayoutParams(
        screenWidth / 4,
        screenHeight / 4
    ).apply {
        gravity = Gravity.TOP or Gravity.END
    }
}
combinedOverlayView?.addView(previewImageView)
```

## 帧采集与预览更新

`PrivacyAccessibilityService.startMediaProjection()` 的逻辑：

```kotlin
imageReader = ImageReader.newInstance(
    screenWidth,
    screenHeight,
    PixelFormat.RGBA_8888,
    2
)

imageReader.setOnImageAvailableListener({ reader ->
    if (!projectionRunning) return@setOnImageAvailableListener
    val image = reader.acquireLatestImage() ?: return@setOnImageAvailableListener
    updatePreviewFromImage(image)
    image.close()
}, handler)

virtualDisplay = mediaProjection.createVirtualDisplay(
    "PrivacyOverlay",
    screenWidth,
    screenHeight,
    screenDensity,
    16,
    imageReader.surface,
    null,
    handler
)
projectionRunning = true
```

`updatePreviewFromImage(image)` 的逻辑：

```kotlin
val plane = image.planes[0]
val src = plane.buffer
val rowStride = plane.rowStride
val pixelStride = plane.pixelStride
val out = ByteBuffer.allocateDirect(screenWidth * screenHeight * 4)

PrivacyOverlayNative.processFrame(
    src,
    rowStride,
    pixelStride,
    screenWidth,
    screenHeight,
    out
)

out.rewind()
val bitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888)
bitmap.copyPixelsFromBuffer(out)
handler.post {
    previewImageView?.setImageBitmap(bitmap)
}
```

说明：

- `processFrame()` 明显是 native 图像处理入口。
- 这个处理大概率包含：
  - 行对齐修正
  - RGBA 拷贝/重排
  - 可能的缩放、裁剪、遮罩处理
- `initPrivacyOverlay(width, height, 5)` 里的第三个参数日志里叫 `alpha: 5`，推测 native 黑遮罩可能有自己的 alpha 配置。

## Native 层可确认的信息

从 `lib/arm64-v8a/libprivacy_overlay.so` 可确认：

- JNI 导出函数：
  - `Java_com_pj_myapplication_PrivacyOverlayNative_createCombinedOverlay`
  - `Java_com_pj_myapplication_PrivacyOverlayNative_initPrivacyOverlay__II`
  - `Java_com_pj_myapplication_PrivacyOverlayNative_initPrivacyOverlay__III`
  - `Java_com_pj_myapplication_PrivacyOverlayNative_processFrame`
  - `Java_com_pj_myapplication_PrivacyOverlayNative_setMaskView`
- `.so` 带有 Rust 构建痕迹：
  - `jni-0.21.1`
  - `src\\overlay_builder.rs`
  - Cargo registry 路径

因此可以判断：

- 这个 native 库大概率是 Rust 写的 JNI 模块，不是 C/C++。
- 遮罩 view 的拼装至少有一部分在 native 内完成。
- 但 `.so` 当前是编译产物，不是可直接回收的原始 Rust 源文件。

也就是说：

- Java/Kotlin 主链路可以较高可信度还原。
- native Rust 只能恢复“职责”和“调用边界”，无法无损恢复成原工程源码。

## 它和 RustDesk 当前实现的关键差异

RustDesk 当前 Android 隐私模式实现在：

- [PrivacyModeService.kt](/D:/rustdesk/RustDesk-master/flutter/android/app/src/main/kotlin/com/carriez/flutter_hbb/PrivacyModeService.kt)
- [MainService.kt](/D:/rustdesk/RustDesk-master/flutter/android/app/src/main/kotlin/com/carriez/flutter_hbb/MainService.kt)
- [InputService.kt](/D:/rustdesk/RustDesk-master/flutter/android/app/src/main/kotlin/com/carriez/flutter_hbb/InputService.kt)

当前策略核心是：

- 默认不直接铺全黑 overlay。
- 更偏向：
  - `1x1` 透明亮度锚点
  - `WRITE_SETTINGS`
  - 把系统亮度压到 `0`
- 只在少数机型允许更激进的全屏遮罩。

APK 则相反：

- 默认就是完整黑遮罩。
- 再叠一个本地预览角窗。
- 遮罩服务本身依赖 `AccessibilityService`。

## 为什么不能直接照搬进 RustDesk

这是这次逆向里最重要的结论。

RustDesk 的隐私模式要求是：

- 本地看不到真实屏幕。
- 远端仍然能看到真实屏幕。

而这个 APK 的实现只证明了：

- 本地能被一个稳定的黑遮罩盖住。
- 它自己还能拿 `MediaProjection` 做本地小窗预览。

它没有证明：

- 这个全屏黑遮罩不会进入 RustDesk 自己的 `MediaProjection` 采集链路。

反而从 Android 官方文档语义看，`MediaProjection` 采的是设备显示内容本身，并没有给 `TYPE_ACCESSIBILITY_OVERLAY` 提供“永不进录屏”的官方保证。

因此，直接把这个 APK 的“全屏黑遮罩”替换到 RustDesk 上，有很大概率出现：

- 本地黑了。
- 远端也一起黑了。

这也是 RustDesk 当前代码为什么保守地把“全屏黑遮罩”限制在少数机型，而大多数设备优先走亮度路线。

## 对 RustDesk 更可行的迁移建议

如果后续要把这个 APK 的思路吸收进 RustDesk，建议按下面顺序做：

1. 不要直接删掉当前 `BRIGHTNESS_COMPAT` 路线。
2. 新增一个实验策略，例如：
   - `ACCESSIBILITY_BLACK_MASK_PREVIEW`
3. 复用 RustDesk 现有 `MainService.mediaProjection`，不要再复制一套新的录屏授权流程。
4. 让 `PrivacyModeService` 从 `MainService` 读取：
   - `MediaProjection`
   - 当前屏幕宽高和 DPI
5. 先实现 Java 版本的：
   - `TYPE_ACCESSIBILITY_OVERLAY` 全黑遮罩
   - 角落小预览窗
6. 把该策略放在：
   - 手动开关
   - 设备白名单
   - 日志埋点
   下逐步验证。
7. 只有确认“远端仍看真实屏幕”时，才扩大机型范围。

## 对后续编码最有价值的可迁移点

真正值得迁移的不是“把 APK 原样抄进来”，而是这几个结构设计：

- `AccessibilityService` 持有 overlay，而不是普通 service 直接画全屏。
- `MediaProjection` 和遮罩服务解耦，通过 holder 或 service 引用共享。
- 单独的前台服务保活录屏权限。
- 遮罩层和预览层分离：黑遮罩 native/overlay 一层，预览 `ImageView` 一层。

## 结论

这个 APK 已经成功逆出主链路了，结论是：

- 它的稳定性来源于“无障碍全屏遮罩 + MediaProjection 小窗预览 + 前台服务保活”。
- 它不是 RustDesk 当前亮度路线的简单小修补。
- 它可以作为 RustDesk Android 隐私模式的“实验新策略”参考。
- 但不能直接当作“兼容所有机型的现成替换实现”。

如果后续要继续推进，下一步最合理的是：

- 在 RustDesk 里新增一个实验性的 `Accessibility black mask + preview` 路线。
- 先接现有 `MainService.mediaProjection`。
- 不要移除当前亮度兼容方案。
