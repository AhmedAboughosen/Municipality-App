package com.example.mymunicipalityapp.addSuggestionActivity;

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


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CheckInternet();
    }

    private void CheckInternet(){

        check_internet.setOnClickListener(v -> {
            try {

                if(NetworkUtil.isNetworkConnected((ConnectivityManager) AddSuggestionActivity.getInstance(). getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)))
                {
                    AddSuggestionActivity.getInstance().updateSuggestionUI.PopBackStack();
                    AddSuggestionActivity.getInstance().update(layout , fragment);
                }
                else {
                    Toast.makeText(AddSuggestionActivity.getInstance() , "لا يوجد إتصال بالانترنت" , Toast.LENGTH_LONG).show();
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });
    }
}
