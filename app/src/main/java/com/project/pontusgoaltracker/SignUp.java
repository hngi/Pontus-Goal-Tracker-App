package com.project.pontusgoaltracker;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.project.pontusgoaltracker.models.Goal;
import com.project.pontusgoaltracker.models.GoalType;
import com.project.pontusgoaltracker.models.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class SignUp extends AppCompatActivity {


    //declaring the variables
    TextView sign_in;
    EditText email_up;
    EditText user_name;
    EditText pass_word;
    ProgressBar progress_bar;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase mDatabase;
    ImageView google;

    PhoneAuthProvider phoneAuthProvider;

    Button btn;
    GoogleSignInClient mGoogleSignInClient;
    static final int RC_SIGN_IN = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //finding the views by id
        google = findViewById(R.id.google);
        email_up = findViewById(R.id.email);
        user_name = findViewById(R.id.name);
        pass_word = findViewById(R.id.password);
        sign_in = findViewById(R.id.signin);
        progress_bar = findViewById(R.id.progress_up);
        btn = findViewById(R.id.button);


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


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


        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_bar.setVisibility(View.VISIBLE);
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
                Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            progress_bar.setVisibility(View.INVISIBLE);
                            Log.d("TAG", "Sign in success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Intent intent = new Intent(SignUp.this, GoalListActivity.class);
                            startActivity(intent);
                            Toast.makeText(SignUp.this, "Sign Up successful", Toast.LENGTH_SHORT).show();

                        } else {
                            progress_bar.setVisibility(View.INVISIBLE);
                            Log.d("TAG", "Sign in failed");
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUp.this, "failed!", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });


    }

    public void regUser() {
        String email = email_up.getText().toString();
        String userName = user_name.getText().toString();
        String password = pass_word.getText().toString();

        if (TextUtils.isEmpty(email)) {
            email_up.setError("Please enter an email address");
            email_up.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(userName)) {
            user_name.setError("Please enter a username");
            user_name.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            pass_word.setError("Please enter a password");
            pass_word.requestFocus();
            return;
        }


        /* if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_up.setError("Please enter a valid email address");
            email_up.requestFocus();
            return;
         }*/

        progress_bar.setVisibility(View.VISIBLE);


        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            progress_bar.setVisibility(View.VISIBLE);

            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progress_bar.setVisibility(View.GONE);


                        User user = createUser(userName, email, firebaseAuth.getCurrentUser().getUid());
                        DatabaseWriter.writeUserToDatabase(user);
                        Intent intent = new Intent(SignUp.this, SignIn.class);
                        startActivity(intent);

                    } else {
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

        }else if(Patterns.PHONE.matcher(email).matches()){
            Intent intent = new Intent(SignUp.this, PhoneVerification.class);
            intent.putExtra("phone", email);
            startActivity(intent);

        } else if (Patterns.PHONE.matcher(email).matches()) {
            Intent intent = new Intent(SignUp.this, PhoneVerification.class);
            intent.putExtra("phone", email);
            startActivity(intent);

        }

    }


    public User createUser(String username, String email, String uId) {

        User user = new User(username, email, uId);

        return user;
    }
}





