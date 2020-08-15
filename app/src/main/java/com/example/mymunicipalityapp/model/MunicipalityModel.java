package com.example.mymunicipalityapp.model;

import android.content.Context;
import android.content.SharedPreferences;

public class MunicipalityModel {

    private String municipality_name;
    private double longitude;
    private double latitude;
    private int id;


    private Context mCtx;

    private  final String SHARED_PREF_NAME = "MunicipalityModel";
    private  final String KEY_municipality_name= "municipality_name";
    private  final String KEY_ID = "id";


    public MunicipalityModel(Context context) {
        mCtx = context;
    }

    public MunicipalityModel() {
    }

    public boolean putData(String municipality_name, int id){

        try {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(KEY_municipality_name, municipality_name);
            editor.putInt(KEY_ID, id);

            editor.apply();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }


        return false;
    }

    public Object[] getData(){
        try {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            Object[] objects = new Object[2];
            objects[0] = sharedPreferences.getString(KEY_municipality_name, null);
            objects[1] = sharedPreferences.getInt(KEY_ID, 0);
            return objects;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public  void AllClear(){
        try {
            SharedPreferences settings = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            settings.edit().clear().apply();
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public String getMunicipality_name() {
        return municipality_name;
    }

    public void setMunicipality_name(String municipality_name) {
        this.municipality_name = municipality_name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
