package com.mc2022.template.Activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.mc2022.template.Adapters.PrescriptionAdapter;
import com.mc2022.template.R;

public class ShowPrescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_prescription);
        ImageView prescription_img = findViewById(R.id.imageView6);
        Drawable got_image_here = PrescriptionAdapter.here_photo;
        prescription_img.setImageDrawable(got_image_here);
        //prescription_img.setI(got_image_here);

    }
}