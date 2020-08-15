package com.example.mymunicipalityapp.searchFragment;


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
import com.example.mymunicipalityapp.adapter.ListofAllComplaintsAdapter;
import com.example.mymunicipalityapp.interfaces.InteractToServer;
import com.example.mymunicipalityapp.mainActivity.SearchFragment;
import com.example.mymunicipalityapp.model.complaintModel.ComplaintItem;
import com.example.mymunicipalityapp.network.NetworkUtil;
import com.example.mymunicipalityapp.network.RetrofitClient;
import com.example.mymunicipalityapp.model.user.UserManager;
import com.example.mymunicipalityapp.util.initRecyclerView;
import com.example.mymunicipalityapp.util.UpdateFragment;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllComplaintFragment extends Fragment implements InteractToServer {



    @BindView(R.id.Recycler_list)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout )
    SwipeRefreshLayout swipeRefreshLayout;



    private ArrayList<ComplaintItem> list_complaint_item;
    private ListofAllComplaintsAdapter customAdapter;
    private SearchFragment context;

    public AllComplaintFragment(SearchFragment context) {
        // Required empty public constructor
        list_complaint_item = new ArrayList<>();
        this.context = context;
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

        View view=  inflater.inflate(R.layout.layout_base__swiperefres_recyclerview, container, false);

        try {

            ButterKnife.bind(this , view);
            for (int i = 0; i < 3; i++) {
                ComplaintItem c = new ComplaintItem();
                c.setNumber(0);
                list_complaint_item.add(c);
            }

            initRecyclerView();
            initSwipeRefreshLayout();

            initServer();
        }catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(getActivity() , "هناك خطأ في التطبيق. يرجى تحديث الصفحة او اعادة المحاولة." , Toast.LENGTH_LONG).show();
        }

        return view;
    }


    private  void initRecyclerView(){
        try {
            this.customAdapter = new ListofAllComplaintsAdapter(getActivity(),list_complaint_item);
            initRecyclerView.initView( recyclerView , getActivity() , this.customAdapter);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void initServer() {
        if(NetworkUtil.isNetworkConnected( (ConnectivityManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CONNECTIVITY_SERVICE)))
        {
            connectToServer();
        }
        else{
            Toast.makeText(getActivity() ,  "لا يوجد اتصال بالإنترنت ", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void connectToServer(){

        try {
            UserManager loginSharedPrefManager = new UserManager(this.getActivity());
            String phone = loginSharedPrefManager.getPhoneNumber();
            String id = loginSharedPrefManager.getNationalID();

           // gatherData(RetrofitClient.getInstance().getHTTPOperation().getComplaintsState("120051236547" , "0945635557" ));
            gatherData(RetrofitClient.getInstance().getHTTPOperation().getComplaintsState(id, phone ));

        }catch (Exception ex){
            ex.printStackTrace();
        }
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
                    try {
                        _onFailure() ;
                    }catch (Exception ex){
                        ex.printStackTrace();

                    }
                }
            });
        }catch (SecurityException s){
            s.printStackTrace();
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
            else{
                _onFailure();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void _onFailure() {
        NotFoundItemFragment notFoundItemFragment = new NotFoundItemFragment();
        notFoundItemFragment.setFragment(new AllComplaintFragment(context) , context);
        UpdateFragment.loadFragment(notFoundItemFragment , R.id.searchpager , context.getChildFragmentManager());
    }


    @Override
    public void parseJsonResponse(String s) {


        if (s != null) {
            try {

                ArrayList<ComplaintItem> list = new ArrayList<>();
                JSONArray jsonObject = new JSONArray(s);

                // looping through All complaint
                for (int i = 0; i < jsonObject.length(); i++) {
                    JSONObject c = jsonObject.getJSONObject(i);
                    int id = c.getInt("id");
                    String state = c.getString("state");
                    String details = c.getString("details");
                    String created_at = c.getString("created_at");
                    String type = c.getString("type");
                    String municipality = c.getString("municipality");


                    ComplaintItem v = new ComplaintItem();
                    v.setNumber(id);
                    v.setDetails(details);
                    v.setMunicipality(municipality);
                    v.setState(state);
                    v.setDate(created_at);
                    v.setType(type);
                    v.setBackgroundColor(v.getState().equals("قيد المراجعة") ? R.drawable.in_review_drawable : v.getState().equals("قيد المعالجة") ? R.drawable.in_execute_drawable :R.drawable.in_finish_drawable );
                    // adding data to complaint list
                    list.add(v);
                }

                if(list.size() != 0){
                    customAdapter.setItems(list);
                    customAdapter.notifyDataSetChanged();
                }
                else {
                    UpdateFragment.loadFragment(new NoItemFragment(true)  , R.id.searchpager , context.getChildFragmentManager());
                }

            } catch (JSONException e) {
                e.printStackTrace();
                _onFailure();
            }catch (Exception ex){
                ex.printStackTrace();
                Toast.makeText(getActivity() , "هناك خطأ في التطبيق. يرجى تحديث الصفحة او اعادة المحاولة." , Toast.LENGTH_LONG).show();
            }

        }
    }

}
