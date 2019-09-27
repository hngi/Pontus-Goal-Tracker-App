package com.project.pontusgoaltracker;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;
import com.project.pontusgoaltracker.models.Goal;
import com.project.pontusgoaltracker.models.GoalType;
import com.project.pontusgoaltracker.models.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    //declaring the variables
    TextView sign_in;
    EditText email_up;
    EditText user_name;
    EditText pass_word;
    ProgressBar progress_bar;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase mDatabase;

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //finding the views by id
        email_up = findViewById(R.id.email);
        user_name = findViewById(R.id.name);
        pass_word = findViewById(R.id.password);
        sign_in = findViewById(R.id.signin);
        progress_bar = findViewById(R.id.progress_up);
        btn = findViewById(R.id.button);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, SignIn.class);
                startActivity(intent);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regUser();
            }
        });


    }

    public void regUser(){
        String email = email_up.getText().toString();
        String userName = user_name.getText().toString();
        String password = pass_word.getText().toString();

        if(TextUtils.isEmpty(email)){
            email_up.setError("Please enter an email address");
            email_up.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(userName)){
            user_name.setError("Please enter a username");
            user_name.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(password)){
            pass_word.setError("Please enter a password");
            pass_word.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_up.setError("Please enter a valid email address");
            email_up.requestFocus();
            return;
        }

        progress_bar.setVisibility(View.VISIBLE);
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progress_bar.setVisibility(View.GONE);


                    User user = createUser(userName,email,firebaseAuth.getCurrentUser().getUid());
                    DatabaseWriter.writeUserToDatabase(user);
                    Intent intent = new Intent(SignUp.this, SignIn.class);
                    startActivity(intent);
                }else{
                    //check if an email is being registered twice
                    progress_bar.setVisibility(View.GONE);
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(SignUp.this, "You are already registered", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }



    public User createUser(String username, String email, String uId){

        User user= new User(username , email, uId);

        return user;
    }




}
