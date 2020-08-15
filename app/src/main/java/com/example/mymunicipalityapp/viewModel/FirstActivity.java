package com.example.mymunicipalityapp.viewModel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mymunicipalityapp.R;
import com.example.mymunicipalityapp.mainActivity.MainActivity;
import com.example.mymunicipalityapp.model.SharedPrefSplashActivityManager;
import com.example.mymunicipalityapp.setupActivity.SetupActivity;
import com.example.mymunicipalityapp.util.StartActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FirstActivity extends AppCompatActivity {


    //Introduce main_logo delay
    private  int WAIT_TIME = 2500;
    private Handler uiHandler;
    @BindView(R.id.load)
    LottieAnimationView load;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        try {
            if(!(new SharedPrefSplashActivityManager(getApplicationContext()).isAliveActivity()))
            {
                Intent();
                return;
            }
            ButterKnife.bind(this);
            load.setAnimation(R.raw.load);
            backgroundThread();
        }catch (Exception ex){
            Toast.makeText(getApplicationContext() , "هناك خطأ في التطبيق. يرجى تحديث الصفحة او اعادة المحاولة." , Toast.LENGTH_LONG).show();
        }
    }



    private void Intent(){
        try {
            StartActivity.StartIntent(FirstActivity.this, SetupActivity.class);
            finish();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }



    public  void backgroundThread(){
        uiHandler = new Handler(); // anything posted to this handler will run on the UI Thread

        final   Runnable onUi = () -> {
            // this will run on the main UI thread
            StartActivity.StartIntent(getApplicationContext() , MainActivity.class );
            finish();
        };

        Runnable background = () -> {
            // This will run on main_logo background thread

            // This is the delay
            try {
                Thread.sleep( WAIT_TIME );
                uiHandler.post( onUi );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        };

        new Thread( background ).start();
    }

}
