package com.onion.android.app.pokemon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onion.android.app.pokemon.model.Pokemon
import com.onion.android.databinding.PokedexItemPokemonBinding

class PokemonAdapter : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>(){

    private val items:MutableList<Pokemon> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonViewHolder {
        val binding = PokedexItemPokemonBinding.inflate(LayoutInflater.from(parent.context))
        return PokemonViewHolder(binding).apply {
            binding.root.setOnClickListener{

            }
        }
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.binding.apply {
            pokemon = items[position]
            // 执行绑定配置
            executePendingBindings()
        }
    }

    override fun getItemCount(): Int = items.size

    fun setPokemonList(pokemonList: List<Pokemon>){
        val previousItemSize = items.size
        items.clear()
        items.addAll(pokemonList)
        notifyItemRangeChanged(previousItemSize,pokemonList.size)
    }

    class PokemonViewHolder(val binding: PokedexItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root)

}