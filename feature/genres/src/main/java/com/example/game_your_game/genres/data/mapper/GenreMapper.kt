package com.example.game_your_game.genres.data.mapper

import com.example.game_your_game.genres.data.local.entity.GenreEntity
import javax.inject.Inject
import com.example.game_your_game.genres.data.remote.dto.GenreDto
import com.example.game_your_game.genres.domain.model.Genre



class GenreMapperImpl @Inject constructor() : GenreMapper {
    override fun mapToDomain(dto: GenreDto): Genre = Genre(
        id = dto.id,
        name = dto.name.orEmpty(),
        slug = dto.slug.orEmpty()
    )

    override fun mapToEntity(dto: GenreDto): GenreEntity = GenreEntity(
        id = dto.id,
        name = dto.name.orEmpty(),
        slug = dto.slug.orEmpty()
    )

    override fun mapToDomain(entity: GenreEntity): Genre = Genre(
        id = entity.id,
        name = entity.name,
        slug = entity.slug
    )
}
