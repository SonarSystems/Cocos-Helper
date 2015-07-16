package sonar.systems.frameworks.MoPub;

import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubInterstitial.InterstitialAdListener;
import com.mopub.mobileads.MoPubView;
import com.mopub.mobileads.MoPubView.BannerAdListener;

import sonar.systems.frameworks.BaseClass.Framework;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;


public class MoPubAds extends Framework implements BannerAdListener, InterstitialAdListener
{
	private Activity activity;
	
	private String banner_id = "";
	private String interstitial_id = "";
	
	private MoPubView moPubView;
	private MoPubInterstitial mInterstitial;
	
    FrameLayout.LayoutParams banner_adParams;
	public MoPubAds()
	{
		
	}
	
	@Override
	public void SetActivity(Activity activity)
	{
		this.activity = activity;
		
		banner_id = activity.getResources().getString(activity.getResources().getIdentifier("mopub_banner_id","string",activity.getPackageName()));
		interstitial_id = activity.getResources().getString(activity.getResources().getIdentifier("mopub_fullscreen_id","string",activity.getPackageName()));
		
		banner_adParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, android.view.Gravity.TOP|android.view.Gravity.CENTER_HORIZONTAL);
		
		moPubView = new MoPubView(activity);
		moPubView.setAdUnitId(banner_id);
		moPubView.setBannerAdListener(this);
		
		mInterstitial = new MoPubInterstitial(activity, interstitial_id);
        mInterstitial.setInterstitialAdListener(this);
		activity.addContentView(moPubView,banner_adParams);
		
	}
	
	
	@Override
	public void onCreate(Bundle b) 
	{
		
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

	}
	
	@Override
	public void ShowFullscreenAd()
	{
	   mInterstitial.load();
	}
	
	@Override
	public void ShowBannerAd()
	{
		moPubView.loadAd();
	
	}

	@Override
	public void HideBannerAd()
	{
		if (moPubView != null) 
		{
		 activity.runOnUiThread(new Runnable()
	     {

		     @Override
		     public void run()
		     {
		    	 	if (moPubView.isEnabled())
		    	 		moPubView.setEnabled(false);
		    	 	if (moPubView.getVisibility() != View.INVISIBLE )
		    	 		moPubView.setVisibility(View.INVISIBLE);
		     }
	     });
		}
		//moPubView.setVisibility(visibility)
	}
	
	@Override
	public void onDestroy()
	{
		moPubView.destroy();
		mInterstitial.destroy();
	}

	@Override
	public void onBannerClicked(MoPubView arg0) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBannerCollapsed(MoPubView arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBannerExpanded(MoPubView arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBannerFailed(MoPubView arg0, MoPubErrorCode arg1)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBannerLoaded(MoPubView arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInterstitialClicked(MoPubInterstitial arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInterstitialDismissed(MoPubInterstitial arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInterstitialFailed(MoPubInterstitial arg0, MoPubErrorCode arg1) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInterstitialLoaded(MoPubInterstitial arg0) 
	{
		// TODO Auto-generated method stub
		mInterstitial.show();
	}

	@Override
	public void onInterstitialShown(MoPubInterstitial arg0) 
	{
		// TODO Auto-generated method stub
		
	}
	

}
