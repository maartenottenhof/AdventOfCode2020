package day14

import java.io.File

fun main() {
    val input = File("src/day14/input.txt").readLines()

    println(task1(input))
}

private fun task1(input: List<String>): Long {
    var mask = ""
    val register = mutableMapOf<Int, Long>()

    input.forEach {
        if (it.contains("mask")) mask = it.substringAfter(" = ")
        else {
            val values = Regex("\\d+").findAll(it).toList().map(MatchResult::value)
            val numberAsBits = values[1].toInt().toString(2).padStart(36, '0')
            register[values[0].toInt()] =
                mask.zip(numberAsBits).map { i -> if (i.first == 'X') i.second else i.first }.joinToString("").toLong(2)
        }
    }

    return register.values.sum()
}