package day21

import java.io.File

fun main() {
    val foods = File("src/day21/input.txt").readLines()
    val mappedAllergens = mapAllergenToIngredients(foods)

    println(task1(foods, mappedAllergens))
    println(task2(mappedAllergens))
}

private fun task2(mappedAllergens: MutableMap<String, String>) =
    mappedAllergens.toSortedMap().values.joinToString(",")

private fun task1(foods: List<String>, mappedAllergens: MutableMap<String, String>) =
    foods.map { it.substringBefore(" (").split(" ") }.flatten()
        .filterNot { it in mappedAllergens.values }.size

private fun mapAllergenToIngredients(foods: List<String>): MutableMap<String, String> {
    val ingredients = mutableMapOf<String, MutableMap<String, Int>>()
    foods.forEach{ s ->
        s.substringBefore(" (").split(" ").forEach { ingredient ->
            s.substringAfter("(contains").trim(')').split(",").forEach {
                val currentAllergens = ingredients[ingredient] ?: mutableMapOf()
                currentAllergens[it.trim()] = (currentAllergens[it.trim()] ?: 0) + 1
                ingredients[ingredient] = currentAllergens
            }
        }
    }

    val allergens = ingredients.map { it.value.keys }.flatten().toMutableSet()
    val mappedAllergens = mutableMapOf<String, String>()
    while (allergens.isNotEmpty()) {
        for (allergen in allergens.toSet()) {
            val max = ingredients.maxOfOrNull { it.value[allergen] ?: 0 }
            val occurrences = ingredients.filter { it.value[allergen] == max }
            if (occurrences.count() == 1) {
                val ingredient = occurrences.keys.first()
                mappedAllergens[allergen] = ingredient
                ingredients.remove(ingredient)
                allergens.remove(allergen)
                ingredients.forEach { it.value.remove(allergen) }
            }
        }
    }

    return mappedAllergens
}