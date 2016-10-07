package coder.victorydst3.mangareader.containerFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import coder.victorydst3.mangareader.R;


public class BaseContainerFragment extends Fragment {


    /**
     * Add new child fragment on page
     *
     * @param fragment       new child fragment
     * @param addToBackStack true or false
     */
    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getChildFragmentManager()
                .beginTransaction();
        if (!addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.replace(R.id.container_framelayout, fragment);
        transaction.commitAllowingStateLoss();
    }

    public boolean popFragment() {
        boolean isPop = false;
        FragmentManager fm = getChildFragmentManager();
        if (fm.getBackStackEntryCount() > 1) {
            isPop = true;
            fm.popBackStack();
        }
        return isPop;
    }

    public Fragment getCurrentFragment() {
        return getChildFragmentManager().findFragmentById(
                R.id.container_framelayout);
    }

    /**
     * @return count in backtrack
     */
    public int getCountBackStack() {
        return getChildFragmentManager().getBackStackEntryCount();
    }

}
