package com.joesemper.mymoviesdb.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.joesemper.mymoviesdb.R
import com.joesemper.mymoviesdb.ui.theme.*
import java.time.format.TextStyle

@Composable
fun DefaultAppBar(
    modifier: Modifier = Modifier,
    navigationIcon: @Composable() (() -> Unit)? = null,
    title: String,
    actions: @Composable() (RowScope.() -> Unit) = {}
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = title) },
        navigationIcon = navigationIcon,
        elevation = 4.dp,
        actions = actions
    )
}

@Composable
fun LoadingScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun DefaultCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClick() },
        elevation = 8.dp,
        shape = RoundedCornerShape(6.dp),
        content = content
    )
}

@Composable
fun BackgroundImage(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.fillMaxSize(),
        colorFilter = ColorFilter.tint(
            SecondaryColorTransparent,
            BlendMode.ColorDodge
        ),
        painter = painterResource(id = R.drawable.ic_pattern),
        contentDescription = "",
        alpha = 0.1f,
        contentScale = ContentScale.FillWidth
    )
}

@Composable
fun RatingItem(
    modifier: Modifier = Modifier,
    score: Double
) {
    Card(
        modifier = modifier
            .size(40.dp)
            .zIndex(2.0f),
        shape = CircleShape,
        backgroundColor = when {
            score >= 7.0 -> GoodRatingColor
            score <5.0 -> BadRatingColor
            else -> AverageRatingColor
        }
    ) {
        Box() {
            Text(
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.h6,
                text = score.toString(),
                color = Color.White
            )
        }

    }
}

@Composable
fun SubtitleItem(
    modifier: Modifier = Modifier,
    title: String
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp),
        elevation = 4.dp,
        color = PrimaryColorTransparent
    ) {
        Box(modifier = Modifier.background(color = PrimaryColorTransparent)) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 2.dp),
                text = title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1,
                fontSize = 14.sp,
                maxLines = 2,
                softWrap = true,
                color = Color.White
            )
        }

    }
}

@Composable
fun DefaultNavigationIcon(
    modifier: Modifier = Modifier,
    navIcon: ImageVector = Icons.Default.ArrowBack,
    onNavClick: () -> Unit = {},
) {
    IconButton(
        modifier = modifier.size(48.dp),
        onClick = { onNavClick() }
    ) {
        Icon(
            imageVector = navIcon,
            tint = Color.White,
            contentDescription = stringResource(R.string.back)
        )
    }
}