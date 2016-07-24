package com.home.user.mobileguard.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.home.user.mobileguard.R;
import com.home.user.mobileguard.Utils.EncryptionTools;
import com.home.user.mobileguard.Utils.MyContants;
import com.home.user.mobileguard.Utils.SPTools;

/**
 * Created by user on 16-7-3.
 */
public class LostFindSetup3Activity extends LostFindBaseSetupActivity {

    private Button btn_select_safe_num;
    private EditText et_safe_num;

    private String safeNum;

    @Override
    protected void initEvent() {
        btn_select_safe_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LostFindSetup3Activity.this,ContactsSelectActivity.class);
                startActivityForResult(intent,0);
            }
        });
    }

    @Override
    protected void initData() {
        safeNum = EncryptionTools.deciphering(MyContants.OFFSET,SPTools.getValue(this, MyContants.SAFENUM, ""));
    }

    @Override
    protected void initView() {
        setContentView(R.layout.layout_lost_find_setup3);

        et_safe_num = (EditText) findViewById(R.id.et_safe_num);
        btn_select_safe_num = (Button) findViewById(R.id.btn_select_safe_num);
        et_safe_num.setText(safeNum);
    }

    @Override
    protected void nextActivity() {
        startOtherActivity(LostFindSetup4Activity.class);
    }

    @Override
    protected void previousActivity() {
        startOtherActivity(LostFindSetup2Activity.class);
    }

    /**
     * 重写父类的下一步方法
     *
     * @param view
     */
    @Override
    public void next(View view) {
        safeNum = et_safe_num.getText().toString().trim();
        if (TextUtils.isEmpty(safeNum)) {
            Toast.makeText(this, "安全号码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        SPTools.putValue(this, MyContants.SAFENUM, EncryptionTools.encryption(MyContants.OFFSET,safeNum));
        super.next(view);
    }

    /**
     * 再次进入页面的回调方法
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            String safeNum = data.getStringExtra(MyContants.SAFENUM);
            et_safe_num.setText(safeNum);
        }
    }
}
