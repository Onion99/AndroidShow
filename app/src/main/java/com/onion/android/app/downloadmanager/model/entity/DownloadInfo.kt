package com.onion.android.app.downloadmanager.model.entity

import android.net.Uri
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.onion.android.app.downloadmanager.storage.converter.UriConverter
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity
@Parcelize
class DownloadInfo() : Parcelable {

    constructor(
        dirPath: Uri, url: String,
        fileName: String, mediaName: String,
        mediaBackdrop: String, mediaId: String,
        mediaType: String, refer: String
    ) : this() {
        this.dirPath = dirPath
        this.url = url
        this.fileName = fileName
        this.mediaName = mediaName
        this.mediaBackdrop = mediaBackdrop
        this.mediaId = mediaId
        this.mediaType = mediaType
        this.refer = refer
    }

    @PrimaryKey
    var id: UUID = UUID.randomUUID();

    @TypeConverters(UriConverter::class)
    var dirPath: Uri = Uri.EMPTY
    var url: String = ""
    var fileName: String = ""
    var mediaName: String = ""
    var mediaBackdrop: String = ""
    var mediaId: String = ""
    var mediaType: String = ""
    var refer: String = ""
    var description: String = ""
    var statusMsg: String = ""
    var userAgent: String = ""
    var checksum: String = ""
    var mimeType: String = "application/octet-stream"
    var totalBytes: Long = -1
    var dateAdded: Long = -1
    var lastModify: Long = -1
    var retryAfter: Int = 0
    var numFailed: Int = 0
    var visibility: Int = 0
    var statusCode: Int = 0
    var numPieces: Int = 0

}