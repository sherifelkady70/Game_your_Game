package com.example.game_your_game.genres

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.game_your_game.core.navigation.NavRoutes
import com.example.game_your_game.core.theme.ScreenBackground
import com.example.game_your_game.genres.components.GenresListContent

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
        onRetry = { viewModel.setIntent(GenresScreenIntent.OnRetry) },
        modifier = modifier
            .fillMaxSize()
            .background(ScreenBackground)
    )
}
