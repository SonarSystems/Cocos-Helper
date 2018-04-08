package sonar.systems.frameworks.Amazon;

/**
 * Written by: Oscar Leif
 */

import android.app.Activity;
import android.util.Log;

import com.amazon.ags.api.AGResponseHandle;
import com.amazon.ags.api.AmazonGamesCallback;
import com.amazon.ags.api.AmazonGamesClient;
import com.amazon.ags.api.AmazonGamesFeature;
import com.amazon.ags.api.AmazonGamesStatus;
import com.amazon.ags.api.leaderboards.LeaderboardsClient;
import com.amazon.ags.api.leaderboards.SubmitScoreResponse;

import java.util.EnumSet;

import sonar.systems.frameworks.BaseClass.Framework;

public class AmazonGameCircles extends Framework
{
    public static String TAG = "Game Circles Plugin";

    private Activity mainActivity;
    private AmazonGamesClient agsClient;
    private AmazonGamesCallback callbackListener = new AmazonGamesCallback()
    {
        @Override
        public void onServiceReady(AmazonGamesClient amazonGamesClient)
        {
            agsClient = amazonGamesClient;
            Log.d(TAG, "Amazon Games Client is Ready");
        }

        @Override
        public void onServiceNotReady(AmazonGamesStatus amazonGamesStatus)
        {
            agsClient = null;
            Log.d(TAG, "Amazon Games Client is not ready");
        }
    };
    private EnumSet<AmazonGamesFeature> myGameFeatures = EnumSet.of(AmazonGamesFeature.Achievements,AmazonGamesFeature.Leaderboards);

    //Must First Call this From Cocos2d-x
    @Override
    public void SetActivity(Activity activity)
    {
        super.SetActivity(activity);
        this.mainActivity = activity;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        AmazonGamesClient.initialize(this.mainActivity, callbackListener, myGameFeatures);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if(this.agsClient!=null)
        {
            this.agsClient.release();
        }
    }

    @Override
    public void showLeaderboardsAmazon()
    {
        super.showLeaderboardsAmazon();
        if(this.agsClient!=null)
        {
            this.agsClient.getLeaderboardsClient().showLeaderboardsOverlay();
        }
        else
        {
            Log.d(TAG, "Game Service is not ready. This will only work on Signed Apps");
        }
    }

    @Override
    public void showLeaderboardAmazon(String leaderboardId)
    {
        super.showLeaderboardAmazon(leaderboardId);
        if(this.agsClient!=null)
        {
            this.agsClient.getLeaderboardsClient().showLeaderboardOverlay(leaderboardId);
        }
        else
        {
            Log.d(TAG, "Game Service is not ready. This will only work on Signed Apps");
        }
    }

    @Override
    public void showAchievementsAmazon()
    {
        super.showAchievementsAmazon();
        if(this.agsClient!=null)
        {
            this.agsClient.getAchievementsClient().showAchievementsOverlay();
        }
        else
        {
            Log.d(TAG, "Game Service is not ready. This will only work on Signed Apps");
        }
    }

    @Override
    public void submitScoreAmazon(String leaderboardID, int score)
    {
        super.submitScoreAmazon(leaderboardID, score);
        if(this.agsClient!=null)
        {
            LeaderboardsClient lbClient = agsClient.getLeaderboardsClient();
            AGResponseHandle<SubmitScoreResponse> handle = lbClient.submitScore(leaderboardID, score);
        }
        else
        {
            Log.d(TAG, "Game Service is not ready. This will only work on Signed Apps");
        }
    }

    @Override
    public void unlockAchievementAmazon(String AchievementID)
    {
        super.unlockAchievementAmazon(AchievementID);
        float achivementProgress = 100f;
        if(this.agsClient!=null)
        {
            this.agsClient.getAchievementsClient().updateProgress(AchievementID, achivementProgress);
        }
        else
        {
            Log.d(TAG, "Game Service is not ready. This will only work on Signed Apps");
        }
    }

    //TODO Implements this Via C++ java object Calls
    public void unlockAchievementAmazon(String AchievementID, float progressUnlock)
    {
        if(this.agsClient!=null)
        {
            this.agsClient.getAchievementsClient().updateProgress(AchievementID, progressUnlock);
        }
        else
        {
            Log.d(TAG, "Game Service is not ready. This will only work on Signed Apps");
        }
    }
}
