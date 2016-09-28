package coder.victorydst3.mangareader.ui.newest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import coder.victorydst3.mangareader.R;
import coder.victorydst3.mangareader.model.Manga;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by VinhHLB on 9/27/16.
 */

public class ListMangaAdapter extends RecyclerView.Adapter<ListMangaAdapter.TopViewHolder> {
    private final Context mContext;
    private List<Manga> mData = new ArrayList<>();
    private final OnItemMangaClickListenner mOnItemMangaClickListenner;

    public ListMangaAdapter(Context context, List<Manga> data, OnItemMangaClickListenner onItemMangaClickListenner) {
        mContext = context;
        mData = data;
        mOnItemMangaClickListenner = onItemMangaClickListenner;
    }

    @Override
    public TopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_manga, parent, false);
        return new TopViewHolder(view, mOnItemMangaClickListenner);
    }

    @Override
    public void onBindViewHolder(TopViewHolder holder, int position) {
        Manga manga = mData.get(position);
        if (manga.getAuthor() == null || manga.getAuthor().size() == 0) {
            holder.mTvAuthor.setVisibility(View.GONE);
        } else {
            holder.mTvAuthor.setVisibility(View.VISIBLE);
            String author = "";
            for (int i = 0; i < manga.getAuthor().size(); i++) {
                author += " " + manga.getAuthor().get(i) + ", ";
            }
            author = author.substring(0, author.length() - 2);
            holder.mTvAuthor.setText(mContext.getString(R.string.author, author));
        }
        if (manga.getCategories() == null || manga.getCategories().size() == 0) {
            holder.mTvCategory.setVisibility(View.GONE);
        } else {
            holder.mTvCategory.setVisibility(View.VISIBLE);
            String category = "";
            for (int i = 0; i < manga.getCategories().size(); i++) {
                category += manga.getCategories().get(i) + ", ";
            }
            category = category.substring(0, category.length() - 2);
            holder.mTvCategory.setText(mContext.getString(R.string.category, category));
        }
        holder.mTvDateUpdate.setText(mContext.getString(R.string.date_update, manga.getDateUpdate()));
        holder.mTvNewChap.setText(mContext.getString(R.string.new_chap, manga.getNewChap()));
        holder.mTvNumRead.setText(mContext.getString(R.string.num_read, manga.getNumRead()));
        holder.mTvName.setText(manga.getName());
        Glide.with(mContext).load(manga.getImageUrl()).into(holder.mImgContent);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class TopViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTvName;
        private final TextView mTvAuthor;
        private final TextView mTvDateUpdate;
        private final TextView mTvNewChap;
        private final TextView mTvNumRead;
        private final TextView mTvCategory;

        private final ImageView mImgContent;

        TopViewHolder(View itemView, final OnItemMangaClickListenner onItemMangaClickListenner) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvAuthor = (TextView) itemView.findViewById(R.id.tvAuthor);
            mTvDateUpdate = (TextView) itemView.findViewById(R.id.tvUpdate);
            mTvNewChap = (TextView) itemView.findViewById(R.id.tvNewChap);
            mTvNumRead = (TextView) itemView.findViewById(R.id.tvNumRead);
            mTvCategory = (TextView) itemView.findViewById(R.id.tvCategory);
            mImgContent = (ImageView) itemView.findViewById(R.id.imgContent);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemMangaClickListenner.onItemClick(getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemMangaClickListenner {
        void onItemClick(int position);
    }
}
