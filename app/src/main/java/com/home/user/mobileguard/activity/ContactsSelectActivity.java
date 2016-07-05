package com.home.user.mobileguard.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.home.user.mobileguard.R;
import com.home.user.mobileguard.Utils.MyContants;
import com.home.user.mobileguard.Utils.ReadContacts;
import com.home.user.mobileguard.bean.ContactBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 16-7-4.
 */
public class ContactsSelectActivity extends Activity {
    private static final int SHOWPROGRESS = 0;
    private static final int UPDATELISTVIEW = 1;
    private List<ContactBean> datas = new ArrayList<ContactBean>();
    private ListView lv_contacts_select;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();

        initView();

        initEvent();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        setContentView(R.layout.layout_contacts_select);

        lv_contacts_select = (ListView) findViewById(R.id.lv_contacts_select);
        myAdapter = new MyAdapter();
        lv_contacts_select.setAdapter(myAdapter);
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        lv_contacts_select.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContactBean contactBean = datas.get(position);
                Intent intent = new Intent();
                intent.putExtra(MyContants.SAFENUM, contactBean.getSafeNum());
                ContactsSelectActivity.this.setResult(0,intent);
                ContactsSelectActivity.this.finish();
            }
        });
    }

    /**
     * 消息处理器
     */
    private Handler handler = new Handler() {

        private ProgressDialog pd;
        private ProgressBar pb;

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOWPROGRESS:
                    pd = new ProgressDialog(ContactsSelectActivity.this);
                    pd.setMessage("正在加载中...");
                    pd.show();
                    break;
                case UPDATELISTVIEW:
                    if (pd != null) {
                        pd.dismiss();
                    }
//                    pb = null;
                    myAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    /**
     * 初始化数据
     */
    private void initData() {
        new Thread() {
            @Override
            public void run() {
                handler.sendEmptyMessage(SHOWPROGRESS);
                SystemClock.sleep(2000);
                ReadContacts.getContacts(getApplicationContext(),datas);
                Log.i(MyContants.TAG, datas.toString());
                handler.sendEmptyMessage(UPDATELISTVIEW);
            }
        }.start();
    }

    /**
     * 列表适配器
     */
    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHold viewHold = null;
            ContactBean contactBean = datas.get(position);
            if (convertView == null) {
                viewHold = new ViewHold();
                convertView = View.inflate(ContactsSelectActivity.this, R.layout.item_contacts_selector, null);
                viewHold.tv_contacts_name = (TextView) convertView.findViewById(R.id.tv_contacts_name);
                viewHold.tv_contacts_phone = (TextView) convertView.findViewById(R.id.tv_contacts_phone);
                convertView.setTag(viewHold);
            } else {
                viewHold = (ViewHold) convertView.getTag();
            }
            viewHold.tv_contacts_name.setText(contactBean.getName());
            viewHold.tv_contacts_phone.setText(contactBean.getSafeNum());
            return convertView;
        }

        private class ViewHold {
            TextView tv_contacts_name;
            TextView tv_contacts_phone;
        }
    }
}
