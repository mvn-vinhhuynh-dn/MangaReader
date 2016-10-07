/* Copyright 2013 Andreas Stuetz

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package coder.victorydst3.mangareader.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

/**
 * Custom ui sliding menu horizontal
 */

public class PagerSlidingTabStrip extends HorizontalScrollView {

    // @formatter:off
    private static final int[] ATTRS = new int[]{
            android.R.attr.textSize,
            android.R.attr.textColor
    };
    private final PageListener mPageListener = new PageListener();
    // @formatter:on
    private final LinearLayout.LayoutParams mDefaultTabLayoutParams;
    private final LinearLayout.LayoutParams mExpandedTabLayoutParams;
    private final LinearLayout mTabsContainer;
    private final Paint mRectPaint;
    private final Paint mDividerPaint;
    private final Typeface mTabTypeface = null;
    private OnPageChangeListener delegatePageListener;
    private ListenPagerChange mListenPagerChange;
    private ViewPager mPager;
    private int mTabCount;
    private int mCurrentPosition = 0;
    private float mCurrentPositionOffset = 0f;
    private int mIndicatorColor = 0xFF666666;
    private int mUnderlineColor = 0x1A000000;
    private int mDividerColor = 0x1A000000;
    private boolean mShouldExpand = false;
    private boolean mTextAllCaps = true;
    private int mScrollOffset = 52;
    private int mIndicatorHeight = 8;
    private int mUnderlineHeight = 2;
    private int mDividerPadding = 12;
    private int mTabPadding = 16;
    private int mTabTextSize = 12;
    private int mTabTextColor = 0xFF666666;
    private int mLastScrollX = 0;
    private Locale mLocale;

    public PagerSlidingTabStrip(Context context) {
        this(context, null);
    }

    public PagerSlidingTabStrip(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagerSlidingTabStrip(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFillViewport(true);
        setWillNotDraw(false);

        mTabsContainer = new LinearLayout(context);
        mTabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        mTabsContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(mTabsContainer);

        DisplayMetrics dm = getResources().getDisplayMetrics();

        mScrollOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mScrollOffset, dm);
        mIndicatorHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mIndicatorHeight, dm);
        mUnderlineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mUnderlineHeight, dm);
        mDividerPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mDividerPadding, dm);
        mTabPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mTabPadding, dm);
        int dividerWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, dm);
        mTabTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mTabTextSize, dm);

        // get system attrs (android:textSize and android:textColor)

        TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);
        mTabTextSize = a.getDimensionPixelSize(0, mTabTextSize);
        mTabTextColor = a.getColor(0, mTabTextColor);
        a.recycle();

        // get custom attrs
        a = context.obtainStyledAttributes(attrs, com.astuetz.pagerslidingtabstrip.R.styleable.PagerSlidingTabStrip);
        mIndicatorColor = a.getColor(com.astuetz.pagerslidingtabstrip.R.styleable.PagerSlidingTabStrip_pstsIndicatorColor, mIndicatorColor);
        mUnderlineColor = a.getColor(com.astuetz.pagerslidingtabstrip.R.styleable.PagerSlidingTabStrip_pstsUnderlineColor, mUnderlineColor);
        mDividerColor = a.getColor(com.astuetz.pagerslidingtabstrip.R.styleable.PagerSlidingTabStrip_pstsDividerColor, mDividerColor);
        mIndicatorHeight = a.getDimensionPixelSize(com.astuetz.pagerslidingtabstrip.R.styleable.PagerSlidingTabStrip_pstsIndicatorHeight, mIndicatorHeight);
        mUnderlineHeight = a.getDimensionPixelSize(com.astuetz.pagerslidingtabstrip.R.styleable.PagerSlidingTabStrip_pstsUnderlineHeight, mUnderlineHeight);
        mDividerPadding = a.getDimensionPixelSize(com.astuetz.pagerslidingtabstrip.R.styleable.PagerSlidingTabStrip_pstsDividerPadding, mDividerPadding);
        mTabPadding = a.getDimensionPixelSize(com.astuetz.pagerslidingtabstrip.R.styleable.PagerSlidingTabStrip_pstsTabPaddingLeftRight, mTabPadding);
        mShouldExpand = a.getBoolean(com.astuetz.pagerslidingtabstrip.R.styleable.PagerSlidingTabStrip_pstsShouldExpand, mShouldExpand);
        mScrollOffset = a.getDimensionPixelSize(com.astuetz.pagerslidingtabstrip.R.styleable.PagerSlidingTabStrip_pstsScrollOffset, mScrollOffset);
        mTextAllCaps = a.getBoolean(com.astuetz.pagerslidingtabstrip.R.styleable.PagerSlidingTabStrip_pstsTextAllCaps, mTextAllCaps);
        a.recycle();

        mRectPaint = new Paint();
        mRectPaint.setAntiAlias(true);
        mRectPaint.setStyle(Style.FILL);
        mDividerPaint = new Paint();
        mDividerPaint.setAntiAlias(true);
        mDividerPaint.setStrokeWidth(dividerWidth);

        mDefaultTabLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        mExpandedTabLayoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);

        if (mLocale == null) {
            mLocale = getResources().getConfiguration().locale;
        }
    }

    public void setListenPagerChange(ListenPagerChange listenPagerChange) {
        mListenPagerChange = listenPagerChange;
    }

    public void setViewPager(ViewPager pager) {
        this.mPager = pager;

        if (pager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }

        pager.addOnPageChangeListener(mPageListener);
        notifyDataSetChanged();
    }

    private void notifyDataSetChanged() {
        mTabsContainer.removeAllViews();
        mTabCount = mPager.getAdapter().getCount();

        for (int i = 0; i < mTabCount; i++) {
            if (mPager.getAdapter() instanceof IconTabProvider) {
                addTabView(i);
            } else {
                addTextTab(i, mPager.getAdapter().getPageTitle(i).toString());
            }
        }

        updateTabStyles();
        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }

                mCurrentPosition = mPager.getCurrentItem();
                mTabsContainer.getChildAt(mCurrentPosition).setSelected(true);
                scrollToChild(mCurrentPosition, 0);
            }
        });
    }

    private void addTextTab(final int position, String title) {
        TextView tab = new TextView(getContext());
        tab.setText(title);
        tab.setGravity(Gravity.CENTER);
        tab.setSingleLine();
        addTab(position, tab);
    }

    private void addTabView(int position) {
        LinearLayout view = new LinearLayout(getContext());
        view.setOrientation(LinearLayout.VERTICAL);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setGravity(Gravity.CENTER);
        view.setPadding(mTabPadding, mIndicatorHeight, mTabPadding, mIndicatorHeight);
        View tab = ((IconTabProvider) mPager.getAdapter()).getView(position);
        view.addView(tab);
        addTab(position, view);
    }

    private void addTab(final int position, View tab) {
        tab.setFocusable(true);
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPager.getAdapter() instanceof IconTabProvider) {
                    ((IconTabProvider) mPager.getAdapter()).onTabClick(position, mPager.getCurrentItem() == position);
                }
                mPager.setCurrentItem(position);
            }
        });
        mTabsContainer.addView(tab, position, mShouldExpand ? mExpandedTabLayoutParams : mDefaultTabLayoutParams);
    }

    private void updateTabStyles() {
        for (int i = 0; i < mTabCount; i++) {
            View v = mTabsContainer.getChildAt(i);
            if (v instanceof TextView) {
                TextView tab = (TextView) v;
                tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabTextSize);
                tab.setTypeface(mTabTypeface, Typeface.BOLD);
                tab.setTextColor(mTabTextColor);

                if (mTextAllCaps) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                        tab.setAllCaps(true);
                    } else {
                        tab.setText(tab.getText().toString().toUpperCase(mLocale));
                    }
                }
            } else if (v instanceof ViewGroup) {
                int numChildView = ((ViewGroup) v).getChildCount();
                View childView;
                for (int j = 0; j < numChildView; j++) {
                    childView = ((ViewGroup) v).getChildAt(j);
                    if (childView instanceof TextView) {
                        TextView tab = (TextView) childView;
                        tab.setTypeface(mTabTypeface, Typeface.BOLD);
                        tab.setTextColor(mTabTextColor);

                        if (mTextAllCaps) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                                tab.setAllCaps(true);
                            } else {
                                tab.setText(tab.getText().toString().toUpperCase(mLocale));
                            }
                        }
                    }
                }
            }
        }

    }

    private void scrollToChild(int position, int offset) {
        if (mTabCount == 0) {
            return;
        }

        int newScrollX = mTabsContainer.getChildAt(position).getLeft() + offset;
        if (position > 0 || offset > 0) {
            newScrollX -= mScrollOffset;
        }

        if (newScrollX != mLastScrollX) {
            mLastScrollX = newScrollX;
            scrollTo(newScrollX, 0);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isInEditMode() || mTabCount == 0) {
            return;
        }

        final int height = getHeight();
        mRectPaint.setColor(mIndicatorColor);
        View currentTab = mTabsContainer.getChildAt(mCurrentPosition);
        float lineLeft = currentTab.getLeft();
        float lineRight = currentTab.getRight();
        // if there is an offset, start interpolating left and right coordinates between current and next tab
        if (mCurrentPositionOffset > 0f && mCurrentPosition < mTabCount - 1) {
            View nextTab = mTabsContainer.getChildAt(mCurrentPosition + 1);
            final float nextTabLeft = nextTab.getLeft();
            final float nextTabRight = nextTab.getRight();
            lineLeft = (mCurrentPositionOffset * nextTabLeft + (1f - mCurrentPositionOffset) * lineLeft);
            lineRight = (mCurrentPositionOffset * nextTabRight + (1f - mCurrentPositionOffset) * lineRight);
        }

        canvas.drawRect(lineLeft, height - mIndicatorHeight, lineRight, height, mRectPaint);
        // draw underline
        mRectPaint.setColor(mUnderlineColor);
        canvas.drawRect(0, height - mUnderlineHeight, mTabsContainer.getWidth(), height, mRectPaint);
        // draw divider
        mDividerPaint.setColor(mDividerColor);
        for (int i = 0; i < mTabCount - 1; i++) {
            View tab = mTabsContainer.getChildAt(i);
            canvas.drawLine(tab.getRight(), mDividerPadding, tab.getRight(), height - mDividerPadding, mDividerPaint);
        }
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        mCurrentPosition = savedState.currentPosition;
        requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPosition = mCurrentPosition;
        return savedState;
    }

    /**
     * handle view item menu
     */
    public interface IconTabProvider {
        View getView(int position);

        void onPageScrollState(int position);

        void onTabClick(int position, boolean isSameTab);

    }

    /**
     * ListenPagerChange
     */
    public interface ListenPagerChange {
        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

        void onPageScrollStateChanged(int state);

        void onPageSelected(int position);
    }

    /**
     * save status pager and item menu
     */
    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        int currentPosition;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            currentPosition = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPosition);
        }
    }

    /**
     * handle viewPager listenr from tabStrip
     */
    private class PageListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mCurrentPosition = position;
            mCurrentPositionOffset = positionOffset;
            scrollToChild(position, (int) (positionOffset * mTabsContainer.getChildAt(position).getWidth()));
            if (mListenPagerChange != null) {
                mListenPagerChange.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
            invalidate();
            if (delegatePageListener != null) {
                delegatePageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                scrollToChild(mPager.getCurrentItem(), 0);
            }
            if (mListenPagerChange != null) {
                mListenPagerChange.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position) {

            if (mPager.getAdapter() instanceof IconTabProvider) {
                ((IconTabProvider) mPager.getAdapter()).onPageScrollState(position);
            }
            View layoutTab;
            int countTabs = mTabsContainer.getChildCount();
            for (int i = 0; i < countTabs; i++) {
                layoutTab = mTabsContainer.getChildAt(i);
                if (layoutTab instanceof ViewGroup) {
                    int countChild = ((ViewGroup) layoutTab).getChildCount();
                    for (int j = 0; j < countChild; j++) {
                        ((ViewGroup) layoutTab).getChildAt(j).setSelected(i == position);
                    }
                }
            }
            if (mListenPagerChange != null) {
                mListenPagerChange.onPageSelected(position);
            }
            if (delegatePageListener != null) {
                delegatePageListener.onPageSelected(position);
            }
        }
    }
}
