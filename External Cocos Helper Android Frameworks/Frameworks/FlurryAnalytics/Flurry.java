package sonar.systems.frameworks.FlurryAnalytics;

import com.flurry.android.FlurryAgent;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import sonar.systems.frameworks.BaseClass.Framework;

public class Flurry extends Framework
{
    public static Flurry Instance = null;

    String mFlurryApiKey = "NPYF6NTWH95CFDGRBH56";

    public static String tag = "Flurry Plugin";

    private static final Flurry instance = null;

    private Activity activity;

    private boolean IsInitialized = false;

    private String versionName;

    public Flurry()
    {
        if(instance==null)
        {
            try
            {
                throw new Exception("Object Already Exist");
            }
            catch (Exception e){e.printStackTrace();}
        }
    }

    public static Flurry getInstance()
    {
        if(Instance == null)
        {
            Instance = new Flurry();
        }
        return Instance;
    }


    public void SetActivity(Activity activity)
    {
        this.setActivity(activity);
        Instance = this;
        init(mFlurryApiKey,false);
    }

    public void init(String appKey, boolean testMode)
    {
        Log.i(tag, "Flurry SDK initialized");

        new FlurryAgent.Builder().withLogEnabled(testMode).build(activity, appKey);
        PackageInfo pInfo;
        try
        {
            pInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            versionName = pInfo.versionName;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        FlurryAgent.setVersionName(versionName);
        IsInitialized = true;
        //Print (Get) Release version
        Log.i(tag, FlurryAgent.getReleaseVersion());
        //Start Session
        FlurryAgent.onStartSession(activity);
    }


    @Override
    public void onCreate(Bundle b)
    {
        // Flurry analytics
        super.onCreate(b);
    }
    @Override
    public void onStart()
    {
        super.onStart();
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
