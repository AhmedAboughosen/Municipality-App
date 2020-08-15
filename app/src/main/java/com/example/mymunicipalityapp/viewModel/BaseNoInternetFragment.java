package com.example.mymunicipalityapp.viewModel;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mymunicipalityapp.R;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseNoInternetFragment extends Fragment {

    protected Fragment fragment;
    protected int layout ;

    public  @BindView(R.id.NoInternetButton)
    Button check_internet;

    @BindView(R.id.nowifi)
    LottieAnimationView nowifi;

    public BaseNoInternetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_base_no_internet, container, false);

        ButterKnife.bind(this , view);
        nowifi.setAnimation(R.raw.no_internet);

        return view;
    }


    public void setFragment(Fragment fragment , int layout){
        this.fragment = fragment;
        this.layout = layout;
    }
}
