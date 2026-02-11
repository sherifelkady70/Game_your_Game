package com.example.game_your_game.gamedetails.data.mapper

import com.example.game_your_game.gamedetails.data.local.entity.GameDetailsEntity
import com.example.game_your_game.gamedetails.data.remote.dto.GameDetailsDto
import com.example.game_your_game.gamedetails.domain.model.GameDetails
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GameDetailsMapperImplTest {

    private lateinit var mapper: GameDetailsMapperImpl

    @Before
    fun setup() {
        mapper = GameDetailsMapperImpl()
    }

    @Test
    fun `mapToDomain from DTO`() {
        val dto = GameDetailsDto(
            id = 1,
            name = "Game",
            backgroundImage = "https://img.url",
            released = "2024-01-15",
            rating = 4.5,
            description = "Short",
            descriptionRaw = "Long description"
        )
        val result = mapper.mapToDomain(dto)
        assertEquals(1, result.id)
        assertEquals("Game", result.name)
        assertEquals("https://img.url", result.imageUrl)
        assertEquals("2024-01-15", result.releaseDate)
        assertEquals("4.5", result.rating)
        assertEquals("Long description", result.description)
    }

    @Test
    fun `mapToEntity from DTO`() {
        val dto = GameDetailsDto(id = 2, name = "Title", released = "2023-06-01", rating = 3.5)
        val result = mapper.mapToEntity(dto)
        assertEquals(
            GameDetailsEntity(id = 2, name = "Title", imageUrl = null, releaseDate = "2023-06-01", rating = 3.5, description = ""),
            result
        )
    }

    @Test
    fun `mapToDomain from entity`() {
        val entity = GameDetailsEntity(
            id = 3,
            name = "Entity",
            imageUrl = "url",
            releaseDate = "2022-01-01",
            rating = 4.0,
            description = "Desc"
        )
        val result = mapper.mapToDomain(entity)
        assertEquals(3, result.id)
        assertEquals("Entity", result.name)
        assertEquals("4.0", result.rating)
        assertEquals("Desc", result.description)
    }
}
