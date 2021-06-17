package com.onion.android.kotlin.sandwich.coroutines

import com.onion.android.app.pokemon.network.handler.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

/*
* CoroutinesResponseCallAdapter是用于从服务方法创建ApiResponse的协程调用适配器。
* 异步请求API网络调用并返回ApiResponse
* */
class CoroutineResponseCallAdapter constructor(
    private val resultType: Type
) : CallAdapter<Type,Call<ApiResponse<Type>>>{

    override fun responseType(): Type {
        return resultType
    }

    override fun adapt(call: Call<Type>): Call<ApiResponse<Type>> {
        return ApiResponseCallDelegate(call)
    }
}