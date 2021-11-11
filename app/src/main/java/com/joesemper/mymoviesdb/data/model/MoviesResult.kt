package com.joesemper.mymoviesdb.data.model

data class MoviesResult(
    val page: Int = 0,
    val results: List<Result> = listOf(),
    val total_pages: Int = 0,
    val total_results: Int = 0
)