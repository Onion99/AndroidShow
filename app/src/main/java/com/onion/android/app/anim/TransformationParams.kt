package com.onion.android.app.anim

import java.io.Serializable

internal interface TransformationParams : Serializable {
    val duration: Long
    var pathMotion: TransformationLayout.Motion
    var zOrder: Int
    var containerColor: Int
    var scrimColor: Int
}