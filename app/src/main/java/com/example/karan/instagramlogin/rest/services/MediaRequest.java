package com.example.karan.instagramlogin.rest.services;

import com.example.karan.instagramlogin.models.Media;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface MediaRequest {

    @GET("/{user-id}/media")
    Call<Media>getMedia(@Path("user-id")String UserId,@QueryMap Map<String,String>hash);
}
