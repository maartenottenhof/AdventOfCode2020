package day13

import java.io.File

fun main() {
    val input = File("src/day13/input.txt").readLines()
    val timestamp = input[0].toInt()
    val busses = input[1].split(",")

    println(task1(busses, timestamp))
}

private fun task1(busses: List<String>, timestamp: Int) = busses.filterNot { it == "x" }
    .map { it.toInt() }
    .map { i -> i to generateSequence(i) { it + i }.first { it >= timestamp } }
    .minBy { it.second }.let { (it!!.second - timestamp) * it.first }


