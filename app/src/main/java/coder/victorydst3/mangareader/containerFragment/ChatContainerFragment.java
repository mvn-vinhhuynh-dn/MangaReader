package coder.victorydst3.mangareader.containerFragment;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import coder.victorydst3.mangareader.R;

/**
 * Created by TienLQ on 6/2/16
 */
@EFragment(R.layout.container_fragment)
public class ChatContainerFragment extends BaseContainerFragment {

    @AfterViews
    protected void afterView() {
//        replaceFragment(ChatFragment_.builder().build(), false);
    }

}
