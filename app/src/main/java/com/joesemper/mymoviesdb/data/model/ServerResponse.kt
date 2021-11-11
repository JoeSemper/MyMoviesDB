package com.joesemper.mymoviesdb.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ServerResponse(
    val results: List<Result> = listOf()
) : Parcelable
