package com.joesemper.mymoviesdb.ui.compose

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.joesemper.mymoviesdb.R
import com.joesemper.mymoviesdb.data.model.Movie
import com.joesemper.mymoviesdb.ui.theme.PrimaryColor
import com.joesemper.mymoviesdb.ui.theme.PrimaryColorTransparent
import com.joesemper.mymoviesdb.ui.theme.SecondaryColorTransparent
import com.joesemper.mymoviesdb.ui.viewmodel.MovieViewModel
import com.joesemper.mymoviesdb.utils.BASE_IMG_URL
import okhttp3.internal.wait
import org.koin.androidx.compose.getViewModel
import java.lang.Float.min

private const val MinHeight = 56f
private const val MaxHeight = 300f

private const val MinAlpha = 0.1f
private const val MaxAlpha = 1f

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

    LaunchedEffect(movieId) {
        movieId?.let {
            movie.value = viewModel.getMoviesDetails(movieId = it)
        }
    }

    val scrollState = rememberLazyListState()

    val scrollOffset: Float = min(
        1f,
        1 - (scrollState.firstVisibleItemScrollOffset / 600f + scrollState.firstVisibleItemIndex)
    )

    movie.value?.let {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                MovieDetailsCollapsingToolbar(
                    movie = it,
                    scrollOffset = scrollOffset
                )
            }
        ) {
            BackgroundImage()
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                state = scrollState
            ) {
                items(20) {
                    Spacer(modifier = Modifier.size(100.dp))
                    Text(text = "Hello Scroll $it")
                }

            }

        }
    }

}

@Composable
private fun MovieDetailsCollapsingToolbar(movie: Movie, scrollOffset: Float) {
    val imageSize by animateDpAsState(targetValue = max(72.dp, 128.dp * scrollOffset))
    val toolbarHeight by animateDpAsState(targetValue = max(0.dp, 300.dp * scrollOffset))
    val dynamicAlpha by animateFloatAsState(targetValue = kotlin.math.min(1f, scrollOffset))
    val dynamicLines = kotlin.math.max(3f, scrollOffset * 6).toInt()


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp + toolbarHeight)
            .background(color = PrimaryColor)
    ) {
        Row(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DefaultNavigationIcon(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
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

@Composable
fun MainFilmInformation(
    modifier: Modifier = Modifier,
    movie: Movie
) {
    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        val (poster, score, date, budget, runtime, status, language) = createRefs()

        DefaultCard(
            Modifier
                .fillMaxHeight()
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
                contentScale = ContentScale.Crop,
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



