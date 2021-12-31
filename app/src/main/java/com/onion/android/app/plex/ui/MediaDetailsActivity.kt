package com.onion.android.app.plex.ui

import android.graphics.Color
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.onion.android.R
import com.onion.android.app.base.BaseActivity
import com.onion.android.app.plex.data.local.entity.Media
import com.onion.android.app.plex.data.model.stream.MediaStream
import com.onion.android.app.plex.ui.adapter.RelatesAdapter
import com.onion.android.app.plex.ui.adapter.decoration.SpacingItemDecoration
import com.onion.android.app.plex.vm.DetailVideModel
import com.onion.android.app.utils.Tools
import com.onion.android.app.view.dp
import com.onion.android.databinding.ActivityMovieDetailsActivityBinding
import com.onion.android.databinding.PlexDialogBottomStreamBinding
import com.onion.android.kotlin.extension.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

const val ARG_MOVIE = "movie"

class MediaDetailsActivity :
    BaseActivity<ActivityMovieDetailsActivityBinding>(R.layout.activity_movie_details_activity) {

    @Inject
    lateinit var detailVideModel: DetailVideModel
    private lateinit var currentUrl: String
    private var mediaGenre = ""
    private lateinit var currentMediaStream: MediaStream

    override fun init() {
        intent.getParcelableExtra<Media>(ARG_MOVIE) ?: return
        detailVideModel.media = intent.getParcelableExtra(ARG_MOVIE)!!
        initView()
        initLiveData()
        if (detailVideModel.media.tmdbId.isNotNull()) {
            detailVideModel.getMediaDetail(detailVideModel.media.tmdbId)
        } else {
            detailVideModel.getMediaDetail(detailVideModel.media.id)
        }
    }

    private fun initView() {
        setSystemBarTransparent()
        hideSystemBar()
        loadToolbar(binding.toolbar, binding.appbar)
        binding.backbutton.setOnClickListener { onBackPressed() }
        binding.PlayButtonIcon.setOnClickListener {
            showVideoSelect()
        }
    }

    private fun initLiveData() {
        detailVideModel.movieDetailMutableLiveData.observe(this) {
            detailVideModel.media = it
            // 背景
            binding.imageMoviePoster.loadUrl(it.posterPath)
            // 标题
            binding.textMovieTitle.text = it.title
            // 发行日期
            if (!it.firstAirDate.isNullOrBlank()) {
                val sdf1 = SimpleDateFormat("yyyy-MM-dd")
                val sdf2 = SimpleDateFormat("yyyy")
                try {
                    val releaseDate = sdf1.parse(it.firstAirDate)
                    binding.textMovieRelease.text = " - " + sdf2.format(releaseDate!!)
                } catch (e: ParseException) {
                    Timber.d("%s", Arrays.toString(e.stackTrace))
                }
            }
            // 概述
            binding.textOverviewLabel.text = it.overview
            // 设置评分
            binding.viewMovieRating.text = it.voteAverage.toString()
            binding.ratingBar.rating = it.voteAverage / 2
            // 设置观看人数
            binding.viewMovieViews.text = getString(R.string.views) + it.views
            // 滑动监听
            binding.itemDetailContainer.viewTreeObserver.addOnScrollChangedListener {
                val scrollY = binding.itemDetailContainer.scrollY
                var color = Color.parseColor("#9A434141") // ideally a global variable
                if (scrollY < 256) {
                    val alpha = scrollY shl 24 or (-1 ushr 8)
                    color = color and alpha
                    binding.serieName.text = ""
                    binding.serieName.visibility = View.GONE
                } else {
                    binding.serieName.text = it.title
                    binding.serieName.visibility = View.VISIBLE
                }
                binding.toolbar.setBackgroundColor(color)
            }
            detailVideModel.getRelatedMovies(Integer.parseInt(it.id))
            checkLoad()
        }

        detailVideModel.movieRelatesMutableLiveData.observe(this) { relateds ->
            if (relateds.relateds.isNullOrEmpty()) {
                binding.tvLike.visibility = View.VISIBLE
                return@observe
            }
            val adapter = RelatesAdapter()
            adapter.submitList(relateds.relateds)
            binding.rvMylike.adapter = adapter
            binding.rvMylike.setHasFixedSize(true)
            binding.rvMylike.isNestedScrollingEnabled = false
            binding.rvMylike.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.rvMylike.addItemDecoration(
                SpacingItemDecoration(1, 2.dp.toInt(), true)
            )
        }
    }

    private fun checkLoad() {
        launch {
            delay(500)
            binding.progressBar.fadeOut()
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showVideoSelect() {
        val charSequence = arrayOfNulls<String>(detailVideModel.media.videos.size)
        for (index in detailVideModel.media.videos.indices) {
            charSequence[index] =
                detailVideModel.media.videos[index].server + " - " + detailVideModel.media.videos[index].lang
        }
        val builder = AlertDialog.Builder(this, R.style.MyAlertDialogTheme)
        builder.setTitle("清晰度")
        builder.setItems(charSequence) { _, which ->
            detailVideModel.media.videos[which]?.apply {
                showPlayDialog(which)
                currentUrl = link
                currentMediaStream = this
            }
        }
        builder.setCancelable(true)
        builder.show()
    }

    //                                 startStreamFromDialog(movieDetail, wich, externalId, movieDetail.getVideos().get(wich).getLink(), movieDetail.getVideos().get(wich));
    private fun showPlayDialog(which: Int) {
        val dialog = AppCompatDialog(this)
        dialog.setBindingView<PlexDialogBottomStreamBinding>(R.layout.plex_dialog_bottom_stream)
            .apply {
                btClose.setOnClickListener { dialog.dismiss() }
                plexPlayer.setOnClickListener {
                    Tools.useMainPlay(
                        this@MediaDetailsActivity,
                        detailVideModel.media, currentUrl, currentMediaStream.server,
                        mediaGenre, currentMediaStream
                    )
                }
            }
        dialog.setNormalStyleShow()
    }
}