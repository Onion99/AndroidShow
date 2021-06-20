package com.onion.android.app.di.module;

import com.onion.android.app.plex.ui.frg.AnimesFragment;
import com.onion.android.app.plex.ui.frg.DiscoverFragment;
import com.onion.android.app.plex.ui.frg.HomeFragment;
import com.onion.android.app.plex.ui.frg.LibraryFragment;
import com.onion.android.app.plex.ui.frg.MoviesFragment;
import com.onion.android.app.plex.ui.frg.SeriesFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * 又被对应依赖注入的Activity引用的Fragment，都要声明在下面
 */
@Module
public abstract class PlexFragmentBuilderModule {

    @ContributesAndroidInjector
    abstract HomeFragment contributeHomeFragment();

    @ContributesAndroidInjector
    abstract LibraryFragment contributeLibraryFragment();

    @ContributesAndroidInjector
    abstract MoviesFragment contributeMoviesFragment();

    @ContributesAndroidInjector
    abstract SeriesFragment contributeSeriesFragment();

    @ContributesAndroidInjector
    abstract AnimesFragment contributeAnimesFragment();

    @ContributesAndroidInjector
    abstract DiscoverFragment contributeDiscoverFragment();

}
