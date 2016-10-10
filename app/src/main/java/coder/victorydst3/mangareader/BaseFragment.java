package coder.victorydst3.mangareader;

import android.support.v4.app.Fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import coder.victorydst3.mangareader.containerFragment.BaseContainerFragment;

@EFragment
public abstract class BaseFragment extends Fragment {
    private static final String TAG = BaseFragment.class.getSimpleName();

    @AfterViews
    protected abstract void afterView();

    /**
     * @param fragment:       new fragment for show
     * @param viewRoot:       id view root
     * @param allowBackPress: if allowBackPress= true has even handle back press
     */

    /**
     * Add new child fragment on page
     *
     * @param fragment    new child fragment
     * @param isBackStack true or false
     */
    protected void replaceFragment(Fragment fragment, boolean isBackStack) {
        ((BaseContainerFragment) getParentFragment()).replaceFragment(fragment, isBackStack);
    }

    /**
     * Add new child fragment on page
     *
     * @param fragment    new child fragment
     * @param isBackStack true or false
     */
    protected void replaceViewPagerFragment(Fragment fragment, boolean isBackStack) {
        ((BaseContainerFragment) getParentFragment()).replaceFragment(fragment, isBackStack);
    }

    protected void popFragment() {
        ((BaseContainerFragment) getParentFragment()).popFragment();
    }

}
