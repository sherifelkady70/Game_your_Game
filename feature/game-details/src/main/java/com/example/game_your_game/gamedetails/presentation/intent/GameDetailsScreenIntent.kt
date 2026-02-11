package com.example.game_your_game.gamedetails.presentation.intent

import com.example.game_your_game.gamedetails.domain.model.GameDetails

sealed class GameDetailsScreenIntent {
    data object OnRetry : GameDetailsScreenIntent()
}

sealed interface GameDetailsState {
    data object Loading : GameDetailsState
    data class Success(val details: GameDetails) : GameDetailsState
    data class Error(val message: String) : GameDetailsState
    data class Empty(val message: String) : GameDetailsState
}
