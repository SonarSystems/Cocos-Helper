package sonar.systems.frameworks.amazon;

/**
 * Written by: Oscar Leif
 */

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.amazon.device.ads.Ad;
import com.amazon.device.ads.AdError;
import com.amazon.device.ads.AdLayout;
import com.amazon.device.ads.AdProperties;
import com.amazon.device.ads.AdRegistration;
import com.amazon.device.ads.AdSize;
import com.amazon.device.ads.AdTargetingOptions;
import com.amazon.device.ads.DefaultAdListener;
import com.amazon.device.ads.InterstitialAd;

import sonar.systems.frameworks.BaseClass.Framework;

public class AmazonAds extends Framework
{
    private static AmazonAds instance = null;
    private Activity activity;
    private static String APP_KEY = "";                               // Sample Application Key. Replace this value with your Application Key.
    private static final String LOG_TAG = "Amazon Ads Plugin"; // Tag used to prefix all log messages.

    private ViewGroup adViewContainer;                            // View group to which the ad view will
    // be added.
    private AdLayout adView = null;                                     // The ad that is currently visible to the
    // user.
    private InterstitialAd interstitialAd;

    private boolean isInitizalided = false;

    public static boolean interstitialAdLoaded = false;

    public AmazonAds()
    {
        if (instance == null)
        {
            try
            {
                throw new Exception("Object already exist");
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static AmazonAds getInstance() throws Exception
    {
        if (instance == null)
        {
            instance = new AmazonAds();
        }
        return instance;
    }

    public void SetActivity(Activity activity)
    {
        this.activity = activity;
        instance = this;
        //TODO Update this the Key should be passed from C++ it will protect the Key

        InitializeAds();
    }

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
    }

    private void InitializeAds()
    {
        APP_KEY = "42e4f418e79c4ce1960f9de6ef14bbbd";//activity.getResources().getString(activity.getResources().getIdentifier("amazon_ads_key", "string", activity.getPackageName()));
        //APP_KEY = "sample-app-v1_pub-2";
        AdLayout adLayout = new AdLayout(activity);
        //adLayout.setTimeout(15000); // 20 seconds This is optionally by Default is 10000
        AdRegistration.enableLogging(true);
        // For debugging purposes flag all ad requests as tests, but set to
        // false for production builds.
        AdRegistration.enableTesting(true);
        AdRegistration.setAppKey(APP_KEY);
        isInitizalided = true;

        this.adViewContainer = (FrameLayout) activity.findViewById(android.R.id.content);
        //this.interstitialAd = new InterstitialAd(activity);
        //this.interstitialAd.setListener(new InterstitialsAdListener());
    }

    public void createBanner()
    {

    }

    @Override
    public void HideBannerAd()
    {
        if (adView == null)
        {
            return;
        }
        activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                adView.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void ShowBannerAd()
    {

        super.ShowBannerAd();
        // showCurrentAd();
        if (!isInitizalided)
        {
            Log.d(LOG_TAG, "Amazon Ads plugin is not initialized yet!");
            return;
        }
        if (adView == null)
        {
            activity.runOnUiThread(new Runnable()
            {

                @Override
                public void run()
                {

                    adView = new AdLayout(activity, AdSize.SIZE_AUTO_NO_SCALE);
                    adView.setListener(new CustomAdListener());
                    final android.widget.FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
                    activity.addContentView(adView, layoutParams);
                    AdTargetingOptions adOptions = new AdTargetingOptions();
                    adView.loadAd(adOptions);
                    //adView.startAnimation(slideUp);
                    adView.setVisibility(View.VISIBLE);
                }
            });
        } else
        {
            refresh();
        }
    }

    // Refresh banner for a new ad request
    //TODO Check this refresh method
    public void refresh()
    {
        if (adView != null)
        {
            Log.d(LOG_TAG, "Refreshing Amazon Ad banner.");

            // Run the thread on Unity activity
            activity.runOnUiThread(
                    new Runnable()
                    {
                        public void run()
                        {
                            AdTargetingOptions adOptions = new AdTargetingOptions();
                            adView.loadAd(adOptions);
                            if (adView.getVisibility() == View.INVISIBLE)
                            {
                                adView.setVisibility(View.VISIBLE);
                                //adView.startAnimation(slideUp);
                                return;
                            }
                        }
                    });
        } else
        {
            Log.d("Amazon Ads", "Amazon Ad plugin is not initialized yet!");
        }
    }

    //TODO Check compatibility
    @Override
    public void HideBannerAd(int position)
    {
        super.HideBannerAd(position);
        //return if there's no ad view
        if (adView == null)
        {
            return;
        }

        activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                if (adView != null)
                {
                    adView.setVisibility(View.GONE);
                }
            }
        });


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
                if (interstitialAd != null)
                {
                    if (AmazonAds.interstitialAdLoaded)
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
        activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                boolean shouldRequest = true;

                if (interstitialAd != null)
                {
                    if (interstitialAd.isLoading())
                    {
                        shouldRequest = false;
                    }

                    if (AmazonAds.interstitialAdLoaded)
                    {
                        shouldRequest = false;
                    }
                }
                if (shouldRequest)
                {
                    Log.d("Amazon Ads", "Requesting Amazon Interstitials");
                    interstitialAd = new com.amazon.device.ads.InterstitialAd(activity);
                    interstitialAd.setListener(new InterstitialsAdListener());
                    AdTargetingOptions adOptions = new AdTargetingOptions();
                    interstitialAd.loadAd(adOptions);
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
     */
    private void showCurrentAd()
    {
        this.adViewContainer.addView(this.adView);
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

            } else
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

    // Interstitials ad listener
    private class InterstitialsAdListener extends DefaultAdListener
    {
        /**
         * This event is called once an ad loads successfully.
         */
        @Override
        public void onAdLoaded(final Ad ad, final AdProperties adProperties)
        {
            //UnityPlayer.UnitySendMessage(UnityObjName, "OnAdEvent", "amazon-interstitial-yes");
            //AdsPlugin.interstitialAdLoaded = true;
        }

        /**
         * This event is called if an ad fails to load.
         */
        @Override
        public void onAdFailedToLoad(final Ad view, final AdError error)
        {
            //UnityPlayer.UnitySendMessage(UnityObjName, "OnAdEvent", "amazon-interstitial-no");\
            //AdsPlugin.interstitialAdLoaded = false;
        }

        /**
         * This event is called when an interstitial ad has been dismissed by the user.
         */
        @Override
        public void onAdDismissed(final Ad ad)
        {
            try
            {
                AmazonAds.getInstance().requestInterstital();
            }
            catch (Exception e){ }
        }
    }
}
