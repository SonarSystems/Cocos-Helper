iOS Change Log
==============

Version 5.1.3 *(2015-01-16)*
----------------------------

Features:

Fixes:
- Disabled request retries by default, fix for multithreaded crashes 
- Fix for when the device is in an orientation we do not have assets for. Instead of failing silently a CBLoadError is now called.  

Improvements:

- Better inplay caching  
- Added orientation information to api-click, and video-complete calls. Allows for better analytics 
- Remove hardcoded affiliate token. Now pulled from the server 
- Added example usage of isAnyViewVisible: delegate method into the sample project 
- Added inplay button to the chartboost example app 
- Improved logging for when someone tries to show a rewarded video with prefetching disabled. Instead of failing silently a CBLoadError is called. 

Version 5.1.2 *(2014-12-05)*
----------------------------

Features:

Fixes:
- Fix CBAppCall crash if no resource path sent with URL. 
- Fix bug with SKStoreProductViewController crashing due to race condition. 
- Fix SKStoreProductViewController rotation issue with Unity. 
- Fix concurrency issue in CBConfig. 

Improvements:

Version 5.1.1 *(2014-12-01)*
----------------------------

Features:

Fixes:
- Fix build for armv7s architectures. 

Improvements:

Version 5.1.0 *(2014-11-20)*
----------------------------

Features:

Fixes:
- Fix race condition between actions for video on replay. 
- Fix loading screen causing issues with video and app sheet. 
- Fix interstitial video close button appearing at incorrect time in portrait orientation. 
- Fix rewarded video autoplays when previous display of video is dismissed instead of watched. 
- Fix api/config not executing on soft bootups. 
- Fix close button clipping the video player in corner. 
- Fix api/install not being sent for soft bootups. 
- Fix for various crashes due to memory pressure and concurrency. 
- Fix for api/track executing on hidden files for older devices. 
- Fix for rotating iPhone 6/6+ can cause video to display off screen. 
- Fix for incorrect error code enumerations being used. 
- Fix loading view not appearing for more apps page on slow connections. 
- Fix crash in CBAnalytics if sent an invalid NSDecimalNumber. 

Improvements:

- Added new framework tracking values for Cordova and CocoonJS. 
- Added new API to check visibility of Chartboost UI. 
- Changed delegate callbacks for click and close to be sent after closing or clicking the impression. 
- Changed autocache calls to delay execution for better performance. 

Version 5.0.3 *(2014-09-30)*
----------------------------

- Bugfixes and stability improvements.

Version 5.0.2 *(2014-09-12)*
----------------------------

- Added functionality to close loading view if taking too long.
- Bugfixes and stability improvements.

Version 5.0.1 *(2014-09-09)*
----------------------------

- Bugfixes and stability improvements.

Version 5.0.0 *(2014-09-03)*
----------------------------

- Public video release.
- Update Chartboost SDK interface to use static methods.
- Remove deprecated Chartboost Store.
- New ChartboostExample project.
- Many delegate methods moved to setter and getter functions.
- Introduce new Chartboost display methods that accept a UIViewController.
- Full SKProductsViewController support added.
- CBPostInstallAnalyticsTracker renamed to CBAnalytics.
- Reduce overall size of SDK.
- Dynamically load symbols and frameworks.
- Remove external dependencies.
- Enable logging in DEBUG builds via environment variable.
- Bugfixes and stability improvements.

Version 4.5.1 *(2014-07-18)*
----------------------------

- Fixes for Store.
- Fixes for session tracking and for invalid data types.

Version 4.5.0 *(2014-06-16)*
----------------------------

- Newsfeed publicly available.
- Post Install Analytics publicly available.

Version 4.4.1 *(2014-05-20)*
----------------------------

- Chartboost now supports x86_64 architectures (64 bit simulator)

Version 4.4.0 *(2014-05-17)*
----------------------------

- New MoreApps: Location support, new look and feel, custom headers
- Updates to video beta, optimizations, portrait support
- New Chartboost initialization method: startWithAppId:appSignature:delegate:
- Automatic caching enabled after show calls.
- Deprecated default location API calls.
- Beta Products support: inPlay and News Feed.
- New delegate method (didFailToRecordClick:withError:). Called when a click is registered, but the user is not fowrwarded to the App Store
- Orientation improvements: an ad will now be displayed at any orientation it
  claims supporting, when the current orientation is not.

Version 4.2.0 *(2014-03-25)*
----------------------------

- New library (CBPostInstallAnalyticsTracker) made available to track IAP.
- Apple non-default frameworks (Quartzcore, System Configuration, Store Kit and
  Ad Support) don't have to be linked manually anymore.
- Carrier information added to default request parameters
- Changed Locations for caching interstitials and more apps to an defined list.
  Locations are now a list of enum values that can be found in the Chartboost.h file.
- App Sheet support for More Apps.

Version 4.1.0 *(2014-02-17)*
----------------------------

- Interstitials and More Apps will change orientation with the device if the
  app supports multiple orientations.
- Server responses are now validated and will fail if any field is not correct.
- More Apps header background is now built from a tilable image.
- An Age Gate can now be added after an interstitial has been clicked. Use the
  delegate method `shouldPauseClickForConfirmation:` to activate it.
- Interstitial background is now blurred if the device & OS support it.
- Fixed break statement missing in the example project.
- Fixed issue with the vendor id.
- Fixed massive memory leak, thorough refactor.

Version 4.0.0 *(2014-01-13)*
----------------------------

- Bundle file scanning is now an option in the header, controlled by a boolean
  property set to false by default
- Files in the cache folder will be now deleted if they have not been  used for
  7 days
- Rate limiting of requests: only 3 interstitial requests per 90s per location
  are allowed
- Error delegate methods now take an `enum` describing the error
- When an interstitial is not loaded because of first session interstitials
  being disabled, it now triggers the failure delegate.
- When an interstitial is shown on the wrong orientation, it now also triggers
  an error.
- User-agent updated to allow wrappers to suffix User-agent.
- Add new session analytics tools to the SDK.
- User-agent updated to allow wrappers to suffix User-agent
- New delegate method, to be called when the App Store sheet is dismissed.
- Completely removing deprecated library, `CBAnalytics`.
- New example project is now part of the sdk download package
- When attempting to load an interstitial without having started a session, an
  error is triggered
- The Chartboost Store

Version 4.0.0-b7 *(2013-10-08)*
-------------------------------

- Auto-upsell
- Logging improvements, log level controllable via env variable
- Misc bug fixes

Version 4.0.0-b6 *(2013-06-28)*
-------------------------------

- Improved `localizedPriceForItem` to support caching
- Fixed crashing bug with `localizedPriceForItem` on iOS 5

Version 4.0.0-b5 *(2013-06-05)*
-------------------------------

- Improved `subscribeToStoreView`.

Version 4.0.0-b4 *(2013-06-05)*
-------------------------------

- Fixed bug when default balance is not retained.

Version 4.0.0-b3 *(2013-06-05)*
-------------------------------

- Fixed bug where new status code would not be allowed by mask.
- Removed `initializeStore` from CBStore.h.
- Completed `triggersImmediately` support for `subscribeToStoreView` to show content right away.

Version 4.0.0-b2 *(2013-06-04)*
-------------------------------

- Fixed memory leak in IAP manager.
- Fixed bug where no callback when the pruchase is unsafe.

Version 4.0.0-b1 *(2013-05-31)*
-------------------------------

- Initial release of the Store.
- New logging framework.


Version 3.3.1 *(2013-10-29)*
----------------------------

- Fixed bug causing More Apps page crashes on iOS 7
- Fix crashes on iOS 5 devices related to the new 64-bit support
- New Feature: Xcode 5 now automatically displays integration tips (defined in **chartboost.h**), contextually presenting them while you code

Version 3.3.0 *(2013-10-17)*
----------------------------

- Fully compatible with iOS 7 64-bit-only projects. Maintains support for 32-bit projects and iOS 5.1+ devices. 

Version 3.2.2 *(2013-10-08)*
----------------------------

- Added a new public method (`dismissChartboostView`) to dismiss an interstitial or More Apps view programmatically 
- Fixed a bug causing indefinite loading after More Apps page clicks while in Airplane Mode
- Fixed a crash bug that would occur if the SDK received corrupt image data
- Fixed bug affecting display of More Apps button labels on iOS 7 devices
- Fixed bug causing crashes when user clicked interstitial with no iTunes URL set via the dashboard

Version 3.2.1 *(2013-05-23)*
----------------------------

- Improved long term memory management
- Better handles clicks on interstitials with malformed URLs
- Increased stability on repeated calls to hasCachedInterstitial
- Fixed issue where dismiss button could be pressed multiple times on a single interstitial
- Replaced third party JSON lib with Apple internal JSON serializer
- Compatible only with iOS 5.0+. If you need iOS 4.3+ compatibility, use SDK version 3.2 below

Version 3.2.0 *(2013-05-24)*
----------------------------

- Minimum OS version: iOS 5.0
- __UDID method removed__. Use this SDK in any apps submitted to the App Store after May 1st, 2013.
- More Apps page better handles long app names
- Handles showing an interstitial at the same time as the Game Center login prompt
- Better time-outs on intermittent internet
- No longer shows blank interstitials when app orientation is misconfigured server-side
- Added support for targeting interstitials by language
- The window property has been changed to root view
- CBAnalytics beta features removed

**Note:** "Unique" column of App Analytics temporarily shows 0 for apps using SDK v3.2. Please use "Installs" or "Bootups" for reference.

Version 3.1.1 *(2013-02-26)*
----------------------------

- Stability improvements for apps with over 300mb of bundled assets
- Fixed rare visual glitch where interstitial positions itself incorrectly
  relative to status bar after app rotation

Version 3.1.0 *(2012-10-12)*
----------------------------

- Added support for the iOS 6 App Sheet so your users can download apps without
  ever leaving your app! You must include StoreKit.framework for access to the
  App Sheet. We are rolling out this feature in phases, apply for access via
  [bizdev@chartboost.com](mailto:bizdev@chartboost.com).
- More apps status bar fixed, now auto-adjusts position when status bar is
  visible
- Fixed rare crasher in CBCrypto
- Note: This version and all versions in the future are compatible with iOS
  version 4.3 and higher ONLY (armv7 and armv7s).

Version 3.0.7 *(2012-10-22)*
----------------------------

- **REQUIRED: You must include AdSupport.framework for access to the
identifierForAdvertising**
- Added compatibility with Xcode 4.5 and armv7s. This SDK includes armv6, armv7,
  and armv7s
- Fixed shouldRequestInterstitialsInFirstSession delegate method; now requests
  interstitials only after 2nd startSession call
- Internal API upgrades
 
Version 3.0.6 *(2012-09-13)*
----------------------------

- Automatic interstitial caching now uses a version fallback if your app does
  not include CFBundleShortVersionString in the info.plist. For best
  interstitial caching, add CFBundleShortVersionString key and value to your
  info.plist.

Version 3.0.5 *(2012-08-28)*
----------------------------

- Fixed rare crash in web image caching library

Version 3.0.4 *(2012-08-16)*
----------------------------

- Added SDK support for targeting Wi-fi devices

Version 3.0.3 *(2012-07-31)*
----------------------------

- Removed automatic removal of interstitials and more apps view when
  backgrounding app

Version 3.0.2 *(2012-07-20)*
----------------------------

- Improves click tracking in race conditions
- Fixes issue where cached ads appear during backgrounding
- Fixed JSON crasher in CB Analytics

Version 3.0.1 *(2012-07-06)*
----------------------------

- Improved compatibility with iOS version 4.0 - 4.2

Version 3.0.0 *(2012-07-02)*
----------------------------

- Mandatory updates:
    - Delegate methods now return location strings (no longer pass in a view)
      -- i.e. didFailToLoadInterstitial will pass in the specific location
      identifier that failed
    - Removed methods: loadInterstitial, install -- now use showInterstitial,
      startSession
    - Requirement: rename ChartBoost class to Chartboost (lowercase b, no
      camelCase) Feeling hardcore? Run this bash command in your project
      directory to update camelcase ChartBoost in all your files:
      
      ```bash
      for ext in '*.m' '*.h' '*.c' '*.mm'; do find . -name "$ext" -exec sed -i '' 's/ChartBoost/Chartboost/g' '{}' \; ; done
      ```

- New native interstitials: faster, less memory
- New native More Apps page: faster, less memory, less network activity
- Asset caching: individual assets are cached and only downloaded if they don't
  exist in cache -- all assets stored in caches folder so the OS handles memory
  appropriately
- Cache expiration: cached interstitials automatically expire after 24 hours
- Multi orientation support: if your app works in both orientations, simply
  select both (landscape and portrait) in the dashboard
- Orientation override: SDK detects orientation using the statusbar location,
  you may override this detection
- UDID replacement: iOS 6 compatible
- New delegate methods:
    - didCacheInterstitial: called when an interstitial is successfully cached
      from the server, interstitial location identifier passed in
    - didCacheMoreApps: called when the more apps page is successfully cached
      from the server
    - shouldRequestInterstitialsInFirstSession: default is YES, you may
      override to NO if you don't want interstitials displayed until after the
      2nd startSession (for compliance with Human Interface Guidelines)
- Version notifications: if a new version of the SDK is released, you'll get
  a version notification in the Xcode console if current device is iPhone
  Simulator
- Bundle assets: you may include frame and cross promotion assets into your
  binary
- Bug fixes:
    - Displaying interstitial no longer fails when there is no appDelegate
      window property
    - Fixed memory leaks
- SDK supported - coming soon to the dashboard:
    - Interstitial animations: four, configurable from the dashboard
    - Retina support for interstitials and more apps
    - New cell types: regular, featured, webview
