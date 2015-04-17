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

namespace SonarCocosHelper
{
	enum AdBannerPosition{ eBottom, eTop };

    class GooglePlayServices
    {
    public:
        static bool isSignedIn();
        static void signIn();
        static void signOut();
        static void submitScore(const char* leaderboardID, long score);
        static void unlockAchievement(const char* achievementID);
        static void incrementAchievement(const char* achievementID, int numSteps);
        static void showAchievements();
        static void showLeaderboards();
        static void showLeaderboard(const char* leaderboardID);
    };

    class IOS
    {
    public:
        static void Setup( );
        static void Share( cocos2d::__String shareString, cocos2d::__String imagePath );
    };

    class Facebook
    {
    public:
        //static void SignIn(); not needed for NOW
        static void Share(const char* name,const char* link, const char* description, const char* caption, const char* imagePath );
    };

    class Twitter
    {
    public:
        static void Tweet(const char* tweet,const char* title, const char *imagePath);
    };

    class AdMob
    {
    public:
        static void showBannerAd();
        static void showBannerAd(int position);
        static void hideBannerAd();
        static void showFullscreenAd( );

    };

    class iAds
    {
    public:
        static void showiAdBanner( );
        static void showiAdBanner( int position );
        static void hideiAdBanner( );
    };

    class RevMob
    {
    public:
        static void showFullscreenAd();
        static void showPopupAd();
        static void showBannerAd( );
        static void hideBannerAd( );
    };
    
    class Chartboost
    {
    public:
        static void showFullscreenAd();
        static void showVideoAd();
        static void showMoreApps( );
    };

    class GameCenter
    {
    public:
        static void signIn( );
        static void showLeaderboard( );
        static void showAchievements( );
        static void submitScore( int scoreNumber, cocos2d::__String leaderboardID );
        static void unlockAchievement( cocos2d::__String achievementID );
        static void unlockAchievement( cocos2d::__String achievementID, float percent );
        static void resetPlayerAchievements( );
    };
    
    class Everyplay
    {
    public:
        static void setup( );
        static void showEveryplay( );
        static void record( );
        static void playLastVideoRecording( );
    };
}

#endif
