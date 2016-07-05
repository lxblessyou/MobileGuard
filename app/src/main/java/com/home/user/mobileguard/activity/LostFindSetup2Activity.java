package com.home.user.mobileguard.activity;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.home.user.mobileguard.R;
import com.home.user.mobileguard.Utils.MyContants;
import com.home.user.mobileguard.Utils.SPTools;

/**
 * Created by user on 16-7-3.
 */
public class LostFindSetup2Activity extends LostFindBaseSetupActivity{
    private RelativeLayout rl_setup2_bind;
    private ImageView iv_setup2_bind;

    private String spSimNum;
    private String phoneSimNum;
    @Override
    protected void initEvent() {
        rl_setup2_bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(spSimNum)) {
                    SPTools.putValue(LostFindSetup2Activity.this, MyContants.SIMNUM, phoneSimNum);

                    iv_setup2_bind.setBackgroundResource(R.drawable.lock);
                    spSimNum = phoneSimNum;
                } else {
                    spSimNum = null;
                    SPTools.putValue(LostFindSetup2Activity.this, MyContants.SIMNUM, null);

                    iv_setup2_bind.setBackgroundResource(R.drawable.unlock);
                }
            }
        });
    }

    @Override
    protected void initData() {
        spSimNum = SPTools.getValue(this, MyContants.SIMNUM,null);

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        phoneSimNum = tm.getSimSerialNumber();

        if (TextUtils.isEmpty(spSimNum)) {
            iv_setup2_bind.setBackgroundResource(R.drawable.unlock);
        } else {
            iv_setup2_bind.setBackgroundResource(R.drawable.lock);
        }
    }

    @Override
    public void next(View view) {
        if (TextUtils.isEmpty(spSimNum)) {
            Toast.makeText(this,"请绑定SIM卡！",Toast.LENGTH_LONG).show();
            return;
        }
        super.next(view);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.layout_lost_find_setup2);

        rl_setup2_bind = (RelativeLayout) findViewById(R.id.rl_setup2_bind);
        iv_setup2_bind = (ImageView) findViewById(R.id.iv_setup2_bind);
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
