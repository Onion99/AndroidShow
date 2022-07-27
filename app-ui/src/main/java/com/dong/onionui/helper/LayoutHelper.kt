package com.dong.onionui.helper

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout

object LayoutHelper {
    const val MATCH_PARENT = -1
    const val WRAP_CONTENT = -2


    private fun getSize(size: Float): Int {
        return (if (size < 0) size else AndroidUtilities.dp(size)).toInt()
    }

    // ------------------------------------------------------------------------
    // ViewGroup helper
    // ------------------------------------------------------------------------

    fun createCommonViewGroup():ViewGroup.LayoutParams = ViewGroup.LayoutParams(MATCH_PARENT,MATCH_PARENT)


    // ------------------------------------------------------------------------
    // FrameLayout helper
    // ------------------------------------------------------------------------
    fun createFrame(width: Int, height: Int, gravity: Int): FrameLayout.LayoutParams{
        return FrameLayout.LayoutParams(getSize(width.toFloat()), getSize(height.toFloat()), gravity)
    }

    fun createFrame(width: Int, height: Float): FrameLayout.LayoutParams {
        return FrameLayout.LayoutParams(getSize(width.toFloat()), getSize(height))
    }

    fun createFrame(width: Float, height: Float, gravity: Int): FrameLayout.LayoutParams{
        return FrameLayout.LayoutParams(getSize(width), getSize(height), gravity)
    }


    // ------------------------------------------------------------------------
    // LinearLayout helper
    // ------------------------------------------------------------------------
    fun createLinear(width: Int, height: Int, weight: Float): LinearLayout.LayoutParams{
        return LinearLayout.LayoutParams(getSize(width.toFloat()), getSize(height.toFloat()), weight)
    }

    fun createLinear(width: Int, height: Int): LinearLayout.LayoutParams{
        return LinearLayout.LayoutParams(getSize(width.toFloat()), getSize(height.toFloat()))
    }
}