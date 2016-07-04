package com.home.user.mobileguard.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.home.user.mobileguard.R;

/**
 * Created by user on 16-7-2.
 */
public class LostFindActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lost_find);
    }

    public void reset(View view) {
        Intent intent = new Intent(this,LostFindSetup1Activity.class);
        startActivity(intent);
        finish();
    }
}
