package sonar.systems.frameworks.Facebook;


import java.util.Arrays;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;

import sonar.systems.frameworks.BaseClass.Framework;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


public class Facebook extends Framework
{
	private Activity activity;
	
	private UiLifecycleHelper uiHelper;
	private Session.StatusCallback statusCallback = new SessionStatusCallback();
	 
	public Facebook()
	{
		
	}
	
	@Override
	public void SetActivity(Activity activity)
	{
		this.activity = activity;
	}
	
	@Override
	public void onCreate(Bundle b) 
	{
		uiHelper = new UiLifecycleHelper(activity, null);
	    uiHelper.onCreate(b);
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
	public void onActivityResult(int request, int response, Intent data)
	{
		uiHelper.onActivityResult(request, response, data, new FacebookDialog.Callback() 
		{
	        @Override
	        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) 
	        {
	            Log.e("Activity", String.format("Error: %s", error.toString()));
	        }

	        @Override
	        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) 
	        {
	            Log.i("Activity", "Success!");
	        }
	    });
	}
	
	@Override
	public void onResume()
	{
		uiHelper.onResume();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) 
	{
		uiHelper.onSaveInstanceState(outState);
	}

	@Override
	public void onPause() 
	{
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() 
	{
		uiHelper.onDestroy();
	}
	
	@Override
	public void FacebookSignIn()
	{
		Session session = Session.getActiveSession();
		
        if (session != null) 
        {
        	if(!session.isOpened() && !session.isClosed())
        	{
            session.openForRead(new Session.OpenRequest(activity)
                .setPermissions(Arrays.asList("public_profile"))
                .setCallback(statusCallback));
        	}
        	 else 
             {
                 Session.openActiveSession(activity, true, Arrays.asList("public_profile"), statusCallback);
             }
        }
        else 
        {
            Session.openActiveSession(activity, true, Arrays.asList("public_profile"), statusCallback);
        }
        //to sign out would be
        //session.closeAndClearTokenInformation();
        
	}
	
	@Override
	public void Share(String name, String link, String description, String caption, String imagePath)
	{
		if (FacebookDialog.canPresentShareDialog(activity.getApplicationContext(), FacebookDialog.ShareDialogFeature.SHARE_DIALOG))
		{
				// Publish the post using the Share Dialog
			FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(activity).setName(name).
																						setDescription(description).
																						setLink(link).build();
			uiHelper.trackPendingDialogCall(shareDialog.present());
		} 
		else
		{
			// Fallback. For example, publish the post using the Feed Dialog
			Bundle params = new Bundle();
		    params.putString("name", name);
		   // params.putString("caption",caption);
		    params.putString("description", description);
		    params.putString("link", link);
		    //params.putString("picture", imagePath);

		    WebDialog feedDialog = (new WebDialog.FeedDialogBuilder(activity,
		            					Session.getActiveSession(),
		            					params))
		            					.setOnCompleteListener(new OnCompleteListener() 
		            					{

								            @Override
								            public void onComplete(Bundle values, FacebookException error) 
								            {
								                if (error == null) 
								                {
								                    // When the story is posted, echo the success
								                    // and the post Id.
								                    final String postId = values.getString("post_id");
								                    if (postId != null) 
								                    {
								                        Toast.makeText(activity,
								                            "Posted story, id: "+postId,
								                            Toast.LENGTH_SHORT).show();
								                    } 
								                    else 
								                    {
								                        // User clicked the Cancel button
								                        Toast.makeText(activity.getApplicationContext(), "Publish cancelled", Toast.LENGTH_SHORT).show();
								                    }
								                } 
								                else if (error instanceof FacebookOperationCanceledException) 
								                {
								                    // User clicked the "x" button
								                    Toast.makeText(activity.getApplicationContext(), "Publish cancelled", Toast.LENGTH_SHORT).show();
								                } 
								                else 
								                {
								                    // Generic, ex: network error
								                    Toast.makeText(activity.getApplicationContext(), "Error posting story", Toast.LENGTH_SHORT).show();
								                }
								            }

	

		            						}).build();
		    feedDialog.show();
		}
	}
	
	private class SessionStatusCallback implements Session.StatusCallback 
	{
		@Override
		public void call(Session session, SessionState state,
				Exception exception) 
		{
			 if (state.isOpened()) 
			 {

			  } 
			 else if (state.isClosed()) 
			 {

			 }
			
		}
	}
}
