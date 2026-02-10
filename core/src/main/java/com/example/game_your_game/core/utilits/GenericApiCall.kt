package com.example.game_your_game.core.utilits

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Network-only API call: emits Loading, then Success(mapped result) or Error.
 * Use when there is no local cache.
 */
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

/**
 * Offline-first API call: emits Loading, then Success(cached) if any, then runs [apiCall],
 * saves via [mapAndSave] and emits Success(result). On error, emits Success(cached) or Error.
 * Use to unify repos that have Room (read cache → sync from API → emit from DB).
 */
inline fun <T, R> apiCallWithHandlingOffline(
    crossinline readCache: suspend () -> R?,
    crossinline apiCall: suspend () -> T,
    crossinline mapAndSave: suspend (T) -> R
): Flow<NetworkStateResource<R>> = flow {
    emit(NetworkStateResource.Loading)
    val cached = readCache()
    if (cached != null) {
        emit(NetworkStateResource.Success(cached))
    }
    try {
        val response = apiCall()
        val result = mapAndSave(response)
        emit(NetworkStateResource.Success(result))
    } catch (e: Exception) {
        if (cached != null) {
            emit(NetworkStateResource.Success(cached))
        } else {
            emit(NetworkStateResource.Error(e))
        }
    }
}