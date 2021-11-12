package com.joesemper.mymoviesdb.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.joesemper.mymoviesdb.R
import com.joesemper.mymoviesdb.ui.theme.SecondaryColorTransparent

@Composable
fun DefaultAppBar(
    modifier: Modifier = Modifier,
    navIcon: ImageVector? = Icons.Default.ArrowBack,
    onNavClick: () -> Unit,
    title: String,
    actions: @Composable() (RowScope.() -> Unit) = {}
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            navIcon?.let {
                IconButton(onClick = { onNavClick() }) {
                    Icon(
                        imageVector = navIcon,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        },
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
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClick() }
        ,
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