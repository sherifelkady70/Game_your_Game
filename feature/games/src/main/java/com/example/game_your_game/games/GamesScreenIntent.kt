package com.example.game_your_game.games

sealed class GamesScreenIntent {
    data object OnLoadMore : GamesScreenIntent()
    data object OnRetry : GamesScreenIntent()
}