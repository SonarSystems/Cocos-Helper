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
    view = appController.viewController.view;
    
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
#endif
    
#if SCH_IS_EVERYPLAY_ENABLED == true
    [self setupEveryplay];
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
        if ( position == ADBANNERPOSITION_TOP )
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
        [appController.viewController dismissViewControllerAnimated:YES completion:NULL];
    };
    [appController.viewController presentViewController:slVC animated:YES completion:NULL];
}

// share to twitter (requires the message to be sent and the image path, both of which can be empty strings)
-( void )shareViaTwitter: ( NSString * ) message: ( NSString * ) imagePath
{
    SLComposeViewController *slVC = [SLComposeViewController composeViewControllerForServiceType:SLServiceTypeTwitter];
    [slVC addImage:[UIImage imageNamed:imagePath]];
    [slVC setInitialText:message];
    slVC.completionHandler = ^( SLComposeViewControllerResult result )
    {
        [appController.viewController dismissViewControllerAnimated:YES completion:NULL];
    };
    [appController.viewController presentViewController:slVC animated:YES completion:NULL];
}

-( void )shareWithString:( NSString *) message: ( NSString * ) imagePath
{
    UIImage *image = [[UIImage alloc] initWithData:[NSData dataWithContentsOfFile:imagePath]];
    UIActivityViewController *activityVC = [[UIActivityViewController alloc] initWithActivityItems:@[message, imagePath] applicationActivities:nil];
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
        CGRect bounds = appController.viewController.view.bounds;
        [self.popover presentPopoverFromRect:CGRectMake(CGRectGetMidX(bounds), CGRectGetMidY(bounds), 10, 10) inView:appController.viewController.view permittedArrowDirections:UIPopoverArrowDirectionAny animated:YES];
    }
    else
    {
        [appController.viewController presentViewController:activityVC animated:YES completion:nil];
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
            [appController.viewController presentViewController:viewController animated:YES completion:nil];
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
    
    [GKScore reportScores:@[s] withCompletionHandler:^(NSError *error) {
        if ( error != nil)
        { NSLog( @"%@", [error localizedDescription] ); }
 
    }];
}

-( void )gameCenterShowLeaderboard
{
    
    if (_gameCenterEnabled) {
        
        // Init the following view controller object.
        GKGameCenterViewController *gcViewController = [[GKGameCenterViewController alloc] init];
        
        // Set self as its delegate.
        gcViewController.gameCenterDelegate = self;
        gcViewController.viewState = GKGameCenterViewControllerStateLeaderboards;
        //gcViewController.leaderboardIdentifier = _leaderboardIdentifier;
        
        [appController.viewController presentViewController:gcViewController animated:YES completion:nil];
 
    } else {
        
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
    
    [appController.viewController presentViewController:gcViewController animated:YES completion:nil];
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
            if( [ach.identifier isEqualToString:achievementID] )
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
-( void )showAdMobBanner:( int ) position
{
    if ( !isAdMobTopBannerDisplayed && ADBANNERPOSITION_TOP == position )
    {
        adMobTopBanner = [[GADBannerView alloc] initWithAdSize:kGADAdSizeBanner];
        adMobTopBanner.adUnitID = SCH_AD_MOB_TOP_BANNER_AD_UNIT_ID;
        adMobTopBanner.rootViewController = appController.viewController;
        GADRequest *request = [GADRequest request];
        request.testDevices = @[SCH_AD_MOB_TEST_DEVICE];
        [adMobTopBanner loadRequest:request];
        [appController.viewController.view addSubview:adMobTopBanner];
        adMobTopBanner.translatesAutoresizingMaskIntoConstraints = NO;
        [view addConstraint:[NSLayoutConstraint constraintWithItem:adMobTopBanner attribute:NSLayoutAttributeCenterX relatedBy:NSLayoutRelationEqual toItem:view attribute:NSLayoutAttributeCenterX multiplier:1. constant:0]];
        
            [view addConstraint:[NSLayoutConstraint constraintWithItem:adMobTopBanner attribute:NSLayoutAttributeTop relatedBy:NSLayoutRelationEqual toItem:view attribute:NSLayoutAttributeTop multiplier:1. constant:0]];
            
            isAdMobTopBannerDisplayed = true;
        
    }
    else if ( !isAdMobBottomBannerDisplayed && ADBANNERPOSITION_BOTTOM == position )
    {
        adMobBottomBanner = [[GADBannerView alloc] initWithAdSize:kGADAdSizeBanner];
        adMobBottomBanner.adUnitID = SCH_AD_MOB_BOTTOM_BANNER_AD_UNIT_ID;
        adMobBottomBanner.rootViewController = appController.viewController;
        GADRequest *request = [GADRequest request];
        request.testDevices = @[SCH_AD_MOB_TEST_DEVICE];
        [adMobBottomBanner loadRequest:request];
        [appController.viewController.view addSubview:adMobBottomBanner];
        adMobBottomBanner.translatesAutoresizingMaskIntoConstraints = NO;
        [view addConstraint:[NSLayoutConstraint constraintWithItem:adMobBottomBanner attribute:NSLayoutAttributeCenterX relatedBy:NSLayoutRelationEqual toItem:view attribute:NSLayoutAttributeCenterX multiplier:1. constant:0]];
        
        [view addConstraint:[NSLayoutConstraint constraintWithItem:adMobBottomBanner attribute:NSLayoutAttributeBottom relatedBy:NSLayoutRelationEqual toItem:view attribute:NSLayoutAttributeBottom multiplier:1. constant:0]];
        
        isAdMobBottomBannerDisplayed = true;
    }
    else if ( ADBANNERPOSITION_BOTH == position )
    {
        adMobTopBanner = [[GADBannerView alloc] initWithAdSize:kGADAdSizeBanner];
        adMobTopBanner.adUnitID = SCH_AD_MOB_TOP_BANNER_AD_UNIT_ID;
        adMobTopBanner.rootViewController = appController.viewController;
        GADRequest *request = [GADRequest request];
        request.testDevices = @[SCH_AD_MOB_TEST_DEVICE];
        [adMobTopBanner loadRequest:request];
        [appController.viewController.view addSubview:adMobTopBanner];
        adMobTopBanner.translatesAutoresizingMaskIntoConstraints = NO;
        [view addConstraint:[NSLayoutConstraint constraintWithItem:adMobTopBanner attribute:NSLayoutAttributeCenterX relatedBy:NSLayoutRelationEqual toItem:view attribute:NSLayoutAttributeCenterX multiplier:1. constant:0]];
        
        adMobBottomBanner = [[GADBannerView alloc] initWithAdSize:kGADAdSizeBanner];
        adMobBottomBanner.adUnitID = SCH_AD_MOB_BOTTOM_BANNER_AD_UNIT_ID;
        adMobBottomBanner.rootViewController = appController.viewController;
        request.testDevices = @[SCH_AD_MOB_TEST_DEVICE];
        [adMobBottomBanner loadRequest:request];
        [appController.viewController.view addSubview:adMobBottomBanner];
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
        [adMobInterstitial presentFromRootViewController:appController.viewController];
        
        [self requestAdMobFullscreenAd];
    }
}

-( void )requestAdMobFullscreenAd
{
    adMobInterstitial = [[GADInterstitial alloc] init];
    adMobInterstitial.adUnitID = SCH_AD_MOB_FULLSCREEN_AD_UNIT_ID;
    adMobInterstitial.delegate = self;
    GADRequest *request = [GADRequest request];
    request.testDevices = @[SCH_AD_MOB_TEST_DEVICE];
    [adMobInterstitial loadRequest:request];
}

-( void )interstitialDidReceiveAd:( GADInterstitial * )ad
{ isAdMobFullscreenLoaded = true; }

#endif

#pragma mark - MOPUB

#if SCH_IS_MOPUB_ENABLED == true

// Banner Ad
-( void )showMopubBanner
{
    if ( !isMopubBannerDisplayed )
    {
        self.adView = [[MPAdView alloc] initWithAdUnitId:SCH_MOPUB_BANNER_AD_UNIT size:MOPUB_BANNER_SIZE];
        self.adView.delegate = self;
        
        self.adView.translatesAutoresizingMaskIntoConstraints = NO;
        
        self.adView.frame = CGRectMake( ( view.bounds.size.width - MOPUB_BANNER_SIZE.width ) / 2,
                                       0,
                                       MOPUB_BANNER_SIZE.width,
                                       MOPUB_BANNER_SIZE.height );
        
        [appController.viewController.view addSubview:self.adView];

        [self.adView loadAd];
        
        isMopubBannerDisplayed = true;
    }
}

-( void )hideMopubBanner
{
    [self.adView removeFromSuperview];
    isMopubBannerDisplayed = false;
}

-( UIViewController * )viewControllerForPresentingModalView
{
    return appController.viewController;
}

// Interstitial Ad

-( void )requestLaunchFullscreenAd
{

    self.interstitialLaunch = [MPInterstitialAdController interstitialAdControllerForAdUnitId:SCH_MOPUB_LAUNCH_INTERSTITIAL_AD_UNIT];
    
    [self.interstitialLaunch loadAd];
}

-( void )showLaunchFullscreenAd
{
    if ( self.interstitialLaunch.ready )
    {
        [self.interstitialLaunch showFromViewController:appController.viewController];
    }
}

-( void )requestEndLevelFullscreenAd
{
    self.interstitialEndlevel = [MPInterstitialAdController interstitialAdControllerForAdUnitId:SCH_MOPUB_ENDLEVEL_INTERSTITIAL_AD_UNIT];
    
    [self.interstitialEndlevel loadAd];
}

-( void )showEndLevelFullscreenAd
{
    if ( self.interstitialEndlevel.ready )
    {
        [self.interstitialEndlevel showFromViewController:appController.viewController];
    }
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

-( void )voidTestMethod1;
{
    
}

-( void )voidTestMethod2
{
    
}

-( bool )boolTestMethod1
{
    
}

-( bool )boolTestMethod2
{
    
}

-( int )intTestMethod1
{
    
}

-( int )intTestMethod2
{
    
}

-( float )floatTestMethod1
{
    
}

-( float )floatTestMethod2
{
    
}

@end
