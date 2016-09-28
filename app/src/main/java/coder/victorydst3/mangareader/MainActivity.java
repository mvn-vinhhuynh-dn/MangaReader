package coder.victorydst3.mangareader;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import coder.victorydst3.mangareader.ui.menu.DrawerFragment;
import coder.victorydst3.mangareader.ui.menu.DrawerFragment_;
import coder.victorydst3.mangareader.ui.newest.NewestFragment_;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity implements DrawerFragment
        .FragmentDrawerListener {
    private DrawerFragment mDrawerFragment;

    @ViewById(R.id.toolbar)
    Toolbar mToolBar;

    @AfterViews
    void afterViews() {
        setSupportActionBar(mToolBar);
        initView();
        initListener();
        replaceFragment(NewestFragment_.builder().build(), false);
    }

    private void initView() {
        mDrawerFragment = (DrawerFragment_)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        mDrawerFragment.setUp(R.id.fragment_navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), mToolBar);
    }

    private void initListener() {
        mDrawerFragment.setDrawerListener(this);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
//        Fragment fragment = null;
//        String title = getString(R.string.app_name);
//        switch (position) {
//            case 0:
//                fragment = new HomeFragment_();
//                title = getString(R.string.title_home);
//                break;
//            default:
//                break;
//        }
//        keepHomeFragment();
//        if (fragment != null) {
//            replaceFragment(fragment, false);
//            setTitle(title);
//        }
    }

    private void keepHomeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
                if (i > 0) {
                    getSupportFragmentManager().popBackStack();
                }
            }
        }
    }
}
