package sonar.systems.frameworks.AdMob;


import sonar.systems.frameworks.BaseClass.Framework;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


public class AdMobAds extends Framework
{
	private Activity activity;
	private String banner_id = "";
	private String interstitial_id = "";
	
	private static AdView adView;
	private InterstitialAd interstitial;

	public AdMobAds()
	{
		
	}
	
	
	@Override
	public void SetActivity(Activity activity)
	{
		this.activity = activity;
		banner_id = activity.getResources().getString(activity.getResources().getIdentifier("admob_banner_id","string",activity.getPackageName()));
		interstitial_id = activity.getResources().getString(activity.getResources().getIdentifier("admob_interstitial_id","string",activity.getPackageName()));
	}
	
	
	@Override
	public void onCreate(Bundle b) 
	{
		adView = new AdView(activity);
		adView.setAdSize(AdSize.BANNER);
		adView.setAdUnitId(banner_id);


		AdRequest adRequest = new AdRequest.Builder()
		.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
		.addTestDevice("HASH_DEVICE_ID")
		.addTestDevice("9137563ECD48269F3D3867F6B4E5863C")
		.build();
		
		FrameLayout.LayoutParams adParams =new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, android.view.Gravity.TOP|android.view.Gravity.CENTER_HORIZONTAL);

		
		adView.loadAd(adRequest);
		adView.setBackgroundColor(Color.BLACK);
		adView.setBackgroundColor(0);
		activity.addContentView(adView,adParams);
		
		//preload intersertial
		// Create the interstitial.
	    interstitial = new InterstitialAd(activity);
	    interstitial.setAdUnitId(interstitial_id);

	    interstitial.setAdListener(new AdListener()
	    {
	          public void onAdLoaded()
	          {
	        	  LoadFullscreenAd();
	          }
	    });
	    HideBannerAd();
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
		if (adView != null) 
		{
			adView.pause();
		}
	}
	
	@Override
	public void onActivityResult(int request, int response, Intent data)
	{

	}
	
	@Override
	public void onResume()
	{
		if (adView != null) 
		{
			adView.resume();
		}
	}
	
	@Override
	public void onDestroy() 
	{
		 adView.destroy();
	}
	
	@Override
	public void ShowBannerAd()
	{
		if (adView != null) 
		{
		 activity.runOnUiThread(new Runnable()
	     {

		     @Override
		     public void run()
		     {  
		    	 	if (!adView.isEnabled())
		    	 			adView.setEnabled(true);
		    	 	 if (adView.getVisibility() == 4 )
		    	 		 	adView.setVisibility(View.VISIBLE); 
		     }
	     });
		}

	  
	}
	
	@Override
	public void ShowBannerAd(final int position)
	{

		if (adView != null) 
		{
		 activity.runOnUiThread(new Runnable()
	     {

		     @Override
		     public void run()
		     {  
		    	FrameLayout.LayoutParams adParams = null;
				if(position == 0) // bottom
					adParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, android.view.Gravity.BOTTOM|android.view.Gravity.CENTER_HORIZONTAL);
				else
					adParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, android.view.Gravity.TOP|android.view.Gravity.CENTER_HORIZONTAL);
				adView.setLayoutParams(adParams);
					
		    	 	if (!adView.isEnabled())
		    	 			adView.setEnabled(true);
		    	 	 if (adView.getVisibility() == 4 )
		    	 		 	adView.setVisibility(View.VISIBLE); 
		     }
	     });
		}

	  
	}

	@Override
	public void HideBannerAd()
	{
		if (adView != null) 
		{
		 activity.runOnUiThread(new Runnable()
	     {

		     @Override
		     public void run()
		     {
		    	 	if (adView.isEnabled())
		    	 			adView.setEnabled(false);
		    	 	if (adView.getVisibility() != 4 )
		    	 			adView.setVisibility(View.INVISIBLE);
		     }
	     });
		}
	}
	
	void LoadFullscreenAd()
	{
		if(interstitial != null) 
		{
			activity.runOnUiThread(new Runnable()
		     {
	
			     @Override
			     public void run()
			     {
			    	 if(interstitial.isLoaded())
			    		 interstitial.show();
			     }
		     });
		}
	}
	
	@Override
	public void ShowFullscreenAd()
	{   
		
	    
	    if(interstitial != null) 
		{
			activity.runOnUiThread(new Runnable()
		     {
	
			     @Override
			     public void run()
			     {
			    	// Create ad request.
			 	    AdRequest adRequest = new AdRequest.Builder()
			 	    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
			 		.addTestDevice("HASH_DEVICE_ID")
			 		.addTestDevice("9137563ECD48269F3D3867F6B4E5863C").build();

			 	    // Begin loading your interstitial.
			 	    interstitial.loadAd(adRequest);
			     }
		     });
		}
	}
	

}
