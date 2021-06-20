package com.onion.android.app.pokemon.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

///////////////////////////////////////////////////////////////////////////
// @JvmSuppressWildcards: 用来注解类和方法，使得被标记元素的泛型参数不会被编译成通配符?
// Kotlin学习笔记——注解 : https://blog.csdn.net/hjkcghjmguyy/article/details/73931877
///////////////////////////////////////////////////////////////////////////
@Singleton
class PokedexViewModelFactory
@Inject constructor(private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) :
    ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        var creator: Provider<out ViewModel>? = creators[modelClass]
        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }

        requireNotNull(creator) { "unknown model class $modelClass" }
        return try {
            creator.get() as T
        } catch (e: Exception) {
            throw IllegalStateException(e)
        }
    }
}
