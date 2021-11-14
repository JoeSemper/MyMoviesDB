package com.joesemper.mymoviesdb.data.api

import com.joesemper.mymoviesdb.data.model.CastResult
import com.joesemper.mymoviesdb.data.model.Movie
import com.joesemper.mymoviesdb.data.model.MoviesResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDatabaseApiService {

    @GET("discover/movie")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = "274f828ad283bd634ef4fc1ee4af255f",
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("page") page: Int
    ): MoviesResult

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String = "274f828ad283bd634ef4fc1ee4af255f",
        @Query("language") language: String = "en-US",
    ): Movie

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String = "274f828ad283bd634ef4fc1ee4af255f",
        @Query("language") language: String = "en-US",
    ): CastResult

}