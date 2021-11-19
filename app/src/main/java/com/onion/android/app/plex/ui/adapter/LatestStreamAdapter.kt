package com.onion.android.app.plex.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.onion.android.R
import com.onion.android.app.base.adpte.BindingAdapter
import com.onion.android.app.plex.data.local.entity.Media
import com.onion.android.databinding.PlexItemShowStreamingBinding
import com.onion.android.kotlin.extension.getBinding
import com.onion.android.kotlin.extension.loadUrl

class LatestStreamAdapter : BindingAdapter<Media, LatestStreamAdapter.RelatesViewHolder>(diffUtil) {

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Media>() {
            override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean {
                return oldItem == newItem
            }
        }
    }


    inner class RelatesViewHolder(val binding: PlexItemShowStreamingBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelatesViewHolder =
        RelatesViewHolder(parent.getBinding(R.layout.plex_item_show_streaming))

    override fun onBindViewHolder(holder: RelatesViewHolder, position: Int) {
        val media = getItem(position)
        holder.binding.movietitle.text = media.name
        for (genre in media.genres) {
            holder.binding.mgenres.text = genre.getName()
        }
        holder.binding.itemMovieImage.loadUrl(media.posterPath)
    }
}