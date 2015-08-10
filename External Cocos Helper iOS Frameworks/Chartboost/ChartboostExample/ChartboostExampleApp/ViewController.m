/*
 * ViewController.m
 * ChartboostExampleApp
 *
 * Copyright (c) 2013 Chartboost. All rights reserved.
 */

#import "ViewController.h"
#import <Chartboost/Chartboost.h>
#import <Chartboost/CBNewsfeed.h>
#import <Chartboost/CBAnalytics.h>
#import <Chartboost/CBInPlay.h>

#import <StoreKit/StoreKit.h>

static NSUInteger inPlaySuccessViewTag = 999;
static NSUInteger inPlayErrorViewTag = 888;

#define SYSTEM_VERSION_EQUAL_TO(v)                  ([[[UIDevice currentDevice] systemVersion] compare:v options:NSNumericSearch] == NSOrderedSame)
#define SYSTEM_VERSION_GREATER_THAN(v)              ([[[UIDevice currentDevice] systemVersion] compare:v options:NSNumericSearch] == NSOrderedDescending)
#define SYSTEM_VERSION_GREATER_THAN_OR_EQUAL_TO(v)  ([[[UIDevice currentDevice] systemVersion] compare:v options:NSNumericSearch] != NSOrderedAscending)
#define SYSTEM_VERSION_LESS_THAN(v)                 ([[[UIDevice currentDevice] systemVersion] compare:v options:NSNumericSearch] == NSOrderedAscending)
#define SYSTEM_VERSION_LESS_THAN_OR_EQUAL_TO(v)     ([[[UIDevice currentDevice] systemVersion] compare:v options:NSNumericSearch] != NSOrderedDescending)

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    self.inPlayShowing = NO;
    self.inPlayShowingError = NO;
    [super viewDidLoad];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

- (IBAction)showInterstitial {
    [Chartboost showInterstitial:CBLocationHomeScreen];
}

- (IBAction)showMoreApps {
    [Chartboost showMoreApps:self location:CBLocationHomeScreen];
}

- (IBAction)cacheInterstitial {
    [Chartboost cacheInterstitial:CBLocationHomeScreen];
}

- (IBAction)cacheMoreApps {
    [Chartboost cacheMoreApps:CBLocationHomeScreen];
}

- (IBAction)showNewsfeed {
    [CBNewsfeed showNewsfeedUI];
}

- (IBAction)cacheRewardedVideo {
    [Chartboost cacheRewardedVideo:CBLocationHomeScreen];
}

- (IBAction)showRewardedVideo {
    [Chartboost showRewardedVideo:CBLocationMainMenu];
}

- (IBAction)showNotificationUI:(id)sender {
    [CBNewsfeed showNotificationUI];
}

- (IBAction)showSupport:(id)sender {
    
    [[UIApplication sharedApplication] openURL:[NSURL URLWithString:@"http://answers.chartboost.com"]];
}

- (IBAction)showInPlay:(id)sender {
    CBInPlay *inPlay = [Chartboost getInPlay:@"native"];
    if (inPlay) {
        NSLog(@"Success, we have a valid inPlay item");
        [self renderInPlay:inPlay];
    }
}

- (void)renderInPlay:(CBInPlay*)inPlay {
    if (self.inPlayShowing) {
        return;
    }
    self.inPlayShowing = YES;
    
    CGFloat screenWidth = self.view.bounds.size.width;
    CGFloat screenHeight = self.view.bounds.size.height;
    CGFloat width = screenWidth;
    CGFloat height = screenHeight;
    CGFloat x = 0;
    CGFloat y = 0;
   
    CGRect overlayFrame = CGRectMake(x, y, width, height);
    UIView *blur = [UIView new];
    if (SYSTEM_VERSION_LESS_THAN(@"7.0")) {
        blur = [[UIView alloc] initWithFrame:overlayFrame];
        blur.backgroundColor = [[UIColor blackColor] colorWithAlphaComponent:0.7f];
    } else {
        blur = [[UIToolbar alloc] initWithFrame:overlayFrame];
        ((UIToolbar*)blur).barStyle = UIBarStyleBlack;
        ((UIToolbar*)blur).translucent = YES;
    }
    
    // Add inPlay SubViews
    UIImage *appIconImage = [UIImage imageWithData:inPlay.appIcon];
    UIImageView *appIcon = [[UIImageView alloc] initWithImage:[UIImage imageWithData:inPlay.appIcon]];
    appIcon.frame = CGRectMake(screenWidth/2.0f - [appIconImage size].width/2.0f, 30.0f, [appIconImage size].height, [appIconImage size].width);
    UILabel *appName = [[UILabel alloc] initWithFrame:CGRectMake(0.0f, appIcon.frame.origin.y + appIcon.frame.size.height + 50.0f, 999.0f, 999.0f)];
    appName.font = [UIFont fontWithName:@"Helvetica" size:18.0f];
    appName.text = inPlay.appName;
    [appName sizeToFit];
    appName.frame = CGRectMake(screenWidth/2.0f - appName.frame.size.width/2.0f, appName.frame.origin.y, appName.frame.size.width, appName.frame.size.height);
    appName.textColor = [UIColor whiteColor];
    appName.backgroundColor = [UIColor clearColor];
    UILabel *iconTitle = [[UILabel alloc] initWithFrame:CGRectMake(appIcon.frame.origin.x, appIcon.frame.origin.y + appIcon.frame.size.height + 3.0f, appIcon.frame.size.width, 10.0f)];
    iconTitle.font = [UIFont systemFontOfSize:9.0f];
    iconTitle.textColor = [UIColor whiteColor];
    iconTitle.textAlignment = NSTextAlignmentCenter;
    iconTitle.text = @"App Icon";
    iconTitle.backgroundColor = [UIColor clearColor];
    UILabel *nameTitle = [[UILabel alloc] initWithFrame:CGRectMake(appName.frame.origin.x, appName.frame.origin.y + appName.frame.size.height + 3.0f, appName.frame.size.width, 10.0f)];
    nameTitle.font = [UIFont systemFontOfSize:9.0f];
    nameTitle.textColor = [UIColor whiteColor];
    nameTitle.textAlignment = NSTextAlignmentCenter;
    nameTitle.text = @"App Name";
    nameTitle.backgroundColor = [UIColor clearColor];
    
    CGFloat buttonWidth = 50.0f;
    UIButton *closeButton = [[UIButton alloc] initWithFrame:CGRectMake(screenWidth - buttonWidth - 5.0f, 5.0f, buttonWidth, buttonWidth)];
    [closeButton addTarget:self action:@selector(closeButtonInPlay:) forControlEvents:UIControlEventTouchUpInside];
    UILabel *closeButtonLabel = [[UILabel alloc] initWithFrame:closeButton.frame];
    closeButtonLabel.text = @"\U00002715";
    closeButtonLabel.font = [UIFont systemFontOfSize:35.0f];
    closeButtonLabel.textColor = [UIColor whiteColor];
    closeButtonLabel.textAlignment = NSTextAlignmentCenter;
    closeButtonLabel.backgroundColor = [UIColor clearColor];
    
    [blur addSubview:closeButton];
    [blur addSubview:closeButtonLabel];
    [blur addSubview:appIcon];
    [blur addSubview:appName];
    [blur addSubview:iconTitle];
    [blur addSubview:nameTitle];
    
    blur.tag = inPlaySuccessViewTag;
    blur.alpha = 0.0f;
    [self.view addSubview:blur];
    
    [UIView animateWithDuration:0.3f animations:^(void){
        [self.view viewWithTag:inPlaySuccessViewTag].alpha = 1.0f;
    } completion:^(BOOL finished){
        
    }];
    self.inPlayShowing = YES;
}

- (void)closeButtonInPlay:(id)button {
    [UIView animateWithDuration:0.3f animations:^(void){
        [self.view viewWithTag:inPlaySuccessViewTag].alpha = 0.0f;
    } completion:^(BOOL finished){
        [[self.view viewWithTag:inPlaySuccessViewTag] removeFromSuperview];
        self.inPlayShowing = NO;
    }];
}

- (void)renderInPlayError:(NSString *)error {
    if (self.inPlayShowingError) {
        return;
    }
    self.inPlayShowingError = YES;
    
    CGFloat screenWidth = self.view.bounds.size.width;
    CGFloat screenHeight = self.view.bounds.size.height;
    CGFloat width = screenWidth;
    CGFloat height = screenHeight;
    CGFloat x = 0;
    CGFloat y = 0;
    
    CGRect overlayFrame = CGRectMake(x, y, width, height);
    UIView *blur = [UIView new];
    if (SYSTEM_VERSION_LESS_THAN(@"7.0")) {
        blur = [[UIView alloc] initWithFrame:overlayFrame];
        blur.backgroundColor = [[UIColor blackColor] colorWithAlphaComponent:0.7f];
    } else {
        blur = [[UIToolbar alloc] initWithFrame:overlayFrame];
        ((UIToolbar*)blur).barStyle = UIBarStyleBlack;
        ((UIToolbar*)blur).translucent = YES;
    }
    
    
    // Add inPlay SubViews
    UILabel *errorLabel = [[UILabel alloc] initWithFrame:CGRectMake(0.0f, 0.0f, 999.0f, 999.0f)];
    errorLabel.font = [UIFont fontWithName:@"Helvetica" size:18.0f];
    errorLabel.text = error;
    [errorLabel sizeToFit];
    errorLabel.frame = CGRectMake(screenWidth/2.0f - errorLabel.frame.size.width/2.0f, screenHeight/2.0f - errorLabel.frame.size.height/2.0f, errorLabel.frame.size.width, errorLabel.frame.size.height);
    errorLabel.textColor = [UIColor whiteColor];
    errorLabel.backgroundColor = [UIColor clearColor];
    UILabel *errorTitle = [[UILabel alloc] initWithFrame:CGRectMake(errorLabel.frame.origin.x, errorLabel.frame.origin.y + errorLabel.frame.size.height + 3.0f, errorLabel.frame.size.width, 10.0f)];
    errorTitle.font = [UIFont systemFontOfSize:9.0f];
    errorTitle.textColor = [UIColor whiteColor];
    errorTitle.textAlignment = NSTextAlignmentCenter;
    errorTitle.text = @"Error";
    errorTitle.backgroundColor = [UIColor clearColor];
    
    CGFloat buttonWidth = 50.0f;
    UIButton *closeButton = [[UIButton alloc] initWithFrame:CGRectMake(screenWidth - buttonWidth - 5.0f, 5.0f, buttonWidth, buttonWidth)];
    [closeButton addTarget:self action:@selector(closeButtonInPlayError:) forControlEvents:UIControlEventTouchUpInside];
    UILabel *closeButtonLabel = [[UILabel alloc] initWithFrame:closeButton.frame];
    closeButtonLabel.text = @"\U00002715";
    closeButtonLabel.font = [UIFont systemFontOfSize:35.0f];
    closeButtonLabel.textColor = [UIColor whiteColor];
    closeButtonLabel.textAlignment = NSTextAlignmentCenter;
    closeButtonLabel.backgroundColor = [UIColor clearColor];
    
    [blur addSubview:closeButton];
    [blur addSubview:closeButtonLabel];
    [blur addSubview:errorLabel];
    [blur addSubview:errorTitle];
    blur.tag = inPlayErrorViewTag;
    blur.alpha = 0.0f;
    [self.view addSubview:blur];
    
    [UIView animateWithDuration:0.3f animations:^(void){
        [self.view viewWithTag:inPlayErrorViewTag].alpha = 1.0f;
    } completion:^(BOOL finished){
        
    }];
    self.inPlayShowingError = YES;
}

- (void)closeButtonInPlayError:(id)button {
    [UIView animateWithDuration:0.3f animations:^(void){
        [self.view viewWithTag:inPlayErrorViewTag].alpha = 0.0f;
    } completion:^(BOOL finished){
        [[self.view viewWithTag:inPlayErrorViewTag] removeFromSuperview];
        self.inPlayShowingError = NO;
    }];
}
/*
 * This is an example of how to call the Chartboost Post Install Analytics API.
 * To fully use this feature you must implement the Apple In-App Purchase
 *
 * Checkout https://developer.apple.com/in-app-purchase/ for information on how to setup your app to use StoreKit
 */
- (void)trackInAppPurchase:(NSData *)transactionReceipt product:(SKProduct *)product {
    [CBAnalytics trackInAppPurchaseEvent:transactionReceipt product:product];
}

@end
