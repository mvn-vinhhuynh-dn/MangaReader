package coder.victorydst3.mangareader.ui.menu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import coder.victorydst3.mangareader.R;
import coder.victorydst3.mangareader.model.NavigationDrawerItem;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by VinhHLB on 9/28/16.
 */
public class NavigationDrawerAdapter extends RecyclerView
        .Adapter<NavigationDrawerAdapter.MyViewHolder> {
    private List<NavigationDrawerItem> mDatas = Collections.emptyList();
    private LayoutInflater mInflater;
    private Context mContext;

    NavigationDrawerAdapter(Context context, List<NavigationDrawerItem> data) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDatas = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.nav_drawer_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView imageView;

        MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            imageView = (ImageView) itemView.findViewById(R.id.img_icon);
        }
    }
}
