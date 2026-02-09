package com.example.game_your_game.data.repository

import com.example.game_your_game.data.api.ApiService
import com.example.game_your_game.data.api.ExampleResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Example repository using Kotlin Flow.
 * Replace with your own data sources and mapping logic.
 */
class ExampleRepository @Inject constructor(
    private val api: ApiService
) {

    fun getExample(): Flow<Result<ExampleResponse>> = flow {
        try {
            val response = api.getExample()
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
