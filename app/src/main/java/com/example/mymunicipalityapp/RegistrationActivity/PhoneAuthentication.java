package com.example.mymunicipalityapp.RegistrationActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;
import com.example.mymunicipalityapp.R;
import com.example.mymunicipalityapp.network.NetworkUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Objects;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.mymunicipalityapp.util.StringUtil.isEmptyString;

public class PhoneAuthentication extends AppCompatActivity {




    @BindView(R.id.phone_number)
    TextInputEditText  phone_number;

    @BindView(R.id.login_proceed)
    FloatingActionButton login_proceed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_authentication);

        try{
            ButterKnife.bind(this);
            initPhoneNumber();
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void initPhoneNumber(){

        login_proceed.setOnClickListener(view -> {

            try {
                if(NetworkUtil.isNetworkConnected((ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)))
                {
                    if (isCorrectPhoneNumber())
                    {
                        String phonenumber = Objects.requireNonNull(phone_number.getText()).toString().trim();
                        Intent intent = new Intent(getApplication() , PhoneNumberVerification.class);
                        intent.putExtra("phonenumber" ,phonenumber );
                        startActivity(intent);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext() , "لا يوجد اتصال بالانترنت", Toast.LENGTH_LONG).show();
                }

            }catch (Exception ex){
                ex.printStackTrace();
            }

        });
    }



    //make sure phone number is correct

    private boolean isCorrectPhoneNumber()
    {
        try{
            String userphone  = Objects.requireNonNull(phone_number.getText()).toString();

            if(!isEmptyString(userphone))
            {
                if(userphone.replace(" ","").length() == 10){
                    String number = userphone.substring(0 , 3);

                    if(number.equals("091") || number.equals("092") || number.equals("093") || number.equals("094"))
                    {
                        return true;
                    }
                    else{
                        phone_number.setError("الرجاء التاكد من صحة رقم الهاتف ");
                        phone_number.requestFocus();
                        return false;
                    }
                }
                else{
                    phone_number.setError("الرجاء التاكد من صحة رقم الهاتف ");
                    phone_number.requestFocus();
                    return false;
                }
            }
            else{
                phone_number.setError("رقم الهاتف مطلوب");
                phone_number.requestFocus();
                return  false;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  false;
    }

}
