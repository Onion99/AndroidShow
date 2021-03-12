package com.onion.android.java.base;

import android.app.Activity;
import android.app.Application;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.onion.android.java.base.strictdatabinding.DataBindingActivity;
import com.onion.android.java.utils.android.AdaptScreenUtils;
import com.onion.android.java.utils.android.BarUtils;
import com.onion.android.java.utils.android.ScreenUtils;

public abstract class BaseActivity extends DataBindingActivity {
    /**
     * 一般来讲，我们存储的东西一般分两个级别
     * Activity级
     * Application级
     */
    private ViewModelProvider mActivityProvider;
    private ViewModelProvider mApplicationProvide;

    protected <T extends ViewModel> T getActivityScopeViewModel(@NonNull Class<T> modelClass){
        if(mActivityProvider == null){
            mActivityProvider = new ViewModelProvider(this);
        }
        return mActivityProvider.get(modelClass);
    }

    protected <T extends ViewModel> T getApplicationScopeViewModel(@NonNull Class<T> modelClass){
        if(mApplicationProvide == null){
            mApplicationProvide = new ViewModelProvider(this);
        }
        return mApplicationProvide.get(modelClass);
    }

    private ViewModelProvider.Factory getAppFactory(Activity activity) {
        Application application = checkApplication(activity);
        return ViewModelProvider.AndroidViewModelFactory.getInstance(application);
    }

    private Application checkApplication(Activity activity) {
        Application application = activity.getApplication();
        if (application == null) {
            throw new IllegalStateException("Your activity/fragment is not yet attached to "
                    + "Application. You can't request ViewModel before onCreate call.");
        }
        return application;
    }


    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        BarUtils.setStatusBarColor(this, Color.TRANSPARENT);
        BarUtils.setStatusBarLightMode(this, true);

        super.onCreate(savedInstanceState);
    }

    /* 适用于屏幕适配 */
    @Override
    public Resources getResources() {
        if (ScreenUtils.isPortrait()) {
            return AdaptScreenUtils.adaptWidth(super.getResources(), 360);
        } else {
            return AdaptScreenUtils.adaptHeight(super.getResources(), 640);
        }
    }
}
