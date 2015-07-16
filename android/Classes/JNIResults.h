//
//  JNIResults.h
//  RescueGiant
//
//  Created by AppleProgrammer on 03.06.15.
//
//

#ifndef __RescueGiant__JNIResults__
#define __RescueGiant__JNIResults__

#include <stdio.h>

#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
#include <jni.h>
#include "platform/android/jni/JniHelper.h"
#include <android/log.h>
#include "cocos2d.h"
#include "SonarCocosHelper/SonarFrameworks.h"
#endif

#ifdef __cplusplus
extern "C" {
#endif
#if(CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
    JNIEXPORT void JNICALL Java_sonar_systems_frameworks_Vungle_VungleAds_rewardedVideoWasViewedVungle(JNIEnv* env, jobject thiz, jboolean result);
    
    JNIEXPORT void JNICALL Java_sonar_systems_frameworks_AdColony_AdColonyAds_rewardedVideoWasViewedAdcolony(JNIEnv* env, jobject thiz, jboolean result);

    JNIEXPORT void JNICALL Java_sonar_systems_frameworks_AdMob_AdMobAds_FullscreenAdPreloaded(JNIEnv* env, jobject thiz, jboolean result);

    JNIEXPORT void JNICALL Java_sonar_systems_frameworks_Chartboost_ChartBoostAds_rewardVideowasViewedChartboost(JNIEnv* env, jobject thiz, jboolean result);

    JNIEXPORT void JNICALL Java_sonar_systems_frameworks_Chartboost_ChartBoostAds_FullscreenAdPreloaded(JNIEnv* env, jobject thiz, jboolean result);
#endif
#ifdef __cplusplus
};
#endif
#endif /* defined(__RescueGiant__JNIResults__) */
