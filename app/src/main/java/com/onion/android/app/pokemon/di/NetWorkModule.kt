package com.onion.android.app.pokemon.di

import com.onion.android.app.pokemon.network.PokemonClient
import com.onion.android.app.pokemon.network.PokemonService
import com.onion.android.app.pokemon.network.interceptor.HttpRequestInterceptor
import com.onion.android.kotlin.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/*
* 父级注释
* @Module:表明这个类是Module
* @InstallIn：用@Module同时必须用@InstallIn，目的是告知模块用在哪个Android类中。
*   1. SingletonComponent d
* 子级注释
* @Provides :下面的方法能提供我们需要的XX类。
* @Singleton:表示单例
* */
@Module
@InstallIn(SingletonComponent::class)
object NetWorkModule {


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient{
        return OkHttpClient
            .Builder()
            .addInterceptor(HttpRequestInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit{
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
}