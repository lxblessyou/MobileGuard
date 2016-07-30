package com.home.user.mobileguard.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.home.user.mobileguard.R;

/**
 * Created by user on 2016-07-30.
 */
public class SettingCenterItemView extends RelativeLayout implements View.OnClickListener {

    private View mView;
    private TextView tv_title;
    private TextView tv_content;
    private CheckBox cb_checked;
    private String mTitle;
    private String mContent;

    public SettingCenterItemView(Context context) {
        super(context);
        initData();
        initView();
        initEvent();
    }

    public SettingCenterItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
        initAttrs(attrs);
        initView();
        initEvent();
    }

    /**
     * 初始化数据
     */
    private void initData() {

    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        mView.setOnClickListener(this);
    }

    /**
     * 初始化属性
     * @param attrs
     */
    private void initAttrs(AttributeSet attrs) {
        mTitle = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "title");
        mContent = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "content");
    }

    /**
     * 初始化视图
     */
    private void initView() {
        mView = View.inflate(getContext(), R.layout.layout_setting_center_item,null);
        tv_title = (TextView) mView.findViewById(R.id.tv_title);
        tv_content = (TextView) mView.findViewById(R.id.tv_content);
        cb_checked = (CheckBox) mView.findViewById(R.id.cb_checked);

        tv_title.setText(mTitle);
        tv_content.setText(mContent);
        //别忘了把View添加到布局中
        addView(mView);

    }

    /**
     * 单击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (cb_checked.isChecked()) {
            cb_checked.setChecked(false);
            tv_content.setText("自动更新未开启");
            tv_content.setTextColor(Color.RED);
        }
        else  {
            cb_checked.setChecked(true);
            tv_content.setText("自动更新已开启");
            tv_content.setTextColor(Color.GREEN);
        }
    }
}
