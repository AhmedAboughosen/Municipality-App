package com.example.mymunicipalityapp.interfaces;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.kofigyan.stateprogressbar.StateProgressBar;

public interface UpdateComplaintSuggestionView {

     void update(StateProgressBar.StateNumber pos, View toolbar);
     void update_view(int layout, Fragment fragment);
     void onBackPressed();
     void update(View view ,StateProgressBar.StateNumber number , int layout );
     void PopBackStack();
}
