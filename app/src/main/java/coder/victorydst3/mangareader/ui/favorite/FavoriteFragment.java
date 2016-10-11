package coder.victorydst3.mangareader.ui.favorite;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

import coder.victorydst3.mangareader.BaseFragment;
import coder.victorydst3.mangareader.R;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by VinhHLB on 10/3/16.
 */
@EFragment(R.layout.fragment_favorite)
public class FavoriteFragment extends BaseFragment {
    @FragmentArg
    String mFragmentTag;

    @Override
    protected void afterView() {

    }
}
