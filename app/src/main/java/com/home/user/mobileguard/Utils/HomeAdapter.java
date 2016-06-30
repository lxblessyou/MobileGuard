package com.home.user.mobileguard.Utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.user.mobileguard.R;
import com.home.user.mobileguard.activity.HomeActivity;

/**
 * Created by user on 2016-06-30.
 */
public class HomeAdapter extends BaseAdapter {
    private HomeActivity homeActivity;
    private int icons[];
    private String names[] ;

    public HomeAdapter(HomeActivity homeActivity, int[] icons, String[] names) {
        this.homeActivity = homeActivity;
        this.icons = icons;
        this.names = names;
    }

    @Override
    public int getCount() {
        return icons.length;
    }

    @Override
    public Object getItem(int position) {
        return icons[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold viewHold = null;
        if (convertView == null) {
            viewHold = new ViewHold();
            convertView = View.inflate(homeActivity, R.layout.item_home_gridview, null);
            viewHold.iv = (ImageView) convertView.findViewById(R.id.iv_home_item);
            viewHold.tv = (TextView) convertView.findViewById(R.id.tv_home_item);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        viewHold.iv.setBackgroundResource(icons[position]);
        viewHold.tv.setText(names[position]);
        return convertView;
    }

    class ViewHold {
        ImageView iv;
        TextView tv;
    }
}
