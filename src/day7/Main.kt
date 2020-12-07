package day7

import java.io.File
import java.util.*

fun main() {
    val bags = File("src/day7/input.txt").readText().removeSuffix(".")
        .split(".")
        .filterNot { it.contains("no other") }
        .map { it.trim().replace("bag[s]?".toRegex(), "").replace("contain", ",").split(",") }
        .map { it.map(String::trim) }
        .map { Bag(it[0], toInnerBags(it.subList(1, it.size))) }
        .map { it.colour to it }.toMap()

    task1(bags).let(::println)
    task2("shiny gold", bags).let(::println)
}

private fun task1(bags: Map<String, Bag>) = bags.filter { it ->
    val queue = LinkedList(it.value.innerBags.keys)
    while (queue.isNotEmpty()) {
        if (queue.first == "shiny gold") {
            return@filter true
        } else {
            bags[queue.removeFirst()]?.let { it.innerBags.keys.forEach() { clr -> queue.add(clr) } }
        }
    }
    false
}.count()

private fun task2(colour: String, bags: Map<String, Bag>) = bags[colour]?.let {
    var numberOfBags = 0
    val queue = LinkedList<String>()
    it.innerBags.forEach { (colour, amount) ->
        repeat(amount) {
            queue.add(colour)
            numberOfBags++
        }
    }

    while (queue.isNotEmpty()) {
        bags[queue.removeFirst()]?.let { bag ->
            bag.innerBags.forEach { (colour, amount) ->
                repeat(amount) {
                    queue.add(colour)
                    numberOfBags++
                }
            }
        }
    }
    numberOfBags
}

private data class Bag(val colour: String, val innerBags: Map<String, Int>)

private fun toInnerBags(input: List<String>) =
    input.map { it.substring(2) to Integer.parseInt(it.substring(0, 1)) }.toMap()
