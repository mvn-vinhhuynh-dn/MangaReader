package coder.victorydst3.mangareader.ui.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import coder.victorydst3.mangareader.BaseAdapter;
import coder.victorydst3.mangareader.OnReadMangaListener;
import coder.victorydst3.mangareader.R;
import coder.victorydst3.mangareader.model.MangaDetail;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by VinhHLB on 9/27/16.
 */

class DetailAdapter extends BaseAdapter<RecyclerView.ViewHolder> {
    /**
     * Enum define for item type
     */
    private enum Type {
        TYPE_HEADER, TYPE_ITEM
    }

    private final Context mContext;
    private MangaDetail mMangaDetail;
    private final OnReadMangaListener mOnReadMangaListener;

    DetailAdapter(@NonNull Context context, MangaDetail mangaDetail, OnReadMangaListener onReadMangaListener) {
        super(context);
        mContext = context;
        mMangaDetail = mangaDetail;
        mOnReadMangaListener = onReadMangaListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Type.TYPE_HEADER.ordinal()) {
            // Inflate your layout and pass it to view holder
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_header_detail, parent, false);
            return new HeaderViewHolder(view);
        } else if (viewType == Type.TYPE_ITEM.ordinal()) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_title_detail, parent, false);
            return new ItemViewHolder(view, mOnReadMangaListener, mMangaDetail);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.mTvNumChap.setText(mContext.getString(R.string.numChap, mMangaDetail.getManga().getNewChap()));
            headerViewHolder.mTvNumRead.setText(mContext.getString(R.string.num_read, mMangaDetail.getManga().getNumRead()));

            if (mMangaDetail.getManga().getCategories() == null || mMangaDetail.getManga().getCategories().size() == 0) {
                headerViewHolder.mTvCategory.setVisibility(View.GONE);
            } else {
                headerViewHolder.mTvCategory.setVisibility(View.VISIBLE);
                String category = "";
                for (int i = 0; i < mMangaDetail.getManga().getCategories().size(); i++) {
                    category += mMangaDetail.getManga().getCategories().get(i) + ", ";
                }
                category = category.substring(0, category.length() - 2);
                headerViewHolder.mTvCategory.setText(mContext.getString(R.string.category, category));
            }

            headerViewHolder.mTvStatus.setText(mContext.getString(R.string.status, mMangaDetail.getManga().getStatus()));
            headerViewHolder.mTvName.setText(mMangaDetail.getManga().getName());
            Glide.with(mContext).load(mMangaDetail.getManga().getImageUrl()).into(headerViewHolder.mImgContent);
        } else if (holder instanceof ItemViewHolder) {
            Log.d("VVVV", "onBindViewHolder: 2" + position);
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.mTvContent.setText(mMangaDetail.getChapters().get(position - 1).getTitle());
        }
    }

    @Override
    public int getItemCount() {
        if (mMangaDetail == null || mMangaDetail.getChapters() == null) {
            return 0;
        }
        return mMangaDetail.getChapters().size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return Type.TYPE_HEADER.ordinal();
        }
        return Type.TYPE_ITEM.ordinal();
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTvName;
        private final TextView mTvNumRead;
        private final TextView mTvNumChap;
        private final TextView mTvStatus;
        private final TextView mTvCategory;
        private final ImageView mImgContent;

        HeaderViewHolder(View view) {
            super(view);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvNumRead = (TextView) itemView.findViewById(R.id.tvNumread);
            mTvStatus = (TextView) itemView.findViewById(R.id.tvStatus);
            mTvCategory = (TextView) itemView.findViewById(R.id.tvCategory);
            mTvNumChap = (TextView) itemView.findViewById(R.id.tvNumChap);
            mImgContent = (ImageView) itemView.findViewById(R.id.imgThumb);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTvContent;

        ItemViewHolder(View view, final OnReadMangaListener onReadMangaListener, final MangaDetail mangaDetail) {
            super(view);
            mTvContent = (TextView) itemView.findViewById(R.id.tvContent);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onReadMangaListener.onReadMangaClick(mangaDetail.getChapters().get(getLayoutPosition() - 1).getProcessUrl());
                }
            });
        }
    }
}