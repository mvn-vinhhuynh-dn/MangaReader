package coder.victorydst3.mangareader;

import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import coder.victorydst3.mangareader.widget.HeaderBar;
import lombok.Getter;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @ViewById(R.id.viewPager)
    ViewPager mViewPager;

    @ViewById(R.id.tabStrip)
    PagerSlidingTabStrip mTabTrip;

    ViewPagerAdapter mViewPagerAdapter;
    @ViewById(R.id.toolbar)
    @Getter
    HeaderBar mToolBar;

    @AfterViews
    void afterViews() {
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabTrip.setViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
