package com.dong.onionui.ui.view.celebrate

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.dong.onionui.ui.view.celebrate.emitter.BaseEmitter
import com.dong.onionui.ui.view.celebrate.emitter.PartyEmitter

open class CelebrateView : View{
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    // ---- 主动粒子系统 ------
    private val systems: MutableList<PartySystem> = mutableListOf()
    // ---- 跟踪帧渲染之间的增量时间 ------
    private var timer: TimerIntegration = TimerIntegration()
    // ---- 绘制区域 ------
    private var drawArea = Rect()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 获取增量时间
        val deltaTime = timer.getDeltaTime()
        for (i in systems.size - 1 downTo 0) {
            val partySystem = systems[i]

            val totalTimeRunning = timer.getTotalTimeRunning(partySystem.createdAt)
            if (totalTimeRunning >= partySystem.party.delay) {
                partySystem.render(deltaTime, drawArea).forEach {
                    it.display(canvas)
                }
            }

            if (partySystem.isDoneEmitting()) {
                systems.removeAt(i)
            }
        }
        if (systems.size != 0) {
            invalidate()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        drawArea = Rect(0, 0, w, h)
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        timer.reset()
    }

    // ---- 画笔 ------
    private val paint: Paint = Paint()
    private fun Particle.display(canvas: Canvas) {
        // 通过 paint.setAlpha 设置 alpha 分配一个临时的“ColorSpaceNamed”对象
        // 通过 setColor 更有效
        paint.color = color

        val centerX = scaleX * width / 2
        val saveCount = canvas.save()
        canvas.translate(x - centerX, y)
        canvas.rotate(rotation, centerX, width / 2)
        canvas.scale(scaleX, 1f)

        shape.draw(canvas, paint, width)
        canvas.restoreToCount(saveCount)
    }


    // ------------------------------------------------------------------------
    // 展示庆祝动画
    // ------------------------------------------------------------------------
    fun start(party: List<Party>) {
        systems.addAll(
            party.map {
                PartySystem(it)
            }
        )
        invalidate()
    }
}

// ---- 粒子渲染系统 ------
class PartySystem(val party: Party, val createdAt: Long = System.currentTimeMillis(), pixelDensity: Float = Resources.getSystem().displayMetrics.density) {

    var enabled = true

    private var emitter: BaseEmitter = PartyEmitter(party.emitter, pixelDensity)

    private val activeParticles = mutableListOf<Confetti>()

    // ------------------------------------------------------------------------
    // 调用每个帧来创建和更新粒子状态，返回准备渲染的粒子列表
    // ------------------------------------------------------------------------
    fun render(deltaTime: Float, drawArea: Rect): List<Particle> {
        if (enabled) {
            activeParticles.addAll(emitter.createConfetti(deltaTime, party, drawArea))
        }

        activeParticles.forEach { it.render(deltaTime, drawArea) }

        activeParticles.removeAll { it.isDead() }

        return activeParticles.filter { it.drawParticle }.map { it.toParticle() }
    }

    // ------------------------------------------------------------------------
    // 如果发射器发射完毕，则为true；
    // 如果发射器仍然繁忙或需要根据延迟启动，则为false
    // ------------------------------------------------------------------------
    fun isDoneEmitting(): Boolean = (emitter.isFinished() && activeParticles.size == 0) || (!enabled && activeParticles.size == 0)
}


// ------------------------------------------------------------------------
// TimerIntegration 检索自渲染前一帧以来的增量时间。如果发生任何帧丢失，则使用增量时间正确绘制彩色纸屑
// ------------------------------------------------------------------------
class TimerIntegration {

    // ---- 上一次绘制的时间 ------
    private var previousTime: Long = -1L

    fun reset() {
        previousTime = -1L
    }

    // ------------------------------------------------------------------------
    // 获取增量时间
    // ------------------------------------------------------------------------
    fun getDeltaTime(): Float {
        // 比较当前时间和上一次绘制的时间 (纳秒时间: https://www.cnblogs.com/somefuture/p/13690961.html) 毫秒是纳秒的10的6次方
        if (previousTime == -1L) previousTime = System.nanoTime()

        val currentTime = System.nanoTime()
        // 取得增量时间
        val deltaTime = (currentTime - previousTime) / 1000000f
        previousTime = currentTime
        return deltaTime / 1000
    }

    // ------------------------------------------------------------------------
    // 获取运行时间
    // ------------------------------------------------------------------------
    fun getTotalTimeRunning(startTime: Long): Long {
        val currentTime = System.currentTimeMillis()
        return (currentTime - startTime)
    }
}
