package com.example.game_your_game.gamedetails.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GameDetailsDto(
    val id: Int,
    val name: String? = null,
    @SerializedName("background_image") val backgroundImage: String? = null,
    val released: String? = null,
    val rating: Double? = null,
    val description: String? = null,
    @SerializedName("description_raw") val descriptionRaw: String? = null
)
