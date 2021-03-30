package com.onion.android.app.plex.di.module;

import com.onion.android.app.plex.ui.frg.HomeFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract HomeFragment contributeHomeFragment();
}
