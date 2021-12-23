package com.onion.android.kotlin.retrofit_coroutine_call_adapter_factory.coroutines

import com.onion.android.kotlin.retrofit_coroutine_call_adapter_factory.common.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// ------------------------------------------------------------------------
// ApiResponseCallDelegate 是一个委托调用代理，用于处理和转换普通的泛型类型 T 作为 ApiResponse 包装来自网络响应的 T 数据
// ------------------------------------------------------------------------
internal class ApiResponseCallDelegate<T>(proxy: Call<T>) : CallDelegate<T, ApiResponse<T>>(proxy) {


    // ------------------------------------------------------------------------
    // 处理Http响应正文
    // ------------------------------------------------------------------------
    override fun enqueueImpl(callback: Callback<ApiResponse<T>>) = proxy.enqueue(
        object : Callback<T> {
            // 成功响应
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val apiResponse = ApiResponse.of { response }
                callback.onResponse(this@ApiResponseCallDelegate, Response.success(apiResponse))
            }

            // 失败响应
            override fun onFailure(call: Call<T>, t: Throwable) {
                callback.onResponse(
                    this@ApiResponseCallDelegate,
                    Response.success(ApiResponse.error(t))
                )
            }
        }
    )

    // ------------------------------------------------------------------------
    // Call(网路请求)复用处理
    // ------------------------------------------------------------------------
    override fun cloneImpl(): Call<ApiResponse<T>> = ApiResponseCallDelegate(proxy.clone())
}