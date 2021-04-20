package com.onion.android.app.plex.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.onion.android.R;
import com.onion.android.app.plex.manager.SettingsManager;
import com.onion.android.app.plex.vm.SettingsViewModel;
import com.onion.android.app.utils.Tools;
import com.onion.android.databinding.PlexActivitySplashBinding;
import com.onion.android.java.base.PlexBaseActivity;

import javax.inject.Inject;

import info.guardianproject.netcipher.client.StrongBuilder;
import info.guardianproject.netcipher.client.StrongOkHttpClientBuilder;
import okhttp3.OkHttpClient;

import static com.onion.android.app.constants.PlexConstants.SERVER_BASE_URL;

public class SplashActivity extends PlexBaseActivity<PlexActivitySplashBinding> implements StrongBuilder.Callback<OkHttpClient> {

    @Inject
    SettingsViewModel settingsViewModel;
    @Inject
    SettingsManager settingsManager;

    /**
     * 初始化洋葱代理，使用的洋葱路由概念(提供匿名性和对交通监控的抵抗)
     */
    private void initNetcipher(){
        // Netcipher-step-2-Creating a Activity Builder
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
    public void initView() {
        initNetcipher();
        Tools.hideSystemBar(this,true);
        Tools.loadHttpImg(getApplication(), mBinding.logoImageTop,SERVER_BASE_URL +"image/logo");
        settingsViewModel.getSettingsDetails();
        // 监听配置信息获取
        settingsViewModel.settingsMutableLiveData.observe(this, settings -> {
            settingsManager.saveSettings(settings);
            Tools.postDelayed(()->{ startActivity(new Intent(this,MainActivity.class)); },1900);
        });

    }

    @Override
    public int getBindingContent(@Nullable Bundle savedInstanceState) {
        return R.layout.plex_activity_splash;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Tools.clearHandler();
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
