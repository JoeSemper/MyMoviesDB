package com.joesemper.mymoviesdb.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.joesemper.mymoviesdb.data.repository.MoviesRepository

class HomeViewModel(private val repository: MoviesRepository) : ViewModel() {

    suspend fun getPopularMovies() = repository.getPopularMovies()
}

