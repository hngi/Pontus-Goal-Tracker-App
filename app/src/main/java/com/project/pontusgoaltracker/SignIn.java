package com.project.pontusgoaltracker;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SignIn extends AppCompatActivity implements View.OnClickListener {

    EditText EmailET, PwdET;
    CheckBox remember;
    Button button;
    TextView signUP;
    ImageView google, linked, facebook;

import android.widget.TextView;

public class SignIn extends AppCompatActivity {
    TextView sign_up;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        EmailET = findViewById(R.id.email);
        PwdET = findViewById(R.id.password);
        remember = findViewById(R.id.checkBox);
        button = findViewById(R.id.button);
        signUP = findViewById(R.id.sign);
        google = findViewById(R.id.google);
        linked = findViewById(R.id.ln);
        facebook = findViewById(R.id.fb);

        button.setOnClickListener(this);
        signUP.setOnClickListener(this);
        google.setOnClickListener(this);
        linked.setOnClickListener(this);
        facebook.setOnClickListener(this);


        sign_up = findViewById(R.id.sign);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, SignUp.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:

                break;
            case R.id.sign:
                Intent i = new Intent(SignIn.this , SignUp.class  );
                startActivity(i);

                break;
            case R.id.google:
                Toast.makeText(SignIn.this, "Feature under construction", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ln:
                Toast.makeText(SignIn.this, "Feature under construction", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fb:
                Toast.makeText(SignIn.this, "Feature under construction", Toast.LENGTH_SHORT).show();
                break;


        }

    }
}
