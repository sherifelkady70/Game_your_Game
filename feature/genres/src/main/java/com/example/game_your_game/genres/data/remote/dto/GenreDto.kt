package com.example.game_your_game.genres.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GenresResponseDto(
    val results: List<GenreDto>? = null
)

data class GenreDto(
    val id: Int,
    val name: String? = null,
    val slug: String? = null,
    @SerializedName("image_background") val imageBackground: String? = null
)
