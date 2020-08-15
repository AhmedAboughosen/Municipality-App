package com.example.mymunicipalityapp.model;

public class AllObjectitem {
    private String state;
    private int backgroundColor;
    private int Number;
    private String Date;
    private String Details;
    private String municipality;
    private String Type;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type= type;
    }
}
