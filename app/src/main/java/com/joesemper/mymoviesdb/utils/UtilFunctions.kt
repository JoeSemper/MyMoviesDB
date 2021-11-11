package com.joesemper.mymoviesdb.utils

import com.joesemper.mymoviesdb.data.model.Result

fun sortByPairs(list: List<Result>): List<List<Result>> {
    val result: MutableList<MutableList<Result>> = mutableListOf()

    list.forEachIndexed { index, item ->
        if (index.isEven()) {
            result.add(mutableListOf(item))
        } else {
            result.last().add(item)
        }
    }
    return result
}

fun Int.isEven(): Boolean {
    return (this % 2 == 0)
}
