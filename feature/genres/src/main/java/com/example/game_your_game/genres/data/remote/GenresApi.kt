package com.example.game_your_game.genres.data.remote

import com.example.game_your_game.genres.data.remote.dto.GenresResponseDto
import retrofit2.http.GET

interface GenresApi {

    @GET("genres")
    suspend fun getGenres(): GenresResponseDto
}
