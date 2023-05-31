package com.mc2022.template.Models;


public class UserBPSugar {

    String dateMain, weight, bp, sugar;


    public UserBPSugar(String weight, String bp, String sugar, String dateMain) {
        this.dateMain = dateMain;
        this.weight = weight;
        this.bp = bp;
        this.sugar = sugar;
    }

    public String getDateMain() {
        return dateMain;
    }

    public void setDateMain(String dateMain) {
        this.dateMain = dateMain;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBp() {
        return bp;
    }

    public void setBp(String bp) {
        this.bp = bp;
    }

    public String getSugar() {
        return sugar;
    }

    public void setSugar(String sugar) {
        this.sugar = sugar;
    }
}
