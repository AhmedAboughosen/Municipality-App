package com.example.mymunicipalityapp.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://projects.pos-alfarouk.com/.Private/lg-complaints/api/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;


    private RetrofitClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(0, TimeUnit.SECONDS)
                .writeTimeout(0, TimeUnit.SECONDS)
                .readTimeout(0, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder().client(okHttpClient).baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static synchronized RetrofitClient getInstance() {
        return (mInstance == null) ? mInstance = new RetrofitClient() : mInstance;
    }

    public HTTPOperation getHTTPOperation() {
        return retrofit.create(HTTPOperation.class);
    }
}
