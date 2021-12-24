package com.onion.android.app.pokemon.vm

import androidx.annotation.MainThread
import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.onion.android.app.pokemon.repository.MainRepository
import com.onion.android.kotlin.extension.asBindingProperty
import com.onion.android.kotlin.jetpack.BindingViewModel
import com.onion.android.kotlin.jetpack.bindingProperty
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class PokedexMainViewModel @Inject constructor(private val mainRepository: MainRepository) :
    BindingViewModel() {

    // ------------------------------------------------------------------------
    //  private set -> 表示此 setter 是私有的并且有默认实现
    //  @get:Bindable -> 将 @Bindable 注解应用于属性 getter ,因为我们自定义 set 的实现了
    //  @field:Ann val foo -> 标注 Java 字段
    // ------------------------------------------------------------------------
    @get:Bindable
    var isLoading by bindingProperty(false)
        private set

    @get:Bindable
    var toastMessage by bindingProperty("")
        private set

    private val pokemonFetchingIndex: MutableStateFlow<Int> = MutableStateFlow(0)

    private val getPokemonList = pokemonFetchingIndex.flatMapLatest { page ->
        // flatMapLatest ->多次发送,只取最新的值
        mainRepository.fetchPokemonList(
            page,
            { isLoading = true },
            { isLoading = false },
            { toastMessage = it ?: "" }
        )
    }

    @get:Bindable
    val pokemonList by getPokemonList.asBindingProperty(viewModelScope, emptyList())


    @MainThread
    fun fetchNextPokemonList() {
        if (!isLoading) {
            pokemonFetchingIndex.value++
        }
    }
}