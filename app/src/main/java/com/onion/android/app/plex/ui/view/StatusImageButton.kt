package com.onion.android.app.plex.ui.view

import android.content.Context
import android.util.AttributeSet
import android.widget.Checkable
import androidx.appcompat.widget.AppCompatImageButton
import com.onion.android.R

// ------------------------------------------------------------------------
// 类似于Switch Button 的Image Button
// ------------------------------------------------------------------------
class StatusImageButton : AppCompatImageButton, Checkable {

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

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
            val a =
                context.theme.obtainStyledAttributes(attrs, R.styleable.SubTitleImageButton, 0, 0)
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


    override fun setChecked(checked: Boolean) {
        isChecked = checked
        setDrawableSelector()
    }

    override fun isChecked(): Boolean = isChecked

    override fun toggle() = setChecked(!isChecked())
}