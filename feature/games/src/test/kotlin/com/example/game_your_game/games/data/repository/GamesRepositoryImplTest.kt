package com.example.game_your_game.games.data.repository

import com.example.game_your_game.core.utilits.NetworkStateResource
import com.example.game_your_game.games.data.local.dao.GamesDao
import com.example.game_your_game.games.data.local.entity.GameEntity
import com.example.game_your_game.games.data.mapper.GameMapper
import com.example.game_your_game.games.data.remote.GamesApi
import com.example.game_your_game.games.data.remote.dto.GameDto
import com.example.game_your_game.games.data.remote.dto.GamesResponseDto
import com.example.game_your_game.games.domain.model.Game
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GamesRepositoryImplTest {

    private val api: GamesApi = mockk()
    private val dao: GamesDao = mockk()
    private val mapper: GameMapper = mockk()
    private lateinit var repository: GamesRepositoryImpl

    @Before
    fun setup() {
        repository = GamesRepositoryImpl(api, dao, mapper)
    }

    @Test
    fun `emits Loading then Success when cache empty and api succeeds`() = runTest {
        val genreId = 5
        val page = 1
        val gameDto = GameDto(1, "Game A", "https://img.url", 4.5)
        val response = GamesResponseDto(results = listOf(gameDto))
        val entity = GameEntity(1, genreId, "Game A", "https://img.url", 4.5, page)
        val domain = Game(1, "Game A", "https://img.url", 4.5)

        coEvery { dao.getGamesByGenreOnce(genreId) } returns emptyList() andThen listOf(entity)
        coEvery { api.getGamesByGenre(genreId, page=page) } returns response
        coEvery { mapper.mapToEntity(gameDto, genreId, page) } returns entity
        coEvery { mapper.mapToDomain(entity) } returns domain
        coEvery { dao.deleteByGenre(genreId) } just Runs
        coEvery { dao.insertAll(any()) } just Runs

        val results = mutableListOf<NetworkStateResource<List<Game>>>()
        repository.getGamesByGenre(genreId, page).collect { results.add(it) }

        assertEquals(2, results.size)
        assertTrue(results[0] is NetworkStateResource.Loading)
        val success = results[1] as? NetworkStateResource.Success
        assertTrue(success != null)
        assertEquals(listOf(domain), success!!.data)
        coVerify { dao.deleteByGenre(genreId) }
        coVerify { api.getGamesByGenre(genreId, page= page) }
        coVerify { dao.insertAll(any()) }
    }



    @Test
    fun `emits Loading then Error when api getGamesByGenre throws`() = runTest {
        val genreId = 10
        coEvery { dao.getGamesByGenreOnce(genreId) } returns emptyList()
        val error = RuntimeException("Network error")
        coEvery { api.getGamesByGenre(genreId, page= 1) } throws error

        val results = mutableListOf<NetworkStateResource<List<Game>>>()
        repository.getGamesByGenre(genreId, 1).collect { results.add(it) }

        assertEquals(2, results.size)
        assertTrue(results[0] is NetworkStateResource.Loading)
        assertTrue(results[1] is NetworkStateResource.Error)
        assertEquals(error, (results[1] as NetworkStateResource.Error).throwable)
    }
}
