package com.onion99.android.kotlin.base

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import kotlin.properties.Delegates

/*
* 抽象类 abstract class
* 1 - 抽象成员在本类中可以不用实现
* 2 - 不需要用 open 标注一个抽象类或者函数
* 3 - 可以用一个抽象成员覆盖一个非抽象的开放成员
* */
abstract class DataBindingActivityKt : AppCompatActivity() {
    /*
    * inline 内联
    * 1 - 可以将函数体直接复制到函数调用处( 减少函数调用，这样就可以调用栈的产生,但如果inline函数体很大，调用他的地方有很多，这样还会产生更多的字节码)
    * 2 - 同样如果短时间内 lambda 表达式被多次调用，大量的对象实例化就会产生内存流失（Memory Churn）,为了避免这种情况，我们就可以使用 inline
    * 3 - inline 关键字应该只用在需要内联特性的函数中，比如高阶函数作为参数和具体化的类型参数时
    * 4 - 内联函数体中不能直接访问到其外部类的成员，所以需要声明访问的成员为 internal
    * */
    protected inline fun <reified T: ViewDataBinding> binding(
        @LayoutRes resId:Int
    ): Lazy<T> = lazy { DataBindingUtil.setContentView(this,resId) }

    /*
    * reified 用于内联函数支持具体化的类型参数，即将内联函数的类型参数标记为在运行时可访问
    * 因为给函数使用内联之后，编译器会用其函数体来替换掉函数调用，而如果该函数里面有泛型就可能会出现编译器不懂该泛型的问题，所以引入reified，使该泛型被智能替换成对应的类型
    * */

    /*
    * by - 可以将一个对象的构造和设置值，都给委托给其它
    * */

    // TODO 委托模式
    /*
    * 延迟属性（ Lazy ) : 其值只在首次访问时计算；
    * 1 - lazy { } - 是接受一个 lambda 并返回一个Lazy <T>实例的函数，返回的实例可以作为实现延迟属性的委托
    * 2 - 延迟属性第一次调用get()会执行已传递给lazy()的 lambda 表达式并记录结果，后续调用get()只是返回记录的结果
    * 3 - 默认情况下，对于 lazy 属性的求值是同步锁的（synchronized）：该值只在一个线程中计算，并且所有线程会看到相同的值。如果初始化委托的同步锁不是必需的，这样多个线程可以同时执行，那么将LazyThreadSafetyMode.PUBLICATION作为参数传递给lazy()函数
    * 4 - 如果你确定初始化将总是发生在与属性使用位于相同的线程，那么可以使用LazyThreadSafetyMode.NONE模式：它不会有任何线程安全的保证以及相关的开销
    * */
    val lazyValue : String by lazy {
        "Lazy Value"
    }
    val lazyValue2 : String by lazy(LazyThreadSafetyMode.PUBLICATION) {
        "()"
    }
    private val lazyFun = lazy(LazyThreadSafetyMode.NONE,{ "NONE" })
    val lazyValue3 : String by lazyFun

    /*
    * 可观察属性（observable properties) : 监听器会收到有关此属性变更的通知
    * 1 - Delegates.notNull 在取值的时候，如果是null就会抛异常
    * 2 - Delegates.observable 接受两个参数：初始值与修改时处理程序（handler）。每当我们给属性赋值时会调用该处理程序（在赋值后执行）
    * 3 - Delegates.vetoable 能在设置值的时候，加逻辑判断，如果不满足便不更新值
    * */
    val delegateValue : String by Delegates.notNull<String>()
    private var backPressedTime by Delegates.observable(0L,{
            _, oldValue, newValue ->
        if(newValue - oldValue < 2000){
             finish()
        }else{
            // toast: really back?
        }
    })
    override fun onBackPressed(){
        backPressedTime = System.currentTimeMillis()
    }
    val delegateValue3 by Delegates.vetoable(""){
        property, oldValue, newValue ->  newValue.startsWith("delegate")
    }
    /*
    * 属性委托要求
    * a - 只读属性（ val）,委托必须提供一个操作符函数getValue() , 该函数具有一下参数
    *   1 - thisRef —— 必须与属性所有者类型（对于扩展属性——指被扩展的类型）相同或者是其超类型
    *   2 - property —— 必须是类型KProperty<*>或其超类型
    * b - 可写属性（ var ）, ，委托必须额外提供一个操作符函数setValue()，该函数具有以下参数
    *   1 - thisRef —— 必须与属性所有者类型（对于扩展属性——指被扩展的类型）相同或者是其超类型
    *   2 - property —— 必须是类型KProperty<*>或其超类型
    *   3 - value — 必须与属性类型相同（或者是其超类型）
    * */
}
open class Carvas(){
    /*
    * TODO 可见性 private,protected,internal,public
    * 包级别的
    * 1 - protected 在 “top-level” 中不可以使用，即不能修饰包级别的方法或者属性等
    * 2 - private 声明在包含声明的源文件中可见
    * 3 - internal 声明，在同一模块中的任何地方可见
    * 类型和接口中
    * 1 - private 只在该类(以及它的成员)中可见+
    * 2 - protected 和 private 一样但protected在子类中也可见
    * 3 - internal 在本模块的所有可以访问到声明区域的均可以访问该类的所有 internal 成员
    * 4 - public 任何地方可见
    * 局部声明
    * 1 - 函数和类是不允许使用修饰词的
    * 构造函数中使用修饰词
    * 1 - 指定主构造函数不可访问，私有化
    * */
    // 包级别的,即 com.onion99.android.xxx 下，不在任何类中
    private fun  test1(){}
    public   var v="任何地方都可以见,默认就是public，因而可以省略"
    private  var v2="只有在本源文件中可见"
    internal val v3="同一模块下可见"
    // 类型和接口中
    private val v4 = "该属性不能被子类所访问"
    protected val v5 = "能被子类所访问"
    internal val v6 = "同一摸快下可以被访问"
    val v7 = "到处可以被访问"

    open fun draw(){}
}
abstract class Paint: Carvas(){
    abstract override fun draw()
}