package com.example.game_your_game.games.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.game_your_game.games.data.local.entity.GameEntity

@Dao
interface GamesDao {

    @Query("SELECT * FROM games WHERE genreId = :genreId ORDER BY page ASC, id ASC")
    suspend fun getGamesByGenreOnce(genreId: Int): List<GameEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(games: List<GameEntity>)

    @Query("DELETE FROM games WHERE genreId = :genreId")
    suspend fun deleteByGenre(genreId: Int)
}
