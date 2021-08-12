package com.onion.android.customview.second_xfermode

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.onion.android.app.view.px

///////////////////////////////////////////////////////////////////////////
// 用于Xfermode
///////////////////////////////////////////////////////////////////////////
class XfermodeView(context: Context, attributeSet: AttributeSet) :
    View(context, attributeSet) {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC)

    private val bounds = RectF(150f.px, 50f.px, 300f.px, 200f.px)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas) {
        // 这样做很容易，就将每个绘制区块的透明区域给忽略，导致使用Xfermode使用一场
        /*val singleCanvasTag = canvas.saveLayer(bounds,paint)
        // 圆
        paint.color = Color.DKGRAY
        canvas.drawOval(200f.px,50f.px,300f.px,150f.px,paint)
        paint.xfermode = xfermode
        // 矩形
        paint.color = Color.RED
        canvas.drawRect(150f.px,100f.px,250f.px,200f.px,paint)

        canvas.restoreToCount(singleCanvasTag)*/
        // 正确的做法
        val singleCanvasTag = canvas.saveLayer(bounds, paint)
        canvas.drawBitmap(circleBitmap, 150f.px, 50f.px, paint)
        paint.xfermode = xfermode
        canvas.drawBitmap(squareBitmap, 150f.px, 50f.px, paint)

        canvas.restoreToCount(singleCanvasTag)
    }

    private val circleBitmap = Bitmap.createBitmap(
        150f.px.toInt(), 150f.px.toInt(), Bitmap.Config.ARGB_8888
    )
    private val squareBitmap = Bitmap.createBitmap(
        150f.px.toInt(), 150f.px.toInt(), Bitmap.Config.ARGB_8888
    )

    // 一开始绘制Bitmap
    init {
        val canvas = Canvas(circleBitmap)
        paint.color = Color.DKGRAY
        canvas.drawOval(50f.px, 0f.px, 150f.px, 100f.px, paint)
        paint.color = Color.RED
        canvas.setBitmap(squareBitmap)
        canvas.drawRect(0f.px, 50f.px, 100f.px, 150f.px, paint)
    }
}