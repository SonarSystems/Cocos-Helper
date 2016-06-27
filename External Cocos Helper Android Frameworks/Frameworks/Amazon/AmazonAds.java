/**
 * Written by: Oscar Leif
 * 
 */
package sonar.systems.frameworks.Amazon;

import com.amazon.device.ads.Ad;
import com.amazon.device.ads.AdError;
import com.amazon.device.ads.AdLayout;
import com.amazon.device.ads.AdProperties;
import com.amazon.device.ads.AdRegistration;
import com.amazon.device.ads.AdSize;
import com.amazon.device.ads.AdTargetingOptions;
import com.amazon.device.ads.DefaultAdListener;
import com.amazon.device.ads.InterstitialAd;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import sonar.systems.frameworks.BaseClass.Framework;

public class AmazonAds extends Framework
{

    private Activity            activity;
    private static String       APP_KEY = "";                               // Sample Application Key. Replace this value with your Application Key.
    private static final String LOG_TAG = "Amazon Ads Cocos Helper Plugin"; // Tag used to prefix all log messages.

    private ViewGroup           adViewContainer;                            // View group to which the ad view will
                                                                            // be added.
    private AdLayout            adView;                                     // The ad that is currently visible to the
                                                                            // user.
    private InterstitialAd      interstitialAd;
    
    private boolean isInitizalided = false;
    
    public static boolean interstitialAdLoaded = false;
    
    private Animation slideUp;
    private Animation slideDown;

    public void SetActivity(Activity activity)
    {
        this.activity = activity;
        APP_KEY = activity.getResources().getString(activity.getResources().getIdentifier("amazon_ads_key", "string", activity.getPackageName()));
        AdLayout adLayout = new AdLayout(activity);
        adLayout.setTimeout(15000); // 20 seconds This is optionally by Default is 10000
        slideUp = AnimationUtils.loadAnimation(activity, activity.getResources().getIdentifier("slide_up", "anim", activity.getPackageName()));
        slideDown = AnimationUtils.loadAnimation(activity, activity.getResources().getIdentifier("slide_down", "anim", activity.getPackageName()));
    }

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        // For debugging purposes enable logging, but disable for production builds.
        // For Production set to false, be careful to set it to false in test or debug.  
        AdRegistration.enableLogging(true);
        // For debugging purposes flag all ad requests as tests, but set to
        // false for production builds.
        AdRegistration.enableTesting(true); 
        try
        {
            AdRegistration.setAppKey(APP_KEY);
            isInitizalided = true;
        }
        catch (final IllegalArgumentException e)
        {
            Log.e(LOG_TAG, "IllegalArgumentException thrown: " + e.toString());
            return;
        }
        this.adViewContainer = (FrameLayout) activity.findViewById(android.R.id.content);
        this.interstitialAd = new InterstitialAd(activity);
        this.interstitialAd.setListener(new InterstitialsAdListener());
    }

    @Override
    public void HideBannerAd()
    {
        if(adView == null)
        {
            return;
        }
        activity.runOnUiThread(new Runnable() 
        {
            @Override
            public void run() 
            {
                final Animation slideDown = AnimationUtils.loadAnimation(activity, activity.getResources().getIdentifier("slide_down", "anim", activity.getPackageName()));
                adView.startAnimation(slideDown);
                adView.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void ShowBannerAd()
    {
        super.ShowBannerAd();
        // showCurrentAd();
        if(!isInitizalided)
        {
            Log.d("Amazon Ads", "Amazon Ads plugin is not initialized yet!");
            return;
        }
        if(adView == null)
        {
            activity.runOnUiThread(new Runnable() {
                
                @Override
                public void run() 
                {
                    
                    adView = new AdLayout(activity, AdSize.SIZE_AUTO);
                    adView.setListener(new CustomAdListener());
                    final android.widget.FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
                    activity.addContentView(adView, layoutParams);
                    AdTargetingOptions adOptions = new AdTargetingOptions();
                    adView.loadAd(adOptions);
                    adView.startAnimation(slideUp);
                    adView.setVisibility(View.VISIBLE);
                }
            });
        }
        else
        {
            refresh();
        }
    }
    
    // Refresh banner for a new ad request
    public void refresh()
    {           
        if ( adView != null )
        {
            Log.d ( "Amazon Ads" , "Refreshing Amazon Ad banner.");
            
            // Run the thread on Unity activity
            activity.runOnUiThread (
              new Runnable() { 
                public void run() 
                {
                    AdTargetingOptions adOptions = new AdTargetingOptions();
                    adView.loadAd(adOptions);
                    if(adView.getVisibility() == View.INVISIBLE)
                    {
                        adView.setVisibility(View.VISIBLE);
                        adView.startAnimation(slideUp);
                        return;
                    }
                }
              });
        }
        else
        {
            Log.d ( "Amazon Ads", "Amazon Ad plugin is not initialized yet!");
        }
    }

    @Override
    public void ShowFullscreenAd()
    {
        super.ShowFullscreenAd();
        activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run() 
            {
                requestInterstital();
                if(interstitialAd != null)
                {
                    if(AmazonAds.interstitialAdLoaded)
                    {
                        interstitialAd.showAd();
                        AmazonAds.interstitialAdLoaded = false;
                    }
                }               
            }
        });
        
    }
    
 // Request interstitials
    public void requestInterstital()
    {
        activity.runOnUiThread( new Runnable()
        { 
            @Override public void run()
            { 
                boolean shouldRequest = true;
            
                if ( interstitialAd != null )
                {
                    if ( interstitialAd.isLoading() )
                    {
                        shouldRequest = false;
                    }
                    
                    if ( AmazonAds.interstitialAdLoaded )
                    {
                        shouldRequest = false;
                    }
                }
                if ( shouldRequest )
                {
                    Log.d ( "Amazon Ads" , "Requesting Amazon Interstitials");
                    interstitialAd = new com.amazon.device.ads.InterstitialAd( activity  );
                    interstitialAd.setListener( new InterstitialsAdListener() );
                    AdTargetingOptions adOptions = new AdTargetingOptions();
                    interstitialAd.loadAd( adOptions );
                }
            } 
          });
        }
    /**
     * Clean up all ad view resources when destroying the activity.
     */
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (this.adView != null)
        {
            this.adView.destroy();
            this.adView = null;
        }
    }

    /**
     * Shows the ad that is in the current ad view to the user.
     * 
     */
    private void showCurrentAd()
    {
        this.adViewContainer.addView(this.adView);
        this.adView.startAnimation(slideUp);
    }
    
    /**
     * This class is for an event listener that tracks ad life cycle events. It extends DefaultAdListener, so you can override 
     * only the methods that you need. This event is called once an ad loads successfully. onAdLoaded.
     */
    class CustomAdListener extends DefaultAdListener
    {
        @Override
        public void onAdLoaded(final Ad ad, final AdProperties adProperties)
        {
            Log.i(LOG_TAG, adProperties.getAdType().toString() + " ad loaded successfully.");
            // If there is an ad currently being displayed, swap the ad that just loaded with current ad.
            // Otherwise simply display the ad that just loaded. 
            if (adView != null)
            {
                
            }
            else
            {
                
            }
        }

        /**
         * This event is called if an ad fails to load.
         */
        @Override
        public void onAdFailedToLoad(final Ad ad, final AdError error)
        {
            Log.w(LOG_TAG, "Ad failed to load. Code: " + error.getCode() + ", Message: " + error.getMessage());
        }

        /**
         * This event is called after a rich media ad expands.
         */
        @Override
        public void onAdExpanded(final Ad ad)
        {
            Log.i(LOG_TAG, "Ad expanded.");
            // You may want to pause your activity here.
        }

        /**
         * This event is called after a rich media ad has collapsed from an expanded state.
         */
        @Override
        public void onAdCollapsed(final Ad ad)
        {
            Log.i(LOG_TAG, "Ad collapsed.");
            // Resume your activity here, if it was paused in onAdExpanded.
        }
    }
    //Interstitials ad listener
    class InterstitialsAdListener extends DefaultAdListener
    {
        /**
         * This event is called once an ad loads successfully.
         */
        @Override
        public void onAdLoaded(final Ad ad, final AdProperties adProperties) 
        {
            AmazonAds.interstitialAdLoaded = true;
            AmazonAds.this.ShowFullscreenAd();
        }
        /**
         * This event is called if an ad fails to load.
         */
        @Override
        public void onAdFailedToLoad(final Ad view, final AdError error) 
        {
            AmazonAds.interstitialAdLoaded = false;
        }
        /**
         * This event is called when an interstitial ad has been dismissed by the user.
         */
        @Override
        public void onAdDismissed(final Ad ad) {}
    }  
 }