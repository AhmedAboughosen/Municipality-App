package com.example.mymunicipalityapp.model.suggestionModel;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mymunicipalityapp.model.ObjectTypeModel;

public class SuggestionTypeModel extends ObjectTypeModel {

    private  Context mCtx;

    private  final String SHARED_PREF_NAME = "SuggestionTypeModel";
    private  final String KEY_Title = "title";
    private  final String KEY_ID = "id";


    public  SuggestionTypeModel(Context context){
        mCtx = context;
    }


    public SuggestionTypeModel(){

    }

    public boolean putData(String title, int id){

        try {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(KEY_Title, title);
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
            objects[0] = sharedPreferences.getString(KEY_Title, null);
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
}
