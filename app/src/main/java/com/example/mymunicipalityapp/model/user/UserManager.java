package com.example.mymunicipalityapp.model.user;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mymunicipalityapp.util.SecurePreferences;

public class UserManager {

    private  Context mCtx;

    private  final String SHARED_PREF_NAME = "UserManager";
    private  final String KEY_FULLNAME = "FullName";
    private  final String KEY_USER_EMAIL = "Email";
    private  final String KEY_USER_NATIONAL_ID = "national_id";
    private  final String KEY_USER_PHONE = "phone_number";


    public UserManager(Context context) {
        mCtx = context;
    }


    public void userLogin(String id,  String Email , String full_name){
        SecurePreferences preferences = new SecurePreferences(mCtx, SHARED_PREF_NAME, "SometopSecretKey1235", true);

        preferences.put(KEY_USER_NATIONAL_ID, id);
        preferences.put(KEY_USER_EMAIL, Email);
        preferences.put(KEY_FULLNAME, full_name);
    }

    public void putPhone(String phone){
        SecurePreferences preferences = new SecurePreferences(mCtx, SHARED_PREF_NAME, "SometopSecretKey1235", true);
        preferences.put(KEY_USER_PHONE, phone);
    }

    public boolean isLoggedIn(){
        SecurePreferences preferences = new SecurePreferences(mCtx, SHARED_PREF_NAME, "SometopSecretKey1235", true);
        return preferences.getString(KEY_USER_NATIONAL_ID) != null;
    }



    public String getFullName(){
        SecurePreferences preferences = new SecurePreferences(mCtx, SHARED_PREF_NAME, "SometopSecretKey1235", true);
        return preferences.getString(KEY_FULLNAME);
    }

    public String getPhoneNumber(){
        SecurePreferences preferences = new SecurePreferences(mCtx, SHARED_PREF_NAME, "SometopSecretKey1235", true);
        return preferences.getString(KEY_USER_PHONE);
    }

    public String getEmail(){
        SecurePreferences preferences = new SecurePreferences(mCtx, SHARED_PREF_NAME, "SometopSecretKey1235", true);
        return preferences.getString(KEY_USER_EMAIL);
    }

    public String getNationalID(){
        SecurePreferences preferences = new SecurePreferences(mCtx, SHARED_PREF_NAME, "SometopSecretKey1235", true);
        return preferences.getString(KEY_USER_NATIONAL_ID);
    }
}
