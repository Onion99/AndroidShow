package com.onion.android.app.downloadmanager.storage.converter

import android.net.Uri

class UriConverter {

    fun toUri(uri: String): Uri = Uri.parse(uri)
    fun fromUri(uri: Uri) = uri.toString()
}