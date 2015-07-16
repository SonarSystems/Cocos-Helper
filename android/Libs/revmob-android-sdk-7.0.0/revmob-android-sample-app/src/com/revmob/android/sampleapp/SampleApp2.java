package com.revmob.android.sampleapp;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.revmob.android.R;


public class SampleApp2 extends SampleApp {

    @Override
    void updateTestInfo() {
    	super.updateTestInfo();
    	((Button) this.findViewById(R.id.buttonChangeActivity)).setText("Change to Activity 1");
    }

    @Override
	public void changeActivity(View v) {
    	Intent intent = new Intent(this, SampleApp.class);
    	this.startActivity(intent);
    }

}
