package com.verksamheten.texttimer;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by Elliott on 2016-11-23.
 */
public class SendMessageService extends Service{
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;

    boolean isRunning;
    int startId;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        String state = intent.getExtras().getString("extra");

        assert state != null;
        switch (state) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }

        if(!this.isRunning && startId == 1){
            String phoneNumber;

            ContactsClass contact = (ContactsClass) intent.getSerializableExtra("contact");

            if (contact != null) {
                phoneNumber = contact.getPhoneNumber();
            }else{
                phoneNumber = intent.getExtras().getString("phoneNumber");
            }
            String message = intent.getExtras().getString("message");
Log.e("OUT", "--- "+new Date().toString());
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);

            this.isRunning = true;
            this.startId = 0;
        }
        else if(this.isRunning && startId == 0){

            this.isRunning = false;
            this.startId = 0;

        }
        else if(!this.isRunning && startId == 0){

            this.isRunning = false;
            this.startId = 0;

        }
        else if(this.isRunning && startId == 1){
            this.isRunning = true;
            this.startId = 1;
        }
        else{

        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        this.isRunning = false;
    }

}
