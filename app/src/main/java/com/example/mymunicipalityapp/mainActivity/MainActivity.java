package com.example.mymunicipalityapp.mainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mymunicipalityapp.R;
import com.example.mymunicipalityapp.RegistrationActivity.UserDataActivity;
import com.example.mymunicipalityapp.model.user.UserManager;
import com.example.mymunicipalityapp.network.NetworkUtil;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    @BindView(R.id.navigation)
    BottomNavigationViewEx navigation ;

    public @BindView(R.id.toolbar)
    Toolbar toolbar;
    private int layout;

    private  static MainActivity ourinstance ;

    public static MainActivity getInstance(){
        return ourinstance;
    }

    private UpdateMainUI
    updateMainUI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            ButterKnife.bind(this);
            this.layout = R.layout.toolbar_main_view;
            ourinstance = this;
            initNavigation();
            updateMainUI = new UpdateMainUI();
            updateMainUI.update_view(this.layout , new HomeFragment() , "الصفحة الرئيسية");

        }catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(getApplicationContext() , "هناك خطأ في التطبيق. يرجى تحديث الصفحة او اعادة المحاولة." , Toast.LENGTH_LONG).show();
        }
    }




    private void initNavigation(){
        navigation.setSelectedItemId(R.id.navigation_home);
        navigation.enableAnimation(true);
        navigation.enableShiftingMode(true);
        navigation.enableItemShiftingMode(false);


        navigation.setOnNavigationItemSelectedListener(menuItem -> {
            try{
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        fragment = new HomeFragment();
                        updateMainUI.update_view( this.layout , fragment , "الصفحة الرئيسية");
                        return true;
                    case R.id.navigation_add:
                        fragment = new AddFragment();
                      updateMainUI.update_view(this.layout , fragment , "تقديم");
                        return true;
                    case R.id.navigation_search:
                        fragment = new SearchFragment();
                       updateview(fragment ,this.layout, "متابعة");
                        return true;
                    case R.id.navigation_news:
                        fragment = new NewsFragment();
                        updateview(fragment , this.layout, "حملات التوعوية");
                        return true;
                    case R.id.navigation_setting:
                      setPopupMenu();
                        return true;
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }

            return false;
        });

    }

    private void setPopupMenu(){
        try {
            View menuItemView = this.findViewById(R.id.navigation_setting);
            PopupMenu popup = new PopupMenu(this , menuItemView);
            popup.setOnMenuItemClickListener(MainActivity.this);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.popup_menu, popup.getMenu());
            popup.show();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aboutus:
                // do your code
                updateMainUI.update_view( this.layout , new AboutAppFragment() , "معلومات التطبيق");
                return true;
            case R.id.callus:
                // do your code
                updateMainUI.update_view(  this.layout , new CallusFragment(), "اتصل بنا");
                return true;

            default:
                return false;
        }
    }


    public void updateview(Fragment fragment , int layout , String text){

        /*
         *  Check for network connectivity.
         */
        try {
            if(NetworkUtil.isNetworkConnected((ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)))
            {
                updateMainUI.update_view( layout , fragment , text);
            }
            else {
                NoInternetFragment noInternetFragment = new NoInternetFragment();
                noInternetFragment.setFragment(fragment , layout );
                noInternetFragment.setText(text);
                updateMainUI.update_view( layout , noInternetFragment , text);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        if(!new UserManager(this).isLoggedIn()){
            UserDataIntent();
        }
    }

    private void UserDataIntent() {
        Intent intent = new Intent(MainActivity.this, UserDataActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}
