package com.onion.android.kotlin.extension

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

fun <T : ViewDataBinding> ViewGroup.getBinding(
    @LayoutRes layout: Int,
    attachParent: Boolean = false
): T {
    return DataBindingUtil.inflate(LayoutInflater.from(context), layout, this, attachParent)
}