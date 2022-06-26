package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ComplaintListViewer extends AppCompatActivity {

    Button backBtn, postBtn;
    RecyclerView recycler_view;
    ComplaintListAdapter adapter;
    DatabaseReference dbReference;
    ArrayList<ComplaintsItems> complaintsListItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_list_viewer);
        getSupportActionBar().setTitle("Complaints");


        recycler_view = findViewById(R.id.recycler_view);
        dbReference = FirebaseDatabase.getInstance().getReference().child("Accommodation Data").child("Complaint List");
        setRecyclerView();


        postBtn = findViewById(R.id.postBtn);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),complaintsList.class));
                finish();
            }
        });

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMain();
            }
        });
    }

    private void setRecyclerView() {

        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        complaintsListItems = new ArrayList<>();

        adapter = new ComplaintListAdapter(this, complaintsListItems);
        recycler_view.setAdapter(adapter);

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){


                    ComplaintsItems complaintsItems = dataSnapshot.getValue(ComplaintsItems.class);
                    complaintsListItems.add(complaintsItems);

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void backToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}