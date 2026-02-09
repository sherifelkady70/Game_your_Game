package com.example.game_your_game.gamedetails.domain.usecase

import com.example.game_your_game.gamedetails.domain.model.GameDetails
import com.example.game_your_game.gamedetails.domain.repository.GameDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGameDetailsUseCase @Inject constructor(
    private val repository: GameDetailsRepository
) {
    operator fun invoke(gameId: Int): Flow<Result<GameDetails>> =
        repository.getGameDetails(gameId)
}
