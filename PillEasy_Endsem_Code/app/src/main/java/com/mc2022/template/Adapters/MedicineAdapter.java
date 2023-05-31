package com.mc2022.template.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.mc2022.template.Activities.AddMedicine;
import com.mc2022.template.Database.SQLiteHelper;
import com.mc2022.template.Models.Medicine;
import com.mc2022.template.R;

import java.util.ArrayList;
import java.util.Hashtable;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder> {

    private final Context context;
    private final ArrayList<Medicine> medicines;
    private final Hashtable<String, Integer> table;
    private final int parentPosition;
    Snackbar snackbar;

    public MedicineAdapter(Context context, ArrayList<Medicine> medicines, int parentPosition) {
        this.context = context;
        this.medicines = medicines;
        this.parentPosition = parentPosition;
        table = new Hashtable<>();
        table.put("Pills", R.drawable.pills);
        table.put("Injection", R.drawable.syringe);
        table.put("Spray", R.drawable.spray);
        table.put("Drops", R.drawable.dropper);
        table.put("Inhaler", R.drawable.inhaler);
        table.put("Powder", R.drawable.powder);
        table.put("Solution", R.drawable.med_solution);
        table.put("Cream", R.drawable.cream_gel_ointment);
    }

    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.medicine_layout, parent, false);
        return new MedicineViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "NonConstantResourceId"})
    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder holder, int position) {
        Medicine temp = medicines.get(position);
        try {
            holder.txtMedicineName.setText(temp.getMedicine_name());
            holder.txtTime.setText("Time: " + temp.getDose().getTiming());
            holder.txtDosage.setText("Dosage: " + temp.getDose().getDose());
            holder.imgType.setImageResource(table.get(temp.getMedicine_type()));
            // Log.e("from Adapter", String.valueOf(table.get(temp.getMedicine_type())));
            holder.medicine_menu_options.setOnClickListener(view -> {
                PopupMenu menu = new PopupMenu(context, holder.medicine_menu_options);
                menu.inflate(R.menu.medicine_options_menu);
                menu.setOnMenuItemClickListener(menuItem -> {
                    switch (menuItem.getItemId()) {
                        case R.id.edit_medicine:
//                            Toast.makeText(context, temp.getMedicine_name() + " is going to be edited", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, AddMedicine.class);
                            intent.putExtra("MedicineName", temp.getMedicine_name());
                            context.startActivity(intent);
                            break;
                        case R.id.delete_medicine:
                            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
                            builder.setTitle("Warning");
                            builder.setMessage("Are you sure you want to delete all previous and incpming data for this medicine?");
                            builder.setPositiveButton("OK", (dialogInterface, i) -> {
                                try {
                                    new SQLiteHelper(context).deleteMedicine(temp.getMedicine_name());
                                    ViewPagerDateAdapter.adapter.notifyItemChanged(parentPosition);
                                    ShowSnackbar("Medicine Deleted", ((Activity) context).findViewById(R.id.activity_main2));
                                } catch (Exception e) {
                                    ShowSnackbar(e.getMessage(), ((Activity) context).findViewById(R.id.activity_main2));
                                }
                            });
                            builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
                            builder.show();
                            // Toast.makeText(context, temp.getMedicine_name() + " is going to be deleted", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    return false;
                });
                menu.show();
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return medicines.size();
    }

    public void ShowSnackbar(String message, View view) {
        snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.setDuration(10000);
        snackbar.show();
        snackbar.setAction("OK", view1 -> snackbar.dismiss());
    }

    public static class MedicineViewHolder extends RecyclerView.ViewHolder {
        TextView txtMedicineName, txtTime, txtDosage, medicine_menu_options;
        ImageView imgType;

        public MedicineViewHolder(@NonNull View itemView) {
            super(itemView);
            imgType = itemView.findViewById(R.id.imgType);
            txtMedicineName = itemView.findViewById(R.id.txtMedicineName);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtDosage = itemView.findViewById(R.id.txtDosage);
            medicine_menu_options = itemView.findViewById(R.id.medicine_menu_options);
        }
    }
}
