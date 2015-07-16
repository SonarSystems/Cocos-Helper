package sonar.systems.frameworks.Vungle;


import sonar.systems.frameworks.BaseClass.Framework;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.vungle.publisher.VunglePub;
import com.vungle.publisher.AdConfig;
import com.vungle.publisher.Orientation;
import com.vungle.publisher.EventListener;


public class VungleAds extends Framework
{
	private Activity activity;
	private String app_id = "";
    final VunglePub vunglePub = VunglePub.getInstance();

    public static native void rewardedVideoWasViewedVungle(boolean result);
    
	public VungleAds()
	{
		
	}
	
	
	@Override
	public void SetActivity(Activity activity)
	{
		this.activity = activity;
		app_id = activity.getResources().getString(activity.getResources().getIdentifier("vungle_app_id","string",activity.getPackageName()));
	}
	
	
	@Override
	public void onCreate(Bundle b) 
	{
        // initialize the Publisher SDK
        vunglePub.init(activity, app_id);
        vunglePub.setEventListeners(vungleListener);
        // get a reference to the global AdConfig object
        final AdConfig globalAdConfig = vunglePub.getGlobalAdConfig();
        
        // set any configuration options you like.
        // For a full description of available options, see the
        // 'Configuration Options' section.
        globalAdConfig.setSoundEnabled(true);
        globalAdConfig.setOrientation(Orientation.autoRotate);
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
	public void onPause()
	{
        vunglePub.onPause();
	}
	
	@Override
	public void onActivityResult(int request, int response, Intent data)
	{

	}
	
	@Override
	public void onResume()
	{
        vunglePub.onResume();
	}
	
	@Override
	public void onDestroy() 
	{
	}
	
    
    @Override
    public void ShowVideoAd(boolean condition)
    {
        final AdConfig overrideConfig = new AdConfig();
        
        // set any configuration options you like.
        // For a full description of available options, see the 'Configuration Options' section.
        overrideConfig.setIncentivized(condition);
        vunglePub.playAd(overrideConfig);
    }
	
    private final EventListener vungleListener = new EventListener() 
	{
        @Override
        public void onVideoView(boolean isCompletedView, int watchedMillis, int videoDurationMillis) 
        {
        // Called each time a video completes.  isCompletedView is true if >= 80% of the video was watched.
           rewardedVideoWasViewedVungle(isCompletedView);
        }
        
        @Override
        public void onAdStart() {
        // Called before playing an ad.
        }
        
        @Override
        public void onAdUnavailable(String reason) {
        // Called when VunglePub.playAd() was called but no ad is available to show to the user.
        }
        
        @Override
        public void onAdEnd(boolean wasCallToActionClicked) {
        // Called when the user leaves the ad and control is returned to your application.
        }
        
        @Override
        public void onAdPlayableChanged(boolean isAdPlayable) {
        // Called when ad playability changes.
        }
    };

}
