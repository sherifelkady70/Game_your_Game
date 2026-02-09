package com.example.game_your_game.genres.domain.usecase

import com.example.game_your_game.core.utilits.NetworkStateResource
import com.example.game_your_game.genres.domain.model.Genre
import com.example.game_your_game.genres.domain.repository.GenresRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val repository: GenresRepository
) {
    operator fun invoke(): Flow<NetworkStateResource<List<Genre>>> = repository.getGenres()
}
