package com.example.mymunicipalityapp.util;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class GoolgeServices {

    private static final int ERROR_DIALOG_REQUEST = 9001;

    public static  boolean isServicesOK(AppCompatActivity context){
        try {

            int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);

            if(available == ConnectionResult.SUCCESS){
                //everything is fine and the user can make  requests
                return true;
            }
            else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
                //an error occured but we can resolve it
                Log.d("google", "isServicesOK: an error occured but we can fix it");
                Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(context, available, ERROR_DIALOG_REQUEST);
                dialog.show();
            }else{
                Toast.makeText(context, "يوجد لديك مشكلة في خدمات google services version", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();

        }

        return false;
    }

}
