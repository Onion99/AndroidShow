package com.onion.android.app.plex.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.onion.android.R;
import com.onion.android.app.plex.vm.SplashViewModel;
import com.onion.android.app.utils.Tools;
import com.onion.android.databinding.PlexActivitySplashBinding;
import com.onion.android.java.base.PlexBaseActivity;

import info.guardianproject.netcipher.client.StrongBuilder;
import info.guardianproject.netcipher.client.StrongOkHttpClientBuilder;
import okhttp3.OkHttpClient;

import static com.onion.android.app.constants.PlexConstants.SERVER_BASE_URL;

public class SplashActivity extends PlexBaseActivity<PlexActivitySplashBinding, SplashViewModel> implements StrongBuilder.Callback<OkHttpClient> {

    private void initNetcipher(){
        // Netcipher-step-2-Creating a Builder
        try {
            StrongOkHttpClientBuilder
                    .forMaxSecurity(this)
                    .withTorValidation()
                    .build(this);
        } catch (Exception e) {
            Toast.makeText(this, "The App will not run until you close your sniffer", Toast.LENGTH_LONG).show();
            finish();
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initNetcipher();
        Tools.loadHttpImg(getApplication(),binding.logoImageTop,SERVER_BASE_URL +"image/logo");
        Tools.postDelayed(()->{
            finish();
        },1600);
    }

    @Override
    public int getBindingContent(@Nullable Bundle savedInstanceState) {
        return R.layout.plex_activity_splash;
    }

    @Override
    public void onConnected(OkHttpClient connection) {
    }

    @Override
    public void onConnectionException(Exception e) {

    }

    @Override
    public void onTimeout() {

    }

    @Override
    public void onInvalid() {

    }
}
