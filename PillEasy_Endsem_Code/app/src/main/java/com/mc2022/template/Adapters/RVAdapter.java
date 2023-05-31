package com.mc2022.template.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mc2022.template.Models.PrescriptionModel;
import com.mc2022.template.R;

import java.util.ArrayList;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVViewHolderClass> {

    ArrayList<PrescriptionModel> objectModelClassList;

    public RVAdapter(ArrayList<PrescriptionModel> objectModelClassList) {
        this.objectModelClassList = objectModelClassList;
    }

    @NonNull
    @Override
    public RVViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RVViewHolderClass(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RVViewHolderClass holder, int position) {
        PrescriptionModel objectModelClass = objectModelClassList.get(position);
        holder.image.setImageBitmap(objectModelClass.getImage());
    }

    @Override
    public int getItemCount() {
        return objectModelClassList.size();
    }

    public static class RVViewHolderClass extends RecyclerView.ViewHolder {

        ImageView image;

        public RVViewHolderClass(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imageViewRV);
        }
    }


}
