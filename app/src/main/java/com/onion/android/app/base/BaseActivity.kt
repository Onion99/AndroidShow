package com.onion.android.app.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onion.android.kotlin.extension.hideSystemBar
import com.onion.android.kotlin.extension.setSystemBarTransparent
import dagger.android.AndroidInjection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

abstract class BaseActivity<T : ViewDataBinding> constructor(@LayoutRes private val layoutId: Int) :
    AppCompatActivity(), CoroutineScope by MainScope() {

    private lateinit var fragmentProvider: ViewModelProvider
    private lateinit var activityProvider: ViewModelProvider

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @BindingOnly
    protected val binding: T by lazy(LazyThreadSafetyMode.NONE) {
        DataBindingUtil.setContentView(this, layoutId, DataBindingUtil.getDefaultComponent())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setSystemBarTransparent()
        hideSystemBar()
        init()
    }

    abstract fun init()

    ///////////////////////////////////////////////////////////////////////////
    // 确保Activity在调用[onCreate]之前执行[binding]属性
    ///////////////////////////////////////////////////////////////////////////
    init {
        // 监听Context可用时,初始化ViewDataBinding
        addOnContextAvailableListener {
            // 通知侦听器此实例的所有属性已更改。
            binding.notifyChange()
        }
    }

    override fun onDestroy() {
        binding.unbind()
        cancel()
        super.onDestroy()
    }

    protected open fun <T : ViewModel> getActivityScopeViewModel(modelClass: Class<T>): T {
        if (!::activityProvider.isInitialized) {
            activityProvider = ViewModelProvider(this, viewModelFactory)
        }
        return activityProvider.get(modelClass)
    }
}