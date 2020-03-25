package com.example.karan.instagramlogin.repositories;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.karan.instagramlogin.Constants.constants;
import com.example.karan.instagramlogin.models.AuthToken;
import com.example.karan.instagramlogin.models.Media;
import com.example.karan.instagramlogin.rest.Clients.MediaClient;
import com.example.karan.instagramlogin.rest.Clients.RestClient;
import com.example.karan.instagramlogin.rest.services.Auth;
import com.example.karan.instagramlogin.rest.services.MediaRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityRepo {
    private static final String TAG = "MainActivityRepo";

    private static MainActivityRepo instance;
    private MutableLiveData<List<Media.Datum>>data = new MutableLiveData<>();
    private List<Media.Datum>datumList = new ArrayList<>();

    public static MainActivityRepo getInstance(){
        if(instance == null) {
            instance = new MainActivityRepo();
        }
        return instance;
    }

    public MutableLiveData<List<Media.Datum>> getAccessToken(String code){

        Auth auth = RestClient.getInstance();

        Call<AuthToken> authTokenCall = auth.getAuthToken(constants.client_id, constants.client_secret, "authorization_code", constants.redirect_uri,
                code);


        authTokenCall.enqueue(new Callback<AuthToken>() {

            @Override
            public void onResponse(Call<AuthToken> call, Response<AuthToken> response) {
                Log.d(TAG, "onResponse: " + response.body().getAccessToken());

                RequestMedia(response.body());

            }

            @Override
            public void onFailure(Call<AuthToken> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });


        return data;
    }

    //hitting media api
    private void RequestMedia(AuthToken authToken) {

        String access_Token = authToken.getAccessToken();
        String user_id = authToken.getUserId();

        //building querymap
        Map<String, String> query = new HashMap<>();
        query.put("fields", "media_url");
        query.put("access_token", access_Token);

        MediaRequest mediaRequest = MediaClient.getInstance();
        Call<Media> mediaCall = mediaRequest.getMedia(user_id, query);

        mediaCall.enqueue(new Callback<Media>() {
            @Override
            public void onResponse(Call<Media> call, Response<Media> response) {
                Log.d(TAG, "onResponse: media" + response.body().getData().get(0).getMediaUrl());
                Log.d(TAG, "onResponse: " + response.body().getData().size());
                datumList = response.body().getData();
                Log.d(TAG, "RequestMedia: " + datumList.size());
                data.setValue(datumList);
            }

            @Override
            public void onFailure(Call<Media> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });



    }
}
