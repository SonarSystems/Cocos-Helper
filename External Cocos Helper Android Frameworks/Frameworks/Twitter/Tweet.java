package sonar.systems.frameworks.Twitter;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import sonar.systems.frameworks.BaseClass.Framework;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;



public class Tweet extends Framework
{
	private Activity activity;
    
	
	public Tweet( )
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
	public void onSaveInstanceState(Bundle outState) 
	{
		
	}

	@Override
	public void onPause() 
	{
		
	}

	@Override
	public void onDestroy() 
	{
		
	}

	public void SignIn()
	{
		
	}


	@Override
	public void TwitterTweet(String statusString, String title)
	{
		/*Intent shareIntent = new Intent();
	    shareIntent.setAction(Intent.ACTION_SEND);
	    shareIntent.setType("text/plain");
	    shareIntent.putExtra(Intent.EXTRA_TEXT, statusString);
	    activity.startActivity(Intent.createChooser(shareIntent, title));*/
		
		// Create intent using ACTION_VIEW and a normal Twitter url:
		String tweetUrl = 
		    String.format("https://twitter.com/intent/tweet?text=%s&url=",
		        urlEncode(statusString));
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl));

		// Narrow down to official Twitter app, if available:
		List<ResolveInfo> matches = activity.getPackageManager().queryIntentActivities(intent, 0);
		for (ResolveInfo info : matches) {
		    if (info.activityInfo.packageName.toLowerCase().startsWith("com.twitter")) {
		        intent.setPackage(info.activityInfo.packageName);
		    }
		}

		activity.startActivity(intent);
	}
	
	public static String urlEncode(String s) 
	{
	    try {
	        return URLEncoder.encode(s, "UTF-8");
	    }
	    catch (UnsupportedEncodingException e) {
	        Log.wtf("TWITTER", "UTF-8 should always be supported", e);
	        throw new RuntimeException("URLEncoder.encode() failed for " + s);
	    }
	}

}
