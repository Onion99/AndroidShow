package com.onion.android.java.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import java.util.Optional;

public abstract class PlexBaseFragment<T extends ViewDataBinding> extends Fragment {
    public T mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, getBindingContent(savedInstanceState), container,false);
        initView();
        return mBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Optional.ofNullable(mBinding).ifPresent(ViewDataBinding::unbind);
    }

    public abstract void initView();

    public abstract int getBindingContent(@Nullable Bundle savedInstanceState);
}
