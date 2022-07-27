package com.dong.onionui.ui.view.celebrate

import com.dong.onionui.ui.view.celebrate.emitter.EmitterConfig

/**
 * Factory class to enable builder methods for Java implementations
 * See [Party] for documentation on the configuration settings
 */
class PartyFactory(val emitter: EmitterConfig) {

    private var party: Party = Party(emitter = emitter)

    fun angle(angle: Int): PartyFactory {
        party = party.copy(angle = angle)
        return this
    }

    fun spread(spread: Int): PartyFactory {
        party = party.copy(spread = spread)
        return this
    }

    fun setSpeed(speed: Float): PartyFactory {
        party = party.copy(speed = speed)
        return this
    }

    fun setSpeedBetween(minSpeed: Float, maxSpeed: Float): PartyFactory {
        party = party.copy(speed = minSpeed, maxSpeed = maxSpeed)
        return this
    }

    fun setDamping(damping: Float): PartyFactory {
        party = party.copy(damping = damping)
        return this
    }

    fun position(position: Position): PartyFactory {
        party = party.copy(position = position)
        return this
    }

    fun position(x: Float, y: Float): PartyFactory {
        party = party.copy(position = Position.Absolute(x, y))
        return this
    }

    fun position(minX: Float, minY: Float, maxX: Float, maxY: Float): PartyFactory {
        party = party.copy(
            position = Position.Absolute(minX, minY)
                .between(Position.Absolute(maxX, maxY))
        )
        return this
    }

    fun position(x: Double, y: Double): PartyFactory {
        party = party.copy(position = Position.Relative(x, y))
        return this
    }

    fun position(minX: Double, minY: Double, maxX: Double, maxY: Double): PartyFactory {
        party = party.copy(
            position = Position.Relative(minX, minY).between(Position.Relative(maxX, maxY))
        )
        return this
    }

    fun sizes(vararg sizes: Size): PartyFactory {
        party = party.copy(size = sizes.toList())
        return this
    }

    fun sizes(size: List<Size>): PartyFactory {
        party = party.copy(size = size)
        return this
    }

    fun colors(colors: List<Int>): PartyFactory {
        party = party.copy(colors = colors)
        return this
    }

    fun shapes(shapes: List<Shape>): PartyFactory {
        party = party.copy(shapes = shapes)
        return this
    }

    fun shapes(vararg shapes: Shape): PartyFactory {
        party = party.copy(shapes = shapes.toList())
        return this
    }

    fun timeToLive(timeToLive: Long): PartyFactory {
        party = party.copy(timeToLive = timeToLive)
        return this
    }

    fun fadeOutEnabled(fadeOutEnabled: Boolean): PartyFactory {
        party = party.copy(fadeOutEnabled = fadeOutEnabled)
        return this
    }

    fun delay(delay: Int): PartyFactory {
        party = party.copy(delay = delay)
        return this
    }

    fun rotation(rotation: Rotation): PartyFactory {
        party = party.copy(rotation = rotation)
        return this
    }

    fun build(): Party = party
}

data class Party(
    val angle: Int = 0,
    val spread: Int = 360,
    val speed: Float = 30f,
    val maxSpeed: Float = 0f,
    val damping: Float = 0.9f,
    val size: List<Size> = listOf(Size.SMALL, Size.MEDIUM, Size.LARGE),
    val colors: List<Int> = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
    val shapes: List<Shape> = listOf(Shape.Square, Shape.Circle),
    val timeToLive: Long = 2000,
    val fadeOutEnabled: Boolean = true,
    val position: Position = Position.Relative(0.5, 0.5),
    val delay: Int = 0,
    val rotation: Rotation = Rotation(),
    val emitter: EmitterConfig
)

/**
 * Helper class for easily setting an angle based on easy understandable constants
 * [TOP] 270 degrees
 * [RIGHT] 0 degrees
 * [BOTTOM] 90 degrees
 * [LEFT] 180 degrees
 */
class Angle {
    companion object {
        const val TOP: Int = 270
        const val RIGHT: Int = 0
        const val BOTTOM: Int = 90
        const val LEFT: Int = 180
    }
}

/**
 * Helper class for for easily configuring [Spread] when creating a [Party]
 * These are presets but any custom amount will work within 0-360 degrees
 */
class Spread {
    companion object {
        const val SMALL: Int = 30
        const val WIDE: Int = 100
        const val ROUND: Int = 360
    }
}

sealed class Position {
    /**
     * Set absolute position on the x and y axis of the KonfettiView
     * @property x the x coordinate in pixels
     * @property y the y coordinate in pixels
     */
    data class Absolute(val x: Float, val y: Float) : Position() {
        fun between(value: Absolute): Position = between(this, value)
    }

    /**
     * Set relative position on the x and y axis of the KonfettiView. Some examples:
     * [x: 0.0, y: 0.0] Top left corner
     * [x: 1.0, y: 0.0] Top right corner
     * [x: 0.0, y: 1.0] Bottom left corner
     * [x: 1.0, y: 1.0] Bottom right corner
     * [x: 0.5, y: 0.5] Center of the view
     *
     * @property x the relative x coordinate as a double
     * @property y the relative y coordinate as a double
     */
    data class Relative(val x: Double, val y: Double) : Position() {
        fun between(value: Relative): Position = between(this, value)
    }

    /**
     * Use this if you want to spawn confetti between multiple locations. Use this with [Absolute]
     * and [Relative] to connect two points
     * Example: Relative(0.0, 0.0).between(Relative(1.0, 0.0))
     * This will spawn confetti from the full width and top of the view
     */
    internal data class between(val min: Position, val max: Position) : Position()
}

/**
 * @property enabled by default true. Set to false to prevent the confetti from rotating
 * @property speed the rate at which the confetti will rotate per frame. Control the 2D and 3D rotation
 * separately using [multiplier2D] and [multiplier3D]
 * @property variance the margin in which the rotationSpeed can differ to add randomness
 * to the rotation speed of each confetti.
 * @property multiplier2D Multiplier controlling the speed of the rotation around the center of the
 * confetti. Set this value to 0 to disable the 2D rotation effect.
 * @property multiplier3D Multiplier controlling the 3D rotation of the confetti.
 */
data class Rotation(
    val enabled: Boolean = true,
    val speed: Float = 1f,
    val variance: Float = 0.5f,
    val multiplier2D: Float = 8f,
    val multiplier3D: Float = 1.5f
) {
    companion object {
        fun enabled() = Rotation(enabled = true)
        fun disabled() = Rotation(enabled = false)
    }
}

data class Size(val sizeInDp: Int, val mass: Float = 5f, val massVariance: Float = 0.2f) {

    init {
        require(mass != 0F) { "mass=$mass must be != 0" }
    }

    companion object {
        val SMALL: Size = Size(sizeInDp = 6, mass = 4f)
        val MEDIUM: Size = Size(8)
        val LARGE: Size = Size(10, mass = 6f)
    }
}
