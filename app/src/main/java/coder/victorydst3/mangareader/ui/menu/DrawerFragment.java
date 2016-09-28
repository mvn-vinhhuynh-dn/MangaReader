package coder.victorydst3.mangareader.ui.menu;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by VinhHLB on 9/28/16.
 */

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import coder.victorydst3.mangareader.BaseFragment;
import coder.victorydst3.mangareader.R;
import coder.victorydst3.mangareader.model.NavigationDrawerItem;

/**
 * Copyright © 2015 AsianTech inc.
 * Created by VinhHlb on 10/6/15.
 */
@EFragment(R.layout.fragment_drawer)
public class DrawerFragment extends BaseFragment {
    @ViewById(R.id.recycler)
    RecyclerView mRecycleView;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter mAdapter;
    private View mContainView;
    private static String[] mTitles = null;
    private FragmentDrawerListener drawerListener;

    @AfterViews
    public void afterView() {
        initView();
        initData();
        initListener();
        setAdapter();
    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    private void initView() {
        //DO nothing
    }

    private void initData() {
        mTitles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);
        mAdapter = new NavigationDrawerAdapter(getActivity(), getData());
    }

    private void initListener() {
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                mRecycleView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                drawerListener.onDrawerItemSelected(view, position);
                mDrawerLayout.closeDrawer(mContainView);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    public boolean isShowing() {
        return mDrawerLayout.isDrawerOpen(mContainView);
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(mContainView);
    }

    private void setAdapter() {
        mAdapter = new NavigationDrawerAdapter(getActivity(), getData());
        mRecycleView.setAdapter(mAdapter);
    }

    public static List<NavigationDrawerItem> getData() {
        List<NavigationDrawerItem> data = new ArrayList<>();
        // preparing navigation drawer items
        for (String mTitle : mTitles) {
            NavigationDrawerItem navItem = new NavigationDrawerItem();
            navItem.setTitle(mTitle);
            data.add(navItem);
        }
        return data;
    }
    public void notifyDatasetChange(){
        mAdapter.notifyDataSetChanged();
    }
    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        mContainView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),
                drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        RecyclerTouchListener(Context context,
                              final RecyclerView recyclerView,
                              final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context,
                    new GestureDetector.SimpleOnGestureListener() {
                        @Override
                        public boolean onSingleTapUp(MotionEvent e) {
                            return true;
                        }

                        @Override
                        public void onLongPress(MotionEvent e) {
                            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                            if (child != null && clickListener != null) {
                                clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                            }
                        }
                    });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public interface FragmentDrawerListener {
        void onDrawerItemSelected(View view, int position);
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }
}
