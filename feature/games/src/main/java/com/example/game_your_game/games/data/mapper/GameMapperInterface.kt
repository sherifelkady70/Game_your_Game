package com.example.game_your_game.games.data.mapper

import com.example.game_your_game.games.data.local.entity.GameEntity
import com.example.game_your_game.games.data.remote.dto.GameDto
import com.example.game_your_game.games.domain.model.Game

interface GameMapper {
    fun mapToDomain(dto: GameDto): Game
    fun mapToEntity(dto: GameDto, genreId: Int, page: Int): GameEntity
    fun mapToDomain(entity: GameEntity): Game
}