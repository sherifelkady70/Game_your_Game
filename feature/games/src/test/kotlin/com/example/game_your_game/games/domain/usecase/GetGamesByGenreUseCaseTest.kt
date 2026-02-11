package com.example.game_your_game.games.domain.usecase

import com.example.game_your_game.core.utilits.NetworkStateResource
import com.example.game_your_game.games.domain.model.Game
import com.example.game_your_game.games.domain.repository.GamesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetGamesByGenreUseCaseTest {

    private val repository: GamesRepository = mockk()
    private lateinit var useCase: GetGamesByGenreUseCase

    @Before
    fun setup() {
        useCase = GetGamesByGenreUseCase(repository)
    }

    @Test
    fun `returns flow from repository for given genreId and page`() = runTest {
        val games = listOf(
            Game(1, "Game A", "https://img.url", 4.5),
            Game(2, "Game B", null, null)
        )
        coEvery { repository.getGamesByGenre(genreId = 5, page = 1) } returns flowOf(
            NetworkStateResource.Loading,
            NetworkStateResource.Success(games)
        )

        val results = mutableListOf<NetworkStateResource<List<Game>>>()
        useCase(genreId = 5, page = 1).collect { results.add(it) }

        assertEquals(2, results.size)
        assertTrue(results[0] is NetworkStateResource.Loading)
        val success = results[1] as? NetworkStateResource.Success
        assertTrue(success != null)
        assertEquals(games, success!!.data)
    }
}
