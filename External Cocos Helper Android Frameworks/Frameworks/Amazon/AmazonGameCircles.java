package sonar.systems.frameworks.Amazon;

/**
 * Written by: Oscar Leif
 */

import java.util.EnumSet;

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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import sonar.systems.frameworks.BaseClass.Framework;

public class AmazonGameCircles extends Framework
{
    private Activity activity;
    private AmazonGamesClient agsClient;
    private LeaderboardsClient lbClient;
    private boolean isEnable;

    public AmazonGameCircles()
    {
    }

    AmazonGamesCallback callback = new AmazonGamesCallback()
    {
        @Override
        public void onServiceReady(AmazonGamesClient arg0)
        {
            agsClient = arg0;
            isEnable = true;
        }
        @Override
        public void onServiceNotReady(AmazonGamesStatus arg0)
        {
            isEnable = false;
        }
    };
    EnumSet<AmazonGamesFeature> myGameFeatures = EnumSet.of(AmazonGamesFeature.Achievements, AmazonGamesFeature.Leaderboards);

    @Override
    public void SetActivity(Activity activity)
    {
        this.activity = activity;
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
    public void submitScore(String leaderboardID, long score)
    {
        super.submitScore(leaderboardID, score);
        if (isEnable == true)
        {
            Log.d("AmazonGameCircle", "Sending LeaderboardID: " + leaderboardID
                    + "with this score: " + Long.toString(score));
            LeaderboardsClient lbClient = agsClient.getLeaderboardsClient();
            AGResponseHandle<SubmitScoreResponse> handle = lbClient
                    .submitScore(leaderboardID, score);

            // Optional callback to receive notification of success/failure
            handle.setCallback(new AGResponseCallback<SubmitScoreResponse>()
            {
                @Override
                public void onComplete(SubmitScoreResponse result)
                {
                    if (result.isError())
                    {
                        // Add optional error
                    }
                    else
                    {
                        // continue game flow.
                    }

                }
            });
        }
        else
        {
            Log.d("AmazonGameCircles",
                    "Developer mode you need to sign the app first");
        }
    }

    @Override
    public Intent showLeaderboard(String leaderboardID)
    {
        // TODO Auto-generated method stub
        Log.d("AmazonGameCircle", "Show LeaderboardID: " + leaderboardID);

        if (isEnable == true)
        {
            LeaderboardsClient lbClient = agsClient.getLeaderboardsClient();
            if (lbClient != null)
            {
                lbClient.showLeaderboardOverlay(leaderboardID);
            }
        }
        else
            Log.d("AmazonGameCircles",
                    "if you're debugin it will not work you need to create release");
        return null;
    }
    @Override
    public void showLeaderboardsAmazon() {
        // TODO Auto-generated method stub
        Log.d("AmazonGameCircle", "Show Leaderboards");
        if(isEnable)
        {
            LeaderboardsClient lbClients = agsClient.getLeaderboardsClient();
            if(lbClients != null)
            {
                lbClients.showLeaderboardsOverlay();
            }
        }
        else
            Log.i("Amazon GameCircle:", "If you're on debug this is normal to appear Game Circles doesn't work in debug mode");
            Log.e("Amazon GameCircle:", "Please check if the app is signed or check your manifest settings");
            
    }
    @Override
    public void unlockAchievementAmazon(String AchievementID)
    {
        // TODO Auto-generated method stub
        super.unlockAchievementAmazon(AchievementID);
        float unlock = 100.0f;
        
        this.sendAchievementsUpdate(AchievementID, unlock);
    }
    public void showAchievementsAmazon()
    {
        if(isEnable)
        {
            AchievementsClient aClient = agsClient.getAchievementsClient();
            if(aClient != null)
            {
                aClient.showAchievementsOverlay();  
            }   
        }
        else
            Log.i("Amazon GameCircle:", "If you're on debug this is normal to appear Game Circles doesn't work in debug mode");
        Log.e("Amazon GameCircle:", "If you're on debug this is normal to appear");
    }
    
    private void sendAchievementsUpdate(String achievementID, float achievementProgress)
    {
        AchievementsClient aClient = agsClient.getAchievementsClient();
        AGResponseHandle<UpdateProgressResponse> handle = aClient.updateProgress(achievementID, achievementProgress);
        //Optional callback to receive notification of success/failure
        handle.setCallback(new AGResponseCallback<UpdateProgressResponse>() 
        {
            @Override
            public void onComplete(UpdateProgressResponse result) 
            {
                if(result.isError())
                {
                    
                }
                else
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