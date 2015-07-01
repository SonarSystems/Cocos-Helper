package sonar.systems.frameworks.BaseClass;

import sonar.systems.framework.SonarFrameworkSettings;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class Framework
{
	public Framework()
	{
	}
	
	public void onCreate(Bundle b) 
	{	
	}

	public void onStart() 
	{
	}

	
	public void onStop() 
	{
	}

	
	public void onActivityResult(int request, int response, Intent data)
	{
	}
	
	public void onResume()
	{

	}
	
	public void onSaveInstanceState(Bundle outState) 
	{
	    
	}

	
	public void onPause() 
	{
	 
	}

	
	public void onDestroy() 
	{
	    
	}
	
	public boolean onBackPressed()
	{
		 return false;
	}
	
	public void SetActivity(Activity activity)
	{

	}
	
	public void SetActivity(Context activity)
	{

	}
	
	

	
	//GOOGLE PLAY SERVICES
	public boolean isSignedIn()
	{
		return false;
	}
	public void beginUserInitiatedSignIn()
	{
		
	}
	public Object getApiClient() 
	{
		return null;
	}
	
	public void signOut() 
	{
		
	}
	
	public void submitScore(final String leaderboardID, final long score) 
	{
		
	}
	
	public void unlockAchivement(final String achievementID) 
	{
		
	}
	
	public void incrementAchievement(final String achievementID,final int numSteps) 
	{
		
	}
	
	public  Intent showLeaderboard(final String leaderboardID)
	{
		return null;
	}
	
	public  Intent showLeaderboards() 
	{
		return null;
	}
	
	public  Intent showAchievements() 
	{
		return null;
	}
	//END GOOGLE PLAY SERVICES
	
	
	
	
	
	//REVMOB / Chartboost / admob
	public void ShowFullscreenAd()
	{
		
	}
	
	public void PreLoadFullscreenAd()
	{
		
	}
	
	public void ShowPreLoadedFullscreenAd()
	{
		
	}
	
	public void ShowBannerAd()
	{
		
	}
	
	public void ShowBannerAd(int position)
	{
		
	}

	public void HideBannerAd()
	{
		
	}
	
	public void HideBannerAd(int position)
	{
		
	}
	
	public void ShowPopUpAd()
	{
		
	}
	
	public void ShowVideoAd()
	{
		
	}
	
	public void ShowVideoAd(boolean condition)
	{
		
	}
	
	public void PreLoadVideoAd()
	{
		
	}
	//END REVMOB
	
	//FACEBOOK
	public void FacebookSignIn()
	{
		
	}
	
	public void Share(String name, String link, String description, String caption, String imagePath)
	{
		
	}
	//END FACEBOOK
	
	//TWITTER
	
	public void TwitterTweet(String statusString, String title)
	{
		
	}
	//
	
	//GOOGLE ANALYTICS
	public void SetGAScreenName(String screenName)
	{
		
	}
	
	public  void SetGADispatchInterval(int dispatchInterval)
	{
		
	}
	
	public  void SendGAEvent(final String category, final String action, final String label, long value)
	{
		
	}
	//END GOOGLE ANALYTICS
}
