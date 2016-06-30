package com.home.user.mobileguard.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.home.user.mobileguard.activity.HomeActivity;

/**
 * Created by user on 2016-06-30.
 */
public class SPTools {
    public static void putValue(HomeActivity homeActivity, String pdkey, String password) {
        SharedPreferences sharedPreferences = homeActivity.getSharedPreferences(MyContants.SPNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(pdkey,password);
        edit.commit();
        Toast.makeText(homeActivity,"保存完成",Toast.LENGTH_SHORT).show();
    }
    public static String getValue(HomeActivity homeActivity, String pdkey, String defaultValue) {
        SharedPreferences sharedPreferences = homeActivity.getSharedPreferences(MyContants.SPNAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(pdkey,defaultValue);
    }
}
