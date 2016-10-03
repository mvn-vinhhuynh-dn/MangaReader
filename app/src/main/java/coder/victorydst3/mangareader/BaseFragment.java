package coder.victorydst3.mangareader;

import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by Thang Truong on 1/6/16.
 */
public class BaseFragment extends Fragment {
    private Activity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mActivity = activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnBaseActivityListener");
        }
    }
}
