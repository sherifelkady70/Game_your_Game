package com.example.game_your_game.games.data.mapper

import com.example.game_your_game.games.data.remote.dto.GameDto
import com.example.game_your_game.games.domain.model.Game

fun GameDto.toDomain(): Game = Game(
    id = id,
    name = name.orEmpty(),
    imageUrl = backgroundImage,
    rating = rating
)
