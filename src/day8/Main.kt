package day8

import java.io.File

fun main() {
    val instructions = File("src/day8/input.txt").readLines()
        .map { it.split(" ") }

    println(task1(instructions))
}

private fun task1(instructions: List<List<String>>): Int {
    var currentIndex = 0
    val indexesVisited = mutableListOf(0)
    var acc = 0;

    do {
        indexesVisited.add(currentIndex)
        val instruction = instructions[currentIndex]
        when (instruction[0]) {
            "acc" -> {
                acc += instruction[1].toInt()
                currentIndex++
            }
            "jmp" -> currentIndex += instruction[1].toInt()
            else -> currentIndex++
        }
    } while (!indexesVisited.contains(currentIndex))

    return acc;
}