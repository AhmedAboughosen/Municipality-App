package com.example.mymunicipalityapp.mainActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mymunicipalityapp.R;
import com.example.mymunicipalityapp.addComplaintActivity.AddComplaintActivity;
import com.example.mymunicipalityapp.addSuggestionActivity.AddSuggestionActivity;
import com.example.mymunicipalityapp.util.StartActivity;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment {


    @BindView(R.id.logo)
    ImageView logo;


    @BindView(R.id.menus)
    LinearLayout menus;

    @BindView(R.id.cardview_complaint)
    CardView cardview_complaint;

    @BindView(R.id.cardview_suggestion) CardView cardview_suggestion;


    public AddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add, container, false);

        try {
            ButterKnife.bind(this , view);
            initComponent();
        }catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(getActivity() , "هناك خطأ في التطبيق. يرجى تحديث الصفحة او اعادة المحاولة." , Toast.LENGTH_LONG).show();

        }
        return view;
    }


    private void initComponent(){

        try {
            Animation frombottom = AnimationUtils.loadAnimation(getActivity(), R.anim.frombottom);

            menus.startAnimation(frombottom);

            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

            logo.animate().alpha(0).setDuration(800).setStartDelay(600);

            cardview_complaint.setOnClickListener(v -> StartActivity.StartIntent(getActivity() ,AddComplaintActivity.class ));

            cardview_suggestion.setOnClickListener(view -> StartActivity.StartIntent(getActivity() ,AddSuggestionActivity.class ));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
