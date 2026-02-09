package com.example.game_your_game.games.data.repository

import com.example.game_your_game.games.data.mapper.toDomain
import com.example.game_your_game.games.data.remote.GamesApi
import com.example.game_your_game.games.domain.model.Game
import com.example.game_your_game.games.domain.repository.GamesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GamesRepositoryImpl @Inject constructor(
    private val api: GamesApi
) : GamesRepository {

    override fun getGamesByGenre(genreId: Int): Flow<Result<List<Game>>> = flow {
        try {
            val response = api.getGamesByGenre(genreId)
            val list = response.results?.map { it.toDomain() } ?: emptyList()
            emit(Result.success(list))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
