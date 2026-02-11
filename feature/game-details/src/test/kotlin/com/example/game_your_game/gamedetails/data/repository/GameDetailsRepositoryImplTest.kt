package com.example.game_your_game.gamedetails.data.repository

import com.example.game_your_game.core.utilits.NetworkStateResource
import com.example.game_your_game.gamedetails.data.local.dao.GameDetailsDao
import com.example.game_your_game.gamedetails.data.local.entity.GameDetailsEntity
import com.example.game_your_game.gamedetails.data.mapper.GameDetailsMapper
import com.example.game_your_game.gamedetails.data.remote.GameDetailsApi
import com.example.game_your_game.gamedetails.data.remote.dto.GameDetailsDto
import com.example.game_your_game.gamedetails.domain.model.GameDetails
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

class GameDetailsRepositoryImplTest {

    private val api: GameDetailsApi = mockk()
    private val dao: GameDetailsDao = mockk()
    private val mapper: GameDetailsMapper = mockk()
    private lateinit var repository: GameDetailsRepositoryImpl

    @Before
    fun setup() {
        repository = GameDetailsRepositoryImpl(api, dao, mapper)
    }

    @Test
    fun `emits Loading then Success when cache empty and api succeeds`() = runTest {
        val gameId = 1
        val dto = GameDetailsDto(id = gameId, name = "Game", released = "2024-01-15", rating = 4.5)
        val entity = GameDetailsEntity(gameId, "Game", null, "2024-01-15", 4.5, null)
        val domain = GameDetails(gameId, "Game", null, "2024-01-15", "4.5", null)

        coEvery { dao.getById(gameId) } returns null
        coEvery { api.getGameDetails(gameId) } returns dto
        coEvery { mapper.mapToEntity(dto) } returns entity
        coEvery { mapper.mapToDomain(dto) } returns domain
        coEvery { dao.insert(any()) } just Runs

        val results = mutableListOf<NetworkStateResource<GameDetails>>()
        repository.getGameDetails(gameId).collect { results.add(it) }

        assertEquals(2, results.size)
        assertTrue(results[0] is NetworkStateResource.Loading)
        val success = results[1] as? NetworkStateResource.Success
        assertTrue(success != null)
        assertEquals(domain, success!!.data)
        coVerify { api.getGameDetails(gameId) }
        coVerify { dao.insert(any()) }
    }

    @Test
    fun `emits Loading then Success when cache exists`() = runTest {
        val gameId = 42
        val cachedEntity = GameDetailsEntity(gameId, "Cached", null, null, null, null)
        val cachedDomain = GameDetails(gameId, "Cached", null, null, null, null)
        val dto = GameDetailsDto(id = gameId, name = "Updated", rating = 4.0)
        val entity = GameDetailsEntity(gameId, "Updated", null, null, 4.0, null)
        val updatedDomain = GameDetails(gameId, "Updated", null, null, "4.0", null)

        coEvery { dao.getById(gameId) } returns cachedEntity andThen entity
        coEvery { mapper.mapToDomain(cachedEntity) } returns cachedDomain
        coEvery { mapper.mapToDomain(entity) } returns updatedDomain
        coEvery { api.getGameDetails(gameId) } returns dto
        coEvery { mapper.mapToEntity(dto) } returns entity
        coEvery { dao.insert(any()) } just Runs

        val results = mutableListOf<NetworkStateResource<GameDetails>>()
        repository.getGameDetails(gameId).collect { results.add(it) }

        assertTrue(results.size >= 2)
        assertTrue(results[0] is NetworkStateResource.Loading)
        assertTrue(results[1] is NetworkStateResource.Success)
        assertEquals(cachedDomain, (results[1] as NetworkStateResource.Success).data)
        val last = results.last() as? NetworkStateResource.Success
        assertTrue(last != null)
        assertEquals(updatedDomain, last!!.data)
    }

    @Test
    fun `emits Loading then Error when getGameDetails api throws`() = runTest {
        val gameId = 1
        coEvery { dao.getById(gameId) } returns null
        val error = RuntimeException("Not found")
        coEvery { api.getGameDetails(gameId) } throws error

        val results = mutableListOf<NetworkStateResource<GameDetails>>()
        repository.getGameDetails(gameId).collect { results.add(it) }

        assertEquals(2, results.size)
        assertTrue(results[0] is NetworkStateResource.Loading)
        assertTrue(results[1] is NetworkStateResource.Error)
        assertEquals(error, (results[1] as NetworkStateResource.Error).throwable)
    }
}
