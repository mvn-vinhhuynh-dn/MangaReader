package coder.victorydst3.mangareader.containerFragment;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import coder.victorydst3.mangareader.R;
import coder.victorydst3.mangareader.ui.favorite.FavoriteFragment_;

/**
 * Created by LanTM on 06/10/16
 */
@EFragment(R.layout.container_fragment)
public class FavoriteContainerFragment extends BaseContainerFragment {

    @AfterViews
    protected void afterView() {
        replaceFragment(FavoriteFragment_.builder().build(), false);
    }

}
