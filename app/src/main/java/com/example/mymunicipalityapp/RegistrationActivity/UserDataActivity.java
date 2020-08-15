package com.example.mymunicipalityapp.RegistrationActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mymunicipalityapp.R;
import com.example.mymunicipalityapp.mainActivity.MainActivity;
import com.example.mymunicipalityapp.model.user.UserManager;
import com.example.mymunicipalityapp.util.StartActivity;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import static com.example.mymunicipalityapp.util.StringUtil.isEmptyString;
import static com.example.mymunicipalityapp.util.EmailAddress.isValidEmail;

public class UserDataActivity extends AppCompatActivity {



    @BindView(R.id.sign_in)
    Button sign_in;

    @BindView(R.id.NationalID)
    EditText NationalID;

    @BindView(R.id.Email)
    EditText Email;

    @BindView(R.id.fullname)
    EditText nick_name;

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        try{
            ButterKnife.bind(this);
            initComponent();
        }catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(this , "هناك خطأ في التطبيق. يرجى تحديث الصفحة او اعادة المحاولة." , Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        String n = new UserManager(getApplicationContext()).getPhoneNumber();
        if(new UserManager(getApplicationContext()).getPhoneNumber() == null){
            Intent intent = new Intent(UserDataActivity.this, PhoneAuthentication.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

    /*   if(FirebaseAuth.getInstance().getCurrentUser() == null)
        {
            Intent intent = new Intent(UserDataActivity.this, PhoneAuthentication.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }*/
    }


    private void Intent(){
        StartActivity.StartIntent(getApplicationContext() ,MainActivity.class );
        finish();
    }


    private void initComponent(){

        sign_in.setOnClickListener(view -> {

            try {
            boolean islogin = true;

            if(!isCorrectID()){
                islogin = false;
            }

            if(!isCorrectEmail()){
                islogin = false;
            }

            if(!isCorrectFullName()){
                islogin = false;
            }

            if(islogin)
            {
                new UserManager(getApplicationContext()).userLogin( NationalID.getText().toString() , this.email  , nick_name.getText().toString() );
                Intent();
            }

            }catch (Exception ex){
                ex.printStackTrace();
            }

        });
    }


    private boolean isCorrectFullName()
    {
        try {
            String name =  nick_name.getText().toString();

            if(!isEmptyString(name)){

                if(name.length() >= 10){
                    return true;
                }
                else {
                    nick_name.setError("الاسم الرباعي مطلوب ");
                    nick_name.requestFocus();
                    return false;
                }
            }
            else{
                nick_name.setError("الاسم الرباعي مطلوب ");
                nick_name.requestFocus();
                return false;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    private boolean isCorrectEmail()
    {
    try{
            this.email  = Email.getText().toString();

            if(!isEmptyString(email))
            {
              if(isValidEmail(email))
              {
                  return true;
              }
              else {
                  Email.setError("عنوان البريد الإلكتروني هذا غير صالح...");
                  Email.requestFocus();
                  return false;
              }
            }
            else{
                this.email = "";
                return true;
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  true;
    }


    private boolean isCorrectID(){

        try {
            String _NationalID = NationalID.getText().toString();


            if(!isEmptyString(_NationalID))
            {

                if(_NationalID.replace(" ","").length() == 12)
                {

                    String first_digit = _NationalID.charAt(0)+"";

                    if(first_digit.equals("1") || first_digit.equals("2"))
                    {
                        String date_birth = _NationalID.substring(1 , 5);
                        int date = Integer.parseInt(date_birth);
                        if((date >= 1900 && date <= 1996))
                        {
                            return true;
                        }
                        else{
                            NationalID.setError("الرجاء التاكد من صحة الرقم الوطني ");
                            NationalID.requestFocus();
                            return false;
                        }
                    }
                    else{
                        NationalID.setError("الرجاء التاكد من صحة الرقم الوطني ");
                        NationalID.requestFocus();
                        return false;
                    }
                }
                else{
                    NationalID.setError("الرقم الوطني غير صحيح");
                    NationalID.requestFocus();
                    return false;
                }
            }
            else {
                NationalID.setError("الرقم الوطني مطلوب");
                NationalID.requestFocus();
                return false;
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
}
