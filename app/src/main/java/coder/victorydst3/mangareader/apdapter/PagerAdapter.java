package coder.victorydst3.mangareader.apdapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import coder.victorydst3.mangareader.model.HomePageItem;


public class PagerAdapter extends FragmentPagerAdapter  {

    private List<HomePageItem> tabItems = new ArrayList<>();
    private Context mContext;

    public PagerAdapter(Context context, List<HomePageItem> items) {
        super(((AppCompatActivity) context).getSupportFragmentManager());
        this.mContext = context;
        this.tabItems = items;

    }

    @Override
    public Fragment getItem(final int position) {
        return tabItems.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return tabItems.size();
    }

    @Override
    public int getItemPosition(Object item) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabItems.get(position).getTitle();
    }


}



