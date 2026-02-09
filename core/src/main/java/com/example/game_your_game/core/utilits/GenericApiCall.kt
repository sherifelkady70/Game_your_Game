package com.example.game_your_game.core.utilits

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

inline fun <T, R> genericApiCall(
    crossinline apiCall: suspend () -> T,
    crossinline mapper: (T) -> R
): Flow<NetworkStateResource<R>> = flow {
    emit(NetworkStateResource.Loading)

    try {
        val response = apiCall()
        val result = mapper(response)
        emit(NetworkStateResource.Success(result))
    } catch (e: Exception) {
        emit(NetworkStateResource.Error(e))
    }
}