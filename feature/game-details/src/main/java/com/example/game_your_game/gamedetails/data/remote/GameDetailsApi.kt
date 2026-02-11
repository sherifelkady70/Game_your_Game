package com.example.game_your_game.gamedetails.data.remote

import com.example.game_your_game.gamedetails.data.remote.dto.GameDetailsDto
import retrofit2.http.GET
import retrofit2.http.Path

interface GameDetailsApi {

    @GET("games/{id}")
    suspend fun getGameDetails(@Path("id") gameId: Int): GameDetailsDto
}
