# Chartboost for iOS

*Version 5.1.3*

The Chartboost iOS SDK is the cornerstone of the Chartboost network. It
provides the functionality for showing interstitials, More-Apps pages, and
tracking in-app purchase revenue.


### Usage

Integrating Chartboost takes two easy steps:

 1. Add the Chartboost.framework into your Xcode project's Frameworks folder.

 2. Instantiate the Chartboost SDK in your application AppDelegate.m, like this:
    
 ```objective-c
     #import <Chartboost/Chartboost.h>
     
     - (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions       
     {
         // initialize the Chartboost library
         [Chartboost startWithAppId:@"YOUR_CHARTBOOST_APP_ID" 
         			  appSignature:@"YOUR_CHARTBOOST_APP_SIGNATURE" 
         			      delegate:self];
           
         // Show an interstitial ad
         [Chartboost showInterstitial:CBLocationHomeScreen];
     }
  ```

> It is NOT RECOMMENDED that you call:
```objective-c
+ (void)showInterstitial:(CBLocation)location;
```
from within your AppDelegate's:
```objective-c
- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions;
```
as depicted above.  It is shown as is for example purposes only.

### Dive deeper

For more common use cases, visit our [online documentation](https://help.chartboost.com/documentation/ios).

Check out our header file `Chartboost.h` for the full API
specification.

If you encounter any issues, do not hesitate to contact our happy support team
at [support@chartboost.com](mailto:support@chartboost.com).
