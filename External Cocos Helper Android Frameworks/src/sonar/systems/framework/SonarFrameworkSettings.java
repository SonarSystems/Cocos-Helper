package sonar.systems.framework;

public class SonarFrameworkSettings
{
	//Set each framework value to true if you want to use it in your project
	
	public static final boolean USE_GOOGLE_PLAY_GAME_SERVICES = false;	//Requires google play services library 
	
	public static final boolean USE_GOOGLE_ANALYTICS = false;			//Requires google play services library 
	
	public static final boolean USE_ADMOB = false;						//Requires  Google play services  library
	
	public static final boolean USE_REVMOB = false;						//Requires revmob jar file and Google play services  library

	public static final boolean USE_FACEBOOK = false;					//Requires facebook sdk library and android support and bolt jar file (in the facebook sdk libs folder)
	
	public static final boolean USE_TWITTER = false; 					//uses intents for now so no need to include anything else.
	
	public static final boolean USE_CHARTBOOST = false; 				//Requires  Google play services  library and chartboost jar
	
	public static final boolean USE_MOPUB = false;						//Requires MoPub SDK
    
    public static final boolean USE_ADCOLONY = false;					//Requires AdColony SDK
    
    public static final boolean USE_VUNGLE = false;						//Requires Vungle SDK
}
