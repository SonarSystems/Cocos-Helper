package sonar.systems.frameworks.RevMob;


import com.revmob.RevMob;
import com.revmob.ads.interstitial.RevMobFullscreen;
import com.revmob.ads.popup.RevMobPopup;
import sonar.systems.frameworks.BaseClass.Framework;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class RevMobAds extends Framework
{
	private Activity activity;
	
	private static RevMobFullscreen fullscreen;
	private static RevMobPopup popup;
	
	public RevMobAds()
	{
		
	}
	
	@Override
	public void SetActivity(Activity activity)
	{
		this.activity = activity;
	}
	
	
	@Override
	public void onCreate(Bundle b) 
	{
		RevMob.start(activity);
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
		RevMob revmob = RevMob.session();
	    fullscreen = revmob.createFullscreen(activity, null);
	    popup = revmob.createPopup(activity, null);
	}
	
	@Override
	public void ShowFullscreenAd()
	{
		if (fullscreen != null) 
		{
		      fullscreen.show();
		}
		else
		{
			RevMob revmob = RevMob.session();
			revmob.showFullscreen(activity);
		}
		
	}
	
	@Override
	public void ShowBannerAd()
	{
		RevMob revmob = RevMob.session();
	    revmob.showBanner(activity);
	}

	@Override
	public void HideBannerAd()
	{
		RevMob revmob = RevMob.session();
	    revmob.hideBanner(activity);
	}
	
	@Override
	public void ShowPopUpAd()
	{
		if (popup != null) 
		{
		      popup.show();
		}
		else
		{
			RevMob revmob = RevMob.session();
			revmob.showPopup(activity);
		}
	}
	@Override
	public void onRestart() 
	{
		
	}

}
