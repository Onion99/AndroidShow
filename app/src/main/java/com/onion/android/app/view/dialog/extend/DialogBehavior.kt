package com.onion.android.app.view.dialog.extend

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.StyleRes
import com.onion.android.app.view.dialog.MaterialDialog

interface DialogBehavior {

    /** 获取 style.xml 应用在当前窗口的主题*/
    @StyleRes fun getThemeRes(isDark:Boolean):Int

    /** 创建Dialog的根布局 */
    fun createView(
        creatingContext:Context,
        dialogWindow:Window,
        layoutInflater: LayoutInflater,
        dialog: MaterialDialog
    ) : ViewGroup

}