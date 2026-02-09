package com.example.game_your_game.games.domain.repository

import com.example.game_your_game.games.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface GamesRepository {

    fun getGamesByGenre(genreId: Int): Flow<Result<List<Game>>>
}
