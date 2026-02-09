package com.example.game_your_game.genres.data.repository

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

    override fun getGenres(): Flow<Result<List<Genre>>> = flow {
        try {
            val response = api.getGenres()
            val list = response.results?.map { it.toDomain() } ?: emptyList()
            emit(Result.success(list))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
