package com.onion.android.app.pokemon.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.onion.android.app.pokemon.adapter.PokemonAdapter
import com.onion.android.app.pokemon.model.Pokemon
import com.onion.android.app.pokemon.vm.PokedexMainViewModel
import com.onion.android.kotlin.simple.whatif.whatIfNotNullAs
import com.onion.android.kotlin.simple.whatif.whatIfNotNullOrEmpty
import com.skydoves.baserecyclerviewadapter.RecyclerViewPaginator

object RecyclerViewBinding {

    @JvmStatic
    @BindingAdapter("adapter")
    fun bindAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>){
        view.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("paginationPokemonList")
    fun paginationPokemonList(view: RecyclerView, viewModelPokedex: PokedexMainViewModel) {
        RecyclerViewPaginator(
            recyclerView = view,
            isLoading = { viewModelPokedex.isLoading.get() },
            loadMore = { viewModelPokedex.fetchPokemonList() },
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