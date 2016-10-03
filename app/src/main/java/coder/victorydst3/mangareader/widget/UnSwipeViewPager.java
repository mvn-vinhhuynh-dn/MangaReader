package coder.victorydst3.mangareader.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by VinhHLB on 10/3/16.
 */

public class UnSwipeViewPager extends ViewPager {

    private boolean mCanScrollHorizontally;

    public UnSwipeViewPager(Context context) {
        super(context);
    }

    public UnSwipeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return mCanScrollHorizontally && super.onInterceptTouchEvent(arg0);
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        return mCanScrollHorizontally && super.canScrollHorizontally(direction);
    }

    public void setCanScrollHorizontally(boolean canScrollHorizontally) {
        if (canScrollHorizontally == mCanScrollHorizontally) {
            return;
        }
        this.mCanScrollHorizontally = canScrollHorizontally;
        invalidate();
    }
}
