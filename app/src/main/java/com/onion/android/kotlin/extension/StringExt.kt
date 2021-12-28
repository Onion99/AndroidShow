package com.onion.android.kotlin.extension

fun String?.isNotNull(): Boolean = !isNullOrEmpty()