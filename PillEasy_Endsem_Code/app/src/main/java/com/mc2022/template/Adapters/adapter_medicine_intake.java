package com.mc2022.template.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mc2022.template.Models.helper_medicine_intake;
import com.mc2022.template.R;

import java.util.ArrayList;

public class adapter_medicine_intake extends RecyclerView.Adapter<adapter_medicine_intake.Medicine_Intake_ViewHold> {

    ArrayList<helper_medicine_intake> phoneLaocations;
    private final ListItemClickListener itemClickListener;
    static boolean flag = false;
    static CardView previous_card = null;

    public adapter_medicine_intake(ArrayList<helper_medicine_intake> phoneLaocations, ListItemClickListener listener) {
        this.phoneLaocations = phoneLaocations;
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public Medicine_Intake_ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_intake_type, parent, false);
        return new Medicine_Intake_ViewHold(view, itemClickListener);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull Medicine_Intake_ViewHold holder, int position) {

        helper_medicine_intake helper_medicine_intake = phoneLaocations.get(position);
        holder.image.setImageResource(helper_medicine_intake.getImage());
        holder.title.setText(helper_medicine_intake.getTitle());
//        if(selectedIndex != -1 && selectedIndex == position)
//            // holder.cardView.setCardBackgroundColor(R.color.crystal);
//            holder.cardView.performClick();
    }

    @Override
    public int getItemCount() {
        return phoneLaocations.size();

    }

    public interface ListItemClickListener {
        void onphoneListClick(int clickedItemIndex);
    }

    public static class Medicine_Intake_ViewHold extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        TextView title;
        ListItemClickListener listItemClickListener;
        CardView cardView;

        public Medicine_Intake_ViewHold(@NonNull View itemView, ListItemClickListener listItemClickListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.listItemClickListener = listItemClickListener;
            image = itemView.findViewById(R.id.medicine_intake_type_image);
            title = itemView.findViewById(R.id.medicine_intake_type_title);
            cardView = itemView.findViewById(R.id.medicine_intake_card_id);
        }


        @Override
        public void onClick(View v) {
            if (!flag) {
                cardView.setCardBackgroundColor(itemView.getResources().getColor(R.color.lightgray));
                flag = true;
            } else {
                cardView.setCardBackgroundColor(itemView.getResources().getColor(R.color.white));
                flag = false;
            }
            if (previous_card == null)
                previous_card = cardView;
            else {
                previous_card.setCardBackgroundColor(itemView.getResources().getColor(R.color.white));
                previous_card = null;
            }
            listItemClickListener.onphoneListClick(getLayoutPosition());
        }
    }
}

