/*
 Copyright (C) 2015 Sonar Systems - All Rights Reserved
 You may use, distribute and modify this code under the
 terms of the MIT license
 
 Any external frameworks used have their own licenses and
 should be followed as such.
 */
//
//  IOSCPPHelper.h
//  Sonar Cocos Helper
//
//  Created by Sonar Systems on 03/03/2015.
//
//

#ifndef __SCH__IOSCPPHelper__
#define __SCH__IOSCPPHelper__

#include "SCHSettings.h"
#include "cocos2d.h"

using namespace cocos2d;

class IOSCPPHelper
{
private:
    
public:    
    static void Setup( );

#if SCH_IS_SOCIAL_ENABLED == true
    static void shareViaFacebook( __String message, __String thumbnailPath );
    static void shareViaTwitter( __String message, __String thumbnailPath );
    static void shareWithString( __String message, __String thumbnailPath );
#endif
    
#if SCH_IS_GAME_CENTER_ENABLED == true
    static void gameCenterLogin( );
    static void gameCenterShowLeaderboard( );
    static void gameCenterShowAchievements( );
    static void gameCenterSubmitScore( int scoreNumber, __String leaderboardID );
    static void gameCenterUnlockAchievement( __String achievementID, float percent );
    static void gameCenterResetPlayerAchievements( );
#endif
    
#if SCH_IS_REVMOB_ENABLED == true
    static void showRevMobBanner( );
    static void hideRevMobBanner( );
    static void showRevMobFullScreenAd( );
    static void showRevMobPopupAd( );
#endif
    
#if SCH_IS_iADS_ENABLED == true
    static void showiAdBanner( int position );
    static void hideiAdBanner( );
#endif
    
#if SCH_IS_CHARTBOOST_ENABLED == true
    static void showChartboostFullScreenAd( );
    static void showChartboostMoreApps( );
    static void showChartboostVideoAd( );
#endif
    
#if SCH_IS_AD_MOB_ENABLED == true
    static void showAdMobBanner( int position );
    static void hideAdMobBanner( int position );
    
    static void requestAdMobFullScreenAd();
    static void showAdMobFullscreenAd( );
#endif

#if SCH_IS_MOPUB_ENABLED == true
    static void showMopubBanner( );
    static void hideMopubBanner( );
    
    static void showMoPubFullscreenAd( );
#endif
    
#if SCH_IS_EVERYPLAY_ENABLED == true
    static void setupEveryplay( );
    static void showEveryplay( );
    static void recordEveryplayVideo( );
    static void playLastEveryplayVideoRecording( );
#endif
    
#if SCH_IS_GOOGLE_ANALYTICS_ENABLED == true
    static void setGAScreenName( __String screenName );
    static void setGADispatchInterval( int dispatchInterval );
    static void sendGAEvent( __String category, __String action, __String label, long value );
#endif

#if SCH_IS_ADCOLONY_ENABLED == true
    static void showVideoAC( bool withPreOp, bool withPostOp );
#endif
    
#if SCH_IS_VUNGLE_ENABLED == true
    static void showVideoVungle( bool isIncentivised );
#endif
    
#if SCH_IS_WECHAT_ENABLED == true
    static void shareTextToWeChat( __String msgString );
    static void shareImageToWeChat( __String thumbImgPath ,__String imgPath );
    static void shareLinkToWeChat( __String thumbImgPath , __String shareTitle , __String msgDes , __String shareUrl );
    static void shareMusicToWeChat( __String msgTitle , __String msgDescription , __String thumbImg , __String  musicUrl , __String  musicDataURL);
    static void shareVideoToWeChat(__String  msgTitle ,__String msgDescription ,__String thumbImg ,__String videoUrl);
#endif
    
#if SCH_IS_NOTIFICATIONS_ENABLED == true
    static void scheduleLocalNotification( float delay, __String textToDisplay, __String notificationTitle , int notificationTag);
    static void scheduleLocalNotification( float delay, __String textToDisplay, __String notificationTitle, __String notificationAction , int notificationTag);
    static void scheduleLocalNotification( float delay, __String textToDisplay, __String notificationTitle, int repeatInterval , int notificationTag);
    static void scheduleLocalNotification( float delay, __String textToDisplay, __String notificationTitle, __String notificationAction, int repeatInterval , int notificationTag);
    static void unscheduleAllLocalNotifications( );
    static void unscheduleLocalNotification( int notificationTag );
#endif
    
};

#endif /* defined(__SNF__IOSCPPHelper__) */
