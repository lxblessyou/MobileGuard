package com.home.user.mobileguard.activity;

import com.home.user.mobileguard.R;
import com.home.user.mobileguard.Utils.MyContants;
import com.home.user.mobileguard.Utils.SPTools;

/**
 * Created by user on 16-7-3.
 */
public class LostFindSetup4Activity extends LostFindBaseSetupActivity{
    @Override
    protected void initView() {
        setContentView(R.layout.layout_lost_find_setup4);
    }

    @Override
    protected void nextActivity() {
        startOtherActivity(LostFindActivity.class);
        SPTools.putValue(this,MyContants.ISSETUPKEY,"true");
    }

    @Override
    protected void previousActivity() {
        startOtherActivity(LostFindSetup3Activity.class);
    }
}
