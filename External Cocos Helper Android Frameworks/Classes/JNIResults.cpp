//
//  JNIResults.cpp
//  RescueGiant
//
//  Created by AppleProgrammer on 03.06.15.
//
//

#include "JNIResults.h"

using namespace SonarCocosHelper;

#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
JNIEXPORT void JNICALL Java_sonar_systems_frameworks_Vungle_VungleAds_rewardedVideoWasViewedVungle(JNIEnv* env, jobject thiz, jboolean result)
{
    //CPP code here
	CCLOG("VideoAdFinished1");

}

JNIEXPORT void JNICALL Java_sonar_systems_frameworks_AdColony_AdColonyAds_rewardedVideoWasViewedAdcolony(JNIEnv* env, jobject thiz, jboolean result)
{
    //CPP code here
	CCLOG("VideoAdFinished1");
}

JNIEXPORT void JNICALL Java_sonar_systems_frameworks_AdMob_AdMobAds_FullscreenAdPreloaded(JNIEnv* env, jobject thiz, jboolean result)
{
    //CPP code here
	AdMob::showPreLoadedFullscreenAd();
}

JNIEXPORT void JNICALL Java_sonar_systems_frameworks_Chartboost_ChartBoostAds_rewardVideowasViewedChartboost(JNIEnv* env, jobject thiz, jboolean result)
{
    //CPP code here
}

JNIEXPORT void JNICALL Java_sonar_systems_frameworks_Chartboost_ChartBoostAds_FullscreenAdPreloaded(JNIEnv* env, jobject thiz, jboolean result)
{
    //CPP code here
	Chartboost::showFullscreenAd();
}
#endif
