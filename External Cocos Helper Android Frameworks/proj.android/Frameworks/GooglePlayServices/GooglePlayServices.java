package sonar.systems.frameworks.GooglePlayServices;

/*
 * Copyright (C) 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import sonar.systems.frameworks.BaseClass.Framework;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

public class GooglePlayServices extends Framework implements
		GooglePlayServicesGameHelper.GameHelperListener
{

	private Context ctx;
	// The game helper object. This class is mainly a wrapper around this
	// object.
	protected GooglePlayServicesGameHelper mHelper;

	// We expose these constants here because we don't want users of this class
	// to have to know about GameHelper at all.
	public static final int CLIENT_GAMES = GooglePlayServicesGameHelper.CLIENT_GAMES;
	public static final int CLIENT_APPSTATE = GooglePlayServicesGameHelper.CLIENT_APPSTATE;
	public static final int CLIENT_PLUS = GooglePlayServicesGameHelper.CLIENT_PLUS;
	public static final int CLIENT_ALL = GooglePlayServicesGameHelper.CLIENT_ALL;

	
	// Requested clients. By default, that's just the games client.
	protected int mRequestedClients = CLIENT_GAMES;

	private final static String TAG = "GooglePlayServices";
	protected boolean mDebugLog = false;

	/** Constructs a BaseGameActivity with default client (GamesClient). */
	public GooglePlayServices() 
	{

	}

	@Override
	public void SetActivity(Activity activity)
	{
		this.ctx = (Context)activity;
	}
	/**
	 * Constructs a BaseGameActivity with the requested clients.
	 * 
	 * @param requestedClients
	 *            The requested clients (a combination of CLIENT_GAMES,
	 *            CLIENT_PLUS and CLIENT_APPSTATE).
	 */
	protected GooglePlayServices(Context ctx, int requestedClients) 
	{
		this.ctx = ctx;
		setRequestedClients(requestedClients);
	}

	/**
	 * Sets the requested clients. The preferred way to set the requested
	 * clients is via the constructor, but this method is available if for some
	 * reason your code cannot do this in the constructor. This must be called
	 * before onCreate or getGameHelper() in order to have any effect. If called
	 * after onCreate()/getGameHelper(), this method is a no-op.
	 * 
	 * @param requestedClients
	 *            A combination of the flags CLIENT_GAMES, CLIENT_PLUS and
	 *            CLIENT_APPSTATE, or CLIENT_ALL to request all available
	 *            clients.
	 */
	protected void setRequestedClients(int requestedClients) 
	{
		mRequestedClients = requestedClients;
	}

	public GooglePlayServicesGameHelper getGameHelper() 
	{
		if (mHelper == null) 
		{
			mHelper = new GooglePlayServicesGameHelper((Activity) ctx, mRequestedClients);
			mHelper.enableDebugLog(mDebugLog);
		}
		return mHelper;
	}
	@Override
	public void onCreate(Bundle b) 
	{
		GooglePlayServicesGameHelperUtils.setResIDS(ctx);
		if (mHelper == null) {
			getGameHelper();
		}
		mHelper.setup(this);
	}
	@Override
	public void onStart() 
	{
		mHelper.onStart((Activity) ctx);
	}
	@Override
	public void onStop() 
	{
		mHelper.onStop();
	}
	@Override
	public void onActivityResult(int request, int response, Intent data)
	{
		//super.onActivityResult(request, response, data);
		mHelper.onActivityResult(request, response, data);
	}
	
	@Override
	public GoogleApiClient getApiClient() 
	{
		return mHelper.getApiClient();
	}
	
	@Override
	public boolean isSignedIn() 
	{
		return mHelper.isSignedIn();
	}
	
	@Override
	public void beginUserInitiatedSignIn() 
	{
		mHelper.beginUserInitiatedSignIn();
	}
	
	@Override
	public void signOut() 
	{
		mHelper.signOut();
	}
	
	@Override
	public void submitScore(final String leaderboardID, final long score) 
	{
		Games.Leaderboards.submitScore(getApiClient(),leaderboardID, score);
	}
	@Override
	public void unlockAchivement(final String achievementID) 
	{
		Games.Achievements.unlock(getApiClient(),achievementID);
	}
	@Override
	public void incrementAchievement(final String achievementID,final int numSteps) 
	{
		Games.Achievements.increment(getApiClient(),achievementID, numSteps);
	}
	@Override
	public  Intent showLeaderboard(final String leaderboardID)
	{
		return Games.Leaderboards.getLeaderboardIntent(getApiClient(),leaderboardID);
	}
	@Override
	public  Intent showLeaderboards() 
	{
		return Games.Leaderboards.getAllLeaderboardsIntent(getApiClient());
	}
	@Override
	public  Intent showAchievements() 
	{
		return Games.Achievements.getAchievementsIntent(getApiClient());
	}
	
	public void showAlert(String message) 
	{
		mHelper.makeSimpleDialog(message).show();
	}

	public void showAlert(String title, String message) 
	{
		mHelper.makeSimpleDialog(title, message).show();
	}

	public void enableDebugLog(boolean enabled) 
	{
		mDebugLog = true;
		if (mHelper != null) {
			mHelper.enableDebugLog(enabled);
		}
	}

	@Deprecated
	protected void enableDebugLog(boolean enabled, String tag)
	{
		Log.w(TAG, "BaseGameActivity.enabledDebugLog(bool,String) is "
				+ "deprecated. Use enableDebugLog(boolean)");
		enableDebugLog(enabled);
	}

	public String getInvitationId() 
	{
		return mHelper.getInvitationId();
	}

	public void reconnectClient() 
	{
		mHelper.reconnectClient();
	}

	public boolean hasSignInError() 
	{
		return mHelper.hasSignInError();
	}

	public GooglePlayServicesGameHelper.SignInFailureReason getSignInError() 
	{
		return mHelper.getSignInError();
	}
	
	@Override
	public void onSignInFailed() 
	{
		// Override in child class
	}

	@Override
	public void onSignInSucceeded() 
	{
		// Override in child class
	}
}
