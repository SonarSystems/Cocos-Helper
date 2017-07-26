package sonar.systems.frameworks.FlurryAnalytics;

import com.flurry.android.FlurryAgent;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import sonar.systems.frameworks.BaseClass.Framework;

public class Flurry extends Framework
{
    private static String mFlurryApiKey;;
    private Activity activity;
    private String versionName;
//    private int verCode; //This is the version of you app
      
    private final static String TAG = "Flurry cocos";
    
    public Flurry()
    {
        // TODO Auto-generated constructor stub
    }
    
    public void SetActivity(Activity activity)
    {
        this.setActivity(activity);
    }
    
    @Override
    public void onCreate(Bundle b)
    {
        // Flurry analytics
        super.onCreate(b);
        Log.i("[Flurry]", "Flurry SDK initialized");
        FlurryAgent.setLogEnabled(true);
        mFlurryApiKey = getActivity().getResources().getString(getActivity().getResources().getIdentifier("my_flurry_apikey","string",getActivity().getPackageName()));
        FlurryAgent.init(getActivity(),mFlurryApiKey);
        PackageInfo pInfo;
        try
        {
            pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            versionName = pInfo.versionName;
            //verCode = pInfo.versionCode; 
        }
        catch (NameNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        FlurryAgent.setVersionName(versionName);
       
    }
    @Override
    public void onStart()
    {
        super.onStart();
        FlurryAgent.onStartSession(getActivity(),mFlurryApiKey);
    }
    @Override
    public void onStop()
    {
        super.onStop();
        FlurryAgent.onEndSession(getActivity());
    }
    @Override
    public void SendLogEvent(String eventId)
    {
        super.SendLogEvent(eventId);
        FlurryAgent.logEvent(eventId);
    }
    @Override
    public void SendLogEvent(String eventId, boolean timed)
    {
        super.SendLogEvent(eventId, timed);
        FlurryAgent.logEvent(eventId, timed);
    }

    public Activity getActivity()
    {
        return activity;
    }

    public void setActivity(Activity activity)
    {
        this.activity = activity;
    }
}
