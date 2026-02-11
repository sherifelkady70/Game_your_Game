package com.example.game_your_game.core.utilits

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

inline fun <T, R> apiCallWithHandlingOffline(
    crossinline readCache: suspend () -> R?,
    crossinline apiCall: suspend () -> T,
    crossinline mapAndSave: suspend (T) -> R,
    crossinline emptyCheck: (R) -> Boolean = { false }
): Flow<NetworkStateResource<R>> = flow {
    emit(NetworkStateResource.Loading)
    val cached = readCache()
    if (cached != null) {
        emit(NetworkStateResource.Success(cached))
    }
    try {
        val response = apiCall()
        val result = mapAndSave(response)
        if (emptyCheck(result)) {
            emit(NetworkStateResource.Empty)
        } else {
            emit(NetworkStateResource.Success(result))
        }
    } catch (e: Exception) {
        emit(NetworkStateResource.Error(e))
    }
}