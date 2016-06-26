package com.home.user.mobileguard.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.user.mobileguard.R;
import com.home.user.mobileguard.bean.ServerJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * splash引导界面
 */
public class SplashActivity extends AppCompatActivity {
    //服务器地址
    private static final String SERVERURL = "http://192.168.1.103:8080/";
    private static final String SERVERJSONPATH = "mobileguard/splash.json";

    //View组件
    private RelativeLayout rl_splash_root;
    private TextView tv_splash_version;

    //常量标记
    private static final int EXCEPTION = 0X0;
    private static final int UPDATEDIALOG = 0x1;

    //封装的服务器json对象
    private ServerJson serverJson;

    private int versionCode;    //版本号
    private long startTime;
    private long endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //获取当前版本码
        try {
            getVersionCode();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //初始化控件
        initView();

        //添加初始动画
        initAnimation();
    }

    //消息处理器
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case UPDATEDIALOG:
                    //弹出更新对话框
                    showUpdateDialog();
                    break;
                case EXCEPTION:
                    if(3000>(endTime-startTime)){
                        new Thread(){
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(3000-(endTime-startTime));
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                //否则跳转到主页
                                showHomeActivity();
                                finish();
                            }
                        }.start();
                    }
                    else {
                        //否则跳转到主页
                        showHomeActivity();
                        finish();
                    }
                    break;
            }
        }

        /**
         * 弹出更新对话框
         */
        private void showUpdateDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
            builder.setTitle("更新提示")
                    .setMessage(serverJson.getDescription())
                    .setCancelable(false)
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //跳转到首页
                            showHomeActivity();
                            finish();
                        }
                    })
                    .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
        }
    };

    /**
     * 跳转到首页
     */
    private void showHomeActivity() {
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
    }

    /**
     * 检查是否包含新版本
     */
    private void obtainNewVersion() {
        startTime = System.currentTimeMillis();
        new Thread() {
            private StringBuilder sb;   //服务器端json字符串

            @Override
            public void run() {
                try {
                    //获取服务器端json数据
                    getServerJson(sb);
//                    Log.i("tag", String.valueOf(sb));

                    //解析json数据
                    serverJson = parsJson(sb);
//                    Log.i("tag", String.valueOf(serverJson));

                    //版本比对，看是否有更新
                    if (versionCode < serverJson.getVersion_code()) {
                        //向UI线程发送消息弹出更新对话会
                        handler.sendEmptyMessage(UPDATEDIALOG);
                    }else {
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    exceptionHandle();
                } catch (IOException e) {
                    e.printStackTrace();
                    exceptionHandle();
                } catch (JSONException e) {
                    e.printStackTrace();
                    exceptionHandle();
                }
            }

            /**
             * 异常处理
             */
            private void exceptionHandle() {
                endTime = System.currentTimeMillis();
                handler.sendEmptyMessage(EXCEPTION);
            }

            /**
             * 解析json数据
             * @param sb json字符串
             * @return json信息的封装对象
             * @throws JSONException
             */
            private ServerJson parsJson(StringBuilder sb) throws JSONException {
                JSONObject jo = new JSONObject(String.valueOf(sb));
                String version_name = jo.getString("version_name");
                int version_code = jo.getInt("version_code");
                String description = jo.getString("description");
                String update_url = jo.getString("update_url");

                return new ServerJson(version_name, version_code, description, update_url);
            }

            /**
             * 获取服务器端json数据
             * @param sb
             * @throws IOException
             */
            private void getServerJson(StringBuilder sb) throws IOException {
                URL url = new URL(SERVERURL + SERVERJSONPATH);
                HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
                httpUrlConnection.setRequestMethod("GET");  //请求方法
                httpUrlConnection.setConnectTimeout(5000);  //连接超时时长
                httpUrlConnection.setReadTimeout(5000); //读取超时时长

                int responseCode = httpUrlConnection.getResponseCode(); //服务器返回的响应码
                if (responseCode % 2 == 0) { //连接成功
                    InputStream inputStream = httpUrlConnection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                    String line = br.readLine();
                    this.sb = new StringBuilder();
                    while (!TextUtils.isEmpty(line)) {
                        this.sb.append(line);
                        line = br.readLine();
                    }
                    inputStream.close();
                }
                httpUrlConnection.disconnect();
            }
        }.start();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        rl_splash_root = (RelativeLayout) findViewById(R.id.rl_splash_root);
        tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
    }

    /**
     * 获取当前版本码
     * @throws PackageManager.NameNotFoundException
     */
    private void getVersionCode() throws PackageManager.NameNotFoundException {
        PackageManager pm = getPackageManager();
        PackageInfo pi = pm.getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
        versionCode = pi.versionCode;
    }

    /**
     * 界面初始动画
     */
    private void initAnimation() {
        //检查是否有新版本
        obtainNewVersion();

        //动画集
        AnimationSet as = new AnimationSet(true);
        as.setFillAfter(true);  //动画播完后的状态
        as.setDuration(3000);   //设置动画持续时长

        //透明度动画/渐变动画
        AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);

        //旋转动画
        RotateAnimation ra = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        //缩放动画
        ScaleAnimation sa = new ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        //把以上三种动画添加到动画集
        as.addAnimation(aa);
        as.addAnimation(ra);
        as.addAnimation(sa);

        //执行动画,因由View组件执行动画
//        as.start();   //错
        rl_splash_root.startAnimation(as);
    }
}