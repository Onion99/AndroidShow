package com.onion.android.app.pokemon.vm

import androidx.annotation.MainThread
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.onion.android.app.pokemon.base.LiveCoroutinesViewModel
import com.onion.android.app.pokemon.model.Pokemon
import com.onion.android.app.pokemon.repository.MainRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber
import javax.inject.Inject

/*
*
*  @ViewModelInject 标识用于注入的androidx.lifecycle.ViewModel的构造函数
* */
// 现在主用Dagger,不用hilt
// @HiltViewModel
class PokedexMainViewModel
@Inject constructor(
    private val mainRepository: MainRepository,
) : LiveCoroutinesViewModel() {

    /**
     * MutableStateFlow https://juejin.cn/post/6844904168910487560
     * 可监听状态Flow
     */
    @ExperimentalCoroutinesApi
    private val pokemonFetchingIndex: MutableStateFlow<Int> = MutableStateFlow(0)

    /**
     * LiveData: https://www.cnblogs.com/guanxinjing/p/11544273.html
     * 1. 首先LiveData其实与数据实体类(POJO类)是一样的东西,它负责暂存数据.
     * 2. 其次LiveData其实也是一个观察者模式的数据实体类,它可以跟它注册的观察者回调数据是否已经更新.
     * 3. LiveData还能知晓它绑定的Activity或者Fragment的生命周期,它只会给前台活动的activity回调(这个很厉害).这样你可以放心的在它的回调方法里直接将数据添加到View,而不用担心会不会报错.(你也可以不用费心费力判断Fragment是否还存活)
     * LiveData与MutableLiveData区别:
     * LiveData在实体类里可以通知指定某个字段的数据更新.
     * MutableLiveData则是完全是整个实体类或者数据类型变化后才通知.不会细节到某个字段
     * LiveData与MutableStateFlow 区别
     * Live 可以感知生命周期
     * MutableStateFlow 有更多的操作式表达
     */
    val pokemonListLiveData: LiveData<List<Pokemon>>
    private val _toastLiveData: MutableLiveData<String> = MutableLiveData()
    val toastLiveData: LiveData<String> get() = _toastLiveData

    /*
    * DataBinding Observable https://blog.csdn.net/qq_26923265/article/details/82745408
    * 同样类似UI页面观察者
    * */
    val isLoading: ObservableBoolean = ObservableBoolean(false)

    init {
        Timber.d("init MainViewModel")
        pokemonListLiveData = pokemonFetchingIndex.asLiveData().switchMap { page ->
            isLoading.set(true)
            mainRepository.fetchPokemonList(
                page = page,
                onSuccess = { isLoading.set(false) },
                onError = { _toastLiveData.postValue(it) }
            ).asLiveDataViewModelScope()
        }
    }

    @MainThread
    fun fetchPokemonList() = pokemonFetchingIndex.value++

}