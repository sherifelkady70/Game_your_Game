package com.example.game_your_game.gamedetails.domain.repository

import com.example.game_your_game.gamedetails.domain.model.GameDetails
import kotlinx.coroutines.flow.Flow

interface GameDetailsRepository {

    fun getGameDetails(gameId: Int): Flow<Result<GameDetails>>
}
