package com.onion.android.kotlin.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

/**
 * 自定义Okhttp拦截器
 */
class HttpRequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()
        val request = originRequest.newBuilder().url(originRequest.url).build()
        // 输出请求内容
        Timber.d(request.toString())
        return chain.proceed(request)
    }

}