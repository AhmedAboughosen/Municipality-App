package com.example.mymunicipalityapp.mainActivity;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mymunicipalityapp.R;
import com.example.mymunicipalityapp.adapter.NewsAdapter;
import com.example.mymunicipalityapp.model.NewsModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    private ArrayList<NewsModel> newsList ;
    @BindView(R.id.Recycler_list)
    RecyclerView recyclerView;
    private NewsAdapter customAdapter ;


    public NewsFragment() {
        // Required empty public constructor
        newsList = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.layout_base__swiperefres_recyclerview, container, false);
        try {
            ButterKnife.bind(this , view);
            setData();
            initRecyclerView();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return view;
    }


    private  void initRecyclerView(){
        try {

            // set main_logo LinearLayoutManager with default vertical orientation
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setHasFixedSize(true);
            // call the constructor of CustomAdapter to send the reference and data to Adapter
            this.customAdapter = new NewsAdapter(getActivity(),newsList);
            recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }


    private void setData()
    {

        NewsModel news = new NewsModel("اعرف حقوقك" ,"عامة"   , "1 يناير 2018"  , "" , R.drawable.crime  ,"اعرف حقوقك\n" + "\n" , "لتعزيز تمكين المشتركين من اتخاذ قرارات مدروسة وتحديد حقوق المستهلكين والتزامات المشغلين المرخص لهم فيما يتعلق بهذه الحقوق؛ تهدف حملة \"اعرف حقوقك\" إلى رفع مستوى الوعي لدى المستهلكين بالحقوق الواردة في اللوائح التنظيمية الصادرة مؤخرًا عن الهيئة بشأن: المنازعات الخاصة بالمستهلك وحماية مستهلكي خدمات الاتصالات.");


        for(int i = 1 ; i < 10 ; ++i){
            newsList.add(news);
        }


    }
}
