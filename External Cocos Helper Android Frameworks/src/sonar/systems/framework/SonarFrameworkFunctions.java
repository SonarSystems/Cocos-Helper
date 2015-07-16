package sonar.systems.framework;

import java.lang.reflect.InvocationTargetException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import sonar.systems.frameworks.BaseClass.Framework;
//import sonar.systems.framework.test.R;

public class SonarFrameworkFunctions 
{
	private static Context app = null;
	
	//private Resources R;
	//Google Play Services
	private static Framework googlePlayServices = null;
	private static final int REQUEST_ACHIEVEMENTS = 10000;
	private static final int REQUEST_LEADERBOARDS = 10001;
	private static final int REQUEST_LEADERBOARD = 10002;
	//Google Play Services
	
	//Google Analytics
	private static Framework googleAnalytics = null;
	//Google Analytics
	
	//RevMob
	private static Framework revmob = null;
	//RevMob
	
	//Facebook
	private static Framework facebook = null;
	//Facebook
	
	//Twitter
	private static Framework twitter = null;
	//Twitter
	
	//AdMob
	private static Framework admob = null;
	//AdMob
	
	//Chartboost
	private static Framework chartboost = null;
	//Chartboost
	
	//MoPub
	private static Framework mopub = null;
	//MoPub
	
    //Adcolony
    private static Framework adcolony = null;
    //Adcolony
    
    //Vungle
    private static Framework vungle = null;
    //Vungle

	public SonarFrameworkFunctions(Context app) throws ClassNotFoundException
	{
		SonarFrameworkFunctions.app = app;
		InitFrameworks();
	}
	
	private static void InitFrameworks() throws ClassNotFoundException
	{	
		//GOOGLE PLAY SERVICES
		if(SonarFrameworkSettings.USE_GOOGLE_PLAY_GAME_SERVICES)
		{

				try {
					googlePlayServices = (Framework) Class.forName("sonar.systems.frameworks.GooglePlayServices.GooglePlayServices").getConstructor().newInstance();
					googlePlayServices.SetActivity(((SonarFrameworkActivity)app));
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		else 
		{
			googlePlayServices = new Framework(); // empty object
		}
		//END GOOGLE PLAY SERVICES
		
		//GOOGLE ANALYTICS
		if(SonarFrameworkSettings.USE_GOOGLE_ANALYTICS)
		{

				try {
					googleAnalytics = (Framework) Class.forName("sonar.systems.frameworks.GoogleAnalyticsAPI.GoogleAnalyticsAPI").getConstructor().newInstance();
					googleAnalytics.SetActivity(((SonarFrameworkActivity)app));
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		else 
		{
			googleAnalytics = new Framework(); // empty object
		}
		//END GOOGLE ANALYTICS
		
		//REVMOB
		if(SonarFrameworkSettings.USE_REVMOB)
		{
			try 
			{
				revmob = (Framework) Class.forName("sonar.systems.frameworks.RevMob.RevMobAds").getConstructor().newInstance();
				revmob.SetActivity(((SonarFrameworkActivity)app));
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else 
		{
			revmob = new Framework(); // empty object
		}
		//END REVMOB
		
		//FACEBOOK
		if(SonarFrameworkSettings.USE_FACEBOOK)
		{
			try 
			{
				facebook = (Framework) Class.forName("sonar.systems.frameworks.Facebook.Facebook").getConstructor().newInstance();
				facebook.SetActivity(((SonarFrameworkActivity)app));
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else 
		{
			facebook = new Framework(); // empty object
		}
		//END FACEBOOK
		
		//TWITTER
		if(SonarFrameworkSettings.USE_TWITTER)
		{
			try 
			{
				twitter = (Framework) Class.forName("sonar.systems.frameworks.Twitter.Tweet").getConstructor().newInstance();
				twitter.SetActivity(((SonarFrameworkActivity)app));
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else 
		{
			twitter = new Framework(); // empty object
		}
		//END TWITTER
		
		//ADMOB
		if(SonarFrameworkSettings.USE_ADMOB)
		{
			try 
			{
				admob = (Framework) Class.forName("sonar.systems.frameworks.AdMob.AdMobAds").getConstructor().newInstance();
				admob.SetActivity(((SonarFrameworkActivity)app));
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else 
		{
			admob = new Framework(); // empty object
		}
		//END ADMOB
		
		//CHARTBOOST
		if(SonarFrameworkSettings.USE_CHARTBOOST)
		{
			
			try {
				chartboost = (Framework) Class.forName("sonar.systems.frameworks.ChartBoost.ChartBoostAds").getConstructor().newInstance();
				chartboost.SetActivity(((SonarFrameworkActivity)app));
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else 
		{
			chartboost = new Framework(); // empty object
		}
		//END CHARTBOOST
		
		//MOPUB
		if(SonarFrameworkSettings.USE_MOPUB)
		{
			
			try {
				mopub = (Framework) Class.forName("sonar.systems.frameworks.MoPub.MoPubAds").getConstructor().newInstance();
				mopub.SetActivity(((SonarFrameworkActivity)app));
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else 
		{
			mopub = new Framework(); // empty object
		}
		//END MOPUB
        
        //ADCOLONY
        if(SonarFrameworkSettings.USE_ADCOLONY)
        {
            
            try {
                adcolony = (Framework) Class.forName("sonar.systems.frameworks.AdColony.AdColonyAds").getConstructor().newInstance();
                adcolony.SetActivity(((SonarFrameworkActivity)app));
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else 
        {
            adcolony = new Framework(); // empty object
        }
        //END ADCOLONY
        
        //VUNGLE
        if(SonarFrameworkSettings.USE_VUNGLE)
        {
            
            try {
                vungle = (Framework) Class.forName("sonar.systems.frameworks.Vungle.VungleAds").getConstructor().newInstance();
                vungle.SetActivity(((SonarFrameworkActivity)app));
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else
        {
            vungle = new Framework(); // empty object
        }
        //END VUNGLE
		
	}
	
	public static void displayAlert(final String message) 
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(app);
		builder.setMessage(message);
		builder.setNeutralButton(app.getResources().getString(android.R.string.ok), null);
		builder.create().show();
	}
	
	//GooglePlayServices Functions 
	public static boolean isSignedIn() 						//returns if the user is signed into google play services
	{
		if(SonarFrameworkSettings.USE_GOOGLE_PLAY_GAME_SERVICES)
		{
			return googlePlayServices.isSignedIn();
		}
		return false;
	}

	public static void gameServicesSignIn() 
	{
		if(SonarFrameworkSettings.USE_GOOGLE_PLAY_GAME_SERVICES)
		{
			((SonarFrameworkActivity) app).runOnUiThread(new Runnable() 
			{
	
				@Override
				public void run() 
				{
					if (!isSignedIn())
					{
						googlePlayServices.beginUserInitiatedSignIn();
					}
				}
			});
		}
	}

	public static void gameServicesSignOut() 
	{
		if(SonarFrameworkSettings.USE_GOOGLE_PLAY_GAME_SERVICES)
		{
			((SonarFrameworkActivity) app).runOnUiThread(new Runnable() 
			{
	
				@Override
				public void run() 
				{
					if (isSignedIn())
					{
						
						googlePlayServices.signOut();
					}
				}
			});
		}
	}

	public static void submitScore(final String leaderboardID, final long score)
	{
		if(SonarFrameworkSettings.USE_GOOGLE_PLAY_GAME_SERVICES)
		{
			((SonarFrameworkActivity) app).runOnUiThread(new Runnable() 
			{
	
				@Override
				public void run() 
				{
					if (isSignedIn()) 
					{
						Log.d("Google Play Services", "Submit score " + score + " to " + leaderboardID);
						googlePlayServices.submitScore(leaderboardID,score);
	
					} 
					else 
					{
						String message = app.getResources().getString(app.getResources().getIdentifier("fail_submit_score_leaderboard","string",app.getPackageName()));
						message = message.replace("{score}", score + "");
						message = message.replace("{leaderboardID}", leaderboardID);
						displayAlert(message);

					}
				}
			});
		}

	}
	
	public static void submitScore(final String leaderboardID, final int score)
	{
		if(SonarFrameworkSettings.USE_GOOGLE_PLAY_GAME_SERVICES)
		{
			((SonarFrameworkActivity) app).runOnUiThread(new Runnable() 
			{
	
				@Override
				public void run() 
				{
					if (isSignedIn()) 
					{
						Log.d("Google Play Services", "Submit score " + score + " to " + leaderboardID);
						googlePlayServices.submitScore(leaderboardID,score);
	
					} 
					else 
					{
						String message = app.getResources().getString(app.getResources().getIdentifier("fail_submit_score_leaderboard","string",app.getPackageName()));
						message = message.replace("{score}", score + "");
						message = message.replace("{leaderboardID}", leaderboardID);
						displayAlert(message);

					}
				}
			});
		}

	}

	public static void unlockAchievement(final String achievementID) 
	{
		if(SonarFrameworkSettings.USE_GOOGLE_PLAY_GAME_SERVICES)
		{
			((SonarFrameworkActivity) app).runOnUiThread(new Runnable()
			{
	
				@Override
				public void run() 
				{
					if (isSignedIn()) 
					{
						Log.d("Google Play Services", "Try to unlock achievement " + achievementID);
						googlePlayServices.unlockAchivement(achievementID);
					} 
					else 
					{
						String message = app.getResources().getString(app.getResources().getIdentifier("fail_unlock_achievement","string",app.getPackageName()));
						message = message.replace("{achievementID}", achievementID);
						displayAlert(message);
					}
				}
			});
		}
	}

	public static void incrementAchievement(final String achievementID,
			final int numSteps) 
	{
		if(SonarFrameworkSettings.USE_GOOGLE_PLAY_GAME_SERVICES)
		{
			((SonarFrameworkActivity) app).runOnUiThread(new Runnable() 
			{
	
				@Override
				public void run() 
				{
					if (isSignedIn())
					{
						googlePlayServices.incrementAchievement(achievementID, numSteps);
					}
					else 
					{
						String message = app.getResources().getString(app.getResources().getIdentifier("fail_increment_achievement","string",app.getPackageName()));
						message = message.replace("{achievementID}", achievementID);
						message = message.replace("{numSteps}", numSteps + "");
						displayAlert(message);
					}
				}
			});
		}
	}

	public static void showAchievements() 
	{
		if(SonarFrameworkSettings.USE_GOOGLE_PLAY_GAME_SERVICES)
		{
			((SonarFrameworkActivity) app).runOnUiThread(new Runnable() 
			{
	
				@Override
				public void run() 
				{
					if (isSignedIn())
					{
						((SonarFrameworkActivity) app).startActivityForResult(googlePlayServices.showAchievements(),REQUEST_ACHIEVEMENTS);
					}
					else 
					{
						String message = app.getResources().getString(app.getResources().getIdentifier("fail_show_achievements","string",app.getPackageName()));
						displayAlert(message);
					}
	
				}
			});
		}

	}

	public static void showLeaderboards() 
	{
		if(SonarFrameworkSettings.USE_GOOGLE_PLAY_GAME_SERVICES)
		{
			((SonarFrameworkActivity) app).runOnUiThread(new Runnable() 
			{
	
				@Override
				public void run() 
				{
					if (isSignedIn())
					{
						((SonarFrameworkActivity) app).startActivityForResult(googlePlayServices.showLeaderboards(),REQUEST_LEADERBOARDS);
					}
					else 
					{
						String message = app.getResources().getString(app.getResources().getIdentifier("fail_show_leaderboards","string",app.getPackageName()));
						displayAlert(message);
					}
				}
			});
		}

	}

	public static void showLeaderboard(final String leaderboardID) 
	{
		if(SonarFrameworkSettings.USE_GOOGLE_PLAY_GAME_SERVICES)
		{
			((SonarFrameworkActivity) app).runOnUiThread(new Runnable() 
			{
	
				@Override
				public void run() {
					if (isSignedIn())
					{
						((SonarFrameworkActivity) app).startActivityForResult(googlePlayServices.showLeaderboard(leaderboardID),REQUEST_LEADERBOARD);
					}
					else 
					{
						String message = app.getResources().getString(app.getResources().getIdentifier("fail_show_leaderboard","string",app.getPackageName()));
						message = message.replace("{leaderboardID}", leaderboardID);
						displayAlert(message);
					}
				}
			});
		}

	}
	//End GooglePlayServices Functions
	
	//Revmob Functions
	public static void ShowFullscreenAd()
	{
		if(SonarFrameworkSettings.USE_REVMOB)
		{
			revmob.ShowFullscreenAd();
		}
	}
	
	public static void ShowPopUpAd()
	{
		if(SonarFrameworkSettings.USE_REVMOB)
		{
			revmob.ShowPopUpAd();
		}
	}
	//End Revmob Functions
	
	//Facebook Functions
	public static void FacebookSignIn()
	{
		if(SonarFrameworkSettings.USE_FACEBOOK)
		{
			facebook.FacebookSignIn();
		}
	}
	
	public static void FacebookShare(final String name,final String link, final String description, final String caption, final String imagePath )
	{
		if(SonarFrameworkSettings.USE_FACEBOOK)
		{
			Log.v("FACEBOOK", name);
			Log.v("FACEBOOK", link);
			Log.v("FACEBOOK", description);
			Log.v("FACEBOOK", caption);
			Log.v("FACEBOOK", imagePath);
			facebook.Share(name, link, description, caption, imagePath);
		}
	}
	//End Facebook Functions
	
	//Twitter Functions
	public static void TwitterTweet(final String tweet, final String title)
	{
		if(SonarFrameworkSettings.USE_TWITTER)
		{
			twitter.TwitterTweet(tweet,title);
		}
	}

	//End Twitter Functions
		
	//AdMob Functions	
	public static void ShowBannerAd()
	{
		if(SonarFrameworkSettings.USE_ADMOB)
		{
			admob.ShowBannerAd();
		}
	}
	
	public static void ShowBannerAd(int position)
	{
		if(SonarFrameworkSettings.USE_ADMOB)
		{
			admob.ShowBannerAd(position);
		}
	}

	public static void HideBannerAd()
	{
		if(SonarFrameworkSettings.USE_ADMOB)
		{
			admob.HideBannerAd();
		}
	}
	
	public static void HideBannerAd(int position)
	{
		if(SonarFrameworkSettings.USE_ADMOB)
		{
			admob.HideBannerAd(position);
		}
	}
	
	public static void ShowFullscreenAdAM()
	{	
		if(SonarFrameworkSettings.USE_ADMOB)
		{
			admob.ShowFullscreenAd();
		}
	}
	
	public static void PreLoadFullscreenAdAM()
	{	
		if(SonarFrameworkSettings.USE_ADMOB)
		{
			admob.PreLoadFullscreenAd();
		}
	}
	
	public static void ShowPreLoadedFullscreenAdAM()
	{	
		if(SonarFrameworkSettings.USE_ADMOB)
		{
			admob.ShowPreLoadedFullscreenAd();
		}
	}
	
	//End AdMob Functions
	
	//Chartboost Functions
	public static void ShowFullscreenAdCB()
	{
		if(SonarFrameworkSettings.USE_CHARTBOOST)
		{
			chartboost.ShowFullscreenAd();
		}
	}
	
	public static void PreLoadFullscreenAdCB()
	{	
		if(SonarFrameworkSettings.USE_CHARTBOOST)
		{
			chartboost.PreLoadFullscreenAd();
		}
	}
	
	public static void PreLoadVideoAdCB()
	{	
		if(SonarFrameworkSettings.USE_CHARTBOOST)
		{
			chartboost.PreLoadVideoAd();
		}
	}
	
	public static void ShowVideoAdCB()
	{
		if(SonarFrameworkSettings.USE_CHARTBOOST)
		{
			chartboost.ShowVideoAd();
		}
	}
	//End Chartboost Functions
	
	//Mopub Functions
	public static void ShowBannerAdMP()
	{
		if(SonarFrameworkSettings.USE_MOPUB)
		{
			mopub.ShowBannerAd();
		}
	}
	
	public static void HideBannerAdMP()
	{
		if(SonarFrameworkSettings.USE_MOPUB)
		{
			mopub.HideBannerAd();
		}
	}
	
	public static void ShowFullscreenAdMP()
	{
		if(SonarFrameworkSettings.USE_MOPUB)
		{
			mopub.ShowFullscreenAd();
		}
	}
	//End Mopub Functions
    
    //Adcolony Functions
    public static void ShowRewardedVideoAdAC()
    {
        if(SonarFrameworkSettings.USE_ADCOLONY)
        {
            adcolony.ShowVideoAd();
        }
    }
    //End Adcolony Functions
    
    //Vungle Functions
    public static void ShowRewardedVideoAdV(boolean isIncentivised)
    {
        if(SonarFrameworkSettings.USE_VUNGLE)
        {
            vungle.ShowVideoAd(isIncentivised);
        }
    }
    //End Vungle Functions
	
	//Google Analytics Functions
	
	public static void SetGAScreenName(final String screenName)
	{
		if(SonarFrameworkSettings.USE_GOOGLE_ANALYTICS)
		{
			googleAnalytics.SetGAScreenName(screenName);
		}
	}
	
	public static void SetGADispatchInterval(int dispatchInterval)
	{
		if(SonarFrameworkSettings.USE_GOOGLE_ANALYTICS)
		{
			googleAnalytics.SetGADispatchInterval(dispatchInterval);
		}
	}
	
	public static void SendGAEvent(final String category, final String action, final String label, long value)
	{
		if(SonarFrameworkSettings.USE_GOOGLE_ANALYTICS)
		{
			googleAnalytics.SendGAEvent(category,action,label,value);
		}
	}
	//End Google Analytics Functions
	
	//Activity Functions OnStart/OnStop/onActivityResult
	public void onStart() 
	{
		//GOOGLE PLAY SERVICES
		if(SonarFrameworkSettings.USE_GOOGLE_PLAY_GAME_SERVICES)
		{
			googlePlayServices.onStart();
		}
		
		if(SonarFrameworkSettings.USE_FACEBOOK)
		{
		    facebook.onStart();
		}
		
		if(SonarFrameworkSettings.USE_TWITTER)
		{
		    twitter.onStart();
		}
		
		if(SonarFrameworkSettings.USE_ADMOB)
		{
			admob.onStart();
		}
		
		if(SonarFrameworkSettings.USE_CHARTBOOST)
	    {
	    	 chartboost.onStart();
	    }
	}

	public void onStop() 
	{
		//GOOGLE PLAY SERVICES
		if(SonarFrameworkSettings.USE_GOOGLE_PLAY_GAME_SERVICES)
		{
			googlePlayServices.onStop();
		}
		
		if(SonarFrameworkSettings.USE_FACEBOOK)
		{
		    facebook.onStop();
		}
		
		if(SonarFrameworkSettings.USE_TWITTER)
		{
		    twitter.onStop();
		}
		
		if(SonarFrameworkSettings.USE_ADMOB)
		{
			admob.onStop();
		}
		
		if(SonarFrameworkSettings.USE_CHARTBOOST)
	    {
	    	 chartboost.onStop();
	    }
		
	}

	public void onActivityResult(int request, int response, Intent data)
	{
		//GOOGLE PLAY SERVICES
		if(SonarFrameworkSettings.USE_GOOGLE_PLAY_GAME_SERVICES)
		{
			googlePlayServices.onActivityResult(request, response, data);
		}
		
		if(SonarFrameworkSettings.USE_FACEBOOK)
		{
		    facebook.onActivityResult(request, response, data);
		}
		
		if(SonarFrameworkSettings.USE_TWITTER)
		{
		    twitter.onActivityResult(request, response, data);
		}
		
		if(SonarFrameworkSettings.USE_ADMOB)
		{
			admob.onActivityResult(request, response, data);
		}
	}
	
	public void onCreate(Bundle b)
	{
		//GOOGLE PLAY SERVICES
		if(SonarFrameworkSettings.USE_GOOGLE_PLAY_GAME_SERVICES)
		{
			googlePlayServices.onCreate(b);
		}
		//REVMOB
		if(SonarFrameworkSettings.USE_REVMOB)
		{
			revmob.onCreate(b);
		}
		
		 if(SonarFrameworkSettings.USE_FACEBOOK)
		 {
		    facebook.onCreate(b);
		 }
		 
		if(SonarFrameworkSettings.USE_TWITTER)
		{
			    twitter.onCreate(b);
		}
		
		if(SonarFrameworkSettings.USE_ADMOB)
		{
			admob.onCreate(b);
		}
		
		 if(SonarFrameworkSettings.USE_CHARTBOOST)
		 {
			 chartboost.onCreate(b);
		 }
        
        if(SonarFrameworkSettings.USE_ADCOLONY)
        {
            adcolony.onCreate(b);
        }
        
        if(SonarFrameworkSettings.USE_VUNGLE)
        {
            vungle.onCreate(b);
        }
		
	}
	
	public void onResume()
	{
		//REVMOB
		if(SonarFrameworkSettings.USE_REVMOB)
		{
			revmob.onResume();
		}
		
		 if(SonarFrameworkSettings.USE_FACEBOOK)
		 {
		    	facebook.onResume();
		 }
		 
		 if(SonarFrameworkSettings.USE_ADMOB)
		{
				 admob.onResume();
		}
		 
		 if(SonarFrameworkSettings.USE_CHARTBOOST)
	    {
	    	 chartboost.onResume();
	    }
        
        if(SonarFrameworkSettings.USE_ADCOLONY)
        {
            adcolony.onResume();
        }
        
        if(SonarFrameworkSettings.USE_VUNGLE)
        {
            vungle.onResume();
        }

	}
	
	public void onSaveInstanceState(Bundle outState) 
	{
	    if(SonarFrameworkSettings.USE_FACEBOOK)
	    {
	    	facebook.onSaveInstanceState(outState);
	    }
	}

	public void onPause() 
	{
		if(SonarFrameworkSettings.USE_FACEBOOK)
	    {
	    	facebook.onPause();
	    }
		
		if(SonarFrameworkSettings.USE_ADMOB)
	    {
	    	admob.onPause();
	    }
		
		if(SonarFrameworkSettings.USE_CHARTBOOST)
	    {
	    	 chartboost.onPause();
	    }
        
        if(SonarFrameworkSettings.USE_ADCOLONY)
        {
            adcolony.onPause();
        }
        
        if(SonarFrameworkSettings.USE_VUNGLE)
        {
            vungle.onPause();
        }
	}

	public void onDestroy() 
	{
		if(SonarFrameworkSettings.USE_FACEBOOK)
	    {
	    	facebook.onDestroy();
	    }
		
		if(SonarFrameworkSettings.USE_ADMOB)
	    {
	    	admob.onDestroy();
	    }
		
		if(SonarFrameworkSettings.USE_CHARTBOOST)
	    {
	    	 chartboost.onDestroy();
	    }
		
		if(SonarFrameworkSettings.USE_MOPUB)
		{
			mopub.onDestroy();
		}
	}
	
	public boolean onBackPressed() 
	{
		if(SonarFrameworkSettings.USE_CHARTBOOST)
	    {
	    	return chartboost.onBackPressed();
	    }
	   
		return false;
	}
	//End Activity Functions OnStart/OnStop/onCreate/onActivityResult
}
