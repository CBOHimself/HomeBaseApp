package com.example.finalyearproject;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DOARemainderList {

    private DatabaseReference databaseReference;

    public DOARemainderList() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Accommodation Data").child("Remainder List");

    }


    public Task<Void> add(remainderItems remItem){

        return databaseReference.push().setValue(remItem);
    }
}
