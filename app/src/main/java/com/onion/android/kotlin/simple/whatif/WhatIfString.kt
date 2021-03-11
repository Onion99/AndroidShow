@file:JvmName("WhatIfString")
@file:JvmMultifileClass

package com.onion99.android.kotlin.simple.whatif

@JvmSynthetic
@WhatIfInlineOnly
public inline fun String?.whatIfNotNullOrEmpty(
    whatIf: (String) -> Unit,
    whatIfNot: () -> Unit
):String? = apply {
    if(!this.isNullOrEmpty()){
        whatIf(this)
    }else{
        whatIfNot()
    }
}

@JvmSynthetic
@WhatIfInlineOnly
public inline fun String?.whatIfNotNullOrEmpty(
    whatIf: (String) -> Unit
):String? = apply {
    this.whatIfNotNullOrEmpty(
        whatIf = whatIf,
        whatIfNot =  {}
    )
}