package com.onion.android.app.downloadmanager

import android.content.Context

class DownloadEngine constructor(context: Context) {


    companion object {
        @Volatile
        private lateinit var engine: DownloadEngine

        fun getInstance(context: Context): DownloadEngine {
            if (this::engine.isInitialized) return engine
            synchronized(DownloadEngine::class) {
                engine = DownloadEngine(context)
            }
            return engine
        }
    }
}