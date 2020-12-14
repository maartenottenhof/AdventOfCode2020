package day14

import java.io.File
import kotlin.math.pow

fun main() {
    val input = File("src/day14/input.txt").readLines()

    println(task1(input))
    println(task2(input))
}

private fun task1(input: List<String>): Long {
    var mask = ""
    val register = mutableMapOf<Long, Long>()

    input.forEach {
        if (it.contains("mask")) mask = it.substringAfter(" = ")
        else {
            val values = Regex("\\d+").findAll(it).toList().map(MatchResult::value)
            register[values[0].toLong()] = values[1].toLong().applyMaskToValue(mask)
        }
    }

    return register.values.sum()
}

private fun task2(input: List<String>): Long {
    var mask = ""
    val register = mutableMapOf<Long, Long>()

    input.forEach {
        if (it.contains("mask")) mask = it.substringAfter(" = ")
        else {
            val values = Regex("\\d+").findAll(it).toList().map(MatchResult::value)
            val maskedAddress = values[0].toLong().applyMaskToAddress(mask)
            val floatingBits = maskedAddress.mapIndexed() { index, c -> index to c }.filter { i -> i.second == 'X' }
                .map { i -> i.first }
            val permutations = IntRange(0, 2.0.pow(floatingBits.size.toDouble()).toInt() - 1).map { i ->
                i.toString(2).padStart(floatingBits.size, '0')
            }
            permutations.map { i ->
                val temp = maskedAddress.toCharArray()
                floatingBits.zip(i.toList()).forEach { j ->
                    temp[j.first] = j.second
                }
                temp.joinToString("").toLong(2)
            }.forEach { i -> register[i] = values[1].toLong() }
        }
    }

    return register.values.sum()
}

private fun Long.applyMaskToValue(mask: String) =
    mask.zip(this.toString(2).padStart(36, '0'))
        .map { if (it.first == 'X') it.second else it.first }.joinToString("").toLong(2)

private fun Long.applyMaskToAddress(mask: String) =
    mask.zip(this.toString(2).padStart(36, '0'))
        .map { if (it.first == '0') it.second else it.first }