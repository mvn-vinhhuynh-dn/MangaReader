package coder.victorydst3.mangareader.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import coder.victorydst3.mangareader.R;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by VinhHLB on 10/3/16.
 */
@EViewGroup(R.layout.tool_bar_custom)
public class HeaderBar extends Toolbar {
    @ViewById(R.id.tvHeaderTitle)
    TextView mTvTitle;

    public HeaderBar(Context context) {
        super(context);
    }

    public HeaderBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setToolBarTitle(String title) {
        mTvTitle.setText(title);
    }
}
