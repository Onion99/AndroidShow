package com.onion.android.customview.first_location

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.onion.android.app.view.px
import kotlin.math.cos
import kotlin.math.sin

val RADIUS = 150f.px
private val ANGLES = floatArrayOf(60f, 90f, 150f, 60f)
private val COLORS = listOf(Color.BLUE, Color.GREEN, Color.CYAN, Color.RED)
private val OFFSET_LENGTH = 20f.px

class PieView(context: Context, attributeSet: AttributeSet) :
    View(context, attributeSet) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

//    init{
//        paint.strokeWidth = 3f.px
//        paint.style = Paint.Style.STROKE
//    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var startAngle = 0f
        for ((index, angle) in ANGLES.withIndex()) {

            if (index == 0) {
                canvas.save()
                canvas.translate(
                    OFFSET_LENGTH * cos(
                        Math.toRadians(startAngle + angle / 2f.toDouble()).toFloat()
                    ),
                    OFFSET_LENGTH * sin(
                        Math.toRadians(startAngle + angle / 2f.toDouble()).toFloat()
                    )
                )
            }

            paint.color = COLORS[index]

            canvas.drawArc(
                width / 2f - RADIUS,
                height / 2f - RADIUS,
                width / 2f + RADIUS,
                height / 2f + RADIUS,
                startAngle,
                angle,
                true,
                paint
            )
            startAngle += angle

            if (index == 0) {
                canvas.restore()
            }
        }
    }

}