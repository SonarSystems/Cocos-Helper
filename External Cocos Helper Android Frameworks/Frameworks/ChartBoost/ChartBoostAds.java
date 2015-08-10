package sonar.systems.frameworks.ChartBoost;


import sonar.systems.frameworks.BaseClass.Framework;
import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.chartboost.sdk.*;
import com.chartboost.sdk.Model.CBError.CBClickError;
import com.chartboost.sdk.Model.CBError.CBImpressionError;


public class ChartBoostAds extends Framework
{
	protected static final String TAG = "CHARTBOOST";
	private Activity activity;
	private String id= "";
	private String sig = "";
	
	public static native void rewardVideowasViewedChartboost(boolean result);
	public static native void FullscreenAdPreloaded(boolean result);
	
	public ChartBoostAds()
	{
		
	}
	
	@Override
	public void SetActivity(Activity activity)
	{
		this.activity = activity;
		
		id = activity.getResources().getString(activity.getResources().getIdentifier("chartboost_app_id","string",activity.getPackageName()));
		sig = activity.getResources().getString(activity.getResources().getIdentifier("chartboost_app_signature","string",activity.getPackageName()));
	}
	
	@Override
	public void onCreate(Bundle b) 
	{
		 Chartboost.startWithAppId(activity, id, sig);
		 /* Optional: If you want to program responses to Chartboost events, supply a delegate object here and see step (10) for more information */
		 Chartboost.setDelegate(delegate);
		 Chartboost.onCreate(activity);
		  
		  
		  
	}
	
	private ChartboostDelegate delegate = new ChartboostDelegate() 
	{
		//Override the Chartboost delegate callbacks you wish to track and control
		 @Override
	        public boolean shouldRequestInterstitial(String location) {
	            Log.i(TAG, "SHOULD REQUEST INTERSTITIAL '"+ (location != null ? location : "null"));        
	            return true;
	        }
	    
	        @Override
	        public boolean shouldDisplayInterstitial(String location) {
	            Log.i(TAG, "SHOULD DISPLAY INTERSTITIAL '"+ (location != null ? location : "null"));
	            return true;
	        }
	    
	        @Override
	        public void didCacheInterstitial(String location)
	        {
	            Log.i(TAG, "DID CACHE INTERSTITIAL '"+ (location != null ? location : "null"));
	            FullscreenAdPreloaded(true);
	        }
	        
	        @Override
	        public void didFailToLoadInterstitial(String location, CBImpressionError error) {
	            Log.i(TAG, "DID FAIL TO LOAD INTERSTITIAL '"+ (location != null ? location : "null")+ " Error: " + error.name());
	            Toast.makeText(activity.getApplicationContext(), "INTERSTITIAL '"+location+"' REQUEST FAILED - " + error.name(), Toast.LENGTH_SHORT).show();
	        }
	    
	        @Override
	        public void didDismissInterstitial(String location) {
	            Log.i(TAG, "DID DISMISS INTERSTITIAL: "+ (location != null ? location : "null"));
	        }
	    
	        @Override
	        public void didCloseInterstitial(String location) {
	            Log.i(TAG, "DID CLOSE INTERSTITIAL: "+ (location != null ? location : "null"));
	        }
	    
	        @Override
	        public void didClickInterstitial(String location) {
	            Log.i(TAG, "DID CLICK INTERSTITIAL: "+ (location != null ? location : "null"));
	        }
	    
	        @Override
	        public void didDisplayInterstitial(String location) {
	            Log.i(TAG, "DID DISPLAY INTERSTITIAL: " +  (location != null ? location : "null"));
	        }
	    
	        @Override
	        public boolean shouldRequestMoreApps(String location) {
	            Log.i(TAG, "SHOULD REQUEST MORE APPS: " +  (location != null ? location : "null"));
	            return true;
	        }
	    
	        @Override
	        public boolean shouldDisplayMoreApps(String location) {
	            Log.i(TAG, "SHOULD DISPLAY MORE APPS: " +  (location != null ? location : "null"));
	            return true;
	        }
	    
	        @Override
	        public void didFailToLoadMoreApps(String location, CBImpressionError error) {
	            Log.i(TAG, "DID FAIL TO LOAD MOREAPPS " +  (location != null ? location : "null")+ " Error: "+ error.name());
	            Toast.makeText(activity.getApplicationContext(), "MORE APPS REQUEST FAILED - " + error.name(), Toast.LENGTH_SHORT).show();
	        }
	    
	        @Override
	        public void didCacheMoreApps(String location) {
	            Log.i(TAG, "DID CACHE MORE APPS: " +  (location != null ? location : "null"));
	        }
	    
	        @Override
	        public void didDismissMoreApps(String location) {
	            Log.i(TAG, "DID DISMISS MORE APPS " +  (location != null ? location : "null"));
	        }
	    
	        @Override
	        public void didCloseMoreApps(String location) {
	            Log.i(TAG, "DID CLOSE MORE APPS: "+  (location != null ? location : "null"));
	        }
	    
	        @Override
	        public void didClickMoreApps(String location) {
	            Log.i(TAG, "DID CLICK MORE APPS: "+  (location != null ? location : "null"));
	        }
	    
	        @Override
	        public void didDisplayMoreApps(String location) {
	            Log.i(TAG, "DID DISPLAY MORE APPS: " +  (location != null ? location : "null"));
	        }
	    
	        @Override
	        public void didFailToRecordClick(String uri, CBClickError error) {
	            Log.i(TAG, "DID FAILED TO RECORD CLICK " + (uri != null ? uri : "null") + ", error: " + error.name());
	            Toast.makeText(activity.getApplicationContext(), "FAILED TO RECORD CLICK " + (uri != null ? uri : "null") + ", error: " + error.name(), Toast.LENGTH_SHORT).show();
	        }
	        
	        @Override
	        public void didDismissRewardedVideo(String location) {
	            Log.i(TAG, String.format("DID DISMISS REWARDED VIDEO '%s'",  (location != null ? location : "null")));
	        }
	    
	        @Override
	        public void didCloseRewardedVideo(String location) {
	            Log.i(TAG, String.format("DID CLOSE REWARDED VIDEO '%s'",  (location != null ? location : "null")));
	        }
	    
	        @Override
	        public void didClickRewardedVideo(String location) 
	        {
	            Log.i(TAG, String.format("DID CLICK REWARDED VIDEO '%s'",  (location != null ? location : "null")));
	        }
	    
	        @Override
	        public void didCompleteRewardedVideo(String location, int reward) 
	        {
	            Log.i(TAG, String.format("DID COMPLETE REWARDED VIDEO '%s' FOR REWARD %d",  (location != null ? location : "null"), reward));
	            rewardVideowasViewedChartboost(true);
	        }
	        
	        @Override
	        public void didDisplayRewardedVideo(String location) 
	        {
	            Log.i(TAG, String.format("DID DISPLAY REWARDED VIDEO: '%s'",  (location != null ? location : "null")));
	        }
	    
	};
	
	@Override
	public boolean onBackPressed()
	{
		 if (Chartboost.onBackPressed())
		        return true;
		    else
		        return false;
	}
	
	@Override
	public void onStart() 
	{
		Chartboost.onStart(activity);
	}

	@Override
	public void onStop() 
	{
		 Chartboost.onStop(activity);
	}

	@Override
	public void onPause()
	{
		Chartboost.onPause(activity);
	}
	
	@Override
	public void onActivityResult(int request, int response, Intent data)
	{

	}
	
	@Override
	public void onResume()
	{
		Chartboost.onResume(activity);
		
		
	}
	
	@Override
	public void onDestroy() 
	{
		Chartboost.onDestroy(activity);
		
	}
	
	@Override
	public void ShowFullscreenAd()
	{
		Chartboost.showInterstitial(CBLocation.LOCATION_DEFAULT);
	}
	
	@Override
	public void ShowVideoAd()
	{
		Chartboost.showRewardedVideo(CBLocation.LOCATION_DEFAULT);
	}
	
	@Override
	public void PreLoadFullscreenAd()
	{
		Chartboost.cacheInterstitial(CBLocation.LOCATION_DEFAULT);
	}
	
	
	@Override
	public void PreLoadVideoAd()
	{
		Chartboost.cacheRewardedVideo(CBLocation.LOCATION_DEFAULT);
	}

	

}
