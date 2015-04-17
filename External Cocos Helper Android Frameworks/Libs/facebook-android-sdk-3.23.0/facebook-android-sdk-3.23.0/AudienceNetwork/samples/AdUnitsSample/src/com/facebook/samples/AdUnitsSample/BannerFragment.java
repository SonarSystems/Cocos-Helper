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

public class BannerFragment extends Fragment implements AdListener {

    private RelativeLayout adViewBannerContainer;
    private TextView adBannerStatusLabel;
    private AdView adViewBanner;

    private String statusLabel = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize status label
        statusLabel = getString(R.string.loading_status);

        // Create a banner's ad view with a unique placement ID (generate your own on the Facebook app settings).
        // Use different ID for each ad placement in your app.
        boolean isTablet = getResources().getBoolean(R.bool.is_tablet);
        adViewBanner = new AdView(this.getActivity(), "YOUR_PLACEMENT_ID",
                isTablet ? AdSize.BANNER_HEIGHT_90 : AdSize.BANNER_HEIGHT_50);

        // Set a listener to get notified on changes or when the user interact with the ad.
        adViewBanner.setAdListener(this);

        // Initiate a request to load an ad.
        adViewBanner.loadAd();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_banner, container, false);

        adViewBannerContainer = (RelativeLayout)view.findViewById(R.id.adViewContainer);

        adBannerStatusLabel = (TextView)view.findViewById(R.id.adStatusLabel);
        setLabel(statusLabel);
        // Reposition the ad and add it to the view hierarchy.
        adViewBannerContainer.addView(adViewBanner);

        return view;
    }

    @Override
    public void onDestroyView() {
        adViewBannerContainer.removeView(adViewBanner);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        adViewBanner.destroy();
        adViewBanner = null;
        super.onDestroy();
    }

    @Override
    public void onError(Ad ad, AdError error) {
        if (ad == adViewBanner) {
            setLabel("Ad failed to load: " + error.getErrorMessage());
        }
    }

    @Override
    public void onAdLoaded(Ad ad) {
        if (ad == adViewBanner) {
            setLabel("");
        }
    }

    @Override
    public void onAdClicked(Ad ad) {
        Toast.makeText(this.getActivity(), "Ad Clicked", Toast.LENGTH_SHORT).show();
    }

    private void setLabel(String status) {
        statusLabel = status;
        if (adBannerStatusLabel != null) {
            adBannerStatusLabel.setText(statusLabel);
        }
    }
}
