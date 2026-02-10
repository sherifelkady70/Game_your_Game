package com.example.game_your_game.games

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
import com.example.game_your_game.games.components.GamesListContent

@Composable
fun GamesListScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: GamesListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    GamesListContent(
        state = state,
        onGameClick = { game ->
            navController.navigate(NavRoutes.gameDetails(game.id))
        },
        onLoadMore = { viewModel.setIntent(GamesScreenIntent.OnLoadMore) },
        modifier = modifier
            .fillMaxSize()
            .background(ScreenBackground)
    )
}
