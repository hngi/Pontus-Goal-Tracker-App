package com.project.pontusgoaltracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth= FirebaseAuth.getInstance();
    FirebaseUser  user = auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Makes the activity visible for 3 seconds //
        int Timeout = 3000;

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                Intent intent ;
                if(user==null) {
                   intent = new Intent(MainActivity.this, SignIn.class);
                }else intent = new Intent(MainActivity.this, GoalListActivity.class);



                startActivity(intent);
                finish();
            }
        }, Timeout);
    }
}
