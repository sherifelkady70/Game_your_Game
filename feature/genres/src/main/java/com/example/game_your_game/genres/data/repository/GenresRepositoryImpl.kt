package com.example.game_your_game.genres.data.repository

import com.example.game_your_game.core.utilits.NetworkStateResource
import com.example.game_your_game.core.utilits.apiCallWithHandlingOffline
import com.example.game_your_game.genres.data.local.dao.GenresDao
import com.example.game_your_game.genres.data.mapper.GenreMapper
import com.example.game_your_game.genres.data.remote.GenresApi
import com.example.game_your_game.genres.domain.model.Genre
import com.example.game_your_game.genres.domain.repository.GenresRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GenresRepositoryImpl @Inject constructor(
    private val api: GenresApi,
    private val dao: GenresDao,
    private val mapper: GenreMapper
) : GenresRepository {

    override fun getGenres(): Flow<NetworkStateResource<List<Genre>>> =
        apiCallWithHandlingOffline(
            readCache = {
                dao.getAllGenresOnce().map { mapper.mapToDomain(it) }.ifEmpty { null }
            },
            apiCall = { api.getGenres() },
            mapAndSave = { response ->
                val entities = response.results?.map { mapper.mapToEntity(it) } ?: emptyList()
                dao.insertAll(entities)
                dao.getAllGenresOnce().map { mapper.mapToDomain(it) }
            },
            emptyCheck = { it.isEmpty() }
        )
}
