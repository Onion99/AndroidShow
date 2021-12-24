package com.onion.android.kotlin.jetpack

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel
import kotlin.reflect.KProperty

abstract class BindingViewModel : ViewModel(), BindingObservable {

    // 监听锁
    private val lock = Any()

    // binding属性注册(监听)表
    private val propertyChangeRegistry = PropertyChangeRegistry()

    // ------------------------------------------------------------------------
    // 添加监听的Binding变量
    // ------------------------------------------------------------------------
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        synchronized(lock) {
            propertyChangeRegistry.add(callback)
        }
    }

    // ------------------------------------------------------------------------
    // 移除监听的Binding变量
    // ------------------------------------------------------------------------
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        synchronized(lock) {
            propertyChangeRegistry.remove(callback)
        }
    }

    // ------------------------------------------------------------------------
    // 通知对应id的Binding变量改变
    // ------------------------------------------------------------------------
    override fun notifyPropertyChanged(property: KProperty<*>) {
        synchronized(lock) {
            propertyChangeRegistry.notifyCallbacks(this, property.bindingId, null)
        }
    }

    override fun notifyPropertyChanged(bindingId: Int) {
        synchronized(lock) {
            propertyChangeRegistry.notifyCallbacks(this, bindingId, null)
        }
    }

    // ------------------------------------------------------------------------
    // 移除所有监听的binding变量
    // ------------------------------------------------------------------------
    override fun clearAllProperties() {
        synchronized(lock) {
            propertyChangeRegistry.clear()
        }
    }
}