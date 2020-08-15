package com.example.mymunicipalityapp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mymunicipalityapp.R;
import com.example.mymunicipalityapp.model.SetupScreenItem;
import com.example.mymunicipalityapp.viewModel.TextViewEx;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SlideAdapter extends PagerAdapter {


    private Context context;


    private ArrayList<SetupScreenItem> setupScreenItems;

    @BindView(R.id.main_icon)
    LottieAnimationView main_icon;
    @BindView(R.id.heading)
    TextView heading;
    @BindView(R.id.details)
    TextViewEx details;


    public SlideAdapter(Context context , ArrayList<SetupScreenItem> setupScreenItems){

        this.context = context;
        this.setupScreenItems = setupScreenItems;
    }

    @Override
    public int getCount() {
        return this.setupScreenItems.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view ==  o;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = null;
        try {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = Objects.requireNonNull(layoutInflater).inflate(R.layout.item_layout_onboarding, container ,false);
            ButterKnife.bind(this , view);
            details.setText(setupScreenItems.get(position).getDescription(), true); // true: enables justification
            heading.setText(setupScreenItems.get(position).getTitle());
            main_icon.setAnimation(setupScreenItems.get(position).getImage());
            container.addView(view);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        try {
            container.removeView((LinearLayout) object);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}

