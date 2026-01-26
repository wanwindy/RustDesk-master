warning: unknown lint: `static_mut_refs`
  --> libs/hbb_common/src/platform/mod.rs:65:17
   |
65 |         #[allow(static_mut_refs)]
   |                 ^^^^^^^^^^^^^^^
   |
   = note: `#[warn(unknown_lints)]` on by default

warning: unknown lint: `static_mut_refs`
   --> libs/hbb_common/src/fingerprint.rs:251:17
    |
251 |         #[allow(static_mut_refs)]
    |                 ^^^^^^^^^^^^^^^

warning: unknown lint: `static_mut_refs`
   --> libs/hbb_common/src/fingerprint.rs:283:17
    |
283 |         #[allow(static_mut_refs)]
    |                 ^^^^^^^^^^^^^^^

warning: scrap@0.5.0: cargo:rustc-check-cfg requires -Zcheck-cfg flag
warning: unused variable: `java_vm`
   --> libs/scrap/src/android/ffi.rs:217:13
    |
217 |         let java_vm = jvm.get_java_vm_pointer() as *mut c_void;
    |             ^^^^^^^ help: if this is intentional, prefix it with an underscore: `_java_vm`
    |
    = note: `#[warn(unused_variables)]` on by default

warning: unused variable: `env`
   --> libs/scrap/src/android/ffi.rs:486:15
    |
486 |     if let Ok(env) = vm.get_env() {
    |               ^^^ help: if this is intentional, prefix it with an underscore: `_env`

warning: unused variable: `java_vm`
   --> libs/scrap/src/android/ffi.rs:505:17
    |
505 |             let java_vm = jvm.get_java_vm_pointer() as *mut c_void;
    |                 ^^^^^^^ help: if this is intentional, prefix it with an underscore: `_java_vm`

warning: `hbb_common` (lib) generated 3 warnings (3 duplicates)
   Compiling rustdesk v1.4.5 (/home/runner/work/RustDesk-master/RustDesk-master)
warning: `scrap` (lib) generated 3 warnings (run `cargo fix --lib -p scrap` to apply 3 suggestions)
warning: `hbb_common` (lib) generated 3 warnings
error[E0433]: failed to resolve: use of undeclared crate or module `log`
   --> src/privacy_mode.rs:212:5
    |
212 |     log::info!("DEBUG_PRIVACY: turn_on_privacy global called with impl_key: {}", impl_key);
    |     ^^^ use of undeclared crate or module `log`

warning: unused imports: `keys`, `option2bool`
  --> src/server/clipboard_service.rs:19:26
   |
19 | use hbb_common::config::{keys, option2bool};
   |                          ^^^^  ^^^^^^^^^^^
   |
   = note: `#[warn(unused_imports)]` on by default

warning: unused imports: `RecvTimeoutError`, `channel`, `io`
  --> src/server/clipboard_service.rs:23:5
   |
23 |     io,
   |     ^^
24 |     sync::mpsc::{channel, RecvTimeoutError},
   |                  ^^^^^^^  ^^^^^^^^^^^^^^^^

warning: unused import: `RecvTimeoutError`
  --> src/client.rs:24:22
   |
24 |         mpsc::{self, RecvTimeoutError},
   |                      ^^^^^^^^^^^^^^^^

warning: unused import: `message_proto::*`
 --> src/client/screenshot.rs:3:18
  |
3 | use hbb_common::{message_proto::*, ResultType};
  |                  ^^^^^^^^^^^^^^^^

warning: the item `ToSocketAddrs` is imported redundantly
    --> src/common.rs:2028:9
     |
4    |     net::{SocketAddr, ToSocketAddrs},
     |                       ------------- the item `ToSocketAddrs` is already imported here
...
2028 |     use std::net::ToSocketAddrs;
     |         ^^^^^^^^^^^^^^^^^^^^^^^

warning: the item `ToSocketAddrs` is imported redundantly
    --> src/common.rs:2052:9
     |
4    |     net::{SocketAddr, ToSocketAddrs},
     |                       ------------- the item `ToSocketAddrs` is already imported here
...
2052 |     use std::net::ToSocketAddrs;
     |         ^^^^^^^^^^^^^^^^^^^^^^^

warning: the item `Enum` is imported redundantly
    --> src/common.rs:2314:9
     |
26   |     protobuf::{Enum, Message as _},
     |                ---- the item `Enum` is already imported here
...
2314 |     use hbb_common::protobuf::Enum;
     |         ^^^^^^^^^^^^^^^^^^^^^^^^^^

warning: unused import: `self`
  --> src/ipc.rs:28:16
   |
28 |         keys::{self, OPTION_ALLOW_WEBSOCKET},
   |                ^^^^

warning: unused imports: `Duration`, `path::PathBuf`
  --> src/flutter_ffi.rs:26:5
   |
26 |     path::PathBuf,
   |     ^^^^^^^^^^^^^
...
31 |     time::{Duration, SystemTime},
   |            ^^^^^^^^

warning: unused imports: `Error as JniError`, `JObject`, `Result as JniResult`
    --> src/flutter_ffi.rs:2954:18
     |
2954 |         errors::{Error as JniError, Result as JniResult},
     |                  ^^^^^^^^^^^^^^^^^  ^^^^^^^^^^^^^^^^^^^
2955 |         objects::{JClass, JObject, JString},
     |                           ^^^^^^^

warning: unused imports: `ResultType`, `bail`, `log`
 --> src/clipboard.rs:3:18
  |
3 | use hbb_common::{bail, log, message_proto::*, ResultType};
  |                  ^^^^  ^^^                    ^^^^^^^^^^

warning: unused imports: `Arc`, `Mutex`, `time::Duration`
 --> src/clipboard.rs:5:12
  |
5 |     sync::{Arc, Mutex},
  |            ^^^  ^^^^^
6 |     time::Duration,
  |     ^^^^^^^^^^^^^^

warning: unused import: `decompress`
   --> src/clipboard.rs:540:47
    |
540 |         compress::{compress as compress_func, decompress},
    |                                               ^^^^^^^^^^

warning: use of deprecated function `hbb_common::base64::encode`: Use Engine::encode
   --> src/hbbs_http/sync.rs:187:52
    |
187 |                         hash = hbb_common::base64::encode(&res[..]);
    |                                                    ^^^^^^
    |
    = note: `#[warn(deprecated)]` on by default

warning: unused variable: `control_permissions`
   --> src/server/connection.rs:354:9
    |
354 |         control_permissions: Option<ControlPermissions>,
    |         ^^^^^^^^^^^^^^^^^^^ help: if this is intentional, prefix it with an underscore: `_control_permissions`
    |
    = note: `#[warn(unused_variables)]` on by default

warning: unused variable: `pos`
   --> src/server/connection.rs:862:61
    |
862 |                         Some(message::Union::CursorPosition(pos)) => {
    |                                                             ^^^ help: if this is intentional, prefix it with an underscore: `_pos`

warning: unused variable: `terminal`
    --> src/server/connection.rs:1536:17
     |
1536 |         let mut terminal = cfg!(not(any(target_os = "android", target_os = "ios")));
     |                 ^^^^^^^^ help: if this is intentional, prefix it with an underscore: `_terminal`

warning: unused variable: `action`
    --> src/server/connection.rs:3196:53
     |
3196 |                 Some(message::Union::TerminalAction(action)) => {
     |                                                     ^^^^^^ help: if this is intentional, prefix it with an underscore: `_action`

For more information about this error, try `rustc --explain E0433`.
warning: `rustdesk` (lib) generated 18 warnings
error: could not compile `rustdesk` (lib) due to previous error; 18 warnings emitted
[2026-01-25T16:46:33Z INFO  cargo_ndk::cli] If the build failed due to a missing target, you can run this command:
[2026-01-25T16:46:33Z INFO  cargo_ndk::cli] 
[2026-01-25T16:46:33Z INFO  cargo_ndk::cli]     rustup target install aarch64-linux-android
Error: Process completed with exit code 101.