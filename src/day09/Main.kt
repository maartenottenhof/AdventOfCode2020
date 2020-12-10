package day09

import java.io.File

const val PREAMBLE = 25

fun main() {
    val numbers = File("src/day9/input.txt").readLines().map { it.toLong() }

    println(task1(numbers))
    println(task2(numbers, task1(numbers)))
}

private fun task1(numbers: List<Long>) =
    numbers.asSequence()
        .filterIndexed { index, l -> index >= PREAMBLE && !numbers.subList(index - PREAMBLE, index).containsSum(l) }
        .first()

private fun task2(numbers: List<Long>, sum: Long) =
    numbers.subList(0, numbers.indexOf(sum)).asSequence()
        .mapIndexed { indexA, _ ->
            numbers.subList(indexA, numbers.indexOf(sum))
                .mapIndexed { indexB, _ -> numbers.subList(indexA, indexA + indexB) }
                .firstOrNull { it.sum() == sum }
        }
        .filterNotNull()
        .map { it.min()!! + it.max()!! }.first()

private fun List<Long>.containsSum(sum: Long) =
    this.asSequence().filter { a -> this.filter { b -> a + b == sum }.any() }.any()