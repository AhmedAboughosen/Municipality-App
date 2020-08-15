package com.example.mymunicipalityapp.addComplaintActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mymunicipalityapp.R;
import com.example.mymunicipalityapp.network.NetworkUtil;
import com.kofigyan.stateprogressbar.StateProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddComplaintActivity extends AppCompatActivity {

    private static  AddComplaintActivity ourInstance ;

    public @BindView(R.id.toolbar)
    Toolbar toolbar;

    public @BindView(R.id.step_view)
    StateProgressBar stateProgressBar ;

    public UpdateComplaintUI updateComplaintUI;


    private void setInstance(){
        ourInstance =this;
    }


    public static AddComplaintActivity getInstance() {
        return (ourInstance);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_complaint);

        try{
            ButterKnife.bind(this);
            updateComplaintUI = new UpdateComplaintUI();
            setInstance();
            initStepView();
            initFragment();
        }catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(this , "هناك خطأ في التطبيق. يرجى تحديث الصفحة او اعادة المحاولة." , Toast.LENGTH_LONG).show();
        }
    }

    private void initStepView()
    {
        try {
            String[] descriptionData = {"نوع\nالشكوى", "موقع\nالشكوى", "البلدية", "تفاصيل\nالشكوى"};
            stateProgressBar.setStateDescriptionData(descriptionData);
            stateProgressBar.enableAnimationToCurrentState(true);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }



    private void initFragment(){
        try {
            update_view(R.layout.toolbar_compalint_type , new ComplaintTypeFragment());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }



    public void  update_view(int layout , Fragment fragment)
    {

        /*
         *  Check for network connectivity.
         */
        try {
            if(NetworkUtil.isNetworkConnected((ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)))
            {
               updateComplaintUI.update_view(layout   , fragment);
            }
            else {
                NoInternetFragment noInternetViewModel =   new NoInternetFragment();
                noInternetViewModel.setFragment(fragment , layout);
                updateComplaintUI.update_view(layout , noInternetViewModel);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        try{
            updateComplaintUI.onBackPressed();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
