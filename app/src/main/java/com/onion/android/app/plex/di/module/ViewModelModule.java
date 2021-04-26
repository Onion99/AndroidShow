package com.onion.android.app.plex.di.module;

import androidx.lifecycle.ViewModelProvider;


import com.onion.android.app.plex.vm.PlexViewModelFactory;

import dagger.Binds;
import dagger.Module;

// 为注射器提供工厂类
// @Module: 表示Dagger2可以将该类当作对象工厂.
@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(PlexViewModelFactory factory);
}
