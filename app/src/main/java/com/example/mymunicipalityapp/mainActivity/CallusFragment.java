package com.example.mymunicipalityapp.mainActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.Toast;

import com.example.mymunicipalityapp.R;
import com.example.mymunicipalityapp.viewModel.BaseMapFragment;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CallusFragment extends BaseMapFragment {



    @BindView(R.id.call_phone)
    ImageView phone ;
    @BindView(R.id.email) ImageView email;




    public CallusFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_callus, container, false);

        try {
            if(isServicesOK()){
                requestPermissions();
                ButterKnife.bind(this , view);
                prepareforIntent();
            }
        }catch (Exception ex){
            Toast.makeText(getActivity() , "هناك خطأ في التطبيق. يرجى تحديث الصفحة او اعادة المحاولة." , Toast.LENGTH_LONG).show();
        }

        return view;
    }

    private void prepareforIntent(){
        try {
            email.setOnClickListener(v -> {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","1515@outlook.com", null));
                startActivity(Intent.createChooser(emailIntent, "ارسل بريد اكتروني"));
            });

            phone.setOnClickListener(v -> {
                String phone = "1515";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);

            });
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }




}
