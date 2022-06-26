package com.example.finalyearproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;


public class ProfileFragment extends Fragment {


    private TextView displayAccomodationId, dispEmail, dispFullname, dispPhone;
    private ImageView dispPfp;
    private Button logOutBtn, settingsBtn;
    private String firstName, lastName, email, phoneNo, accommodation, photo;

    public ProfileFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        displayAccomodationId = view.findViewById(R.id.displayAccommodationId);
        dispEmail = view.findViewById(R.id.displayEmail);
        dispFullname = view.findViewById(R.id.displayFullname);
        dispPhone = view.findViewById(R.id.displayPhone);
        dispPfp = view.findViewById(R.id.displayPfp);
        logOutBtn = view.findViewById(R.id.logOut);
        settingsBtn = view.findViewById(R.id.accountSettings);


        logOutBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity().getApplicationContext(),LogIn.class));
                getActivity().finish();
            }
        } );



        settingsBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (firstName != null) {
                    startActivity(new Intent(getActivity().getApplicationContext(),UserProfileSettings.class));
                    getActivity().finish();
                }
                else {
                    Toast.makeText(getActivity(),"Unable to retrieve details", Toast.LENGTH_SHORT).show();
                }
                //startActivity(new Intent(getActivity().getApplicationContext(),UserProfileSettings.class));
                //getActivity().finish();

            }
        } );



        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = fAuth.getCurrentUser();

        if (currentUser == null) {
            Toast.makeText(getActivity(), "Could not obtain user details!", Toast.LENGTH_SHORT).show();
        }else {
            getUserData(currentUser);
        }

        return view;
    }

    private void getUserData(FirebaseUser currentUser) {
        String userID = currentUser.getUid();

        DatabaseReference referenceUser = FirebaseDatabase.getInstance().getReference("User Data");

        referenceUser.child(userID).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                StoreUserData readUserInfo = snapshot.getValue(StoreUserData.class);
                if (readUserInfo != null) {

                    firstName = readUserInfo.firstName;
                    email = currentUser.getEmail();
                    lastName = readUserInfo.lastName;
                    phoneNo = readUserInfo.phoneNumber;
                    accommodation = readUserInfo.accomId;
                    photo = readUserInfo.photo;

                    dispEmail.setText(email);
                    dispFullname.setText(firstName + " " + lastName);
                    dispPhone.setText(phoneNo);
                    displayAccomodationId.setText(accommodation);


                    Uri uri = currentUser.getPhotoUrl();
                    Picasso.with(getActivity()).load(uri).fit().centerCrop().into(dispPfp);
                    Picasso.with(getActivity()).load(uri).into(dispPfp);



                }   else {
                    Toast.makeText(getActivity(), "An Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error Occurred with authentication", Toast.LENGTH_SHORT).show();
            }
        } );


    }

        public class CropSquareTransformation implements Transformation {
            @Override
            public Bitmap transform(Bitmap source) {
                int size = Math.min(source.getWidth(), source.getHeight());
                int x = (source.getWidth() - size) / 2;
                int y = (source.getHeight() - size) / 2;
                Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
                if (result != source) {
                    source.recycle();
                }
                return result;
            }

            @Override
            public String key() {
                return null;
            }
        }

    }