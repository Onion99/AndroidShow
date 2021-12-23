package com.onion.android.app.pokemon.network

import com.onion.android.app.pokemon.model.PokemonResponse
import com.onion.android.kotlin.retrofit_coroutine_call_adapter_factory.common.ApiResponse
import javax.inject.Inject

class PokemonClient @Inject constructor(
    private val pokemonService: PokemonService
) {
    suspend fun fetchPokemonList(
        page: Int
    ): ApiResponse<PokemonResponse> = pokemonService.fetchPokemonList(
        limit = PAGING_SIZE,
        offset = page * PAGING_SIZE
    )

    companion object {
        private const val PAGING_SIZE = 20
    }
}