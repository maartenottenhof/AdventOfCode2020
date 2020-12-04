package day4

import java.io.File

fun main(args: Array<String>) {
    val entries = File("src/day4/input.txt").readLines()

    println(task1(entries).count());
    println(task2(task1(entries)).count());
}

private fun task1(entries: List<String>): List<String> {
    var passports = arrayOf<String>()
    var currentPassport = "";

    for (entry in entries) {
        if (entry.isNotBlank()) {
            currentPassport += " $entry"
        } else {
            passports = passports.plus(currentPassport)
            currentPassport = ""
        }
    }

    val validPassports = passports.map { it.trim() }.filter { it.split(" ").size == 8 }
    val validCredentials = passports.map { it.trim() }.filter { it.split(" ").size == 7 }
        .filter { !it.contains("cid") }

    return validCredentials.map { it.plus(" cid:ignore") }.plus(validPassports)
}

private fun task2(entries: List<String>) = entries
    .asSequence()
    .map { it.split(" ") }
    .map { it.sorted() }
    .map { it.map { i -> i.split(":") } }
    .mapNotNull {
        try {
            Passport(it[0][1], it[1][1], it[2][1], it[3][1], it[4][1], it[5][1], it[6][1], it[7][1])
        } catch (e: IllegalArgumentException) {
            null
        }
    }

data class Passport(
    val byr: String,
    val cid: String,
    val ecl: String,
    val eyr: String,
    val hcl: String,
    val hgt: String,
    val iyr: String,
    val pid: String
) {
    init {
        require(Integer.valueOf(byr) in 1920..2002)
        require(ecl in "amb blu brn gry grn hzl oth".split(" "))
        require(Integer.valueOf(eyr) in 2020..2030)
        require(hcl.contains("#[a-z0-9]{6}\$".toRegex()))
        require(
            when {
                hgt.contains("cm") -> {
                    Integer.valueOf(hgt.replace("cm", "")) in 150..193
                }
                hgt.contains("in") -> {
                    Integer.valueOf(hgt.replace("in", "")) in 59..76
                }
                else -> false
            }
        )
        require(Integer.valueOf(iyr) in 2010..2020)
        require(pid.length == 9)
    }
}
