package sonar.systems.frameworks.FlurryAds;

import com.flurry.android.FlurryAgent;
import com.flurry.android.ads.FlurryAdBanner;
import com.flurry.android.ads.FlurryAdBannerListener;
import com.flurry.android.ads.FlurryAdErrorType;
import com.flurry.android.ads.FlurryAdInterstitial;
import com.flurry.android.ads.FlurryAdInterstitialListener;
import com.flurry.android.ads.FlurryAdTargeting;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import sonar.systems.frameworks.BaseClass.Framework;

public class FlurryAds extends Framework
{
    private static String mFlurryApiKey;;
    private Activity activity;
    
    private FlurryAdInterstitial mFlurryAdInterstitial = null;
    
    LayoutParams top_adParams;
    FrameLayout.LayoutParams bottom_adParams;
    
    private RelativeLayout mBanner;
    private FlurryAdBanner mFlurryAdBanner = null;
    
    private String mAdSpaceName = "INTERSTITIAL_MEMORY";//This is the intersitial ads
    private String mAdSpaceNameBanner = "BANNER";//This is the banner name
    
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
    
    FlurryAdBannerListener bannerAdListener = new FlurryAdBannerListener() {
        
        @Override
        public void onFetched(FlurryAdBanner adBanner) {
               adBanner.displayAd();
        }

        @Override
        public void onError(FlurryAdBanner adBanner, FlurryAdErrorType adErrorType, int errorCode) {
             adBanner.destroy();
        }
       //..
       //the remainder of the listener callback methods

        @Override
        public void onAppExit(FlurryAdBanner arg0) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void onClicked(FlurryAdBanner arg0) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void onCloseFullscreen(FlurryAdBanner arg0) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void onRendered(FlurryAdBanner arg0) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void onShowFullscreen(FlurryAdBanner arg0) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void onVideoCompleted(FlurryAdBanner arg0) {
            // TODO Auto-generated method stub
            
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
    }
    
    @Override
    public void onCreate(Bundle b)
    {
        // Flurry analytics
        super.onCreate(b);
        bottom_adParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, android.view.Gravity.BOTTOM|android.view.Gravity.CENTER_HORIZONTAL);
        
        FlurryAgent.setLogEnabled(true);
        FlurryAgent.setLogLevel(Log.VERBOSE);
        FlurryAgent.setLogEvents(true);
        FlurryAgent.init(activity, mFlurryApiKey);
        Log.i("[Flurry]", "Flurry SDK initialized");
        
        //Intersitial setup
        mFlurryAdInterstitial = new FlurryAdInterstitial(activity, mAdSpaceName);
        mFlurryAdInterstitial.setListener(mAdInterstitialListener);
        FlurryAdTargeting adTargeting = new FlurryAdTargeting();
        adTargeting.setEnableTestAds(true);
        mFlurryAdInterstitial.setTargeting(adTargeting);
        
        
        //Banner setup
        loadStandardBanner();
        
        Log.i("Cocos2dx Activity", "Flurry on create");
        //ShowBannerAd();
        ShowFullscreenAd();
    }
    private void loadStandardBanner()
    {
        //ViewGroup bannerAdLayout = (ViewGroup) activity.findViewById(R.id.banner_layout);
        
        LinearLayout lin=new LinearLayout(activity);
        lin.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                                              LayoutParams.FILL_PARENT));
        lin.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout lin2=new LinearLayout(activity);
        lin2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                                              LayoutParams.WRAP_CONTENT));

        lin.addView(lin2);      


        LayoutParams linLayoutParam = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT); 
        
        //Banner creation
        mFlurryAdBanner = new FlurryAdBanner(activity, lin,mAdSpaceNameBanner);
        mFlurryAdBanner.setListener(bannerAdListener);
        Log.i(TAG, "Fetching banner ad");
        mFlurryAdBanner.fetchAd();
        
        activity.addContentView(lin2, bottom_adParams);
        
    }
    @Override
    public void onStart()
    {
        // TODO Auto-generated method stub
        super.onStart();
        FlurryAgent.onStartSession(activity,mFlurryApiKey);
        // fetch and prepare ad for this ad space. won’t render one yet
        //mFlurryAdInterstitial.fetchAd();
        mFlurryAdInterstitial.fetchAd();
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
    
    @Override
    public void ShowBannerAd() 
    {
        FlurryAdTargeting adTargeting = new FlurryAdTargeting();
        adTargeting.setEnableTestAds(false);
        super.ShowBannerAd();
        if(mFlurryAdBanner.isReady())
        {
            mFlurryAdBanner.setTargeting(adTargeting);
            mFlurryAdBanner.displayAd();
        }
        else
        {
            mFlurryAdBanner.setTargeting(adTargeting);
            mFlurryAdBanner = new FlurryAdBanner(activity, mBanner, mAdSpaceNameBanner);
            mFlurryAdBanner.setListener(bannerAdListener);
            mFlurryAdBanner.fetchAndDisplayAd();
        }
    }
    
}
