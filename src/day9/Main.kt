package day9

import java.io.File

const val PREAMBLE = 25

fun main() {
    val numbers = File("src/day9/input.txt").readLines().map { it.toLong() }

    println(task1(numbers))
    println(task2(numbers, task1(numbers)))
}

private fun task1(numbers: List<Long>) =
    numbers.filterIndexed { index, l -> index >= PREAMBLE && !numbers.subList(index - PREAMBLE, index).containsSum(l) }
        .first()

private fun task2(numbers: List<Long>, sum: Long) =
    numbers.subList(0, numbers.indexOf(sum))
        .mapIndexed { index_i, _ ->
            numbers.subList(index_i, numbers.indexOf(sum))
                .mapIndexed { index_j, _ -> numbers.subList(index_i, index_i + index_j) }
                .filter { it.sum() == sum }
                .map { it.min()!! + it.max()!! }
        }.flatten().first()

private fun List<Long>.containsSum(sum: Long) = this.filter { i -> this.filter { j -> i + j == sum }.any() }.any()