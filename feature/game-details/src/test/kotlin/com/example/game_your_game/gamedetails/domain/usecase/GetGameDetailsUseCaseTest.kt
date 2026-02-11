package com.example.game_your_game.gamedetails.domain.usecase

import com.example.game_your_game.core.utilits.NetworkStateResource
import com.example.game_your_game.gamedetails.domain.model.GameDetails
import com.example.game_your_game.gamedetails.domain.repository.GameDetailsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetGameDetailsUseCaseTest {

    private val repository: GameDetailsRepository = mockk()
    private lateinit var useCase: GetGameDetailsUseCase

    @Before
    fun setup() {
        useCase = GetGameDetailsUseCase(repository)
    }

    @Test
    fun `returns flow from repository for given gameId`() = runTest {
        val details = GameDetails(
            id = 1,
            name = "Game",
            imageUrl = "https://img.url",
            releaseDate = "2024-01-15",
            rating = "4.5",
            description = "Description"
        )
        coEvery { repository.getGameDetails(gameId = 1) } returns flowOf(
            NetworkStateResource.Loading,
            NetworkStateResource.Success(details)
        )

        val results = mutableListOf<NetworkStateResource<GameDetails>>()
        useCase(1).collect { results.add(it) }

        assertEquals(2, results.size)
        assertTrue(results[0] is NetworkStateResource.Loading)
        val success = results[1] as? NetworkStateResource.Success
        assertTrue(success != null)
        assertEquals(details, success!!.data)
    }

}
