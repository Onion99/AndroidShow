package com.onion.android.app.plex.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.base.ext.startActivity
import com.onion.android.R
import com.onion.android.app.base.adpte.BindingAdapter
import com.onion.android.app.plex.data.local.entity.Media
import com.onion.android.app.plex.ui.ARG_MOVIE
import com.onion.android.app.plex.ui.MediaDetailsActivity
import com.onion.android.databinding.PlexItemRelatedsBinding
import com.onion.android.kotlin.extension.getBinding
import com.onion.android.kotlin.extension.loadUrl

class RelatesAdapter : BindingAdapter<Media, RelatesAdapter.RelatesViewHolder>(diffUtil) {

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


    inner class RelatesViewHolder(val binding: PlexItemRelatedsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelatesViewHolder =
        RelatesViewHolder(parent.getBinding(R.layout.plex_item_relateds))

    override fun onBindViewHolder(holder: RelatesViewHolder, position: Int) {
        val media = getItem(position)
        holder.binding.movieName.text = media.title
        holder.binding.imageMovie.loadUrl(media.posterPath)
        holder.binding.root.setOnClickListener {
            it.context.startActivity<MediaDetailsActivity>(
                ARG_MOVIE to media
            )
        }
    }
}