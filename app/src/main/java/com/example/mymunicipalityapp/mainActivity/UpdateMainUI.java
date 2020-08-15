package com.example.mymunicipalityapp.mainActivity;

import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mymunicipalityapp.R;
import com.example.mymunicipalityapp.util.UpdateFragment;
import com.example.mymunicipalityapp.viewModel.BaseUpdateToolbar;

public class UpdateMainUI extends BaseUpdateToolbar {


    protected Fragment fragment;


    private void update(View layout , String text){
        try {
            TextView tilte = layout.findViewById(R.id.text_view_title);
            tilte.setText(text);
            updateToolbar(layout);
            UpdateFragment.loadFragment(this.fragment   ,R.id.frame_container ,MainActivity.getInstance().getSupportFragmentManager());

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void update_view(int layout, Fragment fragment , String text )
    {
        try {
            this.update_view( layout ,fragment);
            update(MainActivity.getInstance().getLayoutInflater().inflate( this.layout , null) , text);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void update_view(int layout, Fragment fragment ) {
        try {
            this.layout = layout;
            this.fragment = fragment;
            this.toolbar =  MainActivity.getInstance().toolbar;
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
