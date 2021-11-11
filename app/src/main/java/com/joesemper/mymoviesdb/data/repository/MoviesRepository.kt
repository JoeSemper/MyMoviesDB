package com.joesemper.mymoviesdb.data.repository

import com.joesemper.mymoviesdb.data.model.MoviesResponse
import com.joesemper.mymoviesdb.data.model.Movie

interface MoviesRepository {
    suspend fun getPopularMovies(): MoviesResponse
    suspend fun getMovieDetails(movieId: String): Movie
}