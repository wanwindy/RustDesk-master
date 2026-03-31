Rustdesk二次开发电脑端控制安卓手机端黑屏功能的代码
RustDesk 是一款开源的远程控制软件，其在二次开发中实现电脑端控制安卓手机端时启用黑屏功能，核心在于修改 Android 客户端（被控端）的代码，使其在远程连接建立后能够关闭本地屏幕显示，同时保持远程控制会话流畅进行。这涉及到 Android 平台的屏幕控制、远程协议扩展以及与 RustDesk 核心逻辑的集成。
1. 技术原理与方案设计
黑屏功能的核心是视觉隔离，即被控设备（安卓手机）的屏幕关闭或显示纯色，而控制端（电脑）仍然能看到并操作手机界面。根据参考资料，RustDesk 具备此类安全功能基础，例如其“黑屏模式”能在远程维护时实现“本地屏幕不可见、远程可操作的安全隔离”。对于安卓平台，实现此功能通常涉及以下几个层面：
功能层面	技术实现方案	说明与关键点
屏幕内容捕获	保持现有的 MediaProjection 或 Surface 捕获流程	这是远程控制的基础，必须持续运行以向控制端发送画面。
本地屏幕控制	调用 Android 的 PowerManager 或 WindowManager API	使手机本地屏幕变黑或进入休眠状态，同时不影响后台的捕获服务。
远程协议指令	扩展 RustDesk 的自定义信令	新增一个控制指令，由控制端发送，被控端接收并执行“黑屏”或“恢复”操作。
安全与权限	确保 WAKE_LOCK 和 DISABLE_KEYGUARD 等权限	防止屏幕关闭后系统锁屏或进程被挂起，确保远程会话的连续性。
2. 代码实现详解
以下代码示例基于对 RustDesk Android 端源码结构的理解，展示了关键修改点。请注意，这是一个二次开发的示例框架，实际集成需要根据具体的 RustDesk 版本和代码结构进行调整。
1. 扩展协议指令定义 首先，需要在 RustDesk 的通信协议中定义新的消息类型，用于控制端向被控端发送黑屏命令。通常可以在协议定义文件中添加。
// 文件路径示例：rustdesk-android/app/src/main/java/com/rustdesk/protocol/Command.java
public class Command {
    // ... 其他已有命令

    // 新增黑屏相关命令
    public static final int CMD_BLACK_SCREEN_ON = 0x100; // 开启黑屏
    public static final int CMD_BLACK_SCREEN_OFF = 0x101; // 关闭黑屏
}
2. 安卓被控端服务（核心实现） 在负责处理远程连接和屏幕捕获的 Android Service 中，实现黑屏逻辑。这里需要处理远程指令并调用系统 API。
// 文件路径示例：rustdesk-android/app/src/main/java/com/rustdesk/service/CaptureService.java
import android.content.Context;
import android.os.PowerManager;
import android.view.WindowManager;
import android.graphics.PixelFormat;
import android.view.View;
import android.view.Gravity;

public class CaptureService {
    private PowerManager.WakeLock mWakeLock;
    private WindowManager mWindowManager;
    private View mBlackView;
    private boolean isBlackScreenActive = false;

    private void initScreenControl(Context context) {
        // 1. 获取 PowerManager 并创建唤醒锁，防止CPU休眠
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        mWakeLock = powerManager.newWakeLock(
                PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP,
                "RustDesk::BlackScreenWakeLock"
        );

        // 2. 获取 WindowManager，用于添加覆盖层
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    /**
     * 处理从控制端接收到的自定义命令
     * @param command 命令码
     */
    public void handleCustomCommand(int command) {
        switch (command) {
            case Command.CMD_BLACK_SCREEN_ON:
                enableBlackScreen();
                break;
            case Command.CMD_BLACK_SCREEN_OFF:
                disableBlackScreen();
                break;
            default:
                // 处理其他命令
                break;
        }
    }

    /**
     * 启用黑屏功能
     */
    private void enableBlackScreen() {
        if (isBlackScreenActive) {
            return;
        }
        // 1. 获取唤醒锁，保持设备活跃
        if (mWakeLock != null && !mWakeLock.isHeld()) {
            mWakeLock.acquire();
        }

        // 2. 创建一个全屏的黑色覆盖层
        mBlackView = new View(context);
        mBlackView.setBackgroundColor(Color.BLACK);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, // 需要 OVERLAY 权限
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                PixelFormat.TRANSLUCENT
        );
        params.gravity = Gravity.TOP | Gravity.START;

        try {
            mWindowManager.addView(mBlackView, params);
            isBlackScreenActive = true;
            // 此处可以添加日志，通知控制端操作成功
        } catch (Exception e) {
            e.printStackTrace();
            // 处理权限异常等
        }
    }

    /**
     * 禁用黑屏功能
     */
    private void disableBlackScreen() {
        if (!isBlackScreenActive || mBlackView == null) {
            return;
        }
        try {
            mWindowManager.removeView(mBlackView);
            mBlackView = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放唤醒锁
            if (mWakeLock != null && mWakeLock.isHeld()) {
                mWakeLock.release();
            }
            isBlackScreenActive = false;
        }
    }

    // 在服务销毁时清理资源
    @Override
    public void onDestroy() {
        disableBlackScreen();
        super.onDestroy();
    }
}
3. 电脑控制端（发送指令） 控制端需要在用户界面（UI）上添加触发黑屏功能的按钮，并通过 RustDesk 的通信链路发送对应的命令。
// 文件路径示例：rustdesk/src/ui/remote_window.rs (Rust 客户端)
// 这是一个简化的 Rust 示例，展示如何发送自定义命令
use std::net::TcpStream;
use std::io::Write;

impl RemoteWindow {
    // ... 其他代码

    // 假设有一个发送原始命令的函数
    fn send_command_to_peer(&self, command: u32) {
        if let Some(mut stream) = &self.control_stream { // 假设存在控制流
            let cmd_bytes = command.to_be_bytes(); // 转换为网络字节序
            if let Err(e) = stream.write_all(&cmd_bytes) {
                eprintln!("Failed to send command: {}", e);
            }
        }
    }

    // 响应UI按钮点击，发送开启黑屏命令
    fn on_black_screen_button_clicked(&self) {
        self.send_command_to_peer(0x100); // 对应 CMD_BLACK_SCREEN_ON
    }

    // 响应UI按钮点击，发送关闭黑屏命令
    fn on_restore_screen_button_clicked(&self) {
        self.send_command_to_peer(0x101); // 对应 CMD_BLACK_SCREEN_OFF
    }
}
3. 关键配置与权限
安卓端的修改需要相应的系统权限，必须在 AndroidManifest.xml 中声明：
<!-- 文件路径：rustdesk-android/app/src/main/AndroidManifest.xml -->
<manifest ...>
    <!-- 使用唤醒锁的权限 -->
    <uses-permission android:name="android.permission.WAKE_LOCK" />
    <!-- 禁用键盘锁的权限（部分场景可能需要） -->
    <uses-permission android:name="android.permission.DISABLE_KEYGUARD" />
    <!-- 系统悬浮窗权限 (TYPE_APPLICATION_OVERLAY 所需) -->
    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />

    <application ...>
        ...
    </application>
</manifest>
同时，对于 Android 6.0 (API 23) 及以上，SYSTEM_ALERT_WINDOW 是危险权限，需要在运行时动态申请。