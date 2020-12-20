package day20

import java.io.File

fun main() {
    val input = File("src/day20/input.txt").readLines()
    val tiles = mutableListOf<Tile>()

    for (i in 0..input.size step 12) {
        tiles.add(
            Tile(
                input[i].replace("Tile ", "").replace(":", ""),
                input[i + 1],
                input.subList(i + 1, i + 11).map { it.last() }.joinToString(""),
                input[i + 10],
                input.subList(i + 1, i + 11).map { it.first() }.joinToString("")
            )
        )
    }

    val possibleTiles = tiles.flatMap { tile ->
        val rotated = (0 until 3).fold(listOf(tile)) { acc, _ -> acc + acc.last().rotateRight() }
        val flipRotated = (0 until 3).fold(listOf(tile.flip())) { acc, _ -> acc + acc.last().rotateRight() }
        rotated + flipRotated
    }.toMutableList()

    tiles.mapNotNull { tile ->
        possibleTiles.filter {
            tile.up == it.down && tile.id != it.id ||
                    tile.left == it.right && tile.id != it.id ||
                    tile.right == it.left && tile.id != it.id ||
                    tile.down == it.up && tile.id != it.id
        }.size.let { if (it == 2) tile.id else null }
    }.map { it.toLong() }.fold(1L, Long::times).let { println(it) }
}

data class Tile(val id: String, val up: String, val right: String, val down: String, val left: String) {
    fun flip() = Tile(id, up.reversed(), left, down.reversed(), right)
    fun rotateRight() = Tile(id, left.reversed(), up, right.reversed(), down)
}