package com.onion.android.app.view.dialog.extend

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import java.util.jar.Attributes


/**
 * Dialog 的根布局
 */
class DialogLayout(
    context: Context, attrs: AttributeSet?
) : FrameLayout(context, attrs) {

    var maxHeight: Int = 0
    var cornerRadius: FloatArray = floatArrayOf()
}