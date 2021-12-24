package com.onion.android.app.pokemon.repository

import androidx.annotation.WorkerThread
import com.onion.android.app.pokemon.network.PokemonClient
import com.onion.android.app.pokemon.persistence.PokemonDao
import com.onion.android.kotlin.retrofit_coroutine_call_adapter_factory.common.map
import com.onion.android.kotlin.retrofit_coroutine_call_adapter_factory.common.onError
import com.onion.android.kotlin.retrofit_coroutine_call_adapter_factory.common.onException
import com.onion.android.kotlin.retrofit_coroutine_call_adapter_factory.common.suspendOnSuccess
import com.onion.android.kotlin.retrofit_coroutine_call_adapter_factory.mapper.ErrorResponseMapper
import com.onion.android.kotlin.simple.whatif.whatIfNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val pokemonClient: PokemonClient,
    private val pokemonDao: PokemonDao
) : Repository {


    @WorkerThread
    fun fetchPokemonList(
        page: Int,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow {
        var pokemonList = pokemonDao.getPokemonList(page)
        if (pokemonList.isEmpty()) {
            val response = pokemonClient.fetchPokemonList(page = page)
            response.suspendOnSuccess {
                data.whatIfNotNull { response ->
                    pokemonList = response.results
                    pokemonList.forEach { pokemon ->
                        pokemon.page = page
                    }
                    pokemonDao.insertPokemonList(pokemonList)
                    emit(pokemonDao.getAllPokemonList(page))
                }
            }.onError {
                map(ErrorResponseMapper) {
                    onError("[Code: $code]: $message")
                }
            }.onException {
                onError(message)
            }
        } else {
            emit(pokemonDao.getAllPokemonList(page))
        }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(Dispatchers.IO)
}

// ------------------------------------------------------------------------
// Flow
// Kotlin协程中使用挂起函数（Suspend函数）可以异步地返回单个计算结果，但是如果有多个计算结果希望通过协程的方式异步返回，这时可以使用Flows
// 1 - 在调用collect之前，flow{ ... }中的代码不会执行
// 2 - flow{ ... } 构建一个Flow类型
// 3 - flow { ... }内可以使用suspend函数.
// 4 - foo() 不需要是suspend函数
// 5 - emit方法用来发射(传递)数据
// 6 - collect方法用来遍历结果
// ------------------------------------------------------------------------