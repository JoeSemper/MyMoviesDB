package com.joesemper.mymoviesdb.data.datasource

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.joesemper.mymoviesdb.data.api.MovieDatabaseApiService
import com.joesemper.mymoviesdb.data.repository.MoviesRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoviesRepositoryRetrofitImpl: MoviesRepository {
    companion object {
        private const val BASE_WEATHER_URL = "https://api.themoviedb.org/3/"
    }

    override suspend fun getPopularMovies(page: Int) = getService().getPopularMovies(page = page)

    override suspend fun getMovieDetails(movieId: String) = getService().getMovieDetails(movieId)

    private fun getService(): MovieDatabaseApiService {
        return createRetrofit().create(MovieDatabaseApiService::class.java)
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_WEATHER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(createOkHttpClient())
            .build()
    }

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

}