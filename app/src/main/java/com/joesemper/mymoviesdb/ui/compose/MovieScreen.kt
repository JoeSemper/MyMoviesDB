package com.joesemper.mymoviesdb.ui.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.joesemper.mymoviesdb.data.model.Movie
import com.joesemper.mymoviesdb.ui.viewmodel.MovieViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun MovieScreen(
    navController: NavController,
    movieId: String?
) {
    val viewModel: MovieViewModel = getViewModel()

    val movie = remember {
        mutableStateOf<Movie?>(null)
    }

    LaunchedEffect(movieId) {
        movieId?.let {
            movie.value = viewModel.getMoviesDetails(movieId = it)
        }
    }

    Scaffold(modifier= Modifier.fillMaxSize()) {
        movie.value?.let {
            Text(text = it.overview)
        }
    }
}