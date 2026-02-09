package com.example.game_your_game.genres.domain.repository

import com.example.game_your_game.genres.domain.model.Genre
import kotlinx.coroutines.flow.Flow

interface GenresRepository {

    fun getGenres(): Flow<Result<List<Genre>>>
}
