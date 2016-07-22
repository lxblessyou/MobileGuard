package com.home.user.mobileguard.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsMessage;
import android.util.Log;

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
        Log.i("tag", "onDestroy");

        if (smsBroadcastReceiver != null) {
            unregisterReceiver(smsBroadcastReceiver);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("tag", "onStartCommand");
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
                    intent.setClass(context,LocationService.class);
                    startService(intent);
                }
            }
            abortBroadcast();
        }
    }

}
