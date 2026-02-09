package com.example.game_your_game.games.data.repository

import com.example.game_your_game.core.utilits.NetworkStateResource
import com.example.game_your_game.core.utilits.genericApiCall
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

    override fun getGamesByGenre(genreId: Int): Flow<NetworkStateResource<List<Game>>> =
        genericApiCall(
            apiCall = {api.getGamesByGenre(genreId)},
            mapper = { it.results?.map { dto -> dto.toDomain() } as List<Game> }
        )
}
