package com.example.game_your_game.gamedetails.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_details")
data class GameDetailsEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String?,
    val releaseDate: String?,
    val rating: Double?,
    val description: String?
)
