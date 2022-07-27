package com.dong.onionui.ui.view.celebrate.emitter

import android.graphics.Rect
import com.dong.onionui.ui.view.celebrate.Confetti
import com.dong.onionui.ui.view.celebrate.Party
import java.util.concurrent.TimeUnit

// ------------------------------------------------------------------------
// 用于创建自定义发射器的抽象类发射器决定是否应创建粒子以及何时完成发射器
// ------------------------------------------------------------------------
abstract class BaseEmitter {

    // ------------------------------------------------------------------------
    // 当RenderSystem处于活动状态时，每次更新都会调用此函数 保持此函数尽可能轻，否则会减慢渲染系统的速度
    // ------------------------------------------------------------------------
    abstract fun createConfetti(
        deltaTime: Float,
        party: Party,
        drawArea: Rect,
    ): List<Confetti>

    // ------------------------------------------------------------------------
    // 如果发射器不再创建任何粒子，则返回 true 如果仍然很忙，则返回 false
    // ------------------------------------------------------------------------
    abstract fun isFinished(): Boolean
}

/**
 * Emitter class that holds the duration that the emitter will create confetti particles
 */
data class Emitter(
    val duration: Long,
    val timeUnit: TimeUnit = TimeUnit.MILLISECONDS
) {
    /**
     * Max amount of particles that will be created over the duration that is set
     */
    fun max(amount: Int): EmitterConfig = EmitterConfig(this).max(amount)
}

/**
 * EmitterConfig class that will gold the Emitter configuration and amount of particles that
 * will be created over certain time
 */
class EmitterConfig(
    emitter: Emitter
) {

    /** Max time allowed to emit in milliseconds */
    var emittingTime: Long = 0

    /** Amount of time needed for each particle creation in milliseconds */
    var amountPerMs: Float = 0f

    init {
        val (duration, timeUnit) = emitter
        this.emittingTime = TimeUnit.MILLISECONDS.convert(duration, timeUnit)
    }

    /**
     * Amount of particles created over the duration that is set
     */
    fun max(amount: Int): EmitterConfig {
        this.amountPerMs = (emittingTime / amount) / 1000f
        return this
    }

    /**
     * Amount of particles that will be created per second
     */
    fun perSecond(amount: Int): EmitterConfig {
        this.amountPerMs = 1f / amount
        return this
    }
}