package day11

import java.io.File

fun main() {
    val seatGrid = File("src/day11/input.txt").readLines().map { it.toList() }

    println(task1(seatGrid))
    println(task2(seatGrid))
}

private fun task1(seatGrid: List<List<Char>>): Int {
    val seenGrids = mutableSetOf<List<List<Char>>>()
    return generateSequence(seatGrid) {
        it.mapIndexed { x, valX ->
            valX.mapIndexed() { y, valY ->
                when {
                    valY == '.' -> valY
                    valY == 'L' && it.adjacentOccupiedSeats(x, y) == 0 -> '#'
                    valY == 'X' || it.adjacentOccupiedSeats(x, y) >= 4 -> 'L'
                    else -> valY
                }
            }
        }
    }.first() { !seenGrids.add(it) }.seatsOccupied()
}

private fun task2(seatGrid: List<List<Char>>): Int {
    val seenGrids = mutableSetOf<List<List<Char>>>()
    return generateSequence(seatGrid) {
        it.mapIndexed { x, valX ->
            valX.mapIndexed() { y, valY ->
                when {
                    valY == '.' -> valY
                    valY == 'L' && it.indirectOccupiedSeats(x, y) == 0 -> '#'
                    valY == 'X' || it.indirectOccupiedSeats(x, y) >= 5 -> 'L'
                    else -> valY
                }
            }
        }
    }.first() { !seenGrids.add(it) }.seatsOccupied()
}

private fun List<List<Char>>.adjacentOccupiedSeats(x: Int, y: Int) =
    (x - 1..x + 1).map { i -> (y - 1..y + 1).map { j -> i to j } }.flatten().minus(x to y)
        .filter { this.isSeatOccupied(it.first, it.second) }.size

private fun List<List<Char>>.indirectOccupiedSeats(x: Int, y: Int) = listOf(
    this[x].toMutableList().also { it[y] = 'X' },
    this.map { it[y] }.toMutableList().also { it[x] = 'X' },
    this.mapIndexed { index, list -> list.getOrElse(y + index - x) { 'L' } }.toMutableList().also { it[x] = 'X' },
    this.mapIndexed { index, list -> list.getOrElse(y - index + x) { 'L' } }.toMutableList().also { it[x] = 'X' },
).map { it.filterNot { i -> i == '.' } }.map { it.numberOfOccupiedSeatsInRow() }.sum()

private fun List<Char>.numberOfOccupiedSeatsInRow() = listOf(
    this.subList(0, this.indexOf('X')).lastOrNull() ?: 'L' != 'L',
    this.subList(this.indexOf('X') + 1, this.size).firstOrNull() ?: 'L' != 'L'
).count { it }

private fun List<List<Char>>.isSeatOccupied(x: Int, y: Int) = (this.getOrNull(x)?.getOrElse(y) { '.' }) == '#'

private fun List<List<Char>>.seatsOccupied() = this.flatten().count { it == '#' }