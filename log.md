 Compiling powerfmt v0.2.0
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

   Compiling deranged v0.3.11
   Compiling compression-codecs v0.4.31
   Compiling tower v0.5.2
   Compiling flutter_rust_bridge v1.80.1
   Compiling password-hash v0.4.2
   Compiling crc v3.3.0
   Compiling nu-ansi-term v0.46.0
warning: `hbb_common` (lib) generated 3 warnings
   Compiling cipher v0.4.4
   Compiling ndk v0.8.0
   Compiling dasp_envelope v0.11.0
   Compiling scrap v0.5.0 (/home/runner/work/RustDesk-master/RustDesk-master/libs/scrap)
   Compiling bytecodec v0.4.15
   Compiling netlink-packet-core v0.5.0
   Compiling kcp-sys v0.1.0 (https://github.com/rustdesk-org/kcp-sys#32a6c09f)
   Compiling dasp_interpolate v0.11.0
   Compiling magnum-opus v0.4.0 (https://github.com/rustdesk-org/magnum-opus#5cd2bf98)
   Compiling http-body-util v0.1.3
   Compiling tracing-log v0.2.0
   Compiling env_logger v0.10.2
   Compiling sharded-slab v0.1.7
   Compiling dasp_window v0.11.1
   Compiling dlopen2_derive v0.2.0
   Compiling num-integer v0.1.46
   Compiling os-version v0.2.0
   Compiling thread_local v1.1.8
   Compiling enum-map-derive v0.17.0
   Compiling num-derive v0.4.2
   Compiling zerocopy-derive v0.7.34
   Compiling atomic v0.5.3
   Compiling iri-string v0.7.8
   Compiling android_log-sys v0.3.1
   Compiling clap_lex v0.7.6
   Compiling heck v0.4.1
   Compiling cpal v0.15.3 (https://github.com/rustdesk-org/cpal?branch=osx-screencapturekit#6b374bca)
   Compiling time-core v0.1.2
   Compiling md5 v0.7.0
   Compiling strsim v0.11.1
   Compiling num-conv v0.1.0
   Compiling time v0.3.36
   Compiling clap_builder v4.5.53
   Compiling tower-http v0.6.6
   Compiling stun_codec v0.3.5
   Compiling strum_macros v0.24.3
   Compiling android_logger v0.13.3
   Compiling netlink-packet-route v0.15.0
   Compiling oboe v0.6.1
   Compiling allo-isolate v0.1.25
   Compiling zerocopy v0.7.34
   Compiling enum-map v2.7.3
   Compiling tracing-subscriber v0.3.19
   Compiling num-bigint v0.4.6
   Compiling rustdesk v1.4.5 (/home/runner/work/RustDesk-master/RustDesk-master)
   Compiling dlopen2 v0.4.1
   Compiling dasp_signal v0.11.0
   Compiling hyper-tls v0.6.0
   Compiling zstd v0.11.2+zstd.1.5.2
   Compiling bzip2 v0.4.4
   Compiling webm v1.1.0 (https://github.com/rustdesk-org/rust-webm#d2c4d3ac)
   Compiling aes v0.8.4
   Compiling pbkdf2 v0.11.0
   Compiling async-compression v0.4.32
   Compiling threadpool v1.8.1
warning: scrap@0.5.0: cargo:rustc-check-cfg requires -Zcheck-cfg flag
   Compiling hyper-rustls v0.27.7
   Compiling serde_urlencoded v0.7.1
   Compiling dashmap v6.1.0
   Compiling dasp_slice v0.11.0
   Compiling netlink-sys v0.8.6
   Compiling auto_impl v1.3.0
   Compiling rtoolbox v0.0.2
   Compiling flutter_rust_bridge_macros v1.82.6
   Compiling debug-helper v0.3.13
   Compiling widestring v1.1.0
   Compiling urlencoding v2.1.3
   Compiling bytemuck v1.23.2
   Compiling constant_time_eq v0.2.6
   Compiling strum v0.24.1
   Compiling base32 v0.4.0
   Compiling constant_time_eq v0.1.5
   Compiling zip v0.6.6
   Compiling rdev v0.5.0-2 (https://github.com/rustdesk-org/rdev#f9b60b1d)
   Compiling totp-rs v5.5.1
   Compiling cidr-utils v0.5.11
   Compiling rpassword v7.3.1
   Compiling default-net v0.14.1
   Compiling dasp v0.11.0
   Compiling reqwest v0.12.24
   Compiling fon v0.6.0
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

   Compiling stunclient v0.4.1
   Compiling clap v4.5.53
   Compiling parity-tokio-ipc v0.7.3-5 (https://github.com/rustdesk-org/parity-tokio-ipc#c8c8bbcb)
   Compiling android-wakelock v0.1.0 (https://github.com/rustdesk-org/android-wakelock#d0292e5a)
warning: `scrap` (lib) generated 3 warnings (run `cargo fix --lib -p scrap` to apply 3 suggestions)
   Compiling repng v0.2.2
   Compiling ringbuf v0.3.3
   Compiling async-trait v0.1.80
   Compiling serde_repr v0.1.19
   Compiling errno v0.3.14
   Compiling wol-rs v1.0.1
   Compiling hex v0.4.3
   Compiling shutdown_hooks v0.1.0
warning: `hbb_common` (lib) generated 3 warnings (3 duplicates)
error[E0433]: failed to resolve: use of undeclared crate or module `log`
  --> src/privacy_mode/android.rs:48:13
   |
48 |             log::error!("Failed to enable Android privacy mode: {}", e);
   |             ^^^ use of undeclared crate or module `log`

error[E0433]: failed to resolve: use of undeclared crate or module `log`
  --> src/privacy_mode/android.rs:53:9
   |
53 |         log::info!("Android privacy mode turned on for conn_id: {}", conn_id);
   |         ^^^ use of undeclared crate or module `log`

error[E0433]: failed to resolve: use of undeclared crate or module `log`
  --> src/privacy_mode/android.rs:70:13
   |
70 |             log::error!("Failed to disable Android privacy mode: {}", e);
   |             ^^^ use of undeclared crate or module `log`

error[E0433]: failed to resolve: use of undeclared crate or module `log`
  --> src/privacy_mode/android.rs:75:9
   |
75 |         log::info!("Android privacy mode turned off");
   |         ^^^ use of undeclared crate or module `log`

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
error: could not compile `rustdesk` (lib) due to 4 previous errors; 18 warnings emitted
[2026-01-23T17:44:13Z INFO  cargo_ndk::cli] If the build failed due to a missing target, you can run this command:
[2026-01-23T17:44:13Z INFO  cargo_ndk::cli] 
[2026-01-23T17:44:13Z INFO  cargo_ndk::cli]     rustup target install aarch64-linux-android
Error: Process completed with exit code 101.