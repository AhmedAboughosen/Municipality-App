package com.example.mymunicipalityapp.addComplaintActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mymunicipalityapp.network.NetworkUtil;
import com.example.mymunicipalityapp.viewModel.BaseNoInternetFragment;


import butterknife.ButterKnife;

public class NoInternetFragment extends BaseNoInternetFragment {


    public NoInternetFragment(){
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this , view);
        CheckInternet();
    }


    private void CheckInternet(){

        check_internet.setOnClickListener(v -> {
            try {
                if(NetworkUtil.isNetworkConnected((ConnectivityManager) AddComplaintActivity.getInstance(). getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)))
                {
                    AddComplaintActivity.getInstance().updateComplaintUI.PopBackStack();
                    AddComplaintActivity.getInstance().update_view(layout , fragment);
                }
                else {
                    Toast.makeText(getActivity() , "لا يوجد اتصال بالإنترنت" , Toast.LENGTH_LONG).show();

                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });
    }

}
