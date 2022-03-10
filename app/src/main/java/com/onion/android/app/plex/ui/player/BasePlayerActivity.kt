package com.onion.android.app.plex.ui.player

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.Format
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.MergingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.SingleSampleMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector.ParametersBuilder
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultAllocator
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.util.Assertions
import com.google.android.exoplayer2.util.Util
import com.onion.android.R
import com.onion.android.app.constants.PlexConstants.EXTENTIONS
import com.onion.android.app.constants.PlexConstants.WIFI_CHECK
import com.onion.android.app.plex.data.model.media.MediaModel
import com.onion.android.app.plex.player.cache.CacheDataSourceFactory
import com.onion.android.app.plex.player.interfaces.ControlInterface
import com.onion.android.app.plex.player.interfaces.PlaybackActionCallback
import com.onion.android.app.plex.util.EventLogger
import com.onion.android.app.plex.util.MediaHelper
import com.onion.android.app.utils.NetworkUtil
import com.onion.android.app.utils.Tools
import com.onion.android.databinding.PlexActivityPlayerBinding
import com.onion.android.kotlin.extension.isNotNull
import dagger.android.AndroidInjection
import javax.inject.Inject

const val KEY_MEDIA = "key_media"
const val KEY_TRACK_SELECTOR_PARAMETERS = "key_track_selector_parameters"

abstract class BasePlayerActivity : FragmentActivity(), PlaybackActionCallback {

    lateinit var binding: PlexActivityPlayerBinding

    // 基本播放器
    lateinit var moviePlayer: SimpleExoPlayer

    // 媒体源工厂 - 带缓存
    private lateinit var mediaDataSourceCacheFactory: DataSource.Factory

    // 媒体源工厂 - 不带缓存
    private lateinit var mediaDataSourceFactory: DataSource.Factory

    // 视频轨道参数
    private lateinit var trackSelectorParameters: DefaultTrackSelector.Parameters

    // 视频轨道
    private lateinit var trackSelector: DefaultTrackSelector

    // 媒体数据
    private lateinit var mediaModel: MediaModel

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    // ------------------------------------------------------------------------
    // 首先获取传入的媒体数据
    // ------------------------------------------------------------------------
    private fun parseIntent() {
        val error = "MediaModel is empty"
        Assertions.checkState(intent != null && intent.extras != null, error)
        mediaModel = intent.extras!!.getSerializable(KEY_MEDIA) as MediaModel
        mediaDataSourceFactory =
            MediaHelper.buildDataSourceFactory(this, DefaultBandwidthMeter.Builder(baseContext))
        mediaDataSourceCacheFactory =
            CacheDataSourceFactory(this, 100 * 1024 * 1024, 5 * 1024 * 1024)
    }

    // ------------------------------------------------------------------------
    // 初始化工作:UI默认状态,播放进度恢复,媒体数据处理
    // ------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        parseIntent()
        trackSelectorParameters = if (savedInstanceState != null) {
            savedInstanceState.getParcelable(KEY_TRACK_SELECTOR_PARAMETERS)!!
        } else {
            val builder = ParametersBuilder(this)
            builder.build()
        }
        binding = DataBindingUtil.setContentView(this, R.layout.plex_activity_player)
        binding.tubitvPlayer.requestFocus()
        binding.vpaidWebview.setBackgroundColor(Color.BLACK)
        binding.tubitvPlayer.addUserInteractionView(addUserInteractionView())
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        releaseMoviePlayer()
        setIntent(intent)
    }

    override fun onStart() {
        super.onStart()
        setupExo()
    }

    override fun onResume() {
        super.onResume()
        setupExo()
    }

    override fun onPause() {
        super.onPause()
        releaseMoviePlayer()
        updateResumePosition()
    }

    override fun onStop() {
        super.onStop()
        releaseMoviePlayer()
    }

    // ------------------------------------------------------------------------
    // 在onDestroy之前调用,保存状态
    // ------------------------------------------------------------------------
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        updateTrackSelectorParameters()
        updateResumePosition()
        outState.putParcelable(KEY_TRACK_SELECTOR_PARAMETERS, trackSelectorParameters)
    }

    // ------------------------------------------------------------------------
    // 播放准备流程
    // ------------------------------------------------------------------------
    protected open fun setupExo() {
        if (::moviePlayer.isInitialized) return
        // 检测wifi状态,是否强制wifi播放
        if (sharedPreferences.getBoolean(WIFI_CHECK, false) && NetworkUtil.isWifiConnected(this)) {
            binding.wifiLayoutWarning.visibility = View.VISIBLE
            binding.backFromWifi.setOnClickListener { onBackPressed() }
        }
        initMoviePlayer()
        setCaption(isCaptionPreferenceEnable())
        onPlayerReady()
        binding.tubitvPlayer.playerController.triggerSubtitlesToggle(true)
    }

    // ------------------------------------------------------------------------
    // 初始化播发器
    // ------------------------------------------------------------------------
    protected open fun initMoviePlayer() {
        val loadControl = DefaultLoadControl.Builder()
            .setAllocator(DefaultAllocator(true, 16))
            .setBufferDurationsMs(2000, 5000, 1500, 2000)
            .setTargetBufferBytes(-1)
            .setPrioritizeTimeOverSizeThresholds(true).createDefaultLoadControl()
        // 创建播放器
        val renderersFactory =
            MediaHelper.buildRenderersFactory(this, sharedPreferences.getBoolean(EXTENTIONS, false))
        trackSelector = DefaultTrackSelector(this)
        moviePlayer = SimpleExoPlayer.Builder(this, renderersFactory)
            .setTrackSelector(trackSelector)
            .setLoadControl(loadControl).build()
        val logger = EventLogger(trackSelector)
        moviePlayer.addAnalyticsListener(logger)
        moviePlayer.addMetadataOutput(logger)
        binding.tubitvPlayer.setPlayer(moviePlayer, this)
        binding.tubitvPlayer.setMediaModel(mediaModel)
    }

    private fun setCaption(isOn: Boolean) {
        binding.tubitvPlayer.playerController.triggerSubtitlesToggle(isOn)
    }

    // ------------------------------------------------------------------------
    // 释放播放器资源
    // ------------------------------------------------------------------------
    protected open fun releaseMoviePlayer() {
        if (::moviePlayer.isInitialized) {
            updateTrackSelectorParameters()
            updateResumePosition()
            moviePlayer.release()
        }
    }

    // ------------------------------------------------------------------------
    // 建立媒体播放视频源
    // ------------------------------------------------------------------------
    fun buildMediaSource(model: MediaModel): MediaSource {
        var mediaSource: MediaSource
        val test1Uri = Uri.parse("http://vjs.zencdn.net/v/oceans.mp4")
        if (model.hlscustomformat == 1) {
            mediaSource =
                HlsMediaSource.Factory(mediaDataSourceFactory).createMediaSource(model.mediaUrl)
            if (model.mediaSubstitleUrl != null) {
                val subtitleSource = SingleSampleMediaSource.Factory(mediaDataSourceFactory)
                    .createMediaSource(
                        model.mediaSubstitleUrl,
                        Format.createTextSampleFormat(
                            null, Tools.getSubtitleMime(model.mediaSubstitleUrl),
                            null, 0, C.SELECTION_FLAG_DEFAULT,
                            "en", null, 0
                        ), C.TIME_UNSET
                    )
                mediaSource = MergingMediaSource(mediaSource, subtitleSource)
            }
        } else {
            val type =
                if (model.mediaExtension.isNotNull()) Util.inferContentType("." + model.mediaExtension)
                else Util.inferContentType(model.mediaUrl)
            mediaSource = when (type) {
                C.TYPE_OTHER ->
                    // no cache
                    // mediaSource = new ProgressiveMediaSource.Factory(mMediaDataSourceFactory).createMediaSource(model.getMediaUrl());
                    ProgressiveMediaSource.Factory(mediaDataSourceCacheFactory)
                        .createMediaSource(test1Uri)
                C.TYPE_HLS -> HlsMediaSource.Factory(mediaDataSourceFactory)
                    .createMediaSource(model.mediaUrl)
                C.TYPE_SS -> SsMediaSource.Factory(mediaDataSourceFactory)
                    .createMediaSource(model.mediaUrl)
                C.TYPE_DASH -> DashMediaSource.Factory(mediaDataSourceFactory)
                    .createMediaSource(model.mediaUrl)
                else -> throw IllegalStateException("Unexpected value: $type")
            }

            if (model.mediaSubstitleUrl != null) {
                val subtitleSource = SingleSampleMediaSource.Factory(mediaDataSourceFactory)
                    .createMediaSource(
                        model.mediaSubstitleUrl,
                        Format.createTextSampleFormat(
                            null, Tools.getSubtitleMime(model.mediaSubstitleUrl),
                            null, 0, C.SELECTION_FLAG_DEFAULT,
                            "en", null, 0
                        ), C.TIME_UNSET
                    )
                mediaSource = MergingMediaSource(mediaSource, subtitleSource)
            }
        }
        return mediaSource
    }

    // ------------------------------------------------------------------------
    // 更新播放位置
    // ------------------------------------------------------------------------
    private fun updateTrackSelectorParameters() {
        if (::trackSelector.isInitialized) {
            trackSelectorParameters = trackSelector.parameters
        }
    }

    // ------------------------------------------------------------------------
    // 获取视频播放控制器
    // ------------------------------------------------------------------------
    fun getPlayerController(): ControlInterface {
        return binding.tubitvPlayer.playerController
    }

    protected abstract fun addUserInteractionView(): View
    protected abstract fun onPlayerReady()
    protected abstract fun updateResumePosition()
    protected abstract fun isCaptionPreferenceEnable(): Boolean
}