package com.onion.android.kotlin.retrofit_coroutine_call_adapter_factory.coroutines

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// ------------------------------------------------------------------------
// CallDelegate 是一个委托Call代理，用于在两种不同类型的Call请求之间处理和转换一种到另一种泛型类型
// TIn - 输入泛型, Tout - 输出泛型
// ------------------------------------------------------------------------
internal abstract class CallDelegate<TIn, TOut>(protected val proxy: Call<TIn>) : Call<TOut> {
    // 用到时再实现
    override fun execute(): Response<TOut> = throw NotImplementedError()

    // ------------------------------------------------------------------------
    // 转换请求响应处理, final override fun 表明禁止重写
    // ------------------------------------------------------------------------
    final override fun enqueue(callback: Callback<TOut>) = enqueueImpl(callback)

    // ------------------------------------------------------------------------
    // 为这个Call创建一个新的、相同的Call，即使这个Call已经存在，它也可以加入队列或执行
    // ------------------------------------------------------------------------
    final override fun clone(): Call<TOut> = cloneImpl()

    // ------------------------------------------------------------------------
    // 超时处理
    // ------------------------------------------------------------------------
    final override fun timeout(): Timeout = proxy.timeout()

    override fun request(): Request = proxy.request()
    override fun cancel() = proxy.cancel()
    override fun isCanceled() = proxy.isExecuted
    override fun isExecuted() = proxy.isCanceled

    // ------------------------------------------------------------------------
    // 网路请求处理
    // ------------------------------------------------------------------------
    abstract fun enqueueImpl(callback: Callback<TOut>)

    // ------------------------------------------------------------------------
    // Call复用处理
    // ------------------------------------------------------------------------
    abstract fun cloneImpl(): Call<TOut>
}