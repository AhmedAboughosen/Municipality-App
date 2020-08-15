package com.example.mymunicipalityapp.mainActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mymunicipalityapp.network.NetworkUtil;
import com.example.mymunicipalityapp.viewModel.BaseNoInternetFragment;

public class NoInternetFragment extends BaseNoInternetFragment {


    private String  text;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CheckInternet();
    }


    public void setText(String  text){
        this.text = text;
    }


    private void CheckInternet(){

        check_internet.setOnClickListener(v -> {
            try {
                if(NetworkUtil.isNetworkConnected((ConnectivityManager)MainActivity.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE))){
                    MainActivity.getInstance().updateview(fragment , layout , text);
                    return;
                }
                Toast.makeText(getActivity() ,  "لا يوجد اتصال بالإنترنت ", Toast.LENGTH_LONG).show();


            }catch (Exception ex){
                ex.printStackTrace();
            }
        });
    }


}
