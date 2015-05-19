package sonar.systems.frameworks.GoogleAnalyticsAPI;


import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import sonar.systems.frameworks.BaseClass.Framework;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

public class GoogleAnalyticsAPI extends Framework
{
	private Activity activity;
	
	private String tracking_id;
	
	GoogleAnalytics analytics;
	Tracker t;
	
	public GoogleAnalyticsAPI()
	{
		
	}
	
	
	@Override
	public void SetActivity(Activity activity)
	{
		this.activity = activity;
		
		tracking_id = activity.getResources().getString(activity.getResources().getIdentifier("google_analytics_tracking_id","string",activity.getPackageName()));
		
		analytics = GoogleAnalytics.getInstance(activity);
		analytics.setLocalDispatchPeriod(120);

		t = analytics.newTracker(tracking_id);
		t.enableAdvertisingIdCollection(true);
		t.enableAutoActivityTracking(true);
		t.enableExceptionReporting(true);
		t.enableAdvertisingIdCollection(true);

		
	}
	
	@Override
	public  void SetGADispatchInterval(int dispatchInterval)
	{
		analytics.setLocalDispatchPeriod(dispatchInterval);
	}
	@Override
	public  void SendGAEvent(final String category, final String action, final String label, long value)
	{
		t.send(new HitBuilders.EventBuilder()
		.setCategory(category)
		.setAction(action)
		.setLabel(label)
		.setValue(value)
		.build());
	}
	
	@Override
	public void SetGAScreenName(String screenName)
	{
		t.setScreenName(screenName);
		t.send(new HitBuilders.ScreenViewBuilder().build());
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
	public void onPause()
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
	public void onDestroy() 
	{
		
	}

}
