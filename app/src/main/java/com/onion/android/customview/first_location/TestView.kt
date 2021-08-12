package com.onion.android.customview.first_location

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.util.AttributeSet
import android.view.View

class TestView(context: Context, attributeSet: AttributeSet) :
    View(context, attributeSet) {
    // 画笔，一般默认是不开启抗锯齿的，因为默认是要真实的绘制，开了抗锯齿虽美观，但不真实
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    // 路径，用于自定义图形
    private val path = Path()

    // 测量对象，可用于获取相关对象绘制的长度
    private lateinit var pathMeasure: PathMeasure

    ///////////////////////////////////////////////////////////////////////////
    // View尺寸改变的时候的回调，可以在这里重新初始化Paint，Path
    ///////////////////////////////////////////////////////////////////////////
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        path.reset()
        // 绘制方向  ccw -> counter-clockwise 逆时针 ，clockwise 顺时针
        path.addCircle(width / 2f, height / 2f, RADIUS, Path.Direction.CCW)
        path.addRect(
            width / 2f - RADIUS,
            height / 2f,
            width / 2f + RADIUS,
            height / 2f + 2 * RADIUS,
            Path.Direction.CCW
        )
        path.addCircle(width / 2f, height / 2f, RADIUS * 1.5f, Path.Direction.CCW)
        pathMeasure = PathMeasure(path, false)
        // 填充规则 - 一般用于镂空
        // EVEN_ODD 相交为空，无需根据绘制方法判断。一般用这个
        // WINDING 根据绘制相交方向,以及相交点做处理，较复杂，没必要过多关注这个
        path.fillType = Path.FillType.EVEN_ODD
    }

    ///////////////////////////////////////////////////////////////////////////
    // 绘制内容，不要在初始化对象，变量，这样太浪费性能
    ///////////////////////////////////////////////////////////////////////////
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 画线
        // canvas.drawLine(100f, 100f, 200f, 200f,paint)
        // 画圆
        // canvas.drawCircle(width/2f,height/2f,66f.px,paint)
        // 画自定义图形
        canvas.drawPath(path, paint)
    }
}