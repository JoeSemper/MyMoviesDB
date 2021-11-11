package com.joesemper.mymoviesdb.data.repository

import com.joesemper.mymoviesdb.data.model.Movie
import com.joesemper.mymoviesdb.data.model.MoviesResult

interface MoviesRepository {
    suspend fun getPopularMovies(): MoviesResult
    suspend fun getMovieDetails(movieId: String): Movie
}