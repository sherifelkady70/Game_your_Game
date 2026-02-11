package com.example.game_your_game.genres.domain.usecase

import com.example.game_your_game.core.utilits.NetworkStateResource
import com.example.game_your_game.genres.domain.model.Genre
import com.example.game_your_game.genres.domain.repository.GenresRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetGenresUseCaseTest {

    private val repository: GenresRepository = mockk()
    private lateinit var useCase: GetGenresUseCase

    @Before
    fun setup() {
        useCase = GetGenresUseCase(repository)
    }

    @Test
    fun `invoke returns genres sorted by id`() = runTest {
        val unsorted = listOf(
            Genre(3, "RPG", "rpg"),
            Genre(1, "Action", "action"),
            Genre(2, "Adventure", "adventure")
        )
        coEvery { repository.getGenres() } returns flowOf(NetworkStateResource.Success(unsorted))

        val results = mutableListOf<NetworkStateResource<List<Genre>>>()
        useCase().collect { results.add(it) }

        assertEquals(1, results.size)
        val success = results[0] as? NetworkStateResource.Success
        assertTrue(success != null)
        assertEquals(listOf(Genre(1, "Action", "action"), Genre(2, "Adventure", "adventure"), Genre(3, "RPG", "rpg")), success!!.data)
    }

    @Test
    fun `invoke Error as a Runtime Exception from repository`() = runTest {
        val error = RuntimeException("Network error")
        coEvery { repository.getGenres() } returns flowOf(
            NetworkStateResource.Loading,
            NetworkStateResource.Error(error)
        )

        val results = mutableListOf<NetworkStateResource<List<Genre>>>()
        useCase().collect { results.add(it) }

        assertEquals(2, results.size)
        assertTrue(results[0] is NetworkStateResource.Loading)
        assertTrue(results[1] is NetworkStateResource.Error)
        assertEquals(error, (results[1] as NetworkStateResource.Error).throwable)
    }
}
