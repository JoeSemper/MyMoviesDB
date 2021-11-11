package com.joesemper.mymoviesdb.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Result(
    val adult: Boolean = false,
    val backdropPath: String = "",
    val genreIds: List<Int> = listOf(),
    val id: Int,
    val originalLanguage: String = "",
    val originalTitle: String = "",
    val overview: String = "",
    val popularity: Float = 0.0f,
    val posterPath: String = "",
    val releaseDate: String = "",
    val title: String = "",
    val voteAverage: Float = 0.0f,
    val vote_count: Int = 0
) : Parcelable