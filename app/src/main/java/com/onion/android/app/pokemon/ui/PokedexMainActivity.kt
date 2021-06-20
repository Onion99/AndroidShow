package com.onion.android.app.pokemon.ui

import android.os.Bundle
import com.onion.android.R
import com.onion.android.app.base.BindingActivity
import com.onion.android.app.pokemon.adapter.PokemonAdapter
import com.onion.android.app.pokemon.transformation.onTransformationStartContainer
import com.onion.android.app.pokemon.vm.PokedexMainViewModel
import com.onion.android.databinding.PokedexActivityMainBinding

// @AndroidEntryPoint,现在主用Dagger,不用hilt
class PokedexMainActivity :
    BindingActivity<PokedexActivityMainBinding>(R.layout.pokedex_activity_main) {

    ///////////////////////////////////////////////////////////////////////////
    // 返回一个懒惰的委托来访问ComponentActivity的ViewModel
    // 如果指定了factoryProducer ，则它返回的ViewModelProvider.Factory将用于首次创建ViewModel
    // 仅在将Activity附加到应用程序之后才能访问此属性，并且在此之前的访问将导致IllegalArgumentException
    // @VisibleForTesting 可以把这个注解标注到类、方法或者字段上，以便在测试的时候可以使用。
    // 这个Annotation只是一个指示作用，告诉其他开发者该函数为什么有这么大的可见程度
    ///////////////////////////////////////////////////////////////////////////
    val viewModelPokedex: PokedexMainViewModel by lazy {
        getInjectViewModel(PokedexMainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationStartContainer()
        super.onCreate(savedInstanceState)
        binding.apply {
            // 标注其配套的生命周期
            lifecycleOwner = this@PokedexMainActivity
            // 给 Layout 里的 Data 赋值
            adapter = PokemonAdapter()
            vm = viewModelPokedex
        }
    }

}