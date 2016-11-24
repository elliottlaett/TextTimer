package com.verksamheten.texttimer;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import android.support.design.widget.Snackbar;

import android.widget.Toast;

import java.util.Calendar;

public class TextAndDateManager extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS =100 ;

    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Context context;

    EditText phoneNumberEdit;
    TextView messageView;
    TextView textView;
    TextView dateView;
    TextView timeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_and_date_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.context = this;

        ContactsClass contactItem = (ContactsClass) getIntent().getSerializableExtra("contactItem");
        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        final Calendar calendar = Calendar.getInstance();


         messageView = (TextView) findViewById(R.id.msgTextView);
        phoneNumberEdit = (EditText) findViewById(R.id.editNumber);

        messageView.setText(globalVariable.getMessage());
        phoneNumberEdit.setText(globalVariable.getPhoneNumber());

        final ContactsClass contact = globalVariable.getContact();
        final TimePicker timePicker = globalVariable.getTimePicker();
        final DatePicker datePicker = globalVariable.getDatePicker();

        textView = (TextView) findViewById(R.id.textView);
        dateView = (TextView) findViewById(R.id.dateView);
        timeView = (TextView) findViewById(R.id.timeView);

        // Solve problem to handle permissions correct.
        getPermissionReadContacts();
        sendMessagePermission();

        if (contact != null) {
            textView.setText("TO: " + contact.getName() + " " + contact.getPhoneNumber());
        }
        if (datePicker!=null){
            Log.e("OUT", datePicker.getMonth() + "");
            int month = datePicker.getMonth()+1;
            dateView.setText("Date: " + datePicker.getYear() +"-"+month+"-"+datePicker.getDayOfMonth());
        }
            if (timePicker != null){
                timeView.setText("Time: "+timePicker.getHour()+":"+timePicker.getMinute());
            }

        Button dateBtn = (Button) findViewById(R.id.dateBtn);

        dateBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                globalVariable.setMessage(messageView.getText().toString());
                globalVariable.setPhoneNumber(phoneNumberEdit.getText().toString());
                Intent i = new Intent(TextAndDateManager.this, DateSelector.class);
                startActivity(i);
            }
        });
        Button timeBtn = (Button) findViewById(R.id.timeBtn);

        timeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                globalVariable.setMessage(messageView.getText().toString());
                globalVariable.setPhoneNumber(phoneNumberEdit.getText().toString());
                    Intent i = new Intent(TextAndDateManager.this, TimeSelector.class);
                startActivity(i);
            }
        });


        Button chooseContactBtn = (Button) findViewById(R.id.chooseContactBtn);

        chooseContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                globalVariable.setMessage(messageView.getText().toString());
                globalVariable.setPhoneNumber(phoneNumberEdit.getText().toString());

                Intent i = new Intent(TextAndDateManager.this, GetContacts.class);
                startActivity(i);

            }
        });

        final Intent intent = new Intent(this.context, Alarm_Receiver.class);

        Button executeBtn = (Button) findViewById(R.id.executeBtn);

        executeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if ((datePicker != null) && (timePicker != null) && (contact != null || !phoneNumberEdit.getText().toString().equals(""))){

                    calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getHour(), timePicker.getMinute(), 0);

                intent.putExtra("extra", "alarm on");
                intent.putExtra("message", messageView.getText().toString());
                    if(contact != null){
                        intent.putExtra("contact", contact);
                    }else {
                        intent.putExtra("phoneNumber", phoneNumberEdit.getText().toString());
                    }
                pendingIntent = PendingIntent.getBroadcast(TextAndDateManager.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                //alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                Log.e("OUT", calendar.getTime() + " " + calendar.getTimeInMillis() + " " + alarmManager.toString());

                    Snackbar bar = Snackbar.make(v, "Executed! Message will be sent at: " + calendar.getTime(), Snackbar.LENGTH_INDEFINITE)

                            .setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // Handle user action
                                }
                            });

                    bar.show();

            }
                else{
                    Toast.makeText(TextAndDateManager.this, "Some field(s) are not filled",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button cancelBtn = (Button) findViewById(R.id.cancelBtn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alarmManager.cancel(pendingIntent);

                intent.putExtra("extra", "alarm off");

                sendBroadcast(intent);

                Toast.makeText(TextAndDateManager.this, "Canceled!",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getPermissionReadContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }

            if(requestCode == MY_PERMISSIONS_REQUEST_SEND_SMS) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }
            }
        }
    }
    public void sendMessagePermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        }
    }
}
