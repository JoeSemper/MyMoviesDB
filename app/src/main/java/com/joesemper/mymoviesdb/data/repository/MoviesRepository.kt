package com.joesemper.mymoviesdb.data.repository

import com.joesemper.mymoviesdb.data.model.ServerResponse

interface MoviesRepository {
    suspend fun getPopularMovies(): ServerResponse
}