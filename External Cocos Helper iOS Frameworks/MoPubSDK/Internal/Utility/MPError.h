//
//  MPAdRequestError.h
//  MoPub
//
//  Copyright (c) 2012 MoPub. All rights reserved.
//

#import <Foundation/Foundation.h>

extern NSString * const kMPErrorDomain;

typedef enum {
    MPErrorUnknown = -1,
    MPErrorNoInventory = 0,
    MPErrorAdUnitWarmingUp = 1,
    MPErrorNetworkTimedOut = 4,
    MPErrorServerError = 8,
    MPErrorAdapterNotFound = 16,
    MPErrorAdapterInvalid = 17,
    MPErrorAdapterHasNoInventory = 18
} MPErrorCode;

@interface MPError : NSError

+ (MPError *)errorWithCode:(MPErrorCode)code;

@end
