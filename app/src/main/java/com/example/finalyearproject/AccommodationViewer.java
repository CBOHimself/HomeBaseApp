package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AccommodationViewer extends AppCompatActivity {

    Button backBtn;
    RecyclerView recycler_view;
    AccommodationAdapter adapter;
    DatabaseReference dbReference;
    ArrayList<UserData> userDataList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation_viewer);
        getSupportActionBar().setTitle("Accommodation");

        recycler_view = findViewById(R.id.recycler_view);
        dbReference = FirebaseDatabase.getInstance().getReference().child("User Data");
        setRecyclerView();



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
        userDataList = new ArrayList<>();
        adapter = new AccommodationAdapter(this,userDataList);
        recycler_view.setAdapter(adapter);

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){


                    UserData userDataSet = dataSnapshot.getValue(UserData.class);
                    userDataList.add(userDataSet);

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