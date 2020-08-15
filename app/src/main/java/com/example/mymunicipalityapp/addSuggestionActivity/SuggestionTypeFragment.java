package com.example.mymunicipalityapp.addSuggestionActivity;


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
import com.example.mymunicipalityapp.adapter.SuggestionTypeAdapter;
import com.example.mymunicipalityapp.interfaces.InteractToServer;
import com.example.mymunicipalityapp.model.suggestionModel.SuggestionTypeModel;
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
public class SuggestionTypeFragment extends Fragment implements InteractToServer {



    @BindView(R.id.Recycler_list)
    RecyclerView recyclerView;


    @BindView(R.id.swipeRefreshLayout )
    SwipeRefreshLayout swipeRefreshLayout;



    private ArrayList<SuggestionTypeModel> list_Suggestion_type;
    private SuggestionTypeAdapter customAdapter;


    public SuggestionTypeFragment() {
        // Required empty public constructor
        list_Suggestion_type = new ArrayList<>();
    }


    private void  setSwipeRefreshLayout()
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
        try {
            ButterKnife.bind(this , view);
            SuggestionTypeModel d ;
            list_Suggestion_type = new ArrayList<>();
            for (int i = 0; i <= 3; i++) {

                 d = new SuggestionTypeModel();
                 d.setTitle("");
                list_Suggestion_type.add(d);
            }

            setSwipeRefreshLayout();
            initRecyclerView();

            initServer();
        }catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(getActivity() , "هناك خطأ في التطبيق. يرجى تحديث الصفحة او اعادة المحاولة." , Toast.LENGTH_LONG).show();
        }

        return view;
    }


    private  void initRecyclerView(){
        try {
            this.customAdapter = new SuggestionTypeAdapter(getActivity(),list_Suggestion_type);
            initRecyclerView.initView(recyclerView , getActivity() ,  this.customAdapter );
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }



    @Override
    public void initServer() {
        if(NetworkUtil.isNetworkConnected((ConnectivityManager)AddSuggestionActivity.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)))
        {
            connectToServer();
        }
        else{
            Toast.makeText(getActivity() , "لا يوجد اتصال بالإنترنت" , Toast.LENGTH_LONG).show();
        }

    }



    @Override
    public  void connectToServer(){
        gatherData(RetrofitClient.getInstance().getHTTPOperation().getTypesOfSuggestions());
    }

    @Override
    public void gatherData(Call<ResponseBody> retrofitClient) {
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
    public void _onFailure()
    {
        AddSuggestionActivity.getInstance().updateSuggestionUI.PopBackStack();
        NotFoundSuggestion notFoundSuggestion = new NotFoundSuggestion();
        notFoundSuggestion.setFragment(new SuggestionTypeFragment() ,R.layout.toolbar_suggestion_type);
        AddSuggestionActivity.getInstance().update(R.layout.toolbar_suggestion_type , notFoundSuggestion);
    }

    @Override
    public void parseJsonResponse(String s) {

            if (s != null) {
                try {
                    ArrayList<SuggestionTypeModel> list = new ArrayList<>();
                    JSONArray jsonObject = new JSONArray(s);

                    SuggestionTypeModel d ;
                    // looping through All complaint
                    for (int i = 0; i < jsonObject.length(); i++) {

                        JSONObject c = jsonObject.getJSONObject(i);

                        int id = c.getInt("id");
                        String name = c.getString("name");
                        String Description = c.getString("desc");


                        d = new SuggestionTypeModel();
                        d.setTitle(name);
                        d.setId(id);
                        d.setInfo(Description);
                        // adding data to complaint list
                        list.add(d);
                    }

                    if (list.size() != 0) {
                        this.customAdapter.setItems(list);
                        this.customAdapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    _onFailure();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

    }


}
