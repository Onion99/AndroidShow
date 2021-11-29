package com.onion.android.app.plex.player.presenter

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Point
import android.os.Build
import android.view.WindowManager
import com.onion.android.app.constants.PlexConstants.PLAYER_ASPECT_RATIO
import com.onion.android.app.constants.PlexConstants.PREF_FILE
import com.onion.android.app.plex.player.controller.PlayerController
import com.onion.android.app.plex.player.enums.ScaleMode
import com.onion.android.app.plex.player.enums.ScaleMode.*

class ScalePresenter {
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var mContext: Context
    private lateinit var mUserController: PlayerController
    private var mCurrentScaleMode = ScaleMode.MODE_DEFAULT

    fun setContext(context: Context) {
        this.mContext = context
    }

    constructor()
    constructor(context: Context, userController: PlayerController) {
        mContext = context
        mUserController = userController
        sharedPreferences = mContext.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        when {
            sharedPreferences.getString(PLAYER_ASPECT_RATIO, "default") == "16:9" -> {
                doScale(MODE_16_9)
            }
            sharedPreferences.getString(PLAYER_ASPECT_RATIO, "default") == "default" -> {
                doScale(ScaleMode.MODE_DEFAULT)
            }
            sharedPreferences.getString(PLAYER_ASPECT_RATIO, "default") == "4:3" -> {
                doScale(ScaleMode.MODE_4_3)
            }
            sharedPreferences.getString(PLAYER_ASPECT_RATIO, "default") == "full screen" -> {
                doScale(ScaleMode.MODE_FULL_SCREEN)
            }
            sharedPreferences.getString(PLAYER_ASPECT_RATIO, "default") == "Zoom" -> {
                doScale(ScaleMode.MODE_ZOOMED)
            }
        }
    }

    fun doScale() {
        val nextScaleMode: ScaleMode = mCurrentScaleMode.nextMode()
        doScale(nextScaleMode)
        mCurrentScaleMode = nextScaleMode
    }

    private fun doScale(mode: ScaleMode) {
        when (mode) {
            MODE_DEFAULT -> {
                val initVideoAspectRatio: Float = mUserController.getInitVideoAspectRatio()
                if (initVideoAspectRatio > 0) {
                    mUserController.setVideoAspectRatio(initVideoAspectRatio)
                }
            }
            MODE_4_3 -> mUserController.setVideoAspectRatio(4.toFloat() / 3)
            MODE_FULL_SCREEN -> mUserController.setVideoAspectRatio(getScreenWidthHeightRatio())
            else -> mUserController.setVideoAspectRatio(16.toFloat() / 9)
        }
        mUserController.setResizeMode(mode.mode)
    }

    fun getCurrentScaleMode(): ScaleMode {
        return mCurrentScaleMode
    }

    private fun getScreenWidthHeightRatio(): Float {
        val windowManager = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val outPoint = Point()
        if (19 <= Build.VERSION.SDK_INT) {
            // include navigation bar
            display.getRealSize(outPoint)
        } else {
            // exclude navigation bar
            display.getSize(outPoint)
        }
        return outPoint.x.toFloat() / outPoint.y
    }
}