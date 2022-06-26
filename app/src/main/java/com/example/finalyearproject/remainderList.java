package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.Calendar;

public class remainderList extends AppCompatActivity {

    EditText rUtility, rDueDate, rCost;
    String itemAccom;
    Button addToListBtn, backBtn;
    DatePickerDialog picker;
    DecimalFormat costFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remainder_list);
        getSupportActionBar().setTitle("Add New Reminder");




        rUtility = findViewById(R.id.utilityName);
        rDueDate = findViewById(R.id.utilityDate);
        rDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();

                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                //Date Picker

                picker = new DatePickerDialog(remainderList.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        rDueDate.setText(day + "/" + month + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });

        rCost = findViewById(R.id.utilityCost);






        addToListBtn = findViewById(R.id.addBtn);
        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(remainderList.this, remainderListViewer.class);
                startActivity(intent);
            }
        });

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = fAuth.getCurrentUser();

        DOARemainderList doaRemainderList = new DOARemainderList();

        addToListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String remUtility = rUtility.getText().toString().trim();
                String remDueDate = rDueDate.getText().toString().trim();
                String remCost = rCost.getText().toString().trim();
                String remItemAccom = itemAccom;

                if (currentUser == null) {
                    Toast.makeText(remainderList.this, "Could not obtain user details!", Toast.LENGTH_SHORT).show();
                }else {
                    getUserAccom(currentUser);


                    if (TextUtils.isEmpty(remUtility)) {
                        rUtility.setError("Enter Utility Name");
                        return;
                    }

                    if (TextUtils.isEmpty(remDueDate)) {
                        rUtility.setError("Enter Due Date");
                        return;
                    }

                    if (TextUtils.isEmpty(remCost)) {
                        rUtility.setError("Enter Cost");
                        return;
                    }

                    if (!remCost.equals("")) {
                        rCost.setText(costFormat(remCost));
                    }

                    if (remItemAccom == null) {
                        Toast.makeText(remainderList.this, "No Accommodation try again", Toast.LENGTH_SHORT).show();
                    }

                    else if (remItemAccom != null) {


                        remainderItems remItems = new remainderItems(remUtility, remDueDate, remCost, remItemAccom);

                        doaRemainderList.add(remItems).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(remainderList.this, "Remainder Added", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(remainderList.this, "Remainder Could not be Added", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                }

            }
        });



    }

    private void getUserAccom (FirebaseUser currentUser) {

        String userID = currentUser.getUid();

        DatabaseReference referenceUser = FirebaseDatabase.getInstance().getReference("User Data");

        referenceUser.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StoreUserData readUserInfo = snapshot.getValue(StoreUserData.class);

                if (readUserInfo != null) {
                    String accommodationId = readUserInfo.accomId;

                    itemAccom = accommodationId;
                    Toast.makeText(remainderList.this, "Accommodation ID: " + itemAccom, Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(remainderList.this, "An Error Occurred", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private static String costFormat(String number) {
        DecimalFormat costFormat = new DecimalFormat("###,###,##0.00");
        return costFormat.format(Double.parseDouble(number));
    }
}