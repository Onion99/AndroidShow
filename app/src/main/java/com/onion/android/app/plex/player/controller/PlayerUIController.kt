package com.onion.android.app.plex.player.controller

import android.view.View
import android.webkit.WebView
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.SimpleExoPlayer
import com.onion.android.app.plex.util.PlayerDeviceUtils

class PlayerUIController {

    var isPlayingAds = false

    private lateinit var contentPlayer: SimpleExoPlayer

    private lateinit var adPlayer: SimpleExoPlayer

    private lateinit var vpaidWebView: WebView

    private lateinit var exoPlayerView: View

    private var adResumeWindow = C.INDEX_UNSET

    private var adResumePosition = C.TIME_UNSET

    private var movieResumeWindow = C.INDEX_UNSET

    private var movieResumePosition = C.TIME_UNSET

    private var hasHistory = false

    private var historyPosition = C.TIME_UNSET

    constructor()

    constructor(
        contentPlayer: SimpleExoPlayer,
        adPlayer: SimpleExoPlayer,
        vpaidWebView: WebView,
        exoPlayerView: View
    ) {
        this.contentPlayer = contentPlayer
        this.adPlayer = adPlayer
        this.vpaidWebView = vpaidWebView
        this.exoPlayerView = exoPlayerView
    }

    fun getContentPlayer(): SimpleExoPlayer {
        return contentPlayer
    }

    fun setContentPlayer(contentPlayer: SimpleExoPlayer) {
        this.contentPlayer = contentPlayer
    }

    fun getAdPlayer(): SimpleExoPlayer {
        // We'll reuse content player to play ads for single player instance case
        return if (PlayerDeviceUtils.useSinglePlayer()) {
            contentPlayer
        } else adPlayer
    }

    fun setAdPlayer(adPlayer: SimpleExoPlayer) {
        this.adPlayer = adPlayer
    }

    fun getVpaidWebView(): WebView {
        return vpaidWebView
    }

    fun setVpaidWebView(vpaidWebView: WebView) {
        this.vpaidWebView = vpaidWebView
    }

    fun getExoPlayerView(): View? {
        return exoPlayerView
    }

    fun setExoPlayerView(exoPlayerView: View) {
        this.exoPlayerView = exoPlayerView
    }

    /**
     * This is set when user want to begin the movie from current position
     *
     * @param pos
     */
    fun setPlayFromHistory(pos: Long) {
        hasHistory = true
        historyPosition = pos
    }

    fun hasHistory(): Boolean {
        return hasHistory
    }

    fun getHistoryPosition(): Long {
        return historyPosition
    }

    fun clearHistoryRecord() {
        hasHistory = false
        historyPosition = C.TIME_UNSET
    }

    private fun setAdResumeInfo(window: Int, position: Long) {
        adResumeWindow = window
        adResumePosition = position
    }

    fun clearAdResumeInfo() {
        setAdResumeInfo(C.INDEX_UNSET, C.TIME_UNSET)
    }

    fun setMovieResumeInfo(window: Int, position: Long) {
        movieResumeWindow = window
        movieResumePosition = position
    }

    fun clearMovieResumeInfo() {
        setMovieResumeInfo(C.INDEX_UNSET, C.TIME_UNSET)
    }

    fun getAdResumeWindow(): Int {
        return adResumeWindow
    }

    fun getAdResumePosition(): Long {
        return adResumePosition
    }

    fun getMovieResumeWindow(): Int {
        return movieResumeWindow
    }

    fun getMovieResumePosition(): Long {
        return movieResumePosition
    }

    class Builder {
        private lateinit var contentPlayer: SimpleExoPlayer
        private lateinit var adPlayer: SimpleExoPlayer
        private lateinit var vpaidWebView: WebView
        private lateinit var exoPlayerView: View
        fun setContentPlayer(contentPlayer: SimpleExoPlayer): Builder {
            this.contentPlayer = contentPlayer
            return this
        }

        fun setAdPlayer(adPlayer: SimpleExoPlayer): Builder {
            this.adPlayer = adPlayer
            return this
        }

        fun setVpaidWebView(vpaidWebView: WebView): Builder {
            this.vpaidWebView = vpaidWebView
            return this
        }

        fun setExoPlayerView(exoPlayerView: View): Builder {
            this.exoPlayerView = exoPlayerView
            return this
        }

        fun build(): PlayerUIController {
            return PlayerUIController(contentPlayer, adPlayer, vpaidWebView, exoPlayerView)
        }
    }
}