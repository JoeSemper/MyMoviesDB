package com.joesemper.mymoviesdb.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.joesemper.mymoviesdb.R
import com.joesemper.mymoviesdb.data.model.Movie
import com.joesemper.mymoviesdb.ui.viewmodel.MovieViewModel
import com.joesemper.mymoviesdb.utils.BASE_IMG_URL
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
            Image(
                painter = rememberImagePainter(BASE_IMG_URL + it.poster_path),
                contentDescription = stringResource(R.string.photo),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(5.dp))
            )
        }
    }
}