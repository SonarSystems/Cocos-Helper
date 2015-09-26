/*
 Copyright (C) 2015 Sonar Systems - All Rights Reserved
 You may use, distribute and modify this code under the
 terms of the MIT license
 
 Any external frameworks used have their own licenses and
 should be followed as such.
 */
//
//  IOSHelper.h
//  Sonar Cocos Helper
//
//  Created by Sonar Systems on 03/03/2015.
//
//

#import "SCHDefinitions.h"

#import "SCHSettings.h"

#import <Foundation/Foundation.h>
#import "AppController.h"

#if SCH_IS_iADS_ENABLED == true
    #import <iAd/iAd.h>
#endif

#if SCH_IS_CHARTBOOST_ENABLED == true
    #import <Chartboost/Chartboost.h>
#endif

#if SCH_IS_REVMOB_ENABLED == true
    #import <RevMobAds/RevMobAds.h>
#endif

#if SCH_IS_AD_MOB_ENABLED == true
    #import <GoogleMobileAds/GoogleMobileAds.h>
#endif

#if SCH_IS_EVERYPLAY_ENABLED == true
    #import <Everyplay/Everyplay.h>
#endif

#if SCH_IS_MOPUB_ENABLED == true
    #import <MPAdView.h>
    #import <MPInterstitialAdController.h>
#endif

#if SCH_IS_GOOGLE_ANALYTICS_ENABLED == true
    #import <GAI.h>
    #import <GAIDictionaryBuilder.h>
    #import <GAIEcommerceProduct.h>
    #import <GAIEcommerceProductAction.h>
    #import <GAIEcommercePromotion.h>
    #import <GAIFields.h>
    #import <GAILogger.h>
    #import <GAITrackedViewController.h>
    #import <GAITracker.h>
    #import <GAIEcommerceFields.h>
    #import <GAITrackedViewController.h>
#endif

 #if SCH_IS_ADCOLONY_ENABLED == true
    #import <AdColony/AdColony.h>
#endif

#if SCH_IS_VUNGLE_ENABLED == true
    #import <VungleSDK/VungleSDK.h>
#endif

#if SCH_IS_WECHAT_ENABLED == true
    #import "WXApi.h"
#endif

@protocol SCHEmptyProtocol
@end

@interface IOSHelper : NSObject
<
SCHEmptyProtocol
#if SCH_IS_iADS_ENABLED == true
, ADBannerViewDelegate
#endif

#if SCH_IS_CHARTBOOST_ENABLED == true
, ChartboostDelegate
#endif

#if SCH_IS_REVMOB_ENABLED == true
, RevMobAdsDelegate
#endif

#if SCH_IS_SOCIAL_ENABLED == true
, UIPopoverControllerDelegate
#endif

#if SCH_IS_EVERYPLAY_ENABLED == true
, EveryplayDelegate
#endif

#if SCH_IS_MOPUB_ENABLED == true
, MPAdViewDelegate
, MPInterstitialAdControllerDelegate
#endif

#if SCH_IS_ADCOLONY_ENABLED == true
, AdColonyDelegate
#endif

#if SCH_IS_VUNGLE_ENABLED == true
, VungleSDKDelegate
#endif

#if SCH_IS_AD_MOB_ENABLED == true
, GADInterstitialDelegate
#endif

#if SCH_IS_WECHAT_ENABLED == true
, WXApiDelegate
#endif
>
{
    AppController *appController;
    UIView *view;
    RootViewController *localViewController;
    
#if SCH_IS_GOOGLE_ANALYTICS_ENABLED == true
    id<GAITracker> tracker;
#endif
    
#if SCH_IS_iADS_ENABLED == true
    ADBannerView *adView;
    
    BOOL isBannerVisible;
    BOOL isBannerLoaded;
    BOOL isBannerToBeShown;
    
    int bannerPosition;
#endif
    
#if SCH_IS_REVMOB_ENABLED == true
    BOOL isRevMobInitialised;
#endif
    
#if SCH_IS_AD_MOB_ENABLED == true
    GADInterstitial *adMobInterstitial;
    GADBannerView *adMobBottomBanner;
    GADBannerView *adMobTopBanner;
    BOOL isAdMobFullscreenLoaded;
    BOOL isAdMobBottomBannerDisplayed;
    BOOL isAdMobTopBannerDisplayed;
#endif
    
#if SCH_IS_MOPUB_ENABLED == true
    BOOL isMopubBannerDisplayed;
#endif
   
}

#if SCH_IS_MOPUB_ENABLED == true
@property (nonatomic, strong) MPAdView *moPubAdView;
@property (nonatomic, strong) MPInterstitialAdController *moPubinterstitial;
#endif

@property (nonatomic, strong) UIPopoverController *popover;

+( id )instance;

-( void )initialise;

#if SCH_IS_iADS_ENABLED == true
//-( void )showiAdBanner;
-( void )showiAdBanner:( int ) position;
-( void )hideiAdBanner;
-( void )hideiAdBannerPermanently;
#endif

#if SCH_IS_REVMOB_ENABLED == true
-( void )showRevMobBanner;
-( void )hideRevMobBanner;
-( void )showRevMobFullScreenAd;
-( void )showRevMobPopupAd;
#endif

#if SCH_IS_CHARTBOOST_ENABLED == true
-( void )showChartboostFullScreenAd;
-( void )showChartboostMoreApps;
-( void )showChartboostVideoAd;
#endif

#if SCH_IS_SOCIAL_ENABLED == true
-( void )shareViaFacebook:( NSString * ) message: ( NSString * ) imagePath;
-( void )shareViaTwitter:( NSString * ) message: ( NSString * ) imagePath;
-( void )shareWithString:( NSString *) message: ( NSString * ) imagePath;
#endif

#if SCH_IS_GAME_CENTER_ENABLED == true
@property ( nonatomic ) BOOL gameCenterEnabled;
@property ( nonatomic, copy ) NSString *leaderboardIdentifier;

-( void )gameCenterLogin;
-( void )gameCenterShowLeaderboard;
-( void )gameCenterShowAchievements;
-( void )gameCenterSubmitScore:( int )scoreNumber andLeaderboard: ( NSString * )leaderboardID;
-( void )gameCenterUnlockAchievement:( NSString * )achievementID andPercentage:( float )percent;
-( void )gameCenterResetPlayerAchievements;
#endif

#if SCH_IS_AD_MOB_ENABLED == true
-( void )requestAdMobFullscreenAd;
-( void )showAdMobBanner:( int ) position;
-( void )hideAdMobBanner:( int ) position;
-( void )showAdMobFullscreenAd;
#endif

#if SCH_IS_MOPUB_ENABLED == true
- ( void )showMopubBanner;
- ( void )hideMopubBanner;

- ( void )showMoPubFullscreenAd;
#endif

#if SCH_IS_EVERYPLAY_ENABLED == true
-( void )setupEveryplay;
-( void )showEveryplay;
-( void )recordEveryplayVideo;
-( void )playLastEveryplayVideoRecording;
#endif

#if SCH_IS_GOOGLE_ANALYTICS_ENABLED == true
-( void )setGAScreenName:( NSString * )screenString;
-( void )setGADispatchInterval:( int )dispatchInterval;
-( void )sendGAEvent:( NSString * ) category: ( NSString * ) action: ( NSString * ) label: ( NSNumber * ) value;
#endif

#if SCH_IS_ADCOLONY_ENABLED == true
-( void )showV4VCAC:( BOOL ) withPreOP andPostOp: ( BOOL ) withPostOp;
-( void )onAdColonyV4VCReward:( BOOL )success currencyName:( NSString * )currencyName currencyAmount:( int )amount inZone:( NSString * )zoneID;
#endif

#if SCH_IS_VUNGLE_ENABLED == true
-( void )showV4VCV:( BOOL )isIncentivised;
-( void )vungleSDKwillCloseAdWithViewInfo:( NSDictionary * )viewInfo willPresentProductSheet:( BOOL )willPresentProductSheet;
-( void )vungleSDKwillShowAd;
#endif

#if SCH_IS_WECHAT_ENABLED == true
-( void )sendTextContentToWeChat:( NSString * )msgString;
-( void )sendThumbImage:( NSString * ) thumbImgPath andShareImgToWeChat:( NSString * ) imgPath;
-( void )sendLinkWithThumbImg:( NSString* ) thumbImgPath andMsgTitle:( NSString * ) msgTitle andMsgDescription:( NSString * ) msgDes andURLToWeChat:( NSString * ) url;
-( void )sendMusicContentWithTitle:( NSString * ) msgTitle andDescription:( NSString * )msgDescription andThumbImg:( NSString * ) thumbImg andMusicUrl:( NSString * ) musicUrl andMusicDataUrl:(NSString*) musicDataURL;
-( void )sendVideoContentWithTitle:( NSString * ) msgTitle andDescription:( NSString * )msgDescription andThumbImg:( NSString * ) thumbImg andVideoUrl:( NSString * ) videoUrl;
#endif

#if SCH_IS_NOTIFICATIONS_ENABLED == true
-( void )scheduleLocalNotification:( NSTimeInterval )delay andNotificationText:( NSString * )textToDisplay andNotificationTitle:( NSString * )notificationTitle addNotificationTag:( int ) notificationTag;
-( void )scheduleLocalNotification:( NSTimeInterval )delay andNotificationText:( NSString * )textToDisplay andNotificationTitle:( NSString * )notificationTitle andNotificationAction:( NSString * )notificationAction addNotificationTag:( int ) notificationTag;
-( void )scheduleLocalNotification:( NSTimeInterval )delay andNotificationText:( NSString * )textToDisplay andNotificationTitle:( NSString * )notificationTitle andRepeatInterval:( int )repeatInterval addNotificationTag:( int ) notificationTag;
-( void )scheduleLocalNotification:( NSTimeInterval )delay andNotificationText:( NSString * )textToDisplay andNotificationTitle:( NSString * )notificationTitle andNotificationAction:( NSString * )notificationAction andRepeatInterval:( int )repeatInterval addNotificationTag:( int ) notificationTag;
-( void )unscheduleAllLocalNotifications;
-( void )unscheduleLocalNotification:( int )notificationTag;
#endif



@end