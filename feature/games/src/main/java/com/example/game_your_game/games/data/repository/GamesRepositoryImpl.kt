package com.example.game_your_game.games.data.repository

import com.example.game_your_game.core.utilits.NetworkStateResource
import com.example.game_your_game.core.utilits.apiCallWithHandlingOffline
import com.example.game_your_game.games.data.local.dao.GamesDao
import com.example.game_your_game.games.data.mapper.GameMapper
import com.example.game_your_game.games.data.remote.GamesApi
import com.example.game_your_game.games.domain.model.Game
import com.example.game_your_game.games.domain.repository.GamesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GamesRepositoryImpl @Inject constructor(
    private val api: GamesApi,
    private val dao: GamesDao,
    private val mapper: GameMapper
) : GamesRepository {

    override fun getGamesByGenre(genreId: Int, page: Int): Flow<NetworkStateResource<List<Game>>> =
        apiCallWithHandlingOffline(
            readCache = {
                dao.getGamesByGenreOnce(genreId).map { mapper.mapToDomain(it) }.ifEmpty { null }
            },
            apiCall = { api.getGamesByGenre(genreId, page = page) },
            mapAndSave = { response ->
                val dtos = response.results.orEmpty()
                if (page == 1) dao.deleteByGenre(genreId)
                val entities = dtos.map { dto -> mapper.mapToEntity(dto, genreId, page) }
                dao.insertAll(entities)
                dao.getGamesByGenreOnce(genreId).map { mapper.mapToDomain(it) }
            },
            emptyCheck = { it.isEmpty() }
        )
}
