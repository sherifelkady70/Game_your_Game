package com.example.game_your_game.gamedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.game_your_game.core.theme.ScreenBackground
import com.example.game_your_game.gamedetails.components.GameDetailsContent

@Composable
fun GameDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: GameDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    GameDetailsContent(
        state = state,
        modifier = modifier
            .fillMaxSize()
            .background(ScreenBackground)
    )
}
