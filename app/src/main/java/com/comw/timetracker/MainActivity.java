package com.comw.timetracker;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Chronometer chronometer;
    private long pauseOffset;
    private boolean chronimeterRunning;
    private TextView startButton;
//
//    Spinner spinner  = (Spinner) findViewById(R.id.activity_type_spinner);
//    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.activities_array, android.R.layout.simple_spinner_item);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.activity_type_spinner);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.activities_array, android.R.layout.simple_spinner_item);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.activities_array, R.layout.spinner_item);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        startButton = findViewById(R.id.start_button);
        chronometer = findViewById(R.id.chronometer);

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            public void onChronometerTick(Chronometer cArg) {
                long time = SystemClock.elapsedRealtime() - cArg.getBase();
                int hh = (int) time / 3600000;
                int mm = (int) (time - hh * 3600000) / 60000;
//                int ss = (int) (time - mm * 3600000) / 60000;

                cArg.setText(String.format("%02d:%02d", hh, mm));
            }
        });


//        chronometer.setFormat("00:%s");


    }

    public void startChronometer(View v) {

        TextView pauseButton = findViewById(R.id.pause_button);

        if (!chronimeterRunning) {
            if (startButton.getText() != "Start") {
                startButton.setText("Start");
                startButton.setTextColor(getApplication().getResources().getColor(R.color.colorPrimaryDark));
            }

            //TODO: disable Start button unless Pause is clicked;
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            pauseButton.setEnabled(true);
            startButton.setEnabled(false);
            chronometer.start();
            chronimeterRunning = true;
        }

    }

    public void pauseChronometer(View v) {
        if (chronimeterRunning) {

            TextView startButton = findViewById(R.id.start_button);
            TextView pauseButton = findViewById(R.id.pause_button);

            startButton.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
            pauseButton.setTextColor(getApplication().getResources().getColor(R.color.colorPrimaryDark));

            startButton.setEnabled(true);
            pauseButton.setEnabled(false);

            startButton.setText("Resume");

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


    public long stopChromometer(View view) {

        chronometer.stop();
        long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();

        Toast.makeText(this, "Elapsed milliseconds: " + elapsedMillis,
                Toast.LENGTH_SHORT).show();

        chronometer.stop();
        return elapsedMillis;
    }


    public long saveChromometer(View view) {

        long spentTime = 0;

        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
        chronometer.stop();

        return spentTime;
    }
}
