package com.onion.android.app.view.navigation.float

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.onion.android.R
import com.onion.android.databinding.ViewNavigationItemBinding
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.UI
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.px2dip

///////////////////////////////////////////////////////////////////////////
// @JvmOverloads 指示 Kotlin 编译器为此函数生成替代默认参数值的重载。
///////////////////////////////////////////////////////////////////////////
class NavigationItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    ///////////////////////////////////////////////////////////////////////////
    // 使用Anko构造
    ///////////////////////////////////////////////////////////////////////////
    // 这里的Context为AnkoContext<NavigationItem> 是当前View的
    private val itemView = AnkoContext.createDelegate(this).apply {
        frameLayout {}
    }.view

    // 这里的Context为AnkoContext<context> 取决你传进来的
    private val testView = context.UI {
        frameLayout {}
    }.view

    ///////////////////////////////////////////////////////////////////////////
    // 获取当前ItemView
    ///////////////////////////////////////////////////////////////////////////
    private val binding = DataBindingUtil.inflate<ViewNavigationItemBinding>(
        LayoutInflater.from(context),
        R.layout.view_navigation_item,
        this,
        true
    )

    // 默认Icon颜色
    var defaultIconColor = 0

    // 选定Icon颜色
    var selectedIconColor = 0

    // 悬浮圈背景颜色
    var circleColor = 0

    // Icon
    var icon = 0
        set(value) {
            // icon = value
            field = value
            if (allowDraw) binding.iv.setImageResource(value)
        }

    // icon尺寸
    private var iconSize = px2dip(48)

    // 消息提示文本
    var count: String? = ""

    // 消息提示颜色
    var countTextColor = 0

    // 消息提示背景颜色
    var countBackgroundColor = 0

    // 消息提示字体样式
    var countTypeface: Typeface? = null

    // 点击波纹颜色
    var rippleColor = 0

    // 不明白
    var isFromLeft = false

    // 动画时长
    var duration = 0L

    // 位置控制
    private var progress = 0f

    // 是否启用当前Item
    var isEnabledItem = false

    // 是否允许绘制
    private var allowDraw = false

    // 点击监听
    var onClickListener: () -> Unit = {}
        set(value) {
            field = value
            binding.iv.setOnClickListener { onClickListener() }
        }

    init {
        draw()
    }

    private fun draw() {
        if (!allowDraw)
            return
        icon = icon
        count = count
        iconSize = iconSize
        countTextColor = countTextColor
        countBackgroundColor = countBackgroundColor
        countTypeface = countTypeface
        rippleColor = rippleColor
        onClickListener = onClickListener
    }

    fun disableItem(isAnimate: Boolean = true) {
        if (isEnabledItem) animateProgress(false, isAnimate)
        isEnabledItem = false
    }

    fun enableItem(isAnimate: Boolean = true) {
        if (!isEnabledItem) animateProgress(true, isAnimate)
        isEnabledItem = true
    }

    // 进度动画
    private fun animateProgress(enableCell: Boolean, isAnimate: Boolean = true) {
        val d = if (enableCell) duration else 250
        val anim = ValueAnimator.ofFloat(0f, 1f)
        anim.apply {
            startDelay = if (enableCell) d / 4 else 0L
            duration = if (isAnimate) d else 1L
            interpolator = FastOutSlowInInterpolator()
            addUpdateListener {
                val f = it.animatedFraction
                progress = if (enableCell) f else 1f - f
            }
            start()
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // 测量视图及其内容以确定测量的宽度和测量的高度
    ///////////////////////////////////////////////////////////////////////////
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        progress = progress
    }
}