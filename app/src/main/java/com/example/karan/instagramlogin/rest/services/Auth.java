package com.example.karan.instagramlogin.rest.services;

import com.example.karan.instagramlogin.models.AuthToken;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Auth {

    @FormUrlEncoded
    @POST("oauth/access_token")
    Call<AuthToken>getAuthToken(@Field("client_id")String clientid,@Field("client_secret")String client_secret,@Field("grant_type")String grant_type
    ,@Field("redirect_uri")String redirect_uri,@Field("code")String code);
}
