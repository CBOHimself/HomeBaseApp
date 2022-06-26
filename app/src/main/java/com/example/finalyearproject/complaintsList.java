package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Member;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class complaintsList extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button postBtn, backBtn;
    EditText complaintText;
    Spinner posterSpinner;
    DatabaseReference dbReference;
    String spinnerItem, itemAccom, userFullName;


    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = fAuth.getCurrentUser();

    DOAComplaintsList doaComplaintsList = new DOAComplaintsList();



    String [] poster = {"Anonymous", "Name"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints_list);
        getSupportActionBar().setTitle("Add New Complaint");



        Calendar calendar = Calendar.getInstance();

        String dateStamp = DateFormat.getDateInstance(DateFormat.LONG).format(calendar.getTime());



        complaintText = findViewById(R.id.complaintText);

        posterSpinner = findViewById(R.id.posterSpinner);
        posterSpinner.setOnItemSelectedListener(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, poster);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        posterSpinner.setAdapter(arrayAdapter);


        postBtn = findViewById(R.id.postBtn);

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String compText = complaintText.getText().toString().trim();
                String compPoster = spinnerItem.trim();
                String comAccom = itemAccom;
                String comTimeStamp = dateStamp;
                String comUserName = userFullName;


                if (currentUser == null) {
                    Toast.makeText(complaintsList.this, "Could not obtain user details!", Toast.LENGTH_SHORT).show();
                } else {
                    getUserData(currentUser);

                    if (TextUtils.isEmpty(compText)) {
                        complaintText.setError("Enter a complaint");
                        return;
                    }

                    if (compText.length()>200) {
                        complaintText.setError("Complaint too long");

                    }


                    if (comAccom == null && comUserName == null) {
                        Toast.makeText(complaintsList.this, "An error occurred! Try again", Toast.LENGTH_SHORT).show();
                    }

                    else if (comAccom != null) {

                        if (compPoster == "Name") {
                            compPoster = comUserName;
                        }

                        ComplaintsItems comItems = new ComplaintsItems(compPoster, comTimeStamp, compText, comAccom);

                        doaComplaintsList.add(comItems).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(complaintsList.this, "Complaint Posted", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(complaintsList.this, "Complaint could not be posted", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

            }
        });

        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(complaintsList.this, ComplaintListViewer.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        spinnerItem = posterSpinner.getSelectedItem().toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void getUserData (FirebaseUser currentUser) {

        String userID = currentUser.getUid();

        DatabaseReference referenceUser = FirebaseDatabase.getInstance().getReference("User Data");

        referenceUser.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StoreUserData readUserInfo = snapshot.getValue(StoreUserData.class);

                if (readUserInfo != null) {
                    String accommodationId = readUserInfo.accomId;
                    String userName = readUserInfo.firstName + " " + readUserInfo.lastName;

                    itemAccom = accommodationId;
                    userFullName = userName;
                    Toast.makeText(complaintsList.this, "Accommodation ID: " + itemAccom, Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(complaintsList.this, "An Error Occurred", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}