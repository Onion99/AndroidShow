package com.onion.android.app.pokemon.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import com.onion.android.R
import com.onion.android.app.base.BindingActivity
import com.onion.android.databinding.PokedexActivityMainBinding
import com.onion.android.kotlin.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BindingActivity<PokedexActivityMainBinding>(R.layout.pokedex_activity_main){
    /**
     * 返回一个懒惰的委托来访问ComponentActivity的ViewModel，
     * 如果指定了factoryProducer ，则它返回的ViewModelProvider.Factory将用于首次创建ViewModel
     * 仅在将Activity附加到应用程序之后才能访问此属性，并且在此之前的访问将导致IllegalArgumentException
     */
    @VisibleForTesting
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}