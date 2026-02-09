package com.example.game_your_game.gamedetails.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GameDetailsDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String? = null,
    @SerializedName("background_image") val backgroundImage: String? = null,
    @SerializedName("released") val released: String? = null,
    @SerializedName("rating") val rating: Double? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("description_raw") val descriptionRaw: String? = null
)
