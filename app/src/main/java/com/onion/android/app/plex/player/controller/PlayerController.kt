package com.onion.android.app.plex.player.controller

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableDouble
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.Player.TimelineChangeReason
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.video.VideoListener
import com.onion.android.R
import com.onion.android.app.constants.PlexConstants.*
import com.onion.android.app.plex.data.model.media.MediaModel
import com.onion.android.app.plex.data.remote.FsmPlayerApi
import com.onion.android.app.plex.player.enums.ScaleMode
import com.onion.android.app.plex.player.interfaces.ControlInterface
import com.onion.android.app.plex.player.interfaces.PlaybackActionCallback
import com.onion.android.app.plex.player.presenter.ScalePresenter
import com.onion.android.app.plex.player.view.TubiExoPlayerView
import com.onion.android.app.plex.util.PlayerDeviceUtils
import com.onion.android.app.utils.Tools
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class PlayerController : Observable(), ControlInterface, Player.EventListener,
    SeekBar.OnSeekBarChangeListener {

    private val TAG = PlayerController::class.java.simpleName
    var preferences: SharedPreferences? = null
    private var isDraggingSeekBar = false
    private var controlstate = 1


    /**
     * Media action states
     */
    val playerPlaybackState = ObservableInt(Player.STATE_IDLE)
    val isVideoPlayWhenReady = ObservableBoolean(false)

    val currentSpeed = ObservableField("Speed (1x)")


    /**
     * Media informations
     */
    val nextSeasonsID = ObservableField("")


    // Return Media Name
    val serieTvShowName = ObservableField("")

    // Return Media Name
    val videoName = ObservableField("")

    // Return Media Name
    val mediaTypeName = ObservableField("")

    // Return Current Media TMDB Number (EX : 168222)
    val getCurrentMediaTmdbNumber = ObservableField("")


    val getExternalId = ObservableField("")


    // Return Media Genre
    val mediaGenre = ObservableField("")


    // Return Episode Position ( Json )
    val episodePosition = ObservableInt()


    // Return Media Current Stream Link
    val videoCurrentLink = ObservableField<Uri>()


    // Return Media Current Substitle Link
    val videoCurrentSubs = ObservableField("Substitle...")


    // Return Media Current Substitle Link
    val mediaToMyList = ObservableField("Add To MyList")


    // Return Media Current Quality Link
    val videoCurrentQuality = ObservableField("Select Subs..")


    // Return Media ID
    val videoID = ObservableField("")


    // Return Media ID
    val currentEpisodeName = ObservableField("")


    // Return Current Episode Season Number for a Serie or Anime
    val currentSeasonsNumber = ObservableField("")


    // Return Current Episode IMDB Number for a Serie or Anime
    val currentEpisodeImdbNumber = ObservableField("")


    // Return if media Has An ID
    val videoHasID = ObservableField(false)


    // Return if media External Id (TMDB)
    val videoExternalID = ObservableField("")


    // Return Remaining Time for the current Media
    val timeRemaining = ObservableField<String>()


    // Return Media Type
    val mediaType = ObservableField("")


    // Return Media Type
    val mediaPoster = ObservableField<Uri>()


    // Return Media Substile in Uri Format
    val mediaSubstitleUri = ObservableField<Uri>()


    // Return Media Duration
    val mediaDuration = ObservableField(0L)


    // Return Media Current Time ( For SeekBar )
    val mediaCurrentTime = ObservableField(0L)


    // Return Media Current Buffred Position ( For SeekBar )
    val mediaBufferedPosition = ObservableField(0L)

    // Return Media Current Remaining Time in String Format
    val mediaRemainInString = ObservableField("")


    // Return Media Media Position
    val mediaPositionInString = ObservableField("")


    // Return True if the media Has an Active Substitle
    val mediaHasSubstitle = ObservableField(false)


    val lg = ObservableField(false)


    // Return Next Episode Cover
    val nextSerieBackDrop = ObservableField("")


    // Return Current Episode Cover
    val currentMediaCover = ObservableField("")


    // Return True if the media is Ended
    val mediaEnded = ObservableField(false)


    val autoSubstitleActivated = ObservableField(false)


    // Return True if Current Media is a Live Streaming
    val isLive = ObservableField(false)


    // Return True if Current User Has a Premuim Membership
    val isUserPremuim = ObservableField(false)


    // Return Episode Id for a Serie
    val episodeId = ObservableField("4:3")


    // Return Seasons Id for a Serie
    val episodeSeasonsId = ObservableField("")


    // Return Seasons Id for a Serie
    val episodeSeasonsNumber = ObservableField("")


    // Return if Current Media is Premuim
    val mediaPremuim = ObservableInt()


    // Return True if the User has enabled the Substitle
    val isMediaSubstitleEnabled = ObservableField(false)


    val isAutoPlayEnabled = ObservableField(false)


    val isMediaFavoriteEnabled = ObservableField(false)


    val isStreamOnFavorite = ObservableField(false)


    // Return True if the User has enabled the Substitle
    val isMediaCastEnabled = ObservableField(false)


    /**
     * Ad information
     */
    // Return ads Click Url
    val adClickUrl = ObservableField("")


    val adMetaData = ObservableField("")


    // Return Number of Ads Left
    val numberOfAdsLeft = ObservableInt(0)


    // Return True if Current Media is playing an ADS
    val isCurrentAd = ObservableField(false)


    val isSkippable = ObservableField(false)


    // Return True if Current Media has reached a CuePoint
    val isCuePointReached = ObservableField(false)

    // Return AdvertiserName for ADS
    val advertiserName = ObservableField("")


    // Return Ads Remaining Time in String Format
    val adsRemainInString = ObservableField("")

    // Return Ads Skip Time in Seconds
    val adsSkipTimeOffset = ObservableDouble()


    val adsSkipTimeOffsetFinished = ObservableField(false)


    private lateinit var controller: PlayerUIController
    private var mInitVideoAspectRatio = 0f
    private lateinit var mScalePresenter: ScalePresenter
    private val mVideoListener: VideoListener = object : VideoListener {
        override fun onVideoSizeChanged(
            width: Int, height: Int, unappliedRotationDegrees: Int,
            pixelWidthHeightRatio: Float
        ) {
            Timber.d(TAG, "onVideoSizeChanged")
            mInitVideoAspectRatio = if (height == 0) 1f else width * pixelWidthHeightRatio / height
        }

        override fun onRenderedFirstFrame() {
            Timber.d(TAG, "onRenderedFirstFrame")
        }
    }
    private val mProgressUpdateHandler = Handler(Looper.getMainLooper())
    private val mOnControlStateChange: Runnable? = null

    /**
     * the Exoplayer instance which this [PlayerController] is controlling.
     */
    private lateinit var mPlayer: SimpleExoPlayer

    /**
     * this is the current mediaModel being played, it could be a ad or actually video
     */
    private lateinit var mMediaModel: MediaModel
    private lateinit var mPlaybackActionCallback: PlaybackActionCallback
    private val updateProgressAction = Runnable { updateProgress() }
    private lateinit var mTubiExoPlayerView: TubiExoPlayerView


    @Inject
    lateinit var sharedPreferencesEditor: SharedPreferences.Editor


    /**
     * Every time the FsmPlayer change states between
     * AdPlayingState and MoviePlayingState,
     * current controller instance need to update the video instance.
     *
     * @param mediaModel the current video that will be played by the [PlayerController.mPlayer] instance.
     */
    fun setMediaModel(mediaModel: MediaModel, context: Context) {
        preferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        mMediaModel = mediaModel
        //mark flag for ads to movie
        isCurrentAd.set(mMediaModel.isAd)
        mScalePresenter = ScalePresenter(mTubiExoPlayerView.context, this)
        if (mMediaModel.isAd) {
            if (!PlayerDeviceUtils.isTVDevice(context)
                && !TextUtils.isEmpty(mMediaModel.clickThroughUrl)
            ) {
                adClickUrl.set(mMediaModel.clickThroughUrl)
            }
            videoName.set(context.getString(R.string.commercial))
            mediaHasSubstitle.set(false)
        } else {
            if (mediaType.get() == "streaming") {
                isLive.set(true)
            }
            setModelMediaInfo(mediaModel, context)
        }
        if (preferences!!.getBoolean(AUTO_PLAY, true)) {
            isAutoPlayEnabled.set(true)
        } else {
            isAutoPlayEnabled.set(false)
        }
        if (preferences!!.getBoolean(AUTO_PLAY, true)) {
            autoSubstitleActivated.set(true)
        } else {
            autoSubstitleActivated.set(false)
        }
        if (preferences!!.getString(
                FsmPlayerApi.decodeServerMainApi2(),
                FsmPlayerApi.decodeServerMainApi4()
            ) == FsmPlayerApi.decodeServerMainApi4()
        ) {
            lg.set(true)
        } else {
            lg.set(false)
        }
    }


    private fun setModelMediaInfo(mediaModel: MediaModel, context: Context) {
        if (mediaModel.getMediaCover() == null) {
            mediaModel.setMediaCover(SERVER_BASE_URL.toString() + "image/episode")
        }
        if (getMediaType() == "0") {
            mediaTypeName.set(mTubiExoPlayerView.getContext().getString(R.string.lists_movies))
        } else if (getMediaType() == "1") {
            mediaTypeName.set(mTubiExoPlayerView.getContext().getString(R.string.lists_series))
        } else if (getMediaType() == "anime") {
            mediaTypeName.set(mTubiExoPlayerView.getContext().getString(R.string.lists_animes))
        } else {
            mediaTypeName.set(mTubiExoPlayerView.getContext().getString(R.string.lists_streaming))
        }
        if (isStreamOnFavorite.get()!!) {
            mediaToMyList.set("Added")
        } else {
            mediaToMyList.set("Add To MyList")
        }
        if (!TextUtils.isEmpty(mMediaModel.getCurrentTvShowName())) {
            serieTvShowName.set(mMediaModel.getCurrentTvShowName())
        }
        if (!TextUtils.isEmpty(mMediaModel.getMediaName())) {
            videoName.set(mMediaModel.getMediaName())
        }
        if (!TextUtils.isEmpty(mMediaModel.getMediaName())) {
            videoName.set(mMediaModel.getMediaName())
        }
        if (!TextUtils.isEmpty(mMediaModel.getVideoid())) {
            videoID.set(mMediaModel.getVideoid())
            videoHasID.set(true)
        }
        if (mMediaModel.isPremuim != null) {
            mediaPremuim.set(mMediaModel.isPremuim)
        }
        if (!TextUtils.isEmpty(mMediaModel.currentExternalId)) {
            getExternalId.set(mMediaModel.currentExternalId)
        }
        if (!TextUtils.isEmpty(mMediaModel.mediaGenre)) {
            videoExternalID.set(mMediaModel.mediaGenre)
        }
        if (!PlayerDeviceUtils.isTVDevice(context) // Disable artwork for TV
            && mMediaModel.mediaCover != null
        ) {
            mediaPoster.set(mMediaModel.mediaCover)
        }
        if (!TextUtils.isEmpty(mMediaModel.type)) {
            mediaType.set(mMediaModel.type)
        }
        if (!TextUtils.isEmpty(mMediaModel.currentQuality)) {
            videoCurrentQuality.set(mMediaModel.currentQuality)
        }
        videoCurrentLink.set(mediaModel.mediaUrl)
        if (mediaModel.mediaCover != null) {
            currentMediaCover.set(java.lang.String.valueOf(mediaModel.getMediaCover()))
        } else {
            currentMediaCover.set(SERVER_BASE_URL.toString() + "image/episode")
        }
        setMediaInfo2(context)
    }

    private fun setMediaInfo2(context: Context) {
        if (!PlayerDeviceUtils.isTVDevice(context) // Disable artwork for TV
            && mMediaModel.mediaCover != null
        ) {
            mediaPoster.set(mMediaModel.mediaCover)
        }
        if (mMediaModel.mediaSubstitleUrl != null) {
            mediaHasSubstitle.set(true)
            mediaSubstitleUri.set(mMediaModel.mediaSubstitleUrl)
            triggerSubtitlesToggle(true)
        }
        if (!TextUtils.isEmpty(mMediaModel.seasonId)) {
            currentSeasonsNumber.set(mMediaModel.seasonId)
        }
        if (!TextUtils.isEmpty(mMediaModel.epImdb)) {
            currentEpisodeImdbNumber.set(mMediaModel.epImdb)
        }
        if (!TextUtils.isEmpty(mMediaModel.tvSeasonId)) {
            nextSeasonsID.set(mMediaModel.tvSeasonId)
        }
        if (!TextUtils.isEmpty(mMediaModel.currentEpName)) {
            currentEpisodeName.set(mMediaModel.currentEpName)
        }
        if (mMediaModel.epId != null) {
            episodeId.set(java.lang.String.valueOf(mMediaModel.epId))
        }
        if (!TextUtils.isEmpty(mMediaModel.currentSeasonsNumber)) {
            episodeSeasonsId.set(mMediaModel.currentSeasonsNumber)
        }
        if (mMediaModel.episodePostionNumber != null) {
            episodePosition.set(mMediaModel.episodePostionNumber ?: 0)
        }
        if (mMediaModel.currentEpTmdbNumber != null) {
            getCurrentMediaTmdbNumber.set(mMediaModel.currentEpTmdbNumber)
        }
        if (mMediaModel.currentExternalId != null) {
            getExternalId.set(mMediaModel.currentExternalId)
        }
    }


    /**
     * Every time the FsmPlayer change states between
     * AdPlayingState and MoviePlayingState,
     * [PlayerController.mPlayer] instance need to update .
     *
     * @param player the current player that is playing the video
     */
    fun setPlayer(
        player: SimpleExoPlayer, playbackActionCallback: PlaybackActionCallback,
        tubiExoPlayerView: TubiExoPlayerView
    ) {
        if (mPlayer === player) {
            return
        }
        mTubiExoPlayerView = tubiExoPlayerView

        //remove the old listener
        mPlayer.removeListener(this)
        mPlayer = player
        mPlayer.addListener(this)
        mPlayer.addVideoListener(mVideoListener)
        playerPlaybackState.set(mPlayer!!.playbackState)
        mPlaybackActionCallback = playbackActionCallback
        updateProgress()
    }


    fun setAvailableAdLeft(count: Int) {
        numberOfAdsLeft.set(count)
    }

    fun updateTimeTextViews(position: Long, duration: Long) {
        //translate the movie remaining time number into display string, and update the UI
        mediaRemainInString.set(Tools.getProgressTime(duration - position, true))
        adsRemainInString.set(
            mTubiExoPlayerView.context.getString(R.string.up_next) + Tools.getProgressTime(
                duration - position, true
            )
        )
        mediaPositionInString.set(Tools.getProgressTime(position, false))
    }


    /**
     * Get current player control state
     *
     * @return Current control state
     */
    fun getState(): Int {
        return controlstate
    }


    /**
     * Set current player state
     */
    fun setState(state: Int) {
        controlstate = state
        mOnControlStateChange?.run()
    }

    /**
     * Check if it is during custom seek
     *
     * @return True if custom seek is performing
     */
    fun isDuringCustomSeek(): Boolean {
        return controlstate == CUSTOM_SEEK_CONTROL_STATE || controlstate == EDIT_CUSTOM_SEEK_CONTROL_STATE
    }


    override fun setPremuim(premuim: Boolean) {
        if (premuim) {
            isUserPremuim.set(true)
        }
    }


    override fun onAdsPlay(playing: Boolean, isAdsSkippable: Boolean) {
        isCurrentAd.set(playing)
        isSkippable.set(isAdsSkippable)
    }


    override fun triggerSubtitlesToggle(enabled: Boolean) {
        //trigger the hide or show subtitles.
        val subtitles = mTubiExoPlayerView.subtitleView!!
        subtitles.visibility = if (enabled) View.VISIBLE else View.INVISIBLE
        if (mPlaybackActionCallback.isActive()) {
            mPlaybackActionCallback.onSubtitles(mMediaModel, enabled)
        }
        isMediaSubstitleEnabled.set(enabled)
    }

    override fun getPlayerState(): Int {
        return playerPlaybackState.get()
    }

    override fun triggerAutoPlay(enabled: Boolean) {
        isAutoPlayEnabled.set(enabled)
    }

    override fun mediaIsFavorite(enabled: Boolean) {
        isMediaFavoriteEnabled.set(enabled)
    }

    override fun streamMediaIsFavorite(enabled: Boolean) {
        isStreamOnFavorite.set(enabled)
    }

    override fun triggerAddToMyList(enabled: Boolean) {
        mPlaybackActionCallback.onAddMyListMedia(enabled)
    }

    override fun onCheckedChanged(enabled: Boolean) {
        mPlaybackActionCallback.onAutoPlaySwitch(enabled)
    }

    override fun seekBy(millisecond: Long) {
        if (mPlayer == null) {
            Timber.e(TAG, "seekBy() ---> player is empty")
            return
        }
        val currentPosition = mPlayer!!.currentPosition
        var seekPosition = currentPosition + millisecond

        //lower bound
        seekPosition = if (seekPosition < 0) 0 else seekPosition
        //upper bound
        seekPosition = Math.min(seekPosition, mPlayer!!.duration)
        if (mPlaybackActionCallback != null && mPlaybackActionCallback.isActive()) {
            mPlaybackActionCallback.onSeek(mMediaModel, currentPosition, seekPosition)
        }
        seekToPosition(seekPosition)
    }

    override fun onCuePointReached(reached: Boolean) {
        if (reached) {
            isCuePointReached.set(true)
        }
    }


    override fun loadPreview(millisecond: Long, max: Long) {

        //
    }

    override fun seekTo(millisecond: Long) {
        if (mPlaybackActionCallback != null && mPlaybackActionCallback.isActive()) {
            val currentProgress = if (mPlayer != null) mPlayer!!.currentPosition else 0
            mPlaybackActionCallback.onSeek(mMediaModel, currentProgress, millisecond)
        }
        seekToPosition(millisecond)
        loadPreview(millisecond, millisecond)
    }

    override fun isSubtitleEnabled(enabled: Boolean) {
        isMediaSubstitleEnabled.get()
    }

    override fun subtitleCurrentLang(lang: String) {
        videoCurrentSubs.set(lang)
    }


    override fun isMediaPremuim(): Int {
        return mediaPremuim.get()!!
    }


    override fun hasSubsActive(): Boolean {
        return mediaHasSubstitle.get()!!
    }

    override fun triggerPlayOrPause(setPlay: Boolean) {
        mPlayer.playWhenReady = setPlay
        isVideoPlayWhenReady.set(setPlay)
        if (mPlaybackActionCallback.isActive()) {
            mPlaybackActionCallback.onPlayToggle(mMediaModel, setPlay)
        }
    }


    /**
     * Change Video Scale
     */
    override fun scale() {
        mScalePresenter.doScale()
        val scaleMode: ScaleMode = mScalePresenter.getCurrentScaleMode()
        Toast.makeText(mTubiExoPlayerView.context, "" + scaleMode.description, Toast.LENGTH_SHORT)
            .show()
    }

    override fun onLoadEpisodes() {
        mPlaybackActionCallback.onLoadEpisodes()
    }

    override fun onLoadStreaming() {
        mPlaybackActionCallback.onLoadSteaming()
    }

    /**
     * Return Movie or SERIE  Name
     */
    override fun getCurrentVideoName(): String {
        return videoName.get()!!
    }


    override fun getCurrentEpisodePosition(): Int {
        return episodePosition.get()
    }


    override fun getEpID(): String {
        return episodeId.get()!!
    }


    /**
     * Get Next Season ID for TV-SERIE
     */
    override fun nextSeaonsID(): String {
        return nextSeasonsID.get()!!
    }


    /**
     * Get Current Season
     */
    override fun getCurrentSeason(): String {
        return episodeSeasonsId.get()!!
    }

    override fun getCurrentSeasonNumber(): String {
        return episodeSeasonsNumber.get()!!
    }


    /**
     * Get Current Video Quality (Servers)
     */
    override fun getVideoCurrentQuality(): String {
        return videoCurrentQuality.get()!!
    }


    /**
     * Get Episode Name
     */
    override fun getEpName(): String {
        return currentEpisodeName.get()!!
    }


    override fun getSeaonNumber(): String {
        return currentSeasonsNumber.get()!!
    }

    override fun getCurrentTvShowName(): String {
        return serieTvShowName.get()!!
    }

    override fun getCurrentExternalId(): String {
        return getExternalId.get()!!
    }


    /**
     * Get Movie or TV ID
     */
    override fun getVideoID(): String {
        return videoID.get()!!
    }


    override fun getMediaSubstitleName(): String {
        return videoExternalID.get()!!
    }


    /**
     * Get Media Stream Link
     */
    override fun getVideoUrl(): Uri {
        return videoCurrentLink.get()!!
    }

    override fun getMediaSubstitleUrl(): Uri {
        return mediaSubstitleUri.get()!!
    }


    /**
     * Get Media Poster
     */
    override fun getMediaPoster(): Uri {
        return mediaPoster.get()!!
    }

    override fun getCurrentSpeed(speed: String) {
        currentSpeed.set(speed)
    }


    /**
     * Get Media Type
     */
    override fun getMediaType(): String {
        return mediaType.get()!!
    }

    override fun getCurrentEpTmdbNumber(): String {
        return getCurrentMediaTmdbNumber.get()!!
    }


    /**
     * return Media or ad
     */
    override fun isCurrentVideoAd(): Boolean {
        return isCurrentAd.get()!!
    }

    override fun isCurrentSubstitleAuto(enabled: Boolean) {
        autoSubstitleActivated.set(enabled)
    }


    override fun onTracksVideo() {
        mPlaybackActionCallback.onTracksVideo()
    }

    override fun onTracksAudio() {
        mPlaybackActionCallback.onTracksAudio()
    }

    override fun clickPlaybackSetting() {
        if (mPlaybackActionCallback == null) {
            Timber.w(TAG, "clickPlaybackSetting params is null")
            return
        }
        mPlaybackActionCallback.onLoadPlaybackSetting()
    }

    override fun onTracksSubstitles() {
        mPlaybackActionCallback.onTracksSubstitles()
    }

    override fun onLoadFromBeginning() {
        mPlaybackActionCallback.onLoadFromBeginning()
    }


    override fun onLoadSide() {}


    /**
     * Get Media Qualities (480p,720p,1080p,4k)
     */
    override fun clickQualitySettings() {
        if (getVideoID()!!.isEmpty()) {
            Toast.makeText(
                mTubiExoPlayerView.getContext(),
                "there is no quantity available",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            mPlaybackActionCallback.onLoadQualities()
        }
    }

    /**
     * Release Player
     */
    override fun closePlayer() {
        (mTubiExoPlayerView.context as Activity).onBackPressed()
    }


    // Return Movies List
    override fun loadMoviesList() {
        mPlaybackActionCallback.onLoadMoviesList()
    }

    override fun loadSeriesList() {
        mPlaybackActionCallback.onLoadloadSeriesList()
    }


    // Return Next Episode for TV-Serie
    override fun nextEpisode() {
        mPlaybackActionCallback.onLoadNextEpisode()
    }


    override fun isPlayWhenReady(): Boolean {
        return isVideoPlayWhenReady.get()
    }

    override fun onMediaDownload() {
        mPlaybackActionCallback.onMediaDownload()
    }


    override fun addToMyList(enabled: Boolean) {
        mPlaybackActionCallback.onAddMyList(enabled)
    }

    override fun checkFavorite(favorite: Boolean) {
        isStreamOnFavorite.set(favorite)
    }


    // Substitles
    override fun clickOnSubs() {
        mPlaybackActionCallback.onSubtitlesSelection()
    }

    fun getController(): PlayerUIController {
        return controller
    }

    fun setController(controller: PlayerUIController) {
        this.controller = controller
    }

    //------------------------------player playback listener-------------------------------------------//
    override fun onTimelineChanged(timeline: Timeline, @TimelineChangeReason reason: Int) {
        setPlaybackState()
        updateProgress()
    }

    override fun onPositionDiscontinuity(reason: Int) {
        setPlaybackState()
        updateProgress()
    }


    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        playerPlaybackState.set(playbackState)
        isVideoPlayWhenReady.set(playWhenReady)
        updateProgress()
        if (playbackState == Player.STATE_ENDED) {
            mPlaybackActionCallback.onMediaEnded()
            mMediaModel.mediaSource = null
        }
    }

    override fun onRepeatModeChanged(repeatMode: Int) {


        //
    }

    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {


        //
    }

    override fun onTracksChanged(
        trackGroups: TrackGroupArray,
        trackSelections: TrackSelectionArray
    ) {


        //
    }

    override fun onLoadingChanged(isLoading: Boolean) {
        Timber.i(TAG, "onLoadingChanged")
    }

    override fun onPlayerError(error: ExoPlaybackException) {
        mPlaybackActionCallback.onInitPlayer()
    }


    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {
        Timber.d(TAG, "onPlaybackParametersChanged")
    }

    override fun onSeekProcessed() {
        Timber.d(TAG, "onSeekProcessed")
    }

    //-----------------------------------------SeekBar listener--------------------------------------------------------------//
    override fun setVideoAspectRatio(widthHeightRatio: Float) {
        mTubiExoPlayerView.setAspectRatio(widthHeightRatio)
        Timber.i(TAG, "setVideoAspectRatio $widthHeightRatio")
    }


    override fun getInitVideoAspectRatio(): Float {
        Timber.i(TAG, "getInitVideoAspectRatio $mInitVideoAspectRatio")
        return mInitVideoAspectRatio
    }

    override fun setResizeMode(resizeMode: Int) {
        mTubiExoPlayerView.resizeMode = resizeMode
    }


    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (fromUser) {
            val position: Long = Tools.progressToMilli(mPlayer!!.duration, seekBar)
            val duration: Long
            duration = mPlayer!!.duration
            updateTimeTextViews(position, duration)
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
        isDraggingSeekBar = true
        Timber.i(TAG, "onStartTrackingTouch")
    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
        seekTo(Tools.progressToMilli(mPlayer.duration, seekBar))
        isDraggingSeekBar = false
        Timber.i(TAG, "onStopTrackingTouch")
    }

    //---------------------------------------private method---------------------------------------------------------------------------//
    private fun setPlaybackState() {
        val playBackState = mPlayer.playbackState
        playerPlaybackState.set(playBackState)
    }

    private fun seekToPosition(positionMs: Long) {
        mPlayer.seekTo(mPlayer.currentWindowIndex, positionMs)
    }

    private fun updateProgress() {
        val position = mPlayer.currentPosition
        val duration = mPlayer.duration
        val bufferedPosition = mPlayer.bufferedPosition
        //only update the seekBar UI when user are not interacting, to prevent UI interference
        if (!isDraggingSeekBar && !isDuringCustomSeek()) {
            updateSeekBar(position, duration, bufferedPosition)
            updateTimeTextViews(position, duration)
        }
        Timber.i(TAG, "updateProgress:----->" + mediaCurrentTime.get())
        if (mPlaybackActionCallback.isActive()) {
            mPlaybackActionCallback.onProgress(mMediaModel, position, duration)
        } else return
        mProgressUpdateHandler.removeCallbacks(updateProgressAction)
        // Schedule an update if necessary.
        if (!(playerPlaybackState.get() == Player.STATE_IDLE || playerPlaybackState.get() == Player.STATE_ENDED || !mPlaybackActionCallback.isActive())) {
            //don't post the updateProgress event when user pause the video
            if (!mPlayer.playWhenReady) return
            val delayMs = DEFAULT_FREQUENCY
            mProgressUpdateHandler.postDelayed(updateProgressAction, delayMs)
        }
    }

    private fun updateSeekBar(position: Long, duration: Long, bufferedPosition: Long) {
        //update progressBar.
        mediaCurrentTime.set(position)
        mediaDuration.set(duration)
        mediaBufferedPosition.set(bufferedPosition)
    }
}