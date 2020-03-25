package com.example.karan.instagramlogin.rest.Clients;

import com.example.karan.instagramlogin.rest.services.Auth;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    private static Auth REST_CLIENT;
    private static String BASE_URL = "https://api.instagram.com/";

    public RestClient() {}

    public static Auth getInstance() {

        //if REST_CLIENT is null then set-up again.
        if (REST_CLIENT == null) {
            setupRestClient();
        }
        return REST_CLIENT;
    }

    private static void setupRestClient() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        REST_CLIENT = retrofit.create(Auth.class);
    }
}