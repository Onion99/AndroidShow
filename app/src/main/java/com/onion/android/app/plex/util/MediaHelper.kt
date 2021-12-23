package com.onion.android.app.plex.util

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.RenderersFactory
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.util.Util
import com.onion.android.App.Companion.context
import com.onion.android.app.constants.PlexConstants.APP_NAME
import com.onion.android.app.constants.PlexConstants.PREF_FILE
import java.util.*

object MediaHelper {

    fun buildDataSourceFactory(
        context: Context,
        bandwidthMeter: DefaultBandwidthMeter.Builder
    ): DataSource.Factory {
        return DefaultDataSourceFactory(
            context,
            bandwidthMeter.build(),
            buildHttpDataSourceFactory(context, bandwidthMeter.build())
        )
    }


    // put user agent in meta or attrs
    private fun buildHttpDataSourceFactory(
        context: Context,
        bandwidthMeter: DefaultBandwidthMeter
    ): HttpDataSource.Factory {
        return DefaultHttpDataSourceFactory(
            Util.getUserAgent(context, "EasyPlexPlayer"),
            bandwidthMeter,
            DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
            DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
            true
        )
    }

    fun buildRenderersFactory(
        context: Context,
        preferExtensionRenderer: Boolean
    ): RenderersFactory {
        return DefaultRenderersFactory(context.applicationContext).setExtensionRendererMode(
            DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON
        )
    }

    @JvmStatic
    fun userAgent(): String {
        val preferences: SharedPreferences =
            context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        return String.format(
            Locale.US, "%s (Android %s; %s; %s %s; %s)",
            preferences.getString(APP_NAME, "EasyPlex"),
            Build.VERSION.RELEASE, Build.MODEL, Build.BRAND, Build.DEVICE,
            Locale.getDefault().language
        )
    }

}