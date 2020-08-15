package com.example.mymunicipalityapp.addSuggestionActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.bumptech.glide.Glide;
import com.example.mymunicipalityapp.R;
import com.example.mymunicipalityapp.model.suggestionModel.SuggestionDetailsModel;
import com.example.mymunicipalityapp.util.StringUtil;
import com.example.mymunicipalityapp.viewModel.BaseDetailsFragment;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SuggestionDetailsFragment extends BaseDetailsFragment {



    private SuggestionDetailsModel suggestionDetailsModel;

    public SuggestionDetailsFragment() {
        // Required empty public constructor
        suggestionDetailsModel = new SuggestionDetailsModel(AddSuggestionActivity.getInstance());
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = null;
        try {
             view = inflater.inflate(R.layout.fragment_suggestion_details, container, false);
            ButterKnife.bind(this , view);

            textInputLayout.setHint("وصف المقترح");
            TextChange();
            initData();
            initMediaRequest();
            iniStateProgressBar();
            sendRequest(view);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return view;
    }

    private void iniStateProgressBar()
    {
        String[] descriptionData = {"نوع\nالمقترح","البلدية", "تفاصيل\nالمقترح"};
        AddSuggestionActivity.getInstance().stateProgressBar.setStateDescriptionData(descriptionData);
    }


    private void initData(){

        try {

            Object[] obj = suggestionDetailsModel.getData();
            String description = (String)obj[0];
            String img = (String)obj[1];
            String File = (String)obj[2];

            if( description!= null && !description.isEmpty())
            {
                desc.setText(description);
                string_count.setText((description.length()+""));
                if(img != null && !img.isEmpty())
                {
                    Glide.with(Objects.requireNonNull(getActivity()))
                            .load(new File(img))
                            .into(image);
                    image.setPadding(0,0,0,0);
                    photoPaths.add(img);
                }


                if(File != null && !File.isEmpty())
                {
                    file.setImageResource(R.drawable.ic_document);
                    file.setPadding(0,0,0,0);
                    docPaths.add(File);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }


    private void TextChange(){

        desc.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                string_count.setText(s.length() + " / "+getString(R.string.description_string_max_count));
            }
        });


    }


    private void sendRequest(View view){



        Button button =  view.findViewById(R.id.send_request);
        button.setOnClickListener(v -> {

            try {
                if( (StringUtil.isEmptyString(Objects.requireNonNull(desc.getText()).toString()))){
                    desc.setError("تفاصيل المقترح مطلوبة");
                    desc.requestFocus();
                    return;
                }

                if( desc.getText().toString().replace(" ", "").length() <= 35){
                    desc.setError("التفاصيل المقترح يجب ان تحتوي على الاقل 35 حرفا");
                    desc.requestFocus();
                    return;
                }

                photoPaths.add("");
                docPaths.add("");
                suggestionDetailsModel.AllClear();
                suggestionDetailsModel.putData(desc.getText().toString() ,docPaths.get(0), photoPaths.get(0));

                AddSuggestionActivity.getInstance().update(R.layout.toolbar_suggestion_summary, new SuggestionSummaryFragment());

            }catch (Exception ex){
                ex.printStackTrace();
            }



        });
    }




    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            super.ActivityResult(requestCode, resultCode, data);
        }catch (Exception ex){
            ex.printStackTrace();
        }


    }


}
