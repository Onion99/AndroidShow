package com.onion.android.kotlin.retrofit_coroutine_call_adapter_factory.coroutines

import com.onion.android.kotlin.retrofit_coroutine_call_adapter_factory.common.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

// ------------------------------------------------------------------------
// 自定义Retrofit CallAdapter的生成
// ------------------------------------------------------------------------
class CoroutinesResponseCallAdapter constructor(private val resultType: Type) :
    CallAdapter<Type, Call<ApiResponse<Type>>> {

    // ------------------------------------------------------------------------
    // 返回Adapter将 HTTP 响应正文 转换为 Java 对象时使用的映射类型
    // ------------------------------------------------------------------------
    override fun responseType(): Type = resultType

    // ------------------------------------------------------------------------
    // 自定义实现 - 返回委托call的ApiResponse<Type>实例。
    // ------------------------------------------------------------------------
    override fun adapt(call: Call<Type>): Call<ApiResponse<Type>> = ApiResponseCallDelegate(call)
}