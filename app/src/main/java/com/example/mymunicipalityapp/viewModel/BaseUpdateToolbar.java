package com.example.mymunicipalityapp.viewModel;

import android.view.View;

import androidx.appcompat.widget.Toolbar;

public class BaseUpdateToolbar {

    protected Toolbar toolbar;
    protected int layout;


    protected void updateToolbar(View layout){
        try {
            toolbar.removeAllViews();
            toolbar.addView(layout);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
