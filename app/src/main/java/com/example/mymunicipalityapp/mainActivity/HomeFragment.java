package com.example.mymunicipalityapp.mainActivity;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mymunicipalityapp.R;
import com.example.mymunicipalityapp.adapter.HomeAdapter;
import com.example.mymunicipalityapp.model.HomeItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    @BindView(R.id.slideviewpager)
    ViewPager viewPager;

    @BindView(R.id.linear)
    LinearLayout my_linear_layout;

    @BindView(R.id.image0)
    TextView image0;

    @BindView(R.id.image1)
    TextView image1;

    @BindView(R.id.image2)
    TextView image2;

    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        try {


            ButterKnife.bind(this , view);
            initviewPager();
            initCardView();


        }catch (Exception ex){
            ex.printStackTrace();
        }

        return view;

    }



    private void initviewPager(){

        try {

            //Data of view pager
            ArrayList<HomeItem> homeItems = new ArrayList<>();

            homeItems.add(new HomeItem( R.drawable.home));
            homeItems.add(new HomeItem(R.drawable.home));
            homeItems.add(new HomeItem( R.drawable.home));

            HomeAdapter homeAdapter = new HomeAdapter(getActivity(),homeItems );


            viewPager.setAdapter(homeAdapter);

            viewPager.setCurrentItem(3);

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                public void onPageScrollStateChanged(int state) {}
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

                public void onPageSelected(int position) {
                    // Check if this is the page you want.

                    try {
                        image2.setBackgroundResource(position == 2 ? R.drawable.indicator_selected : R.drawable.indicator_default);
                        image1.setBackgroundResource(position == 1 ? R.drawable.indicator_selected : R.drawable.indicator_default);
                        image0.setBackgroundResource(position == 0 ? R.drawable.indicator_selected : R.drawable.indicator_default);
                    }catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }

                }
            });

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void initCardView()
    {
        inflate(R.drawable.ic_care  , getString(R.string.care)  , "ستجد كل ما يهمك في معرفة الحملات التوعوية الخاصة بالمواطنين");
        inflate(R.drawable.ic_complaint  , getString(R.string.complaint)  , "بإمكانك توثيق الشكوى بالإبلاغ عنها بطريقة سهله و سلسة");
        inflate(R.drawable.ic_lightbulb  , getString(R.string.ideas)  , "صندوق مقترحات لاستقبال الأفكار التي من شأنها تطوير الخدمات في جهات مختلفة");
        inflate(R.drawable.ic_bell  , getString(R.string.follow)  , "أصبح الأن بإمكانك متابعة الشكوى و المقترحات");
    }


    private void inflate(int image , String title , String sub_title)
    {
        try {
            View   service_layout = LayoutInflater.from(getActivity()).inflate(R.layout.service_layout,null);

            TextView textView_title = service_layout.findViewById(R.id.title);
            TextView textView__sub_title = service_layout.findViewById(R.id.sub_title);
            ImageView imageView =  service_layout.findViewById(R.id.main_image);
            textView_title.setText(title);
            textView__sub_title.setText(sub_title);
            imageView.setImageResource(image);

            my_linear_layout.addView(service_layout);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
