package com.example.mymunicipalityapp.addComplaintActivity;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mymunicipalityapp.R;
import com.example.mymunicipalityapp.model.complaintModel.ComplaintLocationModel;
import com.example.mymunicipalityapp.municipalityFragment.MunicipalityFragment;
import com.example.mymunicipalityapp.viewModel.BaseMapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComplaintMapFragment extends BaseMapFragment implements GoogleMap.OnMapClickListener {


    private static ComplaintMapFragment instance;
    private ComplaintLocationModel complaintLocationModel;

    public static ComplaintMapFragment getInstance(){
        return instance;
    }



    public ComplaintMapFragment() {
        // Required empty public constructor
        complaintLocationModel = new ComplaintLocationModel(AddComplaintActivity.getInstance());
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_complaint_map, container, false);

        try {
            if(isServicesOK()){
                instance = this;
                requestPermissions();
                selectLocation(view);
                initLanguage();
            }
        }catch (Exception ex){
            Toast.makeText(getActivity() , "هناك خطأ في التطبيق. يرجى تحديث الصفحة او اعادة المحاولة." , Toast.LENGTH_LONG).show();

        }

        return view;
    }

    public void Notfiy(){
        mMap.setOnMapClickListener(this);
        LatLng libya = new LatLng(32.87519, 13.18746);
        this.moveCamera(libya);
    }


    private void selectLocation(View view){

        Button skip = view.findViewById(R.id.skip);
        skip.setOnClickListener(view1 -> {
            try {
                complaintLocationModel.AllClear();
                complaintLocationModel.putData((float) 0  ,(float) 0 );
                AddComplaintActivity.getInstance().update_view(R.layout.toolbar_municipality_type ,  new MunicipalityFragment(true));
            }catch (Exception ex){
                ex.printStackTrace();
            }

        });

        Button selectlocation = view.findViewById(R.id.selectlocation);

        selectlocation.setOnClickListener(v -> {
            try {

                String country = getAddress(getActivity() , complaintLocationModel.getlatitude() , complaintLocationModel.getlongitude());

                if(country.equals("LY"))
                {
                AddComplaintActivity.getInstance().update_view(R.layout.toolbar_municipality_type ,  new MunicipalityFragment(true));
                }
                else{
                    Toast.makeText(getActivity(),"الرجاء تحديد الموقع داخل حدود دولة ليبيا ",Toast.LENGTH_LONG).show();
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }

        });
    }



    private   String getAddress(Context context, double LATITUDE, double LONGITUDE)
    {
        String country = "";

        try {
            //Set Address
            try {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
                if (addresses != null && addresses.size() > 0)
                {
                    country = addresses.get(0).getCountryCode();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return country;
    }


    public void geoLocate(String text){

        try {
            Geocoder geocoder = new Geocoder(getActivity());
            List<Address> list = new ArrayList<>();
            try
            {
                list = geocoder.getFromLocationName(text, 1);
            }catch (IOException e)
            {
                e.printStackTrace();
                Toast.makeText(getActivity(), "هناك مشكلة في اتصال الإنترنت", Toast.LENGTH_SHORT).show();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }

            if(list.size() > 0){
                Address address = list.get(0);
                this.moveCamera(new LatLng(address.getLatitude() , address.getLongitude()));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        try {
            this.moveCamera(new LatLng(latLng.latitude, latLng.longitude));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    protected void moveCamera(LatLng latLng)
    {
     super.moveCamera(latLng);
        setData(latLng);
    }

    private void setData(LatLng latLng){
        complaintLocationModel.AllClear();
        complaintLocationModel.putData((float) latLng.latitude ,(float) latLng.longitude );
    }

}
