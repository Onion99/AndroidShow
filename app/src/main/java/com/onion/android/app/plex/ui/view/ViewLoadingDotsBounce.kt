package com.onion.android.app.plex.ui.view

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.onion.android.app.view.dp

class ViewLoadingDotsBounce : LinearLayout {

    private var imgs = mutableListOf<ImageView>()
    private var layouts = mutableListOf<LinearLayout>()
    private var anims = mutableListOf<ObjectAnimator>()

    // 渐变背景
    private val circle = GradientDrawable()
    private val OBJECT_SIZE = 3
    private val POST_DIV = 10
    private val DURATION = 500


    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        preInit()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private fun preInit() {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER

        var color = Color.GRAY
        val background = background
        if (background is ColorDrawable) {
            color = background.color
        }


        setBackgroundColor(Color.TRANSPARENT)
        removeAllViews()

        circle.shape = GradientDrawable.OVAL
        circle.setColor(color)
        circle.setSize(10.dp.toInt(), 10.dp.toInt())

        val layoutParams2 =
            LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams2.weight = 1f

        for (index in 0 until OBJECT_SIZE) {
            layouts.add(LinearLayout(context))
            layouts[index].gravity = Gravity.CENTER
            layouts[index].layoutParams = layoutParams2
            imgs.add(ImageView(context))
            imgs[index].setBackgroundDrawable(circle)
            layouts[index].addView(imgs[index])
            addView(layouts[index])
        }
    }

    private var hasLayout = false
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (hasLayout) return
        val lp = LayoutParams(width / 5, width / 3)
        for (index in 0 until OBJECT_SIZE) {
            layouts[index].layoutParams = lp
        }
        for (index in 0 until OBJECT_SIZE) {
            imgs[index].translationY = (height / POST_DIV).toFloat()
            val y = PropertyValuesHolder.ofFloat(TRANSLATION_Y, (-height / POST_DIV).toFloat())
            val x = PropertyValuesHolder.ofFloat(TRANSLATION_X, 0f)
            anims.add(ObjectAnimator.ofPropertyValuesHolder(imgs[index], x, y))
            anims[index].repeatCount = -1
            anims[index].repeatMode = ValueAnimator.REVERSE
            anims[index].duration = DURATION.toLong()
            anims[index].startDelay = ((DURATION / 3) * index).toLong()
            anims[index].start()
        }
        hasLayout = true
    }
}