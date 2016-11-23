package com.verksamheten.texttimer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Elliott on 2016-11-19.
 */
public class Alarm_Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("OUT", "In the receiver");

        String buttonPressedMsg = intent.getExtras().getString("extra");
        String message = intent.getExtras().getString("message");
        Log.e("OUT", buttonPressedMsg);

        Intent service_intent = new Intent(context,SendMessageService.class);

        service_intent.putExtra("extra", buttonPressedMsg);
        service_intent.putExtra("message", message);

        context.startService(service_intent);
    }
}
