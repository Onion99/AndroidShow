package com.onion.android.kotlin.jetpack

import kotlin.reflect.KProperty


// ------------------------------------------------------------------------
// 扩展声明属性对Binding Id 的获取
// ------------------------------------------------------------------------
internal inline val KProperty<*>.bindingId: Int
    @JvmSynthetic get() = BindingManager.getBindingIdByProperty(this)


// ------------------------------------------------------------------------
// Binding属性声明实例化扩展
// ------------------------------------------------------------------------
fun <T> bindingProperty(defaultValue: T): BindingPropertyIdWithDefaultValue<T> =
    BindingPropertyIdWithDefaultValue(defaultValue)

// ------------------------------------------------------------------------
// 自定义Binding属性类,因为用于委托代理的 ( by ) 所以必须重载 getValue() 和 setValue()
// ------------------------------------------------------------------------
class BindingPropertyIdWithDefaultValue<T>(private var value: T) {

    operator fun getValue(observable: BindingObservable, property: KProperty<*>): T = value

    operator fun setValue(observable: BindingObservable, property: KProperty<*>, value: T) {
        if (this.value != value) {
            this.value = value
            observable.notifyPropertyChanged(property.bindingId)
        }
    }
}
