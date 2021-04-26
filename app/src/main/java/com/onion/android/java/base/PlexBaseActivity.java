package com.onion.android.java.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import java.util.Optional;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

public abstract class PlexBaseActivity<T extends ViewDataBinding> extends AppCompatActivity implements HasAndroidInjector {

    public T mBinding;

    // DispatchingAndroidInjector 通过 AndroidInjector.Factory 创建AndroidInjector，并将您的activity传递至XXXXActivitySubcomponentImpl中。
    @Inject
    DispatchingAndroidInjector<Object> dispatchingAndroidInjector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Dagger-依赖注入 ,Activity 的 注入的方式有两种 ， 记住实现一种就可以 ， 这里两种都做纯碎就是为了学习
        // 1 - implements HasAndroidInjector
        // 2 - AndroidInjection.inject(this);
        // 最后都在Activity Module 实现实例返回
        AndroidInjection.inject(this);
        // DataBinding 绑定
        mBinding = DataBindingUtil.setContentView(this, getBindingContent(savedInstanceState));
        initViewModel();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Optional.ofNullable(mBinding).ifPresent(ViewDataBinding::unbind);
    }

    public abstract void initViewModel();

    public abstract void initView();

    public abstract int getBindingContent(@Nullable Bundle savedInstanceState);


    @Override
    public AndroidInjector<Object> androidInjector() {
        return dispatchingAndroidInjector;
    }
}
