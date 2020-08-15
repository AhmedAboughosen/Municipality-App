package com.example.mymunicipalityapp.viewModel;


import android.Manifest;
import android.app.Dialog;

import androidx.fragment.app.Fragment;

import android.content.res.Configuration;
import android.util.Log;
import android.widget.Toast;

import com.example.mymunicipalityapp.R;
import com.example.mymunicipalityapp.addComplaintActivity.ComplaintMapFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseMapFragment extends Fragment  implements  OnMapReadyCallback , EasyPermissions.PermissionCallbacks  {

    protected GoogleMap mMap;
    private static final String TAG = "MapViewModel";
    private Marker mMarker;
    private static final float DEFAULT_ZOOM = 15f;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final int ERROR_DIALOG_REQUEST = 9001;


    public BaseMapFragment() {
        // Required empty public constructor
    }

    protected void requestPermissions(){
        try {
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            if (EasyPermissions.hasPermissions(Objects.requireNonNull(getActivity()), permissions)) {
                initMap();
            }
            else{
                EasyPermissions.requestPermissions(
                        new PermissionRequest.Builder(this, LOCATION_PERMISSION_REQUEST_CODE, permissions)
                                .setRationale(R.string.pre_google_map)
                                .setPositiveButtonText(R.string.allow)
                                .setNegativeButtonText(R.string.not_allow)
                                .setTheme(R.style.AppTheme)
                                .build());
            }

        }catch (Exception ex){
            ex.printStackTrace();
            Log.d(TAG, "Exception requestPermissions 1 : called.");

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions,@NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

    }

    @Override
    public void onPermissionsGranted(int requestCode,@NotNull List<String> list) {
        // Some permissions have been granted
        // ...

        initMap();

    }

    @Override
    public void onPermissionsDenied(int requestCode,@NotNull List<String> list) {
        // Some permissions have been denied
        // ...

    }


    protected void initLanguage(){
        try {
            String languageToLoad = "ar_LY";
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            Objects.requireNonNull(getActivity()).getBaseContext().getResources().updateConfiguration(config,  getActivity().getBaseContext().getResources().getDisplayMetrics());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    private void initMap()
    {
        try {
            Log.d("MapsActivity", "initMap: initializing map");

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment    mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            assert mSupportMapFragment != null;
            mSupportMapFragment.getMapAsync(this);
        }catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            mMap = googleMap;
          if( ComplaintMapFragment.getInstance() != null){
                ComplaintMapFragment.getInstance().Notfiy();
            }
          else{
              LatLng libya = new LatLng(32.87519, 13.18746);

              this.moveCamera(libya);
          }



        }catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }



    protected void moveCamera(LatLng latLng){
        try{
            if(mMarker != null)
            {
                mMarker.remove();
            }

            Log.d("moveCamera", "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );

            mMarker =   mMap.addMarker(new MarkerOptions().position(latLng).title("موقع الشكوى").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
        }catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    protected boolean isServicesOK(){
        try {
            Log.d(TAG, "isServicesOK: checking google services version");

            int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getActivity());

            if(available == ConnectionResult.SUCCESS){
                //everything is fine and the user can make map requests
                Log.d(TAG, "isServicesOK: Google Play Services is working");
                return true;
            }
            else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
                //an error occured but we can resolve it
                Log.d(TAG, "isServicesOK: an error occured but we can fix it");
                Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), available, ERROR_DIALOG_REQUEST);
                dialog.show();
            }else{
                Toast.makeText(getActivity(), "يوجد لديك مشكلة في خدمات google services version", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();

        }

        return false;
    }


}
