package com.example.game_your_game.genres.data.repository

import com.example.game_your_game.core.utilits.NetworkStateResource
import com.example.game_your_game.genres.data.local.dao.GenresDao
import com.example.game_your_game.genres.data.local.entity.GenreEntity
import com.example.game_your_game.genres.data.mapper.GenreMapper
import com.example.game_your_game.genres.data.remote.GenresApi
import com.example.game_your_game.genres.data.remote.dto.GenreDto
import com.example.game_your_game.genres.data.remote.dto.GenresResponseDto
import com.example.game_your_game.genres.domain.model.Genre
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

class GenresRepositoryImplTest {

    private val api: GenresApi = mockk()
    private val dao: GenresDao = mockk()
    private val mapper: GenreMapper = mockk()
    private lateinit var repository: GenresRepositoryImpl

    @Before
    fun setup() {
        repository = GenresRepositoryImpl(api, dao, mapper)
    }

    @Test
    fun `emits Loading then Success when cache empty and api succeeds`() = runTest {
        val genreDto = GenreDto(1, "Action", "action")
        val response = GenresResponseDto(results = listOf(genreDto))
        val entity = GenreEntity(1, "Action", "action")
        val domain = Genre(1, "Action", "action")

        coEvery { dao.getAllGenresOnce() } returns emptyList() andThen listOf(entity)
        coEvery { api.getGenres() } returns response
        coEvery { mapper.mapToEntity(genreDto) } returns entity
        coEvery { mapper.mapToDomain(entity) } returns domain
        coEvery { dao.insertAll(any()) }

        val results = mutableListOf<NetworkStateResource<List<Genre>>>()
        repository.getGenres().collect { results.add(it) }

        assertEquals(2, results.size)
        assertTrue(results[0] is NetworkStateResource.Loading)
        val success = results[1] as? NetworkStateResource.Success
        assertTrue(success != null)
        assertEquals(listOf(domain), success!!.data)
        coVerify { api.getGenres() }
        coVerify { dao.insertAll(any()) }
    }

    @Test
    fun `emits Loading then Success cached then Success when cache exists`() = runTest {
        val entity = GenreEntity(1, "Cached", "cached")
        val domain = Genre(1, "Cached", "cached")
        val genreDto = GenreDto(2, "New", "new")
        val response = GenresResponseDto(results = listOf(genreDto))
        val newEntity = GenreEntity(2, "New", "new")
        val newDomain = Genre(2, "New", "new")

        coEvery { dao.getAllGenresOnce() } returns listOf(entity) andThen listOf(newEntity)
        coEvery { mapper.mapToDomain(entity) } returns domain
        coEvery { mapper.mapToDomain(newEntity) } returns newDomain
        coEvery { api.getGenres() } returns response
        coEvery { mapper.mapToEntity(genreDto) } returns newEntity
        coEvery { dao.insertAll(any()) } just Runs

        val results = mutableListOf<NetworkStateResource<List<Genre>>>()
        repository.getGenres().collect { results.add(it) }

        assertTrue(results.size >= 2)
        assertTrue(results[0] is NetworkStateResource.Loading)
        assertTrue(results[1] is NetworkStateResource.Success)
        assertEquals(listOf(domain), (results[1] as NetworkStateResource.Success).data)
        val lastSuccess = results.last() as? NetworkStateResource.Success
        assertTrue(lastSuccess != null)
        assertEquals(listOf(newDomain), lastSuccess!!.data)
    }

    @Test
    fun `emits Loading then Error when genres api throws`() = runTest {
        coEvery { dao.getAllGenresOnce() } returns emptyList()
        val error = RuntimeException("Network error")
        coEvery { api.getGenres() } throws error

        val results = mutableListOf<NetworkStateResource<List<Genre>>>()
        repository.getGenres().collect { results.add(it) }

        assertEquals(2, results.size)
        assertTrue(results[0] is NetworkStateResource.Loading)
        assertTrue(results[1] is NetworkStateResource.Error)
        assertEquals(error, (results[1] as NetworkStateResource.Error).throwable)
    }
}
