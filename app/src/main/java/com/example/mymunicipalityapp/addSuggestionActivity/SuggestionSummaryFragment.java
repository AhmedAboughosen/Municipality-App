package com.example.mymunicipalityapp.addSuggestionActivity;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mymunicipalityapp.R;
import com.example.mymunicipalityapp.addComplaintActivity.AddComplaintActivity;
import com.example.mymunicipalityapp.model.MunicipalityModel;
import com.example.mymunicipalityapp.model.complaintModel.ComplaintTypeModel;
import com.example.mymunicipalityapp.model.suggestionModel.SuggestionDetailsModel;
import com.example.mymunicipalityapp.model.suggestionModel.SuggestionTypeModel;
import com.example.mymunicipalityapp.model.user.UserManager;
import com.example.mymunicipalityapp.network.NetworkUtil;
import com.example.mymunicipalityapp.network.RetrofitClient;
import com.example.mymunicipalityapp.viewModel.DialogViewModel;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import butterknife.BindView;
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
public class SuggestionSummaryFragment extends Fragment {


    private int type_of_suggestion_ID ;
    private String municipality_ID;
    private String detailscontent;
    private String Image;
    private String File;
    private boolean imgState;
    private boolean fileState;
    private RequestBody []requestBody;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  null;
        try {
            view =  inflater.inflate(R.layout.fragment_suggestion_summary, container, false);
            iniStateProgressBar();
            initData(view);
            requestBody = new RequestBody[7];
            sendRequest(view);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  view;
    }

    private void iniStateProgressBar()
    {
        String[] descriptionData = {"نوع\nالمقترح","البلدية", "ملخص\nالمقترح"};
        AddSuggestionActivity.getInstance().stateProgressBar.setStateDescriptionData(descriptionData);
    }

    private void setTypeSuggestionFragment(View view){
        try {
            TextView title = view.findViewById(R.id.typeofsuggestioncontent);
            Object[] obj = new SuggestionTypeModel(getActivity()).getData();
            this.type_of_suggestion_ID = (int)obj[1];
            title.setText((String)obj[0]);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void setMunicipalityFragment(View view)
    {
        try {
            TextView municipality_name = view.findViewById(R.id.municipality_name);

            Object[]  obj=  new MunicipalityModel(getActivity()).getData();
            this.municipality_ID = (int)obj[1] +"";
            municipality_name.setText((String)obj[0]);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }


    private void setDetailsFragment(View view){
        try {

           Object[]  obj=  new SuggestionDetailsModel(getActivity()).getData();

           TextView detailscontent = view.findViewById(R.id.detailscontent);
           this.detailscontent = (String)obj[0];

            detailscontent.setText(this.detailscontent);

            CircleImageView imageofSuggestion = view.findViewById(R.id.imageofSuggestion);
            CircleImageView fileeofsuggestion = view.findViewById(R.id.fileeofsuggestion);
            TextView number_image = view.findViewById(R.id.number_image);
            TextView number_files = view.findViewById(R.id.number_files);

            this.Image = (String)obj[1];

            if((this.Image  != null && !this.Image.isEmpty()))
            {
                File imgFile = new  File(this.Image );

                if(imgFile.exists()){
                    imgState = true;
                    Glide.with(Objects.requireNonNull(getActivity()))
                            .load(new File(this.Image ))
                            .into(imageofSuggestion);
                    imageofSuggestion.setPadding(0,0,0,0);
                }
            }
            else{
                imageofSuggestion.setVisibility(View.GONE);
                number_image.setVisibility(View.GONE);
            }


            this.File = (String)obj[2];

            if(this.File != null && !this.File.isEmpty())
            {
                fileState = true;
                fileeofsuggestion.setImageResource(R.drawable.ic_document);
                fileeofsuggestion.setPadding(0,0,0,0);
            }
            else{
                fileeofsuggestion.setVisibility(View.GONE);
                number_files.setVisibility(View.GONE);
            }

            if(!fileState && !imgState)
            {
                CardView cardView = view.findViewById(R.id.sixcardview);
                cardView.setVisibility( View.GONE);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void initData(View view ){
        try {
            setTypeSuggestionFragment(view);
            setDetailsFragment(view);
            setMunicipalityFragment(view);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void sendRequest(View view){
        view.findViewById(R.id.sendsuggestion).setOnClickListener(view1 -> {
            try {

                if(NetworkUtil.isNetworkConnected((ConnectivityManager) AddSuggestionActivity.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)))

                {
                    if(fileState && imgState){
                        sendDataWithFileAndImage();
                    }
                    else{
                        if(imgState)
                        {
                            sendDataWithImage();
                        }
                        else {
                            if(fileState){
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


    private void userInfo()
    {
        UserManager loginSharedPrefManager = new UserManager(getActivity());

        String name = loginSharedPrefManager.getFullName();
        String phone = loginSharedPrefManager.getPhoneNumber();
        String national_id = loginSharedPrefManager.getNationalID();
        String email = loginSharedPrefManager.getEmail();

      /*   name = "محمد منصور خالد";
         phone = "0924457203";
         national_id = "120191231231";
         email = "";

*/
        //request for name
        requestBody[0] =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), name);

        //request for phone

        requestBody[1] =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), phone);
        //request for national_id

        requestBody[2] =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"),national_id );

        //request for Email
         requestBody[3] = RequestBody.create(MediaType.parse("multipart/form-data"), email);
    }


    private void initData()
    {
        //request for Type_of_suggestion_ID

        requestBody[4] =
                RequestBody.create(MediaType.parse("multipart/form-data"), this.type_of_suggestion_ID+"");

        //request for type_municiplty_ID

        requestBody[5] = RequestBody.create(MediaType.parse("multipart/form-data"), this.municipality_ID+"");

        //request for details

        requestBody[6] = RequestBody.create(MediaType.parse("multipart/form-data"), this.detailscontent);
    }


    private void setData(){
        userInfo();
        initData();
    }

    private void sendDataWithImage(){

        try {
            setData();

            File file = new File(this.Image);
            // Create a request body with file and image media type
            RequestBody fileReqBody = RequestBody.create(MediaType.parse("*/*"), file);
            // Create MultipartBody.Part using file request-body,file name and part name
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("img", file.getName(), fileReqBody);
            //Create request body with text description and text media type
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());


            Call<ResponseBody> call =  RetrofitClient.getInstance().getHTTPOperation().AddSuggestion(requestBody[0] , requestBody[1] , requestBody[2] ,requestBody[3] , requestBody[4] ,requestBody[5]  ,requestBody[6],fileToUpload  ,filename) ;

            connectToserver(call);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    private void sendJustData(){

        try {
            setData();

            Call<ResponseBody> call =  RetrofitClient.getInstance().getHTTPOperation().AddSuggestion(requestBody[0] , requestBody[1] , requestBody[2] ,requestBody[3] , requestBody[4] ,requestBody[5] ,requestBody[6]  ) ;

            connectToserver(call);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    private void sendDataWithFileAndImage(){

        try {
            setData();

            File file = new File(this.File);
            // Create a request body with file and image media type
            RequestBody fileReqBody = RequestBody.create(MediaType.parse("*/*"), file);
            // Create MultipartBody.Part using file request-body,file name and part name
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), fileReqBody);
            //Create request body with text description and text media type
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

            File img = new File(this.Image);

            // Create a request body with file and image media type
            RequestBody imgReqBody = RequestBody.create(MediaType.parse("*/*"), img);
            // Create MultipartBody.Part using file request-body,file name and part name
            MultipartBody.Part imgToUpload = MultipartBody.Part.createFormData("img", img.getName(), imgReqBody);
            //Create request body with text description and text media type
            RequestBody imgename = RequestBody.create(MediaType.parse("text/plain"), img.getName());

            Call<ResponseBody> call =  RetrofitClient.getInstance().getHTTPOperation().AddSuggestion(requestBody[0] , requestBody[1] , requestBody[2] ,requestBody[3] , requestBody[4] ,requestBody[5] ,requestBody[6]  , imgToUpload , imgename , fileToUpload  ,filename  ) ;

            connectToserver(call);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void sendDataWithFile(){

        try {
            setData();

            File file = new File(this.File);
            // Create a request body with file and image media type
            RequestBody fileReqBody = RequestBody.create(MediaType.parse("*/*"), file);
            // Create MultipartBody.Part using file request-body,file name and part name
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), fileReqBody);
            //Create request body with text description and text media type
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());


            Call<ResponseBody> call =  RetrofitClient.getInstance().getHTTPOperation().AddSuggestion(requestBody[0] , requestBody[1] , requestBody[2] ,requestBody[3] , requestBody[4]  ,requestBody[5],fileToUpload  ,filename,requestBody[6] ) ;

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
                        ParseJsonFile(s);
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
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                dialog.dismiss();

                dialogViewModel.ServerError(getActivity());
            }
        });

    }


    private void ParseJsonFile(String s){

        try {
            if(s != null && TextUtils.isDigitsOnly(s))
            {
                new DialogViewModel().finalMessage(getActivity() ,  false , s);
                return;
            }
        }catch (Exception ex){
            ex.printStackTrace();

        }
    }


}
