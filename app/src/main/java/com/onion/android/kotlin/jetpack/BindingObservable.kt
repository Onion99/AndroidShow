package com.onion.android.kotlin.jetpack

import androidx.databinding.Observable
import kotlin.reflect.KProperty

interface BindingObservable : Observable {
    // ------------------------------------------------------------------------
    // 通知binding属性改变
    // ------------------------------------------------------------------------
    fun notifyPropertyChanged(property: KProperty<*>)
    fun notifyPropertyChanged(bindingId: Int)

    // ------------------------------------------------------------------------
    // 清除binding属性
    // ------------------------------------------------------------------------
    fun clearAllProperties()
}