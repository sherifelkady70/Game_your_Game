package com.example.game_your_game.gamedetails.data.repository

import com.example.game_your_game.gamedetails.data.mapper.toDomain
import com.example.game_your_game.gamedetails.data.remote.GameDetailsApi
import com.example.game_your_game.gamedetails.domain.model.GameDetails
import com.example.game_your_game.gamedetails.domain.repository.GameDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GameDetailsRepositoryImpl @Inject constructor(
    private val api: GameDetailsApi
) : GameDetailsRepository {

    override fun getGameDetails(gameId: Int): Flow<Result<GameDetails>> = flow {
        try {
            val dto = api.getGameDetails(gameId)
            emit(Result.success(dto.toDomain()))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
