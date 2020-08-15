package com.example.mymunicipalityapp.addComplaintActivity;


import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mymunicipalityapp.R;
import com.example.mymunicipalityapp.adapter.ComplaintTypeAdapter;
import com.example.mymunicipalityapp.interfaces.InteractToServer;
import com.example.mymunicipalityapp.model.complaintModel.ComplaintTypeModel;
import com.example.mymunicipalityapp.network.NetworkUtil;
import com.example.mymunicipalityapp.network.RetrofitClient;
import com.example.mymunicipalityapp.util.initRecyclerView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComplaintTypeFragment extends Fragment implements InteractToServer {

    @BindView(R.id.Recycler_list)
    RecyclerView recyclerView;

    @BindView(R.id.swipeRefreshLayout )
    SwipeRefreshLayout swipeRefreshLayout;



    private ArrayList<ComplaintTypeModel> list_complaint_type;
    private ComplaintTypeAdapter customAdapter;


    public ComplaintTypeFragment() {
        // Required empty public constructor
        list_complaint_type = new ArrayList<>();
    }


    private void  initSwipeRefreshLayout()
    {
        // Set the color scheme of the SwipeRefreshLayout
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        // Set up OnRefreshListener that is invoked when the user performs a swipe-to-refresh gesture.
        swipeRefreshLayout.setOnRefreshListener(() -> {
           // Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");

            try {
                initServer();

                swipeRefreshLayout.setRefreshing(false);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.layout_base__swiperefres_recyclerview, container, false);

        try{
            ButterKnife.bind(this , view);

            for (int i = 0; i < 3; i++) {
                ComplaintTypeModel c = new ComplaintTypeModel();
                c.setTitle("");
                list_complaint_type.add(c);
            }


            initSwipeRefreshLayout();
            initRecyclerView();

            initServer();

        }catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(getActivity() , "هناك خطأ في التطبيق. يرجى  اعادة المحاولة." , Toast.LENGTH_LONG).show();
        }

        return view;
    }



    @Override
    public void initServer(){
        try {
            if(NetworkUtil.isNetworkConnected((ConnectivityManager)AddComplaintActivity.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)))
            {
                connectToServer();
            }
            else{
                Toast.makeText(getActivity() , "لا يوجد اتصال بالإنترنت" , Toast.LENGTH_LONG).show();
            }
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }



    private  void initRecyclerView(){
        try {
            this.customAdapter = new ComplaintTypeAdapter(getActivity(),list_complaint_type);
            initRecyclerView.initView(recyclerView , getActivity() , this.customAdapter );
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    @Override
    public void connectToServer(){
        gatherData(RetrofitClient.getInstance().getHTTPOperation().getTypesOfComplaints());
    }


    @Override
    public void gatherData(Call<ResponseBody> retrofitClient){

        try{


            retrofitClient.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                    _onResponse(response);
                }

                //Do user registration using the api call
                @Override
                public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                    _onFailure();
                }
            });

        }catch (SecurityException s){
                Toast.makeText(getActivity() , s.getMessage() +"", Toast.LENGTH_LONG).show();
        }catch (Exception ex){
            ex.printStackTrace();
        }




    }

    @Override
    public void _onResponse(Response<ResponseBody> response) {
        String s ;

        try {
            if(response.code() == 200)
            {
                assert response.body() != null;
                s = response.body().string();
                parseJsonResponse(s);
            }
            else {
                _onFailure();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void _onFailure(){
        AddComplaintActivity.getInstance().updateComplaintUI.PopBackStack();

        NotFoundComplaint notFoundComplaint = new NotFoundComplaint();
        notFoundComplaint.setFragment(new ComplaintTypeFragment() ,R.layout.toolbar_compalint_type);
        AddComplaintActivity.getInstance().update_view(R.layout.toolbar_compalint_type , notFoundComplaint);
    }



    @Override
    public void parseJsonResponse(String s) {


        if (s != null) {
            try {

                ArrayList<ComplaintTypeModel> list = new ArrayList<>();
                JSONArray jsonObject = new JSONArray(s);

                // looping through All complaint
                for (int i = 0; i < jsonObject.length(); i++) {
                    JSONObject c = jsonObject.getJSONObject(i);
                    int id = c.getInt("id");
                    String compalintTitle = c.getString("name");
                    String compalintDescription = c.getString("desc");


                    ComplaintTypeModel v = new ComplaintTypeModel();
                    v.setTitle(compalintTitle);
                    v.setInfo(compalintDescription);
                    v.setId(id);

                    // adding data to complaint list
                    list.add(v);
                }

                if(list.size() != 0){
                    this.customAdapter.setItems(list);
                    this.customAdapter.notifyDataSetChanged();
                }


            } catch (JSONException e) {
                e.printStackTrace();
                _onFailure();

            } catch (Exception ex){
                ex.printStackTrace();
            }

        }
    }
}
