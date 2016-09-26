package coder.victorydst3.mangareader;

import android.app.Activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {
    public int A;
    public int B;
    @AfterViews
    void afterViews(){
    }
}
