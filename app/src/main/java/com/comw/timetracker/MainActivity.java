package com.comw.timetracker;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private Chronometer chronometer;
    private long pauseOffset;
    private boolean chronimeterRunning;
//
//    Spinner spinner  = (Spinner) findViewById(R.id.activity_type_spinner);
//    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.activities_array, android.R.layout.simple_spinner_item);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner  = (Spinner) findViewById(R.id.activity_type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.activities_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("00:%s");
    }

    public void startChronometer(View v) {
        if(!chronimeterRunning) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            chronimeterRunning = true;
        }

    }

    public void pauseChronometer(View v) {
        if(chronimeterRunning) {
//            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            chronimeterRunning = false;
        }

    }

    public void resetChromometer(View view) {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
        chronometer.stop();
    }
}
