package com.onion.android.app.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * 基于DataBinding的基类
 */
abstract class BindingActivity<T : ViewDataBinding> constructor(
    @LayoutRes private val layoutId: Int
) : AppCompatActivity(), HasAndroidInjector {

    private lateinit var androidInjector: AndroidInjector<Any>

    /**
     * protected 和 private一样 + 在子类中可见
     * 编译期间生成此接口，以生成所有使用的实例BindingAdapters
     */
    protected var bindingComponent: DataBindingComponent? = DataBindingUtil.getDefaultComponent()

    /**
     * DataBinding将在调用onCreate之前初始化
     * 然后使用layoutId作为Activity的content view进行填充
     */
    @BindingOnly
    protected val binding: T by lazy(LazyThreadSafetyMode.NONE) {
        DataBindingUtil.setContentView(this, layoutId, bindingComponent)
    }

    /**
     * 使用高阶函数会带来一些运行时的效率损失：每一个函数都是一个对象，并且会捕获一个闭包。
     * 即那些在函数体内会访问到的变量。 内存分配（对于函数对象和类）和虚拟调用会引入运行时间开销
     * 但是在许多情况下通过内联化 lambda 表达式可以消除这类的开销
     * https://www.kotlincn.net/docs/reference/inline-functions.html
     * inline 修饰符影响函数本身和传给它的 lambda 表达式：所有这些都将内联到调用处
     * 简单的说，内联函数会将所有内联函数相关代码直接移到调用执行
     */
    @BindingOnly
    protected inline fun binding(block: T.() -> Unit): T {
        return binding.apply(block)
    }

    /**
     * 确保Activity在调用[onCreate]之前执行[binding]属性
     */
    init {
        addOnContextAvailableListener {
            // 通知侦听器此实例的所有属性已更改。
            binding.notifyChange()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 另一种方式实现依赖注入
        // AndroidInjection.inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    fun <T : ViewModel> getInjectViewModel(c: Class<T>) =
        ViewModelProvider(this, viewModelFactory).get(c)
}