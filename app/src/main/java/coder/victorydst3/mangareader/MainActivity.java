package coder.victorydst3.mangareader;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import coder.victorydst3.mangareader.apdapter.PagerAdapter;
import coder.victorydst3.mangareader.api.RestClient;
import coder.victorydst3.mangareader.containerFragment.BaseContainerFragment;
import coder.victorydst3.mangareader.containerFragment.FavoriteContainerFragment_;
import coder.victorydst3.mangareader.containerFragment.HomeContainerFragment_;
import coder.victorydst3.mangareader.containerFragment.NewestContainerFragment_;
import coder.victorydst3.mangareader.model.HomePageItem;
import coder.victorydst3.mangareader.model.ResponseData;
import coder.victorydst3.mangareader.ui.setting.SettingFragment_;
import coder.victorydst3.mangareader.widget.HeaderBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @ViewById(R.id.viewPager)
    ViewPager mViewPager;
    @ViewById(R.id.tabs)
    TabLayout mTabLayout;
    PagerAdapter mPagerAdapter;
    @ViewById(R.id.toolbar)
    HeaderBar mToolBar;
    List<HomePageItem> tabItems = new ArrayList<>();

    @AfterViews
    void afterViews() {
        initViewPager();
    }

    @SuppressWarnings("ResourceType")
    void initViewPager() {
        tabItems.add(new HomePageItem(NewestContainerFragment_.builder().build(), getString(R.string.tv_main_news), R.drawable.ic_main_new));
        tabItems.add(new HomePageItem(HomeContainerFragment_.builder().build(), getString(R.string.tv_main_home), R.drawable.ic_main_new));
        tabItems.add(new HomePageItem(FavoriteContainerFragment_.builder().build(), getString(R.string.tv_main_favotite), R.drawable.ic_main_new));
        tabItems.add(new HomePageItem(SettingFragment_.builder().build(), getString(R.string.tv_main_settings), R.drawable.ic_main_new));
        mPagerAdapter = new PagerAdapter(MainActivity.this, tabItems);
        mViewPager.setVerticalScrollBarEnabled(false);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTabTextColors(getResources().getColor(R.color.unSelect), getResources().getColor(R.color.selected));

        mToolBar.setToolBarTitle(tabItems.get(0).getTitle());
        setupTabIcons();
        fakeData();
    }

    private void fakeData() {
        RestClient.getClient(this).login("username", "password").enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                Log.d("MainActivity", response.body().getData().toString());
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }

    private void setupTabIcons() {
        for (int i = 0; i < tabItems.size(); i++) {
            mTabLayout.getTabAt(i).setIcon(tabItems.get(i).getDrawableResource());
        }
    }

    public BaseContainerFragment getBaseCurrentFragment() {
        return (BaseContainerFragment) mPagerAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
    }

    @Override
    public void onBackPressed() {
        handBackPress();
    }

    public void handBackPress() {
        boolean isPopFragment = false;
        BaseContainerFragment f = getBaseCurrentFragment();
        if (f != null) {
            isPopFragment = f.popFragment();
        }
        if (!isPopFragment) {
            // chỗ này đáng lẽ thoát
            if (getBaseCurrentFragment() instanceof HomeContainerFragment_) {
                moveTaskToBack(true);
            } else {
                mViewPager.setCurrentItem(0);
            }
        }
    }
}
