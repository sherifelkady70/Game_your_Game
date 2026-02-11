package com.example.game_your_game.games.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GamesResponseDto(
    val results: List<GameDto>? = null
)

data class GameDto(
    val id: Int,
    val name: String? = null,
    @SerializedName("background_image") val backgroundImage: String? = null,
    val rating: Double? = null,
    val released: String? = null
)
