package day17

import java.io.File

fun main() {
    val input = File("src/day17/input.txt").readLines()

    println(task1(input))
    println(task2(input))
}

private fun task1(input: List<String>): Int {
    val coordinates =
        input.flatMapIndexed { i, s -> s.mapIndexed { j, c -> if (c == '#') IntTriple(i, j, 0) else null } }
            .filterNotNull().toSet()

    return boot(coordinates).elementAt(6).size
}

private fun task2(input: List<String>): Int {
    val coordinates =
        input.flatMapIndexed { i, s -> s.mapIndexed { j, c -> if (c == '#') IntQuadruple(i, j, 0, 0) else null } }
            .filterNotNull().toSet()

    return boot(coordinates).elementAt(6).size
}

private fun <R: Coordinate<R>> boot(coordinates: Set<R>): Sequence<Set<R>> {
    return generateSequence(coordinates) { i ->
        val toActive = i.fold(mutableMapOf<R, Int>()) { acc, j ->
            j.adjacentCoordinates().forEach { acc[it] = (acc[it] ?: 0) + 1 }.let { acc }
        }.filterValues { it == 3 }.keys
        val stayActive = i.filter { (it.adjacentCoordinates() intersect i).size in 2..3 }

        toActive + stayActive
    }
}

interface Coordinate<R> {
    fun adjacentCoordinates(): Set<R>
}

data class IntTriple(val first: Int, val second: Int, val third: Int) : Coordinate<IntTriple> {
    override fun adjacentCoordinates(): Set<IntTriple> {
        return (-1..1).flatMap { i ->
            (-1..1).flatMap { j ->
                (-1..1).map { k ->
                    IntTriple(first + i, second + j, third + k)
                }
            }
        }.minus(this).toSet()
    }
}

data class IntQuadruple(val first: Int, val second: Int, val third: Int, val fourth: Int) : Coordinate<IntQuadruple> {
    override fun adjacentCoordinates(): Set<IntQuadruple> {
        return (-1..1).flatMap { i ->
            (-1..1).flatMap { j ->
                (-1..1).flatMap { k ->
                    (-1..1).map { l -> IntQuadruple(first + i, second + j, third + k, fourth + l) }
                }
            }
        }.minus(this).toSet()
    }
}