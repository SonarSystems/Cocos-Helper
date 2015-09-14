package sonar.systems.frameworks.Amazon;

import java.util.EnumSet;

import com.amazon.ags.api.AGResponseCallback;
import com.amazon.ags.api.AGResponseHandle;
import com.amazon.ags.api.AmazonGamesCallback;
import com.amazon.ags.api.AmazonGamesClient;
import com.amazon.ags.api.AmazonGamesFeature;
import com.amazon.ags.api.AmazonGamesStatus;
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
            // TODO Auto-generated method stub
            agsClient = arg0;
            isEnable = true;
        }

        @Override
        public void onServiceNotReady(AmazonGamesStatus arg0)
        {
            // TODO Auto-generated method stub
            isEnable = false;
            Log.d("GameCircleClient", "Amazon game services not ready,Game circles doesn't work in debug mode. maybe your api_key.txt file is not loaded. api_ket.txt file goes in resources");
            Log.d("GameCircleClient", "maybe your api_key.txt file is not loaded. api_ket.txt file goes in Resources not in assets. Cocos takes from Resources to assets.");
            Log.d("GameCircleClient", "Go to the maniest in the part of Amazon Game circles replace your.package.app with your own game package"); // That's all the possible errors that I know.
        }
    };
    EnumSet<AmazonGamesFeature> myGameFeatures = EnumSet.of(
            AmazonGamesFeature.Achievements, AmazonGamesFeature.Leaderboards);

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

    public void sendScoreToLeaderboard(String leaderboardID, long score)
    {

    }

    @Override
    public void submitScore(String leaderboardID, long score)
    {
        // TODO Auto-generated method stub
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
            Log.d("AmazonGameCircles","if you're debugin it will not work you need to create release");
        return null;
    }

}
