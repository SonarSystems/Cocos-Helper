#import "SampleAppViewController.h"
#import <QuartzCore/QuartzCore.h>

@interface SampleAppViewController () {
    int yCoordinateControl;
}

@property (nonatomic, strong)RevMobFullscreen *fullscreen;
@property (nonatomic, strong)RevMobBannerView *banner;
@property (nonatomic, strong)RevMobBanner *bannerWindow;
@property (nonatomic, strong)RevMobAdLink *link;

@property (strong, nonatomic) UIScrollView *scroll;
@property (assign, nonatomic) CLLocationManager *locationManager;


- (UIImage *)imageWithColor:(UIColor *)color;
- (void)createButtonWithName:(NSString *)name andSelector:(SEL)selector;
- (void)addVerticalSpace;


@end

@implementation SampleAppViewController

- (id)init {
    self = [super init];
    if (self) {
        yCoordinateControl = 10;
        _scroll = [[UIScrollView alloc] initWithFrame:[[UIScreen mainScreen] applicationFrame]];
        _locationManager = [[CLLocationManager alloc] init];
        _locationManager.delegate = self;
    }
    return self;
}

#pragma mark Layout methods

- (void)createButtonWithName:(NSString *)name andSelector:(SEL)selector {
    UIButton *button = [[[UIButton alloc] initWithFrame:CGRectMake(10, yCoordinateControl, 300, 40)] autorelease];
    [button setTitle:name forState:UIControlStateNormal];
    [button addTarget:self action:selector forControlEvents:UIControlEventTouchUpInside];

    UIImage *background1 = [self imageWithColor:[UIColor grayColor]];
    UIImage *background2 = [self imageWithColor:[UIColor lightGrayColor]];
    [button setBackgroundImage:background1 forState:UIControlStateNormal];
    [button setBackgroundImage:background2 forState:UIControlStateSelected];

    button.layer.cornerRadius = 5;
    button.clipsToBounds = YES;

    [self.scroll addSubview:button];
    yCoordinateControl += 50;
}

- (void)addVerticalSpace {
    yCoordinateControl += 20;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor whiteColor];

#ifdef __IPHONE_7_0
    if (floor(NSFoundationVersionNumber) > NSFoundationVersionNumber_iOS_6_1) {
        CGRect statusBar, frame;
        CGRectDivide(self.view.bounds, &statusBar, &frame, 20, CGRectMinYEdge);
        self.scroll.frame = frame;
    } else {
        self.scroll.frame = self.view.bounds;
    }
#else
    self.scroll.frame = self.view.bounds;
#endif

    self.scroll.autoresizingMask = UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight;
    [self.view addSubview:self.scroll];

    [self createButtonWithName:@"Start Session" andSelector:@selector(startSession)];
    [self addVerticalSpace];
    
    [self createButtonWithName:@"Disable Testing mode" andSelector:@selector(disableTestMode)];
    [self createButtonWithName:@"Testing with Ads" andSelector:@selector(testingWithAds)];
    [self createButtonWithName:@"Testing without Ads" andSelector:@selector(testingWithoutAds)];
    [self createButtonWithName:@"Enable Parallax Effect" andSelector:@selector(enableParallaxEffect)];
    [self createButtonWithName:@"Disable Parallax Effect" andSelector:@selector(disableParallaxEffect)];
    [self createButtonWithName:@"Enable Parallax With Background" andSelector:@selector(enableParallaxWithBackgroundEffect)];
    [self createButtonWithName:@"Print Env Info" andSelector:@selector(printEnvironmentInformation)];
    [self addVerticalSpace];
    
    [self createButtonWithName:@"Basic Usage: Fullscreen" andSelector:@selector(basicUsageShowFullscreen)];
    [self createButtonWithName:@"Basic Usage: Banner" andSelector:@selector(basicUsageShowBanner)];
    [self createButtonWithName:@"Basic Usage: Hide banner" andSelector:@selector(basicUsageHideBanner)];
    [self createButtonWithName:@"Basic Usage: Popup" andSelector:@selector(basicUsageShowPopup)];
    [self createButtonWithName:@"Basic Usage: Link" andSelector:@selector(basicUsageOpenAdLink)];
    [self addVerticalSpace];
    
    [self createButtonWithName:@"Show Fullscreen with delegate" andSelector:@selector(showFullscreenWithDelegate)];
    [self createButtonWithName:@"Show Fullscreen for orientations" andSelector:@selector(showFullscreenWithSpecificOrientations)];
    [self createButtonWithName:@"Pre Load Fullscreen" andSelector:@selector(loadFullscreen)];
    [self createButtonWithName:@"Show pre-loaded fullscreen" andSelector:@selector(showPreLoadedFullscreen)];
    [self addVerticalSpace];
    
    [self createButtonWithName:@"Show Banner with custom frame" andSelector:@selector(showBannerWithCustomFrame)];
    [self createButtonWithName:@"Hide Banner with custom frame" andSelector:@selector(hideBannerWithCustomFrame)];

    
    [self createButtonWithName:@"Show Banner Window" andSelector:@selector(showBannerWindow)];
    [self createButtonWithName:@"Banner Window for orientations" andSelector:@selector(showBannerWindowWithSpecificOrientations)];
    [self createButtonWithName:@"Hide Banner Window" andSelector:@selector(hideBannerWindow)];
    [self addVerticalSpace];
    
    [self createButtonWithName:@"Load Ad Link" andSelector:@selector(loadAdLink)];
    [self createButtonWithName:@"Open Ad Link" andSelector:@selector(openAdLink)];
    [self createButtonWithName:@"Add Ad Button" andSelector:@selector(addAdButton)];
    [self addVerticalSpace];
    
    [self createButtonWithName:@"Show Popup" andSelector:@selector(showPopup)];
    [self addVerticalSpace];
    
    [self createButtonWithName:@"Close Sample App" andSelector:@selector(closeSampleApp)];
    [self addVerticalSpace];
    
    [self.scroll setBackgroundColor:[UIColor whiteColor]];

    [self fillUserInfo];

    self.scroll.contentSize = CGSizeMake(320,yCoordinateControl);
}


- (void)fillUserInfo
{
    RevMobAds *revmob = [RevMobAds session];

    revmob.userGender = RevMobUserGenderFemale;
    revmob.userAgeRangeMin = 18;
    revmob.userAgeRangeMax = 21;
    revmob.userBirthday = [NSDate dateWithTimeIntervalSince1970:0];
    revmob.userPage = @"twitter.com/revmob";
    revmob.userInterests = @[@"mobile", @"iPhone", @"apps"];

}

- (void)setUserLocation
{
    RevMobAds *revmob = [RevMobAds session];
    
    BOOL locationAllowed = [CLLocationManager locationServicesEnabled] && ([CLLocationManager authorizationStatus] != kCLAuthorizationStatusDenied);
    
    if (locationAllowed){
        CLLocation *location = self.locationManager.location;
        
        [self.locationManager setDistanceFilter: kCLDistanceFilterNone];
        [self.locationManager setDesiredAccuracy: kCLLocationAccuracyHundredMeters];
        [self.locationManager startUpdatingLocation];
        
        [revmob setUserLatitude: location.coordinate.latitude
                  userLongitude: location.coordinate.longitude
         userAccuracy: location.horizontalAccuracy];
     }
}
-(void)locationManager:(CLLocationManager *)manager didChangeAuthorizationStatus:(CLAuthorizationStatus)status{
    [self setUserLocation];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)toInterfaceOrientation {
    // Test with all orientations
    return YES;
    
    // Test only with Portrait mode
    //return (interfaceOrientation == UIInterfaceOrientationPortrait || interfaceOrientation == UIInterfaceOrientationPortraitUpsideDown);
    
    // Test only with Landscape mode
    //return (interfaceOrientation == UIInterfaceOrientationLandscapeLeft || interfaceOrientation == UIInterfaceOrientationLandscapeRight);
}

#pragma mark Methods to test RevMob Ads

- (void)startSession {
    [RevMobAds startSessionWithAppID:REVMOB_ID
     withSuccessHandler:^{
         NSLog(@"Session started with block");
     } andFailHandler:^(NSError *error) {
         NSLog(@"Session failed to start with block");
     }];
}

- (void)disableTestMode {
    [RevMobAds session].testingMode = RevMobAdsTestingModeOff;
}

- (void)testingWithAds {
    [RevMobAds session].testingMode = RevMobAdsTestingModeWithAds;
}

- (void)testingWithoutAds {
    [RevMobAds session].testingMode = RevMobAdsTestingModeWithoutAds;
}

- (void)disableParallaxEffect {
    [RevMobAds session].parallaxMode = RevMobParallaxModeOff;
}

- (void)enableParallaxEffect {
    [RevMobAds session].parallaxMode = RevMobParallaxModeDefault;
}

- (void)enableParallaxWithBackgroundEffect {
    [RevMobAds session].parallaxMode = RevMobParallaxModeWithBackground;
}

- (void)printEnvironmentInformation {
    [[RevMobAds session] printEnvironmentInformation];
}

#pragma mark - Basic Usage -

- (void)basicUsageShowFullscreen {
    [[RevMobAds session] showFullscreen];
}

- (void)basicUsageShowBanner {
    [[RevMobAds session] showBanner];
}

- (void)basicUsageHideBanner {
    [[RevMobAds session] hideBanner];
}

- (void)basicUsageShowPopup {
    [[RevMobAds session] showPopup];
}

- (void)basicUsageOpenAdLink {
    [[RevMobAds session] openAdLinkWithDelegate:self];
}

#pragma mark - Advanced mode -


#pragma mark Fullscreen

- (void)showFullscreenWithDelegate {
    RevMobFullscreen *fs = [[RevMobAds session] fullscreen];
    fs.delegate = self;
    [fs showAd];
}

- (void)showFullscreenWithSpecificOrientations {
    RevMobFullscreen *fs = [[RevMobAds session] fullscreen];
    fs.supportedInterfaceOrientations = @[@(UIInterfaceOrientationLandscapeRight), @(UIInterfaceOrientationLandscapeLeft)];

    [fs loadWithSuccessHandler:^(RevMobFullscreen *fs) {
        [fs showAd];
        [self revmobAdDidReceive];
    } andLoadFailHandler:^(RevMobFullscreen *fs, NSError *error) {
        [self revmobAdDidFailWithError:error];
    } onClickHandler:^{
        [self revmobUserClickedInTheAd];
    } onCloseHandler:^{
        [self revmobUserClosedTheAd];
    }];
}

- (void)loadFullscreen {
    self.fullscreen = [[RevMobAds session] fullscreen];
    self.fullscreen.delegate = self;
    [self.fullscreen loadAd];
}

- (void)showPreLoadedFullscreen{
    if (self.fullscreen) [self.fullscreen showAd];
}

#pragma mark Banner

- (void)showBannerWithCustomFrame {
    self.banner = [[RevMobAds session] bannerView];

    [self.banner loadWithSuccessHandler:^(RevMobBannerView *banner) {
        [banner setFrame:CGRectMake(10, 692, 200, 40)];
        [self.scroll addSubview:banner];
        [self revmobAdDidReceive];
    } andLoadFailHandler:^(RevMobBannerView *banner, NSError *error) {
        [self revmobAdDidFailWithError:error];
    } onClickHandler:^(RevMobBannerView *banner) {
        [self revmobUserClickedInTheAd];
    }];

}

- (void)hideBannerWithCustomFrame {
    [self.banner removeFromSuperview];
}

#pragma mark Banner Window

- (void)showBannerWindow {
    self.bannerWindow = [[RevMobAds session] banner];
    [self.bannerWindow loadWithSuccessHandler:^(RevMobBanner *banner) {
        [banner showAd];
        [self revmobAdDidReceive];
    } andLoadFailHandler:^(RevMobBanner *banner, NSError *error) {
        [self revmobAdDidFailWithError:error];
    } onClickHandler:^(RevMobBanner *banner) {
        [self revmobUserClickedInTheAd];
    }];
}

- (void)showBannerWindowWithSpecificOrientations {
    self.bannerWindow = [[RevMobAds session] banner];
    self.bannerWindow.supportedInterfaceOrientations = @[@(UIInterfaceOrientationLandscapeRight), @(UIInterfaceOrientationLandscapeLeft)];
    [self.bannerWindow loadWithSuccessHandler:^(RevMobBanner *banner) {
        [banner showAd];
        [self revmobAdDidReceive];
    } andLoadFailHandler:^(RevMobBanner *banner, NSError *error) {
        [self revmobAdDidFailWithError:error];
    } onClickHandler:^(RevMobBanner *banner) {
        [self revmobUserClickedInTheAd];
    }];

}

- (void)hideBannerWindow {
    [self.bannerWindow hideAd];
}

#pragma mark Link

- (void)loadAdLink {
    self.link = [[RevMobAds session] adLink];
    [self.link loadWithSuccessHandler:^(RevMobAdLink *link) {
        [self revmobAdDidReceive];
    } andLoadFailHandler:^(RevMobAdLink *link, NSError *error) {
        [self revmobAdDidFailWithError:error];
    }];
}

- (void)openAdLink {
    if (self.link) [self.link openLink];
}

- (void)addAdButton {
    RevMobButton *button = [[RevMobAds session] buttonUnloaded];

    [button loadWithSuccessHandler:^(RevMobButton *button) {
        [button setFrame:CGRectMake(10, yCoordinateControl, 300, 40)];
        [self.scroll addSubview:button];
        [button setTitle:@"Free Games" forState:UIControlStateNormal];
        yCoordinateControl += 50;
        self.scroll.contentSize = CGSizeMake(320,yCoordinateControl);
        [self revmobAdDidReceive];
    } andLoadFailHandler:^(RevMobButton *button, NSError *error) {
        [self revmobAdDidFailWithError:error];
    } onClickHandler:^(RevMobButton *button) {
        [self revmobUserClickedInTheAd];
    }];

}

#pragma mark Popup

- (void)showPopup {
    RevMobPopup *popup = [[RevMobAds session] popup];

    [popup loadWithSuccessHandler:^(RevMobPopup *popup) {
        [popup showAd];
        [self revmobAdDidReceive];
    } andLoadFailHandler:^(RevMobPopup *popup, NSError *error) {
        [self revmobAdDidFailWithError:error];
    } onClickHandler:^(RevMobPopup *popup) {
        [self revmobUserClickedInTheAd];
    }];
}

#pragma mark - RevMobAdsDelegate methods

- (void)revmobSessionIsStarted {
    NSLog(@"[RevMob Sample App] Session started again.");
}

- (void)revmobSessionNotStarted:(NSError *)error {
    NSLog(@"[RevMob Sample App] Session not started again: %@", error);
}

- (void)revmobAdDidReceive {
    NSLog(@"[RevMob Sample App] Ad loaded.");
}

- (void)revmobAdDidFailWithError:(NSError *)error {
    NSLog(@"[RevMob Sample App] Ad failed: %@", error);
}

- (void)revmobAdDisplayed {
    NSLog(@"[RevMob Sample App] Ad displayed.");
}

- (void)revmobUserClosedTheAd {
    NSLog(@"[RevMob Sample App] User clicked in the close button.");
}

- (void)revmobUserClickedInTheAd {
    NSLog(@"[RevMob Sample App] User clicked in the Ad.");
}

- (void)installDidReceive {
    NSLog(@"[RevMob Sample App] Install did receive.");
}

- (void)installDidFail {
    NSLog(@"[RevMob Sample App] Install did fail.");
}

#pragma mark - Others

- (UIImage *)imageWithColor:(UIColor *)color {
    CGRect rect = CGRectMake(0.0f, 0.0f, 1.0f, 1.0f);
    UIGraphicsBeginImageContext(rect.size);
    CGContextRef context = UIGraphicsGetCurrentContext();

    CGContextSetFillColorWithColor(context, [color CGColor]);
    CGContextFillRect(context, rect);

    UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();

    return image;
}

- (void)dealloc {
    [_locationManager release], _locationManager = nil;
    [_scroll release], _scroll = nil;

    [super dealloc];
}

- (void)closeSampleApp {
    exit(0);
}


@end
