package com.onion.android.kotlin.retrofit_coroutine_call_adapter_factory

import com.onion.android.kotlin.retrofit_coroutine_call_adapter_factory.common.ApiResponse
import com.onion.android.kotlin.retrofit_coroutine_call_adapter_factory.coroutines.CoroutinesResponseCallAdapter
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

// ------------------------------------------------------------------------
// 通过继承 CallAdapter.Factory() 实现自定义 retrofit 接口返回的Response响应体
// ------------------------------------------------------------------------
class CoroutinesResponseCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CoroutinesResponseCallAdapter? = when (getRawType(returnType)) {
        Call::class.java -> {
            // 从 callType 提取index处的泛型参数 , eg: Map<String, ? extends Runnable> 中 index 为 1  ,则返回Runnable
            val callType = getParameterUpperBound(0, returnType as ParameterizedType)
            // 从 callType 提取原始类型 , eg: List<? extends Runnable> 返回 List.class.
            when (getRawType(callType)) {
                ApiResponse::class.java -> {
                    // 获取 ApiResponse 的泛型参数 , 如 ApiResponse<String> 返回 String
                    val resultType = getParameterUpperBound(0, callType as ParameterizedType)
                    CoroutinesResponseCallAdapter(resultType)
                }
                    else -> null
                }
            }
            else -> null
        }
}