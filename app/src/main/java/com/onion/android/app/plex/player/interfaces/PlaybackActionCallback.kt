package com.onion.android.app.plex.player.interfaces

import com.onion.android.app.plex.data.model.media.MediaModel

interface PlaybackActionCallback {

    fun onProgress(mediaModel: MediaModel, milliseconds: Long, durationMillis: Long)

    fun onSeek(mediaModel: MediaModel, oldPositionMillis: Long, newPositionMillis: Long)

    fun onPlayToggle(mediaModel: MediaModel, playing: Boolean)

    fun onSubtitles(mediaModel: MediaModel, enabled: Boolean)

    fun onSubtitlesSelection()

    fun onMediaEnded()

    fun onLoadEpisodes()

    fun onLoadNextEpisode()

    fun onLoadloadSeriesList()

    fun onLoadloadAnimesList()

    fun onMediaDownload()

    fun onLoadPlaybackSetting()

    fun onLoadSteaming()

    fun onLoadMoviesList()

    fun onInitPlayer()

    fun onLoadFromBeginning()

    fun onAddMyList(enabled: Boolean)


    fun onAutoPlaySwitch(enabled: Boolean)

    fun onAddMyListMedia(enabled: Boolean)

    fun onOpenSubsLoad()

    fun onTracksVideo()

    fun onTracksAudio()

    fun onTracksSubstitles()

    fun onLoadQualities()

    fun onCuePointReceived(cuePoints: LongArray?)

    fun isActive(): Boolean

    fun isPremuim(): Boolean

}