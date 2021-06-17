package com.onion.android.app.pokemon.network

import com.onion.android.app.pokemon.model.PokemonResponse
import com.onion.android.app.pokemon.network.handler.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonService {
    @GET("pokemon")
    suspend fun fetchPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): ApiResponse<PokemonResponse>
}