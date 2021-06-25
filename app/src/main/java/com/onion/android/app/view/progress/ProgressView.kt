package com.onion.android.app.view.progress

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.Px
import com.onion.android.R
import com.onion.android.app.view.NO_COLOR
import com.onion.android.app.view.dp2Px
import com.onion.android.app.view.progress.ProgressViewAnimation.*
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

    // 在ProgressView上显示进度值
    val labelView = TextView(context)

    // 用于显示进度的labelView文本。
    private var labelText: CharSequence? = ""

    // 标签位置对齐
    var labelGravity: Int? = null

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

    // ProgressView 标签布局约束
    var labelConstraints: ProgressLabelConstraints = ProgressLabelConstraints.ALIGN_PROGRESS

    // ProgressView 朝向
    var orientation = ProgressViewOrientation.HORIZONTAL

    // ProgressView 进度动画
    var progressAnimation: ProgressViewAnimation = NORMAL

    // ProgressView 进度最小值
    var min: Float = 0f

    // ProgressView 进度最大值
    var max: Float = 100f

    // ProgressView 当前进度
    var progress: Float = 0f

    // ProgressView 容器圆角
    @Px
    var radius: Float = dp2Px(5).toFloat()

    // ProgressView 动画时长
    var duration: Long = 1000L

    // ProgressView 容器背景颜色
    @ColorInt
    var colorBackground: Int = Color.WHITE

    // ProgressView 容器边框颜色
    @ColorInt
    var borderColor: Int = colorBackground

    // ProgressView 容器边框大小。
    @Px
    var borderWidth: Int = 0

    // ProgressView初始化时是否自动启动进度动画
    var autoAnimate: Boolean = true

    // 启动进度动画,是否从之前的进度值开始
    var progressFromPrevious: Boolean = false

    // highlightView 是一个通过 onClickListener 突出显示笔触的视图
    val highlightView = HighLightView(context)

    // 圆角值定制
    var radiusArray: FloatArray? = null
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

        when (
            properties.getInt(
                R.styleable.ProgressView_progressView_orientation,
                ProgressViewOrientation.HORIZONTAL.value
            )
        ) {
            0 -> this.orientation = ProgressViewOrientation.HORIZONTAL
            1 -> this.orientation = ProgressViewOrientation.VERTICAL
        }
        when (properties.getInt(
            R.styleable.ProgressView_progressView_animation,
            progressAnimation.value
        )) {
            NORMAL.value -> this.progressAnimation = NORMAL
            BOUNCE.value -> this.progressAnimation = BOUNCE
            DECELERATE.value -> this.progressAnimation = DECELERATE
            ACCELERATEDECELERATE.value -> this.progressAnimation = ACCELERATEDECELERATE
        }

        this.min = properties.getFloat(R.styleable.ProgressView_progressView_min, min)
        this.max = properties.getFloat(R.styleable.ProgressView_progressView_max, max)
        this.progress =
            properties.getFloat(R.styleable.ProgressView_progressView_progress, progress)
        this.radius = properties.getDimension(R.styleable.ProgressView_progressView_radius, radius)
        this.duration =
            properties.getInteger(R.styleable.ProgressView_progressView_duration, duration.toInt())
                .toLong()
        this.colorBackground =
            properties.getColor(
                R.styleable.ProgressView_progressView_colorBackground,
                colorBackground
            )
        this.borderColor =
            properties.getColor(R.styleable.ProgressView_progressView_borderColor, borderColor)
        this.borderWidth =
            properties.getDimensionPixelSize(
                R.styleable.ProgressView_progressView_borderWidth,
                borderWidth
            )
        this.autoAnimate =
            properties.getBoolean(R.styleable.ProgressView_progressView_autoAnimate, autoAnimate)
        this.progressFromPrevious =
            properties.getBoolean(
                R.styleable.ProgressView_progressView_progressFromPrevious,
                progressFromPrevious
            )

        with(highlightView) {
            alpha = properties.getFloat(
                R.styleable.ProgressView_progressView_highlightAlpha,
                highlightAlpha
            )
            color = properties.getColor(R.styleable.ProgressView_progressView_colorProgress, color)
            colorGradientStart =
                properties.getColor(
                    R.styleable.ProgressView_progressView_colorGradientStart,
                    NO_COLOR
                )
            colorGradientEnd =
                properties.getColor(
                    R.styleable.ProgressView_progressView_colorGradientEnd,
                    NO_COLOR
                )
            radius = this@ProgressView.radius
            radiusArray = this@ProgressView.radiusArray
            padding =
                properties.getDimension(
                    R.styleable.ProgressView_progressView_padding,
                    borderWidth.toFloat()
                ).toInt()
            highlightColor =
                properties.getColor(
                    R.styleable.ProgressView_progressView_highlightColor,
                    highlightColor
                )
            highlightThickness = properties.getDimension(
                R.styleable.ProgressView_progressView_highlightWidth,
                highlightThickness.toFloat()
            )
                .toInt()
            if (!properties.getBoolean(
                    R.styleable.ProgressView_progressView_highlighting,
                    !highlighting
                )
            ) {
                highlightThickness = 0
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // XML布局被加载完后，就会回调onFinshInfalte这个方法
    // 即使子类覆盖了 onFinishInflate，它们也应该始终确保调用 super 方法，以便我们被调用
    ///////////////////////////////////////////////////////////////////////////
    override fun onFinishInflate() {
        super.onFinishInflate()
        // 更新ProgressView
        updateProgressView()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed && orientation == ProgressViewOrientation.VERTICAL) {
            rotation = 180f
            labelView.rotation = 180f
        }
    }

    // 平滑Container圆角的路径,用于实现圆角的
    private val path = Path()

    ///////////////////////////////////////////////////////////////////////////
    // 当此视图的大小发生变化时，在布局期间调用此方法。 如果您刚刚被添加到视图层次结构中，则会使用旧值 0 调用您
    ///////////////////////////////////////////////////////////////////////////
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.path.apply {
            reset()
            val radiusArray = radiusArray ?: floatArrayOf(
                radius,
                radius,
                radius,
                radius,
                radius,
                radius,
                radius,
                radius
            )
            addRoundRect(
                RectF(0f, 0f, w.toFloat(), h.toFloat()),
                radiusArray,
                Path.Direction.CCW
            )
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // 由 draw 调用以绘制子视图
    ///////////////////////////////////////////////////////////////////////////
    override fun dispatchDraw(canvas: Canvas) {
        canvas.clipPath(this.path)
        super.dispatchDraw(canvas)
    }

    private fun updateProgressView() {
        post {
            updateHighlightView()
        }
    }

    // 填充highlightView
    private fun updateHighlightView() {
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        if (max <= progress) {
            // 垂直取高，水平取宽
            if (isVertical()) {
                params.height = getViewSize(this)
            } else {
                params.width = getViewSize(this)
            }
        } else {
            if (isVertical()) {
                params.height = getProgressSize().toInt()
            } else {
                params.width = getProgressSize().toInt()
            }
        }
        this.highlightView.layoutParams = params
        this.highlightView.updateHighlightView()
        removeView(highlightView)
        addView(highlightView)
    }

    private fun updateLabel() {
        if (labelGravity != null) {
            this.labelView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // 判断Progress的朝向
    ///////////////////////////////////////////////////////////////////////////
    fun isVertical(): Boolean {
        return orientation == ProgressViewOrientation.VERTICAL
    }

    ///////////////////////////////////////////////////////////////////////////
    // 根据View朝向动态获取对应长度
    ///////////////////////////////////////////////////////////////////////////
    private fun getViewSize(view: View): Int {
        return if (isVertical()) view.height
        else view.width
    }

    ///////////////////////////////////////////////////////////////////////////
    // 获取进度大小
    ///////////////////////////////////////////////////////////////////////////
    private fun getProgressSize(progressValue: Float = progress): Float {
        return (getViewSize(this) / max) * progressValue
    }
}