//
//  MPRewardedVideo.m
//  MoPubSDK
//
//  Copyright (c) 2015 MoPub. All rights reserved.
//

#import "MPRewardedVideo.h"
#import "MPLogging.h"
#import "MPRewardedVideoAdManager.h"
#import "MPInstanceProvider.h"
#import "MPRewardedVideoError.h"

static MPRewardedVideo *gSharedInstance = nil;

@interface MPRewardedVideo () <MPRewardedVideoAdManagerDelegate>

@property (nonatomic, strong) NSMutableDictionary *rewardedVideoAdManagers;
@property (nonatomic, weak) id<MPRewardedVideoDelegate> delegate;

+ (MPRewardedVideo *)sharedInstance;

@end

@implementation MPRewardedVideo

- (instancetype)init
{
    if (self = [super init]) {
        _rewardedVideoAdManagers = [[NSMutableDictionary alloc] init];
    }

    return self;
}

+ (void)loadRewardedVideoAdWithAdUnitID:(NSString *)adUnitID withMediationSettings:(NSArray *)mediationSettings
{
    MPRewardedVideo *sharedInstance = [[self class] sharedInstance];

    if (![adUnitID length]) {
        NSError *error = [NSError errorWithDomain:MoPubRewardedVideoAdsSDKDomain code:MPRewardedVideoAdErrorInvalidAdUnitID userInfo:nil];
        [sharedInstance.delegate rewardedVideoAdDidFailToLoadForAdUnitID:adUnitID error:error];
        return;
    }

    MPRewardedVideoAdManager *adManager = sharedInstance.rewardedVideoAdManagers[adUnitID];

    if (!adManager) {
        adManager = [[MPInstanceProvider sharedProvider] buildRewardedVideoAdManagerWithAdUnitID:adUnitID delegate:sharedInstance];
        sharedInstance.rewardedVideoAdManagers[adUnitID] = adManager;
    }

    adManager.mediationSettings = mediationSettings;
    
    [adManager loadRewardedVideoAd];
}

+ (BOOL)hasAdAvailableForAdUnitID:(NSString *)adUnitID
{
    MPRewardedVideo *sharedInstance = [[self class] sharedInstance];
    MPRewardedVideoAdManager *adManager = sharedInstance.rewardedVideoAdManagers[adUnitID];

    return [adManager hasAdAvailable];
}

+ (void)presentRewardedVideoAdForAdUnitID:(NSString *)adUnitID fromViewController:(UIViewController *)viewController
{
    MPRewardedVideo *sharedInstance = [[self class] sharedInstance];
    MPRewardedVideoAdManager *adManager = sharedInstance.rewardedVideoAdManagers[adUnitID];

    if (!adManager) {
        MPLogWarn(@"The rewarded video could not be shown: "
                  @"no ads have been loaded for adUnitID: %@", adUnitID);

        return;
    }

    if (!viewController) {
        MPLogWarn(@"The rewarded video could not be shown: "
                  @"a nil view controller was passed to -presentRewardedVideoAdForAdUnitID:fromViewController:.");

        return;
    }

    if (![viewController.view.window isKeyWindow]) {
        MPLogWarn(@"Attempting to present a rewarded video ad in non-key window. The ad may not render properly.");
    }

    [adManager presentRewardedVideoAdFromViewController:viewController];
}

#pragma mark - Private

+ (MPRewardedVideo *)sharedInstance
{
    static dispatch_once_t once;

    dispatch_once(&once, ^{
        gSharedInstance = [[self alloc] init];
    });

    return gSharedInstance;
}

// This is private as we require the developer to initialize rewarded video through the MoPub object.
+ (void)initializeWithDelegate:(id<MPRewardedVideoDelegate>)delegate
{
    MPRewardedVideo *sharedInstance = [[self class] sharedInstance];

    // Do not allow calls to initialize twice.
    if (sharedInstance.delegate) {
        MPLogWarn(@"Attempting to initialize MPRewardedVideo when it has already been initialized.");
    } else {
        sharedInstance.delegate = delegate;
    }
}

#pragma mark - MPRewardedVideoAdManagerDelegate

- (void)rewardedVideoDidLoadForAdManager:(MPRewardedVideoAdManager *)manager
{
    if ([self.delegate respondsToSelector:@selector(rewardedVideoAdDidLoadForAdUnitID:)]) {
        [self.delegate rewardedVideoAdDidLoadForAdUnitID:manager.adUnitID];
    }
}

- (void)rewardedVideoDidFailToLoadForAdManager:(MPRewardedVideoAdManager *)manager error:(NSError *)error
{
    if ([self.delegate respondsToSelector:@selector(rewardedVideoAdDidFailToLoadForAdUnitID:error:)]) {
        [self.delegate rewardedVideoAdDidFailToLoadForAdUnitID:manager.adUnitID error:error];
    }
}

- (void)rewardedVideoDidExpireForAdManager:(MPRewardedVideoAdManager *)manager
{
    if ([self.delegate respondsToSelector:@selector(rewardedVideoAdDidExpireForAdUnitID:)]) {
        [self.delegate rewardedVideoAdDidExpireForAdUnitID:manager.adUnitID];
    }
}

- (void)rewardedVideoDidFailToPlayForAdManager:(MPRewardedVideoAdManager *)manager error:(NSError *)error
{
    if ([self.delegate respondsToSelector:@selector(rewardedVideoAdDidFailToPlayForAdUnitID:error:)]) {
        [self.delegate rewardedVideoAdDidFailToPlayForAdUnitID:manager.adUnitID error:error];
    }
}

- (void)rewardedVideoWillAppearForAdManager:(MPRewardedVideoAdManager *)manager
{
    if ([self.delegate respondsToSelector:@selector(rewardedVideoAdWillAppearForAdUnitID:)]) {
        [self.delegate rewardedVideoAdWillAppearForAdUnitID:manager.adUnitID];
    }
}

- (void)rewardedVideoDidAppearForAdManager:(MPRewardedVideoAdManager *)manager
{
    if ([self.delegate respondsToSelector:@selector(rewardedVideoAdDidAppearForAdUnitID:)]) {
        [self.delegate rewardedVideoAdDidAppearForAdUnitID:manager.adUnitID];
    }
}

- (void)rewardedVideoWillDisappearForAdManager:(MPRewardedVideoAdManager *)manager
{
    if ([self.delegate respondsToSelector:@selector(rewardedVideoAdWillDisappearForAdUnitID:)]) {
        [self.delegate rewardedVideoAdWillDisappearForAdUnitID:manager.adUnitID];
    }
}

- (void)rewardedVideoDidDisappearForAdManager:(MPRewardedVideoAdManager *)manager
{
    if ([self.delegate respondsToSelector:@selector(rewardedVideoAdDidDisappearForAdUnitID:)]) {
        [self.delegate rewardedVideoAdDidDisappearForAdUnitID:manager.adUnitID];
    }

    // Since multiple ad units may be attached to the same network, we should notify the custom events (which should then notify the application)
    // that their ads may not be available anymore since another ad unit might have "played" their ad. We go through and notify all ad managers
    // that are of the type of ad that is playing now.
    Class customEventClass = manager.customEventClass;
    
    for (id key in self.rewardedVideoAdManagers) {
        MPRewardedVideoAdManager *adManager = self.rewardedVideoAdManagers[key];
        
        if (adManager != manager && adManager.customEventClass == customEventClass) {
            [adManager handleAdPlayedForCustomEventNetwork];
        }
    }
}

- (void)rewardedVideoDidReceiveTapEventForAdManager:(MPRewardedVideoAdManager *)manager
{
    if ([self.delegate respondsToSelector:@selector(rewardedVideoAdDidReceiveTapEventForAdUnitID:)]) {
        [self.delegate rewardedVideoAdDidReceiveTapEventForAdUnitID:manager.adUnitID];
    }
}

- (void)rewardedVideoWillLeaveApplicationForAdManager:(MPRewardedVideoAdManager *)manager
{
    if ([self.delegate respondsToSelector:@selector(rewardedVideoAdWillLeaveApplicationForAdUnitID:)]) {
        [self.delegate rewardedVideoAdWillLeaveApplicationForAdUnitID:manager.adUnitID];
    }
}

- (void)rewardedVideoShouldRewardUserForAdManager:(MPRewardedVideoAdManager *)manager reward:(MPRewardedVideoReward *)reward
{
    if ([self.delegate respondsToSelector:@selector(rewardedVideoAdShouldRewardForAdUnitID:reward:)]) {
        [self.delegate rewardedVideoAdShouldRewardForAdUnitID:manager.adUnitID reward:reward];
    }
}

@end
