package com.example.game_your_game.genres.data.repository

import com.example.game_your_game.core.utilits.NetworkStateResource
import com.example.game_your_game.core.utilits.genericApiCall
import com.example.game_your_game.genres.data.mapper.toDomain
import com.example.game_your_game.genres.data.remote.GenresApi
import com.example.game_your_game.genres.domain.model.Genre
import com.example.game_your_game.genres.domain.repository.GenresRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GenresRepositoryImpl @Inject constructor(
    private val api: GenresApi
) : GenresRepository {

    override fun getGenres(): Flow<NetworkStateResource<List<Genre>>> =
        genericApiCall(
            apiCall = {api.getGenres()},
            mapper = { it.results?.map { dto -> dto.toDomain() } ?: emptyList() }
        )



}
