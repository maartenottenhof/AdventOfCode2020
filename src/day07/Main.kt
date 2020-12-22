package day07

import java.io.File

fun main() {
    val bags = File("src/day07/input.txt").readText().split(".").filterNot { it.contains("no other") }
        .map { it.replace("bag[s]?".toRegex(), "").replace("contain", ",").split(",").map(String::trim) }
        .associate { it[0] to Bag(it[0], toInnerBags(it.subList(1, it.size))) }

    println(task1("shiny gold", bags))
    println(task2("shiny gold", bags))
}

private fun task1(colour: String, bags: Map<String, Bag>) =
    bags.filter { it.value.containsColour(colour, bags) }.count()

private fun task2(colour: String, bags: Map<String, Bag>) = bags[colour]?.numberOfInnerBags(bags)

private data class Bag(val colour: String, val innerBags: Map<String, Int>) {
    fun containsColour(colour: String, bags: Map<String, Bag>): Boolean = this.innerBags.keys.contains(colour) or
            this.innerBags.keys.any { bags[it]?.containsColour(colour, bags) == true }

    fun numberOfInnerBags(bags: Map<String, Bag>): Int =
        this.innerBags.entries.sumBy { (bags[it.key]?.numberOfInnerBags(bags) ?: 0) * it.value + it.value }
}

private fun toInnerBags(input: List<String>) =
    input.associate { it.substring(2) to Integer.parseInt(it.substring(0, 1)) }