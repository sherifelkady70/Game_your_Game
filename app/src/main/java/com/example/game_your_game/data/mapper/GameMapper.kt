package com.example.game_your_game.data.mapper

import com.example.game_your_game.data.remote.dto.GameDto
import com.example.game_your_game.domain.model.Game

fun GameDto.toDomain(): Game = Game(
    id = id,
    name = name.orEmpty(),
    imageUrl = backgroundImage,
    rating = rating
)
