//
//  MPRewardedVideoReward.m
//  MoPubSDK
//
//  Copyright (c) 2015 MoPub. All rights reserved.
//

#import "MPRewardedVideoReward.h"

NSString *const kMPRewardedVideoRewardCurrencyTypeUnspecified = @"MPMoPubRewardedVideoRewardCurrencyTypeUnspecified";
NSInteger const kMPRewardedVideoRewardCurrencyAmountUnspecified = NSIntegerMin;

@implementation MPRewardedVideoReward

- (instancetype)initWithCurrencyType:(NSString *)currencyType amount:(NSNumber *)amount
{
    if (self = [super init]) {
        _currencyType = [currencyType copy];
        _amount = amount;
    }

    return self;
}

- (instancetype)initWithCurrencyAmount:(NSNumber *)amount
{
    return [self initWithCurrencyType:kMPRewardedVideoRewardCurrencyTypeUnspecified amount:amount];
}

@end
