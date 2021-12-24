package com.onion.android.kotlin.extension

import com.onion.android.kotlin.jetpack.BindingManager
import com.onion.android.kotlin.jetpack.BindingObservable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty


fun <T> Flow<T>.asBindingProperty(
    coroutineScope: CoroutineScope,
    defaultValue: T
) = FlowBindingProperty(this, coroutineScope, defaultValue)


// ------------------------------------------------------------------------
// 自定义Binding属性类,因为用于委托代理的 ( by )
// 1. 由于要先处理Flow的数据,所以要重载 Delegate 提供 -> provideDelegate
// 2. 然后再重载 getValue() 和 setValue()
// ------------------------------------------------------------------------
class FlowBindingProperty<T> constructor(
    private val flow: Flow<T>,
    private val coroutineScope: CoroutineScope,
    private val defaultValue: T
) {
    operator fun provideDelegate(
        observable: BindingObservable,
        property: KProperty<*>
    ): Delegate<T> {
        val bindingId = BindingManager.getBindingIdByProperty(property)
        val delegate = Delegate(defaultValue, coroutineScope, bindingId)
        delegate.collect(flow, observable)
        return delegate
    }

    class Delegate<T>(
        private var value: T,
        private val coroutineScope: CoroutineScope,
        private val bindingId: Int
    ) {
        fun collect(flow: Flow<T>, bindingObservable: BindingObservable) {
            coroutineScope.launch {
                flow.distinctUntilChanged().collect {
                    value = it
                    bindingObservable.notifyPropertyChanged(bindingId)
                }
            }
        }

        operator fun getValue(thisRef: Any, property: KProperty<*>): T = value
    }
}