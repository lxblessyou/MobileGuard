package com.home.user.mobileguard.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.user.mobileguard.R;
import com.home.user.mobileguard.Utils.MyContants;
import com.home.user.mobileguard.Utils.SPTools;

/**
 * Created by user on 16-7-2.
 */
public class LostFindActivity extends LostFindBaseSetupActivity {
    private ImageView iv_lost_find_lock;
    private TextView tv_lost_find_safe_num;

    private String isProtected;
    private String safeNum;

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        isProtected = SPTools.getValue(this, MyContants.ISPROTECTED, null);
        safeNum = SPTools.getValue(this, MyContants.SAFENUM, null);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.layout_lost_find);

        iv_lost_find_lock = (ImageView) findViewById(R.id.iv_lost_find_lock);
        tv_lost_find_safe_num = (TextView) findViewById(R.id.tv_lost_find_safe_num);

        if ("true".equals(isProtected)&& iv_lost_find_lock != null) {
            iv_lost_find_lock.setImageResource(R.drawable.lock);
        }
        else {
            iv_lost_find_lock.setImageResource(R.drawable.unlock);
        }
        if (safeNum != null && tv_lost_find_safe_num != null) {
            tv_lost_find_safe_num.append("    " + safeNum);
        }
    }

    @Override
    protected void nextActivity() {

    }

    @Override
    protected void previousActivity() {

    }

    public void reset(View view) {
//        SPTools.clearData(this);
        Intent intent = new Intent(this, LostFindSetup1Activity.class);
        startActivity(intent);
        finish();
    }
}
