package com.example.mymunicipalityapp.searchFragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mymunicipalityapp.R;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoItemFragment extends Fragment {

    @BindView(R.id.no)
    TextView No;
    @BindView(R.id.unhappy)
    LottieAnimationView unhappy;

    private boolean items ;
    public NoItemFragment(boolean items) {
        // Required empty public constructor
        this.items = items;
    }



    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_no_item, container, false);
        ButterKnife.bind(this , view);
        unhappy.setAnimation(R.raw.noitem);

        if( !this.items ){
            No.setText("لا يوجد لديك مقترحات");
        }
        return view;
    }

}
