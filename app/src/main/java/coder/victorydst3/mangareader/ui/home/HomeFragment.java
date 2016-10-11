package coder.victorydst3.mangareader.ui.home;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

import coder.victorydst3.mangareader.BaseFragment;
import coder.victorydst3.mangareader.R;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by VinhHLB on 9/27/16.
 */

@EFragment(R.layout.fragment_home)
public class HomeFragment extends BaseFragment {
    @FragmentArg
    String mFragmentTag;

    @Override
    protected void afterView() {

    }
}
