package com.onion.android.kotlin.retrofit_coroutine_call_adapter_factory.mapper

import com.onion.android.app.pokemon.model.PokemonErrorResponse
import com.onion.android.kotlin.retrofit_coroutine_call_adapter_factory.common.ApiResponse
import com.onion.android.kotlin.retrofit_coroutine_call_adapter_factory.common.message

object ErrorResponseMapper: ApiErrorModelMapper<PokemonErrorResponse> {
    override fun map(apiErrorResponse: ApiResponse.Failure.Error<*>): PokemonErrorResponse {
        return PokemonErrorResponse(apiErrorResponse.statusCode.code,apiErrorResponse.message())
    }

}