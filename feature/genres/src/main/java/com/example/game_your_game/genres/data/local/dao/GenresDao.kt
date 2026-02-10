package com.example.game_your_game.genres.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.game_your_game.genres.data.local.entity.GenreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GenresDao {

    @Query("SELECT * FROM genres ORDER BY name ASC")
    fun getAllGenres(): Flow<List<GenreEntity>>

    @Query("SELECT * FROM genres ORDER BY name ASC")
    suspend fun getAllGenresOnce(): List<GenreEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(genres: List<GenreEntity>)

    @Query("DELETE FROM genres")
    suspend fun deleteAll()
}
