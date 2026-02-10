package com.example.game_your_game.genres

sealed class GenresScreenIntent {
    data object OnRetry : GenresScreenIntent()
}
