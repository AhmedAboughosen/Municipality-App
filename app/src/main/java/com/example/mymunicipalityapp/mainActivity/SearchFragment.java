package com.example.mymunicipalityapp.mainActivity;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mymunicipalityapp.R;
import com.example.mymunicipalityapp.searchFragment.AllComplaintFragment;
import com.example.mymunicipalityapp.searchFragment.AllSuggestionFragment;
import com.example.mymunicipalityapp.util.UpdateFragment;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    public @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_search, container, false);
        try{
            ButterKnife.bind(this , view);
            iniTabLayout();
            initFragment();
        }catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(getActivity() , "هناك خطأ في التطبيق. يرجى تحديث الصفحة او اعادة المحاولة." , Toast.LENGTH_LONG).show();
        }
        return  view;
    }

    private void initFragment(){
        try {

            UpdateFragment.loadFragment(new AllComplaintFragment(this)  , R.id.searchpager ,  getChildFragmentManager());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    private void iniTabLayout(){
        try {
            AddTab(getString(R.string.suggestion_title) , 0);
            AddTab( getString(R.string.complaint_title)  , 1);
            Objects.requireNonNull(tabLayout.getTabAt(1)).select();
            TabSelected();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    //this method is used to add tab to TabLayout
    private void AddTab( String text , int position){
        try {
            TabLayout.Tab Tab = tabLayout.newTab();
            Tab.setText(text);
            tabLayout.addTab(Tab , position ); // add  the tab at in the TabLayout
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void TabSelected(){

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // get the current selected tab's position and replace the fragment accordingly
                try {
                    switch (tab.getPosition()) {
                        case 0:
                            updateAllSuggestion();
                            break;
                        case 1:
                            updateAllComplaint();
                            break;
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 1:

                        break;
                    case 0:
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void updateAllComplaint(){
        UpdateFragment.loadFragment(new AllComplaintFragment(this) ,  R.id.searchpager ,  getChildFragmentManager());
    }
    private void updateAllSuggestion(){
        UpdateFragment.loadFragment(new AllSuggestionFragment(this) ,   R.id.searchpager , getChildFragmentManager());
    }
}
