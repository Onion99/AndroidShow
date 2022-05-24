package com.dong.onionui.telegram

import android.app.ActivityManager.TaskDescription
import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.Window
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.dong.onionui.R
import com.dong.onionui.helper.AndroidUtilities
import com.dong.onionui.telegram.page.splash.IntroView
import com.dong.onionui.ui.view.BaseViewManagerLayout

class LaunchActivity : AppCompatActivity() {

    lateinit var viewManagerLayout: BaseViewManagerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setTheme(R.style.Theme_TMessages)
        // ---- 定制最近任务显示相关信息 https://segmentfault.com/a/1190000004374916 ------
        setTaskDescription(
            TaskDescription(null, null, -0x1000000)
        )
        window.navigationBarColor = -0x1000000
        window.setBackgroundDrawableResource(R.drawable.transparent)
        super.onCreate(savedInstanceState)
        AndroidUtilities.fillStatusBarHeight(this)

        // init ui
        val frameLayout = FrameLayout(this)
        setContentView(frameLayout, ViewGroup.LayoutParams(MATCH_PARENT,MATCH_PARENT))
        viewManagerLayout = BaseViewManagerLayout(this)
        frameLayout.addView(
            viewManagerLayout, ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        )
        viewManagerLayout.presentView(IntroView())

    }
}