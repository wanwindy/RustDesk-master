PS C:\Users\Administrator> adb logcat | Select-String "DEBUG_PRIVACY|flutter_hbb|LOG_SERVICE|rustdesk|Android.*platform|platform.*Android"

01-26 22:54:00.100 19150  5101 I MediaCodec: [mId: 2] [video-debug-enc] Stats 44212ms:Client:com.carriez.flutter_hbb,Co
mponent:c2.qti.hevc.encoder,Qin:11,DQin:11/11,Render:0,encode:11,DQout:11/23
01-26 22:54:00.101  2829 20837 I BatteryNotifier: noteStartVideo: the video refcount for uid(10464) clientName(com.carr
iez.flutter_hbb) is 0.
01-26 22:54:05.277 19150  5095 D LOG_SERVICE: DEBUG_PRIVACY: ===== rustSetByName called =====
01-26 22:54:05.277 19150  5095 D LOG_SERVICE: DEBUG_PRIVACY: name=stop_capture, arg1=, arg2=
01-26 22:54:05.277 19150  5095 D LOG_SERVICE: from rust:stop_capture
01-26 22:54:05.277 19150  5095 D LOG_SERVICE: Stop Capture
01-26 22:54:05.279  2580  3187 I DisplayDeviceRepository: Display device changed state: "RustDeskVD", OFF
01-26 22:54:05.281  2580  3185 W DisplayContentStubImpl:  displayMetricsChanged=false physicalDisplayChanged=false disp
layId=63 newUniqueId=virtual:com.carriez.flutter_hbb,10464,RustDeskVD,61 currentUnique=virtual:com.carriez.flutter_hbb,
10464,RustDeskVD,61
01-26 22:54:05.285  2580  3185 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{4234be u0 com.carriez.flutte
r_hbb/com.carriez.flutter_hbb.MainActivity} 0
01-26 22:54:05.286  2580  3185 D BarFollowAnimation: isAppTaskBarOnTop win:Window{4234be u0 com.carriez.flutter_hbb/com
.carriez.flutter_hbb.MainActivity} (mCurrentTopTask:Task{b871552 #22073 type=standard A=10464:com.carriez.flutter_hbb}
: true); (mAssociatedTask:Task{b871552 #22073 type=standard A=10464:com.carriez.flutter_hbb} : true); (mHomeTask:Task{9
a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLastHomeBarVisible:false
01-26 22:54:05.287  2580  3185 I DisplayDevice: [63] Layerstack set to -1 for virtual:com.carriez.flutter_hbb,10464,Rus
tDeskVD,61
01-26 22:54:05.289  2580  3188 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{4234be u0 com.carriez.flutte
r_hbb/com.carriez.flutter_hbb.MainActivity} 0
01-26 22:54:05.289  2580  3188 D BarFollowAnimation: isAppTaskBarOnTop win:Window{4234be u0 com.carriez.flutter_hbb/com
.carriez.flutter_hbb.MainActivity} (mCurrentTopTask:Task{b871552 #22073 type=standard A=10464:com.carriez.flutter_hbb}
: true); (mAssociatedTask:Task{b871552 #22073 type=standard A=10464:com.carriez.flutter_hbb} : true); (mHomeTask:Task{9
a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLastHomeBarVisible:false
01-26 22:54:05.291 19150  5101 I MediaCodec: [mId: 2] [video-debug-enc] Stats 5192ms:Client:com.carriez.flutter_hbb,Com
ponent:c2.qti.hevc.encoder,Qin:22,DQin:22/22,Render:0,encode:22,DQout:22/46
01-26 22:54:05.299  2084  2084 I SurfaceFlinger: SF-DisplayRect: setDisplayLayerStack, display[name:RustDeskVD,id:virtu
al] displayLayerStack=-1
01-26 22:54:05.300  2084  2084 D SurfaceFlinger: Not adding dormant virtual display with token 0xb4000072003e2710: Rust
DeskVD
01-26 22:54:05.303  2084  2084 D Layer   : releaseRelativeLayerFE size:2 mirrorRootId:795317 com.carriez.flutter_hbb/co
m.carriez.flutter_hbb.MainActivity#795205 path:TraversalPath{.id = 795205, .mirrorRootIds=795317,}
01-26 22:54:05.303  2084  2084 D Layer   : releaseRelativeLayerFE size:2 mirrorRootId:795317 ae9d684 SurfaceView[com.ca
rriez.flutter_hbb/com.carriez.flutter_hbb.MainActivity](BLAST)#795211 path:TraversalPath{.id = 795211, .mirrorRootIds=7
95317,, .relativeRootIds=795210,}
01-26 22:54:05.303  2084  2084 D Layer   : releaseRelativeLayerFE size:2 mirrorRootId:795317 Background for ae9d684 Sur
faceView[com.carriez.flutter_hbb/com.carriez.flutter_hbb.MainActivity]#795212 path:TraversalPath{.id = 795212, .mirrorR
ootIds=795317,, .relativeRootIds=795212,}
01-26 22:54:05.341  2829 20837 I BatteryNotifier: noteStopVideo: the video refcount for uid(10464) clientName(com.carri
ez.flutter_hbb) is 0.
01-26 22:54:05.341 19150  5101 D MediaImpl: MediaImpl::sendVideoPlayData::mediacodec_packgeName : com.carriez.flutter_h
bb
01-26 22:54:05.341 19150  5101 D MediaImpl: MediaImpl::sendVideoPlayData::packgeName : com.carriez.flutter_hbb
01-26 22:54:05.341 19150  5101 D send_data_to_xlog: send_video_play_to_xlog::packgeName : com.carriez.flutter_hbb
01-26 22:54:05.342 19150  5101 D iez.flutter_hbb: EventReporter , EventReporter init failed
01-26 22:54:05.342 19150  5101 E xlog_client: _xlog_write_flat_msg:fail to  send msg {"name":"video_play","audio_event"
:{"video_play_framerate":"30","video_play_hdr":"0","video_play_width_height":"1216_2688","video_play_framratecal":"-1",
"video_play_frc":"0","video_play_aie":"0","video_play_ais":"0","video_play_ace":"0","video_play_dolbyvision":"0", "vide
o_play_codecdomain":"1", "video_play_durationsec":"155","video_play_heif_decode":"0","video_play_heif_encode":"0","vide
o_play_codec":"video/hevc", "video_play_app":"com.carriez.flutter_hbb"},"dgt":"null","audio_ext":"null" }
01-26 22:54:05.342 19150  5101 D iez.flutter_hbb: VideoServiceClientImpl::reportCodecStatus
01-26 22:54:05.342 19150  5101 D iez.flutter_hbb: VideoServiceClientImpl::releaseListener
01-26 22:54:05.343 19150  5101 I iez.flutter_hbb: enter ~VideoServiceListener
01-26 22:54:05.373 19150  5127 I AudioRecord: stop mSessionID=157937, mPortID=28810, mActice: 1 packageName: com.carrie
z.flutter_hbb
01-26 22:54:05.399  2580 12488 E AppOps  : attributionTag  not declared in manifest of com.carriez.flutter_hbb
01-26 22:54:05.575  2068  5105 D AudioFlingerImpl: checkForGameParameter_l() set events appname=-com.carriez.flutter_hb
b
01-26 22:54:05.577 19150  5127 I AudioRecord: stop mSessionID=157937, mPortID=28810, mActice: 0 packageName: com.carrie
z.flutter_hbb
01-26 22:54:05.582 19150 31831 E flutter : #10     ServerModel.androidUpdatekeepScreenOn (package:flutter_hbb/models/se
rver_model.dart:800)
01-26 22:54:05.600  6697  6697 D FocusPlugin: NotifLifetime: onNotificationRemoved, key: 0|com.carriez.flutter_hbb|1601
|null|10464, needExtendLifetime: false, reason: 8
01-26 22:54:05.601  6697  6697 I FocusPlugin: onNotificationRemoved 0|com.carriez.flutter_hbb|1601|null|10464, content:
 null
01-26 22:54:05.601  6697  6697 I FocusPlugin: removeByKey 0|com.carriez.flutter_hbb|1601|null|10464
01-26 22:54:05.601  6697  6697 D HideDeletedFocusController: onNotificationRemoved 8 0|com.carriez.flutter_hbb|1601|nul
l|10464
01-26 22:54:05.601  6697  6697 D AlarmScheduler-FOCUS_NOTIFICATION_DELETE_TIMEOUT: cancelAlarm, key=0|com.carriez.flutt
er_hbb|1601|null|10464, currentKey=0|com.miui.video|954388051|null|10289
01-26 22:54:05.601  6697  6697 D DynamicIslandLifetimeController: maybeExtendLifetime: 0|com.carriez.flutter_hbb|1601|n
ull|10464, null
01-26 22:54:05.601  6697  6697 D AlarmScheduler-fold: cancelAlarm, key=0|com.carriez.flutter_hbb|1601|null|10464, curre
ntKey=0|tv.danmaku.bili|-695265077|null|10309
01-26 22:54:05.601  6697  6697 D AlarmScheduler-TIMEOUT: cancelAlarm, key=0|com.carriez.flutter_hbb|1601|null|10464, cu
rrentKey=0|com.miui.securitycenter|1|null|1000
01-26 22:54:06.111  4698  4877 I Launcher.ApplicationsMessage: update com.carriez.flutter_hbb/ to null
01-26 22:54:14.963 19150  6817 D LOG_SERVICE: DEBUG_PRIVACY: ===== rustSetByName called =====
01-26 22:54:14.964 19150  6817 D LOG_SERVICE: DEBUG_PRIVACY: name=add_connection, arg1={"id":1502,"authorized":true,"di
sconnected":false,"is_file_transfer":false,"is_view_camera":false,"is_terminal":false,"port_forward":"","name":"Admin",
"peer_id":"476755898","keyboard":true,"clipboard":true,"audio":true,"file":true,"restart":true,"recording":true,"block_
input":true,"from_switch":false,"in_voice_call":false,"incoming_voice_call":false}, arg2=
01-26 22:54:14.965 19150  6817 D LOG_SERVICE: updateScreenInfo:w:1200,h:2670
01-26 22:54:14.966 19150  6817 D LOG_SERVICE: Start Capture
01-26 22:54:14.966 19150  6817 D LOG_SERVICE: ImageReader.newInstance:INFO:Info(width=1200, height=2670, scale=1, dpi=4
80)
01-26 22:54:14.967 19150  6817 D LOG_SERVICE: ImageReader.setOnImageAvailableListener done
01-26 22:54:14.967 19150  6817 D LOG_SERVICE: startRawVideoRecorder,screen info:Info(width=1200, height=2670, scale=1,
dpi=480)
01-26 22:54:14.970  2580  3187 I DisplayDeviceRepository: Display device changed state: "RustDeskVD", ON
01-26 22:54:14.971  2580  3185 W DisplayContentStubImpl:  displayMetricsChanged=false physicalDisplayChanged=false disp
layId=63 newUniqueId=virtual:com.carriez.flutter_hbb,10464,RustDeskVD,61 currentUnique=virtual:com.carriez.flutter_hbb,
10464,RustDeskVD,61
01-26 22:54:14.972 19150 19150 W Thread-12: type=1400 audit(0.0:51939373): avc:  denied  { read } for  name="loadavg" d
ev="proc" ino=4026532009 scontext=u:r:untrusted_app_32:s0:c208,c257,c512,c768 tcontext=u:object_r:proc_loadavg:s0 tclas
s=file permissive=0 app=com.carriez.flutter_hbb
01-26 22:54:14.977  2580  3185 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{4234be u0 com.carriez.flutte
r_hbb/com.carriez.flutter_hbb.MainActivity} 0
01-26 22:54:14.978  2580  3185 D BarFollowAnimation: isAppTaskBarOnTop win:Window{4234be u0 com.carriez.flutter_hbb/com
.carriez.flutter_hbb.MainActivity} (mCurrentTopTask:Task{b871552 #22073 type=standard A=10464:com.carriez.flutter_hbb}
: true); (mAssociatedTask:Task{b871552 #22073 type=standard A=10464:com.carriez.flutter_hbb} : true); (mHomeTask:Task{9
a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLastHomeBarVisible:false
01-26 22:54:14.978  2580  3185 I DisplayDevice: [63] Layerstack set to 63 for virtual:com.carriez.flutter_hbb,10464,Rus
tDeskVD,61
01-26 22:54:14.980 19150 32618 I MediaCodec: The process name is com.carriez.flutter_hbb
01-26 22:54:14.980 19150 32618 D MediaCodec: MediaCodec u-p-n client:10464-19150-com.carriez.flutter_hbb process:10464-
19150-com.carriez.flutter_hbb
01-26 22:54:14.985  2580  3188 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{4234be u0 com.carriez.flutte
r_hbb/com.carriez.flutter_hbb.MainActivity} 0
01-26 22:54:14.985  2580  3188 D BarFollowAnimation: isAppTaskBarOnTop win:Window{4234be u0 com.carriez.flutter_hbb/com
.carriez.flutter_hbb.MainActivity} (mCurrentTopTask:Task{b871552 #22073 type=standard A=10464:com.carriez.flutter_hbb}
: true); (mAssociatedTask:Task{b871552 #22073 type=standard A=10464:com.carriez.flutter_hbb} : true); (mHomeTask:Task{9
a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLastHomeBarVisible:false
01-26 22:54:14.994 19150  6817 D AudioRecord: set(): 0xb400007b26880480, inputSource 8, sampleRate 48000, format 0x5, c
hannelMask 0xc, frameCount 1920, notificationFrames 0, sessionId 0, transferType 0, flags 0, attributionSource Attribut
ionSourceState{pid: 19150, uid: 10464, deviceId: 0, packageName: com.carriez.flutter_hbb, attributionTag: (null), token
: binder:0xb400007b1683b990, renouncedPermissions: [], next: []}uid -1, pid -1
01-26 22:54:14.994  2084  2084 I SurfaceFlinger: SF-DisplayRect: setDisplayLayerStack, display[name:RustDeskVD,id:virtu
al] displayLayerStack=63
01-26 22:54:14.994 19150 32618 I MediaCodec: [mId: 3] [video-debug-enc] configure: ClientName: com.carriez.flutter_hbb
ComponentName: c2.qti.hevc.encoder
01-26 22:54:14.994 19150 32618 I MediaCodec:   string activity = "com.carriez.flutter_hbb.MainActivity"
01-26 22:54:14.994  2084  2084 I SurfaceFlinger: SF-DisplayRect: processDisplayAdded, display[name:RustDeskVD,id:virtua
l] displayLayerStack=63
01-26 22:54:14.996 19150 32618 I VideoBox: isNeedFdRoiScene ThreadName: com.carriez.flutter_hbb:Thread-12,  codec name:
 c2.qti.hevc.encoder
01-26 22:54:14.996  2084  2084 D MI-SF   : getP3ProjectionScreenSupportStatus: map is empty, RustDeskVD status return 0
01-26 22:54:14.996  2084  2084 D MI-SF   : setupNewDisplayDeviceInternalcheck: displayName:RustDeskVD, hasWideColorGamu
t=false, isScreenrecord=false
01-26 22:54:14.996 19150 32618 D MediaImpl: MediaImpl::sendPackageName: com.carriez.flutter_hbb
01-26 22:54:14.996 19150 32618 D MediaStub: MediaStub::sendPackageName com.carriez.flutter_hbb
01-26 22:54:14.996  2084  2084 I CompositionEngine: SF-DisplayRect: setProjection display[RustDeskVD], layerStackSpace=
{bounds=Rect(0, 0, 1200, 2670), content=Rect(0, 0, 1200, 2670)}, displaySpace={bounds=Rect(0, 0, 1200, 2670), content=R
ect(0, 0, 1200, 2670)}
01-26 22:54:14.996  2084  2084 I CompositionEngine: SF-DisplayRect: setProjection display[RustDeskVD], layerStackSpace=
{bounds=Rect(0, 0, 1200, 2670), content=Rect(0, 0, 1200, 2670)}, displaySpace={bounds=Rect(0, 0, 1200, 2670), content=R
ect(0, 0, 1200, 2670)}
01-26 22:54:14.996 19150 32618 D iez.flutter_hbb: VideoServiceClientImpl::registerListener
01-26 22:54:14.996 19150 32618 I iez.flutter_hbb: VideoServiceClientImpl::registerListener ok
01-26 22:54:14.996 19150 32618 D iez.flutter_hbb: VideoServiceClientImpl::reportCodecStatus
01-26 22:54:14.998  2084  2084 D SurfaceFlinger: updateSnapshot(com.carriez.flutter_hbb/com.carriez.flutter_hbb.MainAct
ivity#795205-stackId:63) update transformHint from 128 to 0.
01-26 22:54:14.998  2084  2084 D SurfaceFlinger: updateSnapshot(ae9d684 SurfaceView[com.carriez.flutter_hbb/com.carriez
.flutter_hbb.MainActivity](BLAST)#795211-stackId:63) update transformHint from 128 to 0.
01-26 22:54:14.999  2068  5680 D APM_AudioPolicyManager: getInputForAttr AudioPlayBackCapture not supported, appName: c
om.carriez.flutter_hbb
01-26 22:54:15.001 19150  6827 E iez.flutter_hbb: Failed to query component interface for required system resources: 6
01-26 22:54:15.004 19150  6817 D LOG_SERVICE: audio recorder start
01-26 22:54:15.005 19150  6817 I AudioRecord: start mSessionID=157945 start(28821): sync event 0 trigger session 0  pac
kageName: com.carriez.flutter_hbb
01-26 22:54:15.006  2580  7885 E AppOps  : attributionTag  not declared in manifest of com.carriez.flutter_hbb
01-26 22:54:15.008  2068  6833 D AudioFlingerImpl: checkForGameParameter_l() set events appname=+com.carriez.flutter_hb
b
01-26 22:54:15.009  2068  5680 D APM_AudioPolicyManager: startInput input:7334, session:157945, portId:28821, source=8,
 appname com.carriez.flutter_hbb, uid 10464)
01-26 22:54:15.015  1938  6854 I CV      : File: vendor/qcom/proprietary/cv-noship/eva/4.0/src/cpu/platform/android/eva
Session.cpp Line: 496 Function: evaGetDebugSettings() Msg: EVA Debug Logs Disabled
01-26 22:54:15.015  1938  6854 I CV      : File: vendor/qcom/proprietary/cv-noship/eva/4.0/src/cpu/platform/android/eva
Session.cpp Line: 546 Function: evaGetPresilSettings() Msg: EVA Sync default timeout used. Timeout = 2500 ms
01-26 22:54:15.016  1938  6854 E CV      : File: vendor/qcom/proprietary/cv-noship/eva/4.0/src/cpu/platform/android/eva
Session.cpp Line: 1837 Function: evaCreateSession() Msg: evaCreateSession: error while opening subset_parts
01-26 22:54:15.082  2580  7911 I AudioService.RecordingActivityMonitor: rec update riid:134495 uid:10464 session:157945
 src:REMOTE_SUBMIX not silenced pack:com.carriez.flutter_hbb
01-26 22:54:15.083 19150 31831 E flutter : #10     ServerModel.androidUpdatekeepScreenOn (package:flutter_hbb/models/se
rver_model.dart:800)
01-26 22:54:15.089  6697  6697 D FocusPlugin: plugin onNotificationPosted: 0|com.carriez.flutter_hbb|1602|null|10464 co
m.carriez.flutter_hbb
01-26 22:54:15.090  6697  6697 D FocusPlugin: 0|com.carriez.flutter_hbb|1602|null|10464 is not focus notification, isMe
dia: false
01-26 22:54:15.107 19150 32618 D LOG_SERVICE: DEBUG_PRIVACY: ===== rustSetByName called =====
01-26 22:54:15.107 19150 32618 D LOG_SERVICE: DEBUG_PRIVACY: name=half_scale, arg1=false, arg2=
01-26 22:54:15.121 19150 32618 D iez.flutter_hbb: VideoServiceClientImpl::reportCodecStatus
01-26 22:54:15.293  6697  6697 D AlarmScheduler-fold: addAlarm, key=0|com.carriez.flutter_hbb|1602|null|10464, triggerT
ime=2617771234
01-26 22:54:15.294  6697  6697 D AlarmScheduler-TIMEOUT: addAlarm, key=0|com.carriez.flutter_hbb|1602|null|10464, trigg
erTime=2855371235
01-26 22:54:15.296  6697  6697 I MiuiBubbleSettings: com.carriez.flutter_hbb 0isAppEnableBubbleNotification: false
01-26 22:54:15.297  6697  7147 D NotificationProvider: call method=updateNotificationUsageInfo extras=Bundle[{packageNa
me=com.carriez.flutter_hbb}]
01-26 22:54:15.345  6697  6697 D AlertCoordinator: onViewBound, buzzBeep for 0|com.carriez.flutter_hbb|1602|null|10464
01-26 22:54:15.361  6697  6697 W NotifRow: cancelAppearDrawing childKey: 0|com.carriez.flutter_hbb|1602|null|10464 wasD
rawing:false
01-26 22:54:15.364  6697  6697 W NotifRow: cancelAppearDrawing childKey: 0|com.carriez.flutter_hbb|1|null|10464 wasDraw
ing:false
01-26 22:54:15.621  2829 27592 I BatteryNotifier: noteStartVideo: the video refcount for uid(10464) clientName(com.carr
iez.flutter_hbb) is 0.
01-26 22:54:15.800  4698  4877 I Launcher.ApplicationsMessage: update com.carriez.flutter_hbb/ to null
01-26 22:54:20.612 19150  6824 I MediaCodec: [mId: 3] [video-debug-enc] Stats 5611ms:Client:com.carriez.flutter_hbb,Com
ponent:c2.qti.hevc.encoder,Qin:7,DQin:7/7,Render:0,encode:7,DQout:7/18
01-26 22:54:26.630 19150  6824 I MediaCodec: [mId: 3] [video-debug-enc] Stats 6018ms:Client:com.carriez.flutter_hbb,Com
ponent:c2.qti.hevc.encoder,Qin:5,DQin:5/5,Render:0,encode:5,DQout:5/9
01-26 22:54:27.639  2829 27592 I BatteryNotifier: noteStopVideo: the video refcount for uid(10464) clientName(com.carri
ez.flutter_hbb) is 0.
01-26 22:54:27.640  2829 20837 I BatteryNotifier: noteStartVideo: the video refcount for uid(10464) clientName(com.carr
iez.flutter_hbb) is 0.
01-26 22:54:31.651 19150  6824 I MediaCodec: [mId: 3] [video-debug-enc] Stats 5020ms:Client:com.carriez.flutter_hbb,Com
ponent:c2.qti.hevc.encoder,Qin:9,DQin:9/9,Render:0,encode:9,DQout:9/18
01-26 22:54:37.635 19150  6824 I MediaCodec: [mId: 3] [video-debug-enc] Stats 5983ms:Client:com.carriez.flutter_hbb,Com
ponent:c2.qti.hevc.encoder,Qin:11,DQin:11/11,Render:0,encode:11,DQout:11/22
01-26 22:54:37.638  2829 20837 I BatteryNotifier: noteStopVideo: the video refcount for uid(10464) clientName(com.carri
ez.flutter_hbb) is 0.