package com.onion99.android.kotlin.sandwich

import com.onion99.android.kotlin.network.handler.ApiResponse
import com.onion99.android.kotlin.sandwich.coroutines.SuspensionFunction
import com.onion99.android.kotlin.sandwich.mapper.ApiErrorModelMapper


/**
 * crossinline : disable non-local return 不允许本地return
 * inline : 编译器会把函数体替换在函数被调用的地方,主要是解决了函数调用时的开销, 调用栈的保存, 匿名对象的建立等
 * Java在语言层面暂时不支持inline, JVM会做一些相关的优化.
 * noinline : 如果函数有多个函数参数, 有些我不希望被inline, 那就可以用noinline来修饰
 * reified :  有时候我们需要类型作为参数, 但是又觉得函数声明个clazz: Class<T>参数, 传入实参MyClass::class.java这样比较难看
 * 想直接用类名传入，就用reified
 */

// 定义ApiResponse 网络请求成功fun
@JvmSynthetic
@SuspensionFunction
suspend inline fun <T> ApiResponse<T>.suspendOnSuccess(
   crossinline onResult: suspend ApiResponse.Success<T>.() -> Unit
): ApiResponse<T>{
    // this -> ApiResponse<T>
    if(this is ApiResponse.Success){
        onResult(this)
    }
    return this
}

// 定义ApiResponse 网络请求失败fun
@JvmSynthetic
inline fun <T> ApiResponse<T>.onError(
    crossinline onResult: ApiResponse.Failure.Error<T>.() -> Unit
): ApiResponse<T>{
    if(this is ApiResponse.Failure.Error){
        onResult(this)
    }
    return this
}

@JvmSynthetic
@SuspensionFunction
suspend inline fun <T> ApiResponse<T>.onException(
   crossinline onResult: ApiResponse.Failure.Exception<T>.() -> Unit
):ApiResponse<T>{
    if(this is ApiResponse.Failure.Exception){
        onResult(this)
    }
    return this
}

// 定义ApiResponse 网络请求失败匹配fun
fun <T,V> ApiResponse.Failure.Error<T>.map(mapper: ApiErrorModelMapper<V>):V{
    return mapper.map(this)
}

/**
 * 理解 ： T.() ->Unit , () ->Unit ,(T) -> Unit
 * 共同点:
 * 1. 三者的返回值相同，均为Unit，即没有返回值
 * 区别：
 * T.() -> Unit 的函数体中可以直接使用T代表的对象，即用this代表对象
 * (T) -> Unit 将T表示的对象作为实参通过函数参数传递进来，供函数体使用
 *  () -> Unit 与T表示的对象没有直接联系，只能通过外部T实例的变量来访问对象
 */
@JvmSynthetic
inline fun <T,V> ApiResponse.Failure.Error<T>.map(
    mapper: ApiErrorModelMapper<V>,
    crossinline onResult:V.() -> Unit
){
    onResult(mapper.map(this))
}

// 定义日志输出
fun <T> ApiResponse.Failure.Error<T>.message():String = toString()
fun <T> ApiResponse.Failure.Exception<T>.message():String = toString()
