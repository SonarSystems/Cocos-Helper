#import <UIKit/UIKit.h>
#import <RevMobAds/RevMobAds.h>
#import <RevMobAds/RevMobAdsDelegate.h>

@class SampleAppViewController;

@interface AppDelegate : UIResponder <UIApplicationDelegate, RevMobAdsDelegate>

@property (strong, nonatomic) UIWindow *window;

@property (strong, nonatomic) SampleAppViewController *viewController;

@end
