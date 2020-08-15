package com.example.mymunicipalityapp.viewModel;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mymunicipalityapp.R;
import com.example.mymunicipalityapp.addComplaintActivity.AddComplaintActivity;
import com.example.mymunicipalityapp.addComplaintActivity.ComplaintMapFragment;
import com.example.mymunicipalityapp.addSuggestionActivity.AddSuggestionActivity;
import com.example.mymunicipalityapp.model.complaintModel.DetailsCompalintManager;
import com.example.mymunicipalityapp.model.complaintModel.ComplaintTypeModel;
import com.example.mymunicipalityapp.model.complaintModel.ComplaintLocationModel;
import com.example.mymunicipalityapp.model.MunicipalityModel;
import com.example.mymunicipalityapp.model.suggestionModel.SuggestionDetailsModel;
import com.example.mymunicipalityapp.model.suggestionModel.SuggestionTypeModel;
import com.example.mymunicipalityapp.municipalityFragment.MunicipalityFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import cdflynn.android.library.checkview.CheckView;

public class DialogViewModel {


    private static DialogViewModel ourInstance;


    public static DialogViewModel getInstance()
    {
        ourInstance = (ourInstance != null) ? ourInstance : new DialogViewModel();
        return ourInstance;
    }

    public DialogViewModel(){

    }


    public  ProgressDialog ProgressDialog(Context context , String Title , String Message)
    {
        ProgressDialog loadingBar  = new ProgressDialog(context);

        try {
            loadingBar.setTitle(Title);
            loadingBar.setMessage(Message);
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(false);
        }catch (Exception ex){
            ex.printStackTrace();
        }



        return loadingBar;
    }

    public  Dialog VerificationCode(Context context ){
        final Dialog mdialog = dialog(R.layout.dialog_box_verificationcode , context);

        try {
            LottieAnimationView load =mdialog.findViewById(R.id.load);


            load.setAnimation(R.raw.wait);


        }catch (Exception ex){
            ex.printStackTrace();
        }

        return mdialog;
    }





    public void InfoDialog(Context context , final String title , String description ){

        try {
            //create Dialog
            final Dialog mdialog = dialog(R.layout.dialog_box_info_view , context);
            //create instance

            TextView Description =mdialog.findViewById(R.id.description);
            TextView Title =mdialog.findViewById(R.id.title);
            ImageView close =mdialog.findViewById(R.id.close_dialog);

            //set text
            Description.setText(description);
            Title.setText(title);

            //create Click Listener
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mdialog.dismiss();
                }
            });


            mdialog.show();

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }



    public void helpDialogSuggestion(Context context , final String title , String description , final int id)
    {
        try {
            final Dialog mdialog =helpDialog(context , title , description , id);

            Button add =mdialog.findViewById(R.id.button_dialog);
            add.setText("تقديم مقترح");

            add.setOnClickListener(v -> {

                mdialog.dismiss();
                SuggestionTypeModel suggestionTypeModel = new SuggestionTypeModel(context);
                suggestionTypeModel.AllClear();
                suggestionTypeModel.putData(title, id) ;

                AddSuggestionActivity.getInstance().update(R.layout.toolbar_municipality_type, new MunicipalityFragment(false));
            });

            mdialog.show();
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public void helpDialogComplaint(Context context , final String title , String description , final int id)
    {
        try {
            final Dialog mdialog =helpDialog(context , title , description , id);

            Button add =mdialog.findViewById(R.id.button_dialog);
            add.setText("تقديم شكوى");
            add.setOnClickListener(v -> {

                mdialog.dismiss();
                ComplaintTypeModel complaintTypeModel =  new ComplaintTypeModel(context);
                complaintTypeModel.AllClear();
                complaintTypeModel.putData(title , id);
                AddComplaintActivity.getInstance().update_view(R.layout.toolbar_complaint_map, new ComplaintMapFragment());
            });

            mdialog.show();
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public Dialog helpDialog(Context context , final String title , String description , final int id){

        try {
            //create Dialog
            final Dialog mdialog = dialog(R.layout.dialog_box_help_view , context);
            //create instance

            TextView Description =mdialog.findViewById(R.id.description);
            TextView Title =mdialog.findViewById(R.id.title_CS);
            ImageView close =mdialog.findViewById(R.id.close_dialog);

            //set text
            Description.setText(description);
            Title.setText(title);

            //create Click Listener
            close.setOnClickListener(v -> mdialog.dismiss());


            return mdialog;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }



    public void ServerError(Context context){

        try {
            //create Dialog
            final Dialog mdialog = dialog(R.layout.dialog_box_server , context);
            //create instance

            Button button =mdialog.findViewById(R.id.retry);
            button.setOnClickListener(view -> mdialog.dismiss());

            mdialog.show();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    public Dialog SendRequest(Context context){

        try {
            //create Dialog
            final Dialog mdialog = dialog(R.layout.dialog_box_send_request_layout , context);
            //create instance

            LottieAnimationView load =mdialog.findViewById(R.id.load);


            load.setAnimation(R.raw.progressbar);

            mdialog.show();

            return mdialog;
        }catch (Exception ex){
            ex.printStackTrace();
        }
       return null;
    }


    private  Dialog dialog(int i, Context context){
        final Dialog dialog = new Dialog(context);

        try {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(i);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return dialog;

    }

    public void ExitDialogSuggestion(final Context context)
    {
        try {
            final Dialog dialog = ExitDialog(context);
            TextView title = dialog.findViewById(R.id.title);
            title.setText("هل تريد حذف المقترح ؟");
            FrameLayout mDialogOk = dialog.findViewById(R.id.frmOk);

            mDialogOk.setOnClickListener(v -> {
                dialog.cancel();

                new SuggestionTypeModel(context).AllClear();
                new MunicipalityModel(context).AllClear();
                new SuggestionDetailsModel(context).AllClear();
                AddSuggestionActivity.getInstance().finish();

            });

            dialog.show();
        }catch (Exception ex){
            ex.printStackTrace();
        }


    }

    public void ExitDialogComplaint(final Context context)
    {
        final Dialog dialog = ExitDialog(context);
        TextView title = dialog.findViewById(R.id.title);
        title.setText("هل تريد حذف الشكوى ؟");
        FrameLayout mDialogOk = dialog.findViewById(R.id.frmOk);

        mDialogOk.setOnClickListener(v -> {
            dialog.cancel();

            new ComplaintTypeModel(context).AllClear();
            new ComplaintLocationModel(context).AllClear();
            new MunicipalityModel(context).AllClear();
            new DetailsCompalintManager(context).AllClear();

            AddComplaintActivity.getInstance().finish();

        });

        dialog.show();

    }


    public Dialog ExitDialog(final Context context){

        try {
            final Dialog dialog = dialog(R.layout.dialog_box_exit_view , context);


            FrameLayout mDialogNo = dialog.findViewById(R.id.frmNo);

            mDialogNo.setOnClickListener(v -> dialog.dismiss());
            return dialog;

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return null;
    }


    public  void mapDialog(Context context , double latitude , double longitude , boolean state){
        try {
            //create Dialog
            final Dialog mdialog = dialog(R.layout.dialog_municipality_map , context);


            ImageView closeview = mdialog.findViewById(R.id.close_dialog);
            TextView title = mdialog.findViewById(R.id.title_municipality);

            title.setText((!state) ?  "موقع الشكوى" : "موقع البلدية" );

            MapView mMapView ;
            MapsInitializer.initialize(context);

            mMapView = mdialog.findViewById(R.id.mapView);
            mMapView.onCreate(mdialog.onSaveInstanceState());
            mMapView.onResume();// needed to get the map to display immediately

            mMapView.getMapAsync(googleMap -> {
                LatLng posisiabsen = new LatLng(latitude, longitude); ////your lat lng

                googleMap.addMarker(new MarkerOptions().position(posisiabsen).title((!state) ? "موقع الشكوى" :"موقع البلدية" ));

                googleMap.moveCamera(CameraUpdateFactory.newLatLng(posisiabsen));
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            });

            closeview.setOnClickListener(v -> mdialog.dismiss());

            mdialog.show();
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public void finalMessage( Context context , boolean iscomplaint , String number){
        try {
            //create Dialog
            final Dialog mdialog = dialog(R.layout.dialog_final_message_view , context);
            //create instance

            TextView textView =mdialog.findViewById(R.id.text);

            if(iscomplaint){
                textView.setText(    "شكرا لتعاونكم معنا , لقد تم إضافة الشكوى بالرقم "  +number);
            }
            else{
                textView.setText(   "شكرا لتعاونكم معنا , لقد تم إضافة المقترج بالرقم "  +number);
            }


            Button home =mdialog.findViewById(R.id.home_page);
            CheckView checkView = mdialog.findViewById(R.id.check);
            checkView.check();

            home.setOnClickListener(v -> {
                mdialog.dismiss();
                if(iscomplaint){
                  new ComplaintTypeModel(context).AllClear();
                  new ComplaintLocationModel(context).AllClear();
                  new MunicipalityModel(context).AllClear();
                  new DetailsCompalintManager(context).AllClear();
                    AddComplaintActivity.getInstance().finish();
                }
                else{
                    new SuggestionTypeModel(AddSuggestionActivity.getInstance()).AllClear();

                    new SuggestionDetailsModel(AddSuggestionActivity.getInstance()).AllClear();
                    new MunicipalityModel(AddSuggestionActivity.getInstance()).AllClear();

                    AddSuggestionActivity.getInstance().finish();
                }
            });
            mdialog.show();
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

}
