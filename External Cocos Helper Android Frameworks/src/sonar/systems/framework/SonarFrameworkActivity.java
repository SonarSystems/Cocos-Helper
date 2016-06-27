package sonar.systems.framework;

import org.cocos2dx.lib.Cocos2dxActivity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;


public class SonarFrameworkActivity extends Cocos2dxActivity
{
	
	private static SonarFrameworkFunctions functions = null;
	
	protected SonarFrameworkActivity()
	{
		super();
	}

    protected void onLoadNativeLibraries() {
        try {
            ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            String libName = bundle.getString("android.app.lib_name");
            System.loadLibrary(libName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	@Override
	protected void onCreate(Bundle b) 
	{
		super.onCreate(b);
        onLoadNativeLibraries();
		try {
			functions = new SonarFrameworkFunctions(this);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		functions.onCreate(b);
	}

	@Override
	protected void onStart() 
	{
		super.onStart();
		functions.onStart();
	}

	@Override
	protected void onStop() 
	{
		super.onStop();
		functions.onStop();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		functions.onResume();
	}
	
	@Override
	protected void onActivityResult(int request, int response, Intent data)
	{
		super.onActivityResult(request, response, data);
		functions.onActivityResult(request, response, data);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) 
	{
	    super.onSaveInstanceState(outState);
	    functions.onSaveInstanceState(outState);
	}

	@Override
	public void onPause() 
	{
	    super.onPause();
	    functions.onPause();
	}

	@Override
	public void onDestroy() 
	{
	    super.onDestroy();
	    functions.onDestroy();
	}
	
	@Override
	public void onBackPressed() 
	{
			if(functions.onBackPressed())
				return;
			else
				super.onBackPressed();
	}
}