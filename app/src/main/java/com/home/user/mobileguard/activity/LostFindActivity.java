package com.home.user.mobileguard.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.home.user.mobileguard.R;
import com.home.user.mobileguard.Utils.EncryptionTools;
import com.home.user.mobileguard.Utils.MyContants;
import com.home.user.mobileguard.Utils.SPTools;

import java.util.IllegalFormatException;

/**
 * Created by user on 16-7-2.
 */
public class LostFindActivity extends LostFindBaseActivity {
    private ImageView iv_lost_find_lock;
    private TextView tv_lost_find_safe_num;

    private String isProtected;
    private String safeNum;
    private LinearLayout ll_root;
    private View mView;
    private PopupWindow mPopupWindow;
    private Button btn_popup_update;
    private Button btn_popup_cancel;
    private EditText et_popup_rename;
    private ScaleAnimation sa;

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        isProtected = SPTools.getValue(this, MyContants.ISPROTECTED, null);
        safeNum = EncryptionTools.deciphering(MyContants.OFFSET, SPTools.getValue(this, MyContants.SAFENUM, ""));
    }

    @Override
    protected void initView() {
        setContentView(R.layout.layout_lost_find);

        iv_lost_find_lock = (ImageView) findViewById(R.id.iv_lost_find_lock);
        tv_lost_find_safe_num = (TextView) findViewById(R.id.tv_lost_find_safe_num);
        ll_root = (LinearLayout) findViewById(R.id.ll_root);

        if ("true".equals(isProtected) && iv_lost_find_lock != null) {
            iv_lost_find_lock.setImageResource(R.drawable.lock);
        } else {
            iv_lost_find_lock.setImageResource(R.drawable.unlock);
        }
        if (safeNum != null && tv_lost_find_safe_num != null) {
            tv_lost_find_safe_num.append("    " + safeNum);
        }
        initPopup();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:
                Toast.makeText(getApplicationContext(), "menu", Toast.LENGTH_SHORT).show();
                if (mPopupWindow != null && mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                } else {
                    showPopupWindow();
                }
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 弹出PopupWindow
     */
    private void showPopupWindow() {
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setFocusable(true);
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        mView.startAnimation(sa);
        int windowHeight = defaultDisplay.getHeight();
        int windowWidth = defaultDisplay.getWidth();
        int viewWidth = mView.getWidth();
        int viewHeight = mView.getHeight();
        Log.i(MyContants.TAG, "  windowWidth: " + windowWidth + "  windowHeight: " + windowHeight + "  viewWidth: " + viewWidth + "  viewHeight: " + viewHeight);

        mPopupWindow.showAtLocation(ll_root, Gravity.NO_GRAVITY, windowWidth / 2, windowHeight / 2);
    }

    /**
     * 初始化Popup
     */
    private void initPopup() {
        mView = getLayoutInflater().inflate(R.layout.layout_lost_find_popup, null);
        sa = new ScaleAnimation(1f, 1f, 0f, 1f, ScaleAnimation.RELATIVE_TO_SELF, 0, ScaleAnimation.RELATIVE_TO_SELF, 0);
        sa.setDuration(1000);

        btn_popup_cancel = (Button) mView.findViewById(R.id.btn_popup_cancel);
        btn_popup_update = (Button) mView.findViewById(R.id.btn_popup_update);
        et_popup_rename = (EditText) mView.findViewById(R.id.et_popup_rename);
        btn_popup_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopupWindow != null) {
                    if (mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    }
                }
            }
        });
        btn_popup_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rename = et_popup_rename.getText().toString().trim();
                if (TextUtils.isEmpty(rename)) {
                    Toast.makeText(getApplicationContext(), "重命名不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    SPTools.putValue(LostFindActivity.this, MyContants.RENAME, rename);
                    mPopupWindow.dismiss();
                }
            }
        });

        mPopupWindow = new PopupWindow(mView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPopupWindow != null) {
            if (mPopupWindow.isShowing()) {
                mPopupWindow.dismiss();
            }
            mPopupWindow = null;
        }
    }
}
