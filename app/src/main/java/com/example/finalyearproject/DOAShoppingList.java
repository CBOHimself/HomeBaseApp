package com.example.finalyearproject;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DOAShoppingList {

    private DatabaseReference databaseReference;
    public DOAShoppingList() {

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Accommodation Data").child("Shopping List");

    }

    public Task<Void> add(ShoppingItems shpItem){

        return databaseReference.push().setValue(shpItem);
    }
}
