package com.onion.android.app.plex.player.interfaces

import android.net.Uri
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout.ResizeMode

interface ControlInterface {
    // on Ads Play (Hide some part of the player)
    fun onAdsPlay(playing: Boolean, isSkippable: Boolean)

    // Return if media is an Ad or Movie
    fun isCurrentVideoAd(): Boolean

    fun isCurrentSubstitleAuto(enabled: Boolean)

    fun isPlayWhenReady(): Boolean


    fun onMediaDownload()

    fun onTracksVideo()

    fun onTracksAudio()

    fun clickPlaybackSetting()

    fun onTracksSubstitles()

    fun onLoadSide()

    fun onLoadFromBeginning()

    fun addToMyList(enabled: Boolean)

    fun checkFavorite(favorite: Boolean)


    // Player Control
    fun triggerSubtitlesToggle(enabled: Boolean)

    fun getPlayerState(): Int

    fun triggerAutoPlay(enabled: Boolean)


    fun mediaIsFavorite(enabled: Boolean)

    fun streamMediaIsFavorite(enabled: Boolean)


    fun triggerAddToMyList(enabled: Boolean)

    fun onCheckedChanged(enabled: Boolean)


    fun seekBy(millisecond: Long)


    fun onCuePointReached(reached: Boolean)

    fun seekTo(millisecond: Long)

    fun closePlayer()

    fun isSubtitleEnabled(enabled: Boolean)


    fun subtitleCurrentLang(lang: String)


    fun hasSubsActive(): Boolean

    fun loadPreview(millisecond: Long, max: Long)

    fun triggerPlayOrPause(setPlay: Boolean)


    fun scale()

    fun clickQualitySettings()

    fun clickOnSubs()


    // Series

    // Series
    fun onLoadEpisodes()

    fun onLoadStreaming()

    fun nextEpisode()

    fun isMediaPremuim(): Int


    // Movies
    fun loadMoviesList()

    fun loadSeriesList()

    // Display Media Info
    fun getCurrentVideoName(): String

    fun getCurrentEpisodePosition(): Int

    fun getVideoID(): String


    fun getMediaSubstitleName(): String

    fun getVideoUrl(): Uri

    fun getMediaSubstitleUrl(): Uri

    fun getMediaPoster(): Uri

    fun getCurrentSpeed(speed: String)

    fun getMediaType(): String

    fun getCurrentEpTmdbNumber(): String

    fun getEpID(): String

    fun getVideoCurrentQuality(): String

    fun nextSeaonsID(): String

    fun getCurrentSeason(): String

    fun getCurrentSeasonNumber(): String

    fun getEpName(): String

    fun getSeaonNumber(): String

    fun getCurrentTvShowName(): String

    fun getCurrentExternalId(): String

    fun setVideoAspectRatio(widthHeightRatio: Float)

    fun getInitVideoAspectRatio(): Float

    fun setResizeMode(@ResizeMode resizeMode: Int)

    fun setPremuim(premuim: Boolean)
}