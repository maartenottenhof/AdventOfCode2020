package day4

import java.io.File

fun main() {
    val passports = File("src/day4/input.txt").readText()
        .split("\n\n")
        .map { it.replace("\n", " ") }

    println(task1(passports).count())
    println(task2(task1(passports)).count())
}

private fun task1(passports: List<String>) = passports
    .map(String::trim)
    .map { it.split(" ") }
    .filter { it.size == 8 || (it.size == 7 && it.none { i -> i.contains("cid") }) }

private fun task2(entries: List<List<String>>) = entries
    .map { it.map { i -> i.split(":") }.map { i -> i[0] to i[1] } }
    .filter { it.all { i -> validators[i.first]?.invoke(i.second)!! } }

private val validators = mapOf<String, (String) -> Boolean>(
    "byr" to { i -> i.toIntOrNull() in 1920..2002 },
    "cid" to { true },
    "ecl" to { i -> i in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth") },
    "eyr" to { i -> i.toIntOrNull() in 2020..2030 },
    "hcl" to { i -> i.matches("#[a-z0-9]{6}\$".toRegex()) },
    "hgt" to { i -> i.matches("(1[5-8][0-9]|19[0-3])cm".toRegex()) || i.matches("(59|6[0-9]|7[0-6])in".toRegex()) },
    "iyr" to { i -> i.toIntOrNull() in 2010..2020 },
    "pid" to { i -> i.length == 9 }
)
