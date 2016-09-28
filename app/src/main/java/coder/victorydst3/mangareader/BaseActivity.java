package coder.victorydst3.mangareader;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by VinhHLB on 9/27/16.
 */

public class BaseActivity extends AppCompatActivity {

    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!addToBackStack) {
            transaction.addToBackStack(fragment.getTag());
        }
        transaction.replace(R.id.mainContainer, fragment);
        transaction.commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    public Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.mainContainer);
    }

}
