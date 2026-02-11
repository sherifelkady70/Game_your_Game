package com.example.game_your_game.genres.data.mapper

import com.example.game_your_game.genres.data.local.entity.GenreEntity
import com.example.game_your_game.genres.data.remote.dto.GenreDto
import com.example.game_your_game.genres.domain.model.Genre

interface GenreMapper {
    fun mapToDomain(dto: GenreDto): Genre
    fun mapToEntity(dto: GenreDto): GenreEntity
    fun mapToDomain(entity: GenreEntity): Genre
}