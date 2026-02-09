package com.example.game_your_game.core.utilits

sealed class NetworkStateResource<out T> {
    data object Loading : NetworkStateResource<Nothing>()
    data class Success<T>(val data: T) : NetworkStateResource<T>()
    data class Error(val throwable: Throwable) : NetworkStateResource<Nothing>()
}
