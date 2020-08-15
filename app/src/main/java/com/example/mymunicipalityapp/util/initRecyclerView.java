package com.example.mymunicipalityapp.util;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class initRecyclerView {

    public static   void initView(RecyclerView recyclerView , Context context , RecyclerView.Adapter adapter){
        try {
            // set main_logo LinearLayoutManager with default vertical orientation
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            // call the constructor of CustomAdapter to send the reference and data to Adapter
            recyclerView.setAdapter(adapter); // set the Adapter to RecyclerView
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
