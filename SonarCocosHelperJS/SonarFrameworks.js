/*
 Copyright (C) 2015 Sonar Systems - All Rights Reserved
 You may use, distribute and modify this code under the
 terms of the MIT license
 
 Any external frameworks used have their own licenses and
 should be followed as such.
 */
//
//  SonarFrameworks.js
//  Sonar Cocos Helper
//
//  Created by Sonar Systems on 03/03/2015.
//
//

// Root Namespace
var SonarCocosHelper = { };

var CLASS_PATH = "sonar/systems/framework/SonarFrameworkFunctions";

SonarCocosHelper.AdBannerPosition = { eBottom: 0, eTop: 1, eBoth: 2 };

SonarCocosHelper.IOS = function () { }; // tested by Frahaan
SonarCocosHelper.GooglePlayServices = function () { };
SonarCocosHelper.Facebook = function () { }; // tested by Frahaan
SonarCocosHelper.Twitter = function () { }; // tested by Frahaan
SonarCocosHelper.AdMob = function () { }; // tested by Frahaan
SonarCocosHelper.Mopub = function () { }; // tested by Frahaan
SonarCocosHelper.iAds = function () { }; // tested by Frahaan
SonarCocosHelper.RevMob = function () { }; // tested by Frahaan
SonarCocosHelper.Chartboost = function () { }; // tested by Frahaan
SonarCocosHelper.GameCenter = function () { }; // tested by Frahaan
SonarCocosHelper.GoogleAnalytics = function () { }; // tested by Frahaan
SonarCocosHelper.AdColony = function () { }; // tested by Frahaan
SonarCocosHelper.Vungle = function () { }; // tested by Frahaan

/**
 * Check if the user is signed in
 * @return true is signed in, false is not signed in
 */
SonarCocosHelper.GooglePlayServices.isSignedIn = function ( )
{
    if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
        return jsb.reflection.callStaticMethod(CLASS_PATH, "isSignedIn", "()Z");
    }
};

/**
 * Sign the user in
 */
SonarCocosHelper.GooglePlayServices.signIn = function ( )
{
    if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
        jsb.reflection.callStaticMethod(CLASS_PATH, "gameServicesSignIn", "()V");
    }
};

/**
 * Sign the user out
 */
SonarCocosHelper.GooglePlayServices.signOut = function ( )
{
    if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
         jsb.reflection.callStaticMethod(CLASS_PATH, "gameServicesSignOut", "()V");
    }
};

/**
 * Submit score to online leaderboard
 * @param leaderboardID is the name of your leaderboard
 * @param score is the score to submit online
 */
SonarCocosHelper.GooglePlayServices.submitScore = function ( leaderboardID, score )
{
    if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
         jsb.reflection.callStaticMethod(CLASS_PATH, "submitScore", "(Ljava/lang/String;I)V", leaderboardID, score);
    }
};

/**
 * Unlock achievement
 * @param achievementID is the achievement to unlock
 */
SonarCocosHelper.GooglePlayServices.unlockAchievement = function ( achievementID )
{
    if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
        jsb.reflection.callStaticMethod(CLASS_PATH, "unlockAchievement", "(Ljava/lang/String;)V", achievementID);
    }
};

/**
 * Increment incremental achievement
 * @param achievementID is the achievement to increment
 * @param numSteps is the number of steps to increase achievement by
 */
SonarCocosHelper.GooglePlayServices.incrementAchievement = function ( achievementID, numSteps )
{
    if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
         jsb.reflection.callStaticMethod(CLASS_PATH, "incrementAchievement", "(Ljava/lang/String;I)V", achievementID, numSteps);
    }
};

/**
 * Show the achievements
 */
SonarCocosHelper.GooglePlayServices.showAchievements = function ( )
{
    if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
         jsb.reflection.callStaticMethod(CLASS_PATH, "showAchievements", "()V");
    }
};

/**
 * Show leaderboard
 * @param leaderboardID is the leaderboard to display
 */
SonarCocosHelper.GooglePlayServices.showLeaderboard = function ( leaderboardID )
{
    if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
         jsb.reflection.callStaticMethod(CLASS_PATH, "showLeaderboard", "(Ljava/lang/String;)V", leaderboardID);
    }
};

/**
 * Show leaderboards
 */
SonarCocosHelper.GooglePlayServices.showLeaderboards = function ( )
{
    if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
         jsb.reflection.callStaticMethod(CLASS_PATH, "showLeaderboards", "()V");
    }
};

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Initializes the Cocos Helper for use, only needs to be called once
 */
SonarCocosHelper.IOS.Setup = function ( )
{
    if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
        
    }
    else if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "Setup" );
    }
};


/**
 * Opens the share dialog
 * @param shareString is the string to share
 * @param imagePath is the path to an image to share as well (optional)
 */
SonarCocosHelper.IOS.Share = function ( shareString, imagePath )
{
    if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
        
    }
    else if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "shareWithString:andContent:", shareString, imagePath );
    }
};

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Share to Facebook
 * @param name is the post title (optional)
 * @param link is a link to be attached to the post (optional)
 * @param description is the text to post
 * @param caption is a caption to the main post (optional)
 * @param imagePath is the path to an image to share as well (optional)
 */
SonarCocosHelper.Facebook.Share = function (name, link, description, caption, imagePath)
{
	if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
        jsb.reflection.callStaticMethod(CLASS_PATH, "FacebookShare", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", name, link, description, caption, imagePath);
    }
    else if ( cc.sys.os == cc.sys.OS_IOS )
    {
       jsb.reflection.callStaticMethod( "IOSJSHelper", "shareViaFacebook:andContent:", description, imagePath );
    }
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Tweet to Twitter
 * @param description is the text to tweet
 * @param title is the tweet title (optional)
 * @param imagePath is the path to an image to tweet as well (optional)
 */
SonarCocosHelper.Twitter.Tweet = function (tweet, title, imagePath)
{
	if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
        jsb.reflection.callStaticMethod(CLASS_PATH, "TwitterTweet", "(Ljava/lang/String;Ljava/lang/String;)V", tweet, title);
    }
    else if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "shareViaTwitter:andContent:", tweet, imagePath );
    }
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Show a banner on the top of the screen
 * @param SonarCocosHelper::eTop displays the ad banner at the top of the screen
 * @param SonarCocosHelper::eBottom displays the ad banner at the bottom of the screen
 * @param SonarCocosHelper::eBoth displays the a ad banner on the top and the bottom
 */
SonarCocosHelper.AdMob.showBannerAd = function ( position )
{
    if ( typeof position === 'undefined' )
    { var position = SonarCocosHelper.AdBannerPosition.eTop; }
    
	if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
         jsb.reflection.callStaticMethod( CLASS_PATH, "ShowBannerAd", "(I)V", position );
    }
    else if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "showAdMobBanner:", position );
    }
}

/**
 * Hide a banner on the top of the screen
 * @param SonarCocosHelper::eTop hide the ad banner at the top of the screen
 * @param SonarCocosHelper::eBottom hide the ad banner at the bottom of the screen
 * @param SonarCocosHelper::eBoth hides all visible ad banners
 */
SonarCocosHelper.AdMob.hideBannerAd = function ( position )
{
    if ( typeof position === 'undefined' )
    { var position = SonarCocosHelper.AdBannerPosition.eBoth; }
    
	if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
        jsb.reflection.callStaticMethod( CLASS_PATH, "HideBannerAd", "(I)V", position );
    }
    else if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "hideAdMobBanner:", position );
    }
}

/**
 * Show a fullscreen interstitial ad
 */
 SonarCocosHelper.AdMob.showFullscreenAd = function ()
{
	if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
        jsb.reflection.callStaticMethod(CLASS_PATH, "ShowFullscreenAdAM", "()V");
    }
    else if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "showAdMobFullscreenAd" );
    }
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
 /**
 * Show a banner ad
 */
SonarCocosHelper.Mopub.showBannerAd = function ()
{
	if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
        jsb.reflection.callStaticMethod(CLASS_PATH, "ShowBannerAdMP", "()V");
    }
    else if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "showMopubBanner" );
    }
}

 /**
 * Hide the banner ad
 */
SonarCocosHelper.Mopub.hideBannerAd = function ()
{
	if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
        jsb.reflection.callStaticMethod(CLASS_PATH, "HideBannerAdMP", "()V");
    }
    else if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "hideMopubBanner" );
    }
}

/**
 * Show fullscreen interstitial ad
 */
SonarCocosHelper.Mopub.showFullscreenAd = function ()
{
	if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
		jsb.reflection.callStaticMethod(CLASS_PATH, "ShowFullscreenAdMP", "()V");
    }
    else if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "showMoPubFullscreenAd" );
    }
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Show a banner on the top of the screen
 */
SonarCocosHelper.iAds.showBanner = function ( )
{
    if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "showiAdBanner:", SonarCocosHelper.AdBannerPosition.eTop );
    }
}

/**
 * Show a banner on the screen
 * @param SonarCocosHelper.AdBannerPosition.eTop displays the ad banner at the top of the screen
 * @param SonarCocosHelper.AdBannerPosition.eBottom displays the ad banner at the bottom of the screen
 */
SonarCocosHelper.iAds.showBanner = function ( position )
{
    if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "showiAdBanner:", position );
    }
}

/**
 * Hide ad banner
 */
SonarCocosHelper.iAds.hideBanner = function ( position )
{
    if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "hideiAdBanner" );
    }
}

/**
 * Show fullscreen interstitial ad
 */
SonarCocosHelper.RevMob.showFullscreenAd = function ()
{
	if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
        jsb.reflection.callStaticMethod(CLASS_PATH, "ShowFullscreenAd", "()V");
    }
    else if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "showRevMobFullScreenAd" );
    }
}

/**
 * Show a popup ad
 */
SonarCocosHelper.RevMob.showPopupAd = function ()
{
	if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
        jsb.reflection.callStaticMethod(CLASS_PATH, "ShowPopUpAd", "()V");
    }
    else if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "showRevMobPopupAd" );
    }
}

/**
 * Show a banner ad
 */
SonarCocosHelper.RevMob.showBannerAd = function ()
{
	if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
        // not available
    }
    else if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "showRevMobBanner" );
    }
}

/**
 * Hide the  banner ad
 */
SonarCocosHelper.RevMob.hideBannerAd = function ( )
{
	if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
        // not available
    }
    else if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "hideRevMobBanner" );
    }
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Show a fullscreen interstitial ad
 */
SonarCocosHelper.Chartboost.showFullscreenAd = function ( )
{
	if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
        jsb.reflection.callStaticMethod( CLASS_PATH, "ShowFullscreenAdCB", "()V" );
    }
    else if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "showChartboostFullScreenAd" );
    }
}

/**
 * Show a fullscreen video ad
 */
SonarCocosHelper.Chartboost.showVideoAd = function ( )
{
	if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
        jsb.reflection.callStaticMethod( CLASS_PATH, "ShowVideoAdCB", "()V" );
    }
    else if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "showChartboostVideoAd" );
    }
}


/**
 * Show more apps
 */
SonarCocosHelper.Chartboost.showMoreApps = function ( )
{
	if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
        // not available
    }
    else if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "showChartboostMoreApps" );
    }
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Sign the user in
 */
SonarCocosHelper.GameCenter.signIn = function ( )
{
    if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "gameCenterLogin" );
    }
}

/**
 * Show achievements
 */
SonarCocosHelper.GameCenter.showAchievements = function ( )
{
    if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "gameCenterShowAchievements" );
    }
}

/**
 * Show leaderboard
 */
SonarCocosHelper.GameCenter.showLeaderboard = function ( )
{
    if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "gameCenterShowLeaderboard" );
    }
}

/**
 * Submit score to online leaderboard
 * @param scoreNumber is the score to submit online
 * @param leaderboardID is the name of your leaderboard
 */
SonarCocosHelper.GameCenter.submitScore = function ( scoreNumber, leaderboardID )
{
    if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "gameCenterSubmitScore:andLeaderboard:", scoreNumber, leaderboardID );
    }
}

/**
 * Unlock percentage of an achievement
 * @param achievementID is the achievement to increment
 * @param percent is the percentage of the achievement to unlock
 */
SonarCocosHelper.GameCenter.unlockAchievement = function ( achievementID, percent )
{
    if ( cc.sys.os == cc.sys.OS_IOS )
    {
        if ( typeof percent === 'undefined' )
        { var percent = 100; }
        
        jsb.reflection.callStaticMethod( "IOSJSHelper", "gameCenterUnlockAchievement:andPercentage:", achievementID, percent );
    }
}

/**
 * Reset all player achievements (cannot be undone)
 */
SonarCocosHelper.GameCenter.resetPlayerAchievements = function ( )
{
    if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "gameCenterResetPlayerAchievements" );
    }
}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////


/**
 * Set the screen name
 * @param screenName string to set for the screen
 */
SonarCocosHelper.GoogleAnalytics.setScreenName = function ( screenName )
{
	if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
        jsb.reflection.callStaticMethod( CLASS_PATH, "SetGAScreenName", "(Ljava/lang/String;)V", screenName );
    }
    else if ( cc.sys.os == cc.sys.OS_IOS )
    {
       jsb.reflection.callStaticMethod( "IOSJSHelper", "setGAScreenName:", screenName );
    }
}

/**
 * Set dispatch interval (frequency of which data is submitted to the server)
 * @param dispatchInterval submit frequency
 */
SonarCocosHelper.GoogleAnalytics.setDispatchInterval = function ( dispatchInterval )
{
	if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
        jsb.reflection.callStaticMethod( CLASS_PATH, "SetGADispatchInterval", "(I)V", dispatchInterval );
    }
    else if ( cc.sys.os == cc.sys.OS_IOS )
    {
       jsb.reflection.callStaticMethod( "IOSJSHelper", "setGADispatchInterval:", dispatchInterval );
    }
}

/**
 * Set a custom event
 * @param category (required) a string of what the event category is
 * @param action (required) a string of what the action performed is
 * @param label (optional) a string of what the label for the action is
 * @param label (optional, only for Android not iOS) a value of the event that has occurred
 */
SonarCocosHelper.GoogleAnalytics.sendEvent = function (category, action, label, value)
{
    if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
        jsb.reflection.callStaticMethod(CLASS_PATH, "SendGAEvent", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V", category, action, label, value);
    }
    else if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "sendGAEvent:andAction:andLabel:", category, action, label );
    }
}

SonarCocosHelper.AdColony.showVideoAC = function ( withPreOp, withPostOp )
{
    if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
        jsb.reflection.callStaticMethod(CLASS_PATH, "ShowRewardedVideoAdAC", "()V", withPreOp, withPostOp);
    }
    else if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "showVideoAC:andPostOp:", withPreOp, withPostOp );
    }
}

SonarCocosHelper.Vungle.ShowVideoVungle = function ( isIncentivised )
{
    if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
         jsb.reflection.callStaticMethod(CLASS_PATH, "ShowRewardedVideoAdV", "(Z)V", isIncentivised);
    }
    else if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "showVideoVungle:", isIncentivised );
    }
}