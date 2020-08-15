package com.example.mymunicipalityapp.model.complaintModel;

import android.content.Context;
import android.content.SharedPreferences;


public class DetailsCompalintManager {
    private  Context mCtx;

    private  final String SHARED_PREF_NAME = "DetailsCompalintManager";
    private  final String KEY_DESCRIPTION = "description";
    private  final String KEY_USER_FILE = "file";
    private  final String KEY_IMAGE = "photo";


    public DetailsCompalintManager(Context context) {
        this.mCtx = context;
    }

    public boolean putData(String description, String file, String photo){

        try {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(KEY_DESCRIPTION, description);
            editor.putString(KEY_USER_FILE, file);
            editor.putString(KEY_IMAGE, photo);

            editor.apply();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }


        return false;
    }

    public String getDescription(){
        try {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            return sharedPreferences.getString(KEY_DESCRIPTION, null);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public String getFiles(){
        try {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            return sharedPreferences.getString(KEY_USER_FILE, null);
        }catch (Exception ex){
            ex.printStackTrace();
        }
          return null;
    }

    public String getImages(){
        try {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            return sharedPreferences.getString(KEY_IMAGE, null);

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
}
