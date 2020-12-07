package day6

import java.io.File

fun main(args: Array<String>) {
    val forms = File("src/day6/input.txt").readText().split("\n\n")
        .map { it.trim().lines().map(String::toSet) }

    println(task1(forms))
    println(task2(forms))
}

private fun task1(forms: List<List<Set<Char>>>) = forms.sumBy { it.reduce { acc, chars -> acc + chars }.size }

private fun task2(forms: List<List<Set<Char>>>) = forms.sumBy { it.reduceRight { acc, set -> acc intersect set }.size }