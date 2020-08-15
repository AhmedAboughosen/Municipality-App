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

public class DisplayComplaintActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    // init the item view's
    @BindView(R.id.state_complaint)
    TextView state_complaint;
    @BindView(R.id.complaint_number)
    TextView number_complaint;
    @BindView(R.id.typeofcomaplint)
    TextView typeofcomaplint;
    @BindView(R.id.date_card)
    TextView date_card;
    @BindView(R.id.municipality_name)
    TextView municipality_name;

    @BindView(R.id.detailscontent)
    TextView detailscontent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_complaint);
        try{
            ButterKnife.bind(this);

            iniToolbar();
            initComponent();
        }catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(getApplicationContext() , "هناك خطأ في التطبيق. يرجى تحديث الصفحة او اعادة المحاولة." , Toast.LENGTH_LONG).show();
        }
    }


    private void  iniToolbar(){
        View view= getLayoutInflater().inflate(R.layout.toolbar_display_activity,null);

        toolbar.addView(view);
        view.findViewById(R.id.keyboard_arrow).setOnClickListener(view1 -> finish());

    }


    private void initComponent(){
        try{
            String state_complaint_text = getIntent().getStringExtra("state_complaint_text");
            int state_complaint_background = getIntent().getIntExtra("state_complaint_background" , Color.GRAY);

            int number_complaint = getIntent().getIntExtra("number_complaint" , 0);
            String detail_complaint = getIntent().getStringExtra("detail_complaint");

            String date_card = getIntent().getStringExtra("date_card");
            String type = getIntent().getStringExtra("type");
            String municipality = getIntent().getStringExtra("municipality");

            municipality_name.setText(municipality);
            typeofcomaplint.setText(type);
            state_complaint.setText(state_complaint_text);
            state_complaint.setBackgroundResource(state_complaint_background);
            this.number_complaint.setText(String.valueOf(number_complaint));
            detailscontent.setText(detail_complaint);

            this.date_card.setText(date_card);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
