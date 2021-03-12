package com.onion.android.kotlin.sandwich.coroutines

import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal abstract class CallDelegate<TIn,TOut> (
    protected val proxy: Call<TIn>
) : Call<TOut>{
    override fun execute(): Response<TOut> = throw NotImplementedError()
    // final override fun 表明禁止重写
    final override fun enqueue(callback: Callback<TOut>) = enqueueImpl(callback)
    final override fun clone(): Call<TOut> = cloneImpl()

    override fun request(): Request = proxy.request()
    override fun cancel() = proxy.cancel()
    override fun isCanceled() = proxy.isExecuted
    override fun isExecuted() = proxy.isCanceled
    // 要实现的
    // enqueueImpl 请求
    abstract fun enqueueImpl(callback: Callback<TOut>)
    abstract fun cloneImpl():Call<TOut>
}