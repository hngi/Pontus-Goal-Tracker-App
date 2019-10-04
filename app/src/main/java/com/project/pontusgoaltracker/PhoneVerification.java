package com.project.pontusgoaltracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.pontusgoaltracker.models.User;

import java.util.concurrent.TimeUnit;

public class PhoneVerification extends AppCompatActivity {
    EditText otp_text;
    Button otpBtn;
    Button verify;
    EditText phone;
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    String codeSent;
    ProgressBar progressBar;
    private String phoneNumber;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        otp_text = findViewById(R.id.otp);
        otpBtn = findViewById(R.id.sign_in_otp);
        verify = findViewById(R.id.verify_btn);
        phone = findViewById(R.id.phone_num);
        progressBar = findViewById(R.id.progressBar4);

        String phoneNum = getIntent().getStringExtra("phone");
        phone.setText(phoneNum);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                progressBar.setVisibility(View.VISIBLE);
                if(mAuth.getCurrentUser() != null){
                    Intent intent = new Intent(PhoneVerification.this, GoalListActivity.class);
                    startActivity(intent);
                }else {
                    verifyPhone();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        otpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                verifySignInCode();
            }
        });
    }

    private void verifySignInCode() {
        String code = otp_text.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void verifyPhone() {
        phoneNumber = phone.getText().toString();
        if(phoneNumber.isEmpty()){
            phone.setError("Please enter a phone number");
            phone.requestFocus();
        }
        if(phoneNumber.length() < 10){
            phone.setError("Please enter a valid phone number");
            phone.requestFocus();
        }
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks
        );
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {



            Toast.makeText(PhoneVerification.this, "Verification code sent", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            Toast.makeText(PhoneVerification.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent = s;
        }
    };

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);

                            Intent intent = new Intent(PhoneVerification.this, GoalListActivity.class);
                            startActivity(intent);

                        } else {
                            progressBar.setVisibility(View.GONE);
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(PhoneVerification.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }


}