package day20

import java.io.File
import java.util.*
import kotlin.math.sqrt

fun main() {
    val tiles = File("src/day20/input.txt").readLines()
        .toPossibleTiles()
        .toMatchingTiles()

    println(task1(tiles))
    println(task2(tiles))
}

private fun task2(tiles: List<Tile>): Int {
    val topLeftCorner = tiles.find { tile ->
        tiles.any { tile.matchRight(it) } &&
                tiles.any { tile.matchDown(it) } &&
                tiles.none { tile.matchLeft(it) } &&
                tiles.none { tile.matchUp(it) }
    }

    val image = generateSequence(topLeftCorner!!) { tile -> tiles.find { tile.matchDown(it) } }
        .take(sqrt(tiles.size.toDouble()).toInt()).toList()
        .map { startTile ->
            generateSequence(startTile) { tile -> tiles.find { tile.matchRight(it) } }
                .take(sqrt(tiles.size.toDouble()).toInt()).toList()
        }
        .map { it.map(Tile::trimImage) }
        .flatMap { row -> row.first().indices.map { index -> row.joinToString("") { it[index] } } }

    val numberOfDragons = image.possiblePositions().maxOfOrNull {
        Regex("(?=(#.{" + (image.size - 19).toString() + "}#....##....##....###.{" + (image.size - 19).toString() + "}#..#..#..#..#..#))")
            .findAll(it.joinToString("")).count()
    }

    return image.joinToString("").count { it == '#' } - (numberOfDragons!! * 15)
}

private fun task1(definitiveTiles: List<Tile>) =
    definitiveTiles.mapNotNull { tile ->
        definitiveTiles.filter { tile.match(it) }.size
            .let { if (it == 2) tile.id else null }
    }.map { it.toLong() }.fold(1L, Long::times)

private fun List<Tile>.toMatchingTiles(): List<Tile> {
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

    return definitiveTiles.toList()
}

private fun List<String>.toPossibleTiles(): List<Tile> {
    val tiles = mutableListOf<Tile>()
    for (i in 0..size step 12) {
        tiles.add(
            Tile(
                this[i].replace("Tile ", "").replace(":", ""),
                this.subList(i + 1, i + 11)
            )
        )
    }

    return tiles.flatMap { it.possiblePositions() }
}

private fun List<String>.flip() = map { it.reversed() }
private fun List<String>.inverse() = indices.map { index -> indices.map { this[it][index] }.joinToString("") }
private fun List<String>.rotate() = inverse().flip()
private fun List<String>.possiblePositions(): List<List<String>> {
    val rotated = (0 until 3).fold(listOf(this)) { acc, _ -> acc + listOf(acc.last().rotate()) }
    val flipRotated = (0 until 3).fold(listOf(this.flip())) { acc, _ -> acc + listOf(acc.last().rotate()) }
    return rotated + flipRotated
}

private data class Tile(val id: String, val image: List<String>) {
    fun match(tile: Tile) = matchDown(tile) || matchRight(tile) || matchLeft(tile) || matchUp(tile)
    fun matchRight(tile: Tile) =
        image.map { it.last() }.joinToString("") == tile.image.map { it.first() }.joinToString("") && tile.id != id

    fun matchLeft(tile: Tile) =
        image.map { it.first() }.joinToString("") == tile.image.map { it.last() }.joinToString("") && tile.id != id

    fun matchUp(tile: Tile) = image.first() == tile.image.last() && tile.id != id
    fun matchDown(tile: Tile) = image.last() == tile.image.first() && tile.id != id
    fun trimImage() = image.subList(1, image.lastIndex).map { it.substring(1, it.lastIndex) }
    fun possiblePositions() = image.possiblePositions().map { Tile(id, it) }
}