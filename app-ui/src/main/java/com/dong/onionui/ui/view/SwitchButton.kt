/*
 * Copyright 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dong.onionui.ui.view

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.AppCompatImageView
import com.dong.onionui.R
import com.dong.onionui.helper.AndroidUtilities

// ------------------------------------------------------------------------
// IOS风格 Switch
// ------------------------------------------------------------------------
class SwitchButton @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
  defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {


  // ---- 滑块阴影颜色 ------
  @ColorInt var thumbBgShadowColor: Int = Color.BLACK
  // ---- 滑块阴影Y轴偏移量 ------
  var thumbShadowDy: Int = 1
  // ---- 滑块阴影X轴偏移量 ------
  var thumbShadowDx: Int = 2
  // ---- 滑块阴影圆角半径 ------
  var thumbShadowRadius: Int = 2


  // ---- 滑动区域,开启时背景颜色 ------
  @ColorInt var trackOnBgColor: Int = context.getColor(R.color.text_color_secondary)
  // ---- 滑动区域,关闭时背景颜色 ------
  @ColorInt var trackOffBgColor: Int = context.getColor(R.color.theme_color_primary)
  // ---- 滑动区域,宽度 ------
  @Dimension var trackWidth: Int = AndroidUtilities.dp(44f)
  // ---- 滑动区域,长度 ------
  @Dimension var trackHeight: Int = AndroidUtilities.dp(24f)
  // ---- 滑动区域,圆角 ------
  @Dimension var trackBgRadius: Int = AndroidUtilities.dp(24f) / 2

  // ---- 按钮,开启时颜色 ------
  @ColorInt var thumbOnBgColor: Int = context.getColor(R.color.theme_color_on_primary)
  // ---- 按钮,关闭时颜色 ------
  @ColorInt var thumbOffBgColor: Int = context.getColor(R.color.theme_color_on_primary)
  // ---- 按钮,半径 ------
  @Dimension var thumbRadius: Int = AndroidUtilities.dp(10f)
  // ---- 按钮,大小 ------
  @Dimension var thumbSize: Int = thumbRadius * 2

  // ---- 按钮开启状态 ------
  private var isOpened: Boolean = false
  // ---- 按钮开启之前状态 ------
  var isOpenedLast: Boolean = !isOpened


  var isEnableThumbShadow: Boolean = false


  // ---- 按钮动画引擎 ------
  val thumbAnimator: ValueAnimator by lazy { thumbAnimator() }
  // ---- 动画系数 ------
  private var animatedFraction: Float = 1f
  // ---- 按钮偏移百分比 ------
  private var thumbOffsetParent: Float = 0f
  // ---- 滑动区域透明度变化 ------
  private var bgAlpha: Int = 0
  private fun thumbAnimator(): ValueAnimator {
    val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
    valueAnimator.duration = 300L
    valueAnimator.addUpdateListener {
      animatedFraction = it.animatedFraction
      thumbOffsetParent = it.animatedValue as Float
      bgAlpha = ((it.animatedValue as Float) * 255).toInt()
      postInvalidate()
    }
    return valueAnimator
  }

  // ------------------------------------------------------------------------
  // 设置按钮初始状态
  // ------------------------------------------------------------------------
  private fun initButtonState() {
    if (isOpened) {
      bgAlpha = 255
      animatedFraction = 0f
      thumbOffsetParent = 1.0f
    } else {
      bgAlpha = 0
      animatedFraction = 1f
      thumbOffsetParent = 0f
    }
  }


  // ------------------------------------------------------------------------
  // 滑动区域画笔
  // ------------------------------------------------------------------------
  private var trackOnPaint: Paint = Paint()
  private var trackOffPaint: Paint = Paint()
  private fun initTrackPaint() {
    trackOnPaint.style = Paint.Style.FILL
    trackOnPaint.strokeJoin = Paint.Join.ROUND
    trackOnPaint.strokeCap = Paint.Cap.ROUND
    trackOnPaint.color = trackOnBgColor
    trackOnPaint.isAntiAlias = true
    trackOnPaint.isDither = true

    trackOffPaint.style = Paint.Style.FILL
    trackOffPaint.strokeJoin = Paint.Join.ROUND
    trackOffPaint.strokeCap = Paint.Cap.ROUND
    trackOffPaint.color = trackOffBgColor
    trackOffPaint.isAntiAlias = true
    trackOffPaint.isDither = true
  }

  // ------------------------------------------------------------------------
  // 按钮画笔
  // ------------------------------------------------------------------------
  private var thumbShadowPaint = Paint()
  private var thumbOnBgPaint: Paint = Paint()
  private var thumbOffBgPaint: Paint = Paint()
  private fun initThumbPaint() {
    thumbOnBgPaint.isAntiAlias = true
    thumbOnBgPaint.isDither = true
    thumbOnBgPaint.style = Paint.Style.FILL
    thumbOnBgPaint.strokeJoin = Paint.Join.ROUND
    thumbOnBgPaint.strokeCap = Paint.Cap.ROUND
    thumbOnBgPaint.color = thumbOnBgColor

    thumbOffBgPaint.isAntiAlias = true
    thumbOffBgPaint.isDither = true
    thumbOffBgPaint.style = Paint.Style.FILL
    thumbOffBgPaint.strokeJoin = Paint.Join.ROUND
    thumbOffBgPaint.strokeCap = Paint.Cap.ROUND
    thumbOffBgPaint.color = thumbOffBgColor

    thumbShadowPaint.isAntiAlias = true
    thumbShadowPaint.isDither = true
    thumbShadowPaint.style = Paint.Style.FILL
    thumbShadowPaint.strokeJoin = Paint.Join.ROUND
    thumbShadowPaint.strokeCap = Paint.Cap.ROUND
  }

  init{
    initButtonState()
    initTrackPaint()
    initThumbPaint()
  }


  // ------------------------------------------------------------------------
  // 测量宽高
  // ------------------------------------------------------------------------
  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    val defaultWidth = trackWidth
    val defaultHeight = calculateHeight()
    val width = measureSize(widthMeasureSpec, defaultWidth)
    val height = measureSize(heightMeasureSpec, defaultHeight)
    setMeasuredDimension(width, height)
  }

  // ------------------------------------------------------------------------
  // 根据测量规格进行测量
  // ------------------------------------------------------------------------
  private fun measureSize(measureSpec: Int, defaultSize: Int): Int {
    var result: Int
    val mode = MeasureSpec.getMode(measureSpec)
    val size = MeasureSpec.getSize(measureSpec)
    if (mode == MeasureSpec.EXACTLY) {
      result = size
    } else {
      result = defaultSize
      if (mode == MeasureSpec.AT_MOST) result = result.coerceAtMost(size)
    }
    return result
  }

  // ------------------------------------------------------------------------
  // 精确测量高度
  // ------------------------------------------------------------------------
  private fun calculateHeight(): Int {
    var height = if (thumbSize > trackHeight) {
      thumbSize
    } else if (thumbSize + thumbShadowSize > trackHeight) {
      thumbSize
    } else {
      trackHeight
    }
    if (isEnableThumbShadow && height < thumbSize + thumbShadowSize) {
      height = +thumbShadowSize
    }
    return height
  }


  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    super.onSizeChanged(w, h, oldw, oldh)
    initTrackPath()
    initThumbConfig()
  }


  // ------------------------------------------------------------------------
  // 滑动区域画笔路径
  // ------------------------------------------------------------------------
  private var trackPath: Path = Path()
  private var trackRectF = RectF()
  private fun initTrackPath() {
    trackPath.reset()
    trackRectF.left = (width - trackWidth.toFloat()) / 2
    trackRectF.right = this.trackRectF.left + trackWidth
    trackRectF.top = (height - trackHeight.toFloat()) / 2
    trackRectF.bottom = this.trackRectF.top + trackHeight
    trackPath.addRoundRect(
      this.trackRectF,
      trackBgRadius.toFloat(),
      trackBgRadius.toFloat(),
      Path.Direction.CW
    )
  }

  // ------------------------------------------------------------------------
  // 按钮画笔路径
  // ------------------------------------------------------------------------
  private var thumbOffCenterX: Float = 0f
  private var thumbOnCenterX: Float = 0f
  private var thumbCenterY: Float = 0f
  private var thumbShadowSize: Int = 0
  private var thumbTotalOffset: Float = 0f
  private fun initThumbConfig() {
    var padding = (height - thumbSize) * 0.5f
    if (isEnableThumbShadow) {
      padding -= (thumbShadowSize / 2)
    }
    thumbOffCenterX = padding + thumbSize * 0.5f
    thumbOnCenterX = width - padding - thumbSize * 0.5f
    thumbCenterY = height * 0.5f
    thumbTotalOffset = width - padding - thumbOffCenterX - thumbSize * 0.5f
    thumbShadowSize = (thumbSize - thumbShadowRadius * 3)
  }


  // ------------------------------------------------------------------------
  // 绘制
  // ------------------------------------------------------------------------
  @SuppressLint("DrawAllocation")
  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    canvas.drawFilter = PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
    onDrawToggleTrack(canvas)
    onDrawToggleThumbShadow(canvas)
    onDrawToggleThumb(canvas)
  }


  // ------------------------------------------------------------------------
  // 绘制滑动区域
  // ------------------------------------------------------------------------
  private fun onDrawToggleTrack(canvas: Canvas) {
    canvas.save()
    canvas.drawPath(trackPath, trackOffPaint)
    trackOnPaint.alpha = bgAlpha
    canvas.drawPath(trackPath, trackOnPaint)
    canvas.restore()
  }

  // ------------------------------------------------------------------------
  // 绘制阴影
  // ------------------------------------------------------------------------
  private var thumbShadowPath: Path = Path()
  private fun onDrawToggleThumbShadow(canvas: Canvas) {
    if (!isEnableThumbShadow) {
      return
    }
    thumbShadowPath.reset()
    val alpha = animatedFraction * 255
    val showDx = if (isOpenedLast) {
      thumbShadowDx.toFloat()
    } else {
      -thumbShadowDx.toFloat()
    }
    val centerX: Float = thumbOffCenterX + thumbOffsetParent * thumbTotalOffset
    thumbShadowPaint.alpha = alpha.toInt()
    thumbShadowPaint.setShadowLayer(
      thumbShadowRadius.toFloat(),
      showDx,
      thumbShadowDy.toFloat(),
      thumbBgShadowColor
    )
    thumbShadowPath.addCircle(centerX, thumbCenterY, thumbShadowSize * 0.5f, Path.Direction.CW)
    canvas.save()
    canvas.drawPath(thumbShadowPath, thumbShadowPaint)
    canvas.restore()
  }

  // ------------------------------------------------------------------------
  // 绘制按钮
  // ------------------------------------------------------------------------
  private var thumbPath: Path = Path()
  private fun onDrawToggleThumb(canvas: Canvas) {
    thumbPath.reset()
    var centerX = thumbOffCenterX + thumbOffsetParent * thumbTotalOffset
    thumbPath.addCircle(centerX, thumbCenterY, thumbSize * 0.5f, Path.Direction.CW)

    canvas.save()
    canvas.drawPath(thumbPath, thumbOffBgPaint)
    thumbOnBgPaint.alpha = bgAlpha
    canvas.drawPath(thumbPath, thumbOnBgPaint)
    canvas.restore()
  }

  // ------------------------------------------------------------------------
  // 触摸事件
  // ------------------------------------------------------------------------
  override fun onTouchEvent(event: MotionEvent): Boolean {
    //如果还在做动画，则不允许点击
    if (thumbOffsetParent != 0f && thumbOffsetParent != 1f) {
      return true
    }
    when (event.action) {
      MotionEvent.ACTION_DOWN -> {
        performClick()
        return true
      }
      MotionEvent.ACTION_UP -> {
        if (toggleSwitch(!isOpened)) {
          callOnClick()
        }
      }
    }
    return super.onTouchEvent(event)
  }

  // ------------------------------------------------------------------------
  // 切换开关
  // ------------------------------------------------------------------------
  private fun toggleSwitch(isOpen: Boolean): Boolean {
    if (this.isOpened == isOpen) {
      return false
    }
    this.isOpenedLast = isOpened
    this.isOpened = isOpen

    if (!isOpenedLast) {
      //off->on
      thumbAnimator.setFloatValues(0f, 1f)
    } else {
      //on->off
      thumbAnimator.setFloatValues(1f, 0f)
    }
    thumbAnimator.start()

    return true
  }

}
