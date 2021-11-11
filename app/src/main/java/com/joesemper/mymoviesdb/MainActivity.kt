package com.joesemper.mymoviesdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.joesemper.mymoviesdb.ui.compose.HomeScreen
import com.joesemper.mymoviesdb.ui.compose.MovieScreen
import com.joesemper.mymoviesdb.ui.theme.MyMoviesDBTheme
import com.joesemper.mymoviesdb.utils.HOME_SCREEN
import com.joesemper.mymoviesdb.utils.MOVIE_SCREEN

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyMoviesDBTheme {
                Navigation()
            }
        }
    }

    @Composable
    fun Navigation() {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = HOME_SCREEN
        ) {
            composable(HOME_SCREEN) {
                HomeScreen(navController = navController)
            }
            composable(
                "$MOVIE_SCREEN/{movieId}",
                arguments = listOf(navArgument("movieId") { type = NavType.StringType })
            ) { backStackEntry ->
                MovieScreen(
                    navController = navController,
                    backStackEntry.arguments?.getString("movieId")
                )
            }
        }
    }
}