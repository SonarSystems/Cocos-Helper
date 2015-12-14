package sonar.systems.frameworks.FlurryAds;

import com.flurry.android.FlurryAgent;
import com.flurry.android.ads.FlurryAdErrorType;
import com.flurry.android.ads.FlurryAdInterstitial;
import com.flurry.android.ads.FlurryAdInterstitialListener;
import com.flurry.android.ads.FlurryAdTargeting;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import sonar.systems.frameworks.BaseClass.Framework;

public class FlurryAds extends Framework
{
    private static String mFlurryApiKey;;
    private Activity activity;
    
    private FlurryAdInterstitial mFlurryAdInterstitial = null;
    
    private String mAdSpaceName = "fullscreen_ads";
    private final static String TAG = "Flurry cocos";
    
    FlurryAdInterstitialListener mAdInterstitialListener = new FlurryAdInterstitialListener() {
        @Override
        public void onFetched(FlurryAdInterstitial flurryAdInterstitial) {
            Log.i(TAG, "Full screen ad fetched");
            //mListView.setEnabled(true);
            flurryAdInterstitial.displayAd();
        }

        @Override
        public void onRendered(FlurryAdInterstitial flurryAdInterstitial) {
            Log.i(TAG, "Ad rendered");
        }

        @Override
        public void onDisplay(FlurryAdInterstitial flurryAdInterstitial) {
            Log.i(TAG, "Ad displayed");
        }

        @Override
        public void onClose(FlurryAdInterstitial flurryAdInterstitial) {
            Log.i(TAG, "Ad closed");
        }

        @Override
        public void onAppExit(FlurryAdInterstitial flurryAdInterstitial) {
            Log.i(TAG, "App closing");
        }

        @Override
        public void onClicked(FlurryAdInterstitial flurryAdInterstitial) {
            Log.i(TAG, "Ad clicked");
        }

        @Override
        public void onVideoCompleted(FlurryAdInterstitial flurryAdInterstitial) {
            Log.i(TAG, "Video is completed");
            //Toast.makeText(MainActivity.this, "Video completed, where's my reward",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(FlurryAdInterstitial flurryAdInterstitial, FlurryAdErrorType flurryAdErrorType, int i) {
            Log.e(TAG, "Full screen ad load error - Error type: " + flurryAdErrorType + " Code: " + i);
            //Toast.makeText(MainActivity.this, "Ad load failed - try again", Toast.LENGTH_SHORT).show();
            //mListView.setEnabled(true);
        }
    };
    
    public FlurryAds()
    {
        // TODO Auto-generated constructor stub
    }
    
    public void SetActivity(Activity activity)
    {
        this.activity = activity;
        mFlurryApiKey = activity.getResources().getString(activity.getResources().getIdentifier("my_flurry_apikey","string",activity.getPackageName()));
        System.out.println();
    }
    
    @Override
    public void onCreate(Bundle b)
    {
        // Flurry analytics
        super.onCreate(b);
        
        FlurryAgent.setLogEnabled(false);
        //FlurryAgent.setLogLevel(Log.VERBOSE);
        FlurryAgent.setLogEvents(false);
        
        FlurryAgent.init(activity, mFlurryApiKey);
        
        mFlurryAdInterstitial = new FlurryAdInterstitial(activity, mAdSpaceName);

        mFlurryAdInterstitial.setListener(mAdInterstitialListener);

        FlurryAdTargeting adTargeting = new FlurryAdTargeting();
        adTargeting.setEnableTestAds(false);
        mFlurryAdInterstitial.setTargeting(adTargeting);

        //mFlurryAdInterstitial.fetchAd();
        Log.d("Cocos2dx Activity", "Flurry on create");
    }
    
    @Override
    public void onStart()
    {
        // TODO Auto-generated method stub
        super.onStart();
        FlurryAgent.onStartSession(activity,mFlurryApiKey);
        // fetch and prepare ad for this ad space. won’t render one yet
        //mFlurryAdInterstitial.fetchAd();
    }
    @Override
    public void onStop()
    {
        FlurryAgent.onEndSession(activity);
        //do NOT call mFlurryAdInterstitial.destroy() here.  
        //it will destroy the object prematurely and prevent certain listener callbacks form fireing
        super.onStop();
    }
    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
        mFlurryAdInterstitial.destroy();
    }
    
    @Override
    public void ShowFullscreenAd()
    {
        // TODO Auto-generated method stub
        super.ShowFullscreenAd();
        if(mFlurryAdInterstitial.isReady())
        {
            mFlurryAdInterstitial.displayAd();
        }
        else
        {
            mFlurryAdInterstitial = new FlurryAdInterstitial(activity, mAdSpaceName);
            mFlurryAdInterstitial.fetchAd();
        }        
        Log.d("Flurry", "Flurry ads interstitial show");
    }
    
}
