package com.onion.android.ui.dialog.extend

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import java.util.jar.Attributes


/**
 * Dialog 的根布局
 *
 *
 */
class DialogLayout(
    context: Context, attrs: AttributeSet?
) : FrameLayout(context, attrs) {

    var maxHeight: Int = 0
    var cornerRadius: FloatArray = floatArrayOf()
    set(value) {

    }


}