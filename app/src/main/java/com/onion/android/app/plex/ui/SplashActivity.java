package com.onion.android.app.plex.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import info.guardianproject.netcipher.client.StrongBuilder;
import info.guardianproject.netcipher.client.StrongOkHttpClientBuilder;
import okhttp3.OkHttpClient;

public class SplashActivity extends AppCompatActivity implements StrongBuilder.Callback<OkHttpClient> {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
