package day19

import java.io.File

fun main() {
    val input = File("src/day19/input.txt").readLines()
    val rules = input.subList(0, input.indexOf(""))
        .map { it.split(": ") }
        .map { it[0] to it[1] }.toMap().toMutableMap()
    val messages = input.subList(input.indexOf("") + 1, input.size)

    print(task1(rules, messages))
}

private fun task1(rules: Map<String, String>, messages: List<String>) : Int {
    val mutableRules = rules.toMutableMap()
    mutableRules.forEach { entry ->
        mutableRules.forEach {
            mutableRules[it.key] = it.value.replace(entry.key.exactMatch(), "(" + entry.value + ")")
        }
    }

    return messages.filter { it.matches(mutableRules["0"]!!.replace("\"", "").replace(" ", "").exactMatch()) }.size
}

private fun String.exactMatch() = "\\b$this\\b".toRegex()