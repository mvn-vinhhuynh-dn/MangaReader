package coder.victorydst3.mangareader.apdapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import coder.victorydst3.mangareader.R;
import coder.victorydst3.mangareader.model.HomePageItem;
import coder.victorydst3.mangareader.widget.PagerSlidingTabStrip;


public class PagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {

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

    @Override
    public View getView(final int position) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_tab_item, null, false);
        TextView title = (TextView) v.findViewById(R.id.title);
        final RelativeLayout rlTab = (RelativeLayout) v.findViewById(R.id.rlTab);
        title.setText(tabItems.get(position).getTitle());
        final ImageView icon = (ImageView) v.findViewById(R.id.thumb);
        icon.setImageResource(tabItems.get(position).getDrawableResource());

        int width = 0;
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );


        return v;
    }

    @Override
    public void onPageScrollState(int position) {
    }

    @Override
    public void onTabClick(int position, boolean isSameTab) {
//        mOnSelectedTab.onReSelectTab(position);
    }

}



