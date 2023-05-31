package com.mc2022.template.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mc2022.template.Models.UserBPSugar;
import com.mc2022.template.R;

import java.util.ArrayList;

public class BPSugarAdapter extends RecyclerView.Adapter<BPSugarAdapter.myviewholder> {
    ArrayList<UserBPSugar> dataholder;

    public BPSugarAdapter(ArrayList<UserBPSugar> dataholder) {
        this.dataholder = dataholder;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow, parent, false);
        return new myviewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.date.setText(dataholder.get(position).getDateMain());
        holder.dweight.setText(dataholder.get(position).getWeight());
        holder.dbp.setText(dataholder.get(position).getBp());
        holder.dsugar.setText(dataholder.get(position).getSugar());
    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    static class myviewholder extends RecyclerView.ViewHolder {

        TextView date, dweight, dbp, dsugar;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.dateDisplay);
            dweight = (TextView) itemView.findViewById(R.id.displayWeight);
            dbp = (TextView) itemView.findViewById(R.id.displayBp);
            dsugar = (TextView) itemView.findViewById(R.id.displaySugar);


        }

    }
}
