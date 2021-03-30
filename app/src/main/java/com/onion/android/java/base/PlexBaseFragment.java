package com.onion.android.java.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.onion.android.App;

import java.util.Objects;
import java.util.Optional;

public abstract class PlexBaseFragment<T extends ViewDataBinding,V extends ViewModel> extends Fragment {

    public T mBinding;
    public V mViewModel;
    // ViewModel 提供者 根据层级区分
    private ViewModelProvider mActivityProvider;
    private ViewModelProvider mApplicationProvider;

    public abstract void initView();
    public abstract int getBindingContent(@Nullable Bundle savedInstanceState);
    public abstract Class<V> getViewModelClass();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, getBindingContent(savedInstanceState), container,false);
        mBinding.setLifecycleOwner(this);
//        mViewModel = getActivityScopeViewModel(getViewModelClass());
        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Optional.ofNullable(mBinding).ifPresent(ViewDataBinding::unbind);
    }

    protected V getActivityScopeViewModel(@NonNull Class<V> modelClass){
        if(mActivityProvider == null){
            mActivityProvider = new ViewModelProvider(this);
        }
        return mActivityProvider.get(modelClass);
    }

    protected V getApplicationScopeViewModel(@NonNull Class<V> modelClass){
        if(mApplicationProvider == null){
            mApplicationProvider = new ViewModelProvider((App)requireActivity().getApplication(),
                    getAppFactory(requireActivity()));
        }
        return mApplicationProvider.get(modelClass);
    }

    private ViewModelProvider.Factory getAppFactory(Activity activity){
        Objects.requireNonNull(activity.getApplication(), "Application is Null");
        return ViewModelProvider.AndroidViewModelFactory.getInstance(activity.getApplication());
    }
}

