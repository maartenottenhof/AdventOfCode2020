package day14

import java.io.File

fun main() {
    val input = File("src/day14/input.txt").readLines()

    println(task1(input))
    println(task2(input))
}

private fun task1(input: List<String>) = decode(input) { v, m -> version1Decode(v, m) }
private fun task2(input: List<String>) = decode(input) { v, m -> version2Decode(v, m) }

private fun decode(input: List<String>, decodingFunction: (Pair<Long, Long>, String) -> (Map<Long, Long>)): Long {
    var mask = ""
    return input.fold(mapOf<Long, Long>()) { acc, i ->
        if (i.contains("mask")) acc.also { mask = i.substringAfter(" = ") }
        else acc + Regex("\\d+").findAll(i).toList().map(MatchResult::value).let { it[0].toLong() to it[1].toLong() }
            .let { decodingFunction.invoke(it, mask) }
    }.values.sum()
}

private fun version1Decode(entry: Pair<Long, Long>, mask: String) =
    mapOf(entry.first to entry.second.applyMaskToValue(mask))

private fun version2Decode(entry: Pair<Long, Long>, mask: String): Map<Long, Long> {
    val maskedAddress = entry.first.applyMaskToAddress(mask)
    val floatingIndices = maskedAddress.withIndex().filter { i -> i.value == 'X' }.map { i -> i.index }
    return (0 until (1 shl floatingIndices.size))
        .map { it.toString(2).padStart(floatingIndices.size, '0').toList() }
        .map { maskedAddress.applyPermutation(floatingIndices, it) }
        .map { it to entry.second }.toMap()
}

private fun List<Char>.applyPermutation(floatingIndices: List<Int>, permutation: List<Char>) =
    floatingIndices.zip(permutation.toList()).fold(this.toCharArray()) { acc, pair ->
        acc.apply { acc[pair.first] = pair.second }
    }.joinToString("").toLong(2)

private fun Long.applyMaskToValue(mask: String) =
    mask.zip(this.toString(2).padStart(36, '0'))
        .map { if (it.first == 'X') it.second else it.first }.joinToString("").toLong(2)

private fun Long.applyMaskToAddress(mask: String) =
    mask.zip(this.toString(2).padStart(36, '0'))
        .map { if (it.first == '0') it.second else it.first }