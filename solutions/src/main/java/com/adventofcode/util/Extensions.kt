package com.adventofcode.util


fun CharSequence.splitIgnoreEmpty(vararg delimiters: String): List<String> {
    return this.split(*delimiters).filter {
        it.isNotEmpty()
    }
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


fun <T> List<T>.permutationsChooseK(size: Int): List<List<T>> =
    if (this.isEmpty() || size == 0) {
        listOf(emptyList())
    } else {
        val list = this
        list.flatMap { item ->
            (list - item).permutationsChooseK(size - 1).map { it + item }
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