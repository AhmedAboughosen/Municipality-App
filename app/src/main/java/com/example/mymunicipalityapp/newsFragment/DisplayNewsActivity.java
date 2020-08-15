package com.example.mymunicipalityapp.newsFragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymunicipalityapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DisplayNewsActivity extends AppCompatActivity {

    @BindView(R.id.header_image)
    ImageView header_image;

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.share)
    ImageView share;


    @BindView(R.id.content)
    TextView content;

    @BindView(R.id.date)
    TextView date;

    @BindView(R.id.title_card) TextView title;

    private  String title_header = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_news);
        try{
            ButterKnife.bind(this);
            initData();
        }catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(this , "هناك خطأ في التطبيق. يرجى تحديث الصفحة او اعادة المحاولة." , Toast.LENGTH_LONG).show();
        }
    }

    private  void initData(){
        try{
            Bundle  intent = getIntent().getExtras();
            if(intent != null){
                int image = intent.getInt("image");

                header_image.setImageResource(image);

                content.setText(intent.getString("content"));
                date.setText(intent.getString("date"));
                title_header = intent.getString("title");
                this.title.setText(title_header);
            }

            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareData(title_header);
                }
            });

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void shareData(String title) {
        try {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, title + " : " + "link of our app");


            startActivity(Intent.createChooser(sharingIntent, getApplication().getString(R.string.share_article)));
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
