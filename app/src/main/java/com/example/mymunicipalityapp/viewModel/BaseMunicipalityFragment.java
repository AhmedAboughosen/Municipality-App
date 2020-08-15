package com.example.mymunicipalityapp.viewModel;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mymunicipalityapp.R;

import com.example.mymunicipalityapp.model.MunicipalityModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseMunicipalityFragment extends Fragment {

   public  @BindView(R.id.Recycler_list)
    RecyclerView recyclerView;
    public @BindView(R.id.swipeRefreshLayout )
    SwipeRefreshLayout swipeRefreshLayout;


    protected ArrayList<MunicipalityModel> list_Municipality_Model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.layout_base__swiperefres_recyclerview, container, false);

        try {
            ButterKnife.bind(this ,  view);
            list_Municipality_Model = new ArrayList<>();

            for (int i = 0; i <= 3; i++) {
                MunicipalityModel c = new MunicipalityModel();
                c.setMunicipality_name("");
                list_Municipality_Model.add(c);
            }


        }catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(getActivity() , "هناك خطأ في التطبيق. يرجى تحديث الصفحة او اعادة المحاولة." , Toast.LENGTH_LONG).show();
        }

        return view;
    }


    protected  void initRecyclerView( RecyclerView.Adapter adapter){
        try {
            // set main_logo LinearLayoutManager with default vertical orientation
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter); // set the Adapter to RecyclerView
            Log.e("initRecyclerView" , "initRecyclerView");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
