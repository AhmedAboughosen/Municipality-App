package com.example.mymunicipalityapp.util;

import android.content.Context;
import android.content.Intent;


public class StartActivity {


    public static void StartIntent(Context packageContext, Class<?> cls  ) {
        try {
            Intent intent = new Intent(packageContext , cls);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            packageContext.startActivity(intent);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
