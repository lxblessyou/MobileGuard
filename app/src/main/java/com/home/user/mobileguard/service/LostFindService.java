package com.home.user.mobileguard.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PermissionInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by user on 16-7-6.
 */
public class LostFindService extends Service {

    private SMSBroadcastReceiver smsBroadcastReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (smsBroadcastReceiver != null) {
            unregisterReceiver(smsBroadcastReceiver);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        smsBroadcastReceiver = new SMSBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(smsBroadcastReceiver, intentFilter);

        return START_STICKY;
    }

    public class SMSBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            SmsMessage[] pdus = (SmsMessage[]) extras.get("pdus");

            for (SmsMessage smsMessage : pdus) {
                String messageBody = smsMessage.getMessageBody();
                String originatingAddress = smsMessage.getOriginatingAddress();
                Toast.makeText(getApplicationContext(),messageBody + originatingAddress,Toast.LENGTH_SHORT).show();
            }
        }
    }

}
