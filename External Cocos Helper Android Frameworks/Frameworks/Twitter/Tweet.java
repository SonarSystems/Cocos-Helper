package sonar.systems.frameworks.Twitter;


import sonar.systems.frameworks.BaseClass.Framework;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;



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
		Intent shareIntent = new Intent();
	    shareIntent.setAction(Intent.ACTION_SEND);
	    shareIntent.setType("text/plain");
	    shareIntent.putExtra(Intent.EXTRA_TEXT, statusString);
	    activity.startActivity(Intent.createChooser(shareIntent, title));
	}

}
