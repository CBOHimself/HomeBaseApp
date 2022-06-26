package com.example.finalyearproject;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AccommodationAdapter extends RecyclerView.Adapter<AccommodationAdapter.ViewHolder> {

    Context context;
    Button callBtn;
 //   private FirebaseAuth fAuth;


    List<UserData> userDataList;
    private static final int REQUEST_CALL = 1;

    public AccommodationAdapter(Context context, List<UserData> userDataList) {
        this.context = context;
        this.userDataList = userDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (userDataList != null && userDataList.size() >0) {


            UserData model = userDataList.get(position);

            String fullName = model.getFirstName() + " " + model.getLastName();

            String photo = model.getPhoto();

   //         Uri photoUri = Uri.parse(photo);


//            Picasso.with(holder.userPfp.getContext()).load(photoUri)
//                    .placeholder(R.drawable.ic_baseline_phone_24)
//                    .error(R.mipmap.ic_launcher)
//                    .into(holder.userPfp);
            holder.username.setText(fullName);
            holder.phoneNo.setText(model.getPhoneNumber());
            holder.address.setText(model.getAddress());
            holder.city.setText(model.getCity());
            holder.country.setText(model.getCountry());
            holder.postcode.setText(model.getPostCode());
            holder.accomId.setText(model.getAccomId());


        } else {
            return;
        }

    }

    @Override
    public int getItemCount() {
        return userDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView username, phoneNo, address, city, country, postcode, accomId;
        ImageView userPfp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userPfp = itemView.findViewById(R.id.userPfp);
            username = itemView.findViewById(R.id.userName);
            phoneNo = itemView.findViewById(R.id.phoneNo);
            address = itemView.findViewById(R.id.address);
            city = itemView.findViewById(R.id.city);
            country = itemView.findViewById(R.id.country);
            postcode = itemView.findViewById(R.id.postcode);
            accomId = itemView.findViewById(R.id.accomId);
            accomId.setVisibility(View.GONE);



            callBtn = itemView.findViewById(R.id.callBtn);
            callBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String number = phoneNo.getText().toString();
                    if (number.trim().length()>0) {
                        if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions((Activity) view.getContext(), new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                        }
                        else {
                            //String number = phoneNumber.getText().toString();;
                            String dial = "tel:" + number;

                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(dial));
                            context.startActivity(intent);
                        }

                    }
                    else {
                        Toast.makeText(view.getContext(), "Error Calling Users", Toast.LENGTH_SHORT).show();
                    }
                }


            });

        }
    }



}
