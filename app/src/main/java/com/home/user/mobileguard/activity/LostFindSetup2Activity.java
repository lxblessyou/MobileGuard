package com.home.user.mobileguard.activity;

import android.app.Activity;

import com.home.user.mobileguard.R;

/**
 * Created by user on 16-7-3.
 */
public class LostFindSetup2Activity extends LostFindBaseSetupActivity{
    @Override
    protected void initView() {
        setContentView(R.layout.layout_lost_find_setup2);
    }

    @Override
    protected void nextActivity() {
        startOtherActivity(LostFindSetup3Activity.class);
    }

    @Override
    protected void previousActivity() {
        startOtherActivity(LostFindSetup1Activity.class);
    }
}
