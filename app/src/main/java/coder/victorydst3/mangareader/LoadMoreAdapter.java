package coder.victorydst3.mangareader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


/**
 * Created by lantm-mac-air on 3/29/16
 * <p/>
 * Class this use for add row item loading when load more
 */
public class LoadMoreAdapter extends BaseAdapter {

    private final BaseAdapter mAdapter;
    private boolean mProgressBarVisible;
    private final static int LOAD_MORE_TYPE = 99;

    public LoadMoreAdapter(@NonNull Context context, @NonNull BaseAdapter adapter) {
        super(context);
        mAdapter = adapter;
    }

    public void displayLoadMore(boolean progressBarVisible) {
        mProgressBarVisible = progressBarVisible;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LOAD_MORE_TYPE) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_load_more, parent, false);
            return new LoadMoreHolder(view);
        } else {
            return mAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == LOAD_MORE_TYPE) {
            // type load more
            onBindLoadMoreHolder((LoadMoreHolder) holder);
        } else {
            //// TODO: i will fix it later
            mAdapter.onBindViewHolder(holder, position);
        }
    }

    private void onBindLoadMoreHolder(LoadMoreHolder holder) {
        holder.mProgressBar.setVisibility(mProgressBarVisible ? View.VISIBLE : View.GONE);
    }

    /**
     * Count + 1 for add view at bottom
     */
    @Override
    public int getItemCount() {
        return mAdapter == null ? 0 : mAdapter.getItemCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            // last item
            return LOAD_MORE_TYPE;
        }
        return mAdapter.getItemViewType(position) ;
    }

    /**
     * A holder describes an item view and metadata about its place within the RecyclerView.
     */
    private class LoadMoreHolder extends RecyclerView.ViewHolder {

        private final ProgressBar mProgressBar;

        public LoadMoreHolder(View itemView) {
            super(itemView);
            if (itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams layoutParams =
                        (StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams();
                layoutParams.setFullSpan(true);
            }
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }

}
