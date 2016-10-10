package coder.victorydst3.mangareader.containerFragment;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import coder.victorydst3.mangareader.R;
import coder.victorydst3.mangareader.ui.newest.NewestFragment_;

/**
 * Created by TienLQ on 6/2/16
 */
@EFragment(R.layout.container_fragment)
public class NewestContainerFragment extends BaseContainerFragment {

    @AfterViews
    protected void afterView() {
        replaceFragment(NewestFragment_.builder().build(), false);
    }

}
