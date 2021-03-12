package com.onion.android.kotlin.simple.whatif

/**
 * @JvmSynthetic 用于标记Kotlin 特有API，使得其无法从java代码中访问
 */
@JvmSynthetic
@WhatIfInlineOnly
public inline fun <T> T?.whatIfNotNull(
    whatIf: (T) ->Unit
): T?{
    return this.whatIfNotNull(
        whatIf = whatIf,
        whatIfNot = {}
    )
}

@JvmSynthetic
@WhatIfInlineOnly
public inline fun <T> T?.whatIfNotNull(
    whatIf: (T) -> Unit,
    whatIfNot: () -> Unit
): T?{
    if(this != null){
        whatIf(this)
        return this
    }
    whatIfNot()
    return this
}
// 任意类型转换非空判断
@JvmSynthetic
@WhatIfInlineOnly
public inline fun <reified R> Any?.whatIfNotNullAs(
    whatIf: (R) -> Unit,
    whatIfNot: () -> Unit
): Any?{
    if(this != null && this is R){
        whatIf(this as R)
        return this
    }
    whatIfNot()
    return this
}

@JvmSynthetic
@WhatIfInlineOnly
public inline fun <reified R> Any?.whatIfNotNullAs(
    whatIf: (R) -> Unit
):Any? {
    return whatIfNotNullAs<R>(
        whatIf = whatIf,
        whatIfNot = {}
    )
}