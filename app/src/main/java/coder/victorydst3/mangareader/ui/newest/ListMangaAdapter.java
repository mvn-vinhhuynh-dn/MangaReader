package coder.victorydst3.mangareader.ui.newest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import coder.victorydst3.mangareader.model.Manga;
import coder.victorydst3.mangareader.ui.slide.SlideView;
import coder.victorydst3.mangareader.ui.slide.SlideView_;
import coder.victorydst3.mangareader.widget.MangaView;
import coder.victorydst3.mangareader.widget.MangaView_;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by VinhHLB on 9/27/16.
 */

class ListMangaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    enum Type {
        TYPE_CAMPAIGN, TYPE_ITEM
    }

    private static final int ITEM_OFFSET = 1;
    private final Context mContext;
    private List<Manga> mData = new ArrayList<>();
    private final OnItemMangaClickListenner mOnItemMangaClickListenner;

    ListMangaAdapter(Context context, List<Manga> data, OnItemMangaClickListenner onItemMangaClickListenner) {
        mContext = context;
        mData = data;
        mOnItemMangaClickListenner = onItemMangaClickListenner;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Type.TYPE_ITEM.ordinal()) {
            MangaView itemView = MangaView_.build(mContext);
            return new TopViewHolder(itemView, mOnItemMangaClickListenner);
        } else if (viewType == Type.TYPE_CAMPAIGN.ordinal()) {
            SlideView slideView = SlideView_.build(mContext);
            return new CampaignViewHolder(slideView);
        }
        throw new RuntimeException("There is no type that matches the type "
                + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TopViewHolder) {
            Manga manga = mData.get(position - ITEM_OFFSET);
            if (manga != null) {
                ((MangaView) holder.itemView).setManga(manga);
            }
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return Type.TYPE_CAMPAIGN.ordinal();
        }
        return Type.TYPE_ITEM.ordinal();
    }

    @Override
    public int getItemCount() {
        return mData.size() + ITEM_OFFSET;
    }

    private class TopViewHolder extends RecyclerView.ViewHolder {
        TopViewHolder(View itemView, final OnItemMangaClickListenner onItemMangaClickListenner) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemMangaClickListenner.onItemClick(getAdapterPosition());
                }
            });
        }
    }

    private class CampaignViewHolder extends RecyclerView.ViewHolder {
        CampaignViewHolder(View itemView) {
            super(itemView);
        }
    }

    interface OnItemMangaClickListenner {
        void onItemClick(int position);
    }
}
