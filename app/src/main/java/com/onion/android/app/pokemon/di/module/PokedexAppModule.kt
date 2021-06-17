package com.onion.android.app.pokemon.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.onion.android.app.pokemon.network.PokemonClient
import com.onion.android.app.pokemon.network.PokemonService
import com.onion.android.app.pokemon.network.interceptor.HttpRequestInterceptor
import com.onion.android.app.pokemon.persistence.AppDataBase
import com.onion.android.app.pokemon.persistence.PokemonDao
import com.onion.android.kotlin.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
open class PokedexAppModule {

    // Application 注入
    @Singleton
    @Provides
    open fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(HttpRequestInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory())
            .build()

    }

    @Provides
    @Singleton
    fun providePokemonService(retrofit: Retrofit): PokemonService {
        return retrofit.create(PokemonService::class.java)
    }

    @Provides
    @Singleton
    fun providePokemonClient(pokemonService: PokemonService): PokemonClient {
        return PokemonClient(pokemonService)
    }

    @Provides
    @Singleton
    fun provideAppDataBase(
        application: Application
    ): AppDataBase {
        return Room.databaseBuilder(application, AppDataBase::class.java, "Pokemon.db")
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providePokemonDao(appDataBase: AppDataBase): PokemonDao {
        return appDataBase.pokemonDao()
    }
}