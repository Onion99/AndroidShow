package com.onion.android.app.plex.ui

import android.view.View
import com.onion.android.R
import com.onion.android.app.base.BaseActivity
import com.onion.android.app.constants.PlexConstants
import com.onion.android.app.constants.PlexConstants.SPECIALS
import com.onion.android.app.plex.data.local.entity.Media
import com.onion.android.app.plex.vm.DetailVideModel
import com.onion.android.databinding.ActivitySerieDetailsActivityBinding
import com.onion.android.kotlin.extension.fadeOut
import com.onion.android.kotlin.extension.loadUrl
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

const val ARG_MOVIE = "movie"

class MediaDetailsActivity :
    BaseActivity<ActivitySerieDetailsActivityBinding>(R.layout.activity_serie_details_activity) {

    private val detailVideModel by lazy { getActivityScopeViewModel(DetailVideModel::class.java) }

    override fun init() {
        intent.getParcelableExtra<Media>(ARG_MOVIE) ?: return
        detailVideModel.media = intent.getParcelableExtra(ARG_MOVIE)!!
        binding.scrollView.visibility = View.GONE
        binding.backbutton.setOnClickListener { onBackPressed() }
        initLiveData()

        if (detailVideModel.media.tmdbId.isNotEmpty()) {
            detailVideModel.getMediaDetail(detailVideModel.media.tmdbId)
        } else {
            detailVideModel.getMediaDetail(detailVideModel.media.id)
        }
    }

    private fun initLiveData() {
        detailVideModel.movieDetailMutableLiveData.observe(this) {
            // 背景
            binding.imageMoviePoster.loadUrl(it.posterPath)
            // 标题
            binding.serieTitle.text = it.name
            // 第几集
            it.seasons?.apply {
                binding.mseason.text = PlexConstants.SEASONS + size
                if (it.tmdbId.isNotEmpty()) {
                    val iterator = iterator()
                    while (iterator.hasNext()) {
                        if (iterator.next().name.equals(SPECIALS)) iterator.remove()
                    }
                }
            }
            // 发行日期
            if (it.firstAirDate.isNotEmpty()) {
                val sdf1 = SimpleDateFormat("yyyy-MM-dd")
                val sdf2 = SimpleDateFormat("yyyy")
                try {
                    val releaseDate = sdf1.parse(it.firstAirDate)
                    binding.mrelease.text = " - " + sdf2.format(releaseDate!!)
                } catch (e: ParseException) {
                    Timber.d("%s", Arrays.toString(e.stackTrace))
                }
            }
            // 概述
            binding.serieOverview.text = it.overview
            // 演员
            var genres = ""
            for (index in it.genres.indices) {
                if (index == it.genres.size - 1) {
                    genres += it.genre[1].toString()
                } else {
                    genres = genres + it.genre[index].toString() + ", "
                }
            }
            // 设置评分
            binding.viewMovieRating.text = it.voteAverage.toString()
            binding.ratingBar.rating = it.voteAverage / 2
            // 设置观看人数
            binding.viewMovieViews.text = getString(R.string.views) + it.views
            checkLoad()
        }
    }

    private fun checkLoad() {
        launch {
            delay(500)
            binding.progressBar.fadeOut()
            binding.progressBar.visibility = View.GONE
            binding.scrollView.visibility = View.VISIBLE
        }
    }
}