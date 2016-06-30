package com.home.user.mobileguard.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.home.user.mobileguard.R;
import com.home.user.mobileguard.Utils.HomeAdapter;
import com.home.user.mobileguard.Utils.MyContants;
import com.home.user.mobileguard.Utils.SPTools;

/**
 * Created by user on 2016-06-26.
 */
public class HomeActivity extends Activity implements View.OnClickListener {
    //View
    private GridView gv_home_function;
    private AlertDialog ad_pd;

    //GridView资源
    private int icons[] = {R.drawable.safe, R.drawable.callmsgsafe, R.drawable.app
            , R.drawable.taskmanager, R.drawable.netmanager, R.drawable.trojan
            , R.drawable.sysoptimize, R.drawable.atools, R.drawable.settings};
    private String names[] = {"手机防盗", "通讯卫士", "软件管家",
            "进程管理", "流量统计", "病毒查杀",
            "缓存清理", "高级工具", "设置中心"};
    private EditText et_dialog_pdsetting_confirm;
    private EditText et_dialog_pdsetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView(); //初始化View

        initEvent();  //初始化事件
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        gv_home_function.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        //弹出手机防盗对话框
                        showPasswordDialog();
                        break;
                }
            }
        });
    }

    /**
     * 设置密码对话框
     */
    private void showPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        View view = View.inflate(this, R.layout.layout_dialog_pd, null);
        et_dialog_pdsetting = (EditText) view.findViewById(R.id.et_dialog_pdsetting);
        et_dialog_pdsetting_confirm = (EditText) view.findViewById(R.id.et_dialog_pdsetting_confirm);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_setting_cancel);
        Button btn_confirm = (Button) view.findViewById(R.id.btn_setting_confirm);
        btn_cancel.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
        builder.setView(view);
        ad_pd = builder.create();
        ad_pd.show();
    }

    /**
     * 初始化View
     */
    private void initView() {
        gv_home_function = (GridView) findViewById(R.id.gv_home_function);
        gv_home_function.setAdapter(new HomeAdapter(HomeActivity.this, icons, names));
    }

    /**
     * 单击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_setting_cancel:
                ad_pd.cancel();
                break;
            case R.id.btn_setting_confirm:
                String password = et_dialog_pdsetting.getText().toString().trim();
                String passwordConfirm = et_dialog_pdsetting_confirm.getText().toString().trim();
                if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(passwordConfirm)) {
                    if (password.equals(passwordConfirm)) {
                        SPTools.putValue(this, MyContants.PDKEY, password);
                        ad_pd.cancel();
                    } else {
                        Toast.makeText(this, "两次密码不同", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
