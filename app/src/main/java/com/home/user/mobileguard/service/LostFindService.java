package com.home.user.mobileguard.service;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsMessage;
import android.util.Log;

import com.home.user.mobileguard.R;
import com.home.user.mobileguard.Utils.MyContants;
import com.home.user.mobileguard.receiver.DeviceAdminSampleReceiver;

import java.io.File;

/**
 * Created by user on 16-7-6.
 */
public class LostFindService extends Service {

    private SMSBroadcastReceiver smsBroadcastReceiver;
    private ComponentName who;
    private DevicePolicyManager dpm;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("tag", "onDestroy");

        if (smsBroadcastReceiver != null) {
            unregisterReceiver(smsBroadcastReceiver);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("tag", "onStartCommand");

        dpm = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        who = new ComponentName(this, DeviceAdminSampleReceiver.class);

        smsBroadcastReceiver = new SMSBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(smsBroadcastReceiver, intentFilter);

        return START_STICKY;
    }

    public class SMSBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("tag", "onReceive");
            Bundle extras = intent.getExtras();
            Object[] pdus = (Object[]) extras.get("pdus");

            for (Object pdu : pdus) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);

                String messageBody = smsMessage.getMessageBody();
//                String originatingAddress = smsMessage.getOriginatingAddress();
//                Log.i("tag", messageBody + "-----" + originatingAddress);
                if ("#*gps*#".equals(messageBody)) {
                    intent.setClass(context, LocationService.class);
                    startService(intent);
                } else if ("#*lockscreen*#".equals(messageBody)) {
                    //锁屏
                    lockScreen();
                }else if ("#*wipedata*#".equals(messageBody)) {
                    //清除数据
                    wipedata();
                }else if ("#*music*#".equals(messageBody)) {
                    //播放报警音乐
                    playMusic();
                }
            }
            abortBroadcast();
        }

        /**
         * 播放报警音乐
         */
        private void playMusic() {
            MediaPlayer mp = MediaPlayer.create(LostFindService.this, R.raw.baojing);
            mp.setVolume(1f,1f);
            mp.start();
        }

        /**
         * 清除数据
         */
        private void wipedata() {
            Log.i(MyContants.TAG, "lockScreen: ");
            if (dpm.isAdminActive(who)) {
                dpm.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);
                Log.i(MyContants.TAG, "lockScreen:lockNow ");
            } else {
//            <activity android:name="DeviceAdminAdd"
//            android:label="@string/device_admin_add_title"
//            android:theme="@style/TallTitleBarTheme"
//            android:clearTaskOnLaunch="true"
//                    >
//            <intent-filter>
//            <action android:name="android.app.action.ADD_DEVICE_ADMIN" />
//            <category android:name="android.intent.category.DEFAULT" />
//            </intent-filter>
//            </activity>
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, who);
                intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "添加到设备管理");
                startActivity(intent);
                Log.i(MyContants.TAG, "lockScreen:startActivity ");
            }
        }

        /**
         * 锁屏
         */
        private void lockScreen() {
            Log.i(MyContants.TAG, "lockScreen: ");
            if (dpm.isAdminActive(who)) {
//                dpm.resetPassword("8888",Intent.FLAG_ACTIVITY_NEW_TASK);
                dpm.lockNow();
                Log.i(MyContants.TAG, "lockScreen:lockNow ");
            } else {
//            <activity android:name="DeviceAdminAdd"
//            android:label="@string/device_admin_add_title"
//            android:theme="@style/TallTitleBarTheme"
//            android:clearTaskOnLaunch="true"
//                    >
//            <intent-filter>
//            <action android:name="android.app.action.ADD_DEVICE_ADMIN" />
//            <category android:name="android.intent.category.DEFAULT" />
//            </intent-filter>
//            </activity>
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, who);
                intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "添加到设备管理");
                startActivity(intent);
                Log.i(MyContants.TAG, "lockScreen:startActivity ");
            }
        }
    }

}
