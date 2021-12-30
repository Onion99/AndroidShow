package com.onion.android.app.plex.player.controller

import android.content.Context
import android.view.View
import android.widget.SeekBar
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.onion.android.app.plex.data.model.media.MediaModel
import com.onion.android.app.plex.player.interfaces.ControlInterface
import com.onion.android.app.plex.player.interfaces.PlaybackActionCallback
import com.onion.android.app.plex.player.view.ExoPlayerView
import java.util.*

class PlayerController : Observable(), ControlInterface, Player.EventListener,
    SeekBar.OnSeekBarChangeListener {

    // 是否播放错误
    private val isPlayerError = ObservableBoolean(false)

    // 返回媒体
    val videoID = ObservableField("")

    private lateinit var exoPlayerView: ExoPlayerView
    private lateinit var moviePlayer: SimpleExoPlayer
    private lateinit var actionCallback: PlaybackActionCallback
    private lateinit var mediaModel: MediaModel


    // ------------------------------------------------------------------------
    // 每次 FsmPlayer 在 AdPlayingState 和 MoviePlayingState 之间改变状态时， mPlayer实例都需要更新。
    // ------------------------------------------------------------------------
    fun setPlayer(
        player: SimpleExoPlayer,
        actionCallback: PlaybackActionCallback,
        playerView: ExoPlayerView
    ) {
        if (moviePlayer == player) return
        exoPlayerView = playerView
        moviePlayer.removeListener(this)
        moviePlayer = player
        moviePlayer.addListener(this)
        this.actionCallback = actionCallback
    }


    // ------------------------------------------------------------------------
    // 每次 FsmPlayer 在 AdPlayingState 和 MoviePlayingState 之间改变状态时，当前控制器实例都需要更新视频实例
    // ------------------------------------------------------------------------
    fun setMediaModel(mediaModel: MediaModel, context: Context) {
        this.mediaModel = mediaModel
    }


    override fun isMediaPlayerError(): Boolean = isPlayerError.get()

    override fun triggerSubtitlesToggle(enabled: Boolean) {
        if (!::exoPlayerView.isInitialized) return
        exoPlayerView.subtitleView!!.visibility = if (enabled) View.VISIBLE else View.GONE
        mediaSubsTitleGet.set(true)
    }

    override fun getVideoID(): String {
        TODO("Not yet implemented")
    }

    override fun getMediaType(): String {
        TODO("Not yet implemented")
    }

    override fun getCurrentEpTmdbNumber(): String {
        TODO("Not yet implemented")
    }

    override fun setVideoAspectRatio(widthHeightRatio: Float) {
        TODO("Not yet implemented")
    }

    override fun getInitVideoAspectRatio(): Float {
        TODO("Not yet implemented")
    }

    override fun setResizeMode(resizeMode: Int) {
        TODO("Not yet implemented")
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        TODO("Not yet implemented")
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        TODO("Not yet implemented")
    }


    // ------------------------------------------------------------------------
    // 自动播放状态改变
    // ------------------------------------------------------------------------
    fun onCheckedAutoPlayChanged(enabled: Boolean) {}

    // ------------------------------------------------------------------------
    // 改变屏幕尺寸
    // ------------------------------------------------------------------------
    fun scale() {}

    // ------------------------------------------------------------------------
    // 加载侧栏资讯
    // ------------------------------------------------------------------------
    fun onLoadSide() {}

    // ------------------------------------------------------------------------
    // 播放进度调整
    // ------------------------------------------------------------------------
    fun seekBy(millisecond: Long) {}

    // ------------------------------------------------------------------------
    // 改变视频播放状态
    // ------------------------------------------------------------------------
    fun triggerPlayOrPause(play: Boolean) {}

    // ------------------------------------------------------------------------
    // 字幕点击处理
    // ------------------------------------------------------------------------
    fun clickOnSubs() {}

    // ------------------------------------------------------------------------
    // 重新播放视频
    // ------------------------------------------------------------------------
    fun onLoadFromBeginning() {}

    // ------------------------------------------------------------------------
    // 快进设置点击
    // ------------------------------------------------------------------------
    fun clickPlaybackSetting() {}

    // ------------------------------------------------------------------------
    // 加载更多剧集点击
    // ------------------------------------------------------------------------
    fun onLoadEpisodes() {}

    // ------------------------------------------------------------------------
    // 加载更多流媒体
    // ------------------------------------------------------------------------
    fun onLoadStreaming() {}

    // ------------------------------------------------------------------------
    // 加载更多电影
    // ------------------------------------------------------------------------
    fun onLoadMoviesList() {}

    // ------------------------------------------------------------------------
    // 播放下一集
    // ------------------------------------------------------------------------
    fun nextEpisode() {}

    // ------------------------------------------------------------------------
    // 切换声源
    // ------------------------------------------------------------------------
    fun onTracksMedia() {}

    // ------------------------------------------------------------------------
    // 跳过视频简介
    // ------------------------------------------------------------------------
    fun mediaHasSkipRecap() {}


    // ------------------------------------------------------------------------
    // View Binding  Property
    // ------------------------------------------------------------------------
    val currentMediaCover = ObservableField("") // 电影海报
    val videoName = ObservableField("") // 电影标题
    var mediaTypeName = ObservableField("")
    var mediaPositionInString = ObservableField("") // 媒体播放位置
    var videoCurrentSubs = ObservableField("Substitle")
    var mediaRemainInString = ObservableField("") // 以字符串格式返回媒体当前剩余时间
    var timeRemaining = ObservableField("") // 返回媒体当前剩余时间（用于 SeekBar）
    val playerPlaybackState = ObservableInt(Player.STATE_IDLE) //播放状态
    var isLive = ObservableField(false) // 是不是直播
    var mediaEnded = ObservableField(false) // 媒体是否播放完毕
    val mediaHasSubsTitle = ObservableField(false) // 是否存在字幕
    var isAutoPlayEnabled = ObservableField(false) // 是否启用自动播放
    var isVideoPlayWhenReady = ObservableBoolean(false) // 视频播放状态
    val mediaSubsTitleGet = ObservableField(false) // 是否可以切换字幕
    val isMediaHasSkipRecap = ObservableField(false) // 视频简介是否跳过
    var currentSpeed = ObservableField("Normal") // 快进速度说明
    var mediaDuration = ObservableField(0L) // 视频时长
    var mediaCurrentTime = ObservableField(0L) // 返回媒体当前时间（用于 SeekBar）
    var mediaBufferedPosition = ObservableField(0L) // 返回媒体当前缓冲位置（对于 SeekBar）返回媒体当前缓冲位置（用于 SeekBar）


}