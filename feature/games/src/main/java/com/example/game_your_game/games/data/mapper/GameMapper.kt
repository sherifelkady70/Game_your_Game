package com.example.game_your_game.games.data.mapper

import com.example.game_your_game.games.data.local.entity.GameEntity
import javax.inject.Inject
import com.example.game_your_game.games.data.remote.dto.GameDto
import com.example.game_your_game.games.domain.model.Game



class GameMapperImpl @Inject constructor() : GameMapper {
    override fun mapToDomain(dto: GameDto): Game = Game(
        id = dto.id,
        name = dto.name.orEmpty(),
        imageUrl = dto.backgroundImage,
        rating = dto.rating
    )

    override fun mapToEntity(dto: GameDto, genreId: Int, page: Int): GameEntity = GameEntity(
        id = dto.id,
        genreId = genreId,
        name = dto.name.orEmpty(),
        imageUrl = dto.backgroundImage,
        rating = dto.rating,
        page = page
    )

    override fun mapToDomain(entity: GameEntity): Game = Game(
        id = entity.id,
        name = entity.name,
        imageUrl = entity.imageUrl,
        rating = entity.rating
    )
}
