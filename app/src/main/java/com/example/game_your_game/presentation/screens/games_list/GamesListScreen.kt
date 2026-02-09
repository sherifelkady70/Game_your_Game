package com.example.game_your_game.presentation.screens.games_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.game_your_game.presentation.navigation.NavRoutes
import com.example.game_your_game.presentation.screens.games_list.components.GamesListContent
import com.example.game_your_game.presentation.theme.ScreenBackground
import androidx.navigation.NavController

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
        modifier = modifier
            .fillMaxSize()
            .background(ScreenBackground)
    )
}
