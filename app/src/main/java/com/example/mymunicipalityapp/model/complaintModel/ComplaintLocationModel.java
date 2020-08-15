package com.example.mymunicipalityapp.model.complaintModel;

import android.content.Context;
import android.content.SharedPreferences;

public class ComplaintLocationModel {


    private Context mCtx;

    private  final String SHARED_PREF_NAME = "ComplaintLocationModel";
    private  final String KEY_latitude= "latitude";
    private  final String KEY_longitude = "longitude";


    public ComplaintLocationModel(Context context) {
        mCtx = context;
    }



    public boolean putData( float latitude, float longitude){

        try {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putFloat(KEY_latitude, latitude);
            editor.putFloat(KEY_longitude, longitude);

            editor.apply();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }


        return false;
    }


    public float getlatitude(){
        float num = 0;
        try {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            num = sharedPreferences.getFloat(KEY_latitude, 0);

            return num;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return num;
    }

    public float getlongitude(){
        float num = 0;
        try {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            num = sharedPreferences.getFloat(KEY_longitude, 0);

            return num;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return num;
    }
    public  void AllClear(){
        try {
            SharedPreferences settings = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            settings.edit().clear().apply();
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

}
