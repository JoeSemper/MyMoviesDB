package com.joesemper.mymoviesdb.ui.compose

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.joesemper.mymoviesdb.R
import com.joesemper.mymoviesdb.data.model.*
import com.joesemper.mymoviesdb.ui.theme.PrimaryColor
import com.joesemper.mymoviesdb.ui.viewmodel.MovieViewModel
import com.joesemper.mymoviesdb.utils.BASE_IMG_URL
import org.koin.androidx.compose.getViewModel
import java.lang.Float.min

@ExperimentalCoilApi
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun MovieScreen(
    navController: NavController,
    movieId: String?
) {
    val viewModel: MovieViewModel = getViewModel()

    val movie = remember {
        mutableStateOf<Movie?>(null)
    }

    val cast = remember {
        mutableStateOf<CastResult?>(null)
    }

    LaunchedEffect(movieId) {
        movieId?.let {
            movie.value = viewModel.getMoviesDetails(movieId = it)
            cast.value = viewModel.getMoviesCast(movieId = it)
        }
    }

    val scrollState = rememberLazyListState()

    val scrollOffset: Float = min(
        1f,
        1 - (scrollState.firstVisibleItemScrollOffset / 600f + scrollState.firstVisibleItemIndex)
    )

    movie.value?.let { film ->
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                MovieDetailsCollapsingToolbar(
                    movie = film,
                    scrollOffset = scrollOffset,
                    onNavClick = {
                        navController.popBackStack()
                    }
                )
            }
        ) {
            BackgroundImage()
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                state = scrollState
            ) {
                item {
                    FilmOverview(overview = film.overview)
                }
                item {
                    FilmGenres(genres = film.genres)
                }
                item {
                    FilmCompanies(companies = film.production_companies)
                }
                cast.value?.let {
                    item {
                        FilmCast(cast = it.cast)
                    }
                }
            }

        }
    }

}

@ExperimentalCoilApi
@Composable
private fun MovieDetailsCollapsingToolbar(
    movie: Movie,
    scrollOffset: Float,
    onNavClick: () -> Unit
) {
    val toolbarHeight by animateDpAsState(
        targetValue = if (max(200.dp, 300.dp * scrollOffset) > 200.dp) {
            (max(150.dp, 300.dp * scrollOffset))
        } else {
            0.dp
        }

    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp + toolbarHeight)
            .background(color = PrimaryColor)
    ) {
        Row(
            modifier = Modifier
                .background(color = PrimaryColor)
                .zIndex(1f)
                .height(56.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DefaultNavigationIcon(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp),
                onNavClick = onNavClick
            )
            Text(
                modifier = Modifier
                    .weight(10f)
                    .padding(horizontal = 4.dp),
                text = movie.title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6,
                maxLines = 2,
                softWrap = true,
                color = Color.White
            )
        }
        MainFilmInformation(
            modifier = Modifier.height(toolbarHeight),
            movie = movie
        )
    }
}

@ExperimentalCoilApi
@Composable
fun MainFilmInformation(
    modifier: Modifier = Modifier,
    movie: Movie
) {
    ConstraintLayout(modifier = modifier.fillMaxWidth().zIndex(0f)) {
        val (poster, score, date, budget, runtime, status, language) = createRefs()

        DefaultCard(
            Modifier
                .height(267.dp)
                .width(170.dp)
                .padding(horizontal = 8.dp, vertical = 20.dp)
                .constrainAs(poster) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    absoluteLeft.linkTo(parent.absoluteLeft)
                }
        ) {
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
        }

        Text(
            modifier = Modifier.constrainAs(date) {
                top.linkTo(parent.top, 16.dp)
                absoluteLeft.linkTo(poster.absoluteRight, 4.dp)
                bottom.linkTo(budget.top)
            },
            text = "Release: " + movie.release_date,
            style = MaterialTheme.typography.h6,
            color = Color.White
        )
        Text(
            modifier = Modifier.constrainAs(budget) {
                top.linkTo(date.bottom, 8.dp)
                absoluteLeft.linkTo(poster.absoluteRight, 4.dp)
                bottom.linkTo(runtime.top)
            },
            text = "Budget: " + movie.budget + "$",
            style = MaterialTheme.typography.h6,
            color = Color.White
        )
        Text(
            modifier = Modifier.constrainAs(runtime) {
                top.linkTo(budget.bottom, 8.dp)
                absoluteLeft.linkTo(poster.absoluteRight, 4.dp)
                bottom.linkTo(status.top)
            },
            text = "Runtime: " + movie.runtime + " min",
            style = MaterialTheme.typography.h6,
            color = Color.White
        )

        Text(
            modifier = Modifier.constrainAs(status) {
                top.linkTo(runtime.bottom, 8.dp)
                absoluteLeft.linkTo(poster.absoluteRight, 4.dp)
                bottom.linkTo(language.top)
            },
            text = "Status: " + movie.status,
            style = MaterialTheme.typography.h6,
            color = Color.White
        )

        Text(
            modifier = Modifier.constrainAs(language) {
                top.linkTo(status.bottom, 8.dp)
                absoluteLeft.linkTo(poster.absoluteRight, 4.dp)
                bottom.linkTo(parent.bottom, 16.dp)
            },
            text = "Language: " + movie.original_language,
            style = MaterialTheme.typography.h6,
            color = Color.White
        )

        RatingItem(
            modifier = Modifier.constrainAs(score) {
                bottom.linkTo(parent.bottom, 8.dp)
                absoluteRight.linkTo(parent.absoluteRight, 8.dp)
            },
            score = movie.vote_average
        )

    }
}

@Composable
fun FilmOverview(modifier: Modifier = Modifier, overview: String) {
    DefaultCard(modifier = modifier.padding(horizontal = 4.dp, vertical = 8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Overview",
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Divider()
            Spacer(modifier = Modifier.padding(4.dp))
            Text(text = overview)
        }


    }
}

@Composable
fun FilmGenres(modifier: Modifier = Modifier, genres: List<Genre>) {
    DefaultCard(modifier = modifier.padding(horizontal = 4.dp, vertical = 8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Genres",
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Divider()
            Spacer(modifier = Modifier.padding(4.dp))
            genres.forEach {
                Text(text = it.name)
            }

        }
    }
}

@ExperimentalCoilApi
@Composable
fun FilmCompanies(modifier: Modifier = Modifier, companies: List<ProductionCompany>) {
    DefaultCard(modifier = modifier.padding(horizontal = 4.dp, vertical = 8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Companies",
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Divider()
            Spacer(modifier = Modifier.padding(4.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(companies.size) { index ->
                    Column(
                        modifier = Modifier
                            .width(80.dp)
                            .height(140.dp)
                    ) {
                        Image(
                            painter = rememberImagePainter(
                                data = BASE_IMG_URL + companies[index].logo_path
                            ),
                            contentDescription = stringResource(R.string.photo),
                            contentScale = ContentScale.Inside,
                            modifier = Modifier
                                .zIndex(-1.0f)
                                .weight(6f)
                                .fillMaxSize()
                        )
                        Text(
                            modifier = Modifier.weight(2f),
                            text = companies[index].name,
                            style = MaterialTheme.typography.body2,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun FilmCast(modifier: Modifier = Modifier, cast: List<Cast>) {
    DefaultCard(modifier = modifier.padding(horizontal = 4.dp, vertical = 8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Cast",
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Divider()
            Spacer(modifier = Modifier.padding(4.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(cast.size) { index ->
                    Column(
                        modifier = Modifier
                            .width(80.dp)
                            .height(140.dp)
                    ) {
                        Image(
                            painter = rememberImagePainter(
                                data = BASE_IMG_URL + cast[index].profile_path,
                            ),
                            contentDescription = stringResource(R.string.photo),
                            contentScale = ContentScale.Inside,
                            modifier = Modifier
                                .zIndex(-1.0f)
                                .weight(6f)
                                .fillMaxSize()
                        )
                        Text(
                            modifier = Modifier.weight(2f),
                            text = cast[index].name,
                            style = MaterialTheme.typography.body2,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}




