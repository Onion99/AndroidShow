package com.onion.android.app.plex.ui

import android.os.Bundle
import android.view.View
import com.onion.android.R
import com.onion.android.app.base.BaseActivity
import com.onion.android.app.plex.data.local.entity.Media
import com.onion.android.app.plex.vm.DetailVideModel
import com.onion.android.app.utils.Tools
import com.onion.android.databinding.ActivitySerieDetailsActivityBinding

const val ARG_MOVIE = "movie"

class SerieDetailsActivity :
    BaseActivity<ActivitySerieDetailsActivityBinding>(R.layout.activity_serie_details_activity) {

    private val detailVideModel by lazy { getActivityScopeViewModel(DetailVideModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getParcelableExtra<Media>(ARG_MOVIE) ?: return
        detailVideModel.media = intent.getParcelableExtra(ARG_MOVIE)!!
        detailVideModel.movieDetailMutableLiveData.observe(this) {
            Tools.onLoadMediaCover(this, binding.imageMoviePoster, it.posterPath)
            binding.serieTitle.text = it.name
        }
    }

    fun init() {
        binding.scrollView.visibility = View.GONE
        if (detailVideModel.media.tmdbId.isNullOrEmpty()) {
            detailVideModel.getMediaDetail(detailVideModel.media.tmdbId)
        } else {
            detailVideModel.getMediaDetail(detailVideModel.media.id)
        }
    }
}