package com.example.mymunicipalityapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.mymunicipalityapp.R;
import com.example.mymunicipalityapp.model.HomeItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeAdapter  extends PagerAdapter {

    private ArrayList<HomeItem> homeItems;
    private Context context;

    @BindView(R.id.main_home_image)
    ImageView main_home_image;



    public HomeAdapter(Context context , ArrayList<HomeItem> homeItems){

        this.context = context;
        this.homeItems = homeItems;
    }




    @Override
    public int getCount() {
        return this.homeItems.size();
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view ==  object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view ;
        try {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            view = layoutInflater.inflate(R.layout.item_layout_home, container ,false);
            ButterKnife.bind(this , view);
            main_home_image.setImageResource(homeItems.get(position).getImageView());
            container.addView(view);
            return view;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  null;

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
