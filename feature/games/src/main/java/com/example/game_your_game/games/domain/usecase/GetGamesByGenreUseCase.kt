package com.example.game_your_game.games.domain.usecase

import com.example.game_your_game.games.domain.model.Game
import com.example.game_your_game.games.domain.repository.GamesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGamesByGenreUseCase @Inject constructor(
    private val repository: GamesRepository
) {
    operator fun invoke(genreId: Int): Flow<Result<List<Game>>> =
        repository.getGamesByGenre(genreId)
}
