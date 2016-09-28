package coder.victorydst3.mangareader;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import coder.victorydst3.mangareader.ui.home.HomeFragment_;
import coder.victorydst3.mangareader.ui.newest.NewestFragment_;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by VinhHLB on 9/27/16.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        return getFragment(position);
    }

    private Fragment getFragment(int position) {
        switch (position) {
            case 0:
                return NewestFragment_.builder()
                        .build();
            case 1:
                return HomeFragment_.builder()
                        .build();
        }
        throw new RuntimeException("Do not know this position of tab" + position);
    }
}
