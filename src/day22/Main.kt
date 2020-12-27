package day22

import java.io.File
import java.util.*

fun main() {
    val input = File("src/day22/input.txt").readLines()
    val deck1 = input.drop(1).takeWhile { it.isNotBlank() }.map { it.toInt() }
    val deck2 = input.drop(1).dropWhile { !it.contains("Player 2:") }.drop(1).map { it.toInt() }

    println(task1(LinkedList(deck1), LinkedList(deck2)))
    println(task2(LinkedList(deck1), LinkedList(deck2)))
}

private fun task1(deck1: LinkedList<Int>, deck2: LinkedList<Int>): Int {
    while (deck1.isNotEmpty() && deck2.isNotEmpty()) {
        val card1 = deck1.pop()
        val card2 = deck2.pop()

        if (card1 > card2) deck1.add(card1, card2)
        else deck2.add(card2, card1)
    }

    return (deck1 + deck2).reversed()
        .mapIndexed { index, i -> (index + 1) * i }
        .sum()
}

private fun task2(deck1: LinkedList<Int>, deck2: LinkedList<Int>): Int {
    return recursiveCombat(deck1, deck2)
        .let { it.first + it.second }
        .reversed()
        .mapIndexed { index, i -> (index + 1) * i }
        .sum()
}

private fun recursiveCombat(deck1: LinkedList<Int>, deck2: LinkedList<Int>): Pair<LinkedList<Int>, LinkedList<Int>> {
    val seenDecks1 = mutableSetOf<List<Int>>()

    while (deck1.isNotEmpty() && deck2.isNotEmpty()) {
        if (deck1 in seenDecks1) {
            deck2.removeAll(deck2)
            break
        } else seenDecks1.add(deck1.toList())

        val card1 = deck1.pop()
        val card2 = deck2.pop()

        if ((card1 <= deck1.size && card2 <= deck2.size)) {
            val result = recursiveCombat(LinkedList(deck1.subList(0, card1)), LinkedList(deck2.subList(0, card2)))
            if (result.first.isNotEmpty()) {
                deck1.add(card1, card2)
            } else {
                deck2.add(card2, card1)
            }
        } else if (card1 > card2) {
            deck1.add(card1, card2)
        } else {
            deck2.add(card2, card1)
        }
    }

    return deck1 to deck2
}