package sonar.systems.frameworks.AmazonAds;

import com.amazon.device.ads.Ad;
import com.amazon.device.ads.AdError;
import com.amazon.device.ads.AdLayout;
import com.amazon.device.ads.AdProperties;
import com.amazon.device.ads.AdRegistration;
import com.amazon.device.ads.AdSize;
import com.amazon.device.ads.DefaultAdListener;
import com.amazon.device.ads.InterstitialAd;
import com.flurry.sdk.ai.c;

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
import android.widget.Toast;
import sonar.systems.frameworks.BaseClass.Framework;

public class AmazonAds extends Framework
{

    private Activity activity;
    private static String APP_KEY = ""; // Sample Application Key. Replace this value with your Application Key.
    private static final String LOG_TAG = "Amazon Ads Cocos Helper Plugin"; // Tag used to prefix all log messages.

    private ViewGroup adViewContainer; // View group to which the ad view will
                                       // be added.
    private AdLayout currentAdView; // The ad that is currently visible to the
                                    // user.
    private AdLayout nextAdView; // A placeholder for the next ad so we can keep
                                 // the current ad visible while the next ad
                                 // loads.
    private InterstitialAd interstitialAd;

    public void SetActivity(Activity activity)
    {
        this.activity = activity;
        APP_KEY = activity.getResources().getString(activity.getResources().getIdentifier("amazon_ads_key","string",activity.getPackageName()));
    }

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        // For debugging purposes enable logging, but disable for production
        // builds.
        AdRegistration.enableLogging(true);
        // For debugging purposes flag all ad requests as tests, but set to
        // false for production builds.
        AdRegistration.enableTesting(true);

        try
        {
            AdRegistration.setAppKey(APP_KEY);
        }
        catch (final IllegalArgumentException e)
        {
            Log.e(LOG_TAG, "IllegalArgumentException thrown: " + e.toString());
            return;
        }
        this.adViewContainer = (FrameLayout) activity.findViewById(android.R.id.content);
        this.interstitialAd = new InterstitialAd(activity);
        this.interstitialAd.setListener(new InterstitialAdListener());
        loadAd();
    }
    
    @Override
    public void HideBannerAd()
    {
        activity.runOnUiThread(new Runnable()
        {
            
            @Override
            public void run()
            {
                if(currentAdView != null)
                {
                    if(currentAdView.getVisibility() == View.VISIBLE)
                    {
                        final Animation slideDown = AnimationUtils.loadAnimation(activity,com.hammergames.hammermole.R.anim.slide_down);
                        currentAdView.startAnimation(slideDown);
                        currentAdView.setVisibility(View.INVISIBLE);    
                    }
                }
                else if(currentAdView == null)
                {
                    Log.w(LOG_TAG, "Something wrong please: if Server Message is: DISABLED_APP maybe it's the tax information");
                    //Toast.makeText(activity, "Hello I failed :(", 2).show();
                }
                
            }
        });
    }
    @Override
    public void ShowBannerAd()
    {
        super.ShowBannerAd();
        
        //showCurrentAd();
        activity.runOnUiThread(new Runnable()
        {
            public void run()
            {
                if(currentAdView != null)
                {
                    if(currentAdView.getVisibility() == View.INVISIBLE)
                    {
                        currentAdView.setVisibility(View.VISIBLE);
                        final Animation slideUp = AnimationUtils.loadAnimation(activity,com.hammergames.hammermole.R.anim.slide_up);
                        currentAdView.startAnimation(slideUp);
                    }    
                }
                else if(currentAdView == null)
                {
                    Log.w(LOG_TAG, "Something wrong please: if Server Message is: DISABLED_APP maybe it's the tax information");
                }
                
            }
        });
        
    }
    
    @Override
    public void ShowFullscreenAd()
    {
        // TODO Auto-generated method stub
        super.ShowFullscreenAd();
        if(!this.interstitialAd.showAd())
        {
            Log.w(LOG_TAG, "The interstitial ad was not show. Check logcar for more information");
        }
    }
    /**
     * Clean up all ad view resources when destroying the activity.
     */
    @Override
    public void onDestroy() 
    {
        super.onDestroy();
        if (this.currentAdView != null)
            this.currentAdView.destroy();
        if (this.nextAdView != null)
            this.nextAdView.destroy();
    }

    /**
     * Loads a new ad. Keeps the current ad visible while the next ad loads.
     */
    private void loadAd()
    {
        if (this.nextAdView == null)
        { // Create and configure a new ad if the next ad doesn't currently
          // exist.
            this.nextAdView = new AdLayout(activity,AdSize.SIZE_AUTO_NO_SCALE);
            final android.widget.FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);

            // Note: The above implementation is for an auto-sized ad in an
            // AdLayout of width MATCH_PARENT and
            // height WRAP_CONTENT. If you instead want to give the ad a fixed
            // size, you will need to factor in
            // the phone's scale when setting up the AdLayout dimensions. See
            // the example below for 320x50 dpi:
            //
            // this.nextAdView = new AdLayout(this, AdSize.SIZE_320x50);
            // final float scale =
            // this.getApplicationContext().getResources().getDisplayMetrics().density;
            // final LayoutParams layoutParams = new
            // FrameLayout.LayoutParams((int) (320 * scale),
            // (int) (50 * scale), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);

            this.nextAdView.setLayoutParams(layoutParams);
            // Register our ad handler that will receive callbacks for state
            // changes during the ad lifecycle.
            this.nextAdView.setListener(new SampleAdListener());
        }

        // Load the ad with default ad targeting.
        this.nextAdView.loadAd();

        // Note: You can choose to provide additional targeting information to
        // modify how your ads
        // are targeted to your users. This is done via an AdTargetingOptions
        // parameter that's passed
        // to the loadAd call. See an example below:
        //
        // final AdTargetingOptions adOptions = new AdTargetingOptions();
        // adOptions.enableGeoLocation(true);
        // this.nextAdView.loadAd(adOptions);
        if(this.interstitialAd.loadAd())
        {
            
        }
        else
        {
            Log.w(LOG_TAG, "The ad could not be loaded. Check the logcat for more information.");
        }
    }

    /**
     * Hides the ad that is in the current ad view, and then displays the ad
     * that is in the next ad view.
     */
    private void swapCurrentAd()
    {
        final Animation slideDown = AnimationUtils.loadAnimation(activity,com.hammergames.hammermole.R.anim.slide_down);
        slideDown.setAnimationListener(new AnimationListener()
        {
            public void onAnimationEnd(final Animation animation)
            {
                showNextAd();
            }

            public void onAnimationRepeat(final Animation animation)
            {
            }

            public void onAnimationStart(final Animation animation)
            {
            }
        });
        this.currentAdView.startAnimation(slideDown);
    }

    /**
     * Shows the ad that is in the current ad view to the user.
     */
    private void showCurrentAd()
    {
        this.adViewContainer.addView(this.currentAdView);
        final Animation slideUp = AnimationUtils.loadAnimation(activity,com.hammergames.hammermole.R.anim.slide_up);
        this.currentAdView.startAnimation(slideUp);    
    }

    /**
     * Shows the ad that is in the next ad view to the user.
     */
    private void showNextAd()
    {
        this.adViewContainer.removeView(this.currentAdView);
        final AdLayout tmp = this.currentAdView;
        this.currentAdView = this.nextAdView;
        this.nextAdView = tmp;
        showCurrentAd();
    }

    /**
     * This class is for an event listener that tracks ad lifecycle events. It
     * extends DefaultAdListener, so you can override only the methods that you
     * need.
     */
    class SampleAdListener extends DefaultAdListener
    {
        /**
         * This event is called once an ad loads successfully.
         */
        @Override
        public void onAdLoaded(final Ad ad, final AdProperties adProperties)
        {
            Log.i(LOG_TAG, adProperties.getAdType().toString()
                    + " ad loaded successfully.");
            // If there is an ad currently being displayed, swap the ad that
            // just loaded with current ad.
            // Otherwise simply display the ad that just loaded.
            if (currentAdView != null)
            {
                swapCurrentAd();
            }
            else
            {
                // This is the first time we're loading an ad, so set the
                // current ad view to the ad we just loaded and set the next to
                // null
                // so that we can load a new ad in the background.
                currentAdView = nextAdView;
                nextAdView = null;
                showCurrentAd();
            }
        }

        /**
         * This event is called if an ad fails to load.
         */
        @Override
        public void onAdFailedToLoad(final Ad ad, final AdError error)
        {
            Log.w(LOG_TAG, "Ad failed to load. Code: " + error.getCode()
                    + ", Message: " + error.getMessage());
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
         * This event is called after a rich media ad has collapsed from an
         * expanded state.
         */
        @Override
        public void onAdCollapsed(final Ad ad)
        {
            Log.i(LOG_TAG, "Ad collapsed.");
            // Resume your activity here, if it was paused in onAdExpanded.
        }
    }
    /**
     * This class is for an event listener that tracks ad lifecycle events.
     * It extends DefaultAdListener, so you can override only the methods that you need.
     * In this case, there is no need to override methods specific to expandable ads.
     */
    class InterstitialAdListener extends DefaultAdListener {
        /**
         * This event is called once an ad loads successfully.
         */
        @Override
        public void onAdLoaded(final Ad ad, final AdProperties adProperties) {
            Log.i(LOG_TAG, adProperties.getAdType().toString() + " ad loaded successfully.");
    
            // Once an interstitial ad has been loaded, it can then be shown.
            ///AmazonAds.this.showButton.setEnabled(true);
        }
    
        /**
         * This event is called if an ad fails to load.
         */
        @Override
        public void onAdFailedToLoad(final Ad view, final AdError error) {
            Log.w(LOG_TAG, "Ad failed to load. Code: " + error.getCode() + ", Message: " + error.getMessage());
            
            // A new load action may be attempted once the previous one has returned a failure callback.
            //InterstitialAdActivity.this.loadButton.setEnabled(true);
        }
        
        /**
         * This event is called when an interstitial ad has been dismissed by the user.
         */
        @Override
        public void onAdDismissed(final Ad ad) {
            Log.i(LOG_TAG, "Ad has been dismissed by the user.");
            
            // Once the shown ad is dismissed, its lifecycle is complete and a new ad can be loaded.
            //InterstitialAdActivity.this.loadButton.setEnabled(true);
            AmazonAds.this.loadAd();
        }
    }
}
