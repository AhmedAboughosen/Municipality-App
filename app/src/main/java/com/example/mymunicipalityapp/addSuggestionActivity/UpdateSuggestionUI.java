package com.example.mymunicipalityapp.addSuggestionActivity;


import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import com.example.mymunicipalityapp.R;
import com.example.mymunicipalityapp.interfaces.UpdateComplaintSuggestionView;
import com.example.mymunicipalityapp.model.MunicipalityModel;
import com.example.mymunicipalityapp.model.suggestionModel.SuggestionDetailsModel;
import com.example.mymunicipalityapp.model.suggestionModel.SuggestionTypeModel;
import com.example.mymunicipalityapp.municipalityFragment.MunicipalityFragment;
import com.example.mymunicipalityapp.viewModel.DialogViewModel;
import com.example.mymunicipalityapp.viewModel.UpdateComplaintSuggestionUI;
import com.kofigyan.stateprogressbar.StateProgressBar;

public class UpdateSuggestionUI extends UpdateComplaintSuggestionUI implements UpdateComplaintSuggestionView
{

    private View viewSuggestionTypeFragment ;
    private View viewMunicipalityTypeFragment;
    private View viewSuggestionDetailsFragment;



    @Override
    public void update(StateProgressBar.StateNumber pos , View view){
        try {
            updateToolbar(view);
            updateStepView(pos);
            updateFragment(AddSuggestionActivity.getInstance().getSupportFragmentManager() ,R.id.Suggestion_Fragment);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }


    @Override
    public void update_view(int layout, Fragment fragment) {
        try {
            this.stateProgressBar = AddSuggestionActivity.getInstance().stateProgressBar;
            this.fragment = fragment;
            this.toolbar = AddSuggestionActivity.getInstance().toolbar;
            this.layout = layout;


            switch (layout)
            {

                case R.layout.toolbar_suggestion_type:
                    initSuggestionTypeFragment();
                    break;
                case R.layout.toolbar_municipality_type :
                    initMunicipalityTypeFragment();
                    break;

                case R.layout.toolbar_suggestion_details:
                    initSuggestionDetailsFragment();
                    break;
                case R.layout.toolbar_suggestion_summary:
                    initSuggestionSummaryFragment();
                    break;
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }



    private void initSuggestionTypeFragment()
    {
        try {
            viewSuggestionTypeFragment = AddSuggestionActivity.getInstance().getLayoutInflater().inflate(this.layout,null);

           update(StateProgressBar.StateNumber.ONE , viewSuggestionTypeFragment);


           TextView title = viewSuggestionTypeFragment.findViewById(R.id.titile_types);
            title.setText("نوع المقترح");

            viewSuggestionTypeFragment.findViewById(R.id.close_dialog).setOnClickListener(v -> {
                try {
                    Object[] a = new SuggestionTypeModel(AddSuggestionActivity.getInstance()).getData();
                    String title1 = (String) a[0];
                    if(title1 != null && !title1.isEmpty())
                    {
                        new DialogViewModel().ExitDialogSuggestion( AddSuggestionActivity.getInstance());
                        return;
                    }

                    AddSuggestionActivity.getInstance().finish();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            });
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    private void initMunicipalityTypeFragment()
    {
        try {
            viewMunicipalityTypeFragment = AddSuggestionActivity.getInstance().getLayoutInflater().inflate(this.layout,null);

            update(StateProgressBar.StateNumber.TWO , viewMunicipalityTypeFragment);


            TextView title = viewMunicipalityTypeFragment.findViewById(R.id.title_text_view);
            title.setText("حدد البلدية");

            ImageView keyboard_arrow = viewMunicipalityTypeFragment.findViewById(R.id.keyboard_arrow);

            keyboard_arrow.setOnClickListener(view -> onBackPressed());


            SearchView searchView = viewMunicipalityTypeFragment.findViewById(R.id.search_view);

            searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
            searchView.setQueryHint(Html.fromHtml("<font color = #ffffff>" + "بحث"+ "</font>"));

            searchView.setOnSearchClickListener(view -> {
                title.setVisibility(View.GONE);
                keyboard_arrow.setVisibility(View.GONE);
            });

            searchView.setOnCloseListener(() -> {
                title.setVisibility(View.VISIBLE);
                keyboard_arrow.setVisibility(View.VISIBLE);
                return false;
            });


            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {

                    try {
                        MunicipalityFragment.getInstance().customAdapter.getFilter().filter(s);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    return false;
                }
            });



        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    private void initSuggestionDetailsFragment()
    {
        try {
            viewSuggestionDetailsFragment =  AddSuggestionActivity.getInstance().getLayoutInflater().inflate(layout,null);
            update(StateProgressBar.StateNumber.THREE , viewSuggestionDetailsFragment);

            TextView textView = viewSuggestionDetailsFragment.findViewById(R.id.title_details);
            textView.setText("تفاصل المقترح");
            viewSuggestionDetailsFragment.findViewById(R.id.details_keyboard_arrow).setOnClickListener(v -> onBackPressed());
            viewSuggestionDetailsFragment.findViewById(R.id.details_close).setOnClickListener(view -> new DialogViewModel().ExitDialogSuggestion(AddSuggestionActivity.getInstance()));
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void initSuggestionSummaryFragment(){
        try {
            View   viewSuggestionSummaryFragment =  AddSuggestionActivity.getInstance().getLayoutInflater().inflate(layout,null);
            update(StateProgressBar.StateNumber.THREE , viewSuggestionSummaryFragment);


            TextView t = viewSuggestionSummaryFragment.findViewById(R.id.summary_titile);
            t.setText("ملخص المقترح");

            viewSuggestionSummaryFragment.findViewById(R.id.summary_keyboard_arrow).setOnClickListener(v -> {
                try {
                    onBackPressed();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            });


            viewSuggestionSummaryFragment.findViewById(R.id.summary_close).setOnClickListener(view -> {
                try {
                    new DialogViewModel().ExitDialogSuggestion(AddSuggestionActivity.getInstance());
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            });
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onBackPressed()
    {
        try {
            switch (this.layout)
            {
                case R.layout.toolbar_suggestion_type:
                    new SuggestionTypeModel(AddSuggestionActivity.getInstance()).AllClear();
                    new SuggestionDetailsModel(AddSuggestionActivity.getInstance()).AllClear();
                    new MunicipalityModel(AddSuggestionActivity.getInstance()).AllClear();
                    AddSuggestionActivity.getInstance().finish();
                    break;

                case R.layout.toolbar_municipality_type :
                    update(viewSuggestionTypeFragment , StateProgressBar.StateNumber.ONE  , R.layout.toolbar_suggestion_type);
                    break;
                case R.layout.toolbar_suggestion_details:
                    update(viewMunicipalityTypeFragment , StateProgressBar.StateNumber.TWO  , R.layout.toolbar_municipality_type);
                    break;
                case R.layout.toolbar_suggestion_summary:
                    update(viewSuggestionDetailsFragment , StateProgressBar.StateNumber.THREE  , R.layout.toolbar_suggestion_details);
                    break;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }


    @Override
    public void update(View view, StateProgressBar.StateNumber number, int layout) {
        updateToolbar( view);
        updateStepView(number);
        this.layout = layout;
        PopBackStack();
    }


    @Override
    public void PopBackStack() {
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        }
        else {
            AddSuggestionActivity.getInstance().finish();
        }
    }

}
