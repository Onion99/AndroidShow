package com.onion99.android.kotlin.sandwich.mapper

import com.onion99.android.kotlin.network.handler.ApiResponse

/*
* 简单来说就避免了kotlin 定义 callback，再用 object 去声明的麻烦
fun interface Action {
    fun run()
}
fun runAction(a: Action) = a.run()

runAction {
    println("Hello")
}
*
* */
fun interface ApiErrorModelMapper<V> {
    fun map(apiErrorResponse: ApiResponse.Failure.Error<*>): V
}