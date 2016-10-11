package coder.victorydst3.mangareader;

import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;


import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Created by lantm-mac-air on 3/29/16
 *
 * @param <A> You can use any class
 */
@EFragment
public abstract class BaseListFragment<A extends BaseAdapter> extends BaseFragment {

    private static final String TAG = BaseListFragment.class.getSimpleName();

    protected boolean mFinished;
    protected int mPage = 1;
    @Getter
    @Accessors(prefix = "m")
    protected A mAdapter;

    @ViewById(R.id.swipeRefreshLayout)
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    @ViewById(R.id.recyclerView)
    protected RecyclerView mRecyclerView;

    @ViewById(R.id.progressBar)
    protected ProgressBar mProgressBar;

    protected boolean mIsLoading;

    protected abstract A initAdapter();

    protected abstract RecyclerView.LayoutManager getLayoutManager();

    @Override
    protected void afterView() {
        initRecyclerView();
    }

    public void initRecyclerView() {
        mAdapter = initAdapter();
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new BaseScrollListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if (canLoadMore()) {
                    BaseListFragment.this.onLoadMore();
                }
            }
        });

        if (mSwipeRefreshLayout == null) {
            return;
        }
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(),
                R.color.colorPrimary));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mIsLoading) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    return;
                }
                BaseListFragment.this.onRefresh();
            }
        });
    }

    /**
     * This method is used for load more
     */
    protected void onLoadMore() {
    }


    protected void onRefresh() {
        onResetParams();
    }

    /**
     * This method is used  when start call api
     */
    protected void onPreRequest(boolean isRefresh) {
        mIsLoading = true;
        if (isRefresh) {
            if (mSwipeRefreshLayout != null) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                    }
                });
            }
        } else {
            displayLoadMore(true);
        }
    }

    /**
     * This method is used  when finish call api
     */
    protected void onPostRequest() {
        mIsLoading = false;
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        displayLoadMore(false);
    }

    /**
     * This method is reset param after pull to refresh
     */
    private void onResetParams() {
        mPage = 1;
        mFinished = false;
    }

    /**
     * This method is used to check can load more data
     */
    private boolean canLoadMore() {
        return !mIsLoading && mFinished;
    }

    /**
     * This method is used to add progressbar in bottom when load more data
     */
    private void displayLoadMore(boolean display) {
        if (getAdapter() instanceof LoadMoreAdapter) {
            ((LoadMoreAdapter) getAdapter()).displayLoadMore(display);
            try {
                getAdapter().notifyItemChanged(getAdapter().getItemCount() - 1);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

}
