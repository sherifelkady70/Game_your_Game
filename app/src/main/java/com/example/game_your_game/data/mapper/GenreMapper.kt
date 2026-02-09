package com.example.game_your_game.data.mapper

import com.example.game_your_game.data.remote.dto.GenreDto
import com.example.game_your_game.domain.model.Genre

fun GenreDto.toDomain(): Genre = Genre(
    id = id,
    name = name.orEmpty(),
    slug = slug.orEmpty()
)
