package com.onion.android.app.plex.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.onion.android.R;
import com.onion.android.app.base.PlexBaseActivity;
import com.onion.android.app.plex.manager.SettingsManager;
import com.onion.android.app.plex.vm.SettingsViewModel;
import com.onion.android.app.utils.GlideApp;
import com.onion.android.app.utils.Tools;
import com.onion.android.databinding.PlexActivitySplashBinding;

import javax.inject.Inject;

import info.guardianproject.netcipher.client.StrongBuilder;
import info.guardianproject.netcipher.client.StrongOkHttpClientBuilder;
import okhttp3.OkHttpClient;

import static com.onion.android.app.constants.PlexConstants.SERVER_BASE_URL;

public class SplashActivity extends PlexBaseActivity<PlexActivitySplashBinding> implements StrongBuilder.Callback<OkHttpClient> {

    SettingsViewModel settingsViewModel;

    @Inject
    SettingsManager settingsManager;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
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
    public void initViewModel() {
        settingsViewModel = new ViewModelProvider(this, viewModelFactory).get(SettingsViewModel.class);
        settingsViewModel.getSettingsDetails();
        // 监听配置信息获取
        settingsViewModel.settingsMutableLiveData.observe(this, settings -> {
            settingsManager.saveSettings(settings);
            Tools.postDelayed(() -> {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }, 1900);
        });
    }



    @Override
    public void initView() {
        initNetcipher();
        Tools.hideSystemBar(this,true);
        Tools.loadHttpImg(getApplication(), mBinding.logoImageTop,SERVER_BASE_URL +"image/logo");
        GlideApp.with(this).asBitmap()
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        mBinding.loader.setVisibility(View.GONE);
                        return false;
                    }
                })
                .load(settingsManager.getSettings().getSplashImage())
                .into(mBinding.splashImage);
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
        Toast.makeText(this, "成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionException(Exception e) {
        Toast.makeText(this, "除非您关闭嗅探器，否则该应用程序将无法运行", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTimeout() {}

    @Override
    public void onInvalid() { }
}
