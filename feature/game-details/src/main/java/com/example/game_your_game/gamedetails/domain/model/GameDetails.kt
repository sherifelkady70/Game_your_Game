package com.example.game_your_game.gamedetails.domain.model

data class GameDetails(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val releaseDate: String?,
    val rating: Double?,
    val description: String?
)
