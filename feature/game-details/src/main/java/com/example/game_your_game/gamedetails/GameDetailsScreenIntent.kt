package com.example.game_your_game.gamedetails

sealed class GameDetailsScreenIntent {
    data object OnRetry : GameDetailsScreenIntent()
}
