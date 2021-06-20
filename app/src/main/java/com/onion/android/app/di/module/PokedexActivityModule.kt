package com.onion.android.app.di.module

import com.onion.android.app.pokemon.ui.PokedexMainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PokedexActivityModule {

    @ContributesAndroidInjector
    abstract fun contributePokedexMainActivity(): PokedexMainActivity

}