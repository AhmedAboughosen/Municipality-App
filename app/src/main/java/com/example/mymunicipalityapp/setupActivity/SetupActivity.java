package com.example.mymunicipalityapp.setupActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymunicipalityapp.R;
import com.example.mymunicipalityapp.adapter.SlideAdapter;
import com.example.mymunicipalityapp.mainActivity.MainActivity;
import com.example.mymunicipalityapp.model.SetupScreenItem;
import com.example.mymunicipalityapp.util.StartActivity;
import com.example.mymunicipalityapp.viewModel.FirstActivity;
import com.example.mymunicipalityapp.model.SharedPrefSplashActivityManager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetupActivity extends AppCompatActivity {



    @BindView(R.id.slideviewpager)
    ViewPager viewPager;
    @BindView(R.id.tabIndicator)
    TabLayout tabIndicator;
    @BindView(R.id.next_button)
    Button start_button;
    @BindView(R.id.skip)  Button skip;
    private int CurrentItem = 3 ;
    SharedPrefSplashActivityManager s ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        try{
            s = new SharedPrefSplashActivityManager(this);
            ButterKnife.bind(this);
            initviewPager();
            initButtons();
        }catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(this , "هناك خطأ في التطبيق. يرجى تحديث الصفحة او اعادة المحاولة." , Toast.LENGTH_LONG).show();
        }


    }

    private void initviewPager(){

        try {

            //Data of view pager
            ArrayList<SetupScreenItem> setupScreenItems = new ArrayList<>();

            setupScreenItems.add(new SetupScreenItem("كن واعيا","ستجد كل ما يهمك في معرفة الحملات التوعوية الخاصة بالمواطنين بالإضافة يمكنك مشاركة المحتوى في شبكات التواصل الاجتماعي.", R.raw.news));
            setupScreenItems.add(new SetupScreenItem("وتق الشكوى","بإمكانك توثيق الشكوى بالإبلاغ عنها بطريقة سهله و سلسة مع استخدام جميع الوسائط سواء صورة او ملف .pdf", R.raw.complaint));
            setupScreenItems.add(new SetupScreenItem("تابع","أصبح الأن بإمكانك متابعة الشكوى و المقترحات التي تم توثيقها و معرفة حالتها.", R.raw.search));
            setupScreenItems.add(new SetupScreenItem("بنوك الأفكار","هو صندوق مقترحات لاستقبال الأفكار التي من شأنها تطوير الخدمات في جهات مختلفة وايصال صوتك بمقترح أو فكرة للمسؤولين حتى يتم تطبيقها والاستفادة من خدماتها.", R.raw.brainstorming));

            SlideAdapter  slideAdapter = new SlideAdapter(this,setupScreenItems );
            viewPager.setAdapter(slideAdapter);


            viewPager.setCurrentItem(3);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                public void onPageScrollStateChanged(int state) {}
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

                public void onPageSelected(int position) {
                    // Check if this is the page you want.
                    CurrentItem = position;
                    if(CurrentItem == 0)
                    {
                        start_button.setText("انهاء");
                    }
                    else {
                        start_button.setText("التالي");
                    }
                }
            });

            tabIndicator.setupWithViewPager(viewPager);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        if((s.isAliveActivity()))
        {
            Intent();
        }
    }

    private void Intent(){
        try {
            Intent intent = new Intent(SetupActivity.this, FirstActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    private void nextActivity(){
        try {
           s.setActivity("1");
            Intent();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    private void initButtons()
    {

        start_button.setOnClickListener(view -> {
            try{
                CurrentItem = viewPager.getCurrentItem();
                if (CurrentItem != 0 && !start_button.getText().equals("انهاء"))
                {
                    viewPager.setCurrentItem(CurrentItem - 1);
                } else
                {
                    nextActivity();
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }


        });

        skip.setOnClickListener(view -> nextActivity());
    }


}
