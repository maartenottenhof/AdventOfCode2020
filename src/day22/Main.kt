package day22

import java.io.File
import java.util.*

fun main() {
    val input = File("src/day22/input.txt").readLines()
    val deck1 = input.drop(1).takeWhile { it.isNotBlank() }.map { it.toInt() }
    val deck2 = input.drop(1).dropWhile { !it.contains("Player 2:") }.drop(1).map { it.toInt() }

    println(task1(LinkedList(deck1), LinkedList(deck2)))
}

private fun task1(deck1: LinkedList<Int>, deck2: LinkedList<Int>): Int {
    while (deck1.isNotEmpty() && deck2.isNotEmpty()) {
        val card1 = deck1.pop()
        val card2 = deck2.pop()

        if (card1 > card2) {
            deck1.add(card1)
            deck1.add(card2)
        } else {
            deck2.add(card2)
            deck2.add(card1)
        }
    }

    return (deck1 + deck2).reversed().mapIndexed { index, i -> (index + 1) * i }.sum()
}