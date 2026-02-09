package com.example.game_your_game.presentation.screens.genres

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.game_your_game.presentation.navigation.NavRoutes
import com.example.game_your_game.presentation.screens.genres.components.GenresListContent
import com.example.game_your_game.presentation.theme.ScreenBackground
import androidx.navigation.NavController

@Composable
fun GenresScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: GenresViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    GenresListContent(
        state = state,
        onGenreClick = { genre ->
            navController.navigate(NavRoutes.gamesList(genre.id))
        },
        modifier = modifier
            .fillMaxSize()
            .background(ScreenBackground)
    )
}
