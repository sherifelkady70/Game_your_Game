package com.example.game_your_game.gamedetails.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.game_your_game.gamedetails.data.local.entity.GameDetailsEntity

@Dao
interface GameDetailsDao {

    @Query("SELECT * FROM game_details WHERE id = :gameId LIMIT 1")
    suspend fun getById(gameId: Int): GameDetailsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(details: GameDetailsEntity)
}
