package com.example.mymunicipalityapp.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public interface InteractToServer {

    void initServer();
    void connectToServer();
    void gatherData(Call<ResponseBody> retrofitClient);
    void _onResponse(Response<ResponseBody> response);
    void _onFailure();
    void parseJsonResponse(String s);
}
