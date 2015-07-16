package com.facebook.samples.AdUnitsSample;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsViewPagerAdapter extends FragmentPagerAdapter {
    public TabsViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new BannerFragment();
            case 1:
                return new RectangleFragment();
            case 2:
                return new InterstitialFragment();
        }

        return null;
    }

    @Override
    public int getCount() { return 3; }

}
