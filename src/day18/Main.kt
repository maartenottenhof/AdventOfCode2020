package day18

import java.io.File

fun main() {
    val input = File("src/day18/input.txt").readLines()
        .map { it.replace("(", " ( ").replace(")", " ) ").trim().split("\\s+".toRegex()) }

    input.map { it.solveParentheses() }.sum().let { println(it) }
}

private fun List<String>.solveParentheses(): Long {
    if (!contains("(")) {
        return solveMaths2()
    }

    val lastOpenIndex = indexOfLast { it == "(" }
    val matchingCloseIndex = drop(lastOpenIndex).indexOfFirst { it == ")" } + lastOpenIndex
    val solvedBrackets = subList(lastOpenIndex + 1, matchingCloseIndex).solveMaths2()
    val newList = subList(0, lastOpenIndex).plus(solvedBrackets.toString()).plus(subList(matchingCloseIndex + 1, size))

    return if (newList.contains("(")) {
        newList.solveParentheses()
    } else {
        newList.solveMaths2()
    }
}

private fun List<String>.solveMaths() : Long {
    val numberOne = this[0].toLong()
    val operator = this[1]
    val numberTwo = this[2].toLong()
    val newValue = when (operator) {
        "+" -> numberOne + numberTwo
        "*" -> numberOne * numberTwo
        else -> error("Unexpected operator: $operator")
    }

    return if (size == 3) {
        newValue
    } else {
        (listOf(newValue.toString()) + this.subList(3, size)).solveMaths2()
    }
}

private fun List<String>.solveMaths2() : Long {
    val newList: List<String> = if (contains("+")) {
        val plusIndex = indexOfFirst { it == "+" }
        subList(0, plusIndex - 1)
            .plus((this[plusIndex - 1].toLong() + this[plusIndex + 1].toLong()).toString())
            .plus(subList(plusIndex + 2, size))
    } else {
        (listOf((this[0].toLong() * this[2].toLong()).toString()) + this.subList(3, size))
    }

    return if (size == 3) {
        newList[0].toLong()
    } else {
        newList.solveMaths2()
    }
}