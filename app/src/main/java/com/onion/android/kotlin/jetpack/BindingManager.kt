package com.onion.android.kotlin.jetpack

import androidx.databinding.Bindable
import com.onion.android.BR
import java.util.*
import kotlin.reflect.KProperty
import kotlin.reflect.full.hasAnnotation

object BindingManager {

    private val bindingFieldsMap = emptyMap<String, Int>()

    private const val JAVA_BEANS_BOOLEAN: String = "is"
    private const val JAVA_BEANS_GETTER: String = "get"
    private const val JAVA_BEANS_SETTER: String = "set"


    internal fun getBindingIdByProperty(property: KProperty<*>): Int {
        val bindingProperty = property.takeIf {
            it.getter.hasAnnotation<Bindable>()
        }
            ?: throw IllegalArgumentException("KProperty: ${property.name} must be annotated with the `@Bindable` annotation on the getter.")
        val propertyName = bindingProperty.name.decapitalize(Locale.ENGLISH)
        val bindingPropertyName = propertyName.takeIf {
            it.startsWith(JAVA_BEANS_BOOLEAN)
        }?.replaceFirst(JAVA_BEANS_BOOLEAN, String())
            ?.decapitalize(Locale.ENGLISH) ?: propertyName
        return bindingFieldsMap[bindingPropertyName] ?: BR._all
    }
}