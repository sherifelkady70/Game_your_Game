package com.example.game_your_game

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.game_your_game.core.navigation.NavRoutes
import com.example.game_your_game.gamedetails.presentation.screen.GameDetailsScreen
import com.example.game_your_game.games.presentation.screen.GamesListScreen
import com.example.game_your_game.genres.presentation.screen.GenresScreen

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.GENRES
    ) {
        composable(NavRoutes.GENRES) {
            GenresScreen(navController = navController)
        }
        composable(
            route = NavRoutes.GAMES_LIST,
            arguments = listOf(navArgument("genreId") { type = NavType.IntType })
        ) {
            GamesListScreen(navController = navController)
        }
        composable(
            route = NavRoutes.GAME_DETAILS,
            arguments = listOf(navArgument("gameId") { type = NavType.IntType })
        ) {
            GameDetailsScreen()
        }
    }
}
