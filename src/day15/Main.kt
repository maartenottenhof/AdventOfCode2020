package day15

import java.io.File

fun main() {
    val input = File("src/day15/input.txt").readText().split(",").map { it.toInt() }

    println(task1(input))
    println(task2(input))
}

private fun task1(input: List<Int>) = playGame(input).elementAt(2020 - input.size).first
private fun task2(input: List<Int>) = playGame(input).elementAt(30000000 - input.size).first

private fun playGame(input: List<Int>): Sequence<Pair<Int, Int>> {
    val lastSeen = input.subList(0, input.lastIndex).mapIndexed { index, i -> i to index }.toMap().toMutableMap()
    return generateSequence(input.last() to input.lastIndex) {
        ((lastSeen[it.first]?.let { i -> (it.second - i) } ?: 0) to (it.second + 1))
            .also { _ -> lastSeen[it.first] = it.second }
    }
}