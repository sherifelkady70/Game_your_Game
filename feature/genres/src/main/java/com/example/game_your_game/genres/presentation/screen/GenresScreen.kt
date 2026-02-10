package com.example.game_your_game.genres.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.game_your_game.core.navigation.NavRoutes
import com.example.game_your_game.core.theme.ScreenBackground
import com.example.game_your_game.genres.presentation.intent.GenresScreenIntent
import com.example.game_your_game.genres.components.GenresListContent
import com.example.game_your_game.genres.presentation.intent.GenresScreenEffect
import com.example.game_your_game.genres.presentation.viewmodel.GenresViewModel

@Composable
fun GenresScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: GenresViewModel = hiltViewModel()
) {
    val state by viewModel.genreState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.viewEffect.collect {
            when(it) {
                is GenresScreenEffect.NavigateToGameList -> navController.navigate(NavRoutes
                    .gamesList(it.genreId))
            }

        }
    }
    GenresListContent(
        state = state,
        onGenreClick = { genre ->
           viewModel.setIntent(GenresScreenIntent.OnGenresClicked(genre.id))
        },
        onRetry = { viewModel.setIntent(GenresScreenIntent.OnRetry) },
        modifier = modifier
            .fillMaxSize()
            .background(ScreenBackground)
    )
}
