package com.onion.android.app.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.onion.android.app.di.annotation.ViewModelKey;
import com.onion.android.app.di.common.MineViewModelFactory;
import com.onion.android.app.plex.vm.DetailVideModel;
import com.onion.android.app.plex.vm.GenresViewModel;
import com.onion.android.app.plex.vm.HomeViewModel;
import com.onion.android.app.plex.vm.SearchViewModel;
import com.onion.android.app.plex.vm.SettingsViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

// 为注射器提供工厂类
// @Module: 表示Dagger2可以将该类当作对象工厂.
@Module
public abstract class PlexViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel.class)
    abstract ViewModel bindSettingsViewModel(SettingsViewModel settingsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel bindHomeViewModel(HomeViewModel homeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(GenresViewModel.class)
    abstract ViewModel bindGenresViewModel(GenresViewModel homeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DetailVideModel.class)
    abstract ViewModel bindDetailVideModel(DetailVideModel detailVideModel);

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel.class)
    abstract ViewModel bindSearchViewModel(SearchViewModel searchViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(MineViewModelFactory factory);
}
