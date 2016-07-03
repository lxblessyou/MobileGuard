package com.home.user.mobileguard.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.home.user.mobileguard.R;

public abstract class LostFindBaseSetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 下一步点击事件
     *
     * @param view
     */
    public void next(View view) {
        //开启下一步的Activity
        nextActivity();

        //预定义动画，淡入淡出（透明动画加位移动画）。这里采用XML方式
        nextAnimation();
    }

    /**
     * 预定义动画，淡入淡出（透明动画加位移动画）。这里采用XML方式
     */
    private void nextAnimation() {
        overridePendingTransition(R.anim.next_enter, R.anim.next_exit);
    }

    protected abstract void nextActivity();

    protected void startOtherActivity(Class type) {
        Intent intent = new Intent(this, type);
        startActivity(intent);
        finish();
    }

    /**
     * 上一步点击事件
     */
    public void previous(View view) {
        //开启上一步的Activity
        previousActivity();

        //预定义动画，淡入淡出（透明动画加位移动画）。这里采用XML方式
        previousAnimation();
    }

    /**
     * 预定义动画，淡入淡出（透明动画加位移动画）。这里采用XML方式
     */
    private void previousAnimation() {
        overridePendingTransition(R.anim.previous_enter, R.anim.previous_exit);
    }

    /**
     * 上一步点击事件
     */
    protected abstract void previousActivity();
}
