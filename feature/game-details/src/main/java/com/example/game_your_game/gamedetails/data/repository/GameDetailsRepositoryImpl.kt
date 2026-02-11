package com.example.game_your_game.gamedetails.data.repository

import com.example.game_your_game.core.utilits.NetworkStateResource
import com.example.game_your_game.core.utilits.apiCallWithHandlingOffline
import com.example.game_your_game.gamedetails.data.local.dao.GameDetailsDao
import com.example.game_your_game.gamedetails.data.mapper.GameDetailsMapper
import com.example.game_your_game.gamedetails.data.remote.GameDetailsApi
import com.example.game_your_game.gamedetails.domain.model.GameDetails
import com.example.game_your_game.gamedetails.domain.repository.GameDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GameDetailsRepositoryImpl @Inject constructor(
    private val api: GameDetailsApi,
    private val dao: GameDetailsDao,
    private val mapper: GameDetailsMapper
) : GameDetailsRepository {

    override fun getGameDetails(gameId: Int): Flow<NetworkStateResource<GameDetails>> =
        apiCallWithHandlingOffline(
            readCache = { dao.getById(gameId)?.let { mapper.mapToDomain(it) } },
            apiCall = { api.getGameDetails(gameId) },
            mapAndSave = { dto ->
                dao.insert(mapper.mapToEntity(dto))
                dao.getById(gameId)?.let { mapper.mapToDomain(it) } ?: mapper.mapToDomain(dto)
            }
        )
}
