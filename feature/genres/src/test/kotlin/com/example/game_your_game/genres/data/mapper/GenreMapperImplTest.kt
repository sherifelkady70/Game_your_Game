package com.example.game_your_game.genres.data.mapper

import com.example.game_your_game.genres.data.local.entity.GenreEntity
import com.example.game_your_game.genres.data.remote.dto.GenreDto
import com.example.game_your_game.genres.domain.model.Genre
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GenreMapperImplTest {

    private lateinit var mapper: GenreMapperImpl

    @Before
    fun setup() {
        mapper = GenreMapperImpl()
    }

    @Test
    fun `mapToDomain from DTO`() {
        val dto = GenreDto(id = 1, name = "Action", slug = "action")
        val result = mapper.mapToDomain(dto)
        assertEquals(Genre(id = 1, name = "Action", slug = "action"), result)
    }

    @Test
    fun `mapToDomain from DTO with null name and slug uses orEmpty`() {
        val dto = GenreDto(id = 2, name = null, slug = null)
        val result = mapper.mapToDomain(dto)
        assertEquals(Genre(id = 2, name = "", slug = ""), result)
    }

    @Test
    fun `mapToEntity from DTO`() {
        val dto = GenreDto(id = 1, name = "RPG", slug = "rpg")
        val result = mapper.mapToEntity(dto)
        assertEquals(GenreEntity(id = 1, name = "RPG", slug = "rpg"), result)
    }

    @Test
    fun `mapToDomain from entity`() {
        val entity = GenreEntity(id = 3, name = "Adventure", slug = "adventure")
        val result = mapper.mapToDomain(entity)
        assertEquals(Genre(id = 3, name = "Adventure", slug = "adventure"), result)
    }
}
