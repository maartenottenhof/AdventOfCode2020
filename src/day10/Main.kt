package day10

import java.io.File

fun main() {
    val adapters = File("src/day10/input.txt").readLines().map { it.toInt() }.sorted()

    println(task1(adapters))
    println(task2(adapters))
}

private fun task1(adapters: List<Int>) = adapters
    .mapIndexed { index, i -> i - adapters.getOrElse(index - 1) { 0 } }.plus(3)
    .let { it.count { i -> i == 1 } * it.count { i -> i == 3 } }

private fun task2(adapters: List<Int>) = adapters.fold(mapOf(0 to 1L), { acc, i ->
    acc + (i to (i - 3 until i).map { acc[it] ?: 0 }.sum())
})[adapters.last()]