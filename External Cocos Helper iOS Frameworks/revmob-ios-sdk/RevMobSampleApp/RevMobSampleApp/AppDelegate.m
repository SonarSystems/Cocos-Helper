#import "AppDelegate.h"
#import "SampleAppViewController.h"
#import <RevMobAds/RevMobAds.h>

@implementation AppDelegate

@synthesize window = _window;
@synthesize viewController = _viewController;

- (void)dealloc {
    [_window release];
    [_viewController release];
    [super dealloc];
}

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    [RevMobAds startSessionWithAppID:REVMOB_ID andDelegate:self];
    
    self.window = [[[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]] autorelease];

    self.viewController = [[[SampleAppViewController alloc] init] autorelease];
    [self.window setRootViewController:self.viewController];

    [self.window makeKeyAndVisible];

    return YES;
}

- (void)applicationDidBecomeActive:(UIApplication *)application {
}

-(void)revmobSessionIsStarted {
    NSLog(@"[RevMob Sample App] Session is started.");
}

- (void)revmobSessionNotStartedWithError:(NSError *)error {
    NSLog(@"[RevMob Sample App] Session failed to start: %@", error);
}

- (void)revmobAdDidFailWithError:(NSError *)error {
}


@end
