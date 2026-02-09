package com.example.game_your_game.data.mapper

import com.example.game_your_game.data.remote.dto.GameDetailsDto
import com.example.game_your_game.domain.model.GameDetails

fun GameDetailsDto.toDomain(): GameDetails = GameDetails(
    id = id,
    name = name.orEmpty(),
    imageUrl = backgroundImage,
    releaseDate = released,
    rating = rating,
    description = (descriptionRaw ?: description).orEmpty()
)
