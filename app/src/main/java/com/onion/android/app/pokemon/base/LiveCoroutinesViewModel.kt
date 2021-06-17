package com.onion.android.app.pokemon.base

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

abstract class LiveCoroutinesViewModel : ViewModel(){
    /*
    * crossinline : 禁止传递给内联函数的 lambda 中的非局部
    * 一些内联函数可能调用传给它们的不是直接来自函数体,
    * 而是来自另一个执行上下文的 lambda 表达式参数，例如来自局部对象或嵌套函数。
    * 在这种情况下，该 lambda 表达式中也不允许非局部控制流。为了标识这种情况，该 lambda 表达式参数需要用crossinline修饰符标记
    * */
    inline fun<T> launchOnViewModelScope( crossinline block: suspend () -> LiveData<T>) : LiveData<T>{
        /*
        * suspend : 将一个函数或 lambda 表达式标记为挂起式（可用做协程）
        * 在协程内部可以像普通函数一样使用挂起函数，不过其额外特性是，同样可以使用其他挂起函数（如本例中的delay）来挂起协程的执行
        * */
        return liveData(viewModelScope.coroutineContext + Dispatchers.IO){
            emitSource(block())
        }
    }

    fun <T> Flow<T>.asLiveDataViewModelScope(): LiveData<T>{
        return  asLiveData(viewModelScope.coroutineContext + Dispatchers.IO)
    }
}