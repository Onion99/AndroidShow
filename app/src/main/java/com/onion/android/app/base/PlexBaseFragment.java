package com.onion.android.app.base;

import static android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.appbar.AppBarLayout;
import com.onion.android.app.di.injector.Injectable;

import java.util.Optional;

import javax.inject.Inject;

public abstract class PlexBaseFragment<T extends ViewDataBinding> extends Fragment implements Injectable {

    public T mBinding;
    public ViewModelProvider mViewModelProvider;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, getBindingContent(savedInstanceState), container, false);
        mBinding.setLifecycleOwner(this);
        return mBinding.getRoot();
    }

    public abstract int getBindingContent(@Nullable Bundle savedInstanceState);

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // initViewModel 必须放在Fragment/Activity UI 初始化后面，否则将拿不到对应ViewModelProvider(this,viewModelFactory),从而报错
        mViewModelProvider = new ViewModelProvider(this, viewModelFactory);
        initViewModel();
        initView();
    }

    public abstract void initViewModel();
    // 创建ViewModelProvider ，这将通过给定的Factory创造ViewModels，并将其保持在给定的ViewModelStoreOwner
    // 创建的ViewModel与给定的范围相关联，只要该范围处于活动状态（例如，如果它是一项活动，直到完成或终止进程），它将一直保留
    // viewModel = new ViewModelProvider(this,viewModelFactory).get(HomeViewModel .class);

    public abstract void initView();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Optional.ofNullable(mBinding).ifPresent(ViewDataBinding::unbind);
    }

    // 状态栏 - 透明
    public void setSystemBarTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    // ------------------------------------------------------------------------
    // 加载标题栏
    // ------------------------------------------------------------------------
    public void loadToolbar(Toolbar toolbar, AppBarLayout appBarLayout) {
        AppCompatActivity appCompatActivity = (AppCompatActivity) requireActivity();
        appCompatActivity.setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        toolbar.setTitle(null);
        if (appBarLayout != null) {
            appBarLayout.bringToFront();
        }
        if (appCompatActivity.getSupportActionBar() != null) {
            appCompatActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }
}

