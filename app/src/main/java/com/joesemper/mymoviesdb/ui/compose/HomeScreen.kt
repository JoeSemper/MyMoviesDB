package com.joesemper.mymoviesdb.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.joesemper.mymoviesdb.R
import com.joesemper.mymoviesdb.data.model.Result
import com.joesemper.mymoviesdb.ui.viewmodel.HomeViewModel
import com.joesemper.mymoviesdb.utils.BASE_IMG_URL
import com.joesemper.mymoviesdb.utils.MOVIE_SCREEN
import com.joesemper.mymoviesdb.utils.sortByPairs
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(navController: NavController) {
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

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DefaultAppBar(
                onNavClick = {
                    navController.popBackStack()
                },
                title = stringResource(R.string.app_name)
            )
        }

    ) {
        if (movies.value.isNotEmpty()) {
            MoviesList(
                movies = movies,
                onItemClick = { movieId ->
                    navController.navigate("$MOVIE_SCREEN/$movieId")
                })
        } else {
            LoadingScreen()
        }
    }
}

@Composable
fun MoviesList(
    modifier: Modifier = Modifier,
    movies: MutableState<List<Result>>,
    onItemClick: (Int) -> Unit
) {
    val moviesSortedList = sortByPairs(movies.value)

    LazyColumn {
        items(moviesSortedList.size) { index ->
            MoviesRowItem(
                movies = moviesSortedList[index],
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
fun MoviesRowItem(
    movies: List<Result>,
    onItemClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 4.dp, horizontal = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        movies.forEach {
            MovieItem(
                modifier = Modifier.weight(1f),
                movie = it,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    movie: Result,
    onItemClick: (Int) -> Unit,
) {
    DefaultCard(
        modifier = modifier.height(300.dp),
        onClick = {
            onItemClick(movie.id)
        }) {
        Image(
            painter = rememberImagePainter(
               data = BASE_IMG_URL + movie.poster_path,
            ),
            contentDescription = stringResource(R.string.photo),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(5.dp))
        )

    }
}
