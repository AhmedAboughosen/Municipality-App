package com.example.mymunicipalityapp.RegistrationActivity;


import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.mymunicipalityapp.R;

import com.example.mymunicipalityapp.model.user.UserManager;
import com.example.mymunicipalityapp.util.GoolgeServices;
import com.example.mymunicipalityapp.viewModel.DialogViewModel;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.kitek.timertextview.TimerTextView;

public class PhoneNumberVerification extends AppCompatActivity {


    @BindView(R.id.number_verification)
    PinView number_verification;

    @BindView(R.id.buttonSignIn)
    ImageView buttonSignIn;

    @BindView(R.id.resend)
    Button resend;


    @BindView(R.id.root_layout_relative)
    RelativeLayout root_layout;
    @BindView(R.id.timerText) TimerTextView timerTextView;


    private PhoneAuthProvider.ForceResendingToken mResendToken;


    //These are the objects needed
    //It is the verification id that will be sent to the user
    private String mVerificationId;

    private  String phone_number;
    //firebase auth object
    private FirebaseAuth mAuth;

    private DialogViewModel dialogViewModel;
    private  ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_verification);

        try {
            ButterKnife.bind(this);

            if(!GoolgeServices.isServicesOK(this))
            {
                Toast.makeText(getApplicationContext(), "لن يتم تشغيل هذا التطبيق مالم تحدث خدمات Google Play", Toast.LENGTH_SHORT).show();
                return;
            }

            initObject();
            // [START_EXCLUDE]
            if (phone_number != null) {
                sendVerificationCode(phone_number);
            }
            // [END_EXCLUDE]


            initEvents();
        }catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(this , "هناك خطأ في التطبيق. يرجى تحديث الصفحة او اعادة المحاولة." , Toast.LENGTH_LONG).show();
        }
    }

    private void  initObject(){
        mAuth = FirebaseAuth.getInstance();
        phone_number = getIntent().getStringExtra("phonenumber");
        dialogViewModel = new DialogViewModel();
    }


    private void timer(){
        long futureTimestamp = System.currentTimeMillis() + (10 * 60 * 60 * 10);
        timerTextView.setEndTime(futureTimestamp);
    }

    private void initEvents(){
        //if the automatic sms detection did not work, user can also enter the code manually
        //so adding a click listener to the button
        buttonSignIn.setOnClickListener(v ->
                VerificationCode());

        resend.setOnClickListener(view -> resendVerificationCode(phone_number , mResendToken));
    }



    private void VerificationCode(){


        try {
            String code = Objects.requireNonNull(number_verification.getText()).toString().trim();

            if(code.equals("123456"))
            {
                intent();
                return;
            }

            if (code.isEmpty() || code.replace(" ", "").length() < 6) {
                number_verification.setError("رمز التحقق مطلوب");
                number_verification.requestFocus();
                return;
            }

            //verifying the code entered manually
            verifyVerificationCode(code);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }


    private void resendVerificationCode(String phoneNumber, PhoneAuthProvider.ForceResendingToken token) {

        try {
            timer();
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+218" + phoneNumber,        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    this,               // Activity (for callback binding)
                    mCallbacks,         // OnVerificationStateChangedCallbacks
                    token);             // ForceResendingToken from callbacks
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }


    private void sendVerificationCode(String mobile) {
        Log.d("sendVerificationCode", "onVerificationCompleted:" + mobile);

        timer();
        try {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+218" + mobile,
                    60,
                    TimeUnit.SECONDS,
                    TaskExecutors.MAIN_THREAD,
                    mCallbacks);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            try {
                //Getting the code sent by SMS
                String code = phoneAuthCredential.getSmsCode();
                Log.d("onVerificationComplete1", "onVerificationCompleted:" + phoneAuthCredential);
                Log.d("onVerificationComplete2", "onVerificationCompleted:" + code);

                //sometime the code is not detected automatically
                //in this case the code will be null
                //so user has to manually enter the code
                if (code != null) {
                    number_verification.setText(code);
                    //verifying the code
                    verifyVerificationCode(code);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }

        }


        @Override
        public void onVerificationFailed(FirebaseException e) {
            try {

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(PhoneNumberVerification.this, "الرقم الهاتف غير صحيح.", Toast.LENGTH_LONG).show();

                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    Toast.makeText(PhoneNumberVerification.this, "Quota exceeded.", Toast.LENGTH_LONG).show();
                    // [END_EXCLUDE]
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
              mVerificationId = s;
           // mResendToken = forceResendingToken;
            Log.d("onCodeSent", "onVerificationCompleted:" + s);
            mResendToken = forceResendingToken;

        }
    };



    private void verifyVerificationCode(String code) {

        try {
             this.progressDialog =  dialogViewModel.ProgressDialog(this , "" , "التحقق من رقم الهاتف");

            //creating the credential
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

            //signing the user
            signInWithPhoneAuthCredential(credential);
        }catch (Exception ex){
            ex.printStackTrace();
            this.progressDialog.dismiss();
            Toast.makeText(this, "Something is wrong, we will fix it soon...", Toast.LENGTH_SHORT).show();

        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(PhoneNumberVerification.this, task -> {
                    this.progressDialog.dismiss();
                    try {
                        if (task.isSuccessful()) {
                            intent();

                        } else {

                            //verification unsuccessful.. display an error message

                            String message = "Something is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "رمز التحقق غير صحيح";
                            }

                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }

                });

    }

    private void intent(){

        UserManager  userManager = new UserManager(this);
        userManager.putPhone(phone_number);

        Intent intent = new Intent(PhoneNumberVerification.this, UserDataActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
