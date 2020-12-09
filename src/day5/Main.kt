package day5

import java.io.File

fun main() {
    val passIds = File("src/day5/input.txt").readLines()
        .map { it.replace("[BR]".toRegex(), "1") }
        .map { it.replace("[FL]".toRegex(), "0") }
        .map { it.toInt(2) }

    println(task1(passIds))
    println(task2(passIds))
}

private fun task1(passIds: List<Int>) = passIds.max()

private fun task2(passIds: List<Int>) = passIds.min()!!..passIds.max()!! subtract passIds
