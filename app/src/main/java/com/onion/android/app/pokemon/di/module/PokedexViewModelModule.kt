package com.onion.android.app.pokemon.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onion.android.app.pokemon.vm.PokedexMainViewModel
import com.onion.android.app.pokemon.vm.PokedexViewModelFactory
import com.onion.android.app.pokemon.vm.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class PokedexViewModelModule {

    ///////////////////////////////////////////////////////////////////////////
    // @Binds 和 @Provider的作用相差不大，区别在于@Provider需要写明具体的实现，而@Binds只是告诉Dagger2谁是谁实现的
    // @IntoMap则可以让Dagger2将多个元素依赖注入到Map之中。
    ///////////////////////////////////////////////////////////////////////////
    @Binds
    @IntoMap
    @ViewModelKey(PokedexMainViewModel::class)
    abstract fun bindPokedexMainViewModel(pokedexMainViewModel: PokedexMainViewModel): ViewModel

    // 提供ViewModel的工厂类
    @Binds
    abstract fun bindViewModelFactory(factory: PokedexViewModelFactory): ViewModelProvider.Factory
}