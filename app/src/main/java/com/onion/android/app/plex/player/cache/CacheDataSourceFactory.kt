package com.onion.android.app.plex.player.cache

import android.content.Context
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.upstream.cache.CacheDataSink
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.google.android.exoplayer2.util.Util
import com.onion.android.R
import java.io.File

class CacheDataSourceFactory(
    private val context: Context,
    private val maxCacheSize: Long,
    private val maxFileSize: Long
) : DataSource.Factory {

    // custom build -> DefaultBandwidthMeter defaultBandwidthMeterNew = new DefaultBandwidthMeter.Builder(context).build();
    // deprecated -> DefaultBandwidthMeter bandwidthMeterOld = new DefaultBandwidthMeter();
    private val defaultBandwidthMeterSingle = DefaultBandwidthMeter.getSingletonInstance(context)
    private val exoDatabaseProvider = ExoDatabaseProvider(context)
    private val defaultDatasourceFactory = DefaultDataSourceFactory(
        context,
        defaultBandwidthMeterSingle,
        DefaultHttpDataSourceFactory(
            Util.getUserAgent(context, context.getString(R.string.app_name)),
            defaultBandwidthMeterSingle
        )
    )


    // ------------------------------------------------------------------------
    // 创建数据源
    // ------------------------------------------------------------------------
    override fun createDataSource(): DataSource {
        val evictor = LeastRecentlyUsedCacheEvictor(maxCacheSize)
        // deprecated -> SimpleCache simpleCacheOld = new SimpleCache(new File(context.getCacheDir(), "media"), evictor);
        val simpleCacheNew = SimpleCache(
            File(context.cacheDir, "media" + System.currentTimeMillis()),
            evictor,
            exoDatabaseProvider
        )
        return CacheDataSource(
            simpleCacheNew, defaultDatasourceFactory.createDataSource(),
            FileDataSource(), CacheDataSink(simpleCacheNew, maxFileSize),
            CacheDataSource.FLAG_BLOCK_ON_CACHE or CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR, null
        )
    }

}