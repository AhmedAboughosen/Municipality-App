package com.example.mymunicipalityapp.addComplaintActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mymunicipalityapp.network.NetworkUtil;
import com.example.mymunicipalityapp.viewModel.NotFoundFragment;


public class NotFoundComplaint extends NotFoundFragment {

    private Fragment fragment;
    private int layout;


    public void setFragment(Fragment fragment , int layout){
        this.fragment = fragment;
        this.layout = layout;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Check();
    }


    private void Check(){

        retry.setOnClickListener(v -> {
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
