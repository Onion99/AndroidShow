package com.onion.android.customview.second_xfermode

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.onion.android.R
import com.onion.android.app.view.px


private val IMAGE_WIDTH = 200f.px
private val IMAGE_PADDING = 20f.px
private val XFERMDOE = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

///////////////////////////////////////////////////////////////////////////
// xfermode: https://developer.android.com/reference/android/graphics/PorterDuff.Mode
///////////////////////////////////////////////////////////////////////////
class AvatarView(context: Context, attributeSet: AttributeSet) :
    View(context, attributeSet) {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    // 缓冲画布范围，（越大越耗性能）
    val bounds = RectF(
        IMAGE_PADDING, IMAGE_PADDING,
        IMAGE_PADDING + IMAGE_WIDTH, IMAGE_PADDING + IMAGE_WIDTH
    )

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas) {
        // Canvs.saveLayer() 把绘制区域拉到单独的离屏缓冲(单独的画布)⾥
        val cacheCanvasTag = canvas.saveLayer(bounds, null)
        // 绘制圆形
        canvas.drawOval(
            IMAGE_PADDING, IMAGE_PADDING,
            IMAGE_PADDING + IMAGE_WIDTH, IMAGE_PADDING + IMAGE_WIDTH, paint
        )
        paint.xfermode = XFERMDOE
        // 绘制图片
        canvas.drawBitmap(
            getAvatar(IMAGE_WIDTH.toInt()),
            IMAGE_PADDING, IMAGE_PADDING, paint
        )
        paint.xfermode = null
        canvas.restoreToCount(cacheCanvasTag)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Bitmap读取优化
    ///////////////////////////////////////////////////////////////////////////
    fun getAvatar(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        // 允许调用者查询位图而无需为其像素分配内存
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.mipmap.ic_avatar, options)
        // 确认好目标尺寸
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.mipmap.ic_avatar, options)
    }
}