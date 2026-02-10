package com.example.game_your_game.gamedetails.data.repository

import com.example.game_your_game.core.utilits.NetworkStateResource
import com.example.game_your_game.core.utilits.apiCallWithHandlingOffline
import com.example.game_your_game.gamedetails.data.local.dao.GameDetailsDao
import com.example.game_your_game.gamedetails.data.mapper.toDomain
import com.example.game_your_game.gamedetails.data.mapper.toEntity
import com.example.game_your_game.gamedetails.data.remote.GameDetailsApi
import com.example.game_your_game.gamedetails.domain.model.GameDetails
import com.example.game_your_game.gamedetails.domain.repository.GameDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GameDetailsRepositoryImpl @Inject constructor(
    private val api: GameDetailsApi,
    private val dao: GameDetailsDao
) : GameDetailsRepository {

    override fun getGameDetails(gameId: Int): Flow<NetworkStateResource<GameDetails>> =
        apiCallWithHandlingOffline(
            readCache = { dao.getById(gameId)?.toDomain() },
            apiCall = { api.getGameDetails(gameId) },
            mapAndSave = { dto ->
                dao.insert(dto.toEntity())
                dao.getById(gameId)?.toDomain() ?: dto.toDomain()
            }
        )
}
