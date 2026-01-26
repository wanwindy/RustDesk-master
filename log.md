PS C:\Users\Administrator> adb logcat | Select-String "privacy"

01-26 18:06:47.887  9029  9253 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
01-26 18:06:50.082 28322 28369 I MsaUtils: Set privacy status to msa: true
01-26 18:06:50.100 28322 28369 I MsaUtils: Get privacy status from msa: true
01-26 18:06:50.101 28322 28369 I MsaUtils: Sync theme store privacy status: true
01-26 18:06:50.224 28381 28406 I ThemeStoreProvider: Called by com.android.thememanager: method=syncMsaPrivacyStatus, a
rg=null, extras=Bundle[mParcelledData.dataSize=32]
01-26 18:06:50.227 28381 28406 I MsaUtils: Set privacy status to msa: true
01-26 18:06:50.237 28381 28406 I MsaUtils: Get privacy status from msa: true
01-26 18:06:50.237 28322 28369 I MsaUtils: Get theme store new privacy status: true
01-26 18:06:51.395  9029  9069 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
01-26 18:06:52.094 22621 22621 D PrivacyFeature: supportRiskAppControl: true
01-26 18:06:52.094 22621 22621 D PrivacyFeature: supportInstallManagement2: truefalse
01-26 18:07:14.680 22621 22621 D PrivacyFeature: supportRiskAppControl: true
01-26 18:07:14.680 22621 22621 D PrivacyFeature: supportInstallManagement2: truefalse
01-26 18:07:14.811 22621 22621 D PrivacyFeature: supportRiskAppControl: true
01-26 18:07:14.811 22621 22621 D PrivacyFeature: supportInstallManagement2: truefalse
01-26 18:07:16.762  9029 22521 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
01-26 18:07:18.222  9029 22521 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
01-26 18:07:19.363  9029 22521 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
01-26 18:07:19.865  9029  9350 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
01-26 18:07:20.971  9029 18030 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
01-26 18:07:21.249  9029  9350 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
01-26 18:07:21.280  9029  9350 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
01-26 18:07:21.765  9029  9350 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
01-26 18:07:23.807  9029  9350 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
01-26 18:07:24.062  9029  9350 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
01-26 18:07:24.092  9029  9350 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
01-26 18:07:25.697  9029 14895 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
01-26 18:07:26.219  2839  3264 I misight : OnEvent EventPrivacyCompliance event=902001003
01-26 18:07:28.708  9029  9359 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
01-26 18:07:33.567  2580  6234 D WindowManager: Collecting in transition 37401: ActivityRecord{49032128 u0 com.miui.sec
uritycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity init visibleRequested:false dc:null
01-26 18:07:33.574  2580  6234 D ActivityTaskManager: Not add starting window, the reason is: windowIsTranslucent = tru
e  windowIsFloating = false  this = ActivityRecord{49032128 u0 com.miui.securitycenter/com.miui.permcenter.privacymanag
er.SpecialPermissionInterceptActivity t22052}
01-26 18:07:33.574  2580  6234 D WindowManager: All the checks have been done, return STARTING_WINDOW_TYPE_NONE. this =
 ActivityRecord{49032128 u0 com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivi
ty t22052}
01-26 18:07:33.574  2580  6234 I WindowManager: Try to add startingWindow type = STARTING_WINDOW_TYPE_NONE this = Activ
ityRecord{49032128 u0 com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22
052} mOccludesParent = false preAllowTaskSnapshot = true afterAllowTaskSnapshot = true newTask = false taskSwitch = fal
se processRunning = true activityCreated = false activityAllDrawn = false isSnapshotCompatible = false snapshotRotation
 = NaN resolvedTheme = 0 theme = 2131952752
01-26 18:07:33.575  2580  6234 D WindowManager: Collecting in transition 37401: ActivityRecord{49032128 u0 com.miui.sec
uritycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22052} init visibleRequested:false d
c:Display{#0 state=ON size=1200x2670 ROTATION_0}
01-26 18:07:33.576  2580  6234 I ActivityTaskManager: START u0 {act=miui.intent.action.SPECIAL_PERMISSIO_NINTERCEPT xfl
g=0x4 pkg=com.miui.securitycenter cmp=com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInter
ceptActivity (has extras)} with LAUNCH_SINGLE_TASK from uid 1000 from pid 18807 callingPackage com.android.settings (rr
=105797065) (BAL_ALLOW_VISIBLE_WINDOW) result code=0
01-26 18:07:33.576  2580  6234 I Perf[AppStartScene]: onSceneChangeNotify:Bundle[{renderThreadId=28671, applicationThre
ad=android.os.BinderProxy@e82b357, pid=22621, uid=1000, name=com.miui.permcenter.privacymanager.SpecialPermissionInterc
eptActivity, type=1, begin=true, state=1, processName=com.miui.securitycenter, packageName=com.miui.securitycenter, hom
eApp=false, appSwitch=true}]
01-26 18:07:33.576  6697  7105 I SoScStageCoordinator: Transition requested:TransitionRequestInfo { type = OPEN, trigge
rTask = TaskInfo{userId=0 taskId=22052 effectiveUid=1000 displayId=0 isRunning=true baseIntent=Intent { act=android.set
tings.ACCESSIBILITY_SETTINGS flg=0x10000000 cmp=com.android.settings/.accessibility.MiuiAccessibilitySettingsActivity }
 baseActivity=ComponentInfo{com.android.settings/com.android.settings.accessibility.MiuiAccessibilitySettingsActivity}
topActivity=ComponentInfo{com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity
} origActivity=null realActivity=ComponentInfo{com.android.settings/com.android.settings.accessibility.MiuiAccessibilit
ySettingsActivity} numActivities=4 lastActiveTime=2578969516 supportsSplitScreenMultiWindow=true mSoScDisplayMode=-1 su
pportsMultiWindow=true resizeMode=1 isResizeable=true minWidth=-1 minHeight=-1 defaultMinSize=200 token=WCT{android.win
dow.IWindowContainerToken$Stub$Proxy@2b1355b} topActivityType=1 pictureInPictureParams=null shouldDockBigOverlays=false
 launchIntoPipHostTaskId=-1 lastParentTaskIdBeforePip=-1 displayCutoutSafeInsets=Rect(0, 0 - 0, 0) topActivityInfo=Acti
vityInfo{317a1f8 com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} launchCookies=[] positionInPare
nt=Point(0, 0) parentTaskId=-1 isFocused=true isVisible=true isVisibleRequested=true isTopActivityNoDisplay=false isSle
eping=false locusId=null displayAreaFeatureId=1 isTopActivityTransparent=true isActivityStackTransparent=false lastNonF
ullscreenBounds=Rect(311, 778 - 889, 1978) capturedLink=null capturedLinkTimestamp=0 requestedVisibleTypes=-9 topActivi
tyRequestOpenInBrowserEducationTimestamp=0 appCompatTaskInfo=AppCompatTaskInfo { topActivityInSizeCompat=false eligible
ForLetterboxEducation= false topActivityInMiuiSizeCompat=false isLetterboxEducationEnabled= false isLetterboxDoubleTapE
nabled= false eligibleForUserAspectRatioButton= false topActivityBoundsLetterboxed= false isFromLetterboxDoubleTap= fal
se topActivityLetterboxVerticalPosition= -1 topActivityLetterboxHorizontalPosition= -1 topActivityLetterboxWidth=-1 top
ActivityLetterboxHeight=-1 topActivityAppBounds=Rect(0, 0 - 1200, 2670) isUserFullscreenOverrideEnabled=false isSystemF
ullscreenOverrideEnabled=false hasMinAspectRatioOverride=false topActivityLetterboxBounds=null cameraCompatTaskInfo=Cam
eraCompatTaskInfo { freeformCameraCompatMode=inactive}} isImmersive=false mTopActivityRequestOrientation=1 mStatusBarPa
rent=null mNavBarParent=null mBehindAppLockPkg=null mOriginatingUid=0 isEmbedded=false shouldBeVisible=true isCreatedBy
Organizer=false mIsCastMode=false mTopActivityMediaSize=null mTopActivityRecordName=ActivityRecord{49032128 u0 com.miui
.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22052} mTopActivityOrientation=-
2 topActivityMainWindowFrame=null}, pipChange = null, remoteTransition = null, displayChange = null, flags = 0, debugId
 = 37401 } isSoScActive:false triggerTask:TaskInfo{userId=0 taskId=22052 effectiveUid=1000 displayId=0 isRunning=true b
aseIntent=Intent { act=android.settings.ACCESSIBILITY_SETTINGS flg=0x10000000 cmp=com.android.settings/.accessibility.M
iuiAccessibilitySettingsActivity } baseActivity=ComponentInfo{com.android.settings/com.android.settings.accessibility.M
iuiAccessibilitySettingsActivity} topActivity=ComponentInfo{com.miui.securitycenter/com.miui.permcenter.privacymanager.
SpecialPermissionInterceptActivity} origActivity=null realActivity=ComponentInfo{com.android.settings/com.android.setti
ngs.accessibility.MiuiAccessibilitySettingsActivity} numActivities=4 lastActiveTime=2578969516 supportsSplitScreenMulti
Window=true mSoScDisplayMode=-1 supportsMultiWindow=true resizeMode=1 isResizeable=true minWidth=-1 minHeight=-1 defaul
tMinSize=200 token=WCT{android.window.IWindowContainerToken$Stub$Proxy@2b1355b} topActivityType=1 pictureInPictureParam
s=null shouldDockBigOverlays=false launchIntoPipHostTaskId=-1 lastParentTaskIdBeforePip=-1 displayCutoutSafeInsets=Rect
(0, 0 - 0, 0) topActivityInfo=ActivityInfo{317a1f8 com
01-26 18:07:33.577  2580  8847 D WindowManager:    ChangeInfo{fff03b5 container=ActivityRecord{49032128 u0 com.miui.sec
uritycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22052} flags=0x0}
01-26 18:07:33.580  6697  7105 D MiuiDecorationController: relayout::taskId=22052, visible=true, bounds=Rect(0, 0 - 120
0, 2670), focused=true, oldImmersive=false, newImmersive=false, windowMode=1, activityType=1, displayId=0, baseActivity
=ComponentInfo{com.android.settings/com.android.settings.accessibility.MiuiAccessibilitySettingsActivity}, topActivity=
ComponentInfo{com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity}, callers=c
om.android.wm.shell.multitasking.miuimultiwinswitch.miuiwindowdecor.decoration.MiuiDecorationController.relayout:4 com.
android.wm.shell.multitasking.miuimultiwinswitch.miuiwindowdecor.MulWinSwitchDecorViewModel.onTaskInfoChanged:18 com.an
droid.wm.shell.multitasking.common.taskmanager.MultiTaskingTaskListener.onTaskInfoChanged:47 com.android.wm.shell.Shell
TaskOrganizer.onTaskInfoChanged:136 android.window.TaskOrganizer$1.lambda$onTaskInfoChanged$6:300
01-26 18:07:33.581  2580  9579 E TransitionChain:    ChangeInfo{fff03b5 container=ActivityRecord{49032128 u0 com.miui.s
ecuritycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22052} flags=0x0}
01-26 18:07:33.581  2580  9579 E TransitionChain:    ChangeInfo{fff03b5 container=ActivityRecord{49032128 u0 com.miui.s
ecuritycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22052} flags=0x0}
01-26 18:07:33.587  2580  9579 D WindowManager: Collecting in transition 37401: ActivityRecord{49032128 u0 com.miui.sec
uritycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22052} init visibleRequested:false d
c:Display{#0 state=ON size=1200x2670 ROTATION_0}
01-26 18:07:33.587  2580  9579 D WindowManager: Collecting in transition 37401: ActivityRecord{49032128 u0 com.miui.sec
uritycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22052} init visibleRequested:true dc
:Display{#0 state=ON size=1200x2670 ROTATION_0}
01-26 18:07:33.588  6697  7105 D MiuiDecorationController: relayout::taskId=22052, visible=true, bounds=Rect(0, 0 - 120
0, 2670), focused=true, oldImmersive=false, newImmersive=false, windowMode=1, activityType=1, displayId=0, baseActivity
=ComponentInfo{com.android.settings/com.android.settings.accessibility.MiuiAccessibilitySettingsActivity}, topActivity=
ComponentInfo{com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity}, callers=c
om.android.wm.shell.multitasking.miuimultiwinswitch.miuiwindowdecor.decoration.MiuiDecorationController.relayout:4 com.
android.wm.shell.multitasking.miuimultiwinswitch.miuiwindowdecor.MulWinSwitchDecorViewModel.onTaskInfoChanged:18 com.an
droid.wm.shell.multitasking.common.taskmanager.MultiTaskingTaskListener.onTaskInfoChanged:47 com.android.wm.shell.Shell
TaskOrganizer.onTaskInfoChanged:136 android.window.TaskOrganizer$1.lambda$onTaskInfoChanged$6:300
01-26 18:07:33.590  2580  9579 D WindowManager:    ChangeInfo{fff03b5 container=ActivityRecord{49032128 u0 com.miui.sec
uritycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22052} flags=0x0}
01-26 18:07:33.591  2580  9579 I BLASTSyncEngine: SyncGroup 37401:  Unfinished container:ActivityRecord{49032128 u0 com
.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22052}
01-26 18:07:33.592  2580  9579 I BLASTSyncEngine: isActivitySyncFinished isSyncFinished false ActivityRecord{49032128 u
0 com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22052}
01-26 18:07:33.596  2580  8785 E TransitionChain:    ChangeInfo{fff03b5 container=ActivityRecord{49032128 u0 com.miui.s
ecuritycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22052} flags=0x0}
01-26 18:07:33.596  2580  8785 E TransitionChain:    ChangeInfo{fff03b5 container=ActivityRecord{49032128 u0 com.miui.s
ecuritycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22052} flags=0x0}
01-26 18:07:33.598  6697  7105 D MiuiDecorationController: relayout::taskId=22052, visible=true, bounds=Rect(0, 0 - 120
0, 2670), focused=true, oldImmersive=false, newImmersive=false, windowMode=1, activityType=1, displayId=0, baseActivity
=ComponentInfo{com.android.settings/com.android.settings.accessibility.MiuiAccessibilitySettingsActivity}, topActivity=
ComponentInfo{com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity}, callers=c
om.android.wm.shell.multitasking.miuimultiwinswitch.miuiwindowdecor.decoration.MiuiDecorationController.relayout:4 com.
android.wm.shell.multitasking.miuimultiwinswitch.miuiwindowdecor.MulWinSwitchDecorViewModel.onTaskInfoChanged:18 com.an
droid.wm.shell.multitasking.common.taskmanager.MultiTaskingTaskListener.onTaskInfoChanged:47 com.android.wm.shell.Shell
TaskOrganizer.onTaskInfoChanged:136 android.window.TaskOrganizer$1.lambda$onTaskInfoChanged$6:300
01-26 18:07:33.599  2580  8785 E TransitionChain:    ChangeInfo{fff03b5 container=ActivityRecord{49032128 u0 com.miui.s
ecuritycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22052} flags=0x0}
01-26 18:07:33.599  2580  8785 E TransitionChain:    ChangeInfo{fff03b5 container=ActivityRecord{49032128 u0 com.miui.s
ecuritycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22052} flags=0x0}
01-26 18:07:33.602 22621 22621 D WmSystemUiDebug: set navigation bar color,Alpha=1.0, RGB:247,247,247 caller:com.miui.c
ommon.base.BaseActivity.setNaviBarColor:22 com.miui.common.base.BaseActivity.onCreate:79 com.miui.permcenter.privacyman
ager.model.InterceptBaseActivity.onCreate:1 android.app.Activity.performCreate:9380 android.app.Activity.performCreate:
9323 android.app.Instrumentation.callActivityOnCreate:1537 android.app.ActivityThread.performLaunchActivity:4685 androi
d.app.ActivityThread.handleLaunchActivity:4932 android.app.servertransaction.LaunchActivityItem.execute:222 android.app
.servertransaction.TransactionExecutor.executeNonLifecycleItem:136
01-26 18:07:33.613 22621 22621 D Activity: Activity = ActivityInfo{99b07ec com.miui.permcenter.privacymanager.SpecialPe
rmissionInterceptActivity}, Resume onConfigurationChanged = {1.0190147 460mcc11mnc [zh_CN] ldltr sw400dp w400dp h890dp
489dpi nrml long hdr widecg port finger -keyb/v/h -nav/h winConfig={ mBounds=Rect(0, 0 - 1200, 2670) mAppBounds=Rect(0,
 0 - 1200, 2670) mMaxBounds=Rect(0, 0 - 1200, 2670) mDisplayRotation=ROTATION_0 mWindowingMode=fullscreen mActivityType
=standard mAlwaysOnTop=undefined mRotation=ROTATION_0 mInSplitScreen=false letterBoxed=false foScaled=false isSpecificE
mbedded=false embeddingScale=1.0} s.1 fontWeightAdjustment=0/d/o themeChanged=0 themeChangedFlags=0 display=0 extraData
 = Bundle[{}] screenType=0/o}
01-26 18:07:33.614 22621 28683 I ContentCatcher: SettingTrigger : register screen QA status observer com.miui.permcente
r.privacymanager.SpecialPermissionInterceptActivity
01-26 18:07:33.615 22621 22621 I ViewRootImpl: com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermis
sionInterceptActivity use first frame acceleration
01-26 18:07:33.615 22621 28683 I ContentCatcher: SettingTrigger : register global collect status observer com.miui.perm
center.privacymanager.SpecialPermissionInterceptActivity
01-26 18:07:33.616 22621 28683 I ContentCatcher: SettingTrigger : registerUIAgentListener com.miui.permcenter.privacyma
nager.SpecialPermissionInterceptActivity, uid:1000
01-26 18:07:33.617  2580  7885 D CoreBackPreview: Window{1414bab u0 com.miui.securitycenter/com.miui.permcenter.privacy
manager.SpecialPermissionInterceptActivity}: Setting back callback OnBackInvokedCallbackInfo{mCallback=android.window.I
OnBackInvokedCallback$Stub$Proxy@70cb6b4, mPriority=0, mIsAnimationCallback=false, mOverrideBehavior=0}
01-26 18:07:33.617 10066 28830 I travelService_ActivityObserverUtils: activityResumed ComponentInfo{com.miui.securityce
nter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity}
01-26 18:07:33.617 23005 23069 D AppUpdateXmsfLauncher: activityResumed intent : Intent { cmp=com.miui.securitycenter/c
om.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity (has extras) }
01-26 18:07:33.619 10066 28830 I ActivityObserverUtil: activityResumed ComponentInfo{com.miui.securitycenter/com.miui.p
ermcenter.privacymanager.SpecialPermissionInterceptActivity}
01-26 18:07:33.619 10066 28830 I TopActivityMonitor: send TopActivityChangeEvent=TopActivityChangeEvent(prev=null, curr
ent=ComponentInfo{com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity},appCha
nged=true)
01-26 18:07:33.619  4698  4698 D RecentsImpl: mActivityStateObserver com.miui.permcenter.privacymanager.SpecialPermissi
onInterceptActivity
01-26 18:07:33.619  4698  4698 W RecentsImpl: updateGestureWindowVisibleImpl, className=com.miui.permcenter.privacymana
ger.SpecialPermissionInterceptActivity
01-26 18:07:33.621  2580  8785 I BLASTSyncEngine: SyncGroup 37401:  Unfinished container:ActivityRecord{49032128 u0 com
.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22052}
01-26 18:07:33.621  2580  8785 I BLASTSyncEngine: isActivitySyncFinished isSyncFinished false ActivityRecord{49032128 u
0 com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22052}
01-26 18:07:33.622  6697  7105 D MiuiDecorationController: relayout::taskId=22052, visible=true, bounds=Rect(0, 0 - 120
0, 2670), focused=true, oldImmersive=false, newImmersive=false, windowMode=1, activityType=1, displayId=0, baseActivity
=ComponentInfo{com.android.settings/com.android.settings.accessibility.MiuiAccessibilitySettingsActivity}, topActivity=
ComponentInfo{com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity}, callers=c
om.android.wm.shell.multitasking.miuimultiwinswitch.miuiwindowdecor.decoration.MiuiDecorationController.relayout:4 com.
android.wm.shell.multitasking.miuimultiwinswitch.miuiwindowdecor.MulWinSwitchDecorViewModel.onTaskInfoChanged:18 com.an
droid.wm.shell.multitasking.common.taskmanager.MultiTaskingTaskListener.onTaskInfoChanged:47 com.android.wm.shell.Shell
TaskOrganizer.onTaskInfoChanged:136 android.window.TaskOrganizer$1.lambda$onTaskInfoChanged$6:300
01-26 18:07:33.622  2580  8785 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:07:33.624  2580  8785 D WindowManager: updateSystemBarAttributes displayId: 16777216 Window{1414bab u0 com.miu
i.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} winAppearance=LIGHT_STATUS_BARS
 LIGHT_NAVIGATION_BARS
01-26 18:07:33.624  2580  8785 D WindowManager: wms.Input focus has changed to Window{1414bab u0 com.miui.securitycente
r/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} display=0 updateInputWindows = true
01-26 18:07:33.625  2580  7885 E TransitionChain:    ChangeInfo{fff03b5 container=ActivityRecord{49032128 u0 com.miui.s
ecuritycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22052} flags=0x0}
01-26 18:07:33.625  2580  7885 E TransitionChain:    ChangeInfo{fff03b5 container=ActivityRecord{49032128 u0 com.miui.s
ecuritycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22052} flags=0x0}
01-26 18:07:33.628  6697  6697 D DynamicIslandController: onTopActivityChanged: ComponentInfo{com.miui.securitycenter/c
om.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity}
01-26 18:07:33.628  6697  6697 D DynamicIslandWindowViewController: onTopActivityChange: topActivity ComponentInfo{com.
miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity}, inSmallWindow false, isSupp
ortPip false, isFocus true fullScreenPkg com.miui.securitycenter
01-26 18:07:33.638  2580  7885 D WindowManager: wms.finishDrawingLocked: mDrawState=COMMIT_DRAW_PENDING Window{1414bab
u0 com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} in Surface(name=com.m
iui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity#787548)/@0x34cd2dd
01-26 18:07:33.639  2580  3188 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:07:33.639  2580  3188 D WindowManager: wms.commitFinishDrawingLocked: mDrawState=READY_TO_SHOW Window{1414bab
u0 com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} in Surface(name=com.m
iui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity#787548)/@0x34cd2dd
01-26 18:07:33.640  2580  3188 D WindowManager: wms.commitFinishDrawingLocked: mDrawState=READY_TO_SHOW Window{1414bab
u0 com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} in Surface(name=com.m
iui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity#787548)/@0x34cd2dd
01-26 18:07:33.640  2580  3188 I WindowManager: wms.showSurfaceRobustly mWin:Window{1414bab u0 com.miui.securitycenter/
com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} in Surface(name=com.miui.securitycenter/com.miui
.permcenter.privacymanager.SpecialPermissionInterceptActivity#787548)/@0x34cd2dd
01-26 18:07:33.640  2580  3188 D WindowManager: Final targets: [ChangeInfo{fff03b5 container=ActivityRecord{49032128 u0
 com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22052} flags=0x0}]
01-26 18:07:33.641  2580  3188 D WindowManager: Calling onTransitionReady info={id=37401 t=OPEN f=0x4000000 trk=0 r=[0@
Point(0, 0)] c=[{m=OPEN f=TRANSLUCENT|FILLS_TASK leash=Surface(name=ActivityRecord{49032128 u0 com.miui.securitycenter/
com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22052}#787546)/@0x88b883e sb=Rect(0, 0 - 0, 0) e
b=Rect(0, 0 - 1200, 2670) epz=Point(1200, 2670) d=-1->0 r=-1->0:-1 windowMode=0->1 bc=ffeaeef3 component=com.miui.secur
itycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity opt={t=FROM_STYLE mUserId=0} v=trueMiui
ChangeImpl {miuiEmbedding:false, scale:1.0}}] mk=[false] noAni=[false] df=[false] rsa=[false] oa=[true] nc=[0] ho=[fals
e] hla=[false] iSync=[-1]}, mToken=Token{1342276 TransitionRecord{d8e2943 id=37401 type=OPEN flags=0x0 c=[
01-26 18:07:33.641  2580  3188 D WindowManager:    ChangeInfo{fff03b5 container=ActivityRecord{49032128 u0 com.miui.sec
uritycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22052} flags=0x0}
01-26 18:07:33.641  2580  5933 D BarFollowAnimation: disableBarAnimation change={m=OPEN f=TRANSLUCENT|FILLS_TASK leash=
Surface(name=ActivityRecord{49032128 u0 com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInt
erceptActivity t22052}#787546)/@0x88b883e sb=Rect(0, 0 - 0, 0) eb=Rect(0, 0 - 1200, 2670) epz=Point(1200, 2670) d=-1->0
 r=-1->0:-1 windowMode=0->1 bc=ffeaeef3 component=com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPer
missionInterceptActivity opt={t=FROM_STYLE mUserId=0} v=trueMiuiChangeImpl {miuiEmbedding:false, scale:1.0}} 0 1
01-26 18:07:33.642  2580  3188 I SmartPower: Miui monitor launch : com.miui.securitycenter/com.miui.permcenter.privacym
anager.SpecialPermissionInterceptActivity  transitionTime:77  launchTime:74  launchFrom:com.android.settings  launchTyp
e:2
01-26 18:07:33.642  2580  3188 I Perf[AppStartScene]: onSceneChangeNotify:Bundle[{renderThreadId=28671, pid=22621, uid=
1000, name=com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity, type=1, begin=false, state=1, process
Name=com.miui.securitycenter, packageName=com.miui.securitycenter}]
01-26 18:07:33.642  2580  3188 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:07:33.643  2580  3188 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:07:33.650  2580  2804 I ActivityTaskManager: Displayed com.miui.securitycenter/com.miui.permcenter.privacymana
ger.SpecialPermissionInterceptActivity for user 0: +74ms
01-26 18:07:33.692  6697  6697 D KeyguardEditorHelper: onTopActivityMayChanged, topActivity=ComponentInfo{com.miui.secu
ritycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity}; mState=IDEL
01-26 18:07:33.696  4698  5467 D RecentsTaskLoader: reloadTasksData [TaskKey{id=22052, stackId=0, baseIntent=Intent { a
ct=android.settings.ACCESSIBILITY_SETTINGS flg=0x10000000 cmp=com.android.settings/.accessibility.MiuiAccessibilitySett
ingsActivity }, userId=0, lastActiveTime=2578969530, windowingMode=1, isThumbnailBlur=false, isAccessLocked=false, isSc
reening=false, topActivity=ComponentInfo{com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionIn
terceptActivity}, mHashCode=657874684}, title=瀹夊叏鏈嶅姟, titleDescription=瀹夊叏鏈嶅姟, bounds=null, isLaunchTarget=
false, isStackTask=true, isSystemApp=true, isDockable=true, baseActivity=ComponentInfo{com.android.settings/com.android
.settings.accessibility.MiuiAccessibilitySettingsActivity}, isLocked=false, mNeedHide=false, hasMultipleTasks=false, ct
i1Key=, cti2Key=]
01-26 18:07:33.723  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:33.730  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:33.731  2580  3188 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:07:33.731  2580  3188 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:07:33.739  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:33.747  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:33.755  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:33.763  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:33.772  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:33.780  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:33.787  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:33.795  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:33.804  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:33.812  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:33.820  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:33.829  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:33.837  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:33.845  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:33.853  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:33.862  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:33.870  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:33.878  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:33.887  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:33.895  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:33.903  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:33.912  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:33.920  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:33.928  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:33.937  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:33.945  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:33.954  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:33.962  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:33.970  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:34.024  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:34.099  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:34.643  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:34.650  2580  4938 I MIUIInput: [MotionEvent] publisher action=0x0, deviceId=7, 1505330628, channel '1414ba
b com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity'
01-26 18:07:34.650  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:34.651 22621 22621 I MIUIInput: [MotionEvent] ViewRootImpl windowName 'com.miui.securitycenter/com.miui.per
mcenter.privacymanager.SpecialPermissionInterceptActivity', { action=ACTION_DOWN, id[0]=0, pointerCount=1, eventTime=15
05330628, downTime=1505330628, phoneEventTime=18:07:34.640 } moveCount:0
01-26 18:07:34.715  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:34.726  2580  4938 I MIUIInput: [MotionEvent] publisher action=0x1, deviceId=7, 1505330712, channel '1414ba
b com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity'
01-26 18:07:34.727 22621 22621 I MIUIInput: [MotionEvent] ViewRootImpl windowName 'com.miui.securitycenter/com.miui.per
mcenter.privacymanager.SpecialPermissionInterceptActivity', { action=ACTION_UP, id[0]=0, pointerCount=1, eventTime=1505
330712, downTime=1505330628, phoneEventTime=18:07:34.725 } moveCount:0
01-26 18:07:35.141  2580  4938 I MIUIInput: [MotionEvent] publisher action=0x0, deviceId=7, 1505331127, channel '1414ba
b com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity'
01-26 18:07:35.145 22621 22621 I MIUIInput: [MotionEvent] ViewRootImpl windowName 'com.miui.securitycenter/com.miui.per
mcenter.privacymanager.SpecialPermissionInterceptActivity', { action=ACTION_DOWN, id[0]=0, pointerCount=1, eventTime=15
05331127, downTime=1505331127, phoneEventTime=18:07:35.140 } moveCount:0
01-26 18:07:35.196  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.203  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.211  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.219  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.228  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.233  2580  4938 I MIUIInput: [MotionEvent] publisher action=0x1, deviceId=7, 1505331219, channel '1414ba
b com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity'
01-26 18:07:35.233 22621 22621 I MIUIInput: [MotionEvent] ViewRootImpl windowName 'com.miui.securitycenter/com.miui.per
mcenter.privacymanager.SpecialPermissionInterceptActivity', { action=ACTION_UP, id[0]=0, pointerCount=1, eventTime=1505
331219, downTime=1505331127, phoneEventTime=18:07:35.231 } moveCount:0
01-26 18:07:35.237  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.245  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.254  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.262  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.269  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.278  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.286  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.294  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.303  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.311  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.319  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.329  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.336  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.344  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.352  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.361  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.369  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.378  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.386  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.395  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.402  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.410  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.419  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.427  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.436  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.444  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.452  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.460  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.469  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.476  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.485  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.493  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.502  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.510  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.518  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.526  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.535  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.543  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.551  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.560  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.568  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.577  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.585  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.593  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.603  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.610  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.619  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.626  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.634  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.643  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.652  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.660  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.668  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.677  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.685  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.693  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.701  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.710  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.719  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.727  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.735  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.743  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.812  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:35.820  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:36.642  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:36.650  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:36.666  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:37.036  2839  3264 I misight : OnEvent EventPrivacyCompliance event=916012002
01-26 18:07:37.651  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:37.675  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:37.740  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:38.654  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:38.704  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:38.770  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:39.653  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:39.661  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:39.726  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:40.657  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:40.698  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:40.709  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:41.661  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:41.729  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:41.796  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:42.660  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:42.692  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:42.760  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:43.666  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:43.717  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:43.791  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:44.675  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:44.685  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:44.706  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:07:48.867  2839  3264 I misight : OnEvent EventPrivacyCompliance event=915100002
01-26 18:08:01.559  2580  6178 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:01.560  2580  6178 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:08:01.565  2580  6980 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:01.566  2580  6980 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:08:01.582  2580  6980 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:01.582  2580  6980 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:08:01.588  2580  6980 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:01.588  2580  6980 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:08:01.604  2580  3188 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:01.605  2580  3188 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:08:01.612  2580  6980 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:01.612  2580  6980 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:08:01.619  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:300x668 blurRect:300x668
01-26 18:08:01.621  2580  6980 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:01.621  2580  6980 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:08:01.627  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:300x668 blurRect:300x668
01-26 18:08:01.627  2580  8785 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:01.627  2580  8785 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:08:01.635  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:300x668 blurRect:300x668
01-26 18:08:01.635  2580  8785 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:01.636  2580  8785 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:08:01.642  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:300x668 blurRect:300x668
01-26 18:08:01.643  2580  8785 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:01.644  2580  8785 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:08:01.651  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:300x668 blurRect:300x668
01-26 18:08:01.652  2580  8847 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:01.652  2580  8847 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:08:01.659  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:300x668 blurRect:300x668
01-26 18:08:01.660  2580  8785 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:01.660  2580  8785 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:08:01.667  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:300x668 blurRect:300x668
01-26 18:08:01.669  2580  8847 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:01.669  2580  8847 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:08:01.675  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:300x668 blurRect:300x668
01-26 18:08:01.677  2580  8847 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:01.678  2580  8847 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:08:01.684  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:300x668 blurRect:300x668
01-26 18:08:01.685  2580  8847 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:01.686  2580  8847 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:08:01.692  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:300x668 blurRect:300x668
01-26 18:08:01.694  2580  8847 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:01.694  2580  8847 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:08:01.701  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:300x668 blurRect:300x668
01-26 18:08:01.702  2580  8847 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:01.703  2580  8847 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:08:01.709  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:300x668 blurRect:300x668
01-26 18:08:01.710  2580  8847 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:01.710  2580  8847 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:08:01.717  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:300x668 blurRect:300x668
01-26 18:08:01.718  2580  8785 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:01.718  2580  8785 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:08:01.726  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:300x668 blurRect:300x668
01-26 18:08:01.727  2580  8847 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:01.727  2580  8847 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:08:01.734  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:300x668 blurRect:300x668
01-26 18:08:01.735  2580  8785 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:01.735  2580  8785 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:08:01.742  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:300x668 blurRect:300x668
01-26 18:08:01.744  2580  8847 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:01.744  2580  8847 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:08:01.750  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:300x668 blurRect:300x668
01-26 18:08:01.752  2580  8847 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:01.752  2580  8847 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:08:01.758  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:300x668 blurRect:300x668
01-26 18:08:01.760  2580  8847 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:01.761  2580  8847 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:08:01.767  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:300x668 blurRect:300x668
01-26 18:08:01.768  2580  8847 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:01.768  2580  8847 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:08:01.775  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:300x668 blurRect:300x668
01-26 18:08:01.807  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:300x668 blurRect:300x668
01-26 18:08:01.819  2580  8785 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:01.819  2580  8785 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:08:01.844  2580  8785 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:01.845  2580  8785 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:08:01.869  2580  8847 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:01.869  2580  8847 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:08:01.902  2580  8847 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:01.902  2580  8847 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:08:10.512  2580  8809 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:10.512  2580  8809 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:08:10.935  2580  8809 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:10.936  2580  8809 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:08:10.977  2580  8809 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:10.978  2580  8809 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:08:31.174  2580  4938 I MIUIInput: [MotionEvent] publisher action=0x0, deviceId=7, 1505387158, channel '1414ba
b com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity'
01-26 18:08:31.178 22621 22621 I MIUIInput: [MotionEvent] ViewRootImpl windowName 'com.miui.securitycenter/com.miui.per
mcenter.privacymanager.SpecialPermissionInterceptActivity', { action=ACTION_DOWN, id[0]=0, pointerCount=1, eventTime=15
05387158, downTime=1505387158, phoneEventTime=18:08:31.170 } moveCount:0
01-26 18:08:31.238  2580  4938 I MIUIInput: [MotionEvent] publisher action=0x1, deviceId=7, 1505387225, channel '1414ba
b com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity'
01-26 18:08:31.239 22621 22621 I MIUIInput: [MotionEvent] ViewRootImpl windowName 'com.miui.securitycenter/com.miui.per
mcenter.privacymanager.SpecialPermissionInterceptActivity', { action=ACTION_UP, id[0]=0, pointerCount=1, eventTime=1505
387225, downTime=1505387158, phoneEventTime=18:08:31.238 } moveCount:0
01-26 18:08:31.248  2580  6178 D WindowManager: Collecting in transition 37402: ActivityRecord{49032128 u0 com.miui.sec
uritycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22052 f}} init visibleRequested:true
 dc:Display{#0 state=ON size=1200x2670 ROTATION_0}
01-26 18:08:31.249  2580  6178 D MiuiFreeFormGestureController: deliverResultForFinishActivity resultTo: ActivityRecord
{105797065 u0 com.android.settings/.SubSettings t22052} resultFrom: ActivityRecord{49032128 u0 com.miui.securitycenter/
com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22052 f}} isInVideoOrGameScene: false intent: In
tent { act=miui.intent.action.SPECIAL_PERMISSIO_NINTERCEPT flg=0x10000000 xflg=0x4 pkg=com.miui.securitycenter cmp=com.
miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity (has extras) }
01-26 18:08:31.249  2580  6178 D WindowManager: Collecting in transition 37402: ActivityRecord{49032128 u0 com.miui.sec
uritycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22052 f}} init visibleRequested:true
 dc:Display{#0 state=ON size=1200x2670 ROTATION_0}
01-26 18:08:31.261  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:08:31.266  2580  6178 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:31.266  2580  6178 D BarFollowAnimation: isAppTaskBarOnTop win:Window{1414bab u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{fceb96f #22052 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{fceb96f #22052 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:08:31.268  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:08:31.269  2580  8847 D WindowManager:    ChangeInfo{2dd802d container=ActivityRecord{49032128 u0 com.miui.sec
uritycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22052 f}} flags=0x0}
01-26 18:08:31.270  2580  3188 D WindowManager: wms.Focus not requested for window=Window{1414bab u0 com.miui.securityc
enter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} because it has no surface or is not focusa
ble.
01-26 18:08:31.275 22621 28683 I ContentCatcher: SettingTrigger : unregister status observer com.miui.permcenter.privac
ymanager.SpecialPermissionInterceptActivity
01-26 18:08:31.275 22621 28683 I ContentCatcher: SettingTrigger : unregisterUIAgentListener com.miui.permcenter.privacy
manager.SpecialPermissionInterceptActivity, uid:1000
01-26 18:08:31.278  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:08:31.282  2580  8847 D WindowManager:    ChangeInfo{2dd802d container=ActivityRecord{49032128 u0 com.miui.sec
uritycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22052 f}} flags=0x0}
01-26 18:08:31.283  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:08:31.283  2580  8847 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:31.284  2580  8847 D WindowManager: Final targets: [ChangeInfo{2dd802d container=ActivityRecord{49032128 u0
 com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22052 f}} flags=0x0}]
01-26 18:08:31.285  2580  8847 D WindowManager: Calling onTransitionReady info={id=37402 t=CLOSE f=0x0 trk=0 r=[0@Point
(0, 0)] c=[{m=CLOSE f=TRANSLUCENT|FILLS_TASK leash=Surface(name=ActivityRecord{49032128 u0 com.miui.securitycenter/com.
miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22052}#787546)/@0x88b883e sb=Rect(0, 0 - 1200, 2670)
 eb=Rect(0, 0 - 1200, 2670) epz=Point(1200, 2670) d=0 bc=ffeaeef3 component=com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity opt={t=FROM_STYLE mUserId=0} v=falseMiuiChangeImpl {miuiEmbedding:fa
lse, scale:1.0}}] mk=[false] noAni=[false] df=[false] rsa=[false] oa=[true] nc=[0] ho=[false] hla=[false] iSync=[-1]},
mToken=Token{6868c8 TransitionRecord{9098744 id=37402 type=CLOSE flags=0x0 c=[
01-26 18:08:31.285  2580  8847 D WindowManager:    ChangeInfo{2dd802d container=ActivityRecord{49032128 u0 com.miui.sec
uritycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22052 f}} flags=0x0}
01-26 18:08:31.285  2580  5933 D BarFollowAnimation: disableBarAnimation change={m=CLOSE f=TRANSLUCENT|FILLS_TASK leash
=Surface(name=ActivityRecord{49032128 u0 com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionIn
terceptActivity t22052}#787546)/@0x88b883e sb=Rect(0, 0 - 1200, 2670) eb=Rect(0, 0 - 1200, 2670) epz=Point(1200, 2670)
d=0 bc=ffeaeef3 component=com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity
 opt={t=FROM_STYLE mUserId=0} v=falseMiuiChangeImpl {miuiEmbedding:false, scale:1.0}} 1 1
01-26 18:08:31.291  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:08:31.299  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:08:31.308  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:08:31.309  9029 18031 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
01-26 18:08:31.316  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:08:31.322 10066 24833 I TopActivityMonitor: send TopActivityChangeEvent=TopActivityChangeEvent(prev=ComponentI
nfo{com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity}, current=ComponentIn
fo{com.android.settings/com.android.settings.SubSettings},appChanged=true)
01-26 18:08:31.324  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:08:31.332  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:08:31.333  2580  3188 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{1414bab u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:08:31.340  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:08:31.349  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:08:31.357  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:08:31.365  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:08:31.374  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:08:31.379  2580  3188 I WindowManager: wms.hideSurface mWin:Window{1414bab u0 com.miui.securitycenter/com.miui
.permcenter.privacymanager.SpecialPermissionInterceptActivity} reason:(prepareSurfaceLocked) in Surface(name=com.miui.s
ecuritycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity#787548)/@0x34cd2dd
01-26 18:08:31.382  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787548 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:08:31.389  2580  8809 D CoreBackPreview: Window{1414bab u0 com.miui.securitycenter/com.miui.permcenter.privacy
manager.SpecialPermissionInterceptActivity}: Setting back callback null
01-26 18:08:34.213  9029 18030 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
01-26 18:08:35.859  9029 18031 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
01-26 18:08:37.142  9029 18031 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
01-26 18:08:40.688  9029 18030 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
01-26 18:08:40.694  2580  7885 I SecurityManagerService: privacy virtual display updated! size=1
01-26 18:08:40.779  6697  6697 W PrivacyNewController: MIUILOG onLocationActiveChanged active: false
01-26 18:08:40.779  6697  6697 I MiuiHomePrivacyController: onPromptInfoChanged: newMiuiPromptInfo=0, mLastMiuiPromptIn
fo=0, mShowPrivacy=false, mIsNewMiuiPromptInfo=false, mFirstUseLocationPrompt=true, mReducePromptInfo=false, mOnlyUpdat
eLocation=false, mLessLocationTip=1
01-26 18:08:40.779  6697  6697 I MiuiHomePrivacyController: onPromptInfoChanged: newMiuiPromptInfo=0, mLastMiuiPromptIn
fo=0, mShowPrivacy=false, mIsNewMiuiPromptInfo=false, mFirstUseLocationPrompt=true, mReducePromptInfo=false, mOnlyUpdat
eLocation=false, mLessLocationTip=1
01-26 18:08:40.779  6697  6697 I MiuiHomePrivacyController: onPromptInfoChanged: newMiuiPromptInfo=0, mLastMiuiPromptIn
fo=0, mShowPrivacy=false, mIsNewMiuiPromptInfo=false, mFirstUseLocationPrompt=true, mReducePromptInfo=false, mOnlyUpdat
eLocation=false, mLessLocationTip=1
01-26 18:08:40.779  6697  6697 I MiuiHomePrivacyController: onPromptInfoChanged: newMiuiPromptInfo=0, mLastMiuiPromptIn
fo=0, mShowPrivacy=false, mIsNewMiuiPromptInfo=false, mFirstUseLocationPrompt=true, mReducePromptInfo=false, mOnlyUpdat
eLocation=false, mLessLocationTip=1
01-26 18:08:40.779  6697  6697 D PrivacyNewController: Clear the last privacy type.
01-26 18:08:41.280  6697  6697 D PrivacyNewController: handleMessage:13
01-26 18:08:43.696  9029  9253 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
01-26 18:10:59.765  2839  3264 I misight : OnEvent EventPrivacyCompliance event=905001001
01-26 18:11:46.376  9029  9069 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
01-26 18:11:49.398  9029  9375 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
01-26 18:13:12.803  2839  3264 I misight : OnEvent EventPrivacyCompliance event=923120001
01-26 18:13:15.228  2580  8821 I SecurityManagerService: privacy virtual display updated! size=0
01-26 18:13:15.293  6697  6697 W PrivacyNewController: MIUILOG onLocationActiveChanged active: false
01-26 18:13:15.294  6697  6697 I MiuiHomePrivacyController: onPromptInfoChanged: newMiuiPromptInfo=0, mLastMiuiPromptIn
fo=0, mShowPrivacy=false, mIsNewMiuiPromptInfo=false, mFirstUseLocationPrompt=true, mReducePromptInfo=false, mOnlyUpdat
eLocation=false, mLessLocationTip=1
01-26 18:13:15.294  6697  6697 I MiuiHomePrivacyController: onPromptInfoChanged: newMiuiPromptInfo=0, mLastMiuiPromptIn
fo=0, mShowPrivacy=false, mIsNewMiuiPromptInfo=false, mFirstUseLocationPrompt=true, mReducePromptInfo=false, mOnlyUpdat
eLocation=false, mLessLocationTip=1
01-26 18:13:15.294  6697  6697 I MiuiHomePrivacyController: onPromptInfoChanged: newMiuiPromptInfo=0, mLastMiuiPromptIn
fo=0, mShowPrivacy=false, mIsNewMiuiPromptInfo=false, mFirstUseLocationPrompt=true, mReducePromptInfo=false, mOnlyUpdat
eLocation=false, mLessLocationTip=1
01-26 18:13:15.294  6697  6697 I MiuiHomePrivacyController: onPromptInfoChanged: newMiuiPromptInfo=0, mLastMiuiPromptIn
fo=0, mShowPrivacy=false, mIsNewMiuiPromptInfo=false, mFirstUseLocationPrompt=true, mReducePromptInfo=false, mOnlyUpdat
eLocation=false, mLessLocationTip=1
01-26 18:13:15.294  6697  6697 D PrivacyNewController: Clear the last privacy type.
01-26 18:13:15.614  9029  9359 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
01-26 18:13:15.794  6697  6697 D PrivacyNewController: handleMessage:13
01-26 18:13:16.708  2839  3264 I misight : OnEvent EventPrivacyCompliance event=923120001
01-26 18:13:17.621  9029  9102 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
01-26 18:13:20.634  9029  9361 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
01-26 18:13:27.842  9029 22521 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
01-26 18:13:28.483  2839  3264 I misight : OnEvent EventPrivacyCompliance event=902001003
01-26 18:13:30.865  9029  9399 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
01-26 18:13:36.604  2580 12541 D WindowManager: Collecting in transition 37419: ActivityRecord{250765547 u0 com.miui.se
curitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity init visibleRequested:false dc:null
01-26 18:13:36.610  2580 12541 D ActivityTaskManager: Not add starting window, the reason is: windowIsTranslucent = tru
e  windowIsFloating = false  this = ActivityRecord{250765547 u0 com.miui.securitycenter/com.miui.permcenter.privacymana
ger.SpecialPermissionInterceptActivity t22054}
01-26 18:13:36.610  2580 12541 D WindowManager: All the checks have been done, return STARTING_WINDOW_TYPE_NONE. this =
 ActivityRecord{250765547 u0 com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActiv
ity t22054}
01-26 18:13:36.610  2580 12541 I WindowManager: Try to add startingWindow type = STARTING_WINDOW_TYPE_NONE this = Activ
ityRecord{250765547 u0 com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t2
2054} mOccludesParent = false preAllowTaskSnapshot = true afterAllowTaskSnapshot = true newTask = false taskSwitch = fa
lse processRunning = true activityCreated = false activityAllDrawn = false isSnapshotCompatible = false snapshotRotatio
n = NaN resolvedTheme = 0 theme = 2131952752
01-26 18:13:36.612  2580 12541 D WindowManager: Collecting in transition 37419: ActivityRecord{250765547 u0 com.miui.se
curitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22054} init visibleRequested:false
dc:Display{#0 state=ON size=1200x2670 ROTATION_0}
01-26 18:13:36.613  2580 12541 I ActivityTaskManager: START u0 {act=miui.intent.action.SPECIAL_PERMISSIO_NINTERCEPT xfl
g=0x4 pkg=com.miui.securitycenter cmp=com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInter
ceptActivity (has extras)} with LAUNCH_SINGLE_TASK from uid 1000 from pid 28757 callingPackage com.android.settings (rr
=61477195) (BAL_ALLOW_VISIBLE_WINDOW) result code=0
01-26 18:13:36.613  2580 12541 I Perf[AppStartScene]: onSceneChangeNotify:Bundle[{renderThreadId=28671, applicationThre
ad=android.os.BinderProxy@e82b357, pid=22621, uid=1000, name=com.miui.permcenter.privacymanager.SpecialPermissionInterc
eptActivity, type=1, begin=true, state=1, processName=com.miui.securitycenter, packageName=com.miui.securitycenter, hom
eApp=false, appSwitch=true}]
01-26 18:13:36.615  6697  7105 I SoScStageCoordinator: Transition requested:TransitionRequestInfo { type = OPEN, trigge
rTask = TaskInfo{userId=0 taskId=22054 effectiveUid=1000 displayId=0 isRunning=true baseIntent=Intent { act=android.set
tings.ACCESSIBILITY_SETTINGS flg=0x10000000 cmp=com.android.settings/.accessibility.MiuiAccessibilitySettingsActivity }
 baseActivity=ComponentInfo{com.android.settings/com.android.settings.accessibility.MiuiAccessibilitySettingsActivity}
topActivity=ComponentInfo{com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity
} origActivity=null realActivity=ComponentInfo{com.android.settings/com.android.settings.accessibility.MiuiAccessibilit
ySettingsActivity} numActivities=4 lastActiveTime=2579332552 supportsSplitScreenMultiWindow=true mSoScDisplayMode=-1 su
pportsMultiWindow=true resizeMode=1 isResizeable=true minWidth=-1 minHeight=-1 defaultMinSize=200 token=WCT{android.win
dow.IWindowContainerToken$Stub$Proxy@67eafd9} topActivityType=1 pictureInPictureParams=null shouldDockBigOverlays=false
 launchIntoPipHostTaskId=-1 lastParentTaskIdBeforePip=-1 displayCutoutSafeInsets=Rect(0, 0 - 0, 0) topActivityInfo=Acti
vityInfo{85b2d9e com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} launchCookies=[] positionInPare
nt=Point(0, 0) parentTaskId=-1 isFocused=true isVisible=true isVisibleRequested=true isTopActivityNoDisplay=false isSle
eping=false locusId=null displayAreaFeatureId=1 isTopActivityTransparent=true isActivityStackTransparent=false lastNonF
ullscreenBounds=Rect(311, 778 - 889, 1978) capturedLink=null capturedLinkTimestamp=0 requestedVisibleTypes=-9 topActivi
tyRequestOpenInBrowserEducationTimestamp=0 appCompatTaskInfo=AppCompatTaskInfo { topActivityInSizeCompat=false eligible
ForLetterboxEducation= false topActivityInMiuiSizeCompat=false isLetterboxEducationEnabled= false isLetterboxDoubleTapE
nabled= false eligibleForUserAspectRatioButton= false topActivityBoundsLetterboxed= false isFromLetterboxDoubleTap= fal
se topActivityLetterboxVerticalPosition= -1 topActivityLetterboxHorizontalPosition= -1 topActivityLetterboxWidth=-1 top
ActivityLetterboxHeight=-1 topActivityAppBounds=Rect(0, 0 - 1200, 2670) isUserFullscreenOverrideEnabled=false isSystemF
ullscreenOverrideEnabled=false hasMinAspectRatioOverride=false topActivityLetterboxBounds=null cameraCompatTaskInfo=Cam
eraCompatTaskInfo { freeformCameraCompatMode=inactive}} isImmersive=false mTopActivityRequestOrientation=1 mStatusBarPa
rent=null mNavBarParent=null mBehindAppLockPkg=null mOriginatingUid=0 isEmbedded=false shouldBeVisible=true isCreatedBy
Organizer=false mIsCastMode=false mTopActivityMediaSize=null mTopActivityRecordName=ActivityRecord{250765547 u0 com.miu
i.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22054} mTopActivityOrientation=
-2 topActivityMainWindowFrame=null}, pipChange = null, remoteTransition = null, displayChange = null, flags = 0, debugI
d = 37419 } isSoScActive:false triggerTask:TaskInfo{userId=0 taskId=22054 effectiveUid=1000 displayId=0 isRunning=true
baseIntent=Intent { act=android.settings.ACCESSIBILITY_SETTINGS flg=0x10000000 cmp=com.android.settings/.accessibility.
MiuiAccessibilitySettingsActivity } baseActivity=ComponentInfo{com.android.settings/com.android.settings.accessibility.
MiuiAccessibilitySettingsActivity} topActivity=ComponentInfo{com.miui.securitycenter/com.miui.permcenter.privacymanager
.SpecialPermissionInterceptActivity} origActivity=null realActivity=ComponentInfo{com.android.settings/com.android.sett
ings.accessibility.MiuiAccessibilitySettingsActivity} numActivities=4 lastActiveTime=2579332552 supportsSplitScreenMult
iWindow=true mSoScDisplayMode=-1 supportsMultiWindow=true resizeMode=1 isResizeable=true minWidth=-1 minHeight=-1 defau
ltMinSize=200 token=WCT{android.window.IWindowContainerToken$Stub$Proxy@67eafd9} topActivityType=1 pictureInPicturePara
ms=null shouldDockBigOverlays=false launchIntoPipHostTaskId=-1 lastParentTaskIdBeforePip=-1 displayCutoutSafeInsets=Rec
t(0, 0 - 0, 0) topActivityInfo=ActivityInfo{85b2d9e co
01-26 18:13:36.615  2580 12541 D WindowManager:    ChangeInfo{ca80263 container=ActivityRecord{250765547 u0 com.miui.se
curitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22054} flags=0x0}
01-26 18:13:36.616  6697  7105 D MiuiDecorationController: relayout::taskId=22054, visible=true, bounds=Rect(0, 0 - 120
0, 2670), focused=true, oldImmersive=false, newImmersive=false, windowMode=1, activityType=1, displayId=0, baseActivity
=ComponentInfo{com.android.settings/com.android.settings.accessibility.MiuiAccessibilitySettingsActivity}, topActivity=
ComponentInfo{com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity}, callers=c
om.android.wm.shell.multitasking.miuimultiwinswitch.miuiwindowdecor.decoration.MiuiDecorationController.relayout:4 com.
android.wm.shell.multitasking.miuimultiwinswitch.miuiwindowdecor.MulWinSwitchDecorViewModel.onTaskInfoChanged:18 com.an
droid.wm.shell.multitasking.common.taskmanager.MultiTaskingTaskListener.onTaskInfoChanged:47 com.android.wm.shell.Shell
TaskOrganizer.onTaskInfoChanged:136 android.window.TaskOrganizer$1.lambda$onTaskInfoChanged$6:300
01-26 18:13:36.618  2580 10989 E TransitionChain:    ChangeInfo{ca80263 container=ActivityRecord{250765547 u0 com.miui.
securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22054} flags=0x0}
01-26 18:13:36.618  2580 10989 E TransitionChain:    ChangeInfo{ca80263 container=ActivityRecord{250765547 u0 com.miui.
securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22054} flags=0x0}
01-26 18:13:36.623  2580  6178 D WindowManager: Collecting in transition 37419: ActivityRecord{250765547 u0 com.miui.se
curitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22054} init visibleRequested:false
dc:Display{#0 state=ON size=1200x2670 ROTATION_0}
01-26 18:13:36.624  2580  6178 D WindowManager: Collecting in transition 37419: ActivityRecord{250765547 u0 com.miui.se
curitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22054} init visibleRequested:true d
c:Display{#0 state=ON size=1200x2670 ROTATION_0}
01-26 18:13:36.625  6697  7105 D MiuiDecorationController: relayout::taskId=22054, visible=true, bounds=Rect(0, 0 - 120
0, 2670), focused=true, oldImmersive=false, newImmersive=false, windowMode=1, activityType=1, displayId=0, baseActivity
=ComponentInfo{com.android.settings/com.android.settings.accessibility.MiuiAccessibilitySettingsActivity}, topActivity=
ComponentInfo{com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity}, callers=c
om.android.wm.shell.multitasking.miuimultiwinswitch.miuiwindowdecor.decoration.MiuiDecorationController.relayout:4 com.
android.wm.shell.multitasking.miuimultiwinswitch.miuiwindowdecor.MulWinSwitchDecorViewModel.onTaskInfoChanged:18 com.an
droid.wm.shell.multitasking.common.taskmanager.MultiTaskingTaskListener.onTaskInfoChanged:47 com.android.wm.shell.Shell
TaskOrganizer.onTaskInfoChanged:136 android.window.TaskOrganizer$1.lambda$onTaskInfoChanged$6:300
01-26 18:13:36.626  2580  6178 D WindowManager:    ChangeInfo{ca80263 container=ActivityRecord{250765547 u0 com.miui.se
curitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22054} flags=0x0}
01-26 18:13:36.628  2580  6178 I BLASTSyncEngine: SyncGroup 37419:  Unfinished container:ActivityRecord{250765547 u0 co
m.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22054}
01-26 18:13:36.628  2580  6178 I BLASTSyncEngine: isActivitySyncFinished isSyncFinished false ActivityRecord{250765547
u0 com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22054}
01-26 18:13:36.629  2580 10989 E TransitionChain:    ChangeInfo{ca80263 container=ActivityRecord{250765547 u0 com.miui.
securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22054} flags=0x0}
01-26 18:13:36.629  2580 10989 E TransitionChain:    ChangeInfo{ca80263 container=ActivityRecord{250765547 u0 com.miui.
securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22054} flags=0x0}
01-26 18:13:36.648 22621 22621 D WmSystemUiDebug: set navigation bar color,Alpha=1.0, RGB:247,247,247 caller:com.miui.c
ommon.base.BaseActivity.setNaviBarColor:22 com.miui.common.base.BaseActivity.onCreate:79 com.miui.permcenter.privacyman
ager.model.InterceptBaseActivity.onCreate:1 android.app.Activity.performCreate:9380 android.app.Activity.performCreate:
9323 android.app.Instrumentation.callActivityOnCreate:1537 android.app.ActivityThread.performLaunchActivity:4685 androi
d.app.ActivityThread.handleLaunchActivity:4932 android.app.servertransaction.LaunchActivityItem.execute:222 android.app
.servertransaction.TransactionExecutor.executeNonLifecycleItem:136
01-26 18:13:36.652 22621 22621 D Activity: Activity = ActivityInfo{8a68fd2 com.miui.permcenter.privacymanager.SpecialPe
rmissionInterceptActivity}, Resume onConfigurationChanged = {1.0190147 460mcc11mnc [zh_CN] ldltr sw400dp w400dp h890dp
489dpi nrml long hdr widecg port finger -keyb/v/h -nav/h winConfig={ mBounds=Rect(0, 0 - 1200, 2670) mAppBounds=Rect(0,
 0 - 1200, 2670) mMaxBounds=Rect(0, 0 - 1200, 2670) mDisplayRotation=ROTATION_0 mWindowingMode=fullscreen mActivityType
=standard mAlwaysOnTop=undefined mRotation=ROTATION_0 mInSplitScreen=false letterBoxed=false foScaled=false isSpecificE
mbedded=false embeddingScale=1.0} s.1 fontWeightAdjustment=0/d/o themeChanged=0 themeChangedFlags=0 display=0 extraData
 = Bundle[{}] screenType=0/o}
01-26 18:13:36.653 22621 28683 I ContentCatcher: SettingTrigger : register screen QA status observer com.miui.permcente
r.privacymanager.SpecialPermissionInterceptActivity
01-26 18:13:36.654 22621 22621 I ViewRootImpl: com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermis
sionInterceptActivity use first frame acceleration
01-26 18:13:36.655 22621 28683 I ContentCatcher: SettingTrigger : register global collect status observer com.miui.perm
center.privacymanager.SpecialPermissionInterceptActivity
01-26 18:13:36.655 22621 28683 I ContentCatcher: SettingTrigger : registerUIAgentListener com.miui.permcenter.privacyma
nager.SpecialPermissionInterceptActivity, uid:1000
01-26 18:13:36.656  2580 10989 D CoreBackPreview: Window{954e88d u0 com.miui.securitycenter/com.miui.permcenter.privacy
manager.SpecialPermissionInterceptActivity}: Setting back callback OnBackInvokedCallbackInfo{mCallback=android.window.I
OnBackInvokedCallback$Stub$Proxy@7faa88e, mPriority=0, mIsAnimationCallback=false, mOverrideBehavior=0}
01-26 18:13:36.657 10066 24833 I travelService_ActivityObserverUtils: activityResumed ComponentInfo{com.miui.securityce
nter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity}
01-26 18:13:36.657 10066 24833 I ActivityObserverUtil: activityResumed ComponentInfo{com.miui.securitycenter/com.miui.p
ermcenter.privacymanager.SpecialPermissionInterceptActivity}
01-26 18:13:36.657 10066 24833 I TopActivityMonitor: send TopActivityChangeEvent=TopActivityChangeEvent(prev=null, curr
ent=ComponentInfo{com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity},appCha
nged=true)
01-26 18:13:36.658  4698  4698 D RecentsImpl: mActivityStateObserver com.miui.permcenter.privacymanager.SpecialPermissi
onInterceptActivity
01-26 18:13:36.658  4698  4698 W RecentsImpl: updateGestureWindowVisibleImpl, className=com.miui.permcenter.privacymana
ger.SpecialPermissionInterceptActivity
01-26 18:13:36.659 23005 23717 D AppUpdateXmsfLauncher: activityResumed intent : Intent { cmp=com.miui.securitycenter/c
om.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity (has extras) }
01-26 18:13:36.661  2580  6980 I BLASTSyncEngine: SyncGroup 37419:  Unfinished container:ActivityRecord{250765547 u0 co
m.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22054}
01-26 18:13:36.662  2580  6980 I BLASTSyncEngine: isActivitySyncFinished isSyncFinished false ActivityRecord{250765547
u0 com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22054}
01-26 18:13:36.662  6697  7105 D MiuiDecorationController: relayout::taskId=22054, visible=true, bounds=Rect(0, 0 - 120
0, 2670), focused=true, oldImmersive=false, newImmersive=false, windowMode=1, activityType=1, displayId=0, baseActivity
=ComponentInfo{com.android.settings/com.android.settings.accessibility.MiuiAccessibilitySettingsActivity}, topActivity=
ComponentInfo{com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity}, callers=c
om.android.wm.shell.multitasking.miuimultiwinswitch.miuiwindowdecor.decoration.MiuiDecorationController.relayout:4 com.
android.wm.shell.multitasking.miuimultiwinswitch.miuiwindowdecor.MulWinSwitchDecorViewModel.onTaskInfoChanged:18 com.an
droid.wm.shell.multitasking.common.taskmanager.MultiTaskingTaskListener.onTaskInfoChanged:47 com.android.wm.shell.Shell
TaskOrganizer.onTaskInfoChanged:136 android.window.TaskOrganizer$1.lambda$onTaskInfoChanged$6:300
01-26 18:13:36.663  2580  6980 D BarFollowAnimation: isAppTaskBarOnTop win:Window{954e88d u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{66e9d5b #22054 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{66e9d5b #22054 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:13:36.665  2580  6980 D WindowManager: updateSystemBarAttributes displayId: 16777216 Window{954e88d u0 com.miu
i.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} winAppearance=LIGHT_STATUS_BARS
 LIGHT_NAVIGATION_BARS
01-26 18:13:36.665  2580  6980 D WindowManager: wms.Input focus has changed to Window{954e88d u0 com.miui.securitycente
r/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} display=0 updateInputWindows = true
01-26 18:13:36.667  2580  6179 E TransitionChain:    ChangeInfo{ca80263 container=ActivityRecord{250765547 u0 com.miui.
securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22054} flags=0x0}
01-26 18:13:36.667  2580  6179 E TransitionChain:    ChangeInfo{ca80263 container=ActivityRecord{250765547 u0 com.miui.
securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22054} flags=0x0}
01-26 18:13:36.667  6697  6697 D DynamicIslandController: onTopActivityChanged: ComponentInfo{com.miui.securitycenter/c
om.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity}
01-26 18:13:36.667  6697  6697 D DynamicIslandWindowViewController: onTopActivityChange: topActivity ComponentInfo{com.
miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity}, inSmallWindow false, isSupp
ortPip false, isFocus true fullScreenPkg com.miui.securitycenter
01-26 18:13:36.678  2580 10989 D WindowManager: wms.finishDrawingLocked: mDrawState=COMMIT_DRAW_PENDING Window{954e88d
u0 com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} in Surface(name=com.m
iui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity#787836)/@0xe938af
01-26 18:13:36.679  2580  3188 D BarFollowAnimation: isAppTaskBarOnTop win:Window{954e88d u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{66e9d5b #22054 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{66e9d5b #22054 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:13:36.679  2580  3188 D WindowManager: wms.commitFinishDrawingLocked: mDrawState=READY_TO_SHOW Window{954e88d
u0 com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} in Surface(name=com.m
iui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity#787836)/@0xe938af
01-26 18:13:36.680  2580  3188 D WindowManager: wms.commitFinishDrawingLocked: mDrawState=READY_TO_SHOW Window{954e88d
u0 com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} in Surface(name=com.m
iui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity#787836)/@0xe938af
01-26 18:13:36.680  2580  3188 I WindowManager: wms.showSurfaceRobustly mWin:Window{954e88d u0 com.miui.securitycenter/
com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} in Surface(name=com.miui.securitycenter/com.miui
.permcenter.privacymanager.SpecialPermissionInterceptActivity#787836)/@0xe938af
01-26 18:13:36.680  2580  3188 D WindowManager: Final targets: [ChangeInfo{ca80263 container=ActivityRecord{250765547 u
0 com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22054} flags=0x0}]
01-26 18:13:36.682  2580  3188 D WindowManager: Calling onTransitionReady info={id=37419 t=OPEN f=0x4000000 trk=0 r=[0@
Point(0, 0)] c=[{m=OPEN f=TRANSLUCENT|FILLS_TASK leash=Surface(name=ActivityRecord{250765547 u0 com.miui.securitycenter
/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22054}#787834)/@0xe4934e1 sb=Rect(0, 0 - 0, 0)
eb=Rect(0, 0 - 1200, 2670) epz=Point(1200, 2670) d=-1->0 r=-1->0:-1 windowMode=0->1 bc=ffeaeef3 component=com.miui.secu
ritycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity opt={t=FROM_STYLE mUserId=0} v=trueMiu
iChangeImpl {miuiEmbedding:false, scale:1.0}}] mk=[false] noAni=[false] df=[false] rsa=[false] oa=[true] nc=[0] ho=[fal
se] hla=[false] iSync=[-1]}, mToken=Token{f5fab9f TransitionRecord{4e29c3a id=37419 type=OPEN flags=0x0 c=[
01-26 18:13:36.682  2580  3188 D WindowManager:    ChangeInfo{ca80263 container=ActivityRecord{250765547 u0 com.miui.se
curitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22054} flags=0x0}
01-26 18:13:36.682  2580  5933 D BarFollowAnimation: disableBarAnimation change={m=OPEN f=TRANSLUCENT|FILLS_TASK leash=
Surface(name=ActivityRecord{250765547 u0 com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionIn
terceptActivity t22054}#787834)/@0xe4934e1 sb=Rect(0, 0 - 0, 0) eb=Rect(0, 0 - 1200, 2670) epz=Point(1200, 2670) d=-1->
0 r=-1->0:-1 windowMode=0->1 bc=ffeaeef3 component=com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPe
rmissionInterceptActivity opt={t=FROM_STYLE mUserId=0} v=trueMiuiChangeImpl {miuiEmbedding:false, scale:1.0}} 0 1
01-26 18:13:36.682  2580  3188 I SmartPower: Miui monitor launch : com.miui.securitycenter/com.miui.permcenter.privacym
anager.SpecialPermissionInterceptActivity  transitionTime:80  launchTime:78  launchFrom:com.android.settings  launchTyp
e:2
01-26 18:13:36.682  2580  3188 I Perf[AppStartScene]: onSceneChangeNotify:Bundle[{renderThreadId=28671, pid=22621, uid=
1000, name=com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity, type=1, begin=false, state=1, process
Name=com.miui.securitycenter, packageName=com.miui.securitycenter}]
01-26 18:13:36.683  2580  3188 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{954e88d u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:13:36.683  2580  3188 D BarFollowAnimation: isAppTaskBarOnTop win:Window{954e88d u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{66e9d5b #22054 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{66e9d5b #22054 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:13:36.686  2580  2804 I ActivityTaskManager: Displayed com.miui.securitycenter/com.miui.permcenter.privacymana
ger.SpecialPermissionInterceptActivity for user 0: +78ms
01-26 18:13:36.698  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.704  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.712  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.720  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.728  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.730  6697  6697 D KeyguardEditorHelper: onTopActivityMayChanged, topActivity=ComponentInfo{com.miui.secu
ritycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity}; mState=IDEL
01-26 18:13:36.733  4698  5467 D RecentsTaskLoader: reloadTasksData [TaskKey{id=22054, stackId=0, baseIntent=Intent { a
ct=android.settings.ACCESSIBILITY_SETTINGS flg=0x10000000 cmp=com.android.settings/.accessibility.MiuiAccessibilitySett
ingsActivity }, userId=0, lastActiveTime=2579332566, windowingMode=1, isThumbnailBlur=false, isAccessLocked=false, isSc
reening=false, topActivity=ComponentInfo{com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionIn
terceptActivity}, mHashCode=657934266}, title=瀹夊叏鏈嶅姟, titleDescription=瀹夊叏鏈嶅姟, bounds=null, isLaunchTarget=
false, isStackTask=true, isSystemApp=true, isDockable=true, baseActivity=ComponentInfo{com.android.settings/com.android
.settings.accessibility.MiuiAccessibilitySettingsActivity}, isLocked=false, mNeedHide=false, hasMultipleTasks=false, ct
i1Key=, cti2Key=]
01-26 18:13:36.737  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.745  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.754  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.763  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.770  2580  3188 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{954e88d u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:13:36.770  2580  3188 D BarFollowAnimation: isAppTaskBarOnTop win:Window{954e88d u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{66e9d5b #22054 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{66e9d5b #22054 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:13:36.771  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.780  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.789  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.797  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.805  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.813  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.820  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.829  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.837  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.845  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.853  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.862  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.870  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.878  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.887  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.895  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.903  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.912  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.920  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.928  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.936  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.945  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.953  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.961  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.969  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.978  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.986  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:36.994  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:37.003  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:37.048  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:37.117  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:37.499  2580  4938 I MIUIInput: [MotionEvent] publisher action=0x0, deviceId=7, 1505693484, channel '954e88
d com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity'
01-26 18:13:37.502 22621 22621 I MIUIInput: [MotionEvent] ViewRootImpl windowName 'com.miui.securitycenter/com.miui.per
mcenter.privacymanager.SpecialPermissionInterceptActivity', { action=ACTION_DOWN, id[0]=0, pointerCount=1, eventTime=15
05693484, downTime=1505693484, phoneEventTime=18:13:37.497 } moveCount:0
01-26 18:13:37.615  2580  4938 I MIUIInput: [MotionEvent] publisher action=0x1, deviceId=7, 1505693601, channel '954e88
d com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity'
01-26 18:13:37.616 22621 22621 I MIUIInput: [MotionEvent] ViewRootImpl windowName 'com.miui.securitycenter/com.miui.per
mcenter.privacymanager.SpecialPermissionInterceptActivity', { action=ACTION_UP, id[0]=0, pointerCount=1, eventTime=1505
693601, downTime=1505693484, phoneEventTime=18:13:37.614 } moveCount:0
01-26 18:13:37.638  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:37.662  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:37.672  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:37.730  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:37.799  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.089  2580  4938 I MIUIInput: [MotionEvent] publisher action=0x0, deviceId=7, 1505694074, channel '954e88
d com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity'
01-26 18:13:38.090 22621 22621 I MIUIInput: [MotionEvent] ViewRootImpl windowName 'com.miui.securitycenter/com.miui.per
mcenter.privacymanager.SpecialPermissionInterceptActivity', { action=ACTION_DOWN, id[0]=0, pointerCount=1, eventTime=15
05694074, downTime=1505694074, phoneEventTime=18:13:38.086 } moveCount:0
01-26 18:13:38.163  2580  4938 I MIUIInput: [MotionEvent] publisher action=0x1, deviceId=7, 1505694149, channel '954e88
d com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity'
01-26 18:13:38.165 22621 22621 I MIUIInput: [MotionEvent] ViewRootImpl windowName 'com.miui.securitycenter/com.miui.per
mcenter.privacymanager.SpecialPermissionInterceptActivity', { action=ACTION_UP, id[0]=0, pointerCount=1, eventTime=1505
694149, downTime=1505694074, phoneEventTime=18:13:38.162 } moveCount:0
01-26 18:13:38.188  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.195  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.203  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.211  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.219  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.228  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.237  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.244  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.252  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.261  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.269  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.278  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.287  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.295  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.302  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.311  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.319  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.327  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.336  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.345  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.352  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.361  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.369  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.377  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.385  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.393  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.402  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.411  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.419  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.427  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.435  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.443  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.453  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.460  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.469  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.477  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.486  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.493  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.502  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.510  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.518  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.527  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.535  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.544  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.552  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.561  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.568  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.576  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.586  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.593  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.602  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.610  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.619  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.627  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.634  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.643  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.652  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.661  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.668  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.676  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.702  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:38.770  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:39.675  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:39.726  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:39.794  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:40.681  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:40.687  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:40.756  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:41.679  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:41.712  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:41.787  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:42.684  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:42.742  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:42.809  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:43.690  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:43.698  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:43.764  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:44.687  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:44.727  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:44.794  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:45.691  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:45.699  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:45.707  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:46.696  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:46.755  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:46.829  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:46.967  2580  4938 I MIUIInput: [MotionEvent] publisher action=0x0, deviceId=7, 1505702953, channel '954e88
d com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity'
01-26 18:13:46.971 22621 22621 I MIUIInput: [MotionEvent] ViewRootImpl windowName 'com.miui.securitycenter/com.miui.per
mcenter.privacymanager.SpecialPermissionInterceptActivity', { action=ACTION_DOWN, id[0]=0, pointerCount=1, eventTime=15
05702953, downTime=1505702953, phoneEventTime=18:13:46.965 } moveCount:0
01-26 18:13:47.029  2580  4938 I MIUIInput: [MotionEvent] publisher action=0x1, deviceId=7, 1505703012, channel '954e88
d com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity'
01-26 18:13:47.030 22621 22621 I MIUIInput: [MotionEvent] ViewRootImpl windowName 'com.miui.securitycenter/com.miui.per
mcenter.privacymanager.SpecialPermissionInterceptActivity', { action=ACTION_UP, id[0]=0, pointerCount=1, eventTime=1505
703012, downTime=1505702953, phoneEventTime=18:13:47.024 } moveCount:0
01-26 18:13:47.039  2580  4627 D WindowManager: Collecting in transition 37420: ActivityRecord{250765547 u0 com.miui.se
curitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22054 f}} init visibleRequested:tru
e dc:Display{#0 state=ON size=1200x2670 ROTATION_0}
01-26 18:13:47.040  2580  4627 D MiuiFreeFormGestureController: deliverResultForFinishActivity resultTo: ActivityRecord
{61477195 u0 com.android.settings/.SubSettings t22054} resultFrom: ActivityRecord{250765547 u0 com.miui.securitycenter/
com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22054 f}} isInVideoOrGameScene: false intent: In
tent { act=miui.intent.action.SPECIAL_PERMISSIO_NINTERCEPT flg=0x10000000 xflg=0x4 pkg=com.miui.securitycenter cmp=com.
miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity (has extras) }
01-26 18:13:47.040  2580  4627 D WindowManager: Collecting in transition 37420: ActivityRecord{250765547 u0 com.miui.se
curitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22054 f}} init visibleRequested:tru
e dc:Display{#0 state=ON size=1200x2670 ROTATION_0}
01-26 18:13:47.044  2580  4627 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{954e88d u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:13:47.044  2580  4627 D BarFollowAnimation: isAppTaskBarOnTop win:Window{954e88d u0 com.miui.securitycenter/co
m.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} (mCurrentTopTask:Task{66e9d5b #22054 type=standard
 A=1000:com.android.settings} : true); (mAssociatedTask:Task{66e9d5b #22054 type=standard A=1000:com.android.settings}
: true); (mHomeTask:Task{9a773a0 #21949 type=home} : false); mReparentToOriginParent:false mLastAppBarVisible:true mLas
tHomeBarVisible:false
01-26 18:13:47.046  2580  6980 D WindowManager:    ChangeInfo{7426bb9 container=ActivityRecord{250765547 u0 com.miui.se
curitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22054 f}} flags=0x0}
01-26 18:13:47.047  2580  3188 D WindowManager: wms.Focus not requested for window=Window{954e88d u0 com.miui.securityc
enter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} because it has no surface or is not focusa
ble.
01-26 18:13:47.050 22621 28683 I ContentCatcher: SettingTrigger : unregister status observer com.miui.permcenter.privac
ymanager.SpecialPermissionInterceptActivity
01-26 18:13:47.051 22621 28683 I ContentCatcher: SettingTrigger : unregisterUIAgentListener com.miui.permcenter.privacy
manager.SpecialPermissionInterceptActivity, uid:1000
01-26 18:13:47.054  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:47.059  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:47.060  2580 10989 D WindowManager:    ChangeInfo{7426bb9 container=ActivityRecord{250765547 u0 com.miui.se
curitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22054 f}} flags=0x0}
01-26 18:13:47.061  2580 10989 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{954e88d u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:13:47.062  2580 10989 D WindowManager: Final targets: [ChangeInfo{7426bb9 container=ActivityRecord{250765547 u
0 com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22054 f}} flags=0x0}]
01-26 18:13:47.063  2580 10989 D WindowManager: Calling onTransitionReady info={id=37420 t=CLOSE f=0x0 trk=0 r=[0@Point
(0, 0)] c=[{m=CLOSE f=TRANSLUCENT|FILLS_TASK leash=Surface(name=ActivityRecord{250765547 u0 com.miui.securitycenter/com
.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22054}#787834)/@0xe4934e1 sb=Rect(0, 0 - 1200, 2670
) eb=Rect(0, 0 - 1200, 2670) epz=Point(1200, 2670) d=0 bc=ffeaeef3 component=com.miui.securitycenter/com.miui.permcente
r.privacymanager.SpecialPermissionInterceptActivity opt={t=FROM_STYLE mUserId=0} v=falseMiuiChangeImpl {miuiEmbedding:f
alse, scale:1.0}}] mk=[false] noAni=[false] df=[false] rsa=[false] oa=[true] nc=[0] ho=[false] hla=[false] iSync=[-1]},
 mToken=Token{6300b57 TransitionRecord{ff980bd id=37420 type=CLOSE flags=0x0 c=[
01-26 18:13:47.063  2580 10989 D WindowManager:    ChangeInfo{7426bb9 container=ActivityRecord{250765547 u0 com.miui.se
curitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity t22054 f}} flags=0x0}
01-26 18:13:47.063  2580  5933 D BarFollowAnimation: disableBarAnimation change={m=CLOSE f=TRANSLUCENT|FILLS_TASK leash
=Surface(name=ActivityRecord{250765547 u0 com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionI
nterceptActivity t22054}#787834)/@0xe4934e1 sb=Rect(0, 0 - 1200, 2670) eb=Rect(0, 0 - 1200, 2670) epz=Point(1200, 2670)
 d=0 bc=ffeaeef3 component=com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivit
y opt={t=FROM_STYLE mUserId=0} v=falseMiuiChangeImpl {miuiEmbedding:false, scale:1.0}} 1 1
01-26 18:13:47.068  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:47.077  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:47.083  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:47.089  9029 13259 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
01-26 18:13:47.093  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:47.100  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:47.118  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:47.122 10066 24833 I TopActivityMonitor: send TopActivityChangeEvent=TopActivityChangeEvent(prev=ComponentI
nfo{com.miui.securitycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity}, current=ComponentIn
fo{com.android.settings/com.android.settings.SubSettings},appChanged=true)
01-26 18:13:47.126  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:47.134  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:47.140  2580  3188 D BarFollowAnimation: pairAppearanceRegionAndWin win=Window{954e88d u0 com.miui.security
center/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity} 8
01-26 18:13:47.142  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:47.151  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:47.157  2580  3188 I WindowManager: wms.hideSurface mWin:Window{954e88d u0 com.miui.securitycenter/com.miui
.permcenter.privacymanager.SpecialPermissionInterceptActivity} reason:(prepareSurfaceLocked) in Surface(name=com.miui.s
ecuritycenter/com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity#787836)/@0xe938af
01-26 18:13:47.160  2084  2246 D RenderEngine: drawLayersInternal layername:com.miui.securitycenter/com.miui.permcenter
.privacymanager.SpecialPermissionInterceptActivity#787836 regionblurRadius:90 blurInput:1200x2670 blurRect:1200x2670
01-26 18:13:47.162  2580  4627 D CoreBackPreview: Window{954e88d u0 com.miui.securitycenter/com.miui.permcenter.privacy
manager.SpecialPermissionInterceptActivity}: Setting back callback null
01-26 18:13:50.100  9029  9252 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
01-26 18:13:50.155  9029  9102 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
01-26 18:13:55.602  9029 13259 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
01-26 18:13:55.613  2580  6980 I SecurityManagerService: privacy virtual display updated! size=1
01-26 18:13:55.688  6697  6697 W PrivacyNewController: MIUILOG onLocationActiveChanged active: false
01-26 18:13:55.689  6697  6697 I MiuiHomePrivacyController: onPromptInfoChanged: newMiuiPromptInfo=0, mLastMiuiPromptIn
fo=0, mShowPrivacy=false, mIsNewMiuiPromptInfo=false, mFirstUseLocationPrompt=true, mReducePromptInfo=false, mOnlyUpdat
eLocation=false, mLessLocationTip=1
01-26 18:13:55.689  6697  6697 I MiuiHomePrivacyController: onPromptInfoChanged: newMiuiPromptInfo=0, mLastMiuiPromptIn
fo=0, mShowPrivacy=false, mIsNewMiuiPromptInfo=false, mFirstUseLocationPrompt=true, mReducePromptInfo=false, mOnlyUpdat
eLocation=false, mLessLocationTip=1
01-26 18:13:55.689  6697  6697 I MiuiHomePrivacyController: onPromptInfoChanged: newMiuiPromptInfo=0, mLastMiuiPromptIn
fo=0, mShowPrivacy=false, mIsNewMiuiPromptInfo=false, mFirstUseLocationPrompt=true, mReducePromptInfo=false, mOnlyUpdat
eLocation=false, mLessLocationTip=1
01-26 18:13:55.689  6697  6697 I MiuiHomePrivacyController: onPromptInfoChanged: newMiuiPromptInfo=0, mLastMiuiPromptIn
fo=0, mShowPrivacy=false, mIsNewMiuiPromptInfo=false, mFirstUseLocationPrompt=true, mReducePromptInfo=false, mOnlyUpdat
eLocation=false, mLessLocationTip=1
01-26 18:13:55.689  6697  6697 D PrivacyNewController: Clear the last privacy type.
01-26 18:13:56.188  6697  6697 D PrivacyNewController: handleMessage:13
01-26 18:13:58.619  9029  9555 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
01-26 18:14:12.751  9029  9361 I AppPredictPrivacyLaunchManager: getLauncherHomeAppList size: 167
PS C:\Users\Administrator> adb logcat MainService:D *:S
PS C:\Users\Administrator> adb logcat PrivacyModeService:D *:S