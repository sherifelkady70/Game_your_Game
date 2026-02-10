package com.example.game_your_game.gamedetails.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.game_your_game.gamedetails.data.local.dao.GameDetailsDao
import com.example.game_your_game.gamedetails.data.local.entity.GameDetailsEntity

@Database(
    entities = [GameDetailsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GameDetailsDatabase : RoomDatabase() {
    abstract fun gameDetailsDao(): GameDetailsDao
}
