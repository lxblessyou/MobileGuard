package com.home.user.mobileguard.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.home.user.mobileguard.Utils.MyContants;
import com.home.user.mobileguard.Utils.SPTools;

/**
 * 开机监听SIM卡变更
 * Created by user on 16/7/15.
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("tag", "onReceive: boot complete");
        String oldSim = SPTools.getValue(context, MyContants.SIMNUM, null);

        TelephonyManager tm = (TelephonyManager) (context.getSystemService(Context.TELEPHONY_SERVICE));

        String phoneSimNum = tm.getSimSerialNumber();
//        Log.i("tag", "oldSim--" + oldSim + "   ,phoneSimNum--" + phoneSimNum);

        if (oldSim != null) {
            if (!oldSim.equals(phoneSimNum + "1")) {
                String safeNum = SPTools.getValue(context, MyContants.SAFENUM, null);

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(safeNum, null, "tou dao la !!!", null, null);
                Log.i(MyContants.TAG, "发送报警短信!");
            }
        }
    }
}
