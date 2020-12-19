package day18

import java.io.File

fun main() {
    val sums = File("src/day18/input.txt").readLines()
        .map { it.replace("(", " ( ").replace(")", " ) ").trim().split("\\s+".toRegex()) }

    println(task1(sums))
    println(task2(sums))
}

private fun task1(sums: List<List<String>>) = sums.sumOf { it.solveParentheses(List<String>::solveEqualPrecedence) }
private fun task2(sums: List<List<String>>) = sums.sumOf { it.solveParentheses(List<String>::solvePlusFirst) }

private fun List<String>.solveParentheses(solver: (List<String>) -> (Long)): Long {
    if (!contains("(")) return solver(this)

    val lastOpenIndex = indexOfLast { it == "(" }
    val matchingCloseIndex = drop(lastOpenIndex).indexOfFirst { it == ")" } + lastOpenIndex
    val newList = subList(0, lastOpenIndex)
        .plus(solver.invoke(subList(lastOpenIndex + 1, matchingCloseIndex)).toString())
        .plus(subList(matchingCloseIndex + 1, size))

    return if (newList.contains("(")) newList.solveParentheses(solver)
    else solver.invoke(newList)
}

private fun List<String>.solveEqualPrecedence() : Long {
    val numberOne = this[0].toLong()
    val numberTwo = this[2].toLong()
    val newValue = when (this[1]) {
        "+" -> numberOne + numberTwo
        "*" -> numberOne * numberTwo
        else -> error("Unexpected operator: $this[1]")
    }

    return if (size == 3) newValue
    else (listOf(newValue.toString()) + this.subList(3, size)).solveEqualPrecedence()
}

private fun List<String>.solvePlusFirst() : Long {
    val newList: List<String> = if (contains("+")) {
        val plusIndex = indexOfFirst { it == "+" }
        subList(0, plusIndex - 1)
            .plus((this[plusIndex - 1].toLong() + this[plusIndex + 1].toLong()).toString())
            .plus(subList(plusIndex + 2, size))
    } else {
        (listOf((this[0].toLong() * this[2].toLong()).toString()) + this.subList(3, size))
    }

    return if (size == 3) newList[0].toLong()
    else newList.solvePlusFirst()
}