package com.onion.android.app.pokemon.ui

import android.os.Bundle
import com.onion.android.R
import com.onion.android.app.base.BindingActivity
import com.onion.android.app.pokemon.adapter.PokemonAdapter
import com.onion.android.app.pokemon.transformation.onTransformationStartContainer
import com.onion.android.app.pokemon.vm.PokedexMainViewModel
import com.onion.android.databinding.PokedexActivityMainBinding
import javax.inject.Inject

// @AndroidEntryPoint,现在主用Dagger,不用hilt
class PokedexMainActivity :
    BindingActivity<PokedexActivityMainBinding>(R.layout.pokedex_activity_main) {

    @Inject
    lateinit var mainViewModel: PokedexMainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationStartContainer()
        super.onCreate(savedInstanceState)
        binding.apply {
            // 标注其配套的生命周期
            lifecycleOwner = this@PokedexMainActivity
            // 给 Layout 里的 Data 赋值
            adapter = PokemonAdapter()
            vm = mainViewModel
        }
    }

}