package com.onion.android.app.view.progress

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.annotation.Px
import com.onion.android.R
import com.onion.android.app.view.dp2Px
import com.onion.android.app.view.px2Sp

///////////////////////////////////////////////////////////////////////////
// 自定义布局控制控件的位置可以通过继承FrameLayout实现
///////////////////////////////////////////////////////////////////////////
class ProgressView : FrameLayout {

    ///////////////////////////////////////////////////////////////////////////
    // 自定View一般需写三个的构造函数
    ///////////////////////////////////////////////////////////////////////////
    constructor(context: Context) : super(context)

    constructor(context: Context, attributes: AttributeSet) : this(context, attributes, 0)

    constructor(context: Context, attributes: AttributeSet, defStyle: Int) : super(
        context,
        attributes,
        defStyle
    ) {

    }

    ///////////////////////////////////////////////////////////////////////////
    // 获取XML布局属性
    ///////////////////////////////////////////////////////////////////////////
    private fun getAttrs(attributes: AttributeSet, defStyle: Int) {
        val typedArray =
            context.obtainStyledAttributes(attributes, R.styleable.ProgressView, defStyle, 0)
        try {

        } finally {
            // 回收TypedArray，供以后的调用者重用。在调用这个函数之后，你永远不能再碰这个类型化数组。
            typedArray.recycle()
        }
    }

    // 用于显示进度的labelView文本。
    private var labelText: CharSequence? = ""

    // 标签视图的文本大小
    @Px
    var labelSize: Float = 12f

    // 前进的容器之间的labelView的间距。如果labelView位于内部或外部，空间将被应用
    @Px
    var labelSpace: Float = dp2Px(8).toFloat()

    // 内部标签颜色
    @ColorInt
    var labelColorInner: Int = Color.WHITE

    // 外部标签颜色
    @ColorInt
    var labelColorOuter: Int = Color.BLACK

    // 标签字体样式
    var labelTypeface: Int = Typeface.NORMAL

    // 标签布局约束
    var labelConstraints: ProgressLabelConstraints = ProgressLabelConstraints.ALIGN_PROGRESS

    // 进度动画
    var progressAnimation: ProgressViewAnimation = ProgressViewAnimation.NORMAL
    private fun setTypeArray(properties: TypedArray) {
        this.labelText = properties.getString(R.styleable.ProgressView_progressView_labelText)
        this.labelSize =
            px2Sp(
                properties.getDimension(
                    R.styleable.ProgressView_progressView_labelSize,
                    labelSize
                )
            )
        this.labelSpace =
            properties.getDimension(R.styleable.ProgressView_progressView_labelSpace, labelSpace)
        this.labelColorInner =
            properties.getColor(
                R.styleable.ProgressView_progressView_labelColorInner,
                labelColorInner
            )
        this.labelColorOuter =
            properties.getColor(
                R.styleable.ProgressView_progressView_labelColorOuter,
                labelColorOuter
            )
        this.labelTypeface =
            when (properties.getInt(
                R.styleable.ProgressView_progressView_labelTypeface,
                Typeface.NORMAL
            )) {
                1 -> Typeface.BOLD
                2 -> Typeface.ITALIC
                else -> Typeface.NORMAL
            }
        this.labelConstraints =
            when (properties.getInt(
                R.styleable.ProgressView_progressView_labelConstraints,
                ProgressLabelConstraints.ALIGN_PROGRESS.ordinal
            )) {
                1 -> ProgressLabelConstraints.ALIGN_CONTAINER
                else -> ProgressLabelConstraints.ALIGN_PROGRESS
            }

    }
}