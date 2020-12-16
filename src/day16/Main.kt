package day16

import java.io.File

fun main() {
    val input = File("src/day16/input.txt").readLines()
    val fields = input.takeWhile { it.isNotBlank() }
        .map { it.substringBefore(":") to it.substringAfter(": ").split("(-|( or ))".toRegex()) }
        .map { it.first to it.second.map(String::toInt) }
        .map { it.first to ((it.second[0] to it.second[1]) to (it.second[2] to it.second[3])) }.toMap()
    val (invalidTickets, validTickets) = input.dropWhile { !it.contains("nearby tickets:") }.drop(1)
        .map { it.split(",").map(String::toInt) }.partition { i -> i.all { it.isValidForAny(fields.values.toList()) } }
    val ownTicket = input.dropWhile { !it.contains("your ticket:") }.drop(1).take(1)
        .map { it.split(",").map(String::toInt) }.flatten()

    println(task1(validTickets, fields))
    println(task2(invalidTickets, fields, ownTicket))
}

private fun task1(nearbyTickets: List<List<Int>>, fields: Map<String, Pair<Pair<Int, Int>, Pair<Int, Int>>>) =
    nearbyTickets.flatten().filterNot { it.isValidForAny(fields.values.toList()) }.sum()

private fun task2(
    validTickets: List<List<Int>>,
    fields: Map<String, Pair<Pair<Int, Int>, Pair<Int, Int>>>,
    ownTicket: List<Int>
): Long {
    val allocatedGroups = mutableListOf<Int>()
    return fields.mapValues { i ->
        fields.values.indices.filter { j ->
            validTickets.all { it[j].isValid(i.value) }
        }
    }
        .toList()
        .sortedBy { it.second.size }
        .map { it.first to (it.second - allocatedGroups)[0].also(allocatedGroups::add) }
        .filter { it.first.contains("departure") }
        .fold(1L) { acc, pair -> acc * ownTicket[pair.second] }
}

private fun Int.isValidForAny(ranges: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>) = ranges.any { isValid(it) }

private fun Int.isValid(range: Pair<Pair<Int, Int>, Pair<Int, Int>>) =
    this in range.first.first..range.first.second || this in range.second.first..range.second.second