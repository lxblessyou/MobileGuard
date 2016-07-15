package com.home.user.mobileguard.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.home.user.mobileguard.activity.HomeActivity;
import com.home.user.mobileguard.activity.LostFindActivity;

/**
 * Created by user on 2016-06-30.
 */
public class SPTools {
    public static void putValue(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyContants.SPNAMEKEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public static String getValue(Context context, String key, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyContants.SPNAMEKEY, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }

    public static void clearData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyContants.SPNAMEKEY,Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }
}
