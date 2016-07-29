package com.home.user.mobileguard.activity;

import com.home.user.mobileguard.R;

/**
 * Created by user on 16-7-2.
 */
public class LostFindSetup1Activity extends LostFindBaseActivity {

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setContentView(R.layout.layout_lost_find_setup1);
    }

    @Override
    protected void nextActivity() {
        //调用父类方法跳转Activity
        startOtherActivity(LostFindSetup2Activity.class);
    }

    @Override
    protected void previousActivity() {

    }
}
