package com.joesemper.mymoviesdb.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.joesemper.mymoviesdb.data.repository.MoviesRepository

class MovieViewModel(private val repository: MoviesRepository) : ViewModel() {

    suspend fun getMoviesDetails(movieId: String) = repository.getMovieDetails(movieId)

}