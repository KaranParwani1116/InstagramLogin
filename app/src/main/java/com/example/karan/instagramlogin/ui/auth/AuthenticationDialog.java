package com.example.karan.instagramlogin.ui.auth;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;

import com.example.karan.instagramlogin.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AuthenticationDialog extends Dialog {
    private static final String TAG = "AuthenticationDialog";

    @BindView(R.id.webView)
    WebView webView;

    //urls
    private final String request_url;
    private final String redirect_url;

    //Constructor Arguments
    private AuthenticationListener listener;
    private Context context;

    public AuthenticationDialog(@NonNull Context context, AuthenticationListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
        this.redirect_url = context.getResources().getString(R.string.redirect_url);
        this.request_url = context.getResources().getString(R.string.base_url) +
                "oauth/authorize/?client_id=" +
                context.getResources().getString(R.string.client_id) +
                "&redirect_uri=" + redirect_url +
                "&response_type=code&scope=user_profile,user_media";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.auth_dialog);
        ButterKnife.bind(this);

        //working with webview to get authcode
        initializeWebView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initializeWebView() {
        Log.d(TAG, request_url);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(webViewClient);
        webView.loadUrl(request_url);
    }

    WebViewClient webViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d(TAG, "shouldOverrideUrlLoading: " + url);
            if (url.startsWith(redirect_url)) {
                AuthenticationDialog.this.dismiss();
                return true;
            }
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.d(TAG, url);
            if (url.contains("code=")) {
                String access_token = url.substring(url.lastIndexOf("=") + 1, url.lastIndexOf("#"));

                Log.d(TAG, "onPageFinished: " + access_token);
                listener.onTokenReceived(access_token);
                dismiss();
            } else if (url.contains("?error")) {
                Log.e("access_token", "getting error fetching access token");
                dismiss();
            }
        }
    };
}
