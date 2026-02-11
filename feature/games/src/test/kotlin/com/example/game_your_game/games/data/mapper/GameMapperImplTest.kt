package com.example.game_your_game.games.data.mapper

import com.example.game_your_game.games.data.local.entity.GameEntity
import com.example.game_your_game.games.data.remote.dto.GameDto
import com.example.game_your_game.games.domain.model.Game
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GameMapperImplTest {

    private lateinit var mapper: GameMapperImpl

    @Before
    fun setup() {
        mapper = GameMapperImpl()
    }

    @Test
    fun `mapToDomain from DTO`() {
        val dto = GameDto(id = 1, name = "Game A", backgroundImage = "https://img.url", rating = 4.5)
        val result = mapper.mapToDomain(dto)
        assertEquals(Game(id = 1, name = "Game A", imageUrl = "https://img.url", rating = 4.5), result)
    }

    @Test
    fun `mapToEntity from DTO with genreId and page`() {
        val dto = GameDto(id = 2, name = "Game B", backgroundImage = null, rating = null)
        val result = mapper.mapToEntity(dto, genreId = 10, page = 1)
        assertEquals(
            GameEntity(id = 2, genreId = 10, name = "Game B", imageUrl = null, rating = null, page = 1),
            result
        )
    }

    @Test
    fun `mapToDomain from entity`() {
        val entity = GameEntity(id = 3, genreId = 5, name = "Game C", imageUrl = "url", rating = 4.0, page = 2)
        val result = mapper.mapToDomain(entity)
        assertEquals(Game(id = 3, name = "Game C", imageUrl = "url", rating = 4.0), result)
    }
}
