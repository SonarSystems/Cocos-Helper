package sonar.systems.frameworks.RevMob;


import com.revmob.RevMob;
import com.revmob.RevMobAdsListener;
import com.revmob.ads.banner.RevMobBanner;
import com.revmob.ads.interstitial.RevMobFullscreen;
import com.revmob.ads.popup.RevMobPopup;
import com.revmob.internal.RMLog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.RelativeLayout;
import sonar.systems.frameworks.BaseClass.Framework;


public class RevMobAds extends Framework
{
	private Activity activity;
	RevMob revmob;
	
	private static RevMobFullscreen fullscreen;
	private static RevMobPopup popup;
	private static RelativeLayout bannerLayout;
	private static RelativeLayout.LayoutParams bannerParams;
	private static FrameLayout appLayout = null;
	private static LayoutParams bannerParam = null;
	
	private boolean showBanner = false;

	private static RevMobBanner bannerAd;
	
	public RevMobAds()
	{
		
	}
	
	@Override
	public void SetActivity(Activity activity)
	{
	    this.activity = activity;
	    bannerParam = new FrameLayout.LayoutParams(   
	            FrameLayout.LayoutParams.WRAP_CONTENT,
	            FrameLayout.LayoutParams.WRAP_CONTENT
	            );
	    bannerParam.gravity = Gravity.BOTTOM | Gravity.CENTER;
	    //get current layout
	    appLayout = (FrameLayout) activity.findViewById(android.R.id.content);
	    bannerLayout = new RelativeLayout(activity);
	    bannerLayout.setLayoutParams(bannerParam);
	    
	    // set default alignment
	    appLayout.addView(bannerLayout);
	}
	
	
	@Override
	public void onCreate(Bundle b) 
	{
	    //RevMob.start(activity);
	    revmob = RevMob.startWithListener(this.activity, revmobListener);
	}
	
	@Override
	public void onStart() 
	{

	}

	@Override
	public void onStop() 
	{

	}

	@Override
	public void onActivityResult(int request, int response, Intent data)
	{

	}
	
	@Override
	public void onResume()
	{
	    //RevMob revmob = RevMob.session();
	    //fullscreen = revmob.createFullscreen(activity, null);
	    //popup = revmob.createPopup(activity, null);
	}
	
	@Override
	public void ShowFullscreenAd()
	{
		if (fullscreen != null && showBanner == false) 
		{
		      fullscreen.show();
		}
		else if(showBanner == false)
		{

			RevMob revmob = RevMob.session();
			revmob.showFullscreen(activity);
		}
		showBanner = true;
	}
	
	@Override
	public void ShowBannerAd()
	{
	    bannerAd = revmob.createBanner(activity,revmobListener);
	    activity.runOnUiThread(new Runnable()
            {
                public void run()
                {
                  ViewGroup view = bannerLayout;
                  view.addView(bannerAd);
                  bannerLayout.setVisibility(View.VISIBLE);
                }
            });
	}

	@Override
	public void HideBannerAd()
	{
	    //RevMob revmob = RevMob.session();
	    //revmob.hideBanner(activity);
	    activity.runOnUiThread(new Runnable()
            {
                
                @Override
                public void run()
                {
                    // TODO Auto-generated method stub
                    System.out.println();
                    bannerLayout.setVisibility(View.INVISIBLE);
                    bannerAd.hide();
                }
            });
	}
	
	@Override
	public void ShowPopUpAd()
	{
		/*if (popup != null) 
		{
		      popup.show();
		}
		else
		{
			RevMob revmob = RevMob.session();
			revmob.showPopup(activity);
		}*/
		RevMobPopup ad = createPopup(activity, null, null);
		ad.show();
	}
	@Override
	public void onRestart() 
	{
		
	}
	
	 public RevMobPopup createPopup(Activity activity, RevMobAdsListener listener)
	 {
		 return createPopup(activity, null, listener);
	 }
	 public RevMobBanner createBanner(Activity activity)
	  {
	    return createBanner(activity, null, null);
	  }
	 
	 public RevMobPopup createPopup(Activity activity, String placementId, RevMobAdsListener listener)
	 {
		 validateActivity(activity);
		 RevMobPopup ad = new RevMobPopup(activity, listener);
		 ad.load(placementId);
		 return ad;
	 }
	 public RevMobBanner createBanner(Activity activity, String placementId, RevMobAdsListener listener)
	 {
		validateActivity(activity);
	    RevMobBanner ad = new RevMobBanner(activity, listener);
	    ad.setChangeBannerParams(false);
	    ad.load(placementId);
	    return ad;
	  }
	 
	 private static void validateActivity(Activity activity)
	 {
		 if (activity == null) {
			 throw new RuntimeException("RevMob: Activity must not be a null value.");
		 }
	 }
	 
	 // Auxiliar methods
	 void runOnAnotherThread(Runnable action)
	 {
	     new Thread(action).start();
	 }
	 RevMobAdsListener revmobListener = new RevMobAdsListener() {
             
             
             /**
              * Start Session Listeners
              */
             @Override
             public void onRevMobSessionIsStarted() {
                     //toastOnUiThread("RevMob session is started.");
             }
 
             @Override
             public void onRevMobSessionNotStarted(String message) {
                     //toastOnUiThread("RevMob session failed to start.");
             }
 
             
             /**
              * Ad Listeners
              */
             @Override
             public void onRevMobAdReceived() {
                     //toastOnUiThread("RevMob ad received.");
             }

             @Override
             public void onRevMobAdNotReceived(String message) {
                     //toastOnUiThread("RevMob ad not received.");
             }

             @Override
             public void onRevMobAdDismissed() {
                     //toastOnUiThread("Ad dismissed.");
             }

             @Override
             public void onRevMobAdClicked() {
                     //toastOnUiThread("Ad clicked.");
             }

             @Override
             public void onRevMobAdDisplayed() {
                     //toastOnUiThread("Ad displayed.");
             }
             
             
             /**
              * Video Listeners
              */
             @Override
             public void onRevMobVideoLoaded(){
                     //toastOnUiThread("RevMob video loaded.");
             }               

             @Override
             public void onRevMobVideoNotCompletelyLoaded() {
                     //toastOnUiThread("RevMob video has not been completely loaded.");
             }
             
             @Override
             public void onRevMobVideoStarted() {
                     //toastOnUiThread("RevMob video started.");
             }
             
             @Override
             public void onRevMobVideoFinished(){
                     //toastOnUiThread("RevMob video finished playing");
             }
             
             
             /**
              * Rewarded Video Listeners
              */
             @Override
             public void onRevMobRewardedVideoLoaded() {
                      //toastOnUiThread("RevMob Rewarded Video loaded.");
             }
             
             @Override
             public void onRevMobRewardedVideoNotCompletelyLoaded() {
                     // toastOnUiThread("RevMob Rewarded Video not completely loaded.");
             }
             
             @Override
             public void onRevMobRewardedPreRollDisplayed() {
                    // toastOnUiThread("RevMob Rewarded Video Pre-Roll displayed");
             }
             
             @Override
             public void onRevMobRewardedVideoStarted() {
                     // toastOnUiThread("RevMob Rewarded Video started playing.");
             }
             
             @Override
             public void onRevMobRewardedVideoFinished() {
                      //toastOnUiThread("RevMob Rewarded Video finished playing.");
             }
             
             @Override
             public void onRevMobRewardedVideoCompleted() {
                    // toastOnUiThread("RevMob Rewarded Video completed. You earned a coin!");
             }

             
             
             
             /**
              * EULA Listeners
              */
             @Override
             public void onRevMobEulaIsShown() {
                     RMLog.i("[RevMob Sample App] Eula is shown.");  
             }

             @Override
             public void onRevMobEulaWasAcceptedAndDismissed() {
                     RMLog.i("[RevMob Sample App] Eula was accepeted and dismissed.");
             }

             @Override
             public void onRevMobEulaWasRejected() {
                     RMLog.i("[RevMob Sample App] Eula was rejected.");
             }
     };
}
