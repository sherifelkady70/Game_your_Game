package com.example.game_your_game.genres.data.mapper

import com.example.game_your_game.genres.data.remote.dto.GenreDto
import com.example.game_your_game.genres.domain.model.Genre

fun GenreDto.toDomain(): Genre = Genre(
    id = id,
    name = name.orEmpty(),
    slug = slug.orEmpty()
)
