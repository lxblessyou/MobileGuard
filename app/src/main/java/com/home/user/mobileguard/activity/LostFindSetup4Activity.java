package com.home.user.mobileguard.activity;

import android.content.Intent;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.home.user.mobileguard.R;
import com.home.user.mobileguard.Utils.MyContants;
import com.home.user.mobileguard.Utils.SPTools;
import com.home.user.mobileguard.service.LostFindService;

/**
 * Created by user on 16-7-3.
 */
public class LostFindSetup4Activity extends LostFindBaseSetupActivity {
    private CheckBox cb_setup4_is_protected;
    private String isProtected = "false";

    @Override
    protected void initEvent() {
        isProtected = String.valueOf(cb_setup4_is_protected.isChecked());
        cb_setup4_is_protected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent intent = new Intent(LostFindSetup4Activity.this,LostFindService.class);
                if (isProtected.equals("true")) {
                    startService(intent);
                } else {
                    stopService(intent);
                }
                SPTools.putValue(LostFindSetup4Activity.this,MyContants.ISPROTECTED, isProtected);
            }
        });
    }

    @Override
    protected void initData() {
        isProtected = SPTools.getValue(LostFindSetup4Activity.this,MyContants.ISPROTECTED, "false");
    }

    @Override
    protected void initView() {
        setContentView(R.layout.layout_lost_find_setup4);

        cb_setup4_is_protected = (CheckBox) findViewById(R.id.cb_setup4_is_protected);
        cb_setup4_is_protected.setChecked(isProtected.equals("true"));
    }

    @Override
    protected void nextActivity() {
        startOtherActivity(LostFindActivity.class);
        SPTools.putValue(this, MyContants.ISSETUPKEY, "true");
    }

    @Override
    protected void previousActivity() {
        startOtherActivity(LostFindSetup3Activity.class);
    }
}
