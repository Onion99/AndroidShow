package com.onion.android.app.base;

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

public abstract class PlexBaseFragment<T extends ViewDataBinding> extends Fragment {

    public T mBinding;
    public abstract void initView();
    public abstract int getBindingContent(@Nullable Bundle savedInstanceState);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, getBindingContent(savedInstanceState), container,false);
        mBinding.setLifecycleOwner(this);
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

    private ViewModelProvider.Factory getAppFactory(Activity activity){
        Objects.requireNonNull(activity.getApplication(), "Application is Null");
        return ViewModelProvider.AndroidViewModelFactory.getInstance(activity.getApplication());
    }
}

