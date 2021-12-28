package com.onion.android.app.plex.ui.player

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import com.google.android.exoplayer2.*
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
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.util.Assertions
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.exoplayer2.util.Util
import com.onion.android.R
import com.onion.android.app.constants.PlexConstants.EXTENTIONS
import com.onion.android.app.constants.PlexConstants.WIFI_CHECK
import com.onion.android.app.plex.data.model.media.MediaModel
import com.onion.android.app.plex.player.interfaces.ControlInterface
import com.onion.android.app.plex.player.interfaces.PlaybackActionCallback
import com.onion.android.app.plex.util.EventLogger
import com.onion.android.app.plex.util.MediaHelper
import com.onion.android.app.utils.NetworkUtil
import com.onion.android.databinding.PlexActivityPlayerBinding
import com.onion.android.kotlin.extension.hideSystemBar
import javax.inject.Inject

const val KEY_MEDIA = "key_media"
const val KEY_TRACK_SELECTOR_PARAMETERS = "key_track_selector_parameters"

abstract class BasePlayerActivity : LifeCycleActivity(), LifecycleOwner, PlaybackActionCallback {

    lateinit var binding: PlexActivityPlayerBinding
    lateinit var mMoviePlayer: SimpleExoPlayer
    private lateinit var mediaDataSourceFactory: DataSource.Factory
    private lateinit var trackSelectorParameters: DefaultTrackSelector.Parameters
    private lateinit var mTrackSelector: DefaultTrackSelector
    private lateinit var mediaModel: MediaModel
    private lateinit var mMediaDataSourceFactory: DataSource.Factory
    private var isInActive = false
    override fun isActive(): Boolean = isInActive

    private var activityRunning = false
    private var bandwidthMeter = DefaultBandwidthMeter.Builder(baseContext)

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    // ------------------------------------------------------------------------
    // 首先获取媒体数据
    // ------------------------------------------------------------------------
    private fun parseIntent() {
        val error = "MediaModel is empty"
        Assertions.checkState(intent != null && intent.extras != null, error)
        mediaModel = intent.extras!!.getSerializable(KEY_MEDIA) as MediaModel
        mediaDataSourceFactory = MediaHelper.buildDataSourceFactory(this, bandwidthMeter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
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
        binding.tubitvPlayer.onResume()
        activityRunning = true
    }

    override fun onResume() {
        super.onResume()
        setupExo()
        binding.tubitvPlayer.onPause()
        if (getPlayerController().hasSubsActive()) {
            if (getPlayerController().getMediaType() == "0") {
                val subs: String =
                    java.lang.String.valueOf(getPlayerController().getMediaSubstitleUrl())
                val id: String = getPlayerController().getVideoID()
                val externalId: String = getPlayerController().getCurrentExternalId()
                val currentQuality: String = getPlayerController().getVideoCurrentQuality()
                val type: String = getPlayerController().getMediaType()
                val artwork: String =
                    java.lang.String.valueOf(getPlayerController().getMediaPoster())
                val name: String = getPlayerController().getCurrentVideoName()
                val videoUrl: String = java.lang.String.valueOf(getPlayerController().getVideoUrl())
                val mMediaModel = MediaModel.media(
                    id, null, currentQuality, type, name, videoUrl, artwork, subs,
                    null, null, null,
                    null, null,
                    null,
                    null, null, null, null, null, externalId
                )
                backState(mMediaModel)
            } else if (getPlayerController().getMediaType() == "1") {
                val subs: String =
                    java.lang.String.valueOf(getPlayerController().getMediaSubstitleUrl())
                val id: String = getPlayerController().getVideoID()
                val externalId: String = getPlayerController().getCurrentExternalId()
                val currentQuality: String = getPlayerController().getVideoCurrentQuality()
                val type: String = getPlayerController().getMediaType()
                val artwork: String =
                    java.lang.String.valueOf(getPlayerController().getMediaPoster())
                val name: String = getPlayerController().getCurrentVideoName()
                val videoUrl: String = java.lang.String.valueOf(getPlayerController().getVideoUrl())
                val mMediaModel = MediaModel.media(
                    id, null, currentQuality, type,
                    name, videoUrl, artwork, subs,
                    getPlayerController().getEpID().toInt(),
                    null,
                    getPlayerController().getCurrentEpTmdbNumber(),
                    getPlayerController().nextSeaonsID(),
                    getPlayerController().getEpName(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    externalId
                )
                backState(mMediaModel)
            }
        }
        activityRunning = true
    }

    override fun onPause() {
        super.onPause()
        releaseMoviePlayer()
        updateResumePosition()
        binding.tubitvPlayer.onPause()
        activityRunning = false
    }

    override fun onStop() {
        super.onStop()
        releaseMoviePlayer()
        updateResumePosition()
        binding.tubitvPlayer.onPause()
    }

    ///////////////////////////////////////////////////////////////////////////
    // 在onDestroy之前调用,保存状态
    ///////////////////////////////////////////////////////////////////////////
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (!::mTrackSelector.isInitialized) trackSelectorParameters = mTrackSelector.parameters
        updateResumePosition()
        outState.putParcelable(KEY_TRACK_SELECTOR_PARAMETERS, trackSelectorParameters)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        hideSystemBar()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        hideSystemBar()
    }

    protected open fun setupExo() {
        // 检测wifi状态,是否强制wifi播放
        if (sharedPreferences.getBoolean(WIFI_CHECK, false) && NetworkUtil.isWifiConnected(this)) {
            binding.wifiLayoutWarning.visibility = View.VISIBLE
            binding.backFromWifi.setOnClickListener { v -> onBackPressed() }
        } else {
            initMoviePlayer()
            setCaption(isCaptionPreferenceEnable())
            isInActive = true
            onPlayerReady()
            binding.tubitvPlayer.playerController.triggerSubtitlesToggle(true)
        }
    }

    protected open fun initMoviePlayer() {
        val loadControl =
            DefaultLoadControl.Builder().setBufferDurationsMs(32 * 1024, 64 * 1024, 1024, 1024)
                .createDefaultLoadControl()
        // 3. 创建播放器
        if (sharedPreferences.getBoolean(EXTENTIONS, false)) {
            val renderersFactory: RenderersFactory = MediaHelper.buildRenderersFactory(this, true)
            mTrackSelector = DefaultTrackSelector(this)
            mTrackSelector.setParameters(
                mTrackSelector
                    .buildUponParameters()
                    .setForceHighestSupportedBitrate(true)
                    .setAllowAudioMixedChannelCountAdaptiveness(true)
                    .setExceedRendererCapabilitiesIfNecessary(true)
            )
            mMoviePlayer =
                SimpleExoPlayer.Builder(this, renderersFactory).setTrackSelector(mTrackSelector)
                    .setLoadControl(loadControl).build()
            val mEventLogger = EventLogger(mTrackSelector)
            mMoviePlayer.addAnalyticsListener(mEventLogger)
            mMoviePlayer.addMetadataOutput(mEventLogger)
            binding.tubitvPlayer.setPlayer(mMoviePlayer, this)
            binding.tubitvPlayer.setMediaModel(mediaModel)
        } else {
            val renderersFactory: RenderersFactory = MediaHelper.buildRenderersFactory(this, false)
            mTrackSelector = DefaultTrackSelector(this)
            mTrackSelector.parameters = trackSelectorParameters
            mMoviePlayer =
                SimpleExoPlayer.Builder(this, renderersFactory).setTrackSelector(mTrackSelector)
                    .setLoadControl(loadControl).build()
            val mEventLogger = EventLogger(mTrackSelector)
            mMoviePlayer.addAnalyticsListener(mEventLogger)
            mMoviePlayer.addMetadataOutput(mEventLogger)
            binding.tubitvPlayer.setPlayer(mMoviePlayer, this)
            binding.tubitvPlayer.setMediaModel(mediaModel)
        }
    }

    private fun setCaption(isOn: Boolean) {
        binding.tubitvPlayer.playerController.triggerSubtitlesToggle(isOn)
    }

    protected open fun releaseMoviePlayer() {
        if (::mMoviePlayer.isInitialized) {
            updateResumePosition()
            mMoviePlayer.release()
        }
        isInActive = false
    }

    protected open fun buildMediaSource(model: MediaModel): MediaSource {
        var mediaSource: MediaSource
        @C.ContentType val type = Util.inferContentType(model.mediaUrl, model.mediaExtension)
        mediaSource =
            when (type) {
                C.TYPE_OTHER -> ProgressiveMediaSource.Factory(mMediaDataSourceFactory)
                    .createMediaSource(model.mediaUrl)
                C.TYPE_SS -> SsMediaSource.Factory(mMediaDataSourceFactory)
                    .createMediaSource(model.mediaUrl)
                C.TYPE_DASH -> DashMediaSource.Factory(mMediaDataSourceFactory)
                    .createMediaSource(model.mediaUrl)
                else -> HlsMediaSource.Factory(mMediaDataSourceFactory)
                    .createMediaSource(model.mediaUrl)
            }
        if (model.mediaSubstitleUrl != null) {
            val subtitleSource: MediaSource =
                SingleSampleMediaSource.Factory(mMediaDataSourceFactory)
                    .createMediaSource(
                        model.mediaSubstitleUrl, Format.createTextSampleFormat(
                            null, MimeTypes.TEXT_VTT, null, Format.NO_VALUE,
                            Format.NO_VALUE, "en", null, 0
                        ), C.TIME_UNSET
                    )
            // Plays the video with the sideloaded subtitle.
            mediaSource = MergingMediaSource(mediaSource, subtitleSource)
        }
        return mediaSource
    }

    open fun getPlayerController(): ControlInterface {
        return binding.tubitvPlayer.playerController
    }

    protected abstract fun addUserInteractionView(): View
    protected abstract fun onPlayerReady()
    protected abstract fun updateResumePosition()
    protected abstract fun isCaptionPreferenceEnable(): Boolean
    protected abstract fun playNext(nextVideo: MediaModel)
    protected abstract fun update(update: MediaModel)
    protected abstract fun backState(backState: MediaModel)
    protected abstract fun prepareFSM()
}