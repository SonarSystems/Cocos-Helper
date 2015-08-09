//
//  NSObject+IOSResults.h
//  RescueGiant
//
//  Created by AppleProgrammer on 03.06.15.
//
//

#import <Foundation/Foundation.h>

@interface IOSResults : NSObject

+( void )videoWasViewedVungle:( BOOL ) result;
+( void )vungleSDKwillShowAd; // used for handling stuff before playing video ad
+( void )videoWasViewedAdcolony:( BOOL ) result;

@end
