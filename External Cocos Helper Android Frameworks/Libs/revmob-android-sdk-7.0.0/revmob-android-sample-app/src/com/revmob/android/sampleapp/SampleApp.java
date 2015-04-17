package com.revmob.android.sampleapp;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.revmob.RevMob;
import com.revmob.RevMobAdsListener;
import com.revmob.RevMobParallaxMode;
import com.revmob.RevMobTestingMode;
import com.revmob.RevMobUserGender;
import com.revmob.ads.banner.RevMobBanner;
import com.revmob.ads.fullscreen.RevMobFullscreen;
import com.revmob.ads.link.RevMobLink;
import com.revmob.ads.popup.RevMobPopup;
import com.revmob.android.R;
import com.revmob.internal.RMLog;

public class SampleApp extends Activity {
	// The App Id is now in the AndroidManifest.xml

	// Just replace the ID below with your app's ID. You can add this code
	// on the Activity.onCreate (above), or anywhere you want - we used a
	// button just for illustration purposes.
	// private static String REVMOB_APPID = "52091490e2f7a34e4e000024";
	// To test in Kindle devices:
	//	private static String REVMOB_APPID = "5106beb3f919861200000072";

	RevMobAdsListener revmobListener = new RevMobAdsListener() {
		@Override
		public void onRevMobSessionIsStarted() {
			toastOnUiThread("RevMob session is started.");
		}
    
		@Override
		public void onRevMobSessionNotStarted(String message) {
			toastOnUiThread("RevMob session failed to start.");
		}
    
    @Override
		public void onRevMobAdReceived() {
			toastOnUiThread("RevMob ad received.");
		}

		@Override
		public void onRevMobAdNotReceived(String message) {
			toastOnUiThread("RevMob ad not received.");
		}

		@Override
		public void onRevMobAdDismiss() {
			toastOnUiThread("Ad dismissed.");
		}

		@Override
		public void onRevMobAdClicked() {
			toastOnUiThread("Ad clicked.");
		}

		@Override
		public void onRevMobAdDisplayed() {
			toastOnUiThread("Ad displayed.");
		}
		
		@Override
		public void onRevMobEulaIsShown() {
			RMLog.i("[RevMob Sample App] Eula is shown.");	
		}

		@Override
		public void onRevMobEulaWasAcceptedAndDismissed() {
			RMLog.i("[RevMob Sample App] Eula was accepeted and dismissed.");
		}

		@Override
		public void onRevMobEulaWasRejected() {
			RMLog.i("[RevMob Sample App] Eula was rejected.");
			
		}
	};
	
	RevMob revmob;
	boolean useUIThread = true;
	Activity currentActivity; // for anonymous classes
	RevMobFullscreen fullscreen;
	RevMobBanner banner;
	RevMobPopup popup;
	RevMobLink link;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RMLog.d("onCreate");
		currentActivity = this;
		setContentView(R.layout.main);
//		revmob = RevMob.start(currentActivity);
		revmob = RevMob.startWithListener(currentActivity, revmobListener);
		// Deprecated:
		//revmob = RevMob.start(currentActivity, REVMOB_APPID);
		fillUserInfo();
		updateTestInfo();
	}

	@Override
	public void onResume() {
		super.onResume();
		RMLog.d("onResume");
	}

	void updateTestInfo() {
		((Button) this.findViewById(R.id.buttonChangeActivity)).setText("Change to Activity 2");
		String activityStr = getClass().getSimpleName().equals("SampleApp") ? "Activity 1" : "Activity 2";
		String threadStr = useUIThread == true ? "UIThread" : "Another thread";
		String testingModeStr;
		if (revmob.getTestingMode() == RevMobTestingMode.WITH_ADS) {
			testingModeStr = "Test mode with ads";
		} else if (revmob.getTestingMode() == RevMobTestingMode.WITHOUT_ADS) {
			testingModeStr = "Test mode without ads";
		} else {
			testingModeStr = "Test mode disabled";
		}
		
		String parallaxModeStr;
		if (revmob.getParallaxMode() == RevMobParallaxMode.DEFAULT) {
			parallaxModeStr = "Parallax mode enabled";
		} else {
			parallaxModeStr = "Parallax mode disabled";
		}
		((TextView) this.findViewById(R.id.textTestInfo)).setText(activityStr + " / " + threadStr + " / " + testingModeStr + " / " + parallaxModeStr);
	}

	// Test functions

	public void changeActivity(View v) {
		Intent intent = new Intent(currentActivity, SampleApp2.class);
		startActivity(intent);
	}

	public void openGLActivity(View v) {
		Intent intent = new Intent(currentActivity, SampleOpenGL.class);
		startActivity(intent);
	}

	public void changeThread(View v) {
		useUIThread = ! useUIThread;
		updateTestInfo();
	}

	public void testingModeWithAds(View v) {
		revmob.setTestingMode(RevMobTestingMode.WITH_ADS);
		updateTestInfo();
	}

	public void testingModeWithoutAds(View v) {
		revmob.setTestingMode(RevMobTestingMode.WITHOUT_ADS);
		updateTestInfo();
	}

	public void testingModeDisable(View v) {
		revmob.setTestingMode(RevMobTestingMode.DISABLED);
		updateTestInfo();
	}
	
	public void parallaxModeDefault(View v) {
		revmob.setParallaxMode(RevMobParallaxMode.DEFAULT);
		updateTestInfo();
	}

	public void parallaxModeDisable(View v) {
		revmob.setParallaxMode(RevMobParallaxMode.DISABLED);
		updateTestInfo();
	}

	public void printEnvironmentInformation(View v) {
		if (useUIThread) {
			revmob.printEnvironmentInformation(currentActivity);
		} else {
			runOnAnotherThread(new Runnable() { @Override
				public void run() { revmob.printEnvironmentInformation(currentActivity); }});
		}
	}

	public void closeApplication(View v) {
		setResult(0);
		finish();
		System.exit(0);
	}

	// Fullscreen

	public void showFullscreen(View v) {
		if (useUIThread) {
			revmob.showFullscreen(currentActivity);
		} else {
			runOnAnotherThread(new Runnable() { @Override
				public void run() { revmob.showFullscreen(currentActivity); } });
		}
	}

	public void loadFullscreen(View v) {
		if (useUIThread) {
			fullscreen = revmob.createFullscreen(currentActivity, revmobListener);
		} else {
			runOnAnotherThread(new Runnable() { @Override
				public void run() { fullscreen = revmob.createFullscreen(currentActivity, revmobListener); } });
		}
	}

	public void showLoadedFullscreen(View v) {
		if (useUIThread) {
			if (fullscreen != null) {
				fullscreen.show();
			}
		} else {
			runOnAnotherThread(new Runnable() { @Override
				public void run() { if (fullscreen != null) {
					fullscreen.show();
				} } });
		}
	}

	// Banner

	void showBanner() {
		banner = revmob.createBanner(currentActivity, revmobListener);
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ViewGroup view = (ViewGroup) findViewById(R.id.banner);
				view.addView(banner);
			}
		});
	}

	public void showBanner(final View v) {
		if (useUIThread) {
			showBanner();
		} else {
			runOnAnotherThread(new Runnable() {
				@Override
				public void run() {
					showBanner();
				}
			});
		}
	}

	public void hideBanner(final View v) {
		if (useUIThread) {
			if (banner != null) {
				banner.hide();
			}
		} else {
			runOnAnotherThread(new Runnable() {
				@Override
				public void run() {
					if (banner != null) {
						banner.hide();
					}
				}
			});
		}
	}

	public void showBannerCustomSize(View v) {
		RevMobBanner banner = revmob.createBanner(currentActivity, revmobListener);
		ViewGroup view = (ViewGroup) findViewById(R.id.bannerCustomSize);
		view.removeAllViews();
		view.addView(banner);
	}

	public void showAbsoluteBannerOnTop(View v) {
		revmob.showBanner(currentActivity, Gravity.TOP, null, revmobListener);
	}

	public void showAbsoluteBannerOnBottom(View v) {
		revmob.showBanner(currentActivity, Gravity.BOTTOM, null, revmobListener);
	}

	public void hideAbsoluteBanner(View v) {
		revmob.hideBanner(currentActivity);
	}

	// Link

	public void openAdLink(View v) {
		if (useUIThread) {
			revmob.openAdLink(currentActivity, revmobListener);
		} else {
			runOnAnotherThread(new Runnable() { @Override
				public void run() { revmob.openAdLink(currentActivity, revmobListener); }});
		}
	}

	public void loadAdLink(View v) {
		if (useUIThread) {
			link = revmob.createAdLink(currentActivity, revmobListener);
		} else {
			runOnAnotherThread(new Runnable() { @Override
				public void run() { link = revmob.createAdLink(currentActivity, revmobListener); }});
		}
	}

	public void openLoadedAdLink(View v) {
		if (useUIThread) {
			if (link != null) {
				link.open();
			}
		} else {
			runOnAnotherThread(new Runnable() { @Override
				public void run() { if (link != null) {
					link.open();
				} }});
		}
	}

	// Popup

	public void showPopup(View v) {
		if (useUIThread) {
			revmob.showPopup(currentActivity);
		} else {
			runOnAnotherThread(new Runnable() { @Override
				public void run() { revmob.showPopup(currentActivity); } });
		}
	}

	public void loadPopup(View v) {
		if (useUIThread) {
			popup = revmob.createPopup(currentActivity, revmobListener);
		} else {
			runOnAnotherThread(new Runnable() { @Override
				public void run() { popup = revmob.createPopup(currentActivity, revmobListener); } });
		}
	}

	public void showLoadedPopup(View v) {
		if (useUIThread) {
			if (popup != null) {
				popup.show();
			}
		} else {
			runOnAnotherThread(new Runnable() { @Override
				public void run() { if (popup != null) {
					popup.show();
				} } });
		}
	}

	// Auxiliar methods

	void runOnAnotherThread(Runnable action) {
		new Thread(action).start();
	}

	void toastOnUiThread(final String message) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(currentActivity, message, Toast.LENGTH_SHORT).show();
			}
		});
	}

	void fillUserInfo() {

		ArrayList<String> interests = new ArrayList<String>();
		interests.add("mobile");
		interests.add("Android");
		interests.add("apps");
		
		try {
			LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
			Location gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			Location netLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


		}
		catch (Exception e) {
			RMLog.d("Unable to get the location data");
		}
	}

	// Debug logs

	@Override
	public void onStart() {
		super.onStart();
		RMLog.d("onStart");
	}

	@Override
	public void onPause() {
		super.onPause();
		RMLog.d("onPause");
	}

	@Override
	public void onStop() {
		super.onStop();
		RMLog.d("onStop");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		RMLog.d("onDestroy");
	}

}
