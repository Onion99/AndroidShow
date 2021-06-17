package com.onion.android.app.pokemon.di.module

import androidx.lifecycle.ViewModel
import com.onion.android.app.plex.di.ViewModelKey
import com.onion.android.app.pokemon.vm.PokedexMainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class PokedexViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(PokedexMainViewModel::class)
    abstract fun bindPokedexMainViewModel(pokedexMainViewModel: PokedexMainViewModel): ViewModel

}