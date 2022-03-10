package com.onion.android.app.plex.player.controller

import android.view.View
import android.webkit.WebView
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.SimpleExoPlayer

class PlayerUIController {

    private lateinit var contentPlayer: SimpleExoPlayer

    private lateinit var vapidWebView: WebView

    private lateinit var exoPlayerView: View


    var movieResumeWindow = C.INDEX_UNSET

    var movieResumePosition = C.TIME_UNSET

    var hasHistory = false

    var historyPosition = C.TIME_UNSET

    constructor()

    constructor(
        contentPlayer: SimpleExoPlayer, adPlayer: SimpleExoPlayer,
        vapidWebView: WebView, exoPlayerView: View
    ) {
        this.contentPlayer = contentPlayer
        this.vapidWebView = vapidWebView
        this.exoPlayerView = exoPlayerView
    }

    fun getContentPlayer(): SimpleExoPlayer = contentPlayer

    fun getExoPlayerView(): View = exoPlayerView

    fun getVapidWebView(): WebView = vapidWebView

    fun setVapidWebView(vapidWebView: WebView) {
        this.vapidWebView = vapidWebView
    }

    fun setContentPlayer(contentPlayer: SimpleExoPlayer) {
        this.contentPlayer = contentPlayer
    }

    fun setExoPlayerView(exoPlayerView: View) {
        this.exoPlayerView = exoPlayerView
    }

    // ------------------------------------------------------------------------
    // 当用户想要从当前位置开始电影时设
    // ------------------------------------------------------------------------
    fun setPlayFromHistory(pos: Long) {
        hasHistory = true
        historyPosition = pos
    }


    fun clearHistoryRecord() {
        hasHistory = false
        historyPosition = C.TIME_UNSET
    }

    fun setMovieResumeInfo(window: Int, position: Long) {
        movieResumeWindow = window
        movieResumePosition = position
    }

    fun clearMovieResumeInfo() {
        setMovieResumeInfo(C.INDEX_UNSET, C.TIME_UNSET)
    }
}