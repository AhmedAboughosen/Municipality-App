package com.example.mymunicipalityapp.searchFragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mymunicipalityapp.R;
import com.example.mymunicipalityapp.addComplaintActivity.AddComplaintActivity;
import com.example.mymunicipalityapp.mainActivity.SearchFragment;
import com.example.mymunicipalityapp.network.NetworkUtil;
import com.example.mymunicipalityapp.util.UpdateFragment;
import com.example.mymunicipalityapp.viewModel.NotFoundFragment;

import java.util.Objects;

public class NotFoundItemFragment extends NotFoundFragment {


    private Fragment fragment;
    private SearchFragment context;



    public void setFragment(Fragment fragment ,SearchFragment context){
        this.fragment = fragment;
        this.context = context;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Check();
    }


    private void Check(){

        retry.setOnClickListener(v -> {
            try {
                if(NetworkUtil.isNetworkConnected((ConnectivityManager)  Objects.requireNonNull(getActivity()).getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)))
                {
                    UpdateFragment.loadFragment( fragment  , R.id.searchpager ,context.getChildFragmentManager());
                }
                else {
                    Toast.makeText(getActivity() ,  "لا يوجد اتصال بالإنترنت ", Toast.LENGTH_LONG).show();
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });
    }
}
