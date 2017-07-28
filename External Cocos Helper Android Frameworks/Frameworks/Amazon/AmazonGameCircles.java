package sonar.systems.frameworks.amazon;

/**
 * Written by: Oscar Leif
 * <p>
 * WARNING: Don't use Android 6 has target it will make this crash
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.amazon.ags.api.AGResponseCallback;
import com.amazon.ags.api.AGResponseHandle;
import com.amazon.ags.api.AmazonGamesCallback;
import com.amazon.ags.api.AmazonGamesClient;
import com.amazon.ags.api.AmazonGamesFeature;
import com.amazon.ags.api.AmazonGamesStatus;
import com.amazon.ags.api.achievements.AchievementsClient;
import com.amazon.ags.api.achievements.UpdateProgressResponse;
import com.amazon.ags.api.leaderboards.LeaderboardsClient;
import com.amazon.ags.api.leaderboards.SubmitScoreResponse;

import java.util.EnumSet;

import sonar.systems.frameworks.BaseClass.Framework;

public class AmazonGameCircles extends Framework
{
    public static AmazonGameCircles instance;
    private Activity activity;
    private AmazonGamesClient agsClient;
    private LeaderboardsClient lbClient;
    private boolean gameServicesAvaliable;

    public AmazonGameCircles()
    {
        if (instance == null)
        {
            try
            {
                throw new Exception("Object already exist");
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static AmazonGameCircles getInstance() throws Exception
    {
        if (instance == null)
        {
            instance = new AmazonGameCircles();
        }
        return instance;
    }

    AmazonGamesCallback callback = new AmazonGamesCallback()
    {
        @Override
        public void onServiceReady(AmazonGamesClient arg0)
        {
            agsClient = arg0;
            gameServicesAvaliable = true;
        }

        @Override
        public void onServiceNotReady(AmazonGamesStatus arg0)
        {
            gameServicesAvaliable = false;
        }
    };
    EnumSet<AmazonGamesFeature> myGameFeatures = EnumSet.of(AmazonGamesFeature.Achievements, AmazonGamesFeature.Leaderboards);

    @Override
    public void SetActivity(Activity activity)
    {
        this.activity = activity;
        instance = this;
    }

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        AmazonGamesClient.initialize(activity, callback, myGameFeatures);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (agsClient != null)
        {
            AmazonGamesClient.release();
        }
    }

    @Override
    public void submitScoreAmazon(String leaderboardID, int score)
    {
        super.submitScore(leaderboardID, score);
        Log.e("Oscar JNI", "SubmitScore Works");
        if (gameServicesAvaliable == true)
        {
            Log.d("AmazonGameCircle", "Sending LeaderboardID: " + leaderboardID + "with this score: " + Long.toString(score));
            LeaderboardsClient lbClient = agsClient.getLeaderboardsClient();
            AGResponseHandle<SubmitScoreResponse> handle = lbClient.submitScore(leaderboardID, score);

            // Optional callback to receive notification of success/failure
            handle.setCallback(new AGResponseCallback<SubmitScoreResponse>()
            {
                @Override
                public void onComplete(SubmitScoreResponse result)
                {
                    if (result.isError())
                    {
                        // Add optional error
                    } else
                    {
                        // continue game flow.
                        Toast.makeText(activity, "Record updated", Toast.LENGTH_SHORT);
                    }
                }
            });
        } else
        {
            Log.d("AmazonGameCircles", "Developer mode you need to sign the app first");
        }
    }

    @Override
    public void showLeaderBoardAmazon(String leaderboard)
    {
        Log.d("AmazonGameCircle", "Show LeaderboardID: " + leaderboard);

        if (gameServicesAvaliable == true)
        {
            LeaderboardsClient lbClient = agsClient.getLeaderboardsClient();
            if (lbClient != null)
            {
                lbClient.showLeaderboardOverlay(leaderboard);
            }
        } else
            Log.d("AmazonGameCircles", "if you're debugin it will not work you need to create release");
    }

    @Override
    public void showLeaderboardsAmazon()
    {
        Log.d("AmazonGameCircle", "Show Leaderboards");
        if (gameServicesAvaliable)
        {
            LeaderboardsClient lbClients = agsClient.getLeaderboardsClient();
            if (lbClients != null)
            {
                lbClients.showLeaderboardsOverlay();
            }
        } else
            Log.i("Amazon GameCircle:", "If you're on debug this is normal to appear Game Circles doesn't work in debug mode");
        Log.e("Amazon GameCircle:", "Please check if the app is signed or check your manifest settings");

    }

    @Override
    public void unlockAchievementAmazon(String AchievementID)
    {
        super.unlockAchievementAmazon(AchievementID);
        float unlock = 100.0f;

        this.sendAchievementsUpdate(AchievementID, unlock);
    }

    public void showAchievementsAmazon()
    {
        if (gameServicesAvaliable)
        {
            AchievementsClient aClient = agsClient.getAchievementsClient();
            if (aClient != null)
            {
                aClient.showAchievementsOverlay();
            }
        } else
            Log.i("Amazon GameCircle:", "If you're on debug this is normal to appear Game Circles doesn't work in debug mode");
        Log.e("Amazon GameCircle:", "If you're on debug this is normal to appear");
    }

    private void sendAchievementsUpdate(String achievementID, float achievementProgress)
    {
        AchievementsClient aClient = agsClient.getAchievementsClient();
        AGResponseHandle<UpdateProgressResponse> handle = aClient.updateProgress(achievementID, achievementProgress);
        // Optional callback to receive notification of success/failure
        handle.setCallback(new AGResponseCallback<UpdateProgressResponse>()
        {
            @Override
            public void onComplete(UpdateProgressResponse result)
            {
                if (result.isError())
                {

                } else
                {
                    // Continue game flow
                }
            }
        });
    }

    private void shutdownGameCircle()
    {
        AmazonGamesClient.shutdown();
    }
}
