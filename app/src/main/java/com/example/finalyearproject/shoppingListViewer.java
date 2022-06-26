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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class shoppingListViewer extends AppCompatActivity {

    Button addBtn, backBtn;
    RecyclerView recycler_view;
    ShoppingListAdapter adapter;
    DatabaseReference dbReference;
    TextView accom;
    ArrayList<ShoppingItems> shoppingItemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_viewer);
        getSupportActionBar().setTitle("Lists");

        accom = findViewById(R.id.accom);
        accom.setVisibility(View.GONE);

        recycler_view = findViewById(R.id.recycler_view);
        dbReference = FirebaseDatabase.getInstance().getReference().child("Accommodation Data").child("Shopping List");
        setRecyclerView();


        addBtn = findViewById(R.id.fabAdd);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),shoppingList.class));
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
        shoppingItemsList = new ArrayList<>();
        adapter = new ShoppingListAdapter(this,shoppingItemsList);
        recycler_view.setAdapter(adapter);

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){


                    ShoppingItems shoppingItems = dataSnapshot.getValue(ShoppingItems.class);
                    shoppingItemsList.add(shoppingItems);

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