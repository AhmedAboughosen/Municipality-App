package com.example.mymunicipalityapp.addComplaintActivity;


import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mymunicipalityapp.R;
import com.example.mymunicipalityapp.model.user.UserManager;
import com.example.mymunicipalityapp.model.complaintModel.ComplaintTypeModel;
import com.example.mymunicipalityapp.model.complaintModel.ComplaintLocationModel;
import com.example.mymunicipalityapp.model.complaintModel.DetailsCompalintManager;
import com.example.mymunicipalityapp.model.MunicipalityModel;
import com.example.mymunicipalityapp.network.NetworkUtil;
import com.example.mymunicipalityapp.network.RetrofitClient;
import com.example.mymunicipalityapp.util.StringUtil;
import com.example.mymunicipalityapp.viewModel.DialogViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComplaintSummaryFragment extends Fragment{


    @BindView(R.id.sendcomplaintbutton)
    Button send_request;


    private boolean file = false;
    private boolean img = false;
    //instance

    private int Type_of_complaint_ID;
    private int type_municiplty_ID;
    private String details;
    private double longitude;
    private double latitude;
    private String image;
    private String files;
    private  RequestBody []requestBody;




    public ComplaintSummaryFragment() {
        // Required empty public constructor
        requestBody = new RequestBody[9];
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_complaint_summary, container, false);

        try {
            ButterKnife.bind(this, view);
            initData(view);
            iniStateProgressBar();
            sendRequest();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return view;
    }


    private void iniStateProgressBar() {
        String[] descriptionData = {"نوع\nالشكوى", "موقع\nالشكوى", "البلدية", "ملخص\nالشكوى"};
        AddComplaintActivity.getInstance().stateProgressBar.setStateDescriptionData(descriptionData);
    }


    private void initData(View view) {

        try {
            setComplaintFragment(view);
            setMunicipalityFragment(view);
            setDetailsFragment(view);
            setMapLocation(view);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }



    private void setComplaintFragment(View view){
        try {
            //get type of complaint
            TextView typeofcomaplint = view.findViewById(R.id.typeofcomaplintcontent);

            Object[] objects = new ComplaintTypeModel(AddComplaintActivity.getInstance()).getData();
            String title = (String) objects[0];
            this.Type_of_complaint_ID = (int) objects[1];

            typeofcomaplint.setText(title);
        }catch (Exception ex){
            ex.printStackTrace();
        }


    }

    private void setMunicipalityFragment(View view){
        try {
            // get municipality_name
            TextView municipality_name = view.findViewById(R.id.municipality_name);

            Object[] objects = new MunicipalityModel(AddComplaintActivity.getInstance()).getData();
            String name = (String) objects[0];
            this.type_municiplty_ID = (int) objects[1];
            municipality_name.setText(name);

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }



    private void setMapLocation(View view){
        try {
            //set location of complaint
            CardView click_here_for_location = view.findViewById(R.id.click_here_for_location);
            ComplaintLocationModel locationModel = new ComplaintLocationModel(AddComplaintActivity.getInstance());
            this.latitude = locationModel.getlatitude();
            this.longitude = locationModel.getlongitude();

            if((int)this.latitude ==  0 && (int)this.longitude == 0)
            {
                click_here_for_location.setVisibility(View.GONE);
            }
            else{
                click_here_for_location.setOnClickListener(view1 -> new DialogViewModel().mapDialog(getActivity(), latitude, longitude, false));
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }


    private void setDetailsFragment(View view){

        try {

            //get details of complaint and image and file
            TextView detailscontent = view.findViewById(R.id.detailscontent);
            DetailsCompalintManager detailsCompalintManager = new DetailsCompalintManager(AddComplaintActivity.getInstance());

            this.details = detailsCompalintManager.getDescription();
            this.image = detailsCompalintManager.getImages();
            this.files = detailsCompalintManager.getFiles();
            detailscontent.setText(this.details);

            //set Image
            CircleImageView imageofcomplaint = view.findViewById(R.id.imageofcomplaint);
            TextView numbner_images = view.findViewById(R.id.number_img);

            if (this.image != null && !this.image.isEmpty()) {
                File imgFile = new File(this.image);

                if (imgFile.exists()) {
                    img = true;
                    Glide.with(Objects.requireNonNull(getActivity()))
                            .load(imgFile)
                            .into(imageofcomplaint);
                    imageofcomplaint.setPadding(0, 0, 0, 0);
                }
            } else {
                imageofcomplaint.setVisibility(View.GONE);
                numbner_images.setVisibility(View.GONE);
            }

            CircleImageView fileeofcomplaint = view.findViewById(R.id.fileeofcomplaint);
            TextView number_files = view.findViewById(R.id.number_files);

            if (this.files != null && !this.files.isEmpty()) {
                file = true;
                fileeofcomplaint.setImageResource(R.drawable.ic_document);
                fileeofcomplaint.setPadding(0, 0, 0, 0);
            } else {
                fileeofcomplaint.setVisibility(View.GONE);
                number_files.setVisibility(View.GONE);
            }


            if (!file && !img) {
                CardView attch_cardview = view.findViewById(R.id.attch_cardview);
                attch_cardview.setVisibility(View.GONE);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }


    }


    private void userInfo(){
        try {
            UserManager loginSharedPrefManager = new UserManager(getActivity());

            String name = loginSharedPrefManager.getFullName();
            String phone = loginSharedPrefManager.getPhoneNumber();
            String national_id = loginSharedPrefManager.getNationalID();
            String email = loginSharedPrefManager.getEmail();

           /* name = "محمود خليل جبران";
            phone = "0945635557";
            national_id = "120051236547";
            email = "null@null.com";*/
            //request for name
            requestBody[0] =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), name);
            //request for phone

            requestBody[1] =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), phone);


            //request for national_id
            requestBody[2] = RequestBody.create(MediaType.parse("multipart/form-data"), national_id);

            //request for Email
            requestBody[3] = RequestBody.create(MediaType.parse("multipart/form-data"), email);


        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    private void DataPrimary(){
        try {
            userInfo();
            //request for Type_of_complaint

            requestBody[4] =
                    RequestBody.create(MediaType.parse("multipart/form-data"), this.Type_of_complaint_ID+"");

            //request for type_municiplty_ID

            requestBody[5] = RequestBody.create(MediaType.parse("multipart/form-data"), type_municiplty_ID+"");
            //request for details

            requestBody[6] = RequestBody.create(MediaType.parse("multipart/form-data"), details);

            ComplaintLocation();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void ComplaintLocation(){
        if((int)this.latitude ==  0 && (int)this.longitude == 0)
        {
            //request for longitude
            requestBody[7] = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            //request for latitude
            requestBody[8] = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            return;
        }
        //request for longitude
        requestBody[7] = RequestBody.create(MediaType.parse("multipart/form-data"), longitude+"");
        //request for latitude
        requestBody[8] = RequestBody.create(MediaType.parse("multipart/form-data"), latitude+"");
    }

    private void sendDataWithImage(){

        try {
            DataPrimary();

            File file = new File(this.image);
            // Create a request body with file and image media type
            RequestBody fileReqBody = RequestBody.create(MediaType.parse("*/*"), file);
            // Create MultipartBody.Part using file request-body,file name and part name
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("img", file.getName(), fileReqBody);
            //Create request body with text description and text media type
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());


            Call<ResponseBody> call =  RetrofitClient.getInstance().getHTTPOperation().AddCompalint(requestBody[0] , requestBody[1] , requestBody[2] ,requestBody[3] , requestBody[4] ,requestBody[5] , requestBody[6] , requestBody[7] , requestBody[8] , fileToUpload  ,filename) ;
             connectToserver(call);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    private void sendDataWithFile(){

        try {
            DataPrimary();

            File file = new File(files);
            // Create a request body with file and image media type
            RequestBody fileReqBody = RequestBody.create(MediaType.parse("*/*"), file);
            // Create MultipartBody.Part using file request-body,file name and part name
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), fileReqBody);
            //Create request body with text description and text media type
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());


            Call<ResponseBody> call =  RetrofitClient.getInstance().getHTTPOperation().AddCompalint(requestBody[0] , requestBody[1] , requestBody[2] ,requestBody[3] , requestBody[4] ,requestBody[5],requestBody[7]  , requestBody[8]  ,fileToUpload  ,filename , requestBody[6]  ) ;

            connectToserver(call);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void sendDataWithFileAndImage(){

        try {
            DataPrimary();

            File file = new File(files);
            // Create a request body with file and image media type
            RequestBody fileReqBody = RequestBody.create(MediaType.parse("*/*"), file);
            // Create MultipartBody.Part using file request-body,file name and part name
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), fileReqBody);
            //Create request body with text description and text media type
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

            File img = new File(image);

            // Create a request body with file and image media type
            RequestBody imgReqBody = RequestBody.create(MediaType.parse("*/*"), img);
            // Create MultipartBody.Part using file request-body,file name and part name
            MultipartBody.Part imgToUpload = MultipartBody.Part.createFormData("img", img.getName(), imgReqBody);
            //Create request body with text description and text media type
            RequestBody imgename = RequestBody.create(MediaType.parse("text/plain"), img.getName());

            Call<ResponseBody> call =  RetrofitClient.getInstance().getHTTPOperation().AddCompalint(requestBody[0] , requestBody[1] , requestBody[2] ,requestBody[3] , requestBody[4] ,requestBody[5] ,requestBody[6] , requestBody[7] , requestBody[8], imgToUpload , imgename , fileToUpload  ,filename  ) ;

            connectToserver(call);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }



    private void sendJustData(){

        try {
            DataPrimary();

            Call<ResponseBody> call =  RetrofitClient.getInstance().getHTTPOperation().AddCompalint(requestBody[0] , requestBody[1] , requestBody[2] ,requestBody[3] , requestBody[4] ,requestBody[5] ,requestBody[6] , requestBody[7] , requestBody[8]  ) ;

            connectToserver(call);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }



    private void connectToserver(Call<ResponseBody> call){
        final DialogViewModel dialogViewModel = new DialogViewModel();
        Dialog dialog = dialogViewModel.SendRequest(getActivity());


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                dialog.dismiss();

                String s ;


                try {
                    if(response.code() == 200)
                    {
                        assert response.body() != null;
                        s = response.body().string();
                        ParseJsonFile(s , dialogViewModel);
                    }
                    else {
                        dialogViewModel.ServerError(getActivity());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //Do user registration using the api call
            @Override
            public void onFailure(@NotNull Call<ResponseBody> call,@NotNull Throwable t) {
                try {
                    dialog.dismiss();

                    dialogViewModel.ServerError(getActivity());
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }

            }
        });

    }


    private void ParseJsonFile(String  s, DialogViewModel dialogViewModel ){

        try {
            if(TextUtils.isDigitsOnly(s))
            {
                new DialogViewModel().finalMessage(getActivity() ,  true , s);
            }
            else {
                dialogViewModel.ServerError(getActivity());
            }

        }catch (Exception ex){
            ex.printStackTrace();
            dialogViewModel.ServerError(getActivity());
        }
    }


    private void sendRequest() {


        send_request.setOnClickListener(v -> {

            try {

                if(NetworkUtil.isNetworkConnected((ConnectivityManager) AddComplaintActivity.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)))
                {
                    if(file && img){
                        sendDataWithFileAndImage();
                    }
                    else{
                        if(img)
                        {
                            sendDataWithImage();
                        }
                        else {
                            if(file){
                                sendDataWithFile();
                            }
                            else {
                                sendJustData();
                            }
                        }
                    }
                }
                else {
                    Toast.makeText(getActivity()  , "لا يوجد اتصال بالانترنت", Toast.LENGTH_LONG).show();

                }



            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });

    }



}