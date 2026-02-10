package com.example.game_your_game.games.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.game_your_game.games.data.local.dao.GamesDao
import com.example.game_your_game.games.data.local.entity.GameEntity

@Database(
    entities = [GameEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GamesDatabase : RoomDatabase() {
    abstract fun gamesDao(): GamesDao
}
