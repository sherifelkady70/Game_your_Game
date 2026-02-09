package com.example.game_your_game.genres.domain.repository

import com.example.game_your_game.core.utilits.NetworkStateResource
import com.example.game_your_game.genres.domain.model.Genre
import kotlinx.coroutines.flow.Flow

interface GenresRepository {

    fun getGenres(): Flow<NetworkStateResource<List<Genre>>>
}
