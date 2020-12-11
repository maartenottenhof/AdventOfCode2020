package day11

import java.io.File

fun main() {
    val seatGrid = File("src/day11/input.txt").readLines().map { it.toList() }

    println(task1(seatGrid))
}

private fun task1(seatGrid: List<List<Char>>): Int {
    val seenGrids = mutableSetOf<List<List<Char>>>()
    return generateSequence(seatGrid) {
        it.mapIndexed { x, valX ->
            valX.mapIndexed() { y, valY ->
                if (valY == '.') valY
                else if (valY == 'L' && it.adjacentOccupiedSeats(x, y) > 0) 'L'
                else if (it.adjacentOccupiedSeats(x, y) >= 4) 'L' else '#'
            }
        }
    }.first() { !seenGrids.add(it) }.flatten().count { it == '#' }
}

private fun List<List<Char>>.adjacentOccupiedSeats(x: Int, y: Int) =
    (x - 1..x + 1).map { i -> (y - 1..y + 1).map { j -> i to j } }.flatten().minus(x to y)
        .filter { this.isSeatOccupied(it.first, it.second) }.size

private fun List<List<Char>>.isSeatOccupied(x: Int, y: Int) = (this.getOrNull(x)?.getOrNull(y) ?: '.') == '#'