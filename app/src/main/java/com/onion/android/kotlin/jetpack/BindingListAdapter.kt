package com.onion.android.kotlin.jetpack

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlin.reflect.KProperty

abstract class BindingListAdapter<T, VH : RecyclerView.ViewHolder> constructor(
    val callBack: DiffUtil.ItemCallback<T>
) : ListAdapter<T, VH>(callBack), BindingObservable {

    @get:Bindable
    var isSubmitted: Boolean = false
        private set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(::isSubmitted)
            }
        }

    override fun submitList(list: MutableList<T>?) {
        super.submitList(list)
        isSubmitted = list != null
    }

    override fun submitList(list: MutableList<T>?, commitCallback: Runnable?) {
        super.submitList(list, commitCallback)
        isSubmitted = list != null
    }

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

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        clearAllProperties()
    }
}