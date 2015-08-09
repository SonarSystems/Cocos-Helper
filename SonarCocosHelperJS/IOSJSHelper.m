/*
 Copyright (C) 2015 Sonar Systems - All Rights Reserved
 You may use, distribute and modify this code under the
 terms of the MIT license
 
 Any external frameworks used have their own licenses and
 should be followed as such.
 */
//
//  IOSJSHelper.m
//  Sonar Cocos Helper
//
//  Created by Sonar Systems on 03/03/2015.
//

#import "IOSJSHelper.h"
#import "IOSHelper.h"
#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

@implementation IOSJSHelper

+( void )Setup
{
    [[IOSHelper instance] initialise];
}

#if SCH_IS_REVMOB_ENABLED == true
+( void )showRevMobBanner
{
    [[IOSHelper instance] showRevMobBanner];
}

+( void )hideRevMobBanner
{
    [[IOSHelper instance] hideRevMobBanner];
}

+( void )showRevMobFullScreenAd
{
    [[IOSHelper instance] showRevMobFullScreenAd];
}

+( void )showRevMobPopupAd
{
    [[IOSHelper instance] showRevMobPopupAd];
}
#endif

#if SCH_IS_iADS_ENABLED == true
+( void )showiAdBanner:( NSNumber * ) position
{
    [[IOSHelper instance] showiAdBanner:position.intValue];
}

+( void )hideiAdBanner
{
    [[IOSHelper instance] hideiAdBannerPermanently];
}
#endif

#if SCH_IS_CHARTBOOST_ENABLED == true
+( void )showChartboostFullScreenAd
{
    [[IOSHelper instance] showChartboostFullScreenAd];
}

+( void )showChartboostMoreApps
{
    [[IOSHelper instance] showChartboostMoreApps];
}

+( void )showChartboostVideoAd
{
    [[IOSHelper instance] showChartboostVideoAd];
}
#endif

#if SCH_IS_SOCIAL_ENABLED == true
+( void )shareViaFacebook:( NSString * ) message andContent: ( NSString * ) imagePath
{
    [[IOSHelper instance] shareViaFacebook:message: imagePath];
}

+( void )shareViaTwitter:( NSString * ) message andContent: ( NSString * ) imagePath
{
    [[IOSHelper instance] shareViaTwitter:message: imagePath];
}

+( void )shareWithString:( NSString * ) message andContent: ( NSString * ) imagePath
{
    [[IOSHelper instance] shareWithString: message: imagePath];
}
#endif

#if SCH_IS_GAME_CENTER_ENABLED == true
+( void )gameCenterLogin
{
    [[IOSHelper instance] gameCenterLogin];
}

+( void )gameCenterShowLeaderboard
{
    [[IOSHelper instance] gameCenterShowLeaderboard];
}

+( void )gameCenterShowAchievements
{
    [[IOSHelper instance] gameCenterShowAchievements];
}

+( void )gameCenterSubmitScore:( NSNumber * )scoreNumber andLeaderboard: ( NSString * )leaderboardID
{
    [[IOSHelper instance] gameCenterSubmitScore:scoreNumber.intValue
                                 andLeaderboard:leaderboardID];
}

+( void )gameCenterUnlockAchievement:( NSString * )achievementID andPercentage:( NSNumber * )percent
{
    [[IOSHelper instance] gameCenterUnlockAchievement:achievementID andPercentage:percent.floatValue];
}

+( void )gameCenterResetPlayerAchievements
{
    [[IOSHelper instance] gameCenterResetPlayerAchievements];
}
#endif

// AdSupport.framework (iOS 6+, set to Optional link for pre-iOS 6 compatibility), AudioToolbox.framework, AVFoundation.framework, CoreGraphics.framework, CoreLocation.framework, CoreTelephony.framework, EventKit.framework, EventKitUI.framework, GameController.framework, iAd.framework, MediaPlayer.framework, MessageUI.framework, MobileCoreServices.framework, PassKit.framework (iOS 6+, set to Optional link for pre-iOS 6 compatibility), QuartzCore.framework, Social.framework (iOS 6+, set to Optional link for pre-iOS 6 compatibility), StoreKit.framework (iOS 6+, set to Optional link for pre-iOS 6 compatibility), SystemConfiguration.framework, Twitter.framework (iOS 6+, set to Optional link for pre-iOS 6 compatibility)

#if SCH_IS_AD_MOB_ENABLED == true
+( void )showAdMobBanner:( NSNumber * ) position
{
    [[IOSHelper instance] showAdMobBanner:position.intValue];
}

+( void )hideAdMobBanner:( NSNumber * ) position
{
    [[IOSHelper instance] hideAdMobBanner:position.intValue];
}

+( void )showAdMobFullscreenAd
{
    [[IOSHelper instance] showAdMobFullscreenAd];
}
#endif

#if SCH_IS_MOPUB_ENABLED == true
+( void )showMopubBanner
{
    [[IOSHelper instance] showMopubBanner];
}

+( void )hideMopubBanner
{
    [[IOSHelper instance] hideMopubBanner];
}

+( void )showMoPubFullscreenAd
{
    [[IOSHelper instance] showMoPubFullscreenAd];
}
#endif

#if SCH_IS_EVERYPLAY_ENABLED == true
+( void )setupEveryplay
{
    [[IOSHelper instance] setupEveryplay];
}

+( void )showEveryplay
{
    [[IOSHelper instance] showEveryplay];
}

+( void )recordEveryplayVideo
{
    [[IOSHelper instance] recordEveryplayVideo];
}

+( void )playLastEveryplayVideoRecording
{
    [[IOSHelper instance] playLastEveryplayVideoRecording];
}
#endif

#if SCH_IS_GOOGLE_ANALYTICS_ENABLED == true
+( void )setGAScreenName:( NSString * )screenString
{
    [[IOSHelper instance] setGAScreenName:screenString];
}

+( void )setGADispatchInterval:( NSNumber * ) dispatchInterval
{
    [[IOSHelper instance] setGADispatchInterval:dispatchInterval.intValue];
}

+( void )sendGAEvent:( NSString * ) category andAction: ( NSString * ) action andLabel: ( NSString * ) label
{
    [[IOSHelper instance] sendGAEvent:category: action: label];
}
#endif

#if SCH_IS_ADCOLONY_ENABLED == true
+( void )showVideoAC:( NSNumber * )withPreOp andPostOp: ( NSNumber * ) withPostOp
{
    [[IOSHelper instance] showV4VCAC:withPreOp.intValue andPostOp: withPostOp.intValue];
}
#endif

#if SCH_IS_VUNGLE_ENABLED == true
+( void )showVideoVungle:( NSNumber * )isIncentivised
{
    [[IOSHelper instance] showV4VCV:isIncentivised.intValue];
}
#endif

#if SCH_IS_WECHAT_ENABLED == true
+( void )sendTextMsgToWeChat:( NSString * ) msgString
{
    [[IOSHelper instance] sendTextContentToWeChat: msgString];
}

+( void )sendThumbImage:( NSString * ) thumbImgPath andShareImgToWeChat:( NSString * ) imgPath
{
    [[IOSHelper instance] sendThumbImage:thumbImgPath andShareImgToWeChat:imgPath];
}

+( void )sendLinkWithThumbImg:( NSString * ) thumbImgPath andMsgTitle:( NSString * ) msgTitle andMsgDescription:( NSString * ) msgDes andURLToWeChat:( NSString * ) url
{
    [[IOSHelper instance] sendLinkWithThumbImg:thumbImgPath andMsgTitle:msgTitle andMsgDescription:msgDes andURLToWeChat:url];
}

+( void )sendMusicContentWithTitle:( NSString * ) msgTitle andDescription:( NSString * )msgDescription andThumbImg:( NSString * ) thumbImg andMusicUrl:( NSString * ) musicUrl andMusicDataUrl:( NSString * ) musicDataURL
{
    [[IOSHelper instance] sendMusicContentWithTitle:msgTitle andDescription:msgDescription andThumbImg:thumbImg andMusicUrl:musicUrl andMusicDataUrl:musicDataURL];
}

+( void )sendVideoContentWithTitle:( NSString * ) msgTitle andDescription:( NSString * )msgDescription andThumbImg:( NSString * ) thumbImg andVideoUrl:( NSString * ) videoUrl
{
    [[IOSHelper instance] sendVideoContentWithTitle:msgTitle andDescription:msgDescription andThumbImg:thumbImg andVideoUrl:videoUrl];
}
#endif

#if SCH_IS_NOTIFICATIONS_ENABLED == true
+( void )scheduleLocalNotification:( NSNumber * )delay andNotificationText:( NSString * ) textToDisplay andNotificationTitle:( NSString * )notificationTitle
{
    [[IOSHelper instance] scheduleLocalNotification:delay.floatValue andNotificationText:textToDisplay andNotificationTitle:notificationTitle];
}

+( void )scheduleLocalNotification:( NSNumber * )delay andNotificationText:( NSString * )textToDisplay andNotificationTitle:( NSString * )notificationTitle andNotificationAction:( NSString * )notificationAction
{
    [[IOSHelper instance] scheduleLocalNotification:delay.floatValue andNotificationText:textToDisplay andNotificationTitle:notificationTitle andNotificationAction:notificationAction];
}

+( void )scheduleLocalNotification:( NSNumber * )delay andNotificationText:( NSString * )textToDisplay andNotificationTitle:( NSString * )notificationTitle andRepeatInterval:( NSNumber * )repeatInterval
{
    [[IOSHelper instance] scheduleLocalNotification:delay.floatValue andNotificationText:textToDisplay andNotificationTitle:notificationTitle andRepeatInterval:repeatInterval.intValue];
}

+( void )scheduleLocalNotification:( NSNumber * )delay andNotificationText:( NSString * )textToDisplay andNotificationTitle:( NSString * )notificationTitle andNotificationAction:( NSString * )notificationAction andRepeatInterval:( NSNumber * )repeatInterval
{
    [[IOSHelper instance] scheduleLocalNotification:delay.floatValue andNotificationText:textToDisplay andNotificationTitle:notificationTitle andNotificationAction:notificationAction andRepeatInterval:repeatInterval.intValue];
}

+( void )unscheduleAllLocalNotifications
{
    [[IOSHelper instance] unscheduleAllLocalNotifications];
}

+( void )unscheduleLocalNotification:( NSString * )notificationTitle
{
    [[IOSHelper instance] unscheduleLocalNotification:notificationTitle];
}
#endif

@end