package com.example.finalyearproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ComplaintListAdapter extends RecyclerView.Adapter<ComplaintListAdapter.ViewHolder>{
    Context context;
    List<ComplaintsItems> complaintsItemsData;

    public ComplaintListAdapter(Context context, List<ComplaintsItems> complaintsItemsData) {
        this.context = context;
        this.complaintsItemsData = complaintsItemsData;
    }

    @NonNull
    @Override
    public ComplaintListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.complaints_table_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintListAdapter.ViewHolder holder, int position) {

        if (complaintsItemsData != null && complaintsItemsData.size() >0) {


            ComplaintsItems model = complaintsItemsData.get(position);


            holder.userName.setText(model.getPoster());
            holder.complaintText.setText(model.getComplaintPost());
            holder.complaintTime.setText(model.getTimeStamp());


        } else {
            return;
        }


    }

    @Override
    public int getItemCount() {
        return complaintsItemsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName, complaintText, complaintTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.userName);
            complaintText = itemView.findViewById(R.id.complaintText);
            complaintTime = itemView.findViewById(R.id.complaintTime);
        }
    }
}
