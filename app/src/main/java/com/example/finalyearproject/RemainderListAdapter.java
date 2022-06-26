package com.example.finalyearproject;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RemainderListAdapter extends RecyclerView.Adapter<RemainderListAdapter.ViewHolder> {

    Context context;
    List<remainderItems> remainderItemsData;
    ImageButton deleteBtn;


    public RemainderListAdapter(Context context, List<remainderItems> remainderItemsData) {
        this.context = context;
        this.remainderItemsData = remainderItemsData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.remainder_table_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (remainderItemsData != null && remainderItemsData.size() >0) {


            remainderItems model = remainderItemsData.get(position);



            holder.remAccom.setText(model.getItemAccom());
            holder.remUtility.setText(model.getUtility());
            holder.remDueDate.setText(model.getDueDate());
            holder.remCost.setText(model.getCost());


        } else {
            return;
        }

    }

    @Override
    public int getItemCount() {
        return remainderItemsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView remUtility, remDueDate, remCost, remAccom;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            remUtility = itemView.findViewById(R.id.remUtility);
            remDueDate = itemView.findViewById(R.id.remDate);
            remCost = itemView.findViewById(R.id.remCost);
            remAccom = itemView.findViewById(R.id.remAccom);

            remAccom.setVisibility(View.GONE);

            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    remUtility.setPaintFlags(remUtility.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    remCost.setPaintFlags(remCost.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    remDueDate.setPaintFlags(remDueDate.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
            });

        }
    }
}
