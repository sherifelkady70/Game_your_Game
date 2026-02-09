package com.example.game_your_game.games.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GamesResponseDto(
    @SerializedName("results") val results: List<GameDto>? = null
)

data class GameDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String? = null,
    @SerializedName("background_image") val backgroundImage: String? = null,
    @SerializedName("rating") val rating: Double? = null,
    @SerializedName("released") val released: String? = null
)
