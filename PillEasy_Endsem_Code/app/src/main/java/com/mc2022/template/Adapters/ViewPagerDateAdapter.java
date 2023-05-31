package com.mc2022.template.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mc2022.template.Database.SQLiteHelper;
import com.mc2022.template.Models.Medicine;
import com.mc2022.template.R;

import java.util.ArrayList;

public class ViewPagerDateAdapter extends RecyclerView.Adapter<ViewPagerDateAdapter.DateAdapterViewHolder> {

    private ArrayList<String> dates;
    private Context context;
    SQLiteHelper helper;
    static MedicineAdapter adapter;

    public ViewPagerDateAdapter(Context context, ArrayList<String> dates) {
        this.context = context;
        this.dates = dates;
        helper = new SQLiteHelper(context);
    }

    @NonNull
    @Override
    public DateAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.date_layout, parent, false);
        return new DateAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateAdapterViewHolder holder, int position) {
        holder.txtDay.setText(dates.get(position));
        ArrayList<Medicine> filteredMedicine = helper.getMedicines(dates.get(position));
        if (filteredMedicine == null || filteredMedicine.size() == 0) {
            holder.recyclerViewMedicinesPerDay.setVisibility(View.INVISIBLE);
            holder.layout.setVisibility(View.VISIBLE);
        } else {
            holder.recyclerViewMedicinesPerDay.setVisibility(View.VISIBLE);
            holder.layout.setVisibility(View.INVISIBLE);
            adapter = new MedicineAdapter(context, filteredMedicine, position);
            holder.recyclerViewMedicinesPerDay.setLayoutManager(new LinearLayoutManager(context));
            holder.recyclerViewMedicinesPerDay.setAdapter(adapter);
        }
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public class DateAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView txtDay;
        RecyclerView recyclerViewMedicinesPerDay;
        RelativeLayout layout;

        public DateAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.txtNoMedicineLayout);
            txtDay = itemView.findViewById(R.id.txtDay);
            recyclerViewMedicinesPerDay = itemView.findViewById(R.id.recyclerViewMedicinesPerDay);
        }
    }
}
