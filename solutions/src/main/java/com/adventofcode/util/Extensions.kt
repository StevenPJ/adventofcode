package com.adventofcode.util


fun CharSequence.splitIgnoreEmpty(vararg delimiters: String): List<String> {
    return this.split(*delimiters).filter {
        it.isNotEmpty()
    }
}

fun CharSequence.nonEmptyLines(): List<String> {
    return this.splitIgnoreEmpty("\n")
}

fun <T> List<T>.eachPermutation(): Sequence<List<T>> =
    if (this.isEmpty()) sequenceOf(emptyList())
    else {
        val list = this
        sequence {
            list.forEach { item ->
                (list - item).eachPermutation().forEach {
                    yield(it + item)
                }
            }
        }
    }

fun <T> List<T>.eachPermutationChooseK(size: Int): Sequence<List<T>> =
    if (this.isEmpty() || size == 0) {
        sequenceOf(emptyList())
    } else {
        val list = this
        sequence {
            list.forEach { item ->
                (list - item).eachPermutationChooseK(size - 1).forEach {
                    yield(it + item)
                }
            }
        }
    }

fun <T> List<T>.eachUnorderedPermutationChooseK(size: Int): Sequence<List<T>> =
        if (this.isEmpty() || size == 0) {
            sequenceOf(emptyList())
        } else {
            val list = this
            sequence {
                list.forEach { item: T ->
                    (list.subList(list.indexOf(item) + 1, list.size)).eachPermutationChooseK(size - 1).forEach {items: List<T> ->
                        if (items.isNotEmpty() && item != null)
                            yield(items + item)
                    }
                }
            }
        }

fun <T> List<T>.eachCombinationsChooseK(size: Int): Sequence<List<T>> =
        if (this.isEmpty() || size == 0) {
            sequenceOf(emptyList())
        } else {
            val list = this
            sequence {
                list.forEach { item ->
                    (list).eachPermutationChooseK(size - 1).forEach {
                        yield(it + item)
                    }
                }
            }
        }

fun <T> List<T>.combinationsChooseK(size: Int): List<List<T>> =
        if (this.isEmpty() || size == 0) {
            listOf(emptyList())
        } else {
            val list = this
            list.flatMap { item ->
                (list).combinationsChooseK(size - 1).map { it + item }
            }
        }


fun <T> Set<T>.permutationsChooseK(size: Int): List<Set<T>> =
    if (this.isEmpty() || size == 0) {
        listOf(emptySet())
    } else {
        val list = this
        list.flatMap { item ->
            (list - item).permutationsChooseK(size - 1).map { it + item }
        }.distinct()
    }


fun <T> Sequence<T>.repeatIndefinitely(): Sequence<T> =
    generateSequence(this) { this }.flatten()

fun <T> List<T>.repeatIndefinitely(): Sequence<T> =
    this.asSequence().repeatIndefinitely()

fun List<Long>.lcm(): Long {
    var result = this[0]
    for (i in 1 until this.size) {
        result = findLCM(result, this[i])
    }
    return result
}

fun findLCM(a: Long, b: Long): Long {
    val larger = if (a > b) a else b
    val maxLcm = a * b
    var lcm = larger
    while (lcm <= maxLcm) {
        if ((lcm % a).toInt() == 0 && (lcm % b).toInt() == 0) {
            return lcm
        }
        lcm += larger
    }
    return maxLcm
}