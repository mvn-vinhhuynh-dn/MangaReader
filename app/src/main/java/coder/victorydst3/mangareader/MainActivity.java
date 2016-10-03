package coder.victorydst3.mangareader;

import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import coder.victorydst3.mangareader.widget.HeaderBar;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @ViewById(R.id.viewPager)
    ViewPager mViewPager;

    @ViewById(R.id.tabStrip)
    PagerSlidingTabStrip mTabTrip;

    ViewPagerAdapter mViewPagerAdapter;
    @ViewById(R.id.toolbar)
    HeaderBar mToolBar;

    @AfterViews
    void afterViews() {
        mToolBar.setToolBarTitle(ViewPagerAdapter.mCategories[0]);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabTrip.setViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mToolBar.setToolBarTitle(ViewPagerAdapter.mCategories[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
