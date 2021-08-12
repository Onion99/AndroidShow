package com.onion.android.customview.drawtext

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.onion.android.app.view.dp

private val RING_WIDTH = 20.dp
private val RADIUS = 150.dp

class SportView(context: Context, attributeSet: AttributeSet) :
    View(context, attributeSet) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 绘制环
        paint.style = Paint.Style.STROKE
        paint.color = Color.GRAY
        paint.strokeWidth = RING_WIDTH
        canvas.drawCircle(width / 2f, height / 2f, RADIUS, paint)
        // 绘制进度条
        paint.color = Color.BLUE
        // 控制如何处理描边线和路径的开始和结束
        paint.strokeCap = Paint.Cap.ROUND
        canvas.drawArc(
            width / 2f - RADIUS,
            height / 2f - RADIUS,
            height / 2f + RADIUS,
            height / 2f + RADIUS,
            -90f,
            225f,
            false,
            paint
        )
    }
}