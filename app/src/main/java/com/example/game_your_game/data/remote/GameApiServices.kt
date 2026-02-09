package com.example.game_your_game.data.remote

import com.example.game_your_game.data.remote.dto.GameDetailsDto
import com.example.game_your_game.data.remote.dto.GamesResponseDto
import com.example.game_your_game.data.remote.dto.GenresResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GameApiServices {

    @GET("genres")
    suspend fun getGenres(): GenresResponseDto

    @GET("games")
    suspend fun getGamesByGenre(
        @Query("genres") genreId: Int,
        @Query("page_size") pageSize: Int = 20
    ): GamesResponseDto

    @GET("games/{id}")
    suspend fun getGameDetails(@Path("id") gameId: Int): GameDetailsDto
}
