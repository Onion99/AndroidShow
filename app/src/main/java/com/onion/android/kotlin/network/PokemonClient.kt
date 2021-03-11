package com.onion99.android.kotlin.network

import com.onion99.android.kotlin.model.PokemonResponse
import com.onion99.android.kotlin.network.handler.ApiResponse
import javax.inject.Inject

class PokemonClient @Inject constructor(
    private val pokemonService:PokemonService
) {
    suspend fun fetchPokemonList(
        page:Int
    ):ApiResponse<PokemonResponse> = pokemonService.fetchPokemonList(
        limit = PAGING_SIZE,
        offset = page * PAGING_SIZE
    )

    companion object {
        private const val PAGING_SIZE = 20
    }
}