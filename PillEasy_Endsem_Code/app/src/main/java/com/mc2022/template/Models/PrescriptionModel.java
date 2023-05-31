package com.mc2022.template.Models;

import android.graphics.Bitmap;

public class PrescriptionModel {

    private Bitmap image;
    private String doctor_name;

    public PrescriptionModel(Bitmap image, String doctor_name) {
        this.image = image;
        this.doctor_name = doctor_name;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}