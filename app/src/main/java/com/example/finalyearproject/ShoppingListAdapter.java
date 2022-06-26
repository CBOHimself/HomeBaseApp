package com.example.finalyearproject;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {


    Context context;
    List<ShoppingItems> shoppingItems;
    ImageButton deleteBtn;

    public ShoppingListAdapter(Context context, List<ShoppingItems> shoppingItems) {
        this.context = context;
        this.shoppingItems = shoppingItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shopping_table_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (shoppingItems != null && shoppingItems.size() >0) {


            ShoppingItems model = shoppingItems.get(position);



            holder.itemAccom.setText(model.getItemAccom());
            holder.itemName.setText(model.getItemName());
            holder.itemQuantity.setText(model.getItemQuantity());
            holder.itemShopper.setText(model.getItemShopper());


        } else {
            return;
        }

    }

    @Override
    public int getItemCount() {
        return shoppingItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemName, itemQuantity, itemShopper, itemAccom;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemName);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
            itemShopper = itemView.findViewById(R.id.itemShopper);
            itemAccom = itemView.findViewById(R.id.itemAccom);
            itemAccom.setVisibility(View.GONE);

            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemName.setPaintFlags(itemName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    itemQuantity.setPaintFlags(itemQuantity.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    itemShopper.setPaintFlags(itemShopper.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
            });


        }
    }






}
