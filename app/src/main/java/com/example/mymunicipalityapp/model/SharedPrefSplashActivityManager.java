package com.example.mymunicipalityapp.model;


import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefSplashActivityManager {

    private  Context mCtx;

    private  final String SHARED_PREF_NAME = "onboardingActivity";
    private  final String KEY_ACTIVITY = "acvtivity";


    public SharedPrefSplashActivityManager(Context context) {
        mCtx = context;
    }


    public void setActivity(String state){

        try {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(KEY_ACTIVITY, state);

            editor.apply();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    public boolean isAliveActivity(){
        try {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
                return sharedPreferences.getString(KEY_ACTIVITY, null) != null;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
}
