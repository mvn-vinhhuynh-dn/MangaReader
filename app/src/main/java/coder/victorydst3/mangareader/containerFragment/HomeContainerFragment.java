package coder.victorydst3.mangareader.containerFragment;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import coder.victorydst3.mangareader.R;
import coder.victorydst3.mangareader.ui.home.HomeFragment_;

/**
 * Created by lantm
 */
@EFragment(R.layout.container_fragment)
public class HomeContainerFragment extends BaseContainerFragment {

    @AfterViews
    protected void afterView() {
        replaceFragment(HomeFragment_.builder().build(), false);
    }

}
