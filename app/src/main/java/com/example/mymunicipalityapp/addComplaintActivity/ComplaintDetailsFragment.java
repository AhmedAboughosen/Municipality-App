package com.example.mymunicipalityapp.addComplaintActivity;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.mymunicipalityapp.R;
import com.example.mymunicipalityapp.model.complaintModel.DetailsCompalintManager;
import com.example.mymunicipalityapp.util.StringUtil;
import com.example.mymunicipalityapp.viewModel.BaseDetailsFragment;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComplaintDetailsFragment extends BaseDetailsFragment {



    private DetailsCompalintManager detailsCompalintManager;




    public ComplaintDetailsFragment() {
        // Required empty public constructor
        detailsCompalintManager =new DetailsCompalintManager(AddComplaintActivity.getInstance());
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_complaint_details, container, false);

        try {
            ButterKnife.bind(this , view);
            initData();
            TextChanged();
            initMediaRequest();
            iniStateProgressBar();
            sendRequest();
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return view;
    }


    private void initData(){
        try {
            if(detailsCompalintManager.getDescription() != null && !detailsCompalintManager.getDescription().isEmpty()){
                desc.setText(detailsCompalintManager.getDescription());

                int length = Objects.requireNonNull(desc.getText()).length();
                StringBuilder s = new StringBuilder(String.valueOf(length));

                string_count.setText(s.append(getString(R.string.description_string_max_count)));

                if(detailsCompalintManager.getImages() != null && !detailsCompalintManager.getImages().isEmpty())
                {
                    Glide.with(Objects.requireNonNull(getActivity()))
                            .load(new File(detailsCompalintManager.getImages()))
                            .into(image);
                    image.setPadding(0,0,0,0);
                    photoPaths.add(detailsCompalintManager.getImages());
                }

                if(detailsCompalintManager.getFiles() != null && !detailsCompalintManager.getFiles().isEmpty())
                {
                    file.setImageResource(R.drawable.ic_document);
                    file.setPadding(0,0,0,0);
                    docPaths.add(detailsCompalintManager.getFiles());
                }
            }


        }catch (Exception ex){
            ex.printStackTrace();
        }

    }


    private void iniStateProgressBar()
    {
        try {
            String[] descriptionData = {"نوع\nالشكوى", "موقع\nالشكوى", "البلدية", "تفاصيل\nالشكوى"};
            AddComplaintActivity.getInstance().stateProgressBar.setStateDescriptionData(descriptionData);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            super.ActivityResult(requestCode, resultCode, data);

    }


    private void TextChanged(){
        desc.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                StringBuilder ss = new StringBuilder(String.valueOf(s.length()));
                ss.append(" / ");
                string_count.setText(ss.append(getString(R.string.description_string_max_count)));
            }
        });
    }


    private void sendRequest(){

        try{


            send_request.setOnClickListener(v -> {


                try {


                    if(StringUtil.isEmptyString(Objects.requireNonNull(desc.getText()).toString()))
                    {
                        desc.setError("تفاصيل الشكوى مطلوبة");
                        desc.requestFocus();
                        return;
                    }

                    if( desc.getText().toString().replace(" ", "").length()<= 35){
                        desc.setError("التفاصيل الشكوى يجب ان تحتوي على الاقل 35 حرفا");
                        desc.requestFocus();
                        return;
                    }

                    photoPaths.add("");
                    docPaths.add("");
                    detailsCompalintManager.AllClear();
                    detailsCompalintManager.putData(desc.getText().toString()  ,docPaths.get(0) , photoPaths.get(0) );
                    AddComplaintActivity.getInstance().update_view(R.layout.toolbar_complaint_summary, new ComplaintSummaryFragment());

                }catch (Exception ex){
                    ex.printStackTrace();
                }

            });
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
