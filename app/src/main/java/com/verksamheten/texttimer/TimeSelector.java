package com.verksamheten.texttimer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class TimeSelector extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_selector);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      final TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);

        timePicker.setIs24HourView(true);

        Button timeAppliedBtn = (Button) findViewById(R.id.timeAppliedBtn);


        timeAppliedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TimeSelector.this, TextAndDateManager.class);

                final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
                globalVariable.setTimePicker(timePicker);
                startActivity(i);

            }
        });
    }

}
