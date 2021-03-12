package com.onion.android.java.base;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;

public abstract class PlexBaseActivity<T extends ViewDataBinding,V extends ViewModel> extends AppCompatActivity {

    public T binding;

    public V viewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getBindingContent(savedInstanceState));
    }

    public abstract int getBindingContent(@Nullable Bundle savedInstanceState);
}
