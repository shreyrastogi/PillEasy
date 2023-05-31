package com.mc2022.template.Models;

public class Dosage {
    private String medicine_name;
    private String timing;
    private int dose;

    public Dosage(String medicine_name, String timing, int dose) {
        this.medicine_name = medicine_name;
        this.timing = timing;
        this.dose = dose;
    }

    public String getMedicine_name() {
        return medicine_name;
    }

    public void setMedicine_name(String medicine_name) {
        this.medicine_name = medicine_name;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public int getDose() {
        return dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }

    @Override
    public String toString() {
        return "Dosage{" +
                "medicine_name='" + medicine_name + '\'' +
                ", timing='" + timing + '\'' +
                ", dose=" + dose +
                '}';
    }
}
