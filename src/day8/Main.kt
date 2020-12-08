package day8

import java.io.File

fun main() {
    val instructions = File("src/day8/input.txt").readLines()
        .map { it.split(" ") }

    println(task1(instructions))
    println(task2(instructions))
}

private fun task1(instructions: List<List<String>>) = boot(instructions).first

private fun task2(instructions: List<List<String>>) =
    instructions.mapIndexed { index, list ->
        val newInstructions = instructions.toMutableList()
        newInstructions[index] = listOf(
            when (list[0]) {
                "nop" -> "jmp"
                "jmp" -> "nop"
                else -> list[0]
            }, list[1]
        )
        boot(newInstructions)
    }.first { it.second }.first

private fun boot(instructions: List<List<String>>): Pair<Int, Boolean> {
    var acc = 0;
    var currentIndex = 0
    val indexesVisited = mutableListOf<Int>()

    do {
        indexesVisited.add(currentIndex)
        val instruction = instructions[currentIndex]
        when (instruction[0]) {
            "acc" -> currentIndex++.also { acc += instruction[1].toInt() }
            "jmp" -> currentIndex += instruction[1].toInt()
            else -> currentIndex++
        }
    } while (!indexesVisited.contains(currentIndex) && currentIndex < instructions.size)

    return acc to (currentIndex >= instructions.size)
}