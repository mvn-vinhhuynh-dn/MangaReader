package coder.victorydst3.mangareader;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import coder.victorydst3.mangareader.ui.favorite.FavoriteFragment_;
import coder.victorydst3.mangareader.ui.home.HomeFragment_;
import coder.victorydst3.mangareader.ui.newest.NewestFragment_;
import coder.victorydst3.mangareader.ui.setting.SettingFragment_;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by VinhHLB on 9/27/16.
 */

class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private static final String[] mCategories = {"Newest", "Home", "Favorite", "Setting"};

    ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 4;
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
            case 2:
                return FavoriteFragment_.builder()
                        .build();
            case 3:
                return SettingFragment_.builder()
                        .build();
        }
        throw new RuntimeException("Do not know this position of tab" + position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mCategories[position];
    }
}
