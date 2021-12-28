package com.onion.android.app.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.google.android.exoplayer2.util.Util;

import java.util.Optional;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

public abstract class PlexBaseActivity<T extends ViewDataBinding> extends AppCompatActivity implements HasAndroidInjector {

    public T mBinding;

    // ------------------------------------------------------------------------
    // DispatchingAndroidInjector 通过 AndroidInjector.Factory 创建AndroidInjector，并将您的activity传递至XXXXActivitySubcomponentImpl中。
    // ------------------------------------------------------------------------
    @Inject
    DispatchingAndroidInjector<Object> dispatchingAndroidInjector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Dagger-依赖注入 ,Activity 的 注入的方式有两种 ， 记住实现一种就可以 ， 这里两种都做纯碎就是为了学习
        // 1 - implements HasAndroidInjector
        // 2 - AndroidInjection.inject(this);
        // 最后都在Activity Module 实现实例返回
        // AndroidInjection.inject(this);
        mBinding = DataBindingUtil.setContentView(this, getBindingContent(savedInstanceState));
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Optional.ofNullable(mBinding).ifPresent(ViewDataBinding::unbind);
    }

    public abstract void initView();

    public abstract int getBindingContent(@Nullable Bundle savedInstanceState);


    @Override
    public AndroidInjector<Object> androidInjector() {
        return dispatchingAndroidInjector;
    }


    // ------------------------------------------------------------------------
    // 隐藏系统状态栏
    // ------------------------------------------------------------------------
    public void hideSystemBar(@NonNull final Activity activity, final boolean immediate) {
        hideSystemBar(activity, immediate, 2000);
    }

    public void hideSystemBar(@NonNull final Activity activity, final boolean immediate,
                              final int delayMs) {
        activity.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        View decorView = activity.getWindow().getDecorView();
        // 设置沉浸式FLAG
        // 设置状态栏覆盖到视图
        // 当状态栏显示或隐藏的时候不要调整
        int uiState = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // 隐藏导航栏
                | View.SYSTEM_UI_FLAG_FULLSCREEN; // 隐藏状态
        if (Util.SDK_INT > 18) {
            // |= 意指 uiState = uiState | View.SYSTEM_UI_FLAG_LAY
            uiState |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_IMMERSIVE;
        } else {
            final Handler handler = new Handler(Looper.getMainLooper());
            decorView.setOnSystemUiVisibilityChangeListener(
                    visibility -> {
                        if (visibility == View.VISIBLE) {
                            Runnable runnable = () -> hideSystemBar(activity, false);
                            if (immediate) {
                                handler.post(runnable);
                            } else {
                                handler.postDelayed(runnable, delayMs);
                            }
                        }
                    }
            );
        }
        decorView.setSystemUiVisibility(uiState);
    }
}
