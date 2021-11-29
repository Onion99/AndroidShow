package com.onion.android.app.plex.util

import android.app.UiModeManager
import android.content.Context
import android.content.res.Configuration
import com.google.android.exoplayer2.util.Util

object PlayerDeviceUtils {

    var sIsTVDevice = true
    private const val MI_BOX_DEVICE = "once"
    private const val XIAOMI_MANUFACTURER = "Xiaomi"
    private const val AMAZON_FEATURE_FIRE_TV = "amazon.hardware.fire_tv"

    fun useSinglePlayer(): Boolean {
        // 在所有TV设备中使用单例
        return if (PlayerDeviceUtils.sIsTVDevice) {
            true
        } else PlayerDeviceUtils.XIAOMI_MANUFACTURER == Util.MANUFACTURER && PlayerDeviceUtils.MI_BOX_DEVICE == Util.DEVICE
        // 在米盒中 使用单例,自exoplayer 2.8.3起不再支持多个player的实例化.
    }

    fun isTVDevice(context: Context): Boolean {
        if (!sIsTVDevice) {
            val uiModeManager = context.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
            sIsTVDevice = uiModeManager.currentModeType == Configuration.UI_MODE_TYPE_TELEVISION
            if (sIsTVDevice) {
                return sIsTVDevice
            }
            sIsTVDevice =
                context.packageManager.hasSystemFeature(PlayerDeviceUtils.AMAZON_FEATURE_FIRE_TV)
        }
        return sIsTVDevice
    }
}