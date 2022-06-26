package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
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

public class shoppingList extends AppCompatActivity {

    EditText itemName, itemShopper, itemQuantity;
    String itemAccom;
    Button addToListBtn, backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        getSupportActionBar().setTitle("Add New Item");

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = fAuth.getCurrentUser();




        itemName = findViewById(R.id.itemName);
        itemShopper = findViewById(R.id.itemShopper);
        itemQuantity = findViewById(R.id.itemQuantity);


        addToListBtn = findViewById(R.id.addBtn);
        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(shoppingList.this, shoppingListViewer.class);
                startActivity(intent);
            }
        });


        DOAShoppingList daoShippingList = new DOAShoppingList();

        addToListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String shpItemName = itemName.getText().toString().trim();
                String shpItemShopper = itemShopper.getText().toString().trim();
                String shpItemQuantity = itemQuantity.getText().toString().trim();
                String shpItemAccom = itemAccom;



                if (currentUser == null) {
                    shpItemAccom = "NaN";
                    Toast.makeText(shoppingList.this, "Could not obtain user details!", Toast.LENGTH_SHORT).show();
                }else {
                    getUserAccom(currentUser);


                    if (TextUtils.isEmpty(shpItemName)) {
                        itemName.setError("Enter Item Quantity");
                        return;
                    }
                    if (TextUtils.isEmpty(shpItemQuantity)) {
                        itemQuantity.setError("Enter Item Quantity");
                        return;
                    }

                    if (TextUtils.isEmpty(shpItemShopper)) {
                        itemShopper.setError("Enter a Shopper to buy the Item");
                        return;
                    }

                    if (shpItemAccom == null) {
                        Toast.makeText(shoppingList.this, "No Accommodation try again", Toast.LENGTH_SHORT).show();
                    }

                    else if (shpItemAccom != null) {

                        ShoppingItems shpItems = new ShoppingItems(shpItemName, shpItemQuantity, shpItemShopper, shpItemAccom);

                        daoShippingList.add(shpItems).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(shoppingList.this, "Item Added to List", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(shoppingList.this, "Item Could not be added to List", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(shoppingList.this, "Accommodation ID: " + itemAccom, Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(shoppingList.this, "An Error Occurred", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}