package com.example.game_your_game.games.data.remote

import com.example.game_your_game.core.utilits.Constants
import com.example.game_your_game.games.data.remote.dto.GamesResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GamesApi {

    @GET(Constants.GAMES)
    suspend fun getGamesByGenre(
        @Query("genres") genreId: Int,
        @Query("page_size") pageSize: Int = 10,
        @Query("page") page: Int
    ): GamesResponseDto
}
