package com.onion.android.app.plex.ui.player

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.onion.android.app.constants.PlexConstants.ARG_MOVIE
import com.onion.android.app.constants.PlexConstants.FSMPLAYER_TESTING
import com.onion.android.app.plex.data.local.entity.History
import com.onion.android.app.plex.data.local.entity.Media
import com.onion.android.app.plex.data.local.entity.Stream
import com.onion.android.app.plex.data.model.media.MediaModel
import com.onion.android.app.plex.data.model.media.Resume
import com.onion.android.app.plex.data.remote.FsmPlayerApi
import com.onion.android.app.plex.data.repository.MediaRepository
import com.onion.android.app.plex.manager.AuthManager
import com.onion.android.app.plex.manager.SettingsManager
import com.onion.android.app.plex.player.controller.PlayerController
import com.onion.android.app.plex.player.controller.PlayerUIController
import com.onion.android.app.plex.player.view.UIControllerView
import com.onion.android.app.utils.Tools
import dagger.android.AndroidInjection
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class MainPlayerActivity : BasePlayerActivity() {

    val TAG = this::class.java.simpleName
    private lateinit var mCountDownTimer: CountDownTimer
    private lateinit var mMediaModel: MediaModel
    private lateinit var random: Random
    private lateinit var media: Media
    private lateinit var stream: Stream
    private lateinit var mediaModel: MediaModel
    val compositeDisposable = CompositeDisposable()

    private val FIRSTPAGE = 1
    private val adsLaunched = false

    @Inject
    lateinit var settingsManager: SettingsManager

    @Inject
    lateinit var authManager: AuthManager

    @Inject
    lateinit var repository: MediaRepository

    @Inject
    lateinit var playerUIController: PlayerUIController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        media = intent.getParcelableExtra(ARG_MOVIE)!!

        stream = Stream(
            media.getId(),
            "streaming",
            media.posterPath,
            media.title,
            media.backdropPath,
            media.link
        )
        media = History(
            media.getId(),
            media.getTmdbId(),
            media.posterPath,
            media.title,
            media.backdropPath,
            media.link
        )

        if (sharedPreferences.getString(
                FsmPlayerApi.decodeServerMainApi2(),
                FsmPlayerApi.decodeServerMainApi4()
            ).equals(FsmPlayerApi.decodeServerMainApi4())
        ) {
            binding.viewStatus.visibility = View.VISIBLE
            binding.restartApp.setOnClickListener {
                binding.viewStatus.visibility = View.GONE
            }
            binding.closeStatus.setOnClickListener {
                binding.viewStatus.visibility = View.GONE
            }
            binding.viewStatus.setOnTouchListener { _, _ ->
                binding.viewStatus.visibility = View.GONE
                false
            }
        }

        getPlayerController().isCurrentSubstitleAuto(settingsManager.settings.autosubstitles == 1)
    }

    override fun initMoviePlayer() {
        super.initMoviePlayer()
        mediaModel.mediaSource = buildMediaSource(mediaModel)
        // 加载视频流
        onLoadMovieResume(getPlayerController().getMediaType())
    }

    private fun onLoadMovieResume(type: String) {
        var id = ""
        when (type) {
            "0" -> {
                id = getPlayerController().getVideoID()
            }
            "1", "anime" -> {
                id = getPlayerController().getCurrentEpTmdbNumber()
            }
        }
        repository.getResumeById(id, settingsManager.settings.apiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Resume> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(resume: Resume) {
                    mMoviePlayer.seekTo(0)
                }

                @SuppressLint("ClickableViewAccessibility")
                override fun onError(e: Throwable) {
                    mMoviePlayer.seekTo(0)
                }

                override fun onComplete() {}
            })
    }


    override fun onProgress(mediaModel: MediaModel, milliseconds: Long, durationMillis: Long) {
        Timber.v(
            TAG,
            mediaModel.mediaName.toString() + ": " + mediaModel.toString() + " onProgress: " + "milliseconds: " + milliseconds + " durationMillis: " + durationMillis
        )
    }

    override fun onSeek(mediaModel: MediaModel, oldPositionMillis: Long, newPositionMillis: Long) {
        TODO("Not yet implemented")
    }

    override fun onPlayToggle(mediaModel: MediaModel, playing: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onSubtitles(mediaModel: MediaModel, enabled: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onSubtitlesSelection() {
        TODO("Not yet implemented")
    }

    override fun onMediaEnded() {
        TODO("Not yet implemented")
    }

    override fun onLoadEpisodes() {
        TODO("Not yet implemented")
    }

    override fun onLoadNextEpisode() {
        TODO("Not yet implemented")
    }

    override fun onLoadloadSeriesList() {
        TODO("Not yet implemented")
    }

    override fun onLoadloadAnimesList() {
        TODO("Not yet implemented")
    }

    override fun onMediaDownload() {
        TODO("Not yet implemented")
    }

    override fun onLoadPlaybackSetting() {
        TODO("Not yet implemented")
    }

    override fun onLoadSteaming() {
        TODO("Not yet implemented")
    }

    override fun onLoadMoviesList() {
        TODO("Not yet implemented")
    }

    override fun onInitPlayer() {
        TODO("Not yet implemented")
    }

    override fun onLoadFromBeginning() {
        TODO("Not yet implemented")
    }

    override fun onAddMyList(enabled: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onAutoPlaySwitch(enabled: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onAddMyListMedia(enabled: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onOpenSubsLoad() {
        TODO("Not yet implemented")
    }

    override fun onTracksVideo() {
        TODO("Not yet implemented")
    }

    override fun onTracksAudio() {
        TODO("Not yet implemented")
    }

    override fun onTracksSubstitles() {
        TODO("Not yet implemented")
    }

    override fun onLoadQualities() {
        TODO("Not yet implemented")
    }

    override fun onCuePointReceived(cuePoints: LongArray?) {
        TODO("Not yet implemented")
    }

    override fun isPremuim(): Boolean {
        TODO("Not yet implemented")
    }

    override fun addUserInteractionView(): View =
        UIControllerView(this).setPlayerController(getPlayerController() as PlayerController)

    ///////////////////////////////////////////////////////////////////////////
    // 播放准备
    ///////////////////////////////////////////////////////////////////////////
    override fun onPlayerReady() {}

    override fun updateResumePosition() {
        //keep track of movie player's position when activity resume back
        if (mMoviePlayer.playbackState != ExoPlayer.STATE_IDLE) {
            val resumeWindow = mMoviePlayer.currentWindowIndex
            val resumePosition =
                if (mMoviePlayer.isCurrentWindowSeekable) Math.max(0, mMoviePlayer.currentPosition)
                else C.TIME_UNSET
            playerUIController.setMovieResumeInfo(resumeWindow, resumePosition)
            Timber.i(FSMPLAYER_TESTING, resumePosition.toString() + "")
        }
        if (getPlayerController().getVideoID().isNotEmpty() && getPlayerController().getMediaType()
                .isNotEmpty() && mMoviePlayer.playbackState != Player.STATE_IDLE
        ) {
            val resumeWindow = mMoviePlayer.currentWindowIndex
            val videoDuration = mMoviePlayer.duration.toInt()
            val resumePosition = (
                    if (mMoviePlayer.isCurrentWindowSeekable) Math.max(
                        0,
                        mMoviePlayer.currentPosition
                    )
                    else C.TIME_UNSET).toInt()
            if (getPlayerController().getMediaType() == "0") {
                repository.getResumeMovie(
                    settingsManager.settings.apiKey,
                    authManager.userInfo.id, getPlayerController().getVideoID(),
                    resumeWindow, resumePosition, videoDuration, Tools.id(baseContext)
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {}
            } else {
                repository.getResumeMovie(
                    settingsManager.settings.apiKey,
                    authManager.userInfo.id, getPlayerController().getCurrentEpTmdbNumber(),
                    resumeWindow, resumePosition, videoDuration, Tools.id(baseContext)
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {}
            }
            Timber.i(FSMPLAYER_TESTING, resumePosition.toString() + "")
        }

    }

    override fun isCaptionPreferenceEnable() = true

    override fun playNext(nextVideo: MediaModel) {
        mediaModel.mediaSource = buildMediaSource(mediaModel)
    }

    override fun update(update: MediaModel) {
        mediaModel.mediaSource = buildMediaSource(mediaModel)
    }

    override fun backState(backState: MediaModel) {
        mediaModel.mediaSource = buildMediaSource(mediaModel)
    }

    override fun prepareFSM() {
        TODO("Not yet implemented")
    }
}