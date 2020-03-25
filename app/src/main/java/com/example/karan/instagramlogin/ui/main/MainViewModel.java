package com.example.karan.instagramlogin.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.karan.instagramlogin.models.Media;
import com.example.karan.instagramlogin.repositories.MainActivityRepo;

import java.util.List;

public class MainViewModel extends ViewModel {

    private static final String TAG = "MainViewModel";
    private MainActivityRepo mRepo;

    private MutableLiveData<List<Media.Datum>>data = null;

    public void Authinit(String code) {
        if(data!=null)
            return;

        mRepo = MainActivityRepo.getInstance();
        data = mRepo.getAccessToken(code);


    }

    public LiveData<List<Media.Datum>> getMedia(){
        return data;
    }

}
