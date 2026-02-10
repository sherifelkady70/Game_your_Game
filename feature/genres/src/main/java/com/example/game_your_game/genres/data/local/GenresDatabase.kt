package com.example.game_your_game.genres.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.game_your_game.genres.data.local.dao.GenresDao
import com.example.game_your_game.genres.data.local.entity.GenreEntity

@Database(
    entities = [GenreEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GenresDatabase : RoomDatabase() {
    abstract fun genresDao(): GenresDao
}
