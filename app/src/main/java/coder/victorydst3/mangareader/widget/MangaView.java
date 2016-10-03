package coder.victorydst3.mangareader.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import coder.victorydst3.mangareader.R;
import coder.victorydst3.mangareader.model.Manga;
import coder.victorydst3.mangareader.util.ScreenUtil;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by VinhHLB on 10/3/16.
 */
@EViewGroup(R.layout.manga_view)
public class MangaView extends LinearLayout {
    @ViewById(R.id.rlContent)
    RelativeLayout mRlContent;

    @ViewById(R.id.imgThumb)
    ImageView mImgConent;

    @ViewById(R.id.tvTitle)
    TextView mTvTitle;

    public MangaView(Context context) {
        super(context);
    }

    public MangaView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MangaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @AfterViews
    void afterViews() {
        setSizeForItem();
    }

    private void setSizeForItem() {
        final int screenWidth = ScreenUtil.getWidthScreen(getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(screenWidth / 3, screenWidth / 3);
        mRlContent.setLayoutParams(layoutParams);
    }

    public void setManga(Manga manga) {
        mTvTitle.setText(manga.getName());
        Glide.with(getContext()).load(manga.getImageUrl()).into(mImgConent);
    }
}
