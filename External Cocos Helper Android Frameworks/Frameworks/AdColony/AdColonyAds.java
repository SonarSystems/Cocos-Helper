package sonar.systems.frameworks.AdColony;


import sonar.systems.frameworks.BaseClass.Framework;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.jirbo.adcolony.*;


public class AdColonyAds extends Framework implements AdColonyAdListener, AdColonyV4VCListener
{
	private Activity activity;
	private String app_id = "";
	private String zone_id = "";

    public static native void rewardedVideoWasViewedAdcolony(boolean result);
    
	public AdColonyAds()
	{
		
	}
	
	
	@Override
	public void SetActivity(Activity activity)
	{
		this.activity = activity;
		app_id = activity.getResources().getString(activity.getResources().getIdentifier("adcolony_app_id","string",activity.getPackageName()));
		zone_id = activity.getResources().getString(activity.getResources().getIdentifier("adcolony_zone_id","string",activity.getPackageName()));
	}
	
	
	@Override
	public void onCreate(Bundle b) 
	{
		AdColony.configure(activity, "version:0.0,store:google", app_id, zone_id);

        AdColony.addV4VCListener( this );
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
        AdColony.pause();
	}
	
	@Override
	public void onActivityResult(int request, int response, Intent data)
	{

	}
	
	@Override
	public void onResume()
	{
        AdColony.resume( activity );
	}
	
	@Override
	public void onDestroy() 
	{
	}
	
    
    @Override
    public void ShowVideoAd()
    {
        AdColonyV4VCAd ad = new AdColonyV4VCAd(zone_id);
        ad.show();
    }
    
    // Reward Callback
    public void onAdColonyV4VCReward( AdColonyV4VCReward reward )
    {
        rewardedVideoWasViewedAdcolony(reward.success());
    }
    
    // Ad Started Callback - called only when an ad successfully starts playing
    public void onAdColonyAdStarted( AdColonyAd ad )
    {
        Log.d("AdColony", "onAdColonyAdStarted");
    }
    
    //Ad Attempt Finished Callback - called at the end of any ad attempt - successful or not.
    public void onAdColonyAdAttemptFinished( AdColonyAd ad )
    {
        // You can ping the AdColonyAd object here for more information:
        // ad.shown() - returns true if the ad was successfully shown.
        // ad.notShown() - returns true if the ad was not shown at all (i.e. if onAdColonyAdStarted was never triggered)
        // ad.skipped() - returns true if the ad was skipped due to an interval play setting
        // ad.canceled() - returns true if the ad was cancelled (either programmatically or by the user)
        // ad.noFill() - returns true if the ad was not shown due to no ad fill.
        
        Log.d("AdColony", "onAdColonyAdAttemptFinished");
    }
    
}
