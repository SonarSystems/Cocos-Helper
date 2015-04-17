package com.facebook.samples.AdUnitsSample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.ads.*;

public class RectangleFragment extends Fragment implements AdListener {

    private RelativeLayout adViewRectangleContainer;
    private TextView adRectangleStatusLabel;
    private AdView adViewRectangle;

    private String statusLabel = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize status label
        statusLabel = getString(R.string.loading_status);

        adViewRectangle = new AdView(this.getActivity(), "YOUR_PLACEMENT_ID", AdSize.RECTANGLE_HEIGHT_250);

        // Set a listener to get notified on changes or when the user interact with the ad.
        adViewRectangle.setAdListener(this);

        // Initiate a request to load an ad.
        adViewRectangle.loadAd();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rectangle, container, false);

        adViewRectangleContainer = (RelativeLayout)view.findViewById(R.id.adViewContainerRectangle);
        adRectangleStatusLabel = (TextView)view.findViewById(R.id.adRectangleStatusLabel);
        setLabel(statusLabel);

        // Reposition the ad and add it to the view hierarchy.
        adViewRectangleContainer.addView(adViewRectangle);

        return view;
    }

    @Override
    public void onDestroyView() {
        adViewRectangleContainer.removeView(adViewRectangle);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        adViewRectangle.destroy();
        adViewRectangle = null;

        super.onDestroy();
    }

    @Override
    public void onError(Ad ad, AdError error) {
        if (ad == adViewRectangle) {
            setLabel("Ad failed to load: " + error.getErrorMessage());
        }
    }

    @Override
    public void onAdLoaded(Ad ad) {
        if (ad == adViewRectangle) {
            setLabel("");
        }
    }

    @Override
    public void onAdClicked(Ad ad) {
        Toast.makeText(this.getActivity(), "Ad Clicked", Toast.LENGTH_SHORT).show();
    }

    private void setLabel(String status) {
        statusLabel = status;
        if (adRectangleStatusLabel != null) {
            adRectangleStatusLabel.setText(statusLabel);
        }
    }
}
