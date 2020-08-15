package com.example.mymunicipalityapp.viewModel;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.mymunicipalityapp.util.UpdateFragment;
import com.kofigyan.stateprogressbar.StateProgressBar;

public class UpdateComplaintSuggestionUI extends BaseUpdateToolbar {


    protected StateProgressBar stateProgressBar;

    protected Fragment fragment;

    //fragment Operation.
    protected FragmentManager fm;

    protected void updateFragment(FragmentManager fm , int id){
        try {
            this.fm = fm;
            UpdateFragment.loadFragment(this.fragment ,  this.fm  ,id );
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }




    protected void updateStepView(StateProgressBar.StateNumber pos){
        try {
            this.stateProgressBar.setCurrentStateNumber(pos);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
