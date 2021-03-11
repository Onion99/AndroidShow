@file:JvmName("WhatIfCollections")
@file:JvmMultifileClass

package com.onion99.android.kotlin.simple.whatif


@JvmSynthetic
@WhatIfInlineOnly
public inline fun <T> List<T>?.whatIfNotNullOrEmpty(
    whatIf : (List<T>) -> Unit,
    whatIfNot: () -> Unit
):List<T>? = apply{
    if(!this.isNullOrEmpty()){
        whatIf(this)
    }else{
        whatIfNot()
    }
}

@JvmSynthetic
@WhatIfInlineOnly
public inline fun <T> List<T>?.whatIfNotNullOrEmpty(
    whatIf: (List<T>) -> Unit
):List<T>? = apply {
    this.whatIfNotNullOrEmpty(whatIf = whatIf,whatIfNot = {})
}