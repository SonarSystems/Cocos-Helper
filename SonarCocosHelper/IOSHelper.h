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
>
{
    AppController *appController;
    UIView *view;
    
#if SCH_IS_iADS_ENABLED == true
    ADBannerView *adView;
#endif
    
    BOOL isBannerVisible;
    BOOL isBannerLoaded;
    BOOL isBannerToBeShown;
    
    int bannerPosition;
    
#if SCH_IS_REVMOB_ENABLED == true
    BOOL isRevMobInitialised;
#endif
    
#if SCH_IS_AD_MOB_ENABLED == true
    GADInterstitial *adMobInterstitial;
    GADBannerView *adMobBanner;
    BOOL isAdMobFullscreenLoaded;
    BOOL isAdMobBannerDisplayed;
#endif
    
#if SCH_IS_MOPUB_ENABLED == true
    BOOL isMopubBannerDisplayed;
#endif
   
}

#if SCH_IS_MOPUB_ENABLED == true
@property (nonatomic, strong) MPAdView *adView;
@property (nonatomic, strong) MPInterstitialAdController *interstitialLaunch;
@property (nonatomic, strong) MPInterstitialAdController *interstitialEndlevel;
#endif

@property (nonatomic, strong) UIPopoverController *popover;

+( id )instance;

-( void )initialise;

#if SCH_IS_iADS_ENABLED == true
-( void )showiAdBanner;
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

//-( void )showBannerWithDelay:( int ) position:( float ) delay; // not done yet
//-( void )showFullScreenAdWithDelay:( float ) delay; // not done yet
//-( void )showMoreAppsWithDelay:( float ) delay; // not done yet
///-( void )showVideoAdWithDelay:( float ) delay; // not done yet

#if SCH_IS_SOCIAL_ENABLED == true
-( void )shareViaFacebook: ( NSString * ) message: ( NSString * ) imagePath;
-( void )shareViaTwitter: ( NSString * ) message: ( NSString * ) imagePath;
-( void )shareWithString:( NSString *) message: ( NSString * ) imagePath;
#endif

#if SCH_IS_GAME_CENTER_ENABLED == true
@property (nonatomic) BOOL gameCenterEnabled;
@property (nonatomic, copy) NSString *leaderboardIdentifier;

-( void )gameCenterLogin;
-( void )gameCenterShowLeaderboard;
-( void )gameCenterShowAchievements;
-( void )gameCenterSubmitScore:( int )scoreNumber andLeaderboard: ( NSString * )leaderboardID;
-( void )gameCenterUnlockAchievement:( NSString * )achievementID andPercentage:( float )percent;
-( void )gameCenterResetPlayerAchievements;
#endif

#if SCH_IS_AD_MOB_ENABLED == true
-( void )showAdMobBanner;
-( void )showAdMobBanner:( int ) position;
-( void )hideAdMobBanner;
-( void )showAdMobFullscreenAd;
#endif

#if SCH_IS_MOPUB_ENABLED == true
- ( void )showMopubBanner;
- ( void )hideMopubBanner;

- ( void )requestLaunchFullscreenAd;
- ( void )showLaunchFullscreenAd;

- ( void )requestEndLevelFullscreenAd;
- ( void )showEndLevelFullscreenAd;
#endif

#if SCH_IS_EVERYPLAY_ENABLED == true
-( void )setupEveryplay;
-( void )showEveryplay;
-( void )recordEveryplayVideo;
-( void )playLastEveryplayVideoRecording;
#endif

@end