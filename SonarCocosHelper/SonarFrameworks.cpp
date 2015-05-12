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

using namespace cocos2d;
using namespace SonarCocosHelper;

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

		return false;
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

void Mopub::showMopubBannerAd()
{
    #if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    
    #elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
        #if SCH_IS_MOPUB_ENABLED == true
            IOSCPPHelper::showMopubBanner();
        #endif
    #endif
 
}

void Mopub::hideMopubBannerAd()
{
    #if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
        
    #elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
        #if SCH_IS_MOPUB_ENABLED == true
    IOSCPPHelper::hideMopubBanner();
        #endif
    #endif
 
}

void Mopub::requestLaunchFullscreenAd( )
{
    #if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
        
    #elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
        #if SCH_IS_MOPUB_ENABLED == true
            IOSCPPHelper::requestFullscreenAd();
        #endif
    #endif
    
}

void Mopub::showLaunchFullscreenAd( )
{
    #if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
        
    #elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
        #if SCH_IS_MOPUB_ENABLED == true
            IOSCPPHelper::showLaunchFullscreenAd();
        #endif
    #endif
 
}

void Mopub::requestEndlevelFullscreenAd()
{
    #if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
        
    #elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
        #if SCH_IS_MOPUB_ENABLED == true
            IOSCPPHelper::requestEndLevelFullscreenAd();
        #endif
    #endif
 
}

void Mopub::showEndlevelFullscreenAd( )
{
    #if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
        
    #elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
        #if SCH_IS_MOPUB_ENABLED == true
            IOSCPPHelper::showEndLevelFullscreenAd();
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
#elif(CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    #if SCH_IS_REVMOB_ENABLED == true
        IOSCPPHelper::showRevMobBanner( );
    #endif
#endif
}

void RevMob::hideBannerAd( )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
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

void TESTCLASS::voidTestMethod1( )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
#elif CC_TARGET_PLATFORM == CC_PLATFORM_IOS
    IOSCPPHelper::voidTestMethod1( );
#endif
}

void TESTCLASS::voidTestMethod2( )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
#elif CC_TARGET_PLATFORM == CC_PLATFORM_IOS
    IOSCPPHelper::voidTestMethod2( );
#endif
}

bool TESTCLASS::boolTestMethod1( )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
#elif CC_TARGET_PLATFORM == CC_PLATFORM_IOS
    return IOSCPPHelper::boolTestMethod1( );
#endif
}

bool TESTCLASS::boolTestMethod2( )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
#elif CC_TARGET_PLATFORM == CC_PLATFORM_IOS
    return IOSCPPHelper::boolTestMethod2( );
#endif
}

int TESTCLASS::intTestMethod1( )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
#elif CC_TARGET_PLATFORM == CC_PLATFORM_IOS
    return IOSCPPHelper::intTestMethod1( );
#endif
}

int TESTCLASS::intTestMethod2( )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
#elif CC_TARGET_PLATFORM == CC_PLATFORM_IOS
    return IOSCPPHelper::intTestMethod2( );
#endif
}

float TESTCLASS::floatTestMethod1( )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
#elif CC_TARGET_PLATFORM == CC_PLATFORM_IOS
    return IOSCPPHelper::floatTestMethod1( );
#endif
}

float TESTCLASS::floatTestMethod2( )
{
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
#elif CC_TARGET_PLATFORM == CC_PLATFORM_IOS
    return IOSCPPHelper::floatTestMethod2( );
#endif
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
