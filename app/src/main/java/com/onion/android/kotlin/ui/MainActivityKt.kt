package com.onion.android.kotlin.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import com.onion.android.R
import com.onion.android.databinding.ActivityMainKtBinding
import com.onion.android.kotlin.base.DataBindingActivityKt
import com.onion.android.kotlin.ui.adapter.PokemonAdapter
import com.onion.android.kotlin.ui.anim.transformation.onTransformationStartContainer
import com.onion.android.kotlin.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

// 标记在Activity和Fragment上. 创建了一个和当前Activity/fragment生命周期相关的container
// 目前支持的类型是: Activity, Fragment, View, Service, BroadcastReceiver.
@AndroidEntryPoint
class MainActivityKt : DataBindingActivityKt() {
    // @VisibleForTesting 可以把这个注解标注到类、方法或者字段上，以便在测试的时候可以使用。
    // 这个Annotation只是一个指示作用，告诉其他开发者该函数为什么有这么大的可见程度
    @VisibleForTesting val viewModel: MainViewModel by viewModels()
    private val binding: ActivityMainKtBinding by binding(R.layout.activity_main_kt)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onTransformationStartContainer()
        binding.apply {
            // 标注其配套的生命周期
            lifecycleOwner = this@MainActivityKt
            // 给 Layout 里的 Data 赋值
            adapter = PokemonAdapter()
            vm = viewModel
        }
    }

}