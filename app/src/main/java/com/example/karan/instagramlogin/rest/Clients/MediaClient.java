package com.example.karan.instagramlogin.rest.Clients;


import com.example.karan.instagramlogin.rest.services.MediaRequest;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MediaClient {

    private static MediaRequest mediaRequest;
    private static String BASE_URL = "https://graph.instagram.com";

    public MediaClient() {}

    public static MediaRequest getInstance() {

        //if REST_CLIENT is null then set-up again.
        if (mediaRequest == null) {
            setupRestClient();
        }
        return mediaRequest;
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
        mediaRequest = retrofit.create(MediaRequest.class);
    }
}