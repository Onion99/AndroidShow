package com.dong.onionui.telegram.page.splash

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ScrollView
import com.airbnb.lottie.LottieAnimationView
import com.dong.onionui.helper.LayoutHelper
import com.dong.onionui.telegram.base.BaseView

class IntroView :BaseView(){

    override fun createView(context: Context): View {
        val scrollView = ScrollView(context)
        // ---- 指示此 ScrollView 是否应拉伸其内容高度以填充Window ------
        scrollView.isFillViewport = true

        val themeIconView = LottieAnimationView(context)

        val themeFrameLayout = FrameLayout(context)
        themeFrameLayout.addView(themeIconView, LayoutHelper.createFrame(28, 28, Gravity.CENTER))

        currentView = scrollView
        return scrollView
    }
}