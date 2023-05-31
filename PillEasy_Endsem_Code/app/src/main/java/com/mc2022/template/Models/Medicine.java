package com.mc2022.template.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Medicine implements Serializable {
    private String medicine_name;
    private String medicine_type;
    private int duration;
    private ArrayList<Dosage> doses;
    private String startdate;
    private String enddate;
    private boolean notifications;
    private ArrayList<String> days;
    private Dosage dose;

    public Medicine(String medicine_name, String medicine_type, String time, int doses) {
        this.medicine_name = medicine_name;
        this.medicine_type = medicine_type;
        this.dose = new Dosage(medicine_name, time, doses);
    }

    public Medicine(String medicine_name, String medicine_type, int duration, ArrayList<Dosage> doses, String startdate, String enddate, boolean notifications, ArrayList<String> days) {
        this.medicine_name = medicine_name;
        this.medicine_type = medicine_type;
        this.duration = duration;
        this.doses = doses;
        this.startdate = startdate;
        this.enddate = enddate;
        this.notifications = notifications;
        this.days = days;
    }

    public Medicine() {

    }

    public String getMedicine_name() {
        return medicine_name;
    }

    public void setMedicine_name(String medicine_name) {
        this.medicine_name = medicine_name;
    }

    public String getMedicine_type() {
        return medicine_type;
    }

    public void setMedicine_type(String medicine_type) {
        this.medicine_type = medicine_type;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public boolean getNotifications() {
        return notifications;
    }

    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
    }

    public ArrayList<Dosage> getDoses() {
        return doses;
    }

    public void setDoses(ArrayList<Dosage> doses) {
        this.doses = doses;
    }

    public ArrayList<String> getDays() {
        return days;
    }

    public void setDays(ArrayList<String> days) {
        this.days = days;
    }

    public Dosage getDose() {
        return dose;
    }

    public void setDose(Dosage dose) {
        this.dose = dose;
    }

    @Override
    public String toString() {
        String d = days == null ? "" : days.toString();
        return "Medicine{" +
                "medicine_name='" + medicine_name + '\'' +
                ", medicine_type='" + medicine_type + '\'' +
                ", duration=" + duration +
                ", doses=" + doses +
                ", startdate='" + startdate + '\'' +
                ", enddate='" + enddate + '\'' +
                ", notifications=" + notifications +
                ", days=" + d +
                ", dose=" + dose +
                '}';
    }
}
