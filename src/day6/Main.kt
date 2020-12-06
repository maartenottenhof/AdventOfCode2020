package day6

import java.io.File

fun main(args: Array<String>) {
    val forms = File("src/day6/input.txt").readText()
        .split("\n\n")
        .map { it.trim().lines().map(String::toSet) }

    println(task1(forms))
    println(task2(forms))
}

private fun task1(forms: List<List<Set<Char>>>) = forms
    .map { it.reduce{ acc, chars -> acc + chars } }
    .map { it.count() }.sum()

private fun task2(forms: List<List<Set<Char>>>) = forms
    .map { it.reduceRight{ acc, set -> acc intersect set } }
    .map { it.count() }.sum()
