package com.onion.android.app.plex.ui.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageButton
import com.onion.android.R

class SeasonsImageButton : ImageButton {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)


    private var isChecked = false
    private var mStateCheckedDrawableId = 0
    private var mStateNotCheckedDrawableId = 0


    /**
     * Initialize all of the drawables and animations as well as apply attributes if set
     *
     *
     */
    private fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.SubTitleImageButton, 0, 0
            )
            try {
                mStateCheckedDrawableId = a.getResourceId(
                    R.styleable.SubTitleImageButton_state_checked,
                    R.drawable.ic_tune
                )
                mStateNotCheckedDrawableId = a.getResourceId(
                    R.styleable.SubTitleImageButton_state_not_checked,
                    R.drawable.ic_tune
                )
            } finally {
                a.recycle()
            }
        }
        setDrawableSelector()
    }


    private fun setDrawableSelector() {
        if (isChecked) {
            setBackgroundResource(mStateCheckedDrawableId)
        } else {
            setBackgroundResource(mStateNotCheckedDrawableId)
        }
        invalidate()
    }


    fun setChecked(checked: Boolean) {
        isChecked = checked
        setDrawableSelector()
    }
}