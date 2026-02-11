package com.example.game_your_game.games.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "games",
    primaryKeys = ["id", "genreId"],
    indices = [Index(value = ["genreId"])]
)
data class GameEntity(
    val id: Int,
    val genreId: Int,
    val name: String,
    val imageUrl: String?,
    val rating: Double?,
    val page: Int
)
