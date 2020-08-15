package com.example.mymunicipalityapp.municipalityFragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mymunicipalityapp.R;
import com.example.mymunicipalityapp.adapter.MunicipalityAdapter;
import com.example.mymunicipalityapp.addComplaintActivity.AddComplaintActivity;
import com.example.mymunicipalityapp.addComplaintActivity.NotFoundComplaint;
import com.example.mymunicipalityapp.addSuggestionActivity.AddSuggestionActivity;
import com.example.mymunicipalityapp.addSuggestionActivity.NotFoundSuggestion;
import com.example.mymunicipalityapp.interfaces.InteractToServer;
import com.example.mymunicipalityapp.model.MunicipalityModel;
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

public class MunicipalityFragment extends Fragment implements InteractToServer {

    public @BindView(R.id.Recycler_list)
    RecyclerView recyclerView;
    public @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private boolean iscomplaint;

    public MunicipalityAdapter customAdapter;


    private static MunicipalityFragment ourinstance;


    public static MunicipalityFragment getInstance() {
        return ourinstance;
    }


    public MunicipalityFragment(boolean iscomplaint) {

        this.iscomplaint = iscomplaint;
        ourinstance = this;
    }

    private void setSwipeRefreshLayout() {
        // Set the color scheme of the SwipeRefreshLayout
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        // Set up OnRefreshListener that is invoked when the user performs a swipe-to-refresh gesture.
        swipeRefreshLayout.setOnRefreshListener(() -> {
            // Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");

            try {
                initServer();
                swipeRefreshLayout.setRefreshing(false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_base__swiperefres_recyclerview, container, false);

        try {
            ButterKnife.bind(this, view);
            ArrayList<MunicipalityModel> list_Municipality_Model = new ArrayList<>();

            for (int i = 0; i <= 3; i++) {
                MunicipalityModel c = new MunicipalityModel();
                c.setMunicipality_name("");
                list_Municipality_Model.add(c);
            }
            setSwipeRefreshLayout();
            this.customAdapter = new MunicipalityAdapter(getActivity(), list_Municipality_Model, iscomplaint);
            initRecyclerView(this.customAdapter);

            initServer();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return view;
    }

    protected void initRecyclerView(RecyclerView.Adapter adapter) {
        try {
            initRecyclerView.initView(recyclerView, getActivity(), adapter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initServer() {
        try {
            boolean check = (iscomplaint) ? NetworkUtil.isNetworkConnected((ConnectivityManager) AddComplaintActivity.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)) : NetworkUtil.isNetworkConnected((ConnectivityManager) AddSuggestionActivity.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE));
            if (check) {
                connectToServer();
            } else {
                Toast.makeText(getActivity(), "لا يوجد اتصال بالإنترنت", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void connectToServer() {
        gatherData(RetrofitClient.getInstance().getHTTPOperation().getMunicipalities());
    }

    @Override
    public void gatherData(Call<ResponseBody> retrofitClient) {
        try {


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
        } catch (SecurityException s) {
            Toast.makeText(getActivity(), s.getMessage() + "+ 2", Toast.LENGTH_LONG).show();
        } catch (Exception es) {
            es.printStackTrace();
        }

    }

    @Override
    public void _onResponse(Response<ResponseBody> response) {
        String s;

        try {
            if (response.code() == 200) {
                assert response.body() != null;
                s = response.body().string();
                parseJsonResponse(s);
            } else {
                _onFailure();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void _onFailure() {

        if (iscomplaint) {
            AddComplaintActivity.getInstance().updateComplaintUI.PopBackStack();
            NotFoundComplaint notFoundComplaint = new NotFoundComplaint();
            notFoundComplaint.setFragment(new MunicipalityFragment(true), R.layout.toolbar_municipality_type);
            AddComplaintActivity.getInstance().update_view(R.layout.toolbar_municipality_type, notFoundComplaint);
        } else {
            AddSuggestionActivity.getInstance().updateSuggestionUI.PopBackStack();
            NotFoundSuggestion notFoundSuggestion = new NotFoundSuggestion();
            notFoundSuggestion.setFragment(new MunicipalityFragment(false), R.layout.toolbar_municipality_type);
            AddSuggestionActivity.getInstance().update(R.layout.toolbar_municipality_type, notFoundSuggestion);
        }
    }


    @Override
    public void parseJsonResponse(String s) {

        if (s != null) {
            try {

                ArrayList<MunicipalityModel> list = new ArrayList<>();
                JSONArray jsonObject = new JSONArray(s);

                // looping through All complaint
                for (int i = 0; i < jsonObject.length(); i++) {
                    JSONObject c = jsonObject.getJSONObject(i);
                    int id = c.getInt("id");
                    String name = c.getString("name");
                    double longitude = c.getDouble("longitude");
                    double latitude = c.getDouble("latitude");


                    MunicipalityModel v = new MunicipalityModel();
                    v.setMunicipality_name(name);
                    v.setLongitude(longitude);
                    v.setLatitude(latitude);
                    v.setId(id);
                    // adding data to Municipality list
                    list.add(v);
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
