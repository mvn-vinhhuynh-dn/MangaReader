package coder.victorydst3.mangareader;

import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @ViewById(R.id.viewPager)
    ViewPager mViewPager;

    @ViewById(R.id.tabStrip)
    PagerSlidingTabStrip mTabTrip;

    ViewPagerAdapter mViewPagerAdapter;

    @AfterViews
    void afterViews() {
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabTrip.setViewPager(mViewPager);
    }
}
