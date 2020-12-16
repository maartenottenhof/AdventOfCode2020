package day16

import java.io.File

fun main() {
    val input = File("src/day16/input.txt").readLines()
    val fields = input.takeWhile { it.isNotBlank() }
        .map { it.substringBefore(":") to it.substringAfter(": ").split("(-|( or ))".toRegex()) }
        .map { it.first to it.second.map(String::toInt) }
        .map { it.first to ((it.second[0] to it.second[1]) to (it.second[2] to it.second[3])) }.toMap()
    val nearbyTickets = input.dropWhile { !it.contains("nearby tickets") }.drop(1)
        .map { it.split(",").map(String::toInt) }

    println(task1(nearbyTickets, fields.values.toList()))
}

private fun task1(nearbyTickets: List<List<Int>>, fields: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>) =
    nearbyTickets.flatten().filterNot { it.isValid(fields) }.sum()

private fun Int.isValid(ranges: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>) =
    ranges.any { this in it.first.first..it.first.second || this in it.second.first..it.second.second }