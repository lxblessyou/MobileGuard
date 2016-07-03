package com.home.user.mobileguard.activity;

import android.app.Activity;
import android.content.Intent;
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
import com.home.user.mobileguard.Utils.MD5Util;
import com.home.user.mobileguard.Utils.MyContants;
import com.home.user.mobileguard.Utils.SPTools;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by user on 2016-06-26.
 */
public class HomeActivity extends Activity implements View.OnClickListener {
    //View
    private GridView gv_home_function;

    private AlertDialog ad_set_pw;
    private EditText et_dialog_pdsetting_confirm;
    private EditText et_dialog_pdsetting;

    private AlertDialog ad_input_pw;
    private EditText et_dialog_input_confirm;

    //GridView资源
    private int icons[] = {R.drawable.safe, R.drawable.callmsgsafe, R.drawable.app
            , R.drawable.taskmanager, R.drawable.netmanager, R.drawable.trojan
            , R.drawable.sysoptimize, R.drawable.atools, R.drawable.settings};
    private String names[] = {"手机防盗", "通讯卫士", "软件管家",
            "进程管理", "流量统计", "病毒查杀",
            "缓存清理", "高级工具", "设置中心"};
    private String spMD5Pw;

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
                        if (TextUtils.isEmpty(SPTools.getValue(HomeActivity.this, MyContants.PDKEY, null))) {
                            //弹出密码设置对话框
                            showSetPwDialog();
                        } else {
                            //弹出确认密码对话框
                            showConfirmPwDialog();
                        }
                        break;
                }
            }
        });
    }

    /**
     * 确认密码对话框
     */
    private void showConfirmPwDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        View view = View.inflate(this, R.layout.layout_dialog_input_pd, null);
        et_dialog_input_confirm = (EditText) view.findViewById(R.id.et_dialog_input_confirm);
        Button btn_input_cancel = (Button) view.findViewById(R.id.btn_input_cancel);
        Button btn_input_confirm = (Button) view.findViewById(R.id.btn_input_confirm);
        btn_input_cancel.setOnClickListener(this);
        btn_input_confirm.setOnClickListener(this);
        builder.setView(view);
        ad_input_pw = builder.create();
        ad_input_pw.show();
    }

    /**
     * 设置密码对话框
     */
    private void showSetPwDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        View view = View.inflate(this, R.layout.layout_dialog_set_pd, null);
        et_dialog_pdsetting = (EditText) view.findViewById(R.id.et_dialog_pdsetting);
        et_dialog_pdsetting_confirm = (EditText) view.findViewById(R.id.et_dialog_pdsetting_confirm);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_setting_cancel);
        Button btn_confirm = (Button) view.findViewById(R.id.btn_setting_confirm);
        btn_cancel.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
        builder.setView(view);
        ad_set_pw = builder.create();
        ad_set_pw.show();
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
        String password = null;
        switch (v.getId()) {
            case R.id.btn_setting_cancel:
                ad_set_pw.cancel();
                break;
            case R.id.btn_setting_confirm:
                setPasswordConfirm();
                break;
            case R.id.btn_input_confirm:
                inputPasswordConfirm();
                break;
            case R.id.btn_input_cancel:
                ad_input_pw.cancel();
                break;
        }
    }

    /**
     * 手机防盗确认密码
     */
    private void inputPasswordConfirm() {
        String password = et_dialog_input_confirm.getText().toString().trim();
        if (password != null) {
            try {
                spMD5Pw = SPTools.getValue(this, MyContants.PDKEY, null);
                if (spMD5Pw != null && spMD5Pw.equals(MD5Util.md5Digest(password))) {
                    Toast.makeText(this, "登陆成功", Toast.LENGTH_LONG).show();
                    ad_input_pw.dismiss();
                    //判断是直接进入LostFindActivity还是先进入引导步骤
                    intoLostFind();
                } else {
                    Toast.makeText(this,"密码错误,请重输!!!",Toast.LENGTH_LONG).show();
                    et_dialog_input_confirm.setText("");
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断是直接进入LostFindActivity还是先进入引导步骤
     */
    private void intoLostFind() {
        String isSetup = SPTools.getValue(this, MyContants.ISSETUPKEY, null);
        Intent intent = new Intent();
        if (isSetup!=null) {
            intent.setClass(this, LostFindActivity.class);
        } else {
            intent.setClass(this, LostFindSetup1Activity.class);
        }
        startActivity(intent);
    }

    /**
     * 密码设置确认密码
     */
    private void setPasswordConfirm() {
        String password = et_dialog_pdsetting.getText().toString().trim();
        String passwordConfirm = et_dialog_pdsetting_confirm.getText().toString().trim();
        if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(passwordConfirm)) {
            if (password.equals(passwordConfirm)) {
                try {
                    //对密码进行MD5加密,xutils工具里有,这里自己写个作为练习
                    spMD5Pw = MD5Util.md5Digest(password);
                    //保存md5密码
                    SPTools.putValue(this, MyContants.PDKEY, spMD5Pw);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                ad_set_pw.cancel();
            } else {
                Toast.makeText(this, "两次密码不同", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
        }
    }
}
