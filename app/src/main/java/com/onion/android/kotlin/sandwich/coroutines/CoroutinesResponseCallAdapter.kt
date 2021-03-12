package com.onion.android.kotlin.sandwich.coroutines

import com.onion.android.kotlin.network.handler.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class CoroutinesResponseCallAdapter constructor(
    private val resultType: Type
) : CallAdapter<Type, Call<ApiResponse<Type>>>{
    override fun responseType(): Type {
        return resultType;
    }

    override fun adapt(call: Call<Type>): Call<ApiResponse<Type>> {
        return ApiResponseCallDelegate(call)
    }

}