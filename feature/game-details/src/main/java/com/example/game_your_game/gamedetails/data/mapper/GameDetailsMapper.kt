package com.example.game_your_game.gamedetails.data.mapper

import com.example.game_your_game.gamedetails.data.local.entity.GameDetailsEntity
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

fun GameDetailsDto.toEntity(): GameDetailsEntity = GameDetailsEntity(
    id = id,
    name = name.orEmpty(),
    imageUrl = backgroundImage,
    releaseDate = released,
    rating = rating,
    description = (descriptionRaw ?: description).orEmpty()
)

fun GameDetailsEntity.toDomain(): GameDetails = GameDetails(
    id = id,
    name = name,
    imageUrl = imageUrl,
    releaseDate = releaseDate,
    rating = rating,
    description = description
)
