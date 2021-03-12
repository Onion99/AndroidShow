/**
 *  @file:JvmName Java调用Kotlin代码
 *  @file:JvmName("WhatIfArray") 在不创建一个Kotlin的class的情况下，实现用 TestA.testName()
 *  @file:JvmMultifileClass 注解可以解决类名相同的问题
 *  TestA.kt 和 TestC.kt都使用这个注解之后，在文件编译后，会将两个文件的中的方法强制合并到一个class类中，这就是这个注解的原理
 */

@file:JvmName("WhatIfArray")
@file:JvmMultifileClass
package com.onion.android.kotlin.simple.whatif

@JvmSynthetic
@WhatIfInlineOnly
public inline fun <T> Array<out T>?.whatIfNotNullOrEmpty(
    whatIf: (Array<out T>) -> Unit
): Array<out T>? = apply {
    this.whatIfNotNullOrEmpty(whatIf = whatIf,whatIfNot = {})
}

@JvmSynthetic
@WhatIfInlineOnly
public inline fun <T> Array<out T>?.whatIfNotNullOrEmpty(
    whatIf: (Array<out T>) -> Unit,
    whatIfNot: () -> Unit,
):Array<out T>? = apply {
    if(!this.isNullOrEmpty()){
        whatIf(this)
    }else{
        whatIfNot()
    }
}