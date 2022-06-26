package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class remainderListViewer extends AppCompatActivity {

    Button addBtn, backBtn;
    RecyclerView recycler_view;
    RemainderListAdapter adapter;
    DatabaseReference dbReference;
    TextView accom;
    ArrayList<remainderItems> remainderItemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remainder_list_viewer);
        getSupportActionBar().setTitle("Reminders");

        accom = findViewById(R.id.accom);
        accom.setVisibility(View.GONE);

        recycler_view = findViewById(R.id.recycler_view);
        dbReference = FirebaseDatabase.getInstance().getReference().child("Accommodation Data").child("Remainder List");
        setRecyclerView();


        addBtn = findViewById(R.id.fabAdd);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),remainderList.class));
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
        remainderItemsList = new ArrayList<>();

        adapter = new RemainderListAdapter(this,remainderItemsList);
        recycler_view.setAdapter(adapter);

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){


                    remainderItems remainderItems = dataSnapshot.getValue(remainderItems.class);
                    remainderItemsList.add(remainderItems);

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