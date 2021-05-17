package com.onion.android.app.plex.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.onion.android.app.plex.di.ViewModelKey;
import com.onion.android.app.plex.vm.HomeViewModel;
import com.onion.android.app.plex.vm.PlexViewModelFactory;
import com.onion.android.app.plex.vm.SettingsViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

// 为注射器提供工厂类
// @Module: 表示Dagger2可以将该类当作对象工厂.
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel.class)
    abstract ViewModel bindSettingsViewModel(SettingsViewModel settingsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel bindHomeViewModel(HomeViewModel homeViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(PlexViewModelFactory factory);
}
