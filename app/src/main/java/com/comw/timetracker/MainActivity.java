package com.comw.timetracker;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Chronometer chronometer;
    private long pauseOffset;
    private boolean chronometerRunning;

    private TextView startButton;
    private TextView pauseButton;
    private TextView stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.activity_type_spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.activities_array, R.layout.spinner_item);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        startButton = findViewById(R.id.start_button);
        pauseButton = findViewById(R.id.pause_button);
        stopButton = findViewById(R.id.reset_button);

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

        if (!chronometerRunning) {

            Log.i("Button tapped", "Start");

            startButton.setEnabled(false);
            pauseButton.setEnabled(true);
            stopButton.setEnabled(true);

            startButton.setTextColor(getApplication().getResources().getColor(R.color.disabledButtonColor));
            pauseButton.setTextColor(getApplication().getResources().getColor(R.color.enabledButtonColor));
            stopButton.setTextColor(getApplication().getResources().getColor(R.color.enabledButtonColor));

            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);

            chronometer.start();
            chronometerRunning = true;
        }

    }

    public void pauseChronometer(View v) {
        if (chronometerRunning) {

            Log.i("Button tapped", "Pause");

            startButton.setEnabled(true);
            pauseButton.setEnabled(false);

            startButton.setTextColor(getApplication().getResources().getColor(R.color.enabledButtonColor));
            pauseButton.setTextColor(getApplication().getResources().getColor(R.color.disabledButtonColor));

            startButton.setText("Resume");

            chronometer.stop();

            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            chronometerRunning = false;
        }

    }

    public void resetChromometer(View view) {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
        chronometer.stop();
    }


    public long stopChromometer(View v) {

        Log.i("Button tapped", "Stop");

        startButton.setText("Start");
        startButton.setEnabled(true);
        startButton.setTextColor(getApplication().getResources().getColor(R.color.enabledButtonColor));

        pauseButton.setEnabled(false);
        pauseButton.setTextColor(getApplication().getResources().getColor(R.color.disabledButtonColor));

        stopButton.setEnabled(false);
        stopButton.setTextColor(getApplication().getResources().getColor(R.color.disabledButtonColor));

        chronometer.stop();
        chronometerRunning = false;
        long elapsedMillis = SystemClock.elapsedRealtime() - pauseOffset;

        Toast.makeText(this, "Elapsed milliseconds: " + elapsedMillis,
                Toast.LENGTH_SHORT).show();
        Log.i("TimeElapsed", String.valueOf(SystemClock.elapsedRealtime() - pauseOffset));
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
