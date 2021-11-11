package com.joesemper.mymoviesdb.data.api

import com.joesemper.mymoviesdb.data.model.ServerResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDatabaseApiService {

    @GET("discover/movie")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = "274f828ad283bd634ef4fc1ee4af255f",
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("page") page: Int = 1
    ): ServerResponse

}