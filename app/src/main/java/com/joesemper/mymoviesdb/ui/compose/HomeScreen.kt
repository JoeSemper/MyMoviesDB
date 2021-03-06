package com.joesemper.mymoviesdb.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.joesemper.mymoviesdb.R
import com.joesemper.mymoviesdb.data.model.Result
import com.joesemper.mymoviesdb.ui.viewmodel.HomeViewModel
import com.joesemper.mymoviesdb.utils.BASE_IMG_URL
import com.joesemper.mymoviesdb.utils.MOVIE_SCREEN
import org.koin.androidx.compose.getViewModel

@ExperimentalCoilApi
@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: HomeViewModel = getViewModel()

    val lazyPagingItems = viewModel.pager.collectAsLazyPagingItems()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DefaultAppBar(
                title = stringResource(R.string.app_name)
            )
        }

    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            BackgroundImage()
            if (lazyPagingItems.itemCount > 0) {
                MoviesList(
                    movies = lazyPagingItems,
                    onItemClick = { movieId ->
                        navController.navigate("$MOVIE_SCREEN/$movieId")
                    })
            } else {
                LoadingScreen()
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun MoviesList(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<List<Result>>,
    onItemClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {

        if (movies.loadState.refresh == LoadState.Loading) {
            item {
                Text(
                    text = "Waiting for items to load from the backend",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }

        itemsIndexed(movies) { index, item ->
            MoviesRowItem(
                movies = item,
                onItemClick = onItemClick
            )
        }

        if (movies.loadState.append == LoadState.Loading) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun MoviesRowItem(
    movies: List<Result>?,
    onItemClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 6.dp, horizontal = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        movies?.forEach {
            MovieItem(
                modifier = Modifier.weight(1f),
                movie = it,
                onItemClick = onItemClick
            )
        }
    }
}

@ExperimentalCoilApi
@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    movie: Result,
    onItemClick: (Int) -> Unit,
) {
    DefaultCard(
        modifier = modifier.height(330.dp),
        onClick = {
            onItemClick(movie.id)
        }) {

        Column() {
            Box(modifier = Modifier.weight(8f)) {
                Image(
                    painter = rememberImagePainter(
                        data = BASE_IMG_URL + movie.poster_path,
                    ),
                    contentDescription = stringResource(R.string.photo),
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .zIndex(-1.0f)
                        .fillMaxSize()
                )
                RatingItem(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(4.dp),
                    score = movie.vote_average
                )
            }
            SubtitleItem(
                modifier = Modifier.weight(1.2f),
                title = movie.title
            )
        }
    }


}
