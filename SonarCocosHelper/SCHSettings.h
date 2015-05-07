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

#define SCH_IS_MOPUB_ENABLED true // AdSupport.framework,CoreGraphics.framework,CoreLocation.framework,CoreTelephony.framework,EventKit.framework,EventKitUI.framework,Foundation.framework,MediaPlayer.framework,QuartzCore.framework,StoreKit.framework†,SystemConfiguration.framework,UIKit.framework

#define SCH_MOPUB_BANNER_AD_UNIT @""
#define SCH_MOPUB_LAUNCH_INTERSTITIAL_AD_UNIT @""
#define SCH_MOPUB_ENDLEVEL_INTERSTITIAL_AD_UNIT @""

#define SCH_CHARTBOOST_APP_ID @""
#define SCH_CHARTBOOST_APP_SIGNATURE @""

#define SCH_REVMOB_MEDIA_ID @""

#define SCH_AD_MOB_BANNER_AD_UNIT_ID @""
#define SCH_AD_MOB_FULLSCREEN_AD_UNIT_ID @""
#define SCH_AD_MOB_TEST_DEVICE @""

//#define SCH_EVERYPLAY_CLIENT_ID @""
//#define SCH_EVERYPLAY_CLIENT_SECRET @""

#endif
