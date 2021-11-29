package com.onion.android.app.plex.player.enums

import com.google.android.exoplayer2.ui.AspectRatioFrameLayout

enum class ScaleMode(val mode: Int, description: String) {

    MODE_DEFAULT(AspectRatioFrameLayout.RESIZE_MODE_FIT, "default"),

    MODE_4_3(AspectRatioFrameLayout.RESIZE_MODE_FIT, "4:3"),
    MODE_16_9(AspectRatioFrameLayout.RESIZE_MODE_FIT, "16:9"),
    MODE_FULL_SCREEN(AspectRatioFrameLayout.RESIZE_MODE_FILL, "full screen"),
    MODE_ZOOMED(AspectRatioFrameLayout.RESIZE_MODE_ZOOM, "Zoom");

    val restId: Int
    val description: String

    fun nextMode(): ScaleMode {
        val modes: Array<ScaleMode> = ScaleMode.values()
        for (i in modes.indices) {
            if (this == modes[i]) {
                return modes[(i + 1) % modes.size]
            }
        }
        return ScaleMode.MODE_DEFAULT
    }

    init {
        restId = -1
        this.description = description
    }
}