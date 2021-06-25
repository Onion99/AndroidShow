package com.onion.android.app.view.progress

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.Px
import com.onion.android.app.view.NO_COLOR
import com.onion.android.app.view.accentColor
import com.onion.android.app.view.dp2Px

///////////////////////////////////////////////////////////////////////////
// 高光View - 点击的时候在相应的View容器边框高亮显示
///////////////////////////////////////////////////////////////////////////
class HighLightView(context: Context, attributeSet: AttributeSet? = null) :
    FrameLayout(context, attributeSet) {

    // 布局组件
    private val bodyView = LinearLayout(context)
    private val strokeView = View(context)

    // 是否高光
    var highlighting: Boolean = false

    // 点击进度监听
    var onProgressClickListener: OnProgressClickListener? = null

    // 渐变颜色开始
    @ColorInt
    var colorGradientStart: Int = NO_COLOR

    // 渐变颜色结束
    @ColorInt
    var colorGradientEnd: Int = NO_COLOR

    // 渐变颜色方向
    var orientation = ProgressViewOrientation.HORIZONTAL

    // 高光Drawable
    var highlight: Drawable? = null

    // 高光颜色 - 默认取全局颜色值
    @ColorInt
    var color: Int = accentColor()

    ///////////////////////////////////////////////////////////////////////////
    // 初始化布局
    ///////////////////////////////////////////////////////////////////////////
    init {
        addView(bodyView)
        addView(strokeView)
        strokeView.setOnClickListener {
            highlighting = highlighting.not()
            onProgressClickListener?.onClickProgress(highlighting)
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // 更新View内容
    ///////////////////////////////////////////////////////////////////////////
    fun updateHighlightView() {
        updateBodyView()
        updateStrokeView()
        updateHighlighting()
    }

    ///////////////////////////////////////////////////////////////////////////
    // 填充BodyView
    ///////////////////////////////////////////////////////////////////////////
    private fun updateBodyView() {
        // 有渐变颜色值先设置渐变Drawable
        bodyView.background = if (colorGradientStart != NO_COLOR && colorGradientEnd != NO_COLOR) {
            var gradientOrientation = GradientDrawable.Orientation.LEFT_RIGHT
            if (orientation == ProgressViewOrientation.VERTICAL) {
                gradientOrientation = GradientDrawable.Orientation.TOP_BOTTOM
            }
            GradientDrawable(gradientOrientation, intArrayOf(colorGradientStart, colorGradientEnd))
        } else if (highlight != null) {
            // 后根据highlight Drawable 获取颜色 设置渐变 GradientDrawable
            GradientDrawable().apply {
                setColor(this@HighLightView.color)
            }
        } else {
            // 兜底Drawable
            highlight
        }
        // 设置间距
        bodyView.applyMargin()
    }

    // 高光边框宽度
    @Px
    var highlightThickness: Int = dp2Px(0)

    // 高光边框颜色
    @ColorInt
    var highlightColor: Int = accentColor()

    ///////////////////////////////////////////////////////////////////////////
    // 填充StrokeView
    ///////////////////////////////////////////////////////////////////////////
    private fun updateStrokeView() {
        strokeView.background = GradientDrawable().apply {
            setColor(Color.TRANSPARENT)
            setStroke(highlightThickness, highlightColor)
            applyRadius(this)
        }
        strokeView.applyMargin()
    }

    // 高光边框透明度
    @FloatRange(from = 0.0, to = 1.0)
    var highlightAlpha: Float = 1.0f

    ///////////////////////////////////////////////////////////////////////////
    // 是否展示高光边框
    ///////////////////////////////////////////////////////////////////////////
    private fun updateHighlighting() {
        if (highlighting) {
            strokeView.alpha = highlightAlpha
        } else {
            strokeView.alpha = 0f
        }
    }

    @Px
    var padding = dp2Px(0)

    ///////////////////////////////////////////////////////////////////////////
    // 设置View间距
    ///////////////////////////////////////////////////////////////////////////
    private fun View.applyMargin() {
        (layoutParams as MarginLayoutParams).setMargins(padding, padding, padding, padding)
    }

    // Drawable圆角定制
    var radiusArray: FloatArray? = null

    // Drawable圆角值
    var radius: Float = dp2Px(5).toFloat()
    private fun applyRadius(gradientDrawable: GradientDrawable) {
        if (radiusArray != null) {
            gradientDrawable.cornerRadii = radiusArray
        } else {
            gradientDrawable.cornerRadius = radius
        }
    }
}