package com.dong.onionui.helper

import android.widget.FrameLayout

object LayoutHelper {
    const val MATCH_PARENT = -1
    const val WRAP_CONTENT = -2


    private fun getSize(size: Float): Int {
        return (if (size < 0) size else AndroidUtilities.dp(size)).toInt()
    }


    fun createFrame(width: Int, height: Int, gravity: Int): FrameLayout.LayoutParams? {
        return FrameLayout.LayoutParams(
            LayoutHelper.getSize(width.toFloat()),
            LayoutHelper.getSize(height.toFloat()),
            gravity
        )
    }
}