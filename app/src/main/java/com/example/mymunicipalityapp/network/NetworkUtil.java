package com.example.mymunicipalityapp.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mymunicipalityapp.addComplaintActivity.AddComplaintActivity;
import com.example.mymunicipalityapp.addSuggestionActivity.AddSuggestionActivity;
import com.example.mymunicipalityapp.mainActivity.MainActivity;

public class NetworkUtil {


    /**
     *  Check for network connectivity.
     */

    public static boolean isNetworkConnected(ConnectivityManager manager) {

        try {
            ConnectivityManager cm = manager;

            if (cm != null) {
                if (Build.VERSION.SDK_INT < 23) {
                    final NetworkInfo ni = cm.getActiveNetworkInfo();

                    if (ni != null) {
                        return (ni.isConnected() && (ni.getType() == ConnectivityManager.TYPE_WIFI || ni.getType() == ConnectivityManager.TYPE_MOBILE));
                    }
                } else {
                    final Network n = cm.getActiveNetwork();

                    if (n != null) {
                        final NetworkCapabilities nc = cm.getNetworkCapabilities(n);
                        return (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
                    }
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }


        return false;
    }

}
