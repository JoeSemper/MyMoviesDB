package com.joesemper.mymoviesdb.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoviesResponse(
    val results: List<Result> = listOf()
) : Parcelable
