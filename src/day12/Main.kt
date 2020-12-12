package day12

import java.io.File
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

fun main() {
    val directions = File("src/day12/input.txt").readLines().map { it[0] to it.substring(1).toInt() }

    println(task1(directions))
    println(task2(directions))
}

private fun task1(directions: List<Pair<Char, Int>>) =
    BoatOne(0, 0, 'E').also { it.sail(directions) }.getManhattanDistance()

private fun task2(directions: List<Pair<Char, Int>>) =
    BoatTwo(0, 0, BoatTwo.Waypoint(10, 1)).also { it.sail(directions) }.getManhattanDistance()

class BoatOne(override var x: Int, override var y: Int, private var facing: Char) : Boat(x, y) {
    private val directions = listOf('N', 'E', 'S', 'W')

    override fun getInstructions() = mapOf<Char, (Int) -> (Unit)>(
        'N' to { y += it },
        'E' to { x += it },
        'S' to { y -= it },
        'W' to { x -= it },
        'L' to { facing = directions[(directions.indexOf(facing) - it / 90 + 4) % 4] },
        'R' to { facing = directions[(directions.indexOf(facing) + it / 90 + 4) % 4] },
    )

    override fun moveForward(times: Int) {
        getInstructions()[facing]?.invoke(times)
    }
}

class BoatTwo(override var x: Int, override var y: Int, private val waypoint: Waypoint) : Boat(x, y) {
    override fun getInstructions() = mapOf<Char, (Int) -> (Unit)>(
        'N' to { waypoint.relativeY += it },
        'E' to { waypoint.relativeX += it },
        'S' to { waypoint.relativeY -= it },
        'W' to { waypoint.relativeX -= it },
        'L' to { waypoint.turn(-it) },
        'R' to { waypoint.turn(it) },
    )

    override fun moveForward(times: Int) {
        x += waypoint.relativeX * times
        y += waypoint.relativeY * times
    }

    data class Waypoint(var relativeX: Int, var relativeY: Int) {
        fun turn(degrees: Int) {
            val tempX = relativeX
            val radians = Math.toRadians(degrees.toDouble())
            relativeX = relativeY * sin(radians).toInt() + relativeX * cos(radians).toInt()
            relativeY = -tempX * sin(radians).toInt() + relativeY * cos(radians).toInt()
        }
    }
}

abstract class Boat(open var x: Int, open var y: Int, private var initialX: Int = x, private var initialY: Int = y) {
    abstract fun moveForward(times: Int)
    abstract fun getInstructions(): Map<Char, (Int) -> (Unit)>
    fun getManhattanDistance() = abs(x - initialX) + abs(y - initialY)
    fun sail(directions: List<Pair<Char, Int>>) = directions.forEach {
        this.getInstructions().getOrElse(it.first) { { i -> this.moveForward(i) } }.invoke(it.second)
    }
}