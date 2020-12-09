package day1

import java.io.File

const val YEAR = 2020

fun main() {
    val numbers = File("src/day1/input.txt").readLines()
        .map(String::toInt)

    println(task1(numbers))
    println(task2(numbers))
}

private fun task1(numbers: List<Int>) =
    numbers.flatMap { i ->
        numbers.filter { j -> i + j == YEAR }
            .map { j -> i * j }
    }.first()

private fun task2(numbers: List<Int>) =
    numbers.flatMap { i ->
        numbers.flatMap { j ->
            numbers.filter { k -> i + j + k == YEAR }
                .map { k -> i * j * k }
        }
    }.first()
