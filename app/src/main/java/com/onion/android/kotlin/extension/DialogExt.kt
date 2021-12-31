package com.onion.android.kotlin.extension

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

// ------------------------------------------------------------------------
// 通过DataBinding 绑定Dialog
// ------------------------------------------------------------------------
fun <V : ViewDataBinding> AppCompatDialog.setBindingView(@LayoutRes layout: Int): V {
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    val binding = DataBindingUtil.inflate<V>(
        LayoutInflater.from(context),
        layout, null, false
    )
    setContentView(binding.root)
    return binding
}


// ------------------------------------------------------------------------
// 正常显示
// ------------------------------------------------------------------------
fun AppCompatDialog.setNormalStyleShow() {
    setCancelable(false)
    window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    val lp = WindowManager.LayoutParams()
    lp.copyFrom(window!!.attributes)
    lp.gravity = Gravity.BOTTOM
    lp.width = ViewGroup.LayoutParams.MATCH_PARENT
    lp.height = ViewGroup.LayoutParams.MATCH_PARENT
    window!!.attributes = lp
    show()
}