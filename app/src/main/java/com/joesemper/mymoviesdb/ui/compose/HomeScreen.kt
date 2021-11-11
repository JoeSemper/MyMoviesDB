package com.joesemper.mymoviesdb.ui.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.joesemper.mymoviesdb.data.model.Result
import com.joesemper.mymoviesdb.ui.viewmodel.HomeViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = getViewModel()

    val page = remember {
        mutableStateOf(1)
    }

    val movies = remember {
        mutableStateOf(listOf<Result>())
    }

    LaunchedEffect(page) {
        movies.value = viewModel.getPopularMovies().results
    }

    Scaffold(modifier = Modifier.fillMaxSize()) {

        if(movies.value.isNotEmpty()) {
            Text(text = movies.value.first().title)
        }
    }


}
