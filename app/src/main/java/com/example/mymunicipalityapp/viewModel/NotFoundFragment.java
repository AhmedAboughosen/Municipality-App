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
public class NotFoundFragment extends Fragment {


    @BindView(R.id.unhappy)
    LottieAnimationView icon;
   public  @BindView(R.id.retry)
    Button retry;

    public NotFoundFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_not_found, container, false);
        ButterKnife.bind(this , view);
        icon.setAnimation(R.raw.fetch_data_error);

        return view;
    }

}
