package com.example.finalyearproject;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DOAComplaintsList {

    private DatabaseReference databaseReference;

    public DOAComplaintsList() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Accommodation Data").child("Complaint List");
    }


    public Task<Void> add(ComplaintsItems complaintsItems){

        return databaseReference.push().setValue(complaintsItems);
    }
}
