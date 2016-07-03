package com.home.user.mobileguard.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.home.user.mobileguard.R;

public abstract class LostFindBaseSetupActivity extends AppCompatActivity {

    private GestureDetector gd;
    private float startX;
    private float startY;
    private float endY;
    private float endX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        //为毛不灵。。。。。。。。。。
        //不用它检测滑动！！！
        //初始化手势对象
        /*gd = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (velocityX>0.1) {  //如果横向滑动速度大于0则进入下一步
                    float space = e1.getX() - e2.getX();
                    float abs = Math.abs(space);
                    if (abs>0.2) {  //如果滑动距离大于100则进入操作
                        if (space > 0) {
                            next(null);
                        } else {
                            previous(null);
                        }
                    }
                }
                return true;
            }
        });*/
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //手势绑定到触摸事件
//        gd.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //获取起始点X轴和Y轴坐标
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //获取结束点X轴和Y轴坐标
                endX = event.getX();
                endY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                float space = startX - endX;
                float abs = Math.abs(space);
                if (abs>150) {  //如果滑动距离大于100则进入操作
                    if (space > 0) {
                        next(null);
                    } else {
                        previous(null);
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
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
