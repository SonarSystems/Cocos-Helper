/*
 Copyright (C) 2015 Sonar Systems - All Rights Reserved
 You may use, distribute and modify this code under the
 terms of the MIT license
 
 Any external frameworks used have their own licenses and
 should be followed as such.
*/
//
//  SCHSettings.h
//  Sonar Cocos Helper
//
//  Created by Sonar Systems on 03/03/2015.
//
//

#ifndef __SCHSettings_h__
#define __SCHSettings_h__

#define SCH_IS_iADS_ENABLED false // iAd.framework
#define SCH_IS_AD_MOB_ENABLED false // AdSupport.framework, AudioToolbox.framework, AVFoundation.framework, CoreGraphics.framework, CoreMedia.framework, CoreTelephony.framework, EventKit.framework, EventKitUI.framework, MessageUI.framework, StoreKit.framework, SystemConfiguration.framework
#define SCH_IS_CHARTBOOST_ENABLED false // StoreKit.framework, Foundation.framework, CoreGraphics.framework, UIKit.framework
#define SCH_IS_REVMOB_ENABLED false // SystemConfiguration.framework, StoreKit.framework, MediaPlayer.framework, AdSupport.framework
#define SCH_IS_SOCIAL_ENABLED false // Social.framework
#define SCH_IS_GAME_CENTER_ENABLED false // Social.framework, GameKit.framework
//#define SCH_IS_EVERYPLAY_ENABLED false // AdSupport (iOS 6+, set to Optional link for pre-iOS 6 compatibility).framework, AssetsLibrary.framework, AudioToolbox.framework, AVFoundation.framework, CoreGraphics.framework, CoreImage (iOS 5+, set to Optional link for pre-iOS 5 compatibility).framework, CoreMedia.framework, CoreVideo.framework, Foundation.framework, MessageUI.framework, MobileCoreServices.framework, OpenGLES.framework, QuartzCore.framework, Security.framework, Social (iOS 6+, set to Optional link for pre-iOS 6 compatibility).framework, StoreKit.framework, SystemConfiguration.framework, Twitter (iOS 5+, set to Optional link for pre-iOS 5 compatibility).framework, UIKit.framework
#define SCH_IS_MOPUB_ENABLED false // AdSupport.framework, CoreGraphics.framework, CoreLocation.framework, CoreTelephony.framework, EventKit.framework, EventKitUI.framework, Foundation.framework, GameController.framework, GameKit.framework, MediaPlayer.framework, MessageUI.framework, QuartzCore.framework, StoreKit.framework†, SystemConfiguration.framework, UIKit.framework
// MOPUB also needs these Linker flags
// -ObjC
// -fobjc-arc to every MoPub file
#define SCH_IS_GOOGLE_ANALYTICS_ENABLED false // CoreData.framework, SystemConfiguration.framework, libz.dylib, libsqlite3.dylib, libGoogleAnalyticsServices.a, libAdIdAccess.a, AdSupport.framework, iAd.framework, GameController.framework
// GOOGLE ANALYTICS also needs these Linker flags for IDFA
// -framework AdSupport
// -force_load "${SRCROOT}/GoogleAnalyticsFramework/libAdIdAccess.a"
#define SCH_IS_ADCOLONY_ENABLED false // AdColony.framework, AdSupport.framework (Set to Optional), AudioToolbox.framework, AVFoundation.framework, CoreGraphics.framework, CoreMedia.framework, CoreTelephony.framework, EventKit.framework, EventKitUI.framework, GameController.framework, libsqlite3.dylib, libz.1.2.5.dylib, MediaPlayer.framework, MessageUI.framework, QuartzCore.framework, Social.framework (Set to Optional), StoreKit.framework (Set to Optional), SystemConfiguration.framework, WebKit.framework (Set to Optional)
// ADCOLONY also needs these Linker flags
// -ObjC
// -fobjc-arc
#define SCH_IS_VUNGLE_ENABLED false // AdSupport.framework, AudioToolbox.framework, AVFoundation.framework, CFNetwork.framework, CoreGraphics.framework, CoreMedia.framework, Foundation.framework, libz.dylib, libsqlite3.dylib, MediaPlayer.framework, QuartzCore.framework, StoreKit.framework, SystemConfiguration.framework, UIKit.framework
#define SCH_IS_WECHAT_ENABLED false // libWeCharSDK.a, WXApi.h, WXApiObject.h, SystemConfiguration.framework, libz.dylib, libsqlite3.0.dylib
#define SCH_IS_NOTIFICATIONS_ENABLED false


#define SCH_ADCOLONY_APP_ID @""
#define SCH_ADCOLONY_ZONE_ID @""

#define SCH_VUNGLE_ID @""

#define SCH_MOPUB_BANNER_AD_UNIT @""
#define SCH_MOPUB_INTERSTITIAL_AD_UNIT @""

#define SCH_CHARTBOOST_APP_ID @""
#define SCH_CHARTBOOST_APP_SIGNATURE @""

#define SCH_REVMOB_MEDIA_ID @""

#define SCH_AD_MOB_TOP_BANNER_AD_UNIT_ID @""
#define SCH_AD_MOB_BOTTOM_BANNER_AD_UNIT_ID @""
#define SCH_AD_MOB_FULLSCREEN_AD_UNIT_ID @""
#define SCH_AD_MOB_TEST_DEVICE @""

#define SCH_GOOGLE_ANALYTICS_TRACKING_ID @""

//#define SCH_EVERYPLAY_CLIENT_ID @""
//#define SCH_EVERYPLAY_CLIENT_SECRET @""


#define SCH_WECHAT_APP_ID @""

#endif
