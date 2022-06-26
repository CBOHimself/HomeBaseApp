package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewAccount extends AppCompatActivity {

    private Button backToSignIn;
    EditText firstName, lastName, userEmail, phone, password, passwordReentry, address, postcode, city, country, accomId;
    Button signUpButton;
    FirebaseAuth fAuth;
    ProgressBar signUpProg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);
        getSupportActionBar().setTitle("Create New Account");




        //Back Button
        backToSignIn = findViewById(R.id.backsignin);
        backToSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                BackToSignIn();
            }
        });


        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        userEmail = findViewById(R.id.userEmail);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        passwordReentry = findViewById(R.id.passwordrentry);
        signUpButton = findViewById(R.id.completesignup);
        address = findViewById(R.id.address);
        postcode = findViewById(R.id.postcode);
        city = findViewById(R.id.city);
        country = findViewById(R.id.country);
        accomId = findViewById(R.id.accomId);

        fAuth = FirebaseAuth.getInstance();

        //Check if user is logged in
        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        signUpProg = findViewById(R.id.signupprog);


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmailAddress = userEmail.getText().toString().trim();
                String userPassword = password.getText().toString().trim();
                String userFirstname = firstName.getText().toString().trim();
                String userLastName = lastName.getText().toString().trim();
                String userPasswordReEntry = passwordReentry.getText().toString().trim();
                String userPhone = phone.getText().toString().trim();
                String userAddress = address.getText().toString().trim();
                String userCity = city.getText().toString().trim();
                String userPostCode = postcode.getText().toString().trim();
                String userCountry = country.getText().toString().trim();
                String userAccommodation = accomId.getText().toString().trim();
                String userPhoto = " ";


                if (TextUtils.isEmpty(userEmailAddress))   {
                    userEmail.setError("Enter an Email Address");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(userEmailAddress).matches()) {
                    userEmail.setError("Enter a valid Email Address");
                    return;
                }

                if (userPhone.length() !=10) {
                    phone.setError("Enter a 10-digit phone number");
                    return;
                }
                if (TextUtils.isEmpty(userPhone))   {
                    phone.setError("Enter a Password");
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

                if (TextUtils.isEmpty(userFirstname))   {
                    firstName.setError("Enter First Name");
                    return;
                }

                if (!userPassword.equals(userPasswordReEntry)) {
                    passwordReentry.setError("Password does not match");
                    return;
                }
                if (TextUtils.isEmpty(userLastName))   {
                    lastName.setError("Enter Last Name");
                    return;
                }
                if (TextUtils.isEmpty(userAddress))   {
                    address.setError("Enter Address");
                    return;
                }
                if (TextUtils.isEmpty(userCity))   {
                    city.setError("Enter City");
                    return;
                }
                if (TextUtils.isEmpty(userCountry))   {
                    country.setError("Enter Country");
                    return;
                }
                if (TextUtils.isEmpty(userPostCode))   {
                    postcode.setError("Enter Post Code");
                    return;
                }
                if (TextUtils.isEmpty(userAccommodation))   {
                    accomId.setError("Enter the Accommodation ID");
                    return;
                }

                else {
                    signUpProg.setVisibility(View.VISIBLE);
                    registerUser(userEmailAddress, userFirstname, userLastName, userPassword, userPhone, userAddress, userPostCode, userCity, userCountry, userAccommodation, userPhoto);
                }


            }
        });



    }

    private void registerUser(String userEmailAddress, String userFirstname, String userLastName, String userPassword, String userPhone, String userAddress, String userPostCode, String userCity, String userCountry, String userAccommodation, String userPhoto) {
        fAuth.createUserWithEmailAndPassword(userEmailAddress,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())    {

                    FirebaseUser firebaseUser = fAuth.getCurrentUser();


                    //Store other data in database
                    StoreUserData storeDetails = new StoreUserData(userFirstname, userLastName, userPhone, userAddress, userCity, userCountry, userPostCode, userAccommodation, userPhoto);
                    DatabaseReference referenceDetails = FirebaseDatabase.getInstance().getReference("User Data");

                    referenceDetails.child(firebaseUser.getUid()).setValue(storeDetails).addOnCompleteListener( new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //Verification Email

                            if (task.isSuccessful()) {
                                firebaseUser.sendEmailVerification();

                                Toast.makeText( NewAccount.this, "Account Created", Toast.LENGTH_SHORT ).show();
                                startActivity( new Intent( getApplicationContext(), MainActivity.class ) );
                            } else {
                                Toast.makeText(NewAccount.this,"Error Occurred: " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }

                        }
                    } );

                }   else {
                    Toast.makeText(NewAccount.this,"Error Occurred: " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void BackToSignIn() {
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
    }
}