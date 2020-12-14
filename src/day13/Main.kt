package day13

import java.io.File

fun main() {
    val input = File("src/day13/input.txt").readLines()
    val timestamp = input[0].toInt()
    val buses = input[1].split(",")

    println(task1(buses, timestamp))
    println(task2(buses))
}

private fun task1(buses: List<String>, timestamp: Int) =
    buses.filterNot { it == "x" }.map { it.toInt() }
        .map { it to timestamp - (timestamp % it) + it }
        .minBy { it.second }.let { (it!!.second - timestamp) * it.first }

private fun task2(buses: List<String>) =
    buses.mapIndexed { index, s -> s to index }
        .filterNot { it.first == "x" }
        .map { it.first.toLong() to it.second.toLong() }.let { findFirstBus(it) }

private fun findFirstBus(input: List<Pair<Long, Long>>) =
    input.fold(1L) { acc, pair -> acc * pair.first }.let { p ->
        input.map { (bus, index) ->
            index * (p / bus).toBigInteger().modInverse(bus.toBigInteger()).toLong() * (p / bus)
        }.sum().let { p - it % p }
    }
