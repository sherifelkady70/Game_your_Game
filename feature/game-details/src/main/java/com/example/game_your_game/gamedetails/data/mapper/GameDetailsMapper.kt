package com.example.game_your_game.gamedetails.data.mapper

import com.example.game_your_game.gamedetails.data.remote.dto.GameDetailsDto
import com.example.game_your_game.gamedetails.domain.model.GameDetails

fun GameDetailsDto.toDomain(): GameDetails = GameDetails(
    id = id,
    name = name.orEmpty(),
    imageUrl = backgroundImage,
    releaseDate = released,
    rating = rating,
    description = (descriptionRaw ?: description).orEmpty()
)
