package com.onion.android.kotlin.sandwich.coroutines

import com.onion.android.kotlin.network.handler.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/*
* CoroutinesResponseCallAdapterFactory是用于创建ApiResponse的协程调用适配器工厂。
* 将此类添加到Retrofit允许您从服务方法返回ApiResponse
* */
class CoroutinesResponseCallAdapterFactory : CallAdapter.Factory(){
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>?  =
        when(getRawType(returnType)){
            Call::class.java -> {
                val callType = getParameterUpperBound(0,returnType as ParameterizedType)
                when(getRawType(callType)){
                    ApiResponse::class.java -> {
                        val resultType = getParameterUpperBound(0,callType as ParameterizedType)
                        CoroutinesResponseCallAdapter(resultType)
                    }
                    else -> null
                }
            }
            else -> null
        }

}