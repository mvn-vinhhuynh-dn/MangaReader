package coder.victorydst3.mangareader.ui.newest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import coder.victorydst3.mangareader.model.Manga;
import coder.victorydst3.mangareader.widget.MangaView;
import coder.victorydst3.mangareader.widget.MangaView_;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by VinhHLB on 9/27/16.
 */

class ListMangaAdapter extends RecyclerView.Adapter<ListMangaAdapter.TopViewHolder> {
    private final Context mContext;
    private List<Manga> mData = new ArrayList<>();
    private final OnItemMangaClickListenner mOnItemMangaClickListenner;

    ListMangaAdapter(Context context, List<Manga> data, OnItemMangaClickListenner onItemMangaClickListenner) {
        mContext = context;
        mData = data;
        mOnItemMangaClickListenner = onItemMangaClickListenner;
    }

    @Override
    public TopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MangaView itemView = MangaView_.build(mContext);
        return new TopViewHolder(itemView, mOnItemMangaClickListenner);
    }

    @Override
    public void onBindViewHolder(TopViewHolder holder, int position) {
        Manga manga = mData.get(position);
        if(manga!=null){
            ((MangaView)holder.itemView).setManga(manga);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class TopViewHolder extends RecyclerView.ViewHolder {

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

    interface OnItemMangaClickListenner {
        void onItemClick(int position);
    }
}
