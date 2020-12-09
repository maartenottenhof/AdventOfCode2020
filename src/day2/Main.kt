package day2

import java.io.File

fun main() {
    val entries = File("src/day2/input.txt").readLines()
        .map { it.replace(":", "") }
        .map { it.replace("-", " ") }
        .map { it.split(" ") }
        .map { Validator(it[0].toInt(), it[1].toInt(), it[2][0]) to it[3] }

    println(task1(entries))
    println(task2(entries))
}

private fun task1(entries: List<Pair<Validator, String>>) =
    entries.filter { it.first.validateOldJob(it.second) }.count()

private fun task2(entries: List<Pair<Validator, String>>) =
    entries.filter { it.first.validateNewJob(it.second) }.count()

class Validator(
    private val first: Int,
    private val second: Int,
    private val filter: Char
) {
    fun validateOldJob(password: String) = password.toCharArray()
        .filter { it == filter }.count() in first..second

    fun validateNewJob(password: String) = (checkIndex(first, password)) xor (checkIndex(second, password))

    private fun checkIndex(index: Int, password: String) = password[index - 1] == filter
}
