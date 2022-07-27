package com.dong.onionui.helper

import android.content.Context
import android.content.res.Configuration
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import kotlin.math.abs
import kotlin.math.ceil

object AndroidUtilities {

    private var density = 1f
    var displaySize = Point()
    var displayMetrics = DisplayMetrics()
    private var screenRefreshRate = 60f
    private var usingHardwareInput = false


    fun checkDisplaySize(context: Context, newConfiguration: Configuration?) {
        try {
            val oldDensity: Float = density
            density = context.resources.displayMetrics.density
            val newDensity: Float = density
            var configuration = newConfiguration
            if (configuration == null) {
                configuration = context.resources.configuration
            }
            usingHardwareInput = configuration!!.keyboard != Configuration.KEYBOARD_NOKEYS && configuration.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO
            val manager = context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
            if (manager != null) {
                val display = manager.defaultDisplay
                if (display != null) {
                    display.getMetrics(displayMetrics)
                    display.getSize(displaySize)
                    screenRefreshRate = display.refreshRate
                }
            }
            if (configuration.screenWidthDp != Configuration.SCREEN_WIDTH_DP_UNDEFINED) {
                val newSize = ceil(configuration.screenWidthDp * density).toInt()
                if (abs(displaySize.x - newSize) > 3) displaySize.x = newSize
            }
            if (configuration.screenHeightDp != Configuration.SCREEN_HEIGHT_DP_UNDEFINED) {
                val newSize = ceil(configuration.screenHeightDp * density).toInt()
                if (abs(displaySize.y - newSize) > 3)  displaySize.y = newSize
            }
        } catch (_: Exception) { }
    }

    fun dp(value: Float): Int {
        return if (value == 0f) 0
        else ceil(density * value).toInt()
    }

    // ------------------------------------------------------------------------
    // 获取状态栏和底部导航栏高度
    // ------------------------------------------------------------------------
    private var statusBarHeight = 0
    private var navigationBarHeight = 0

    fun fillStatusBarHeight(context: Context?) {
        if (context == null || statusBarHeight > 0) {
            return
        }
        statusBarHeight = getStatusBarHeight(context)
        navigationBarHeight = getNavigationBarHeight(context)
    }

    private fun getStatusBarHeight(context: Context): Int {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) context.resources.getDimensionPixelSize(resourceId) else 0
    }

    private fun getNavigationBarHeight(context: Context): Int {
        val resourceId = context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) context.resources.getDimensionPixelSize(resourceId) else 0
    }


    fun hideKeyboard(view: View?) {
        if (view == null) return
        try {
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (!imm.isActive) {
                return
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        } catch (_: Exception) {}
    }
}
