package com.example.finalyearproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ListFragment extends Fragment {

    public ListFragment() {
        // Required empty public constructor
    }




    Button shoppingListBtn, reminderBtn, accommodationBtn, complaintsBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        complaintsBtn = view.findViewById(R.id.complaints);
        complaintsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(getActivity().getApplicationContext(),ComplaintListViewer.class);
                startActivity(intent);
            }
        });



        shoppingListBtn = view.findViewById(R.id.shoppingList);
        shoppingListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(getActivity().getApplicationContext(),shoppingListViewer.class);
                startActivity(intent);
            }
        });

        reminderBtn = view.findViewById(R.id.reminders);
        reminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(getActivity().getApplicationContext(), remainderListViewer.class);
                startActivity(intent);
            }
        });

        accommodationBtn = view.findViewById(R.id.accommodation);
        accommodationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(getActivity().getApplicationContext(), AccommodationViewer.class);
                startActivity(intent);
            }
        });


        return view;
    }

}