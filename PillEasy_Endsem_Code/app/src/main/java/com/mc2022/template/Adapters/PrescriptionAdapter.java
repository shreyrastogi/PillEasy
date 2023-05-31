package com.mc2022.template.Adapters;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mc2022.template.Activities.ShowPrescription;
import com.mc2022.template.Models.PrescriptionModel;
import com.mc2022.template.R;

import java.util.ArrayList;

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.ViewHolderClass> {
    ArrayList<PrescriptionModel> dataholder;
    public static Drawable here_photo;
    int pos;
    //Bitmap here_photo;

    public PrescriptionAdapter(ArrayList<PrescriptionModel> dataholder) {
        this.dataholder = dataholder;
    }

    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prescription, parent, false);
        return new ViewHolderClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass holder, int position) {
        holder.prescription_image.setImageBitmap(dataholder.get(position).getImage());
        holder.my_doctor.setText(dataholder.get(position).getDoctor_name());
    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder {
        ImageView prescription_image;
        TextView my_doctor;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);

            prescription_image = itemView.findViewById(R.id.prescr);
            my_doctor = itemView.findViewById(R.id.doc_name);

            prescription_image.setOnClickListener(view -> {

                here_photo = prescription_image.getDrawable();
                Log.d("img", String.valueOf(here_photo));
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Intent intent = new Intent(activity, ShowPrescription.class);
                activity.startActivity(intent);
            });

        }
    }
}