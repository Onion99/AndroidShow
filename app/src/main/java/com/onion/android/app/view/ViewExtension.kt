@file:Suppress("UNCHECKED_CAST")


package com.onion.android.app.view

import android.content.res.Resources
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import com.onion.android.R

///////////////////////////////////////////////////////////////////////////
// dp 转 px
///////////////////////////////////////////////////////////////////////////
@JvmSynthetic
fun dp2Px(dp: Float): Int {
    // 最好的写法
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        Resources.getSystem().displayMetrics
    )
        .toInt()
    //  return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,resources.displayMetrics)
}

fun dp2Px(dp: Int): Int {
    // 最好的写法
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        Resources.getSystem().displayMetrics
    )
        .toInt()
}

val Float.px
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

val Int.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )


///////////////////////////////////////////////////////////////////////////
// sp 转 px
///////////////////////////////////////////////////////////////////////////
@JvmSynthetic
internal fun View.sp2Px(sp: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        sp,
        context.resources.displayMetrics
    )
}

///////////////////////////////////////////////////////////////////////////
// px 转 sp
///////////////////////////////////////////////////////////////////////////
@JvmSynthetic
internal fun View.px2Sp(px: Float): Float {
    return px / resources.displayMetrics.scaledDensity
}

///////////////////////////////////////////////////////////////////////////
// 从ContextCompat获取颜色
///////////////////////////////////////////////////////////////////////////
@JvmSynthetic
internal fun View.accentColor(): Int {
    val colorAttr: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        R.attr.colorAccent
    } else {
        context.resources.getIdentifier("colorAccent", "attr", context.packageName)
    }
    val outValue = TypedValue()
    context.theme.resolveAttribute(colorAttr, outValue, true)
    return outValue.data
}

///////////////////////////////////////////////////////////////////////////
// 更新 FrameLayout 布局参数
///////////////////////////////////////////////////////////////////////////
@JvmSynthetic
internal fun ViewGroup.updateLayoutParams(block: ViewGroup.LayoutParams.() -> Unit) {
    layoutParams?.let {
        val params: ViewGroup.LayoutParams =
            (layoutParams as ViewGroup.LayoutParams).apply { block(this) }
        layoutParams = params
    }
}
