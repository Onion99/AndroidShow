package com.onion99.android.kotlin.ui.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.onion99.android.kotlin.model.Pokemon
import com.onion99.android.kotlin.simple.whatif.whatIfNotNullAs
import com.onion99.android.kotlin.simple.whatif.whatIfNotNullOrEmpty
import com.onion99.android.kotlin.ui.adapter.PokemonAdapter
import com.onion99.android.kotlin.vm.MainViewModel
import com.skydoves.baserecyclerviewadapter.RecyclerViewPaginator

object RecyclerViewBinding {

    @JvmStatic
    @BindingAdapter("adapter")
    fun bindAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>){
        view.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("paginationPokemonList")
    fun paginationPokemonList(view: RecyclerView,viewModel: MainViewModel){
        RecyclerViewPaginator(
            recyclerView = view,
            isLoading = { viewModel.isLoading.get() },
            loadMore = { viewModel.fetchPokemonList() },
            onLast = { false }
        ).run {
            threshold = 8
        }
    }

    @JvmStatic
    @BindingAdapter("adapterPokemonList")
    fun bindAdapterPokemonList(view: RecyclerView, pokemonList: List<Pokemon>?){
        pokemonList.whatIfNotNullOrEmpty { itemList ->
            view.adapter.whatIfNotNullAs<PokemonAdapter> { adapter ->
                adapter.setPokemonList(itemList)
            }
        }
    }
}