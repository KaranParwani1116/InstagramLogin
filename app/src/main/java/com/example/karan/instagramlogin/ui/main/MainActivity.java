package com.example.karan.instagramlogin.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.karan.instagramlogin.ui.auth.AuthenticationDialog;
import com.example.karan.instagramlogin.ui.auth.AuthenticationListener;
import com.example.karan.instagramlogin.R;
import com.example.karan.instagramlogin.adapters.PostsAdapter;
import com.example.karan.instagramlogin.models.AuthToken;
import com.example.karan.instagramlogin.models.Media;
import com.example.karan.instagramlogin.preferences.AppPreferences;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements AuthenticationListener {

    private static final String TAG = "MainActivity";

    //Initializing layout views
    @BindView(R.id.btn_login)
    Button button;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private String token = null;
    private String code = null;
    private AppPreferences appPreferences = null;
    private AuthenticationDialog authenticationDialog = null;

    //Auth token Model Object
    AuthToken authToken = null;

    private List<Media.Datum> data = new ArrayList<>();
    private PostsAdapter postsAdapter;

    //viewModel
    private MainViewModel mainViewModel;

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        appPreferences = new AppPreferences(this);

        //intitalize_recycler_view
        initrecyclerview();

        //initializing viewModel
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

    }

    public void initrecyclerview(){
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(linearLayoutManager);
        postsAdapter = new PostsAdapter(data, this);
        recyclerView.setAdapter(postsAdapter);
    }

    @Override
    public void onTokenReceived(String auth_code) {
        Log.d(TAG, auth_code);
        appPreferences.putString(AppPreferences.CODE, auth_code);
        code = auth_code;

        mainViewModel.Authinit(code);
        //observing the changes
        mainViewModel.getMedia().observe(this, new Observer<List<Media.Datum>>() {
            @Override
            public void onChanged(List<Media.Datum> data) {
                Log.d(TAG, "onChanged: " +data.size());
                postsAdapter.setData(data);
            }
        });

    }

    @OnClick(R.id.btn_login)
    public void onClick() {
        if (token != null) {

        } else {
            authenticationDialog = new AuthenticationDialog(this, this);
            authenticationDialog.setCancelable(true);
            authenticationDialog.show();
        }
    }

}
