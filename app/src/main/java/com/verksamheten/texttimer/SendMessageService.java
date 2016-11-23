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

/**
 * Created by Elliott on 2016-11-23.
 */
public class SendMessageService extends Service{
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;

    MediaPlayer mediaPlayer;
    boolean isRunning;
    int startId;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        Log.e("OUT", "in the service start command" + startId + " " + intent);

        String state = intent.getExtras().getString("extra");

        Log.e("OUT", state+"");

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

            final GlobalClass globalVariable = (GlobalClass) getApplicationContext();

            String phoneNumber = globalVariable.getContact().getPhoneNumber();
            String message = intent.getExtras().getString("message");

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);

            mediaPlayer = MediaPlayer.create(this,R.raw.codebreaker);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();

            this.isRunning = true;
            this.startId = 0;
        }
        else if(this.isRunning && startId == 0){

            mediaPlayer.stop();
            mediaPlayer.reset();

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
