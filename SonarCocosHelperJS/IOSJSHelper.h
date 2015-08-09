/*
 Copyright (C) 2015 Sonar Systems - All Rights Reserved
 You may use, distribute and modify this code under the
 terms of the MIT license
 
 Any external frameworks used have their own licenses and
 should be followed as such.
 */
//
//  IOSJSHelper.h
//  Sonar Cocos Helper
//
//  Created by Sonar Systems on 03/03/2015.
//
//

#import "SCHSettings.h"


@interface IOSJSHelper : NSObject


+( void )Setup;

#if SCH_IS_iADS_ENABLED == true
+( void )showiAdBanner:( NSNumber * ) position;
+( void )hideiAdBanner;
#endif

#if SCH_IS_REVMOB_ENABLED == true
+( void )showRevMobBanner;
+( void )hideRevMobBanner;
+( void )showRevMobFullScreenAd;
+( void )showRevMobPopupAd;
#endif

#if SCH_IS_CHARTBOOST_ENABLED == true
+( void )showChartboostFullScreenAd;
+( void )showChartboostMoreApps;
+( void )showChartboostVideoAd;
#endif

#if SCH_IS_SOCIAL_ENABLED == true
+( void )shareViaFacebook:( NSString * ) message andContent: ( NSString * ) imagePath;
+( void )shareViaTwitter:( NSString * ) message andContent: ( NSString * ) imagePath;
+( void )shareWithString:( NSString *) message andContent: ( NSString * ) imagePath;
#endif

#if SCH_IS_GAME_CENTER_ENABLED == true
+( void )gameCenterLogin;
+( void )gameCenterShowLeaderboard;
+( void )gameCenterShowAchievements;
+( void )gameCenterSubmitScore:( NSNumber * )scoreNumber andLeaderboard: ( NSString * )leaderboardID;
+( void )gameCenterUnlockAchievement:( NSString * )achievementID andPercentage:( NSNumber * )percent;
+( void )gameCenterResetPlayerAchievements;
#endif

#if SCH_IS_AD_MOB_ENABLED == true
+( void )showAdMobBanner:( NSNumber * ) position;
+( void )hideAdMobBanner:( NSNumber * ) position;
+( void )showAdMobFullscreenAd;
#endif

#if SCH_IS_MOPUB_ENABLED == true
+( void )showMopubBanner;
+( void )hideMopubBanner;

+( void )showMoPubFullscreenAd;
#endif

#if SCH_IS_EVERYPLAY_ENABLED == true
+( void )setupEveryplay;
+( void )showEveryplay;
+( void )recordEveryplayVideo;
+( void )playLastEveryplayVideoRecording;
#endif

#if SCH_IS_GOOGLE_ANALYTICS_ENABLED == true
+( void )setGAScreenName:( NSString * )screenString;
+( void )setGADispatchInterval:( NSNumber * )dispatchInterval;
+( void )sendGAEvent:( NSString * ) category andAction: ( NSString * ) action andLabel: ( NSString * ) label;
#endif

#if SCH_IS_ADCOLONY_ENABLED == true
+( void )showVideoAC:( NSNumber * )withPreOp andPostOp: ( NSNumber * ) withPostOp;
#endif

#if SCH_IS_VUNGLE_ENABLED == true
+( void )showVideoVungle:( NSNumber * )isIncentivised;
#endif

#if SCH_IS_WECHAT_ENABLED == true
+( void )sendTextMsgToWeChat:( NSString * ) msgString;
+( void )sendThumbImage:( NSString * ) thumbImgPath andShareImgToWeChat:( NSString * ) imgPath;
+( void )sendLinkWithThumbImg:( NSString * ) thumbImgPath andMsgTitle:( NSString * ) msgTitle andMsgDescription:( NSString * ) msgDes andURLToWeChat:( NSString * ) url;
+( void )sendMusicContentWithTitle:( NSString * ) msgTitle andDescription:( NSString * )msgDescription andThumbImg:( NSString * ) thumbImg andMusicUrl:( NSString * ) musicUrl andMusicDataUrl:( NSString * ) musicDataURL;
+( void )sendVideoContentWithTitle:( NSString * ) msgTitle andDescription:( NSString * )msgDescription andThumbImg:( NSString * ) thumbImg andVideoUrl:( NSString * ) videoUrl;
#endif

#if SCH_IS_NOTIFICATIONS_ENABLED == true
+( void )scheduleLocalNotification:( NSNumber * )delay andNotificationText:( NSString * )textToDisplay andNotificationTitle:( NSString * )notificationTitle;
+( void )scheduleLocalNotification:( NSNumber * )delay andNotificationText:( NSString * )textToDisplay andNotificationTitle:( NSString * )notificationTitle andNotificationAction:( NSString * )notificationAction;
+( void )scheduleLocalNotification:( NSNumber * )delay andNotificationText:( NSString * )textToDisplay andNotificationTitle:( NSString * )notificationTitle andRepeatInterval:( NSNumber * )repeatInterval;
+( void )scheduleLocalNotification:( NSNumber * )delay andNotificationText:( NSString * )textToDisplay andNotificationTitle:( NSString * )notificationTitle andNotificationAction:( NSString * )notificationAction andRepeatInterval:( NSNumber * )repeatInterval;
+( void )unscheduleAllLocalNotifications;
+( void )unscheduleLocalNotification:( NSString * )notificationTitle;
#endif

@end