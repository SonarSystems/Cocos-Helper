/*
 Copyright (C) 2015 Sonar Systems - All Rights Reserved
 You may use, distribute and modify this code under the
 terms of the MIT license
 
 Any external frameworks used have their own licenses and
 should be followed as such.
 */
//
//  IOSHelper.m
//  Sonar Cocos Helper
//
//  Created by Sonar Systems on 03/03/2015.
//

#import <UIKit/UIKit.h>
#import "IOSHelper.h"
#import "AppController.h"
#import "RootViewController.h"
#import "IOSResults.h"

#if SCH_IS_CHARTBOOST_ENABLED == true
    #import <Chartboost/CBNewsfeed.h>
    #import <CommonCrypto/CommonDigest.h>
    #import <AdSupport/AdSupport.h>
#endif

#if SCH_IS_SOCIAL_ENABLED == true || SCH_IS_GAME_CENTER_ENABLED == true
    #import <Social/Social.h>
#endif

#if SCH_IS_GAME_CENTER_ENABLED == true
    #import <GameKit/GameKit.h>
#endif

@interface IOSHelper ( )
<
SCHEmptyProtocol
#if SCH_IS_CHARTBOOST_ENABLED == true
    , ChartboostDelegate
    , CBNewsfeedDelegate
#endif

#if SCH_IS_GAME_CENTER_ENABLED == true
    , GKGameCenterControllerDelegate
#endif

#if SCH_IS_AD_MOB_ENABLED == true
    , GADInterstitialDelegate
#endif
>

@end

@implementation IOSHelper
{
}

+( id )instance
{
    static dispatch_once_t onceToken;
    static IOSHelper *__helper;
    dispatch_once( &onceToken,
    ^{
        __helper = [[IOSHelper alloc] init];
    } );
    
    return __helper;
}

// initialise the Network Framework to setup external frameworks
-( void )initialise
{    
    appController = ( AppController * )[[UIApplication sharedApplication] delegate];
    
#if COCOS2D_JAVASCRIPT
    localViewController = appController->viewController;
    view = appController->viewController.view;
#else
    localViewController = appController.viewController;
    view = appController.viewController.view;
#endif
    
#if SCH_IS_GOOGLE_ANALYTICS_ENABLED == true
    // Optional: automatically send uncaught exceptions to Google Analytics.
    [GAI sharedInstance].trackUncaughtExceptions = YES;
    
    // Optional: set Google Analytics dispatch interval to e.g. 20 seconds.
    [GAI sharedInstance].dispatchInterval = SCH_GOOGLE_ANALYTICS_DEFAULT_DISPATCH_TIME;
    
    // Optional: set Logger to VERBOSE for debug information.
    [[[GAI sharedInstance] logger] setLogLevel:kGAILogLevelVerbose];
    
    // Initialize tracker. Replace with your tracking ID.
    [[GAI sharedInstance] trackerWithTrackingId:SCH_GOOGLE_ANALYTICS_TRACKING_ID];
    
    tracker = [[GAI sharedInstance] defaultTracker];
    
    // Enable IDFA collection.
    tracker.allowIDFACollection = YES;
#endif

#if SCH_IS_REVMOB_ENABLED == true
    isRevMobInitialised = false;
#endif
    
#if SCH_IS_CHARTBOOST_ENABLED == true
    // Begin a user session. Must not be dependent on user actions or any prior network requests.
    [Chartboost startWithAppId:SCH_CHARTBOOST_APP_ID appSignature:SCH_CHARTBOOST_APP_SIGNATURE delegate:self];
    
    [Chartboost cacheInterstitial:CBLocationDefault];
    [Chartboost cacheMoreApps:CBLocationDefault];
    [Chartboost cacheRewardedVideo:CBLocationDefault];
#endif
   
#if SCH_IS_REVMOB_ENABLED == true
    [RevMobAds startSessionWithAppID:SCH_REVMOB_MEDIA_ID withSuccessHandler:^
    {
        isRevMobInitialised = true;
    } andFailHandler:^( NSError *error )
    {
        isRevMobInitialised = false;
    }];
#endif
    
#if SCH_IS_iADS_ENABLED == true
    isBannerVisible = false;
    isBannerLoaded = false;
    isBannerToBeShown = false;
    
    bannerPosition = ADBANNERPOSITION_TOP;
    
    adView = [[ADBannerView alloc] initWithFrame:CGRectZero];
    adView.frame = CGRectMake( 0, view.frame.size.height * 2, adView.frame.size.width, adView.frame.size.height );
    
    if ( view.frame.size.width > view.frame.size.height )
    { adView.currentContentSizeIdentifier = ADBannerContentSizeIdentifierLandscape; }
    else
    { adView.currentContentSizeIdentifier = ADBannerContentSizeIdentifierPortrait; }
    
    adView.delegate = self;
    [view addSubview:adView];
#endif
    
#if SCH_IS_AD_MOB_ENABLED == true
    isAdMobFullscreenLoaded = false;
    isAdMobTopBannerDisplayed = false;
    isAdMobBottomBannerDisplayed = false;
    
    [self requestAdMobFullscreenAd];
#endif
    
#if SCH_IS_MOPUB_ENABLED == true
    isMopubBannerDisplayed = false;
    
    self.moPubinterstitial = [MPInterstitialAdController interstitialAdControllerForAdUnitId:SCH_MOPUB_INTERSTITIAL_AD_UNIT];
    [self.moPubinterstitial loadAd];
#endif
    
#if SCH_IS_EVERYPLAY_ENABLED == true
    [self setupEveryplay];
#endif

#if SCH_IS_ADCOLONY_ENABLED == true
    [AdColony configureWithAppID: SCH_ADCOLONY_APP_ID
                         zoneIDs: @[ SCH_ADCOLONY_ZONE_ID ]
                        delegate: self
                         logging: YES];
#endif
    
#if SCH_IS_VUNGLE_ENABLED == true
    NSString* appID = SCH_VUNGLE_ID;
    VungleSDK *sdk = [VungleSDK sharedSDK];
    
    // start vungle publisher library
    [sdk startWithAppId:appID];
    
    [[VungleSDK sharedSDK] setLoggingEnabled:YES];
    [[VungleSDK sharedSDK] setDelegate:self];
#endif
    
#if SCH_IS_WECHAT_ENABLED == true
    NSString* appID = SCH_WECHAT_APP_ID;
    // Register your app
    [WXApi registerApp:appID withDescription:@"demo 2.0"];
#endif
    
#if SCH_IS_NOTIFICATIONS_ENABLED == true
    UIApplication *app = [UIApplication sharedApplication];
    if ( [[UIDevice currentDevice].systemVersion floatValue] >= 8.0) {
        if ( [UIApplication instancesRespondToSelector:@selector( registerUserNotificationSettings: )] ){ [app registerUserNotificationSettings:[UIUserNotificationSettings settingsForTypes:UIUserNotificationTypeAlert|UIUserNotificationTypeSound categories:nil]]; }
    }
    
    NSArray *oldNotifications = [app scheduledLocalNotifications];
    
    if ( [oldNotifications count] > 0 )
    { [app cancelAllLocalNotifications]; }
#endif
}

#if SCH_IS_iADS_ENABLED == true
// display iAd banner
-( void )showiAdBanner:( int ) position
{
    isBannerToBeShown = true;
    bannerPosition = position;
    
    if ( !isBannerVisible && isBannerLoaded )
    {
        // check where the ad is to be positioned
        if ( bannerPosition == ADBANNERPOSITION_TOP )
        { adView.frame = CGRectMake( 0, 0, adView.frame.size.width, adView.frame.size.height ); }
        else
        { adView.frame = CGRectMake( 0, view.frame.size.height - adView.frame.size.height, adView.frame.size.width, adView.frame.size.height ); }
        
        isBannerVisible = true;
    }
}

// hide visible ad banner
-( void )hideiAdBanner
{
    if ( isBannerVisible )
    {
        adView.frame = CGRectMake( 0, view.frame.size.height * 2, adView.frame.size.width, adView.frame.size.height );
        isBannerVisible = false;
    }
}

// hide visible iAd banner
// also prevent it from showing again if it loads another iAd
-( void )hideiAdBannerPermanently
{
    [self hideiAdBanner];
    
    isBannerToBeShown = false;
}

// iad banner has successfully loaded/refreshed
-( void )bannerViewDidLoadAd:( ADBannerView * )abanner
{
    isBannerLoaded = true;
    
    if ( isBannerToBeShown )
    { [self showiAdBanner:bannerPosition]; }
}

// iad banner has failed to load/refresh
- ( void )bannerView:( ADBannerView * )banner didFailToReceiveAdWithError:( NSError * )error
{
    isBannerLoaded = false;
    
    [self hideiAdBanner];
}
#endif

#pragma mark - REVMOB

#if SCH_IS_REVMOB_ENABLED == true
// display RevMob banner
-( void )showRevMobBanner
{
    if ( isRevMobInitialised )
    { [[RevMobAds session] showBanner]; }
    else
    {
        [RevMobAds startSessionWithAppID:SCH_REVMOB_MEDIA_ID withSuccessHandler:^
        {
            [[RevMobAds session] showBanner];
            isRevMobInitialised = true;
        } andFailHandler:^( NSError *error )
        {
            isRevMobInitialised = false;
        }];
    }
}


// hide visible RevMob ad banner
-( void )hideRevMobBanner
{
    if ( isRevMobInitialised )
    { [[RevMobAds session] hideBanner]; }
    else
    {
        [RevMobAds startSessionWithAppID:SCH_REVMOB_MEDIA_ID withSuccessHandler:^
        {
            [[RevMobAds session] hideBanner];
            isRevMobInitialised = true;
        } andFailHandler:^( NSError *error )
        {
            isRevMobInitialised = false;
        }];
    }
}

// show a RevMob interstitial ad
-( void )showRevMobFullScreenAd
{
    if ( isRevMobInitialised )
    { [[RevMobAds session] showFullscreen]; }
    else
    {
        [RevMobAds startSessionWithAppID:SCH_REVMOB_MEDIA_ID withSuccessHandler:^
        {
            [[RevMobAds session] showFullscreen];
            isRevMobInitialised = true;
        } andFailHandler:^( NSError *error )
        {
            isRevMobInitialised = false;
        }];
    }
}

// show RevMob more app dialogue
-( void )showRevMobPopupAd
{
    if ( isRevMobInitialised )
    { [[RevMobAds session] showPopup]; }
    else
    {
        [RevMobAds startSessionWithAppID:SCH_REVMOB_MEDIA_ID withSuccessHandler:^
        {
            [[RevMobAds session] showPopup];
            isRevMobInitialised = true;
        } andFailHandler:^( NSError *error )
        {
            isRevMobInitialised = false;
        }];
    }
}
#endif

#pragma mark - CHARTBOOST

#if SCH_IS_CHARTBOOST_ENABLED == true
// show a Chartboost interstitial ad
-( void )showChartboostFullScreenAd
{
    [Chartboost showInterstitial:CBLocationDefault];
}

// show Chartboost more apps dialogue
-( void )showChartboostMoreApps
{
    [Chartboost showMoreApps:CBLocationDefault];
}

// show video ad
-( void )showChartboostVideoAd
{
    [Chartboost showRewardedVideo:CBLocationDefault];
}
#endif

#if SCH_IS_SOCIAL_ENABLED == true
// share to facebook (requires the message to be sent and the image path, both of which can be empty strings)
-( void )shareViaFacebook: ( NSString * ) message: ( NSString * ) imagePath
{
    SLComposeViewController *slVC = [SLComposeViewController composeViewControllerForServiceType:SLServiceTypeFacebook];
    [slVC addImage:[UIImage imageNamed:imagePath]];
    [slVC setInitialText:message];
    slVC.completionHandler = ^( SLComposeViewControllerResult result )
    {
        [localViewController dismissViewControllerAnimated:YES completion:NULL];
    };
    
    [localViewController presentViewController:slVC animated:YES completion:NULL];
}

// share to twitter (requires the message to be sent and the image path, both of which can be empty strings)
-( void )shareViaTwitter: ( NSString * ) message: ( NSString * ) imagePath
{
    SLComposeViewController *slVC = [SLComposeViewController composeViewControllerForServiceType:SLServiceTypeTwitter];
    [slVC addImage:[UIImage imageNamed:imagePath]];
    [slVC setInitialText:message];
    slVC.completionHandler = ^( SLComposeViewControllerResult result )
    {
        [localViewController dismissViewControllerAnimated:YES completion:NULL];
    };

    [localViewController presentViewController:slVC animated:YES completion:NULL];
}

-( void )shareWithString:( NSString *) message: ( NSString * ) imagePath
{
    UIImage *image = [UIImage imageNamed:imagePath];
    UIActivityViewController *activityVC = [[UIActivityViewController alloc] initWithActivityItems:@[message, image] applicationActivities:nil];
    activityVC.excludedActivityTypes = @[UIActivityTypeAirDrop];
    
    // Finally present the view controller.
    if (self.popover)
    {
        [self.popover dismissPopoverAnimated:YES];
    }
    
    if ( UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad )
    {
        self.popover = [[UIPopoverController alloc] initWithContentViewController:activityVC];
        self.popover.popoverContentSize = CGSizeMake(600., 400.);
        self.popover.delegate = self;
        CGRect bounds = localViewController.view.bounds;
        [self.popover presentPopoverFromRect:CGRectMake(CGRectGetMidX(bounds), CGRectGetMidY(bounds), 10, 10) inView:localViewController.view permittedArrowDirections:UIPopoverArrowDirectionAny animated:YES];
    }
    else
    {
        [localViewController presentViewController:activityVC animated:YES completion:nil];
    }
}
#endif

#pragma mark - GAME_CENTER

#if SCH_IS_GAME_CENTER_ENABLED == true
-( void )gameCenterLogin
{
    GKLocalPlayer *localPlayer = [GKLocalPlayer localPlayer];
    
    localPlayer.authenticateHandler = ^( UIViewController *viewController, NSError *error )
    {
        if ( viewController != nil )
        {
            [localViewController presentViewController:viewController animated:YES completion:nil];
        }
        else
        {
            if ( [GKLocalPlayer localPlayer].authenticated )
            {
                _gameCenterEnabled = YES;
                
                // Get the default leaderboard identifier.
                [[GKLocalPlayer localPlayer] loadDefaultLeaderboardIdentifierWithCompletionHandler:^( NSString *leaderboardIdentifier, NSError *error ) {
                    
                    if ( error != nil )
                    {
                        NSLog( @"%@", [error localizedDescription] );
                    }
                    else
                    {
                        _leaderboardIdentifier = leaderboardIdentifier;
                    }
                }];
            }
            else
            {
                _gameCenterEnabled = NO;
            }
        }
    };
}

-( void )gameCenterSubmitScore:( int )scoreNumber andLeaderboard:( NSString * )leaderboardID
{
    if ( !self.gameCenterEnabled || self.leaderboardIdentifier == nil )
    { return; }
    
    GKScore *s = [[[GKScore alloc] initWithLeaderboardIdentifier:leaderboardID] autorelease];
    s.value = scoreNumber;
    
    [GKScore reportScores:@[s] withCompletionHandler:^(NSError *error)
    {
        if ( error != nil)
        { NSLog( @"%@", [error localizedDescription] ); }
 
    }];
}

-( void )gameCenterShowLeaderboard
{
    if ( _gameCenterEnabled )
    {
        // Init the following view controller object.
        GKGameCenterViewController *gcViewController = [[GKGameCenterViewController alloc] init];
        
        // Set self as its delegate.
        gcViewController.gameCenterDelegate = self;
        gcViewController.viewState = GKGameCenterViewControllerStateLeaderboards;
        //gcViewController.leaderboardIdentifier = _leaderboardIdentifier;
        
        [localViewController presentViewController:gcViewController animated:YES completion:nil];
    }
    else
    {
        [self gameCenterLogin];
    }
}

-( void )gameCenterViewControllerDidFinish:( GKGameCenterViewController * )gameCenterViewController
{ [gameCenterViewController dismissViewControllerAnimated:YES completion:nil]; }

-( void )gameCenterShowAchievements
{
    GKGameCenterViewController *gcViewController = [[GKGameCenterViewController alloc] init];
    
    gcViewController.gameCenterDelegate = self;
    
    gcViewController.viewState = GKGameCenterViewControllerStateAchievements;
    
    [localViewController presentViewController:gcViewController animated:YES completion:nil];
}

-( void )gameCenterUnlockAchievement:( NSString* )achievementID andPercentage: ( float )percent
{
    [GKAchievement loadAchievementsWithCompletionHandler:^( NSArray *achievements, NSError *error )
    {
        if ( error )
        { NSLog( @"Error reporting achievement" ); }
        
        for ( GKAchievement *ach in achievements )
        {
            // achievement already unlocked
            if( [ach.identifier isEqualToString:achievementID] && ach.isCompleted )
            { return; }
        }
        
        GKAchievement *achievementToSend = [[GKAchievement alloc] initWithIdentifier:achievementID];
        achievementToSend.percentComplete = percent;
        achievementToSend.showsCompletionBanner = YES;
        [GKAchievement reportAchievements:@[achievementToSend] withCompletionHandler:NULL];
    }];
}

-( void )gameCenterResetPlayerAchievements
{ [GKAchievement resetAchievementsWithCompletionHandler: NULL]; }
#endif

#pragma mark - ADMOB

#if SCH_IS_AD_MOB_ENABLED == true
- (GADRequest *)createRequest
{
    GADRequest *request = [GADRequest request];
    if ([SCH_AD_MOB_TEST_DEVICE length])
    {
        request.testDevices = @[SCH_AD_MOB_TEST_DEVICE];
    }
    return request;
}

-( void )showAdMobBanner:( int ) position
{
    GADAdSize adSize = (UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad) ? kGADAdSizeFullBanner : kGADAdSizeBanner;
    GADRequest *request = [self createRequest];
    if ( !isAdMobTopBannerDisplayed && ADBANNERPOSITION_TOP == position )
    {
        adMobTopBanner = [[GADBannerView alloc] initWithAdSize:adSize];
        adMobTopBanner.adUnitID = SCH_AD_MOB_TOP_BANNER_AD_UNIT_ID;
        adMobTopBanner.rootViewController = localViewController;
        [adMobTopBanner loadRequest:request];
        [localViewController.view addSubview:adMobTopBanner];
        adMobTopBanner.translatesAutoresizingMaskIntoConstraints = NO;
        [view addConstraint:[NSLayoutConstraint constraintWithItem:adMobTopBanner attribute:NSLayoutAttributeCenterX relatedBy:NSLayoutRelationEqual toItem:view attribute:NSLayoutAttributeCenterX multiplier:1. constant:0]];
        
        [view addConstraint:[NSLayoutConstraint constraintWithItem:adMobTopBanner attribute:NSLayoutAttributeTop relatedBy:NSLayoutRelationEqual toItem:view attribute:NSLayoutAttributeTop multiplier:1. constant:0]];
            
        isAdMobTopBannerDisplayed = true;
        
    }
    else if ( !isAdMobBottomBannerDisplayed && ADBANNERPOSITION_BOTTOM == position )
    {
        adMobBottomBanner = [[GADBannerView alloc] initWithAdSize:adSize];
        adMobBottomBanner.adUnitID = SCH_AD_MOB_BOTTOM_BANNER_AD_UNIT_ID;
        adMobBottomBanner.rootViewController = localViewController;
        [adMobBottomBanner loadRequest:request];
        [localViewController.view addSubview:adMobBottomBanner];
        adMobBottomBanner.translatesAutoresizingMaskIntoConstraints = NO;
        [view addConstraint:[NSLayoutConstraint constraintWithItem:adMobBottomBanner attribute:NSLayoutAttributeCenterX relatedBy:NSLayoutRelationEqual toItem:view attribute:NSLayoutAttributeCenterX multiplier:1. constant:0]];
        
        [view addConstraint:[NSLayoutConstraint constraintWithItem:adMobBottomBanner attribute:NSLayoutAttributeBottom relatedBy:NSLayoutRelationEqual toItem:view attribute:NSLayoutAttributeBottom multiplier:1. constant:0]];
        
        isAdMobBottomBannerDisplayed = true;
    }
    else if ( ADBANNERPOSITION_BOTH == position )
    {
        adMobTopBanner = [[GADBannerView alloc] initWithAdSize:adSize];
        adMobTopBanner.adUnitID = SCH_AD_MOB_TOP_BANNER_AD_UNIT_ID;
        adMobTopBanner.rootViewController = localViewController;
        [localViewController.view addSubview:adMobTopBanner];
        adMobTopBanner.translatesAutoresizingMaskIntoConstraints = NO;
        [view addConstraint:[NSLayoutConstraint constraintWithItem:adMobTopBanner attribute:NSLayoutAttributeCenterX relatedBy:NSLayoutRelationEqual toItem:view attribute:NSLayoutAttributeCenterX multiplier:1. constant:0]];
        
        adMobBottomBanner = [[GADBannerView alloc] initWithAdSize:adSize];
        adMobBottomBanner.adUnitID = SCH_AD_MOB_BOTTOM_BANNER_AD_UNIT_ID;
        adMobBottomBanner.rootViewController = localViewController;
        [adMobBottomBanner loadRequest:request];
        [localViewController.view addSubview:adMobBottomBanner];
        adMobBottomBanner.translatesAutoresizingMaskIntoConstraints = NO;
        [view addConstraint:[NSLayoutConstraint constraintWithItem:adMobBottomBanner attribute:NSLayoutAttributeCenterX relatedBy:NSLayoutRelationEqual toItem:view attribute:NSLayoutAttributeCenterX multiplier:1. constant:0]];
        
        [view addConstraint:[NSLayoutConstraint constraintWithItem:adMobTopBanner attribute:NSLayoutAttributeTop relatedBy:NSLayoutRelationEqual toItem:view attribute:NSLayoutAttributeTop multiplier:1. constant:0]];
        [view addConstraint:[NSLayoutConstraint constraintWithItem:adMobBottomBanner attribute:NSLayoutAttributeBottom relatedBy:NSLayoutRelationEqual toItem:view attribute:NSLayoutAttributeBottom multiplier:1. constant:0]];
        
        isAdMobTopBannerDisplayed = true;
        isAdMobBottomBannerDisplayed = true;
    }
}

-( void )hideAdMobBanner:( int ) position
{
    switch ( position )
    {
        case ADBANNERPOSITION_BOTTOM:
            [adMobBottomBanner removeFromSuperview];
            isAdMobBottomBannerDisplayed = false;
            
            break;
            
        case ADBANNERPOSITION_TOP:
            [adMobTopBanner removeFromSuperview];
            isAdMobTopBannerDisplayed = false;
            
            break;
            
        case ADBANNERPOSITION_BOTH:
            [adMobBottomBanner removeFromSuperview];
            isAdMobBottomBannerDisplayed = false;
            [adMobTopBanner removeFromSuperview];
            isAdMobTopBannerDisplayed = false;
            
            break;
    }
}

-( void )showAdMobFullscreenAd
{
    if ( isAdMobFullscreenLoaded )
    {
        [adMobInterstitial presentFromRootViewController:localViewController];
        
        [self requestAdMobFullscreenAd];
    }
}

-( void )requestAdMobFullscreenAd
{
    adMobInterstitial = [[GADInterstitial alloc] initWithAdUnitID:SCH_AD_MOB_FULLSCREEN_AD_UNIT_ID];
    adMobInterstitial.delegate = self;
    GADRequest *request = [self createRequest];
    [adMobInterstitial loadRequest:request];
}

-( void )interstitialDidReceiveAd:( GADInterstitial * )ad
{
    isAdMobFullscreenLoaded = true;
}

#endif

#pragma mark - MOPUB

#if SCH_IS_MOPUB_ENABLED == true

// Banner Ad
-( void )showMopubBanner
{
    if ( !isMopubBannerDisplayed )
    {
        self.moPubAdView = [[MPAdView alloc] initWithAdUnitId:SCH_MOPUB_BANNER_AD_UNIT size:MOPUB_BANNER_SIZE];
        self.moPubAdView.delegate = self;
        
        self.moPubAdView.translatesAutoresizingMaskIntoConstraints = NO;
        
        self.moPubAdView.frame = CGRectMake( ( view.bounds.size.width - MOPUB_BANNER_SIZE.width ) / 2,
                                       0,
                                       MOPUB_BANNER_SIZE.width,
                                       MOPUB_BANNER_SIZE.height );
        
        [localViewController.view addSubview:self.moPubAdView];

        [self.moPubAdView loadAd];
        
        isMopubBannerDisplayed = true;
    }
}

-( void )hideMopubBanner
{
    [self.moPubAdView removeFromSuperview];
    isMopubBannerDisplayed = false;
}

-( UIViewController * )viewControllerForPresentingModalView
{
    return localViewController;
}

// Interstitial Ad
-( void )showMoPubFullscreenAd
{
    [self.moPubinterstitial showFromViewController:localViewController];
}

#endif

#pragma mark - EVERYPLAY

#if SCH_IS_EVERYPLAY_ENABLED == true
-( void )setupEveryplay
{
    // Initialize Everyplay SDK with our client id and secret.
    // These can be created at https://developers.everyplay.com
    [Everyplay setClientId:SCH_EVERYPLAY_CLIENT_ID clientSecret:SCH_EVERYPLAY_CLIENT_SECRET redirectURI:@"https://m.everyplay.com/auth"];
    
    // Tell Everyplay to use our rootViewController for presenting views and for delegate calls.
    [Everyplay initWithDelegate:appController.viewController andParentViewController:appController.window.rootViewController];
}

-( void )showEveryplay
{ [[Everyplay sharedInstance] showEveryplay]; }

-( void )recordEveryplayVideo
{
    if ( [[[Everyplay sharedInstance] capture] isRecording] )
    {
        [[[Everyplay sharedInstance] capture] stopRecording];
        [[Everyplay sharedInstance] showEveryplaySharingModal];
    }
    else
    { [[[Everyplay sharedInstance] capture] startRecording]; }
}

-( void )playLastEveryplayVideoRecording
{ [[Everyplay sharedInstance] playLastRecording]; }
#endif

#pragma mark - GOOGLE_ANALYTICS

#if SCH_IS_GOOGLE_ANALYTICS_ENABLED == true
-( void )setGAScreenName:( NSString * )screenString
{
    [tracker set:kGAIScreenName value:screenString];
    [tracker send:[[GAIDictionaryBuilder createScreenView] build]];
}

-( void )setGADispatchInterval:( int )dispatchInterval
{ [GAI sharedInstance].dispatchInterval = dispatchInterval; }

-( void )sendGAEvent:( NSString * ) category: ( NSString * ) action: ( NSString * ) label: ( NSNumber * ) value
{
    [tracker send:[[GAIDictionaryBuilder createEventWithCategory:category     // Event category (required)
                                                          action:action  // Event action (required)
                                                           label:label          // Event label
                                                           value:value] build]];    // Event value
}
#endif

#pragma mark - AD_COLONY
     
#if SCH_IS_ADCOLONY_ENABLED == true
-( void )showV4VCAC:( BOOL ) withPreOP andPostOp: ( BOOL ) withPostOp
{
    [AdColony playVideoAdForZone:SCH_ADCOLONY_ZONE_ID
                    withDelegate:nil
                withV4VCPrePopup:withPreOP
                andV4VCPostPopup:withPostOp];
}

- ( void ) onAdColonyV4VCReward:( BOOL )success currencyName:( NSString * )currencyName currencyAmount:( int )amount inZone:( NSString * )zoneID
{
    [IOSResults videoWasViewedAdcolony:success];
}
#endif

#pragma mark - VUNGLE

#if SCH_IS_VUNGLE_ENABLED == true
-( void )showV4VCV:( BOOL )isIncentivised
{
    VungleSDK *sdk = [VungleSDK sharedSDK];
    
    // Dict to set custom ad options
    NSDictionary *options = @{VunglePlayAdOptionKeyIncentivized: @(isIncentivised)};
    
    NSError *error;

    [sdk playAd:localViewController withOptions:options error:&error];
    if ( error )
    {
        NSLog( @"Error encountered playing ad: %@", error );
    }
}

-( void )vungleSDKwillCloseAdWithViewInfo:( NSDictionary * )viewInfo willPresentProductSheet:( BOOL )willPresentProductSheet
{
    NSLog( viewInfo[@"completedView"] ? @"true" : @"false" );

    [IOSResults videoWasViewedVungle:[[viewInfo objectForKey:@"completedView"] boolValue]];
}

-( void )vungleSDKwillShowAd
{
    NSLog( @"An ad is about to be played!" );
    
    [IOSResults vungleSDKwillShowAd];
}
#endif


#pragma mark - WECHAT

#if SCH_IS_WECHAT_ENABLED == true
-( void )sendTextContentToWeChat:( NSString * )msgString
{
    SendMessageToWXReq* req = [[[SendMessageToWXReq alloc] init]autorelease];
    req.text = msgString;
    req.bText = YES;
    req.scene = WXSceneSession;
    //WXSceneSession  = 0,        /**< 聊天界面    */
    //WXSceneTimeline = 1,        /**< 朋友圈      */
    //WXSceneFavorite = 2,        /**< 收藏       */
    [WXApi sendReq:req];
}

-( void )sendThumbImage:( NSString * ) thumbImgPath andShareImgToWeChat:( NSString * ) imgPath
{
    WXMediaMessage *message = [WXMediaMessage message];
    //thumb img path
    [message setThumbImage:[UIImage imageNamed:thumbImgPath]];
    WXImageObject *ext = [WXImageObject object];
    //real share img
    //make the format of API
    NSString *imgType = [imgPath pathExtension];
    NSString *imgPathWithoutExt = [imgPath stringByDeletingPathExtension];
    NSString *filePath = [[NSBundle mainBundle] pathForResource:imgPathWithoutExt ofType:imgType];
    ext.imageData = [NSData dataWithContentsOfFile:filePath];
    
    message.mediaObject = ext;
    message.mediaTagName = @"WECHAT_TAG_JUMP_APP";
    message.messageExt = @"msgExt";
    message.messageAction = @"<action>dotalist</action>";
    
    SendMessageToWXReq* req = [[[SendMessageToWXReq alloc] init]autorelease];
    req.bText = NO;
    req.message = message;
    req.scene = WXSceneSession;
    
    [WXApi sendReq:req];
}

-( void )sendLinkWithThumbImg:( NSString * ) thumbImgPath andMsgTitle:( NSString * ) msgTitle andMsgDescription:( NSString * ) msgDes andURLToWeChat:( NSString * ) url
{
    WXMediaMessage *message = [WXMediaMessage message];
    message.title = msgTitle;
    message.description = msgDes;
    [message setThumbImage:[UIImage imageNamed:thumbImgPath]];
    
    WXWebpageObject *ext = [WXWebpageObject object];
    ext.webpageUrl = url;
    
    message.mediaObject = ext;
    message.mediaTagName = @"WECHAT_TAG_JUMP_SHOWRANK";
    
    SendMessageToWXReq* req = [[[SendMessageToWXReq alloc] init]autorelease];
    req.bText = NO;
    req.message = message;
    req.scene = WXSceneSession;
    [WXApi sendReq:req];
}

-( void ) sendMusicContentWithTitle:( NSString * ) msgTitle andDescription:( NSString * )msgDescription andThumbImg:( NSString * ) thumbImg andMusicUrl:( NSString * ) musicUrl andMusicDataUrl:( NSString * ) musicDataURL
{
    WXMediaMessage *message = [WXMediaMessage message];
    message.title = msgTitle;
    message.description = msgDescription;
    [message setThumbImage:[UIImage imageNamed:thumbImg]];
    WXMusicObject *ext = [WXMusicObject object];
    ext.musicUrl = musicUrl;
    ext.musicDataUrl = musicDataURL;
    
    message.mediaObject = ext;
    
    SendMessageToWXReq* req = [[[SendMessageToWXReq alloc] init]autorelease];
    req.bText = NO;
    req.message = message;
    req.scene = WXSceneSession;
    
    [WXApi sendReq:req];
}

-( void ) sendVideoContentWithTitle:( NSString * ) msgTitle andDescription:( NSString * )msgDescription andThumbImg:( NSString * ) thumbImg andVideoUrl:( NSString * ) videoUrl
{
    WXMediaMessage *message = [WXMediaMessage message];
    message.title = msgTitle;
    message.description = msgDescription;
    [message setThumbImage:[UIImage imageNamed:thumbImg]];
    
    WXVideoObject *ext = [WXVideoObject object];
    ext.videoUrl = videoUrl;
    
    message.mediaObject = ext;
    
    SendMessageToWXReq* req = [[[SendMessageToWXReq alloc] init]autorelease];
    req.bText = NO;
    req.message = message;
    req.scene = WXSceneSession;
    
    [WXApi sendReq:req];
}
#endif

#if SCH_IS_NOTIFICATIONS_ENABLED == true
-( void )scheduleLocalNotification:( NSTimeInterval ) delay andNotificationText:( NSString * ) textToDisplay andNotificationTitle:( NSString * ) notificationTitle addNotificationTag:( int ) notificationTag
{
    NSDate *alarmTime = [[NSDate date] dateByAddingTimeInterval:delay];
    UILocalNotification *notifyAlarm = [[UILocalNotification alloc] init];
    
    if ( notifyAlarm )
    {
        if ( [[UIDevice currentDevice].systemVersion floatValue] >= 8.0 )
        {
            notifyAlarm.alertTitle = notificationTitle;
        }
        notifyAlarm.fireDate = alarmTime;
        notifyAlarm.timeZone = [NSTimeZone defaultTimeZone];
        notifyAlarm.soundName = UILocalNotificationDefaultSoundName;
        notifyAlarm.alertBody = textToDisplay;
        
        //NSDictionary * infoDict = [NSDictionary dictionaryWithObject:notificationTag forKey:@"notificationID"];
        //notifyAlarm.userInfo = infoDict;
        
        [[UIApplication sharedApplication] scheduleLocalNotification: notifyAlarm];
    }
}

-( void )scheduleLocalNotification:( NSTimeInterval ) delay andNotificationText:( NSString * ) textToDisplay andNotificationTitle:( NSString * ) notificationTitle andNotificationAction:( NSString * )notificationAction addNotificationTag:( int ) notificationTag
{
    NSDate *alarmTime = [[NSDate date] dateByAddingTimeInterval:delay];
    UILocalNotification *notifyAlarm = [[UILocalNotification alloc] init];
    
    if ( notifyAlarm )
    {
        if ( [[UIDevice currentDevice].systemVersion floatValue] >= 8.0 )
        {
            notifyAlarm.alertTitle = notificationTitle;
        }
        notifyAlarm.fireDate = alarmTime;
        notifyAlarm.timeZone = [NSTimeZone defaultTimeZone];
        notifyAlarm.soundName = UILocalNotificationDefaultSoundName;
        notifyAlarm.alertBody = textToDisplay;
        notifyAlarm.alertAction = notificationAction;
        
        //NSDictionary * infoDict = [NSDictionary dictionaryWithObject:notificationTag forKey:@"notificationID"];
        //notifyAlarm.userInfo = infoDict;
        
        [[UIApplication sharedApplication] scheduleLocalNotification: notifyAlarm];
    }
}

-( void )scheduleLocalNotification:( NSTimeInterval )delay andNotificationText:( NSString * )textToDisplay andNotificationTitle:( NSString * )notificationTitle andRepeatInterval:( int )repeatInterval addNotificationTag:( int ) notificationTag
{
    NSDate *alarmTime = [[NSDate date] dateByAddingTimeInterval:delay];
    UILocalNotification *notifyAlarm = [[UILocalNotification alloc] init];
    
    if ( notifyAlarm )
    {
        if ( [[UIDevice currentDevice].systemVersion floatValue] >= 8.0 )
        {
            notifyAlarm.alertTitle = notificationTitle;
        }
        notifyAlarm.fireDate = alarmTime;
        notifyAlarm.timeZone = [NSTimeZone defaultTimeZone];
        notifyAlarm.soundName = UILocalNotificationDefaultSoundName;
        notifyAlarm.alertBody = textToDisplay;
        notifyAlarm.repeatInterval = [self convertRepeatIntervalToCalendarUnit:repeatInterval];
        
        //NSDictionary * infoDict = [NSDictionary dictionaryWithObject:notificationTag forKey:@"notificationID"];
        //notifyAlarm.userInfo = infoDict;
        
        [[UIApplication sharedApplication] scheduleLocalNotification: notifyAlarm];
    }
}

-( void )scheduleLocalNotification:( NSTimeInterval )delay andNotificationText:( NSString * )textToDisplay andNotificationTitle:( NSString * )notificationTitle andNotificationAction:( NSString * )notificationAction andRepeatInterval:( int )repeatInterval addNotificationTag:( int ) notificationTag
{
    NSDate *alarmTime = [[NSDate date] dateByAddingTimeInterval:delay];
    UILocalNotification *notifyAlarm = [[UILocalNotification alloc] init];
    
    if ( notifyAlarm )
    {
        if ( [[UIDevice currentDevice].systemVersion floatValue] >= 8.0 )
        {
            notifyAlarm.alertTitle = notificationTitle;
        }
        notifyAlarm.fireDate = alarmTime;
        notifyAlarm.timeZone = [NSTimeZone defaultTimeZone];
        notifyAlarm.soundName = UILocalNotificationDefaultSoundName;
        notifyAlarm.alertBody = textToDisplay;
        notifyAlarm.alertAction = notificationAction;
        notifyAlarm.repeatInterval = [self convertRepeatIntervalToCalendarUnit:repeatInterval];
        
        //NSDictionary * infoDict = [NSDictionary dictionaryWithObject:notificationTag forKey:@"notificationID"];
        //notifyAlarm.userInfo = infoDict;
        
        [[UIApplication sharedApplication] scheduleLocalNotification: notifyAlarm];
    }
}

-( NSCalendarUnit )convertRepeatIntervalToCalendarUnit:( int )repeatInterval
{
    NSCalendarUnit *calendarUnit;
    
    switch ( repeatInterval )
    {
        case CALENDAR_UNIT_MINUTE:
            calendarUnit = NSMinuteCalendarUnit;
            
            break;
        case CALENDAR_UNIT_HOURLY:
            calendarUnit = NSHourCalendarUnit;
            
            break;
        case CALENDAR_UNIT_DAILY:
            calendarUnit = NSDayCalendarUnit;
            
            break;
        case CALENDAR_UNIT_WEEKLY:
            calendarUnit = NSWeekCalendarUnit;
            
            break;
        case CALENDAR_UNIT_MONTHLY:
            calendarUnit = NSMonthCalendarUnit;
            
            break;
        case CALENDAR_UNIT_YEARLY:
            calendarUnit = NSYearCalendarUnit;
            
            break;
            
        default:
            break;
    }
    
    return calendarUnit;
}

-( void )unscheduleAllLocalNotifications
{
    UIApplication *app = [UIApplication sharedApplication];
    NSArray *oldNotifications = [app scheduledLocalNotifications];
    
    if ( [oldNotifications count] > 0 )
    { [app cancelAllLocalNotifications]; }
}

-( void )unscheduleLocalNotification:( int )notificationTag
{
    UIApplication *app = [UIApplication sharedApplication];
    NSArray *oldNotifications = [app scheduledLocalNotifications];
    
    for ( NSUInteger i = 0; i < oldNotifications.count; i++ )
    {
        UILocalNotification *scheduledNotification = oldNotifications[i];

        //if ( [scheduledNotification.alertTitle isEqualToString:notificationTitle] )
        if ( [[scheduledNotification.userInfo objectForKey:@"notificationID"] isEqualToNumber:notificationTag] )
        {
            [app cancelLocalNotification:scheduledNotification];
        }
    }
}
#endif


@end
