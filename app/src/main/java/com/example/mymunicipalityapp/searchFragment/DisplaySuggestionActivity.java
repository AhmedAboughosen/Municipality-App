package com.example.mymunicipalityapp.searchFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymunicipalityapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DisplaySuggestionActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    // init the item view's
    @BindView(R.id.state_suggestion)
    TextView state_suggestion;
    @BindView(R.id.suggestion_number)
    TextView suggestion_number;


    @BindView(R.id.typeofsuggestions)
    TextView typeofsuggestions;

    @BindView(R.id.date_card)
    TextView date_card;
    @BindView(R.id.municipality_name)
    TextView municipality_name;

    @BindView(R.id.detailscontent)
    TextView detailscontent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_suggestion);
        try{
            ButterKnife.bind(this);
            initToolbar();
            initComponent();
        }catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(getApplicationContext() , "هناك خطأ في التطبيق. يرجى تحديث الصفحة او اعادة المحاولة." , Toast.LENGTH_LONG).show();
        }
    }

    private void initToolbar(){
        View view= getLayoutInflater().inflate(R.layout.toolbar_display_activity,null);
        TextView textView = view.findViewById(R.id.title_text);
        textView.setText("تفاصيل المقترح");
        toolbar.addView(view);

        view.findViewById(R.id.keyboard_arrow).setOnClickListener(view1 -> finish());

    }

    private void initComponent(){
        try{
            String state_suggestion_text = getIntent().getStringExtra("state_suggestion_text");
            int state_suggestion_background = getIntent().getIntExtra("state_suggestion_background" , Color.GRAY);

            int number_suggestion = getIntent().getIntExtra("number_suggestion" , 0);
            String detail_suggestion = getIntent().getStringExtra("detail_suggestion");

            String date_card = getIntent().getStringExtra("date_card");
            String type = getIntent().getStringExtra("type");
            String municipality = getIntent().getStringExtra("municipality");

            municipality_name.setText(municipality);
            typeofsuggestions.setText(type);

            state_suggestion.setText(state_suggestion_text);
            state_suggestion.setBackgroundResource(state_suggestion_background);

            this.suggestion_number.setText(String.valueOf(number_suggestion));
            detailscontent.setText(detail_suggestion);

            this.date_card.setText(date_card);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
