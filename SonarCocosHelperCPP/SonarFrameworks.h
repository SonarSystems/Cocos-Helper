/*
 Copyright (C) 2015 Sonar Systems - All Rights Reserved
 You may use, distribute and modify this code under the
 terms of the MIT license
 
 Any external frameworks used have their own licenses and
 should be followed as such.
 */
//
//  SonarFrameworks.h
//  Sonar Cocos Helper
//
//  Created by Sonar Systems on 03/03/2015.
//
//

#ifndef __SONAR_FRAMEWORKS_H__
#define __SONAR_FRAMEWORKS_H__

#include "cocos2d.h"

#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
#include "JNIHelpers.h"
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
#include "SCHDefinitions.h"
#endif

#include "SimpleAudioEngine.h"
#include "extensions/cocos-ext.h"
#include "ui/CocosGUI.h"

using namespace cocos2d;

USING_NS_CC_EXT;
using namespace ui;

namespace SonarCocosHelper
{
enum AdBannerPosition { eBottom, eTop, eBoth };
enum UIButtonPosition { eBottomLeft, eBottomRight, eTopLeft, eTopRight };
enum CalendarUnits { eMinute = 0, eHourly, eDaily, eWeekly, eMonthly, eYearly };

class GooglePlayServices
{
public:
    /**
     * Check if the user is signed in
     * @return true is signed in, false is not signed in
     */
    static bool isSignedIn( );
    /**
     * Sign the user in
     */
    static void signIn( );
    /**
     * Sign the user out
     */
    static void signOut();
    /**
     * Submit score to online leaderboard
     * @param leaderboardID is the name of your leaderboard
     * @param score is the score to submit online
     */
    static void submitScore( const char *leaderboardID, long score );
    /**
     * Unlock achievement
     * @param achievementID is the achievement to unlock
     */
    static void unlockAchievement( const char *achievementID );
    /**
     * Increment incremental achievement
     * @param achievementID is the achievement to increment
     * @param numSteps is the number of steps to increase achievement by
     */
    static void incrementAchievement( const char *achievementID, int numSteps );
    /**
     * Show the achievements
     */
    static void showAchievements( );
    /**
     * Show leaderboards
     */
    static void showLeaderboards( );
    /**
     * Show leaderboard
     * @param leaderboardID is the leaderboard to display
     */
    static void showLeaderboard( const char *leaderboardID );
};

class IOS
{
public:
    /**
     * Initializes the Cocos Helper for use, only needs to be called once
     */
    static void Setup( );
    /**
     * Opens the share dialog
     * @param shareString is the string to share
     * @param imagePath is the path to an image to share as well (optional)
     */
    static void Share( cocos2d::__String shareString, cocos2d::__String imagePath );
};

class Facebook
{
public:
    //static void SignIn(); not needed for NOW
    /**
     * Share to Facebook
     * @param name is the post title (optional)
     * @param link is a link to be attached to the post (optional)
     * @param description is the text to post
     * @param caption is a caption to the main post (optional)
     * @param imagePath is the path to an image to share as well (optional)
     */
    static void Share( const char *name, const char *link, const char *description, const char *caption, const char *imagePath );
};

class Twitter
{
public:
    /**
     * Tweet to Twitter
     * @param description is the text to tweet
     * @param title is the tweet title (optional)
     * @param imagePath is the path to an image to tweet as well (optional)
     */
    static void Tweet( const char *tweet, const char *title, const char *imagePath );
};

class AdMob
{
public:
    /**
     * Show a banner on the top of the screen
     */
    static void showBannerAd( );
    /**
     * Show a banner on the top of the screen
     * @param SonarCocosHelper::eTop displays the ad banner at the top of the screen
     * @param SonarCocosHelper::eBottom displays the ad banner at the bottom of the screen
     * @param SonarCocosHelper::eBoth displays the a ad banner on the top and the bottom
     */
    static void showBannerAd( int position );
    /**
     * Hides all visible ad banners
     */
    static void hideBannerAd( );
    /**
     * Hide a banner on the top of the screen
     * @param SonarCocosHelper::eTop hide the ad banner at the top of the screen
     * @param SonarCocosHelper::eBottom hide the ad banner at the bottom of the screen
     * @param SonarCocosHelper::eBoth hides all visible ad banners
     */
    static void hideBannerAd( int position );
    /**
     * Load and then Shows a fullscreen interstitial ad
     */
    static void showFullscreenAd( );
    /**
    * preload a fullscreen interstitial ad
    */
    static void preLoadFullscreenAd();
    /**
    * shows the preloaded fullscreen interstitial ad
    */
    static void showPreLoadedFullscreenAd();
};

class Mopub
{
public:
    /**
     * Show a banner ad
     */
    static void showBannerAd( );
    /**
     * Hide the banner ad
     */
    static void hideBannerAd( );

    /**
     * Show fullscreen interstitial ad
     */
    static void showFullscreenAd( );
};

class iAds
{
public:
    /**
     * Show a banner on the top of the screen
     */
    static void showiAdBanner( );
    /**
     * Show a banner on the screen
     * @param SonarCocosHelper::eTop displays the ad banner at the top of the screen
     * @param SonarCocosHelper::eBottom displays the ad banner at the bottom of the screen
     */
    static void showiAdBanner( int position );
    /**
     * Hide ad banner
     */
    static void hideiAdBanner( );
};

class RevMob
{
public:
    /**
     * Show a fullscreen interstitial ad
     */
    static void showFullscreenAd( );
    /**
     * Show a popup ad
     */
    static void showPopupAd( );
    /**
     * Show a banner ad
     */
    static void showBannerAd( );
    /**
     * Hide the  banner ad
     */
    static void hideBannerAd( );
};

class Chartboost
{
public:
    /**
     * Show a fullscreen interstitial ad
     */
    static void showFullscreenAd( );
    /**
    * preload a fullscreen interstitial ad
    */
    static void preLoadFullscreenAd();
    /**
     * preload a video ad
     */
    static void preLoadVideoAd();
    /**
     * Show a fullscreen video ad
     */
    static void showVideoAd( );
    /**
     * Show more apps
     */
    static void showMoreApps( );
};

class GameCenter
{
public:
    /**
     * Sign the user in
     */
    static void signIn( );
    /**
     * Show leaderboard
     */
    static void showLeaderboard( );
    /**
     * Show achievements
     */
    static void showAchievements( );
    /**
     * Submit score to online leaderboard
     * @param scoreNumber is the score to submit online
     * @param leaderboardID is the name of your leaderboard
     */
    static void submitScore( int scoreNumber, cocos2d::__String leaderboardID );
    /**
     * Unlock achievement
     * @param achievementID is the achievement to unlock
     */
    static void unlockAchievement( cocos2d::__String achievementID );
    /**
     * Unlock percentage of an achievement
     * @param achievementID is the achievement to increment
     * @param percent is the percentage of the achievement to unlock
     */
    static void unlockAchievement( cocos2d::__String achievementID, float percent );
    /**
     * Reset all player achievements (cannot be undone)
     */
    static void resetPlayerAchievements( );
};

class GoogleAnalytics
{
public:
    /**
     * Set the screen name
     * @param screenName string to set for the screen
     */
    static void setScreenName( cocos2d::__String screenName );
    /**
     * Set dispatch interval (frequency of which data is submitted to the server)
     * @param dispatchInterval submit frequency
     */
    static void setDispatchInterval( int dispatchInterval );
    /**
     * Set a custom event
     * @param category (required) a string of what the event category is
     * @param action (required) a string of what the action performed is
     * @param label (optional) a string of what the label for the action is
     * @param label (optional, only for Android not iOS) a value of the event that has occurred
     */
    static void sendEvent( cocos2d::__String category, cocos2d::__String action, cocos2d::__String label, long value );
};

class AdColony
{
public:
    /**
     * Show ad
     * @param withPreOp (required), show optional popup before the ad is shown
     * @param withPostOp (required), show optional popup after the ad is shown
     */
    static void showVideoAC ( bool withPreOp, bool withPostOp );
};

class Vungle
{
public:
    /**
     * Show ad
     * @param isIncentivised (required) true is a reward video ad, false is a regular video ad
     */
    static void ShowVideoVungle( bool isIncentivised );
};

class UI
{
public:
    /**
     * Constructor
     */
    UI( );

    /**
     * Adds a audio toggle button (sounds effects and music)
     * @param onButton (required) audio on button filepath
     * @param onButtonPressed (required) audio on pressed button filepath
     * @param offButton (required) audio off button filepath
     * @param offButtonPressed (required) audio off pressed button filepath
     * @param layer (required) the layer to which the button will be added a child
     * @param position (required) where would you like to position the button (SonarCocosHelper::UIButtonPosition::eBottomLeft, SonarCocosHelper::UIButtonPosition::eBottomRight, SonarCocosHelper::UIButtonPosition::eTopLeft, SonarCocosHelper::UIButtonPosition::eTopRight)
     */
    void AddAudioToggle( const std::string &onButton, const std::string &onButtonPressed, const std::string &offButton, const std::string &offButtonPressed, cocos2d::Layer *layer, int position );
    /**
     * Set the audio toggle button position
     * @param xPos (required) x-axis position
     * @param yPos (required) y-axis position
     */
    void SetAudioButtonPosition( float xPos, float yPos );

    /**
     * Adds a sound effects toggle button
     * @param onButton (required) sound effects on button filepath
     * @param onButtonPressed (required) sound effects on pressed button filepath
     * @param offButton (required) sound effects off button filepath
     * @param offButtonPressed (required) sound effects off pressed button filepath
     * @param layer (required) the layer to which the button will be added a child
     * @param position (required) where would you like to position the button (SonarCocosHelper::UIButtonPosition::eBottomLeft, SonarCocosHelper::UIButtonPosition::eBottomRight, SonarCocosHelper::UIButtonPosition::eTopLeft, SonarCocosHelper::UIButtonPosition::eTopRight)
     */
    void AddSoundEffectsToggle( const std::string &onButton, const std::string &onButtonPressed, const std::string &offButton, const std::string &offButtonPressed, cocos2d::Layer *layer, int position );
    /**
     * Set the sound effects toggle button position
     * @param xPos (required) x-axis position
     * @param yPos (required) y-axis position
     */
    void SetSoundEffectsButtonPosition( float xPos, float yPos );

    /**
     * Adds a music toggle button
     * @param onButton (required) music on button filepath
     * @param onButtonPressed (required) music on pressed button filepath
     * @param offButton (required) music off button filepath
     * @param offButtonPressed (required) music off pressed button filepath
     * @param layer (required) the layer to which the button will be added a child
     * @param position (required) where would you like to position the button (SonarCocosHelper::UIButtonPosition::eBottomLeft, SonarCocosHelper::UIButtonPosition::eBottomRight, SonarCocosHelper::UIButtonPosition::eTopLeft, SonarCocosHelper::UIButtonPosition::eTopRight)
     */
    void AddMusicToggle( const std::string &onButton, const std::string &onButtonPressed, const std::string &offButton, const std::string &offButtonPressed, cocos2d::Layer *layer, int position );
    /**
     * Set the music toggle button position
     * @param xPos (required) x-axis position
     * @param yPos (required) y-axis position
     */
    void SetMusicButtonPosition( float xPos, float yPos );

private:
    ui::Button *audioToggleButton;
    ui::Button *soundEffectsToggleButton;
    ui::Button *musicToggleButton;

    class Audio
    {
    public:
        static std::string offButtonString;
        static std::string offButtonPressedString;
        static std::string onButtonString;
        static std::string onButtonPressedString;
    };

    class SoundEffects
    {
    public:
        static std::string offButtonString;
        static std::string offButtonPressedString;
        static std::string onButtonString;
        static std::string onButtonPressedString;
    };

    class Music
    {
    public:
        static std::string offButtonString;
        static std::string offButtonPressedString;
        static std::string onButtonString;
        static std::string onButtonPressedString;
    };

    void AudioTouchEvent( Ref *sender, Widget::TouchEventType type );
    void SoundEffectsTouchEvent( Ref *sender, Widget::TouchEventType type );
    void MusicTouchEvent( Ref *sender, Widget::TouchEventType type );
};

// NOT WORKING ATM
/*class Everyplay
{
public:
    static void setup( );
    static void showEveryplay( );
    static void record( );
    static void playLastVideoRecording( );
};*/


class WeChat
{
public:
    /**
     * share text content to WeChat
     * @param shareString is the string to share
     */
    static void shareTextToWeChat( cocos2d::__String shareString );
    /**
     * share images to WeChat
     * @param thumbImgPath is the thumb image of the image to share
     * @param imgPath is the image to share
     */
    static void shareImageToWeChat( cocos2d::__String thumbImgPath , cocos2d::__String imgPath );

    /**
     * share link to WeChat
     * @param thumbImgPath is the thumb image of the link to share
     * @param msgTitle is the tile of link to share
     * @param msgDescription is the descripton of link to share
     * @param httpUrl is the link to share
     */
    static void shareLinkToWeChat( cocos2d::__String thumbImgPath , cocos2d::__String msgTitle , cocos2d::__String msgDescription , cocos2d::__String httpUrl);


    /**
     * share music to WeChat
     * @param msgTitle is the tile of link to share
     * @param msgDescription is the descripton of link to share
     * @param thumbImgPath is the thumb image of the music to share
     * @param musicUrl is the url of music loaded page
     * @param musicDataURL is the url of music file
     */
    static void shareMusicToWeChat( cocos2d::__String msgTitle , cocos2d::__String msgDescription , cocos2d::__String thumbImgPath , cocos2d::__String musicUrl , cocos2d::__String musicDataURL);


    /**
     * share video to WeChat
     * @param msgTitle is the tile of link to share
     * @param msgDescription is the descripton of link to share
     * @param thumbImgPath is the thumb image of the link to share
     * @param videoUrl is the link to share
     */

    static void shareVideoToWeChat( cocos2d::__String msgTitle , cocos2d::__String msgDescription , cocos2d::__String thumbImgPath , cocos2d::__String videoUrl);
};

class Notifications
{
public:
    /**
     * schedule local notification
     * @param delay is the amount of time until the notification is display from the time this method is called
     * @param textToDisplay is the text to display in the notification
     * @param notificationTitle is the title for the notification
     */
    static void scheduleLocalNotification( float delay, cocos2d::__String textToDisplay, cocos2d::__String notificationTitle , int notificationTag);
    /**
     * schedule local notification with slide action text
     * @param delay is the amount of time until the notification is display from the time this method is called
     * @param textToDisplay is the text to display in the notification
     * @param notificationTitle is the title for the notification
     * @param notificationAction is the text that appears below the message on the lock screen aka slide to action
     */
    static void scheduleLocalNotification( float delay, cocos2d::__String textToDisplay, cocos2d::__String notificationTitle, cocos2d::__String notificationAction , int notificationTag);
    /**
     * schedule local notification with a repeat interval
     * @param delay is the amount of time until the notification is display from the time this method is called
     * @param textToDisplay is the text to display in the notification
     * @param notificationTitle is the title for the notification
     * @param repeatInterval is how often you want to repeat the action
     */
    static void scheduleLocalNotification( float delay, cocos2d::__String textToDisplay, cocos2d::__String notificationTitle, int repeatInterval , int notificationTag);
    /**
     * schedule local notification with slide action text and a repeat interval
     * @param delay is the amount of time until the notification is display from the time this method is called
     * @param textToDisplay is the text to display in the notification
     * @param notificationTitle is the title for the notification
     * @param notificationAction is the text that appears below the message on the lock screen aka slide to action
     * @param repeatInterval is how often you want to repeat the action
     */
    static void scheduleLocalNotification( float delay, cocos2d::__String textToDisplay, cocos2d::__String notificationTitle, cocos2d::__String notificationAction, int repeatInterval , int notificationTag);

    /**
     * unschedule all local notifications
     */
    static void unscheduleAllLocalNotifications( );
    /**
     * unschedule local notification
     * @param notificationTitle is the notification that should be unscheduled
     */
    //static void unscheduleLocalNotification( cocos2d::__String notificationTitle );
    static void unscheduleLocalNotification( int notificationTag );

};
class AmazonGameCircle
{
public:
    /**
            * show leaderboard
            * @param leaderboardID is the name of your leaderboard
            */
    static void showLeaderboard( const char *leaderboardID );
    /**
     * Submit score to online leaderboard
     * @param leaderboardID is the name of your leaderboard
     * @param score is the score to submit online
     */
    static void submitScore( const char *leaderboardID, long score );

    static void showLeaderboards();

    static void showAchievements();

    /* show achievement
     * @param achievementID is the name of the achievement
     *
     */
    static void unlockAchievement(const char* achievementID);
};

class FlurryAnalytics
{
public:
    static void sendLogEvent(cocos2d::__String eventId);
    static void sendLogEvent(cocos2d::__String eventId,bool timed);
    static void endLogEvent(cocos2d::__String eventId);
};
}

#endif
