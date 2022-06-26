package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {


    EditText logInEmail ,password;
    Button signInButton, NewAccountButton;
    FirebaseAuth fAuth;
    ProgressBar signInProg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        getSupportActionBar().setTitle("Log In");

        logInEmail = findViewById(R.id.signInEmail);
        password = findViewById(R.id.signInPassword);
        signInButton = findViewById(R.id.signInButton);
        NewAccountButton = findViewById(R.id.registerButton);
        fAuth = FirebaseAuth.getInstance();
        signInProg = findViewById(R.id.signInProg);

        //Redirects to registration page
        NewAccountButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openNewAccScrn();
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = logInEmail.getText().toString().trim();
                String userPassword = password.getText().toString().trim();

                if (TextUtils.isEmpty(email))   {
                    logInEmail.setError("Enter an Email Address");
                    return;
                }

                if (TextUtils.isEmpty(userPassword))   {
                    password.setError("Enter a Password");
                    return;
                }

                if (userPassword.length() <= 6)   {
                    password.setError("Password must have 6 or more characters");
                    return;
                }

                //Progress Bar
                signInProg.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())    {
                            Toast.makeText(LogIn.this, "Account Signed In", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }   else {
                            Toast.makeText(LogIn.this,"Unable to Sign In:" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

    }


    public void openNewAccScrn() {
        Intent intent = new Intent(this, NewAccount.class);
        startActivity(intent);
    }
}