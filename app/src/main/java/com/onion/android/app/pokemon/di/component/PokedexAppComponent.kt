package com.onion.android.app.pokemon.di.component

import android.app.Application
import com.onion.android.App
import com.onion.android.app.pokemon.di.module.PokedexActivityModule
import com.onion.android.app.pokemon.di.module.PokedexAppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, PokedexAppModule::class, PokedexActivityModule::class])
interface PokedexAppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): PokedexAppComponent
    }

    fun inject(app: App)
}