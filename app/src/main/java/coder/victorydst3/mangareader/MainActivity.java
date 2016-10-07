package coder.victorydst3.mangareader;

import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import coder.victorydst3.mangareader.apdapter.PagerAdapter;
import coder.victorydst3.mangareader.containerFragment.ChatContainerFragment_;
import coder.victorydst3.mangareader.containerFragment.HomeContainerFragment_;
import coder.victorydst3.mangareader.containerFragment.ProfileContainerFragment_;
import coder.victorydst3.mangareader.containerFragment.RewardContainerFragment_;
import coder.victorydst3.mangareader.model.HomePageItem;
import coder.victorydst3.mangareader.widget.HeaderBar;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity implements ViewPagerAdapter.ImpOnClickItem {
    @ViewById(R.id.viewPager)
    ViewPager mViewPager;

    @ViewById(R.id.tabStrip)
    PagerSlidingTabStrip mTabTrip;
    PagerAdapter mPagerAdapter;
    ViewPagerAdapter mViewPagerAdapter;
    @ViewById(R.id.toolbar)
    HeaderBar mToolBar;

    @AfterViews
    void afterViews() {
        initViewPager();

    }


    @SuppressWarnings("ResourceType")
    private void initViewPager() {
        List<HomePageItem> tabItems = new ArrayList<>();
        tabItems.add(new HomePageItem(HomeContainerFragment_.builder().build(), getString(R.string.tv_main_news), R.drawable.ic_home));
        tabItems.add(new HomePageItem(RewardContainerFragment_.builder().build(), getString(R.string.tv_main_home), R.drawable.ic_home));
        tabItems.add(new HomePageItem(ChatContainerFragment_.builder().build(), getString(R.string.tv_main_favotite), R.drawable.ic_home));
        tabItems.add(new HomePageItem(ProfileContainerFragment_.builder().build(), getString(R.string.tv_main_settings), R.drawable.ic_home));
        mPagerAdapter = new PagerAdapter(MainActivity.this, tabItems);
        mViewPager.setVerticalScrollBarEnabled(false);
        mViewPagerAdapter = new ViewPagerAdapter(this, tabItems, this);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mPagerAdapter);
        mTabTrip.setViewPager(mViewPager);
        mToolBar.setToolBarTitle(tabItems.get(0).getTitle());

    }

    @Override
    public void onClick(int position) {

    }
}
