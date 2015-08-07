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
SonarCocosHelper.UIButtonPosition = { eBottomLeft: 0, eBottomRight: 1, eTopLeft: 2, eTopRight: 3 };
SonarCocosHelper.CalendarUnits = { eMinute: 0, eHourly: 1, eDaily: 2, eWeekly: 3, eMonthly: 4, eYearly: 5 };

SonarCocosHelper.IOS = function () { };
SonarCocosHelper.GooglePlayServices = function () { };
SonarCocosHelper.Facebook = function () { };
SonarCocosHelper.Twitter = function () { };
SonarCocosHelper.AdMob = function () { };
SonarCocosHelper.Mopub = function () { };
SonarCocosHelper.iAds = function () { };
SonarCocosHelper.RevMob = function () { };
SonarCocosHelper.Chartboost = function () { };
SonarCocosHelper.GameCenter = function () { };
SonarCocosHelper.GoogleAnalytics = function () { };
SonarCocosHelper.AdColony = function () { };
SonarCocosHelper.Vungle = function () { };
SonarCocosHelper.WeChat = function () { };
SonarCocosHelper.Notifications = function () { };

SonarCocosHelper.UI = function ()
{
    this.audioToggleButton;
    this.soundEffectsToggleButton;
    this.musicToggleButton;
};

SonarCocosHelper.UI.Audio = function( )
{
    this.offButtonString;
    this.offButtonPressedString;
    this.onButtonString;
    this.onButtonPressedString;
};

SonarCocosHelper.UI.SoundEffects = function( )
{
    this.offButtonString;
    this.offButtonPressedString;
    this.onButtonString;
    this.onButtonPressedString;
};

SonarCocosHelper.UI.Music = function( )
{
    this.offButtonString;
    this.offButtonPressedString;
    this.onButtonString;
    this.onButtonPressedString;
};

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
};
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
};

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
};

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
};

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
};

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

/**
 * Show ad
 * @param withPreOp (required), show optional popup before the ad is shown
 * @param withPostOp (required), show optional popup after the ad is shown
 */
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

/**
 * Show ad
 * @param isIncentivised (required) true is a reward video ad, false is a regular video ad
 */
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

/**
 * Adds a audio toggle button (sounds effects and music)
 * @param onButton (required) audio on button filepath
 * @param onButtonPressed (required) audio on pressed button filepath
 * @param offButton (required) audio off button filepath
 * @param offButtonPressed (required) audio off pressed button filepath
 * @param layer (required) the layer to which the button will be added a child
 * @param position (required) where would you like to position the button (SonarCocosHelper::UIButtonPosition::eBottomLeft, SonarCocosHelper::UIButtonPosition::eBottomRight, SonarCocosHelper::UIButtonPosition::eTopLeft, SonarCocosHelper::UIButtonPosition::eTopRight)
 */

SonarCocosHelper.UI.AddAudioToggle = function ( onButton, onButtonPressed, offButton, offButtonPressed, layer, position )
{
    SonarCocosHelper.UI.audioToggleButton = new ccui.Button( );
    SonarCocosHelper.UI.audioToggleButton.loadTextures( onButton, onButtonPressed );
    
    var visibleSize = cc.winSize;
    
    switch ( position )
    {
        case SonarCocosHelper.UIButtonPosition.eBottomLeft:
            SonarCocosHelper.UI.audioToggleButton.setPosition( SonarCocosHelper.UI.audioToggleButton.getContentSize( ).width / 2, SonarCocosHelper.UI.audioToggleButton.getContentSize( ).height / 2 );
            
            break;
            
        case SonarCocosHelper.UIButtonPosition.eBottomRight:
            SonarCocosHelper.UI.audioToggleButton.setPosition( visibleSize.width - SonarCocosHelper.UI.audioToggleButton.getContentSize( ).width / 2, SonarCocosHelper.UI.audioToggleButton.getContentSize( ).height / 2 );
            
            break;
            
        case SonarCocosHelper.UIButtonPosition.eTopLeft:
            SonarCocosHelper.UI.audioToggleButton.setPosition( SonarCocosHelper.UI.audioToggleButton.getContentSize( ).width / 2, visibleSize.height - SonarCocosHelper.UI.audioToggleButton.getContentSize( ).height / 2 );
            
            break;
            
        case SonarCocosHelper.UIButtonPosition.eTopRight:
            SonarCocosHelper.UI.audioToggleButton.setPosition( visibleSize.width - SonarCocosHelper.UI.audioToggleButton.getContentSize( ).width / 2, visibleSize.height - SonarCocosHelper.UI.audioToggleButton.getContentSize( ).height / 2 );
            
            break;
    }
    
    SonarCocosHelper.UI.audioToggleButton.addTouchEventListener( SonarCocosHelper.UI.AudioTouchEvent, this );
    
    layer.addChild( SonarCocosHelper.UI.audioToggleButton, 10000 );
    
    SonarCocosHelper.UI.Audio.offButtonString = offButton;
    SonarCocosHelper.UI.Audio.offButtonPressedString = offButtonPressed;
    SonarCocosHelper.UI.Audio.onButtonString = onButton;
    SonarCocosHelper.UI.Audio.onButtonPressedString = onButtonPressed;
}

/**
 * Set the audio toggle button position
 * @param xPos (required) x-axis position
 * @param yPos (required) y-axis position
 */
SonarCocosHelper.UI.SetAudioButtonPosition = function( xPos, yPos )
{
    SonarCocosHelper.UI.audioToggleButton.setPosition( xPos, yPos );
}

SonarCocosHelper.UI.AudioTouchEvent = function( sender, type )
{
    this.tempString;
    
    switch ( type )
    {
        case ccui.Widget.TOUCH_BEGAN:
            // code to handle when the button is first clicked
            
            break;
            
        case ccui.Widget.TOUCH_MOVED:
            // code to handle when the user is moving their finger/cursor whilst clicking the button
            
            break;
            
        case ccui.Widget.TOUCH_ENDED:
            // code to handle when the button click has ended (e.g. finger is lifted off the screen)
            tempString = SonarCocosHelper.UI.Audio.offButtonString;
            
            SonarCocosHelper.UI.Audio.offButtonString = SonarCocosHelper.UI.Audio.onButtonString;
            SonarCocosHelper.UI.Audio.onButtonString = tempString;
            
            tempString = SonarCocosHelper.UI.Audio.offButtonPressedString;
            
            SonarCocosHelper.UI.Audio.offButtonPressedString = SonarCocosHelper.UI.Audio.onButtonPressedString;
            SonarCocosHelper.UI.Audio.onButtonPressedString = tempString;
            
            SonarCocosHelper.UI.audioToggleButton.loadTextures( SonarCocosHelper.UI.Audio.onButtonString, SonarCocosHelper.UI.Audio.onButtonPressedString );
            
            this.volume;
            
            if ( cc.audioEngine.getEffectsVolume( ) > 0.0 )
            { volume = 0.0; }
            else
            { volume = 1.0; }
            
            cc.audioEngine.setEffectsVolume( volume );
            
            if ( cc.audioEngine.getMusicVolume( ) > 0.0 )
            { volume = 0.0; }
            else
            { volume = 1.0; }
            
            cc.audioEngine.setMusicVolume( volume );
            
            break;
            
        case ccui.Widget.TOUCH_CANCELLED:
            // code to handle when the button click has been cancelled,  this is usually handled the same way as ENDED in most applications (e.g. another application takes control of the device)
            
            break;
    }
}

/**
 * Adds a sound effects toggle button
 * @param onButton (required) sound effects on button filepath
 * @param onButtonPressed (required) sound effects on pressed button filepath
 * @param offButton (required) sound effects off button filepath
 * @param offButtonPressed (required) sound effects off pressed button filepath
 * @param layer (required) the layer to which the button will be added a child
 * @param position (required) where would you like to position the button (SonarCocosHelper::UIButtonPosition::eBottomLeft, SonarCocosHelper::UIButtonPosition::eBottomRight, SonarCocosHelper::UIButtonPosition::eTopLeft, SonarCocosHelper::UIButtonPosition::eTopRight)
 */
SonarCocosHelper.UI.AddSoundEffectsToggle = function ( onButton, onButtonPressed, offButton, offButtonPressed, layer, position )
{
    SonarCocosHelper.UI.soundEffectsToggleButton = new ccui.Button( );
    SonarCocosHelper.UI.soundEffectsToggleButton.loadTextures( onButton, onButtonPressed );
    
    var visibleSize = cc.winSize;
    
    switch ( position )
    {
        case SonarCocosHelper.UIButtonPosition.eBottomLeft:
            SonarCocosHelper.UI.soundEffectsToggleButton.setPosition( SonarCocosHelper.UI.soundEffectsToggleButton.getContentSize( ).width / 2, SonarCocosHelper.UI.soundEffectsToggleButton.getContentSize( ).height / 2 );
            
            break;
            
        case SonarCocosHelper.UIButtonPosition.eBottomRight:
            SonarCocosHelper.UI.soundEffectsToggleButton.setPosition( visibleSize.width - SonarCocosHelper.UI.soundEffectsToggleButton.getContentSize( ).width / 2, SonarCocosHelper.UI.soundEffectsToggleButton.getContentSize( ).height / 2 );
            
            break;
            
        case SonarCocosHelper.UIButtonPosition.eTopLeft:
            SonarCocosHelper.UI.soundEffectsToggleButton.setPosition( SonarCocosHelper.UI.soundEffectsToggleButton.getContentSize( ).width / 2, visibleSize.height - SonarCocosHelper.UI.soundEffectsToggleButton.getContentSize( ).height / 2 );
            
            break;
            
        case SonarCocosHelper.UIButtonPosition.eTopRight:
            SonarCocosHelper.UI.soundEffectsToggleButton.setPosition( visibleSize.width - SonarCocosHelper.UI.soundEffectsToggleButton.getContentSize( ).width / 2, visibleSize.height - SonarCocosHelper.UI.soundEffectsToggleButton.getContentSize( ).height / 2 );
            
            break;
    }
    
    SonarCocosHelper.UI.soundEffectsToggleButton.addTouchEventListener( SonarCocosHelper.UI.SoundEffectsTouchEvent, this );
    
    layer.addChild( SonarCocosHelper.UI.soundEffectsToggleButton, 10000 );
    
    SonarCocosHelper.UI.SoundEffects.offButtonString = offButton;
    SonarCocosHelper.UI.SoundEffects.offButtonPressedString = offButtonPressed;
    SonarCocosHelper.UI.SoundEffects.onButtonString = onButton;
    SonarCocosHelper.UI.SoundEffects.onButtonPressedString = onButtonPressed;
}

/**
 * Set the sound effects toggle button position
 * @param xPos (required) x-axis position
 * @param yPos (required) y-axis position
 */
SonarCocosHelper.UI.SetSoundEffectsButtonPosition = function( xPos, yPos )
{
    SonarCocosHelper.UI.soundEffectsToggleButton.setPosition( xPos, yPos );
}

SonarCocosHelper.UI.SoundEffectsTouchEvent = function( sender, type )
{
    this.tempString;
    
    switch ( type )
    {
        case ccui.Widget.TOUCH_BEGAN:
            // code to handle when the button is first clicked
            
            break;
            
        case ccui.Widget.TOUCH_MOVED:
            // code to handle when the user is moving their finger/cursor whilst clicking the button
            
            break;
            
        case ccui.Widget.TOUCH_ENDED:
            // code to handle when the button click has ended (e.g. finger is lifted off the screen)
            tempString = SonarCocosHelper.UI.SoundEffects.offButtonString;
            
            SonarCocosHelper.UI.SoundEffects.offButtonString = SonarCocosHelper.UI.SoundEffects.onButtonString;
            SonarCocosHelper.UI.SoundEffects.onButtonString = tempString;
            
            tempString = SonarCocosHelper.UI.SoundEffects.offButtonPressedString;
            
            SonarCocosHelper.UI.SoundEffects.offButtonPressedString = SonarCocosHelper.UI.SoundEffects.onButtonPressedString;
            SonarCocosHelper.UI.SoundEffects.onButtonPressedString = tempString;
            
            SonarCocosHelper.UI.soundEffectsToggleButton.loadTextures( SonarCocosHelper.UI.SoundEffects.onButtonString, SonarCocosHelper.UI.SoundEffects.onButtonPressedString );
            
            this.volume;
            
            if ( cc.audioEngine.getEffectsVolume( ) > 0.0 )
            { volume = 0.0; }
            else
            { volume = 1.0; }
            
            cc.audioEngine.setEffectsVolume( volume );
            
            break;
            
        case ccui.Widget.TOUCH_CANCELLED:
            // code to handle when the button click has been cancelled,  this is usually handled the same way as ENDED in most applications (e.g. another application takes control of the device)
            
            break;
    }
}

/**
 * Adds a music toggle button
 * @param onButton (required) music on button filepath
 * @param onButtonPressed (required) music on pressed button filepath
 * @param offButton (required) music off button filepath
 * @param offButtonPressed (required) music off pressed button filepath
 * @param layer (required) the layer to which the button will be added a child
 * @param position (required) where would you like to position the button (SonarCocosHelper::UIButtonPosition::eBottomLeft, SonarCocosHelper::UIButtonPosition::eBottomRight, SonarCocosHelper::UIButtonPosition::eTopLeft, SonarCocosHelper::UIButtonPosition::eTopRight)
 */
SonarCocosHelper.UI.AddMusicToggle = function ( onButton, onButtonPressed, offButton, offButtonPressed, layer, position )
{
    SonarCocosHelper.UI.musicToggleButton = new ccui.Button( );
    SonarCocosHelper.UI.musicToggleButton.loadTextures( onButton, onButtonPressed );
    
    var visibleSize = cc.winSize;
    
    switch ( position )
    {
        case SonarCocosHelper.UIButtonPosition.eBottomLeft:
            SonarCocosHelper.UI.musicToggleButton.setPosition( SonarCocosHelper.UI.musicToggleButton.getContentSize( ).width / 2, SonarCocosHelper.UI.musicToggleButton.getContentSize( ).height / 2 );
            
            break;
            
        case SonarCocosHelper.UIButtonPosition.eBottomRight:
            SonarCocosHelper.UI.musicToggleButton.setPosition( visibleSize.width - SonarCocosHelper.UI.musicToggleButton.getContentSize( ).width / 2, SonarCocosHelper.UI.musicToggleButton.getContentSize( ).height / 2 );
            
            break;
            
        case SonarCocosHelper.UIButtonPosition.eTopLeft:
            SonarCocosHelper.UI.musicToggleButton.setPosition( SonarCocosHelper.UI.musicToggleButton.getContentSize( ).width / 2, visibleSize.height - SonarCocosHelper.UI.musicToggleButton.getContentSize( ).height / 2 );
            
            break;
            
        case SonarCocosHelper.UIButtonPosition.eTopRight:
            SonarCocosHelper.UI.musicToggleButton.setPosition( visibleSize.width - SonarCocosHelper.UI.musicToggleButton.getContentSize( ).width / 2, visibleSize.height - SonarCocosHelper.UI.musicToggleButton.getContentSize( ).height / 2 );
            
            break;
    }
    
    SonarCocosHelper.UI.musicToggleButton.addTouchEventListener( SonarCocosHelper.UI.MusicTouchEvent, this );
    
    layer.addChild( SonarCocosHelper.UI.musicToggleButton, 10000 );
    
    SonarCocosHelper.UI.Music.offButtonString = offButton;
    SonarCocosHelper.UI.Music.offButtonPressedString = offButtonPressed;
    SonarCocosHelper.UI.Music.onButtonString = onButton;
    SonarCocosHelper.UI.Music.onButtonPressedString = onButtonPressed;
}

/**
 * Set the music toggle button position
 * @param xPos (required) x-axis position
 * @param yPos (required) y-axis position
 */
SonarCocosHelper.UI.SetMusicButtonPosition = function( xPos, yPos )
{
    SonarCocosHelper.UI.musicToggleButton.setPosition( xPos, yPos );
}

SonarCocosHelper.UI.MusicTouchEvent = function( sender, type )
{
    this.tempString;
    
    switch ( type )
    {
        case ccui.Widget.TOUCH_BEGAN:
            // code to handle when the button is first clicked
            
            break;
            
        case ccui.Widget.TOUCH_MOVED:
            // code to handle when the user is moving their finger/cursor whilst clicking the button
            
            break;
            
        case ccui.Widget.TOUCH_ENDED:
            // code to handle when the button click has ended (e.g. finger is lifted off the screen)
            tempString = SonarCocosHelper.UI.Music.offButtonString;
            
            SonarCocosHelper.UI.Music.offButtonString = SonarCocosHelper.UI.Music.onButtonString;
            SonarCocosHelper.UI.Music.onButtonString = tempString;
            
            tempString = SonarCocosHelper.UI.Music.offButtonPressedString;
            
            SonarCocosHelper.UI.Music.offButtonPressedString = SonarCocosHelper.UI.Music.onButtonPressedString;
            SonarCocosHelper.UI.Music.onButtonPressedString = tempString;
            
            SonarCocosHelper.UI.musicToggleButton.loadTextures( SonarCocosHelper.UI.Music.onButtonString, SonarCocosHelper.UI.Music.onButtonPressedString );
            
            this.volume;
            
            if ( cc.audioEngine.getMusicVolume( ) > 0.0 )
            { volume = 0.0; }
            else
            { volume = 1.0; }
            
            cc.audioEngine.setMusicVolume( volume );
            
            break;
            
        case ccui.Widget.TOUCH_CANCELLED:
            // code to handle when the button click has been cancelled,  this is usually handled the same way as ENDED in most applications (e.g. another application takes control of the device)
            
            break;
    }
};


SonarCocosHelper.WeChat.shareTextToWeChat = function( textMsg )
{
    if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
    }
    else if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "sendTextMsgToWeChat:", textMsg );
    }
};

SonarCocosHelper.WeChat.shareImageToWeChat = function( thumbImgPath, imgPath )
{
    if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
    }
    else if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "sendThumbImage:andShareImgToWeChat:", thumbImgPath, imgPath );
    }
};

SonarCocosHelper.WeChat.shareLinkToWeChat = function( thumbImgPath, msgTitle, msgDescription, httpUrl )
{
    if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
    }
    else if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "sendLinkWithThumbImg:andMsgTitle:andMsgDescription:andURLToWeChat:", thumbImgPath, msgTitle, msgDescription, httpUrl);
    }
};

SonarCocosHelper.WeChat.shareMusicToWeChat = function( msgTitle, msgDescription, thumbImgPath, musicUrl, musicDataURL )
{
    if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
    }
    else if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "sendMusicContentWithTitle:andDescription:andThumbImg:andMusicUrl:andMusicDataUrl:", msgTitle, msgDescription, thumbImgPath, musicUrl, musicDataURL );
    }
};

SonarCocosHelper.WeChat.shareVideoToWeChat = function( msgTitle, msgDescription, thumbImgPath, videoUrl )
{
    if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
    }
    else if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "sendVideoContentWithTitle:andDescription:andThumbImg:andVideoUrl:", msgTitle, msgDescription, thumbImgPath, videoUrl );
    }
};

/**
 * schedule local notification with slide action text and a repeat interval
 * @param delay is the amount of time until the notification is display from the time this method is called
 * @param textToDisplay is the text to display in the notification
 * @param notificationTitle is the title for the notification
 * @param notificationAction is the text that appears below the message on the lock screen aka slide to action
 * @param repeatInterval is how often you want to repeat the action
 */
SonarCocosHelper.Notifications.scheduleLocalNotification = function( delay, textToDisplay, notificationTitle, notificationAction, repeatInterval )
{
    if ( typeof notificationAction === 'undefined' && typeof repeatInterval === 'undefined' )
    {
        if ( cc.sys.os == cc.sys.OS_ANDROID )
        {
        }
        else if ( cc.sys.os == cc.sys.OS_IOS )
        {
            jsb.reflection.callStaticMethod( "IOSJSHelper", "scheduleLocalNotification:andNotificationText:andNotificationTitle:", delay, textToDisplay, notificationTitle );
        }
    }
    else if ( typeof notificationAction !== 'undefined' && typeof repeatInterval !== 'undefined' )
    {
        if ( cc.sys.os == cc.sys.OS_ANDROID )
        {
        }
        else if ( cc.sys.os == cc.sys.OS_IOS )
        {
            jsb.reflection.callStaticMethod( "IOSJSHelper", "scheduleLocalNotification:andNotificationText:andNotificationTitle:andNotificationAction:andRepeatInterval:", delay, textToDisplay, notificationTitle, notificationAction, repeatInterval );
        }
    }
};

/**
 * unschedule all local notifications
 */
SonarCocosHelper.Notifications.unscheduleAllLocalNotifications = function( )
{
    if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
    }
    else if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "unscheduleAllLocalNotifications" );
    }
};

/**
 * unschedule local notification
 * @param notificationTitle is the notification that should be unscheduled
 */
SonarCocosHelper.Notifications.unscheduleLocalNotification = function( notificationTitle )
{
    if ( cc.sys.os == cc.sys.OS_ANDROID )
    {
    }
    else if ( cc.sys.os == cc.sys.OS_IOS )
    {
        jsb.reflection.callStaticMethod( "IOSJSHelper", "unscheduleLocalNotification:", notificationTitle );
    }
};


