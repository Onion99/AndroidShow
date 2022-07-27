package com.dong.onionui.ui.view.celebrate.emitter

import android.graphics.Rect
import com.dong.onionui.ui.view.celebrate.*
import com.dong.onionui.ui.view.celebrate.Vector
import java.lang.Math.toRadians
import java.util.*
import kotlin.math.cos
import kotlin.math.sin

// ------------------------------------------------------------------------
// 发射器负责在每个滴答声中创建一定数量的粒子。
// - 在特定时间范围内创建 x 数量的粒子
// - 创建 x 数量的粒子，直到达到阈值maxParticles
// ------------------------------------------------------------------------
class PartyEmitter(
    private val emitterConfig: EmitterConfig,
    private val pixelDensity: Float,
    private val random: Random = Random()
) : BaseEmitter() {

    // ---- 计算在运行发射器时创建了多少粒子 ------
    private var particlesCreated = 0

    // ---- 以毫秒为单位的经过时间 ------
    private var elapsedTime: Float = 0f

    // ---- 自上次粒子创建以来经过的时间量（以毫秒为单位） ------
    private var createParticleMs: Float = 0f

    // ------------------------------------------------------------------------
    // 如果计时器尚未启动，则设置初始启动时间立即创建第一个五彩纸屑并更新最后一个发射时间
    // ------------------------------------------------------------------------
    override fun createConfetti(deltaTime: Float, party: Party, drawArea: Rect): List<Confetti> {
        createParticleMs += deltaTime

        // 初始 deltaTime 不能高于 emittingTime，如果是，则根据最大 emittingTime 计算粒子数量
        val emittingTime = emitterConfig.emittingTime / 1000f
        if (elapsedTime == 0f && deltaTime > emittingTime) {
            createParticleMs = emittingTime
        }

        var particles = listOf<Confetti>()

        // 检查是否应该创建粒子
        if (createParticleMs >= emitterConfig.amountPerMs && !isTimeElapsed()) {
            // 计算在经过的时间内要创建多少个粒子
            val amount: Int = (createParticleMs / emitterConfig.amountPerMs).toInt()

            particles = (1..amount).map { createParticle(party, drawArea) }

            // 重置计时器并为下一个周期添加剩余时间
            createParticleMs %= emitterConfig.amountPerMs
        }

        elapsedTime += deltaTime * 1000
        return particles
    }

    /**
     * 根据Party配置创建粒子
     * @param party 用于创建初始 Confetti 状态的配置
     * @param drawArea 画布的面积和大小
     */
    private fun createParticle(party: Party, drawArea: Rect): Confetti {
        particlesCreated++
        with(party) {
            val randomSize = size[random.nextInt(size.size)]
            return Confetti(
                location = position.get(drawArea).run { Vector(x, y) },
                width = randomSize.sizeInDp * pixelDensity,
                mass = randomSize.massWithVariance(),
                shape = getRandomShape(party.shapes),
                color = colors[random.nextInt(colors.size)],
                lifespan = timeToLive,
                fadeOut = fadeOutEnabled,
                velocity = getVelocity(),
                damping = party.damping,
                rotationSpeed2D = rotation.rotationSpeed() * party.rotation.multiplier2D,
                rotationSpeed3D = rotation.rotationSpeed() * party.rotation.multiplier3D,
                pixelDensity = pixelDensity
            )
        }
    }

    // ------------------------------------------------------------------------
    // 根据基数和方差计算转速乘数
    // ------------------------------------------------------------------------
    private fun Rotation.rotationSpeed(): Float {
        if (!enabled) return 0f
        val randomValue = random.nextFloat() * 2f - 1f
        return speed + (speed * variance * randomValue)
    }

    private fun Party.getSpeed(): Float =
        if (maxSpeed == -1f) speed
        else ((maxSpeed - speed) * random.nextFloat()) + speed

    // ------------------------------------------------------------------------
    // 获得带有轻微变化的质量，以在每个粒子在向上或向下移动时的速度反应之间产生随机性
    // ------------------------------------------------------------------------
    private fun Size.massWithVariance(): Float = mass + (mass * (random.nextFloat() * massVariance))

    // ------------------------------------------------------------------------
    // 根据弧度和速度计算速度
    // ------------------------------------------------------------------------
    private fun Party.getVelocity(): Vector {
        val speed = getSpeed()
        val radian = toRadians(getAngle())
        val vx = speed * cos(radian).toFloat()
        val vy = speed * sin(radian).toFloat()
        return Vector(vx, vy)
    }

    private fun Party.getAngle(): Double {
        if (spread == 0) return angle.toDouble()

        val minAngle = angle - (spread / 2)
        val maxAngle = angle + (spread / 2)
        return (maxAngle - minAngle) * random.nextDouble() + minAngle
    }

    private fun Position.get(drawArea: Rect): Position.Absolute {
        return when (this) {
            is Position.Absolute -> Position.Absolute(x, y)
            is Position.Relative -> {
                Position.Absolute(
                    drawArea.width() * x.toFloat(),
                    drawArea.height() * y.toFloat()
                )
            }
            is Position.between -> {
                val minPos = min.get(drawArea)
                val maxPos = max.get(drawArea)
                return Position.Absolute(
                    x = random.nextFloat().times(maxPos.x.minus(minPos.x)) + minPos.x,
                    y = random.nextFloat().times(maxPos.y.minus(minPos.y)) + minPos.y
                )
            }
        }
    }

    // ------------------------------------------------------------------------
    // 当形状是 DrawableShape 时，改变可绘制对象，以便所有可绘制对象在画布上绘制时具有不同的值。
    // ------------------------------------------------------------------------
    private fun getRandomShape(shapes: List<Shape>): Shape {
        return when (val shape = shapes[random.nextInt(shapes.size)]) {
            is Shape.DrawableShape -> {
                val mutatedState =
                    shape.drawable.constantState?.newDrawable()?.mutate() ?: shape.drawable
                shape.copy(drawable = mutatedState)
            }
            else -> shape
        }
    }

    // ------------------------------------------------------------------------
    // 如果持续时间为 0，则未设置且不相关如果设置了发射时间，请检查elapsedTime是否超过了 emittingTime
    // ------------------------------------------------------------------------
    private fun isTimeElapsed(): Boolean {
        return when (emitterConfig.emittingTime) {
            0L -> false
            else -> elapsedTime >= emitterConfig.emittingTime
        }
    }

    override fun isFinished(): Boolean {
        return if (emitterConfig.emittingTime > 0L) {
            elapsedTime >= emitterConfig.emittingTime
        } else false
    }
}