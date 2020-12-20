package day20

import java.io.File
import java.util.*

fun main() {
    val tiles = File("src/day20/input.txt").readLines()
        .toPossibleTiles()
        .toMatchingTiles()

    println(task1(tiles))
}

private fun task1(definitiveTiles: MutableList<Tile>) =
    definitiveTiles.mapNotNull { tile ->
        definitiveTiles.filter { tile.match(it) }.size
            .let { if (it == 2) tile.id else null }
    }.map { it.toLong() }.fold(1L, Long::times)

private fun List<Tile>.toMatchingTiles(): MutableList<Tile> {
    val definitiveTiles = mutableListOf(first())
    val tilesToResolve = LinkedList(mutableListOf(first()))

    while (tilesToResolve.isNotEmpty()) {
        val tile = tilesToResolve.pop()
        filter { tile.match(it) }
            .filter { definitiveTiles.none { i -> i.id == it.id } }
            .forEach {
                tilesToResolve.add(it)
                definitiveTiles.add(it)
            }
    }

    return definitiveTiles
}

private fun List<String>.toPossibleTiles(): List<Tile> {
    val tiles = mutableListOf<Tile>()
    for (i in 0..size step 12) {
        tiles.add(
            Tile(
                this[i].replace("Tile ", "").replace(":", ""),
                this[i + 1],
                subList(i + 1, i + 11).map { it.last() }.joinToString(""),
                this[i + 10],
                subList(i + 1, i + 11).map { it.first() }.joinToString("")
            )
        )
    }

    return tiles.flatMap { tile ->
        val rotated = (0 until 3).fold(listOf(tile)) { acc, _ -> acc + acc.last().rotateRight() }
        val flipRotated = (0 until 3).fold(listOf(tile.flip())) { acc, _ -> acc + acc.last().rotateRight() }
        rotated + flipRotated
    }
}

data class Tile(val id: String, val up: String, val right: String, val down: String, val left: String) {
    fun flip() = Tile(id, up.reversed(), left, down.reversed(), right)
    fun rotateRight() = Tile(id, left.reversed(), up, right.reversed(), down)
    fun match(tile: Tile) = tile.up == down && tile.id != id ||
            tile.left == right && tile.id != id ||
            tile.right == left && tile.id != id ||
            tile.down == up && tile.id != id
}