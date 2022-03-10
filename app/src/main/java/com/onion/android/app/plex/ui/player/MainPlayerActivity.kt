package com.onion.android.app.plex.ui.player

import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.Player
import com.onion.android.app.plex.data.model.media.Resume
import com.onion.android.app.plex.data.repository.MediaRepository
import com.onion.android.app.plex.manager.AuthManager
import com.onion.android.app.plex.manager.SettingsManager
import com.onion.android.app.plex.player.controller.PlayerController
import com.onion.android.app.plex.player.controller.PlayerUIController
import com.onion.android.app.plex.player.view.UIControllerView
import com.onion.android.app.utils.Tools
import com.onion.android.kotlin.extension.isNotNull
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import kotlin.math.max

class MainPlayerActivity : BasePlayerActivity() {

    @Inject
    lateinit var playerUIController: PlayerUIController

    @Inject
    lateinit var settingsManager: SettingsManager

    @Inject
    lateinit var authManager: AuthManager

    @Inject
    lateinit var repository: MediaRepository

    private lateinit var resume: Resume

    private val compositeDisposable = CompositeDisposable()

    override fun addUserInteractionView() =
        UIControllerView(baseContext).setPlayerController(getPlayerController() as PlayerController);


    override fun onPlayerReady() {
        preparePlayerController()
    }

    private fun preparePlayerController() {
        playerUIController.setContentPlayer(moviePlayer)
        playerUIController.setExoPlayerView(binding.tubitvPlayer)
        playerUIController.setVapidWebView(binding.vpaidWebview)

    }

    // ------------------------------------------------------------------------
    // 更新播放进度
    // ------------------------------------------------------------------------
    override fun updateResumePosition() {
        if (getPlayerController().isMediaPlayerError()) return
        // 当活动恢复时跟踪电影播放器的进度
        if (moviePlayer.playbackState != Player.STATE_IDLE) {
            val resumeWindow = moviePlayer.currentWindowIndex
            val resumePosition =
                if (moviePlayer.isCurrentWindowSeekable) max(0, moviePlayer.currentPosition)
                else C.TIME_UNSET
            playerUIController.setMovieResumeInfo(resumeWindow, resumePosition)
        }
        if (getPlayerController().getVideoID().isNotNull()
            && getPlayerController().getMediaType().isNotNull()
            && moviePlayer.playbackState != Player.STATE_IDLE
            && moviePlayer.playbackState != Player.STATE_ENDED
        ) {
            val resumeWindow = moviePlayer.currentWindowIndex
            val videoDuration = moviePlayer.duration.toInt()
            val resumePosition =
                if (moviePlayer.isCurrentWindowSeekable) max(0, moviePlayer.currentPosition)
                else C.TIME_UNSET
            if (getPlayerController().getMediaType() == "0") {
                if (settingsManager.settings.resumeOffline == 1) {
                    resume = Resume(getPlayerController().getVideoID())
                    resume.tmdb = getPlayerController().getVideoID()
                    resume.deviceId = Tools.id(baseContext)
                    resume.movieDuration = videoDuration
                    resume.resumePosition = resumePosition.toInt()
                    resume.userResumeId = authManager.userInfo.id
                    resume.resumeWindow = resumeWindow
                    compositeDisposable.add(Completable.fromAction {
                        repository.addResume(resume)
                    }.subscribeOn(Schedulers.io()).subscribe())
                } else {
                    compositeDisposable.add(
                        repository.getResumeMovie(
                            settingsManager.settings.apiKey,
                            authManager.userInfo.id,
                            getPlayerController().getCurrentEpTmdbNumber(),
                            resumeWindow, resumePosition.toInt(), videoDuration,
                            Tools.id(baseContext)
                        ).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()).subscribe {}
                    )
                }
            } else {
                if (settingsManager.settings.resumeOffline == 1) {
                    resume = Resume(getPlayerController().getCurrentEpTmdbNumber())
                    resume.tmdb = getPlayerController().getCurrentEpTmdbNumber()
                    resume.deviceId = Tools.id(baseContext)
                    resume.movieDuration = videoDuration
                    resume.resumePosition = resumePosition.toInt()
                    resume.userResumeId = authManager.userInfo.id
                    resume.resumeWindow = resumeWindow
                    compositeDisposable.add(Completable.fromAction {
                        repository.addResume(resume)
                    }.subscribeOn(Schedulers.io()).subscribe())
                } else {
                    compositeDisposable.add(
                        repository.getResumeMovie(
                            settingsManager.settings.apiKey,
                            authManager.userInfo.id,
                            getPlayerController().getCurrentEpTmdbNumber(),
                            resumeWindow, resumePosition.toInt(), videoDuration,
                            Tools.id(baseContext)
                        ).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()).subscribe {}
                    )
                }
            }
        }
    }


    // ------------------------------------------------------------------------
    // 是否显示字幕
    // ------------------------------------------------------------------------
    override fun isCaptionPreferenceEnable() = true
}