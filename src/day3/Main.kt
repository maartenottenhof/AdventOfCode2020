package day3

import java.io.File

fun main(args: Array<String>) {
    val entries = File("src/day3/input.txt").readLines()

    println(task1(entries));
    println(task2(entries));
}

private fun task1(entries: List<String>) = toboggan(entries, 3)

private fun task2(entries: List<String>) = listOf(
    toboggan(entries, 1),
    toboggan(entries, 3),
    toboggan(entries, 5),
    toboggan(entries, 7),
    toboggan(entries, 1, 2)
).fold(1L, Long::times)

private fun toboggan(entries: List<String>, horizontalSteps: Int, verticalSteps: Int = 1) =
    entries.filterIndexed { index, _ -> index % verticalSteps == 0 }
        .filterIndexed { index, row -> row[index * horizontalSteps % row.length] == '#' }.count()
