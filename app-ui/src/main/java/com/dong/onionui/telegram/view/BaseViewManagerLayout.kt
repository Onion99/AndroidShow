package com.dong.onionui.telegram.view

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.dong.onionui.telegram.base.BaseView
import com.dong.onionui.helper.AndroidUtilities
import com.dong.onionui.helper.LayoutHelper

class BaseViewManagerLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {


    fun presentView(baseView: BaseView){
        // ---- 隐藏键盘 ------
        (context as Activity).currentFocus?.let {
            AndroidUtilities.hideKeyboard(it)
        }

        if(!baseView.isInitView()) baseView.createView(context)
        addView(baseView.currentView)
        baseView.currentView.apply {
            val params = layoutParams as LayoutParams
            params.width = LayoutHelper.MATCH_PARENT
            params.height = LayoutHelper.MATCH_PARENT
            layoutParams = params
        }
    }
}