package com.example.mymunicipalityapp.model.suggestionModel;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mymunicipalityapp.model.DetailsModel;

public class SuggestionDetailsModel extends DetailsModel {


    private Context mCtx;

    private  final String SHARED_PREF_NAME = "SuggestionDetailsModel";
    private  final String KEY_DESCRIPTION = "description";
    private  final String KEY_PHOTO = "photo";
    private  final String KEY_FILE = "file";


    public  SuggestionDetailsModel(Context context){
        mCtx = context;
    }





    public boolean putData(String description, String file , String photo){

        try {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(KEY_DESCRIPTION, description);
            editor.putString(KEY_PHOTO, photo);
            editor.putString(KEY_FILE, file);

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
            Object[] objects = new Object[3];
            objects[0] = sharedPreferences.getString(KEY_DESCRIPTION, null);
            objects[1] = sharedPreferences.getString(KEY_PHOTO, null);
            objects[2] = sharedPreferences.getString(KEY_FILE, null);
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
}
