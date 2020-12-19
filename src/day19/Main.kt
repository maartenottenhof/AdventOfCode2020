package day19

import java.io.File

fun main() {
    val input = File("src/day19/input.txt").readLines()
    val rules = input.subList(0, input.indexOf(""))
        .map { it.split(": ") }
        .map { it[0] to it[1].replace("\"", "") }.toMap().toMutableMap()
    val messages = input.subList(input.indexOf("") + 1, input.size)

    println(task1(rules, messages))
    println(task2(rules, messages))
}

private fun task1(rules: Map<String, String>, messages: List<String>) =
    messages.filter { it.matches(resolveRules(rules)["0"]!!.replace(" ", "").exactMatch()) }.size

private fun task2(rules: Map<String, String>, messages: List<String>): Int {
    var resolvedRules = rules.plus("8" to "42 | 42 8").plus("11" to "42 31 | 42 11 31")
    var old: Int
    var new = 0

    do {
        old = new
        resolvedRules = resolveRules(resolvedRules)
        new = messages.filter { it.matches(resolvedRules["0"]!!.replace(" ", "").exactMatch()) }.size
    } while (old != new)

    return new
}

private fun resolveRules(rules: Map<String, String>): Map<String, String> {
    val mutableRules = rules.toMutableMap()
    mutableRules.forEach { entry ->
        mutableRules.forEach {
            mutableRules[it.key] = it.value.replace(entry.key.exactMatch(), "(" + entry.value + ")")
        }
    }
    return mutableRules
}

private fun String.exactMatch() = "\\b$this\\b".toRegex()