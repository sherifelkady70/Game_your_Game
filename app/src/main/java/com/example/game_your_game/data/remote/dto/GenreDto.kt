package com.example.game_your_game.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GenresResponseDto(
    @SerializedName("results") val results: List<GenreDto>? = null
)

data class GenreDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String? = null,
    @SerializedName("slug") val slug: String? = null,
    @SerializedName("image_background") val imageBackground: String? = null
)
