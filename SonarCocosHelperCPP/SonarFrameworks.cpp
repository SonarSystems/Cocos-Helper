/*
 Copyright (C) 2015 Sonar Systems - All Rights Reserved
 You may use, distribute and modify this code under the
 terms of the MIT license
 
 Any external frameworks used have their own licenses and
 should be followed as such.
 */
//
//  SonarFrameworks.cpp
//  Sonar Cocos Helper
//
//  Created by Sonar Systems on 03/03/2015.
//

#include "SonarFrameworks.h"

#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
#include <jni.h>
#include <android/log.h>
#define CLASS_NAME "sonar/systems/framework/SonarFrameworkFunctions"
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
#include "IOSCPPHelper.h"
#endif

using namespace SonarCocosHelper;

std::string UI::Audio::offButtonString = "";
std::string UI::Audio::offButtonPressedString = "";
std::string UI::Audio::onButtonString = "";
std::string UI::Audio::onButtonPressedString = "";
std::string UI::SoundEffects::offButtonString = "";
std::string UI::SoundEffects::offButtonPressedString = "";
std::string UI::SoundEffects::onButtonString = "";
std::string UI::SoundEffects::onButtonPressedString = "";
std::string UI::Music::offButtonString = "";
std::string UI::Music::offButtonPressedString = "";
std::string UI::Music::onButtonString = "";
std::string UI::Music::onButtonPressedString = "";

void IOS::Setup( )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    IOSCPPHelper::Setup( );
#endif
}

void IOS::Share( cocos2d::__String shareString, cocos2d::__String imagePath )
{
#if SCH_IS_SOCIAL_ENABLED == true
    IOSCPPHelper::shareWithString( shareString, imagePath );
#endif
}

bool GooglePlayServices::isSignedIn()
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    return JniHelpers::jniCommonBoolCall(
               "isSignedIn",
               CLASS_NAME);
#endif

    return 0;
}

void GooglePlayServices::signIn()
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    JniHelpers::jniCommonVoidCall(
        "gameServicesSignIn",
        CLASS_NAME);
#endif
}

void GooglePlayServices::signOut()
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    JniHelpers::jniCommonVoidCall(
        "gameServicesSignOut",
        CLASS_NAME);
#endif
}

void GooglePlayServices::submitScore(const char* leaderboardID, long score)
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    JniHelpers::jniCommonVoidCall(
        "submitScore",
        CLASS_NAME,
        leaderboardID,
        score);
#endif
}

void GooglePlayServices::unlockAchievement(const char* achievementID)
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    JniHelpers::jniCommonVoidCall(
        "unlockAchievement",
        CLASS_NAME,
        achievementID);
#endif
}

void GooglePlayServices::incrementAchievement(const char* achievementID, int numSteps)
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    JniHelpers::jniCommonVoidCall(
        "incrementAchievement",
        CLASS_NAME,
        achievementID,
        numSteps);
#endif
}

void GooglePlayServices::showAchievements()
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    JniHelpers::jniCommonVoidCall(
        "showAchievements",
        CLASS_NAME);
#endif
}

void GooglePlayServices::showLeaderboards()
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    JniHelpers::jniCommonVoidCall(
        "showLeaderboards",
        CLASS_NAME);
#endif
}

void GooglePlayServices::showLeaderboard(const char* leaderboardID)
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    JniHelpers::jniCommonVoidCall(
        "showLeaderboard",
        CLASS_NAME,
        leaderboardID);
#endif
}

void GameCenter::signIn( )
{
#if SCH_IS_GAME_CENTER_ENABLED == true && CC_TARGET_PLATFORM == CC_PLATFORM_IOS
    IOSCPPHelper::gameCenterLogin( );
#endif
}

void GameCenter::showLeaderboard( )
{
#if SCH_IS_GAME_CENTER_ENABLED == true && CC_TARGET_PLATFORM == CC_PLATFORM_IOS
    IOSCPPHelper::gameCenterShowLeaderboard( );
#endif
}

void GameCenter::showAchievements( )
{
#if SCH_IS_GAME_CENTER_ENABLED == true && CC_TARGET_PLATFORM == CC_PLATFORM_IOS
    IOSCPPHelper::gameCenterShowAchievements( );
#endif
}

void GameCenter::submitScore( int scoreNumber, cocos2d::__String leaderboardID )
{
#if SCH_IS_GAME_CENTER_ENABLED == true && CC_TARGET_PLATFORM == CC_PLATFORM_IOS
    IOSCPPHelper::gameCenterSubmitScore( scoreNumber, leaderboardID );
#endif
}

void GameCenter::unlockAchievement( cocos2d::__String achievementID )
{
#if SCH_IS_GAME_CENTER_ENABLED == true && CC_TARGET_PLATFORM == CC_PLATFORM_IOS
    IOSCPPHelper::gameCenterUnlockAchievement( achievementID, 100.0f );
#endif
}

void GameCenter::unlockAchievement( cocos2d::__String achievementID, float percent )
{
#if SCH_IS_GAME_CENTER_ENABLED == true && CC_TARGET_PLATFORM == CC_PLATFORM_IOS
    IOSCPPHelper::gameCenterUnlockAchievement( achievementID, percent );
#endif
}

void GameCenter::resetPlayerAchievements( )
{
#if SCH_IS_GAME_CENTER_ENABLED == true && CC_TARGET_PLATFORM == CC_PLATFORM_IOS
    IOSCPPHelper::gameCenterResetPlayerAchievements( );
#endif
}


/*void Facebook::SignIn()
{
	#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
			return JniHelpers::jniCommonVoidCall(
				"FacebookSignIn",
				CLASS_NAME);
	#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
		//TODO
	#endif
}*/

void Facebook::Share(const char* name,const char* link, const char* description, const char* caption, const char* imagePath )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    return JniHelpers::jniCommonVoidCall(
               "FacebookShare",
               CLASS_NAME,
               name,
               link,
               description,
               caption,
               imagePath);
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
        #if SCH_IS_SOCIAL_ENABLED == true

    IOSCPPHelper::shareViaFacebook( description, imagePath );
#endif
	#endif
}

void Twitter::Tweet(const char* tweet,const char* title, const char *imagePath)
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    JniHelpers::jniCommonVoidCall(
        "TwitterTweet",
        CLASS_NAME,
        tweet,
        title);
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
        #if SCH_IS_SOCIAL_ENABLED == true

    IOSCPPHelper::shareViaTwitter( tweet, imagePath );
#endif
	#endif
}

void Mopub::showBannerAd()
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    return JniHelpers::jniCommonVoidCall(
               "ShowBannerAdMP",
               CLASS_NAME
           );
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
        #if SCH_IS_MOPUB_ENABLED == true

    IOSCPPHelper::showMopubBanner();
#endif
    #endif

}

void Mopub::hideBannerAd()
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    return JniHelpers::jniCommonVoidCall(
               "HideBannerAdMP",
               CLASS_NAME
           );
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
        #if SCH_IS_MOPUB_ENABLED == true

    IOSCPPHelper::hideMopubBanner();
#endif
    #endif

}

void Mopub::showFullscreenAd( )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    return JniHelpers::jniCommonVoidCall(
               "ShowFullscreenAdMP",
               CLASS_NAME
           );
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
        #if SCH_IS_MOPUB_ENABLED == true

    IOSCPPHelper::showMoPubFullscreenAd();
#endif
    #endif

}

void AdMob::showBannerAd()
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    return JniHelpers::jniCommonVoidCall(
               "ShowBannerAd",
               CLASS_NAME,
               AdBannerPosition::eTop);
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
        #if SCH_IS_AD_MOB_ENABLED == true

    IOSCPPHelper::showAdMobBanner( AdBannerPosition::eTop );
#endif
	#endif
}

void AdMob::showBannerAd(int position)
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    return JniHelpers::jniCommonVoidCall(
               "ShowBannerAd",
               CLASS_NAME,
               position);
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
        #if SCH_IS_AD_MOB_ENABLED == true

    IOSCPPHelper::showAdMobBanner( position );
#endif
	#endif
}

void AdMob::hideBannerAd()
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    return JniHelpers::jniCommonVoidCall(
               "HideBannerAd",
               CLASS_NAME);
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
        #if SCH_IS_AD_MOB_ENABLED == true

    IOSCPPHelper::hideAdMobBanner( eBoth );
#endif
	#endif
}

void AdMob::hideBannerAd( int position )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    return JniHelpers::jniCommonVoidCall(
               "HideBannerAd",
               CLASS_NAME,
               position);
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
        #if SCH_IS_AD_MOB_ENABLED == true

    IOSCPPHelper::hideAdMobBanner( position );
#endif
	#endif
}

void AdMob::showFullscreenAd()
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    return JniHelpers::jniCommonVoidCall(
               "ShowFullscreenAdAM",
               CLASS_NAME);
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
	#if SCH_IS_AD_MOB_ENABLED == true

    IOSCPPHelper::showAdMobFullscreenAd( );
#endif
#endif
}

void AdMob::preLoadFullscreenAd()
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    return JniHelpers::jniCommonVoidCall(
               "PreLoadFullscreenAdAM",
               CLASS_NAME);
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
	#if SCH_IS_AD_MOB_ENABLED == true
    //todo
#endif
#endif
}

void AdMob::showPreLoadedFullscreenAd()
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    return JniHelpers::jniCommonVoidCall(
               "ShowPreLoadedFullscreenAdAM",
               CLASS_NAME);
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
	#if SCH_IS_AD_MOB_ENABLED == true
    //todo
#endif
#endif
}

void RevMob::showFullscreenAd()
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    return JniHelpers::jniCommonVoidCall(
               "ShowFullscreenAd",
               CLASS_NAME);
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    #if SCH_IS_REVMOB_ENABLED == true

    IOSCPPHelper::showRevMobFullScreenAd( );
#endif
#endif
}

void RevMob::showPopupAd( )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    return JniHelpers::jniCommonVoidCall(
               "ShowPopUpAd",
               CLASS_NAME);
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    #if SCH_IS_REVMOB_ENABLED == true

    IOSCPPHelper::showRevMobPopupAd( );
#endif
#endif
}

void RevMob::showBannerAd( )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    return JniHelpers::jniCommonVoidCall("ShowBannerAd",CLASS_NAME);
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    #if SCH_IS_REVMOB_ENABLED == true

    IOSCPPHelper::showRevMobBanner( );
#endif
#endif
}

void RevMob::hideBannerAd( )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    return JniHelpers::jniCommonVoidCall("HideBannerAd",CLASS_NAME);
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    #if SCH_IS_REVMOB_ENABLED == true

    IOSCPPHelper::hideRevMobBanner( );
#endif
#endif
}

void Chartboost::showFullscreenAd( )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    return JniHelpers::jniCommonVoidCall(
               "ShowFullscreenAdCB",
               CLASS_NAME);
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    #if SCH_IS_CHARTBOOST_ENABLED == true

    IOSCPPHelper::showChartboostFullScreenAd( );
#endif
#endif
}

void Chartboost::preLoadFullscreenAd()
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    return JniHelpers::jniCommonVoidCall(
               "PreLoadFullscreenAdCB",
               CLASS_NAME);
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
	#if SCH_IS_AD_MOB_ENABLED == true
    //todo
#endif
#endif
}

void Chartboost::preLoadVideoAd()
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    return JniHelpers::jniCommonVoidCall(
               "PreLoadVideoAdCB",
               CLASS_NAME);
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
	#if SCH_IS_AD_MOB_ENABLED == true
    //todo
#endif
#endif
}

void Chartboost::showVideoAd( )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    return JniHelpers::jniCommonVoidCall(
               "ShowVideoAdCB",
               CLASS_NAME);
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    #if SCH_IS_CHARTBOOST_ENABLED == true

    IOSCPPHelper::showChartboostVideoAd( );
#endif
#endif
}

void Chartboost::showMoreApps( )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    #if SCH_IS_CHARTBOOST_ENABLED == true
    IOSCPPHelper::showChartboostMoreApps( );
#endif
#endif
}

void iAds::showiAdBanner( )
{
#if SCH_IS_iADS_ENABLED == true && CC_TARGET_PLATFORM == CC_PLATFORM_IOS
    IOSCPPHelper::showiAdBanner( AdBannerPosition::eTop );
#endif
}

void iAds::showiAdBanner( int position )
{
#if SCH_IS_iADS_ENABLED == true && CC_TARGET_PLATFORM == CC_PLATFORM_IOS
    IOSCPPHelper::showiAdBanner( position );
#endif
}

void iAds::hideiAdBanner( )
{
#if SCH_IS_iADS_ENABLED == true && CC_TARGET_PLATFORM == CC_PLATFORM_IOS
    IOSCPPHelper::hideiAdBanner( );
#endif
}

void GoogleAnalytics::setScreenName( cocos2d::__String screenName )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    return JniHelpers::jniCommonVoidCall(
               "SetGAScreenName",
               CLASS_NAME,
               screenName.getCString());

#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    #if SCH_IS_GOOGLE_ANALYTICS_ENABLED == true

    IOSCPPHelper::setGAScreenName( screenName );
#endif
#endif
}

void GoogleAnalytics::setDispatchInterval( int dispatchInterval )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    return JniHelpers::jniCommonVoidCall(
               "SetGADispatchInterval",
               CLASS_NAME,
               dispatchInterval);
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    #if SCH_IS_GOOGLE_ANALYTICS_ENABLED == true

    IOSCPPHelper::setGADispatchInterval( dispatchInterval );
#endif
#endif
}

void GoogleAnalytics::sendEvent( cocos2d::__String category, cocos2d::__String action, cocos2d::__String label, long value )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    return JniHelpers::jniCommonVoidCall(
               "SendGAEvent",
               CLASS_NAME,
               category.getCString(),
               action.getCString(),
               label.getCString(),
               value);
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    #if SCH_IS_GOOGLE_ANALYTICS_ENABLED == true

    IOSCPPHelper::sendGAEvent( category, action, label, value );
#endif
#endif
}

void AdColony::showVideoAC (bool withPreOp, bool withPostOp)
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    return JniHelpers::jniCommonVoidCall(
               "ShowRewardedVideoAdAC",
               CLASS_NAME);
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    #if SCH_IS_ADCOLONY_ENABLED == true

    IOSCPPHelper::showVideoAC( withPreOp, withPostOp );
#endif
#endif
}

void Vungle::ShowVideoVungle( bool isIncentivised )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    return JniHelpers::jniCommonVoidCall(
               "ShowRewardedVideoAdV",
               CLASS_NAME,
               isIncentivised);
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    #if SCH_IS_VUNGLE_ENABLED == true

    IOSCPPHelper::showVideoVungle( isIncentivised );
#endif
#endif
}

UI::UI( )
{ }

void UI::AddAudioToggle( const std::string &onButton, const std::string &onButtonPressed, const std::string &offButton, const std::string &offButtonPressed, cocos2d::Layer *layer, int position )
{
    audioToggleButton = ui::Button::create( onButton, onButtonPressed );

    Size visibleSize = Director::getInstance( )->getVisibleSize( );
    Vec2 origin = Director::getInstance( )->getVisibleOrigin( );

    switch ( position )
    {
    case SonarCocosHelper::UIButtonPosition::eBottomLeft:
        audioToggleButton->cocos2d::Node::setPosition( audioToggleButton->getContentSize( ).width / 2 + origin.x, audioToggleButton->getContentSize( ).height / 2 + origin.y );

        break;

    case SonarCocosHelper::UIButtonPosition::eBottomRight:
        audioToggleButton->cocos2d::Node::setPosition( visibleSize.width - audioToggleButton->getContentSize( ).width / 2 + origin.x, audioToggleButton->getContentSize( ).height / 2 + origin.y );

        break;

    case SonarCocosHelper::UIButtonPosition::eTopLeft:
        audioToggleButton->cocos2d::Node::setPosition( audioToggleButton->getContentSize( ).width / 2 + origin.x, visibleSize.height - audioToggleButton->getContentSize( ).height / 2 + origin.y );

        break;

    case SonarCocosHelper::UIButtonPosition::eTopRight:
        audioToggleButton->cocos2d::Node::setPosition( visibleSize.width - audioToggleButton->getContentSize( ).width / 2 + origin.x, visibleSize.height - audioToggleButton->getContentSize( ).height / 2 + origin.y );

        break;
    }

    audioToggleButton->addTouchEventListener( CC_CALLBACK_2( UI::AudioTouchEvent, this ) );

    layer->addChild( audioToggleButton, 10000 );

    UI::Audio::offButtonString = offButton;
    UI::Audio::offButtonPressedString = offButtonPressed;
    UI::Audio::onButtonString = onButton;
    UI::Audio::onButtonPressedString = onButtonPressed;
}

void UI::SetAudioButtonPosition( float xPos, float yPos )
{
    audioToggleButton->cocos2d::Node::setPosition( xPos, yPos );
}

void UI::AudioTouchEvent( Ref *sender, Widget::TouchEventType type )
{
    ui::Button *node = ( ui::Button * )sender;

    std::string tempString;

    switch ( type )
    {
    case Widget::TouchEventType::BEGAN:
        // code to handle when the button is first clicked

        break;

    case Widget::TouchEventType::MOVED:
        // code to handle when the user is moving their finger/cursor whilst clicking the button
        break;

    case Widget::TouchEventType::ENDED:
        tempString = UI::Audio::offButtonString;

        UI::Audio::offButtonString = UI::Audio::onButtonString;
        UI::Audio::onButtonString = tempString;

        tempString = UI::Audio::offButtonPressedString;

        UI::Audio::offButtonPressedString = UI::Audio::onButtonPressedString;
        UI::Audio::onButtonPressedString = tempString;

        node->loadTextures( UI::Audio::onButtonString, UI::Audio::onButtonPressedString );

        float volume;

        if ( CocosDenshion::SimpleAudioEngine::getInstance( )->getEffectsVolume( ) > 0.0f )
        {
            volume = 0.0f;
        }
        else
        {
            volume = 1.0f;
        }

        CocosDenshion::SimpleAudioEngine::getInstance( )->setEffectsVolume( volume );

        if ( CocosDenshion::SimpleAudioEngine::getInstance( )->getBackgroundMusicVolume( ) > 0.0f )
        {
            volume = 0.0f;
        }
        else
        {
            volume = 1.0f;
        }

        CocosDenshion::SimpleAudioEngine::getInstance( )->setBackgroundMusicVolume( volume );

        break;

    case Widget::TouchEventType::CANCELED:
        // code to handle when the button click has been cancelled,  this is usually handled the same way as ENDED in most applications (e.g. another application takes control of the device)


        break;

    default:
        break;
    }
}

void UI::AddSoundEffectsToggle( const std::string &onButton, const std::string &onButtonPressed, const std::string &offButton, const std::string &offButtonPressed, cocos2d::Layer *layer, int position )
{
    soundEffectsToggleButton = ui::Button::create( onButton, onButtonPressed );

    Size visibleSize = Director::getInstance( )->getVisibleSize( );
    Vec2 origin = Director::getInstance( )->getVisibleOrigin( );

    switch ( position )
    {
    case SonarCocosHelper::UIButtonPosition::eBottomLeft:
        soundEffectsToggleButton->cocos2d::Node::setPosition( soundEffectsToggleButton->getContentSize( ).width / 2 + origin.x, soundEffectsToggleButton->getContentSize( ).height / 2 + origin.y );

        break;

    case SonarCocosHelper::UIButtonPosition::eBottomRight:
        soundEffectsToggleButton->cocos2d::Node::setPosition( visibleSize.width - soundEffectsToggleButton->getContentSize( ).width / 2 + origin.x, soundEffectsToggleButton->getContentSize( ).height / 2 + origin.y );

        break;

    case SonarCocosHelper::UIButtonPosition::eTopLeft:
        soundEffectsToggleButton->cocos2d::Node::setPosition( soundEffectsToggleButton->getContentSize( ).width / 2 + origin.x, visibleSize.height - soundEffectsToggleButton->getContentSize( ).height / 2 + origin.y );

        break;

    case SonarCocosHelper::UIButtonPosition::eTopRight:
        soundEffectsToggleButton->cocos2d::Node::setPosition( visibleSize.width - soundEffectsToggleButton->getContentSize( ).width / 2 + origin.x, visibleSize.height - soundEffectsToggleButton->getContentSize( ).height / 2 + origin.y );

        break;
    }

    soundEffectsToggleButton->addTouchEventListener( CC_CALLBACK_2( UI::SoundEffectsTouchEvent, this ) );

    layer->addChild( soundEffectsToggleButton, 10000 );

    UI::SoundEffects::offButtonString = offButton;
    UI::SoundEffects::offButtonPressedString = offButtonPressed;
    UI::SoundEffects::onButtonString = onButton;
    UI::SoundEffects::onButtonPressedString = onButtonPressed;
}

void UI::SetSoundEffectsButtonPosition( float xPos, float yPos )
{
    soundEffectsToggleButton->cocos2d::Node::setPosition( xPos, yPos );
}

void UI::SoundEffectsTouchEvent( Ref *sender, Widget::TouchEventType type )
{
    ui::Button *node = ( ui::Button * )sender;

    std::string tempString;

    switch ( type )
    {
    case Widget::TouchEventType::BEGAN:
        // code to handle when the button is first clicked

        break;

    case Widget::TouchEventType::MOVED:
        // code to handle when the user is moving their finger/cursor whilst clicking the button
        break;

    case Widget::TouchEventType::ENDED:
        tempString = UI::SoundEffects::offButtonString;

        UI::SoundEffects::offButtonString = UI::SoundEffects::onButtonString;
        UI::SoundEffects::onButtonString = tempString;

        tempString = UI::SoundEffects::offButtonPressedString;

        UI::SoundEffects::offButtonPressedString = UI::SoundEffects::onButtonPressedString;
        UI::SoundEffects::onButtonPressedString = tempString;

        node->loadTextures( UI::SoundEffects::onButtonString, UI::SoundEffects::onButtonPressedString );

        float volume;

        if ( CocosDenshion::SimpleAudioEngine::getInstance( )->getEffectsVolume( ) > 0.0f )
        {
            volume = 0.0f;
        }
        else
        {
            volume = 1.0f;
        }

        CocosDenshion::SimpleAudioEngine::getInstance( )->setEffectsVolume( volume );

        break;

    case Widget::TouchEventType::CANCELED:
        // code to handle when the button click has been cancelled,  this is usually handled the same way as ENDED in most applications (e.g. another application takes control of the device)


        break;

    default:
        break;
    }
}

void UI::AddMusicToggle( const std::string &onButton, const std::string &onButtonPressed, const std::string &offButton, const std::string &offButtonPressed, cocos2d::Layer *layer, int position )
{
    musicToggleButton = ui::Button::create( onButton, onButtonPressed );

    Size visibleSize = Director::getInstance( )->getVisibleSize( );
    Vec2 origin = Director::getInstance( )->getVisibleOrigin( );

    switch ( position )
    {
    case SonarCocosHelper::UIButtonPosition::eBottomLeft:
        musicToggleButton->cocos2d::Node::setPosition( musicToggleButton->getContentSize( ).width / 2 + origin.x, musicToggleButton->getContentSize( ).height / 2 + origin.y );

        break;

    case SonarCocosHelper::UIButtonPosition::eBottomRight:
        musicToggleButton->cocos2d::Node::setPosition( visibleSize.width - musicToggleButton->getContentSize( ).width / 2 + origin.x, musicToggleButton->getContentSize( ).height / 2 + origin.y );

        break;

    case SonarCocosHelper::UIButtonPosition::eTopLeft:
        musicToggleButton->cocos2d::Node::setPosition( musicToggleButton->getContentSize( ).width / 2 + origin.x, visibleSize.height - musicToggleButton->getContentSize( ).height / 2 + origin.y );

        break;

    case SonarCocosHelper::UIButtonPosition::eTopRight:
        musicToggleButton->cocos2d::Node::setPosition( visibleSize.width - musicToggleButton->getContentSize( ).width / 2 + origin.x, visibleSize.height - musicToggleButton->getContentSize( ).height / 2 + origin.y );

        break;
    }

    musicToggleButton->addTouchEventListener( CC_CALLBACK_2( UI::MusicTouchEvent, this ) );

    layer->addChild( musicToggleButton, 10000 );

    UI::Music::offButtonString = offButton;
    UI::Music::offButtonPressedString = offButtonPressed;
    UI::Music::onButtonString = onButton;
    UI::Music::onButtonPressedString = onButtonPressed;
}

void UI::SetMusicButtonPosition( float xPos, float yPos )
{
    musicToggleButton->cocos2d::Node::setPosition( xPos, yPos );
}

void UI::MusicTouchEvent( Ref *sender, Widget::TouchEventType type )
{
    ui::Button *node = ( ui::Button * )sender;

    std::string tempString;

    switch ( type )
    {
    case Widget::TouchEventType::BEGAN:
        // code to handle when the button is first clicked

        break;

    case Widget::TouchEventType::MOVED:
        // code to handle when the user is moving their finger/cursor whilst clicking the button
        break;

    case Widget::TouchEventType::ENDED:
        tempString = UI::Music::offButtonString;

        UI::Music::offButtonString = UI::Music::onButtonString;
        UI::Music::onButtonString = tempString;

        tempString = UI::Music::offButtonPressedString;

        UI::Music::offButtonPressedString = UI::Music::onButtonPressedString;
        UI::Music::onButtonPressedString = tempString;

        node->loadTextures( UI::Music::onButtonString, UI::Music::onButtonPressedString );

        float volume;

        if ( CocosDenshion::SimpleAudioEngine::getInstance( )->getBackgroundMusicVolume( ) > 0.0f )
        {
            volume = 0.0f;
        }
        else
        {
            volume = 1.0f;
        }

        CocosDenshion::SimpleAudioEngine::getInstance( )->setBackgroundMusicVolume( volume );

        break;

    case Widget::TouchEventType::CANCELED:
        // code to handle when the button click has been cancelled,  this is usually handled the same way as ENDED in most applications (e.g. another application takes control of the device)


        break;

    default:
        break;
    }
}

// NOT WORKING ATM
/*
void Everyplay::setup( )
{
#if SCH_IS_EVERYPLAY_ENABLED == true && CC_TARGET_PLATFORM == CC_PLATFORM_IOS
    IOSCPPHelper::setupEveryplay( );
#endif
}
 
void Everyplay::showEveryplay( )
{
#if SCH_IS_EVERYPLAY_ENABLED == true && CC_TARGET_PLATFORM == CC_PLATFORM_IOS
    IOSCPPHelper::showEveryplay( );
#endif
}
 
void Everyplay::record( )
{
#if SCH_IS_EVERYPLAY_ENABLED == true && CC_TARGET_PLATFORM == CC_PLATFORM_IOS
    IOSCPPHelper::recordEveryplayVideo( );
#endif
}
 
void Everyplay::playLastVideoRecording( )
{
#if SCH_IS_EVERYPLAY_ENABLED == true && CC_TARGET_PLATFORM == CC_PLATFORM_IOS
    IOSCPPHelper::playLastEveryplayVideoRecording( );
#endif
}
*/




void WeChat::shareTextToWeChat( cocos2d::__String shareString )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    #if SCH_IS_WECHAT_ENABLED == true
    IOSCPPHelper::shareTextToWeChat( shareString );
#endif
#endif
}

void WeChat::shareImageToWeChat( cocos2d::__String thumbImgPath, cocos2d::__String imgPath )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    #if SCH_IS_WECHAT_ENABLED == true
    IOSCPPHelper::shareImageToWeChat( thumbImgPath, imgPath );
#endif
#endif
}

void WeChat::shareLinkToWeChat( cocos2d::__String thumbImgPath, cocos2d::__String msgTitle, cocos2d::__String msgDescription, cocos2d::__String httpUrl )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    #if SCH_IS_WECHAT_ENABLED == true
    IOSCPPHelper::shareLinkToWeChat( thumbImgPath, msgTitle, msgDescription, httpUrl );
#endif
#endif
}

void WeChat::shareMusicToWeChat( cocos2d::__String msgTitle, cocos2d::__String msgDescription, cocos2d::__String thumbImgPath, cocos2d::__String musicUrl, cocos2d::__String musicDataURL )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    #if SCH_IS_WECHAT_ENABLED == true
    IOSCPPHelper::shareMusicToWeChat( msgTitle, msgDescription, thumbImgPath, musicUrl, musicDataURL );
#endif
#endif
}

void WeChat::shareVideoToWeChat( cocos2d::__String msgTitle, cocos2d::__String msgDescription, cocos2d::__String thumbImgPath, cocos2d::__String videoUrl )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    #if SCH_IS_WECHAT_ENABLED == true
    IOSCPPHelper::shareVideoToWeChat( msgTitle, msgDescription, thumbImgPath, videoUrl );
#endif
#endif
}

void Notifications::scheduleLocalNotification( float delay, cocos2d::__String textToDisplay, cocos2d::__String notificationTitle , int notificationTag )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    #if SCH_IS_NOTIFICATIONS_ENABLED == true
    IOSCPPHelper::scheduleLocalNotification( delay, textToDisplay, notificationTitle ,notificationTag);
#endif
#endif
}

void Notifications::scheduleLocalNotification( float delay, cocos2d::__String textToDisplay, cocos2d::__String notificationTitle, cocos2d::__String notificationAction , int notificationTag )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    #if SCH_IS_NOTIFICATIONS_ENABLED == true
    IOSCPPHelper::scheduleLocalNotification( delay, textToDisplay, notificationTitle, notificationAction ,notificationTag);
#endif
#endif
}

void Notifications::scheduleLocalNotification( float delay, cocos2d::__String textToDisplay, cocos2d::__String notificationTitle, int repeatInterval , int notificationTag )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    #if SCH_IS_NOTIFICATIONS_ENABLED == true
    IOSCPPHelper::scheduleLocalNotification( delay, textToDisplay, notificationTitle, repeatInterval ,notificationTag);
#endif
#endif
}

void Notifications::scheduleLocalNotification( float delay, cocos2d::__String textToDisplay, cocos2d::__String notificationTitle, cocos2d::__String notificationAction, int repeatInterval , int notificationTag )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    #if SCH_IS_NOTIFICATIONS_ENABLED == true
    IOSCPPHelper::scheduleLocalNotification( delay, textToDisplay, notificationTitle, notificationAction, repeatInterval , notificationTag );
#endif
#endif
}

void Notifications::unscheduleAllLocalNotifications( )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    #if SCH_IS_NOTIFICATIONS_ENABLED == true
    IOSCPPHelper::unscheduleAllLocalNotifications( );
#endif
#endif
}

void Notifications::unscheduleLocalNotification(  int notificationTag  )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    #if SCH_IS_NOTIFICATIONS_ENABLED == true
    IOSCPPHelper::unscheduleLocalNotification( notificationTag );
#endif
#endif
}
void AmazonGameCircle::showLeaderboard(const char* leaderboardID)
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    JniHelpers::jniCommonVoidCall(
        "showLeaderboardAmazon",
        CLASS_NAME,
        leaderboardID);
#endif
}
void AmazonGameCircle::submitScore(const char* leaderboardID, long score)
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    JniHelpers::jniCommonVoidCall(
        "submitScoreAmazon",
        CLASS_NAME,
        leaderboardID,
        score);
    cocos2d::log("submit score long");
#endif
}
void AmazonGameCircle::showLeaderboards()
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    JniHelpers::jniCommonVoidCall(
        "showLeaderboardsAmazon",
        CLASS_NAME);
#endif
}
void AmazonGameCircle::showAchievements()
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    JniHelpers::jniCommonVoidCall(
        "showAchievementsAmazon",
        CLASS_NAME);
#endif
}
void AmazonGameCircle::unlockAchievement(const char* achievementID)
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    JniHelpers::jniCommonVoidCall(
        "unlockAchievementAmazon",
        CLASS_NAME,
        achievementID);
#endif
}
void FlurryAnalytics::sendLogEvent(cocos2d::__String eventId)
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    JniHelpers::jniCommonVoidCall("SendLogEvent",CLASS_NAME,eventId.getCString());
#endif
}
void FlurryAnalytics::sendLogEvent(cocos2d::__String eventId, bool timed)
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    JniHelpers::jniCommonIntCall("SendLogEvent",CLASS_NAME,eventId.getCString(),timed);
#endif
}
void FlurryAnalytics::endLogEvent(cocos2d::__String eventId)
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    JniHelpers::jniCommonVoidCall("EndTimeLogEvent",CLASS_NAME,eventId.getCString());
#endif
}
