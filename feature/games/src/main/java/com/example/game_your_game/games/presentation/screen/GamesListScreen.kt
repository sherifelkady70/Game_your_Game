package com.example.game_your_game.games.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.game_your_game.core.navigation.NavRoutes
import com.example.game_your_game.core.theme.ScreenBackground
import com.example.game_your_game.games.presentation.viewmodel.GamesListViewModel
import com.example.game_your_game.games.presentation.intent.GamesScreenIntent
import com.example.game_your_game.games.presentation.components.GamesListContent
import com.example.game_your_game.games.presentation.intent.GamesScreenEffect

@Composable
fun GamesListScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: GamesListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.viewEffect.collect {
            when (it) {
                is GamesScreenEffect.NavigateToGameDetails -> {
                    navController.navigate(NavRoutes.gameDetails(it.gameId))
                }
            }
        }
    }
    GamesListContent(
        state = state,
        onGameClick = { game ->
            viewModel.setIntent(GamesScreenIntent.OnGameClicked(game.id))
        },
        onScrollPosition = { lastVisibleIndex, totalItems ->
            viewModel.setIntent(GamesScreenIntent.OnScrollPosition(lastVisibleIndex, totalItems))
        },
        onRetry = { viewModel.setIntent(GamesScreenIntent.OnRetry) },
        modifier = modifier
            .fillMaxSize()
            .background(ScreenBackground)
    )
}
