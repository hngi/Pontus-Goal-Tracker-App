package com.project.pontusgoaltracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Makes the activity visible for 5 seconds //
        int Timeout = 3000;

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
                Intent intent = new Intent(MainActivity.this, SignIn.class);
                startActivity(intent);
            }
        }, Timeout);
    }
}
