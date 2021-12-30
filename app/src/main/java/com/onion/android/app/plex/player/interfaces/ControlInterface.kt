package com.onion.android.app.plex.player.interfaces

import com.google.android.exoplayer2.ui.AspectRatioFrameLayout.ResizeMode

interface ControlInterface {

    fun isMediaPlayerError(): Boolean


    // Player Control
    fun triggerSubtitlesToggle(enabled: Boolean)


    fun getVideoID(): String


    fun getMediaType(): String

    fun getCurrentEpTmdbNumber(): String


    fun setVideoAspectRatio(widthHeightRatio: Float)

    fun getInitVideoAspectRatio(): Float

    fun setResizeMode(@ResizeMode resizeMode: Int)

}