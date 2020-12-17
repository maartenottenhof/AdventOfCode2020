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
    val lastSeen = IntArray(30000000) { -1 }
    input.subList(0, input.lastIndex).withIndex().forEach{ lastSeen[it.value] = it.index }
    return generateSequence(input.last() to input.lastIndex) {
        ((if (lastSeen[it.first] != -1) it.second - lastSeen[it.first] else 0) to (it.second + 1))
            .also { _ -> lastSeen[it.first] = it.second }
    }
}