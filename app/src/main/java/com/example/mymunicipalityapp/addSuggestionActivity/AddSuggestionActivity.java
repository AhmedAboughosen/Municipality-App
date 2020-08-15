package com.example.mymunicipalityapp.addSuggestionActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.example.mymunicipalityapp.R;
import com.example.mymunicipalityapp.network.NetworkUtil;
import com.kofigyan.stateprogressbar.StateProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddSuggestionActivity extends AppCompatActivity {

    private static  AddSuggestionActivity ourInstance ;

    public UpdateSuggestionUI updateSuggestionUI;

    public @BindView(R.id.toolbar)
    Toolbar toolbar;

    public @BindView(R.id.step_view)
    StateProgressBar stateProgressBar ;

    private void setInstance(){
        ourInstance =this;
    }


    public static AddSuggestionActivity getInstance() {
        return (ourInstance);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_suggestion);
        try {
            ButterKnife.bind(this);
            updateSuggestionUI = new UpdateSuggestionUI();
            setInstance();
            initStepView();
            initFragment();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    private void initStepView()
    {
        try
        {
            String[] descriptionData = {"نوع\nالمقترح","البلدية", "تفاصيل\nالمقترح"};
            stateProgressBar.setMaxStateNumber(StateProgressBar.StateNumber.THREE);
            stateProgressBar.setStateDescriptionData(descriptionData);
            stateProgressBar.enableAnimationToCurrentState(true);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void initFragment(){
        try {
            update(R.layout.toolbar_suggestion_type , new SuggestionTypeFragment());
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }


    /**
     *  update fragment and toolbar and stepview.
     */

    public void update(int layout , Fragment fragment)
    {
        /*
         *  Check for network connectivity.
         */
        try {
            if(NetworkUtil.isNetworkConnected((ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)))
            {

                updateSuggestionUI.update_view(layout , fragment);
            }
            else {
                NoInternetFragment noInternetViewModel =   new NoInternetFragment();
                noInternetViewModel.setFragment( fragment , layout);
                updateSuggestionUI.update_view(layout  , noInternetViewModel);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        try{
            updateSuggestionUI.onBackPressed();
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
