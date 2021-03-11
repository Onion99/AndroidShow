package com.onion99.android.kotlin.sandwich.mapper

import com.onion99.android.kotlin.model.PokemonErrorResponse
import com.onion99.android.kotlin.network.handler.ApiResponse
import com.onion99.android.kotlin.sandwich.message

object ErrorResponseMapper: ApiErrorModelMapper<PokemonErrorResponse> {
    override fun map(apiErrorResponse: ApiResponse.Failure.Error<*>): PokemonErrorResponse {
        return PokemonErrorResponse(apiErrorResponse.statusCode.code,apiErrorResponse.message())
    }

}