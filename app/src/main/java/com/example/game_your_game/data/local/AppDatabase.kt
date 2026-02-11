package com.example.game_your_game.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.game_your_game.gamedetails.data.local.dao.GameDetailsDao
import com.example.game_your_game.gamedetails.data.local.entity.GameDetailsEntity
import com.example.game_your_game.genres.data.local.dao.GenresDao
import com.example.game_your_game.genres.data.local.entity.GenreEntity
import com.example.game_your_game.games.data.local.dao.GamesDao
import com.example.game_your_game.games.data.local.entity.GameEntity

@Database(
    entities = [
        GenreEntity::class,
        GameEntity::class,
        GameDetailsEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun genresDao(): GenresDao
    abstract fun gamesDao(): GamesDao
    abstract fun gameDetailsDao(): GameDetailsDao
}
