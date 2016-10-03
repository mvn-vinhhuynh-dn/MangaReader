package coder.victorydst3.mangareader.ui.slide;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import coder.victorydst3.mangareader.R;
import coder.victorydst3.mangareader.model.Manga;
import coder.victorydst3.mangareader.widget.FixedSpeedScroller;
import coder.victorydst3.mangareader.widget.UnSwipeViewPager;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by VinhHlb on 1/12/16.
 */
@EViewGroup(R.layout.slide)
public class SlideView extends RelativeLayout {
    private static final String TAG = SlideView.class.getSimpleName();

    private static final int SLIDE_DURATION = 500;
    // Auto sliding interval for each page. In screen design document we set 3000ms for interval,
    // but auto-slide duration takes 500ms. So AUTO_SLIDE_INTERVAL should equal 3000 + SLIDE_DURATION
    private static final int AUTO_SLIDE_INTERVAL = 3000 + SLIDE_DURATION;
    // Delay for auto-slide first page
    private static final int AUTO_SLIDE_OFFSET = 3000;

    private long mLastClickTime = 0;
    // If true: allow click image next and previous, false don't allow
    private boolean mCanClick = true;
    private static final int MAX_NUMBER_CAMPAIGN = 10;

    @ViewById(R.id.viewPagerCampaign)
    UnSwipeViewPager mViewPager;

    @ViewById(R.id.rlParent)
    RelativeLayout mRlParent;

    private CampaignAdapter mAdapter;
    private List<Manga> mMangas = new ArrayList<>();

    private Timer mSlideTimer;
    private Handler mSlideHandle = new Handler();

    private final Runnable mSlideRunnable = new Runnable() {
        public void run() {
            if (mViewPager.getCurrentItem() == mAdapter.getCount() - 1) {
                // If auto sliding one round -> stop auto sliding
                stopAutoSlide();
                mViewPager.setCurrentItem(0, false);
            } else {
                // Auto sliding to next page
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            }
            showButtonNextAndPrevious();
        }
    };

    @AfterViews
    void afterViews() {
        initViewPager();

        startAutoSlide();
    }

    public void startAutoSlide() {
        // Stop auto first if exist
        stopAutoSlide();

        // Start at page 0
        mViewPager.setCurrentItem(0, false);
        showButtonNextAndPrevious();
        mSlideTimer = new Timer();
        mSlideTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mSlideHandle.post(mSlideRunnable);
            }
        }, AUTO_SLIDE_OFFSET, AUTO_SLIDE_INTERVAL);
    }

    public void stopAutoSlide() {
        mCanClick = true;
        if (mSlideTimer != null) {
            mSlideTimer.cancel();
        }
        mSlideHandle.removeCallbacks(mSlideRunnable);
    }

    private void initViewPager() {
        changeDurationSlideViewPager(mViewPager);
        mAdapter = new CampaignAdapter(getContext(), mMangas);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mCanClick = false;
            }

            @Override
            public void onPageSelected(int position) {
                // No-op
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                mCanClick = true;
            }
        });
    }

    public SlideView(Context context) {
        super(context);
    }

    public SlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    /**
//     * This function will use to set campaign listener from usedClass
//     *
//     * @param onCampaignListener campaign listener
//     */
//    public void setCampaignListener(OnCampaignListener onCampaignListener) {
//        mOnCampaignListener = onCampaignListener;
//    }

    private void slideImage(boolean isToRight) {
        int i = mViewPager.getCurrentItem();
        if (isToRight) {
            if (i < mAdapter.getCount()) {
                mViewPager.setCurrentItem(i + 1);
            }
        } else {
            if (i != 0) {
                mViewPager.setCurrentItem(i - 1);
            }
        }
        showButtonNextAndPrevious();
    }

    private void showButtonNextAndPrevious() {
        final int currentPosition = mViewPager.getCurrentItem();
//        showLeftIcon(currentPosition != 0);
//        showRightIcon(currentPosition != mAdapter.getCount() - 1 && mMangas.size() > 1);
    }

//    private void showLeftIcon(boolean isShow) {
//        if (isShow) {
//            mImgCampaignLeft.setVisibility(VISIBLE);
//        } else {
//            mImgCampaignLeft.setVisibility(GONE);
//        }
//    }

//    private void showRightIcon(boolean isShow) {
//        if (isShow) {
//            mImgCampaignRight.setVisibility(VISIBLE);
//        } else {
//            mImgCampaignRight.setVisibility(GONE);
//        }
//    }

    private void changeDurationSlideViewPager(ViewPager viewPager) {
        try {
            Field fieldScroller = ViewPager.class.getDeclaredField("mScroller");
            fieldScroller.setAccessible(true);
            Interpolator interpolator = new AccelerateInterpolator();
            FixedSpeedScroller fixedSpeedScroller = new FixedSpeedScroller(viewPager.getContext(), interpolator, false, SLIDE_DURATION);
            fieldScroller.set(viewPager, fixedSpeedScroller);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            Log.e(TAG, "changeDurationSlideViewPager: ", e);
        }
    }

//    @Touch({R.id.imgCampaignLeft, R.id.imgCampaignRight})
//    void onCampaignTouch(View v, MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                // When touch on image next & previous -> stop auto sliding
//                stopAutoSlide();
//                break;
//            case MotionEvent.ACTION_UP:
//                // Don't allow slide when touch on image next and previous, if it is sliding
//                if (SystemClock.elapsedRealtime() - mLastClickTime < SLIDE_DURATION || !mCanClick) {
//                    return;
//                }
//                mLastClickTime = SystemClock.elapsedRealtime();
//
//                // Sliding by touch on image next and previous
//                if (v.getId() == R.id.imgCampaignLeft) {
//                    slideImage(false);
//                } else if (v.getId() == R.id.imgCampaignRight) {
//                    slideImage(true);
//                }
//                break;
//        }
//    }

    /**
     * Class adapter for viewPager
     */
    private static class CampaignAdapter extends PagerAdapter {
        final LayoutInflater mLayoutInflater;
        private List<Manga> mCampaigns;

        CampaignAdapter(Context context, List<Manga> campaigns) {
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mCampaigns = campaigns;
        }

        @Override
        public int getCount() {
            return 10;
//            return mCampaigns.size() > MAX_NUMBER_CAMPAIGN ? MAX_NUMBER_CAMPAIGN : mCampaigns.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = mLayoutInflater.inflate(R.layout.item_list_campaign, container, false);
            ImageView imgCampaign = (ImageView) itemView.findViewById(R.id.imgCampaign);
//            Manga manga = mCampaigns.get(position);
            Glide.with(mLayoutInflater.getContext())
                    .load("http://lh5.ggpht.com/-DFUspEoOcCM/Uam20oqt-5I/AAAAAAAAENU/vYOagZZfhrw/s0/001.jpg")
                    .centerCrop()
                    .dontAnimate()
                    .into(imgCampaign);
            Log.d(TAG, "instantiateItem: ");
            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
