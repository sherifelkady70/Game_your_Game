package com.example.game_your_game.gamedetails.data.mapper

import com.example.game_your_game.gamedetails.data.local.entity.GameDetailsEntity
import com.example.game_your_game.gamedetails.data.remote.dto.GameDetailsDto
import com.example.game_your_game.gamedetails.domain.model.GameDetails

interface GameDetailsMapper {
    fun mapToDomain(dto: GameDetailsDto): GameDetails
    fun mapToEntity(dto: GameDetailsDto): GameDetailsEntity
    fun mapToDomain(entity: GameDetailsEntity): GameDetails
}