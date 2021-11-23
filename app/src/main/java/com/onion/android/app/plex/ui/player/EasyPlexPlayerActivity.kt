package com.onion.android.app.plex.ui.player

import android.content.res.Configuration
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector.ParametersBuilder
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.util.Assertions
import com.onion.android.app.plex.data.model.media.MediaModel
import com.onion.android.app.plex.util.MediaHelper
import com.onion.android.kotlin.extension.hideSystemBar

const val KEY_MEDIA = "key_media"
const val KEY_TRACK_SELECTOR_PARAMETERS = "key_track_selector_parameters"

abstract class EasyPlexPlayerActivity : LifeCycleActivity(), LifecycleOwner {

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        hideSystemBar()
    }

    private lateinit var mediaDataSourceFactory: DataSource.Factory
    private lateinit var trackSelectorParameters: DefaultTrackSelector.Parameters
    private lateinit var mediaModel: MediaModel
    private var bandwidthMeter = DefaultBandwidthMeter.Builder(baseContext)


    private fun parseIntent() {
        val error = "MediaModel is empty"
        Assertions.checkState(intent != null && intent.extras != null, error)
        mediaModel = intent.extras!!.getSerializable(MEDIA_KEY) as MediaModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mediaDataSourceFactory = MediaHelper.buildDataSourceFactory(this, bandwidthMeter)
        trackSelectorParameters = if (savedInstanceState != null) {
            savedInstanceState.getParcelable(KEY_TRACK_SELECTOR_PARAMETERS)!!
        } else {
            val builder = ParametersBuilder(this)
            builder.build()
        }
    }
}