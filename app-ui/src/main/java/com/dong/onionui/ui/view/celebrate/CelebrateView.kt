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

class PartySystem(val party: Party, val createdAt: Long = System.currentTimeMillis(), pixelDensity: Float = Resources.getSystem().displayMetrics.density) {

    var enabled = true

    private var emitter: BaseEmitter = PartyEmitter(party.emitter, pixelDensity)

    private val activeParticles = mutableListOf<Confetti>()

    // Called every frame to create and update the particles state
    // returns a list of particles that are ready to be rendered
    fun render(deltaTime: Float, drawArea: Rect): List<Particle> {
        if (enabled) {
            activeParticles.addAll(emitter.createConfetti(deltaTime, party, drawArea))
        }

        activeParticles.forEach { it.render(deltaTime, drawArea) }

        activeParticles.removeAll { it.isDead() }

        return activeParticles.filter { it.drawParticle }.map { it.toParticle() }
    }

    /**
     * When the emitter is done emitting.
     * @return true if the emitter is done emitting or false when it's still busy or needs to start
     * based on the delay
     */
    fun isDoneEmitting(): Boolean = (emitter.isFinished() && activeParticles.size == 0) || (!enabled && activeParticles.size == 0)
}


// ------------------------------------------------------------------------
// TimerIntegration 检索自上一帧渲染以来的增量时间。如果发生任何丢帧，则使用 Delta 时间正确绘制五彩纸屑
// ------------------------------------------------------------------------
class TimerIntegration {
    private var previousTime: Long = -1L

    fun reset() {
        previousTime = -1L
    }

    fun getDeltaTime(): Float {
        if (previousTime == -1L) previousTime = System.nanoTime()

        val currentTime = System.nanoTime()
        val dt = (currentTime - previousTime) / 1000000f
        previousTime = currentTime
        return dt / 1000
    }

    fun getTotalTimeRunning(startTime: Long): Long {
        val currentTime = System.currentTimeMillis()
        return (currentTime - startTime)
    }
}
