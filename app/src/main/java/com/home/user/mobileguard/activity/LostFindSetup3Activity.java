package com.home.user.mobileguard.activity;

import com.home.user.mobileguard.R;

/**
 * Created by user on 16-7-3.
 */
public class LostFindSetup3Activity extends LostFindBaseSetupActivity{
    @Override
    protected void initView() {
        setContentView(R.layout.layout_lost_find_setup3);
    }

    @Override
    protected void nextActivity() {
        startOtherActivity(LostFindSetup4Activity.class);
    }

    @Override
    protected void previousActivity() {
        startOtherActivity(LostFindSetup2Activity.class);
    }
}
