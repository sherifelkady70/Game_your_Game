package com.example.game_your_game.gamedetails.data.mapper

import com.example.game_your_game.gamedetails.data.local.entity.GameDetailsEntity
import javax.inject.Inject
import com.example.game_your_game.gamedetails.data.remote.dto.GameDetailsDto
import com.example.game_your_game.gamedetails.domain.model.GameDetails



class GameDetailsMapperImpl @Inject constructor() : GameDetailsMapper {
    override fun mapToDomain(dto: GameDetailsDto): GameDetails = GameDetails(
        id = dto.id,
        name = dto.name.orEmpty(),
        imageUrl = dto.backgroundImage,
        releaseDate = dto.released,
        rating = dto.rating,
        description = (dto.descriptionRaw ?: dto.description).orEmpty()
    )

    override fun mapToEntity(dto: GameDetailsDto): GameDetailsEntity = GameDetailsEntity(
        id = dto.id,
        name = dto.name.orEmpty(),
        imageUrl = dto.backgroundImage,
        releaseDate = dto.released,
        rating = dto.rating,
        description = (dto.descriptionRaw ?: dto.description).orEmpty()
    )

    override fun mapToDomain(entity: GameDetailsEntity): GameDetails = GameDetails(
        id = entity.id,
        name = entity.name,
        imageUrl = entity.imageUrl,
        releaseDate = entity.releaseDate,
        rating = entity.rating,
        description = entity.description
    )
}
