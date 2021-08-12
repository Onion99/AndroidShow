package com.onion.android.customview.first_location

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.onion.android.app.view.px
import kotlin.math.cos
import kotlin.math.sin

const val OPEN_ANGEL = 120f
const val MARK = 10
val LENGTH = 120f.px
val DASH_WIDTH = 2f.px
val DASH_LENGTH = 9f.px

class DashboardView(context: Context, attributeSet: AttributeSet) :
    View(context, attributeSet) {

    private val dashPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val arcPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    // 仪盘小刻度
    private val dash = Path()

    // 弧线
    private val path = Path()
    private lateinit var pathEffect: PathEffect

    init {
        dashPaint.strokeWidth = 3f.px
        dashPaint.style = Paint.Style.STROKE
        arcPaint.strokeWidth = 3f.px
        arcPaint.style = Paint.Style.STROKE
        dash.addRect(0f, 0f, DASH_WIDTH, DASH_LENGTH, Path.Direction.CCW)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        path.reset()
        path.addArc(
            width / 2f - 150f.px,
            height / 2f - 150f.px,
            width / 2f + 150f.px,
            height / 2f + 150f.px,
            90 + OPEN_ANGEL / 2,
            360 - OPEN_ANGEL
        )
        // 要平均多少个刻度，就必须先取得总长度
        val pathMeasure = PathMeasure(path, false)
        // 设置Path效果 -> 直线，虚线，或者XX
        pathEffect = PathDashPathEffect(
            dash,
            (pathMeasure.length - DASH_WIDTH) / 20f,
            0f,
            PathDashPathEffect.Style.ROTATE
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 画弧
        canvas.drawPath(path, arcPaint)
        // 画弧,首先得出所在的范围，然后是起始角度以及扫描角度范围，useCenter -> false ,则为半个椭圆，true 则为扇形
        // canvas.drawArc(width/2f -150f.px,height/2f -150f.px,width/2f + 150f.px , height/2f + 150f.px, 90+ OPEN_ANGEL/2,360 - OPEN_ANGEL,false,dashPaint)
        // 画弧线刻度
        dashPaint.pathEffect = pathEffect
        canvas.drawPath(path, dashPaint)
        // 画指向线
        canvas.drawLine(
            width / 2f, height / 2f,
            width / 2f + LENGTH * cos(markToRadians(MARK)).toFloat(),
            height / 2f + LENGTH * sin(markToRadians(MARK)).toFloat(), arcPaint
        )
    }

    private fun markToRadians(mark: Int) =
        Math.toRadians((90 + OPEN_ANGEL / 2f + (360 - OPEN_ANGEL) / 20f * mark).toDouble())
}