/*
 Copyright (C) 2015 Sonar Systems - All Rights Reserved
 You may use, distribute and modify this code under the
 terms of the MIT license
 
 Any external frameworks used have their own licenses and
 should be followed as such.
 */
//
//  IOSCPPHelper.mm
//  Sonar Cocos Helper
//
//  Created by Sonar Systems on 03/03/2015.
//
//

#include "IOSCPPHelper.h"
#import "IOSHelper.h"


void IOSCPPHelper::Setup( )
{
    [[IOSHelper instance] initialise];
}

#if SCH_IS_REVMOB_ENABLED == true
void IOSCPPHelper::showRevMobBanner( )
{
    [[IOSHelper instance] showRevMobBanner];
}

void IOSCPPHelper::hideRevMobBanner( )
{
    [[IOSHelper instance] hideRevMobBanner];
}

void IOSCPPHelper::showRevMobFullScreenAd( )
{
    [[IOSHelper instance] showRevMobFullScreenAd];
}

void IOSCPPHelper::showRevMobPopupAd( )
{
    [[IOSHelper instance] showRevMobPopupAd];
}
#endif

#if SCH_IS_iADS_ENABLED == true
void IOSCPPHelper::showiAdBanner( int position )
{
    [[IOSHelper instance] showiAdBanner:position];
}

void IOSCPPHelper::hideiAdBanner( )
{
    [[IOSHelper instance] hideiAdBannerPermanently];
}
#endif

#if SCH_IS_CHARTBOOST_ENABLED == true
void IOSCPPHelper::showChartboostFullScreenAd( )
{
    [[IOSHelper instance] showChartboostFullScreenAd];
}

void IOSCPPHelper::showChartboostMoreApps( )
{
    [[IOSHelper instance] showChartboostMoreApps];
}

void IOSCPPHelper::showChartboostVideoAd( )
{
    [[IOSHelper instance] showChartboostVideoAd];
}
#endif

#if SCH_IS_SOCIAL_ENABLED == true
void IOSCPPHelper::shareViaFacebook( __String message, __String thumbnailPath )
{
    [[IOSHelper instance] shareViaFacebook:[NSString stringWithCString:message.getCString( ) encoding:NSUTF8StringEncoding]: [NSString stringWithCString:thumbnailPath.getCString( ) encoding:NSUTF8StringEncoding]];
}

void IOSCPPHelper::shareViaTwitter( __String message, __String thumbnailPath )
{
    [[IOSHelper instance] shareViaTwitter:[NSString stringWithCString:message.getCString( ) encoding:NSUTF8StringEncoding]: [NSString stringWithCString:thumbnailPath.getCString( ) encoding:NSUTF8StringEncoding]];
}

void IOSCPPHelper::shareWithString( __String message, __String thumbnailPath )
{
    [[IOSHelper instance] shareWithString:[NSString stringWithCString:message.getCString( ) encoding:NSUTF8StringEncoding]: [NSString stringWithCString:thumbnailPath.getCString( ) encoding:NSUTF8StringEncoding]];
}
#endif

#if SCH_IS_GAME_CENTER_ENABLED == true
void IOSCPPHelper::gameCenterLogin( )
{
    [[IOSHelper instance] gameCenterLogin];
}

void IOSCPPHelper::gameCenterShowLeaderboard( )
{
    [[IOSHelper instance] gameCenterShowLeaderboard];
}

void IOSCPPHelper::gameCenterShowAchievements( )
{
    [[IOSHelper instance] gameCenterShowAchievements];
}

void IOSCPPHelper::gameCenterSubmitScore( int scoreNumber, __String leaderboardID )
{
    [[IOSHelper instance] gameCenterSubmitScore:scoreNumber
                                 andLeaderboard:[NSString stringWithCString:leaderboardID.getCString( ) encoding:NSUTF8StringEncoding]];
}

void IOSCPPHelper::gameCenterUnlockAchievement( __String achievementID, float percent )
{
    [[IOSHelper instance] gameCenterUnlockAchievement:[NSString stringWithCString:achievementID.getCString( ) encoding:NSUTF8StringEncoding] andPercentage:percent];
}

void IOSCPPHelper::gameCenterResetPlayerAchievements( )
{
    [[IOSHelper instance] gameCenterResetPlayerAchievements];
}
#endif

#if SCH_IS_AD_MOB_ENABLED == true
void IOSCPPHelper::showAdMobBanner( int position )
{
    [[IOSHelper instance] showAdMobBanner:position];
}

void IOSCPPHelper::hideAdMobBanner( int position )
{
    [[IOSHelper instance] hideAdMobBanner:position];
}

void IOSCPPHelper::requestAdMobFullScreenAd()
{
    [[IOSHelper instance] requestAdMobFullscreenAd];
}
void IOSCPPHelper::showAdMobFullscreenAd( )
{
    [[IOSHelper instance] showAdMobFullscreenAd];
}
#endif

#if SCH_IS_MOPUB_ENABLED == true
void IOSCPPHelper::showMopubBanner( )
{
    [[IOSHelper instance] showMopubBanner];
}

void IOSCPPHelper::hideMopubBanner( )
{
    [[IOSHelper instance] hideMopubBanner];
}

void IOSCPPHelper::showMoPubFullscreenAd( )
{
    [[IOSHelper instance] showMoPubFullscreenAd];
}
#endif


#if SCH_IS_EVERYPLAY_ENABLED == true
void IOSCPPHelper::setupEveryplay( )
{
    [[IOSHelper instance] setupEveryplay];
}

void IOSCPPHelper::showEveryplay( )
{
    [[IOSHelper instance] showEveryplay];
}

void IOSCPPHelper::recordEveryplayVideo( )
{
    [[IOSHelper instance] recordEveryplayVideo];
}

void IOSCPPHelper::playLastEveryplayVideoRecording( )
{
    [[IOSHelper instance] playLastEveryplayVideoRecording];
}
#endif

#if SCH_IS_GOOGLE_ANALYTICS_ENABLED == true
void IOSCPPHelper::setGAScreenName( __String screenName )
{
    [[IOSHelper instance] setGAScreenName:[NSString stringWithCString:screenName.getCString( ) encoding:NSUTF8StringEncoding]];
}

void IOSCPPHelper::setGADispatchInterval( int dispatchInterval )
{
    [[IOSHelper instance] setGADispatchInterval:dispatchInterval];
}

void IOSCPPHelper::sendGAEvent( __String category, __String action, __String label, long value )
{
    [[IOSHelper instance] sendGAEvent:[NSString stringWithCString:category.getCString( ) encoding:NSUTF8StringEncoding]: [NSString stringWithCString:action.getCString( ) encoding:NSUTF8StringEncoding]: [NSString stringWithCString:label.getCString( ) encoding:NSUTF8StringEncoding]: [NSNumber numberWithLong:(value)]];
}
#endif

#if SCH_IS_ADCOLONY_ENABLED == true
void IOSCPPHelper::showVideoAC( bool withPreOp, bool withPostOp )
{
    [[IOSHelper instance] showV4VCAC: withPreOp andPostOp: withPostOp];
}
#endif

#if SCH_IS_VUNGLE_ENABLED == true
void IOSCPPHelper::showVideoVungle( bool isIncentivised )
{
    [[IOSHelper instance] showV4VCV: isIncentivised];
}
#endif

#if SCH_IS_WECHAT_ENABLED == true
void IOSCPPHelper::shareTextToWeChat( __String msgString )
{
    [[IOSHelper instance] sendTextContentToWeChat:[NSString stringWithCString:msgString.getCString( ) encoding:NSUTF8StringEncoding]];
}
void IOSCPPHelper::shareImageToWeChat( __String thumbImgPath, __String imgPath )
{
    NSString *thumbImg = [NSString stringWithCString:thumbImgPath.getCString( ) encoding:NSUTF8StringEncoding];
    NSString *shareImg = [NSString stringWithCString:imgPath.getCString( ) encoding:NSUTF8StringEncoding];
    
    [[IOSHelper instance] sendThumbImage:thumbImg andShareImgToWeChat:shareImg];
}
void IOSCPPHelper::shareLinkToWeChat( __String thumbImgPath, __String shareTitle, __String msgDes, __String shareUrl )
{
    NSString *thumbImg = [NSString stringWithCString:thumbImgPath.getCString( ) encoding:NSUTF8StringEncoding];
    NSString *msgTitle = [NSString stringWithCString:shareTitle.getCString( ) encoding:NSUTF8StringEncoding];
    NSString *msgDescription = [NSString stringWithCString:msgDes.getCString( ) encoding:NSUTF8StringEncoding];
    NSString *url = [NSString stringWithCString:shareUrl.getCString( ) encoding:NSUTF8StringEncoding];
    
    [[IOSHelper instance] sendLinkWithThumbImg:thumbImg andMsgTitle:msgTitle andMsgDescription:msgDescription andURLToWeChat:url];
}

void IOSCPPHelper::shareMusicToWeChat( __String msgTitle, __String msgDescription, __String thumbImg, __String musicUrl, __String musicDataURL )
{
    NSString *shareTitle = [NSString stringWithCString:msgTitle.getCString( ) encoding:NSUTF8StringEncoding];
    NSString *shareDescription = [NSString stringWithCString:msgDescription.getCString( ) encoding:NSUTF8StringEncoding];
    NSString *shareThImg = [NSString stringWithCString:thumbImg.getCString( ) encoding:NSUTF8StringEncoding];
    NSString *musicLinkUrl = [NSString stringWithCString:musicUrl.getCString( ) encoding:NSUTF8StringEncoding];
    NSString *musicDataUrl = [NSString stringWithCString:musicDataURL.getCString( ) encoding:NSUTF8StringEncoding];
    [[IOSHelper instance] sendMusicContentWithTitle:shareTitle andDescription:shareDescription andThumbImg:shareThImg andMusicUrl:musicLinkUrl andMusicDataUrl:musicDataUrl];
}

void IOSCPPHelper::shareVideoToWeChat( __String msgTitle, __String msgDescription, __String thumbImg, __String videoUrl )
{
    NSString *shareTitle = [NSString stringWithCString:msgTitle.getCString( ) encoding:NSUTF8StringEncoding];
    NSString *shareDescription = [NSString stringWithCString:msgDescription.getCString( ) encoding:NSUTF8StringEncoding];
    NSString *shareThImg = [NSString stringWithCString:thumbImg.getCString( ) encoding:NSUTF8StringEncoding];
    NSString *videoLinkUrl = [NSString stringWithCString:videoUrl.getCString( ) encoding:NSUTF8StringEncoding];
    [[IOSHelper instance] sendVideoContentWithTitle:shareTitle andDescription:shareDescription andThumbImg:shareThImg andVideoUrl:videoLinkUrl];
}
#endif

#if SCH_IS_NOTIFICATIONS_ENABLED == true
void IOSCPPHelper::scheduleLocalNotification( float delay, __String textToDisplay, __String notificationTitle , int notificationTag)
{
    NSString *notificationText = [NSString stringWithCString:textToDisplay.getCString( ) encoding:NSUTF8StringEncoding];
    NSString *notificationTitleLcl = [NSString stringWithCString:notificationTitle.getCString( ) encoding:NSUTF8StringEncoding];
    [[IOSHelper instance] scheduleLocalNotification:delay andNotificationText:notificationText andNotificationTitle:notificationTitleLcl addNotificationTag:notificationTag];
}

void IOSCPPHelper::scheduleLocalNotification( float delay, __String textToDisplay, __String notificationTitle, __String notificationAction , int notificationTag)
{
    NSString *notificationText = [NSString stringWithCString:textToDisplay.getCString( ) encoding:NSUTF8StringEncoding];
    NSString *notificationTitleLcl = [NSString stringWithCString:notificationTitle.getCString( ) encoding:NSUTF8StringEncoding];
    NSString *notificationActionLcl = [NSString stringWithCString:notificationAction.getCString( ) encoding:NSUTF8StringEncoding];
    [[IOSHelper instance] scheduleLocalNotification:delay andNotificationText:notificationText andNotificationTitle:notificationTitleLcl andNotificationAction:notificationActionLcl addNotificationTag:notificationTag];
}

void IOSCPPHelper::scheduleLocalNotification( float delay, __String textToDisplay, __String notificationTitle, int repeatInterval , int notificationTag)
{
    NSString *notificationText = [NSString stringWithCString:textToDisplay.getCString( ) encoding:NSUTF8StringEncoding];
    NSString *notificationTitleLcl = [NSString stringWithCString:notificationTitle.getCString( ) encoding:NSUTF8StringEncoding];
    [[IOSHelper instance] scheduleLocalNotification:delay andNotificationText:notificationText andNotificationTitle:notificationTitleLcl andRepeatInterval:repeatInterval addNotificationTag:notificationTag];
}

void IOSCPPHelper::scheduleLocalNotification( float delay, __String textToDisplay, __String notificationTitle, __String notificationAction, int repeatInterval , int notificationTag)
{
    NSString *notificationText = [NSString stringWithCString:textToDisplay.getCString( ) encoding:NSUTF8StringEncoding];
    NSString *notificationTitleLcl = [NSString stringWithCString:notificationTitle.getCString( ) encoding:NSUTF8StringEncoding];
    NSString *notificationActionLcl = [NSString stringWithCString:notificationAction.getCString( ) encoding:NSUTF8StringEncoding];
    [[IOSHelper instance] scheduleLocalNotification:delay andNotificationText:notificationText andNotificationTitle:notificationTitleLcl andNotificationAction:notificationActionLcl andRepeatInterval:repeatInterval addNotificationTag:notificationTag];
}

void IOSCPPHelper::unscheduleAllLocalNotifications( )
{
    [[IOSHelper instance] unscheduleAllLocalNotifications];
}

void IOSCPPHelper::unscheduleLocalNotification( int notificationTag )
{
    //NSString *notificationTitleLcl = [NSString stringWithCString:notificationTitle.getCString( ) encoding:NSUTF8StringEncoding];
    [[IOSHelper instance] unscheduleLocalNotification:notificationTag];
}
#endif

