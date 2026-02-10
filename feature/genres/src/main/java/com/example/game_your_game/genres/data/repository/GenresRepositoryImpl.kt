package com.example.game_your_game.genres.data.repository

import com.example.game_your_game.core.utilits.NetworkStateResource
import com.example.game_your_game.core.utilits.apiCallWithHandlingOffline
import com.example.game_your_game.genres.data.local.dao.GenresDao
import com.example.game_your_game.genres.data.mapper.toDomain
import com.example.game_your_game.genres.data.mapper.toEntity
import com.example.game_your_game.genres.data.remote.GenresApi
import com.example.game_your_game.genres.domain.model.Genre
import com.example.game_your_game.genres.domain.repository.GenresRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GenresRepositoryImpl @Inject constructor(
    private val api: GenresApi,
    private val dao: GenresDao
) : GenresRepository {

    override fun getGenres(): Flow<NetworkStateResource<List<Genre>>> =
        apiCallWithHandlingOffline(
            readCache = {
                dao.getAllGenresOnce().map { it.toDomain() }.ifEmpty { null }
            },
            apiCall = { api.getGenres() },
            mapAndSave = { response ->
                val entities = response.results?.map { it.toEntity() } ?: emptyList()
                dao.insertAll(entities)
                dao.getAllGenresOnce().map { it.toDomain() }
            }
        )
}
