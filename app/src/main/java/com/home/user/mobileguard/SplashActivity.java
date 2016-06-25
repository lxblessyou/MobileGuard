package com.home.user.mobileguard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * splash引导界面
 */
public class SplashActivity extends AppCompatActivity {
    //View组件
    private RelativeLayout rl_splash_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();

        //添加初始动画
        initAnimation();
    }

    private void initView() {
        rl_splash_root = (RelativeLayout) findViewById(R.id.rl_splash_root);
    }

    /**
     * 界面初始动画
     */
    private void initAnimation() {
        //动画集
        AnimationSet as = new AnimationSet(true);
        as.setFillAfter(true);
        as.setDuration(3000);   //设置动画持续时长

        //透明度动画/渐变动画
        AlphaAnimation aa= new AlphaAnimation(0.0f,1.0f);

        //旋转动画
        RotateAnimation ra = new RotateAnimation (0.0f,360.0f, RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);

        //缩放动画
        ScaleAnimation sa= new ScaleAnimation(0.5f,1.0f,0.5f,1.0f,ScaleAnimation.RELATIVE_TO_SELF,0.5f,ScaleAnimation.RELATIVE_TO_SELF,0.5f);

        //把以上三种动画添加到动画集
        as.addAnimation(aa);
        as.addAnimation(ra);
        as.addAnimation(sa);

        //执行动画,因由View组件执行动画
//        as.start();   //错
        rl_splash_root.startAnimation(as);
    }
}
