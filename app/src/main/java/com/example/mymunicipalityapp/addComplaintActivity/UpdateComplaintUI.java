package com.example.mymunicipalityapp.addComplaintActivity;


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
import com.example.mymunicipalityapp.model.complaintModel.ComplaintLocationModel;
import com.example.mymunicipalityapp.model.complaintModel.ComplaintTypeModel;
import com.example.mymunicipalityapp.model.MunicipalityModel;

import com.example.mymunicipalityapp.model.complaintModel.DetailsCompalintManager;
import com.example.mymunicipalityapp.municipalityFragment.MunicipalityFragment;
import com.example.mymunicipalityapp.viewModel.DialogViewModel;
import com.example.mymunicipalityapp.viewModel.UpdateComplaintSuggestionUI;
import com.kofigyan.stateprogressbar.StateProgressBar;

public class UpdateComplaintUI extends UpdateComplaintSuggestionUI implements UpdateComplaintSuggestionView {


    private View  viewComplaintTypeFragment;
    private View  viewComplaintMapFragment;
    private View  viewComplaintMunicipalityFragment;
    private View  viewComplaintDetailsFragment;


    @Override
    public void update(StateProgressBar.StateNumber pos , View toolbar) {
        try {
            updateStepView(pos);
            updateToolbar(toolbar);
            updateFragment(AddComplaintActivity.getInstance().getSupportFragmentManager() , R.id.Fragment_Complaint);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void update_view(int layout, Fragment fragment) {
        try {
            this.stateProgressBar = AddComplaintActivity.getInstance().stateProgressBar;
            this.fragment = fragment;
            this.toolbar = AddComplaintActivity.getInstance().toolbar;
            this.layout = layout;

            switch (layout) {

                case R.layout.toolbar_compalint_type:
                    initComplaintTypeFragment();
                    break;
                case R.layout.toolbar_complaint_map:
                    initMapFragment();
                    break;
                case R.layout.toolbar_municipality_type:
                    initMunicipalityFragment();
                    break;
                case R.layout.toolbar_complaint_details:
                    initDetailsFragment();
                    break;
                case R.layout.toolbar_complaint_summary:
                    initSummaryFragment();
                    break;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void initComplaintTypeFragment() {
        try {
            viewComplaintTypeFragment = AddComplaintActivity.getInstance().getLayoutInflater().inflate(this.layout, null);
            update(StateProgressBar.StateNumber.ONE , viewComplaintTypeFragment);

            //handel toolbar View.
            ImageView close_dialog = viewComplaintTypeFragment.findViewById(R.id.close_dialog);

            TextView _title = viewComplaintTypeFragment.findViewById(R.id.titile_types);
            _title.setText("حدد نوع الشكوى");


            close_dialog.setOnClickListener(v -> {
                try {
                    ComplaintTypeModel complaintTypeModel = new ComplaintTypeModel(AddComplaintActivity.getInstance());

                    Object[] f = complaintTypeModel.getData();
                    String title = (String) f[0];
                    if ( title != null && !title.isEmpty()) {
                       new DialogViewModel().ExitDialogComplaint(AddComplaintActivity.getInstance());
                        return;
                    }
                    AddComplaintActivity.getInstance().finish();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initMapFragment() {
        try {
             viewComplaintMapFragment= AddComplaintActivity.getInstance().getLayoutInflater().inflate(this.layout, null);

            update(StateProgressBar.StateNumber.TWO , viewComplaintMapFragment);

            ImageView  map_keyboard_arrow =   viewComplaintMapFragment.findViewById(R.id.keyboard_arrow);

            map_keyboard_arrow.setOnClickListener(v -> onBackPressed());

            TextView title_text_view =  viewComplaintMapFragment.findViewById(R.id.title_text_view);

            title_text_view.setText("موقع الشكوى");


            SearchView searchView = viewComplaintMapFragment.findViewById(R.id.search_view);

            searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
            searchView.setQueryHint(Html.fromHtml("<font color = #ffffff>" + "بحث"+ "</font>"));

            searchView.setOnSearchClickListener(view -> {
                title_text_view.setVisibility(View.GONE);
                map_keyboard_arrow.setVisibility(View.GONE);
            });

            searchView.setOnCloseListener(() -> {
                title_text_view.setVisibility(View.VISIBLE);
                map_keyboard_arrow.setVisibility(View.VISIBLE);
                return false;
            });


            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    try {
                        ComplaintMapFragment.getInstance().geoLocate(s);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {

                    return false;
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    private void initMunicipalityFragment() {
        try {

            viewComplaintMunicipalityFragment = AddComplaintActivity.getInstance().getLayoutInflater().inflate(this.layout, null);
            update(StateProgressBar.StateNumber.THREE ,viewComplaintMunicipalityFragment );


            ImageView keyboard_arrow =  viewComplaintMunicipalityFragment.findViewById(R.id.keyboard_arrow);

            keyboard_arrow.setOnClickListener(v -> onBackPressed());

            TextView title_text_view =  viewComplaintMunicipalityFragment.findViewById(R.id.title_text_view);
            title_text_view.setText("حدد البلدية");

            SearchView searchView = viewComplaintMunicipalityFragment.findViewById(R.id.search_view);

            searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
            searchView.setQueryHint(Html.fromHtml("<font color = #ffffff>" + "بحث"+ "</font>"));

            searchView.setOnSearchClickListener(view -> {
                title_text_view.setVisibility(View.GONE);
                keyboard_arrow.setVisibility(View.GONE);
            });

            searchView.setOnCloseListener(() -> {
                title_text_view.setVisibility(View.VISIBLE);
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
                    MunicipalityFragment.getInstance().customAdapter.getFilter().filter(s);
                    return false;
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    private void initDetailsFragment() {

        try {
            viewComplaintDetailsFragment = AddComplaintActivity.getInstance().getLayoutInflater().inflate(this.layout, null);
            update(StateProgressBar.StateNumber.FOUR , viewComplaintDetailsFragment);


            TextView textView = viewComplaintDetailsFragment.findViewById(R.id.title_details);
            textView.setText("وصف الشكوى");
            viewComplaintDetailsFragment.findViewById(R.id.details_close).setOnClickListener(v -> {
                try {
                new DialogViewModel().ExitDialogComplaint(AddComplaintActivity.getInstance());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            viewComplaintDetailsFragment.findViewById(R.id.details_keyboard_arrow).setOnClickListener(v -> {
                try {
                    onBackPressed();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }


    private void initSummaryFragment() {

        View toolbar_summary =  AddComplaintActivity.getInstance().getLayoutInflater().inflate(this.layout, null);
        update(StateProgressBar.StateNumber.FOUR , toolbar_summary);


        TextView t = toolbar_summary.findViewById(R.id.summary_titile);
        t.setText("ملخص الشكوى");

        toolbar_summary.findViewById(R.id.summary_close).setOnClickListener(v -> new DialogViewModel().ExitDialogComplaint(AddComplaintActivity.getInstance()));

        toolbar_summary.findViewById(R.id.summary_keyboard_arrow).setOnClickListener(v -> onBackPressed());

    }


    @Override
    public void onBackPressed() {

        try {
            switch (this.layout) {

                case R.layout.toolbar_compalint_type:
                    //delete All Data
                    new ComplaintTypeModel(AddComplaintActivity.getInstance()).AllClear();
                    new ComplaintLocationModel(AddComplaintActivity.getInstance()).AllClear();
                    new MunicipalityModel(AddComplaintActivity.getInstance()).AllClear();
                    new DetailsCompalintManager(AddComplaintActivity.getInstance()).AllClear();

                    AddComplaintActivity.getInstance().finish();
                    break;

                case R.layout.toolbar_complaint_map:
                    update(viewComplaintTypeFragment , StateProgressBar.StateNumber.ONE  ,R.layout.toolbar_compalint_type );
                    break;
                case R.layout.toolbar_municipality_type:
                    update(viewComplaintMapFragment , StateProgressBar.StateNumber.TWO  ,R.layout.toolbar_complaint_map );
                    break;
                case R.layout.toolbar_complaint_details:
                    update(viewComplaintMunicipalityFragment , StateProgressBar.StateNumber.THREE  ,R.layout.toolbar_municipality_type );

                    break;
                case R.layout.toolbar_complaint_summary:
                    update(viewComplaintDetailsFragment , StateProgressBar.StateNumber.FOUR  ,R.layout.toolbar_complaint_details );
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }



    @Override
    public void update(View view ,StateProgressBar.StateNumber number , int layout ){
        try {
            updateToolbar( view);
            updateStepView(number);
            this.layout = layout;
            PopBackStack();
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }


    @Override
    public void PopBackStack() {
        try {
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
            } else {
                AddComplaintActivity.getInstance().finish();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

}