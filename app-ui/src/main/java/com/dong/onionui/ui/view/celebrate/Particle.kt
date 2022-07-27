package com.dong.onionui.ui.view.celebrate

import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Build
import com.dong.onionui.ui.view.celebrate.Shape.Circle.rect

/**
 * 粒子数据以及如何绘制粒子
 * @param x 画布上的绝对 x 位置
 * @param y 画布上的绝对 y 位置
 * @param width 五彩纸屑的当前宽度
 * @param height 五彩纸屑的当前高度
 * @param color 将用于绘制五彩纸屑的颜色
 * @param rotation 五彩纸屑的当前旋转度数
 * @param scaleX 用于创建 3D 旋转的五彩纸屑的当前比例
 * @param shape 五彩纸屑的形状，例如圆形、自定义形状的正方形
 * @param alpha 五彩纸屑的透明度在 0 - 255 之间

 */
data class Particle(
    val x: Float,
    val y: Float,
    val width: Float,
    val height: Float,
    val color: Int,
    val rotation: Float,
    val scaleX: Float,
    val shape: Shape,
    val alpha: Int
)



interface Shape {
    object Circle : Shape {
        val rect = RectF()
    }
    object Square : Shape

    class Rectangle(
        // ---- 高宽比。必须在0, 1范围内 ------
        val heightRatio: Float
    ) : Shape {
        init {
            require(heightRatio in 0f..1f)
        }
    }
    data class DrawableShape(
        val drawable: Drawable,
        // ---- 设置为false以选择不着色可绘制对象，保持其原始颜色。 ------
        val tint: Boolean = true
    ) : Shape{
        val heightRatio =
            if (drawable.intrinsicHeight == -1 && drawable.intrinsicWidth == -1) {
                // If the drawable has no intrinsic size, fill the available space.
                1f
            } else if (drawable.intrinsicHeight == -1 || drawable.intrinsicWidth == -1) {
                // Currently cannot handle a drawable with only one intrinsic dimension.
                0f
            } else {
                drawable.intrinsicHeight.toFloat() / drawable.intrinsicWidth
            }
    }
}

fun Shape.draw(canvas: Canvas, paint: Paint, size: Float) {

    when (this) {
        Shape.Square -> canvas.drawRect(0f, 0f, size, size, paint)
        Shape.Circle -> {
            rect.set(0f, 0f, size, size)
            canvas.drawOval(rect, paint)
        }
        is Shape.Rectangle -> {
            val height = size * heightRatio
            val top = (size - height) / 2f
            canvas.drawRect(0f, top, size, top + height, paint)
        }
        is Shape.DrawableShape -> {
            if (tint) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    drawable.colorFilter = BlendModeColorFilter(paint.color, BlendMode.SRC_IN)
                } else {
                    drawable.setColorFilter(paint.color, PorterDuff.Mode.SRC_IN)
                }
            } else {
                drawable.alpha = paint.alpha
            }

            val height = (size * heightRatio).toInt()
            val top = ((size - height) / 2f).toInt()

            drawable.setBounds(0, top, size.toInt(), top + height)
            drawable.draw(canvas)
        }
    }
}